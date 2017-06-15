package com.nisovin.magicspells.spells.targeted;

import com.nisovin.magicspells.util.TimeUtil;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.Subspell;
import com.nisovin.magicspells.events.SpellPreImpactEvent;
import com.nisovin.magicspells.spelleffects.EffectPosition;
import com.nisovin.magicspells.spells.TargetedEntityFromLocationSpell;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.BoundingBox;
import com.nisovin.magicspells.util.EffectPackage;
import com.nisovin.magicspells.util.compat.EventUtil;
import com.nisovin.magicspells.util.MagicConfig;
import com.nisovin.magicspells.util.ParticleNameUtil;
import com.nisovin.magicspells.util.TargetInfo;

import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.util.ParticleEffect.ParticleData;

public class HomingMissileSpell extends TargetedSpell implements TargetedEntitySpell, TargetedEntityFromLocationSpell {

	float projectileVelocity;
	float projectileInertia;

	int tickInterval;
	float ticksPerSecond;
	float velocityPerTick;
	int specialEffectInterval;

	String particleName;
	float particleSpeed;
	int particleCount;
	float particleHorizontalSpread;
	float particleVerticalSpread;

	int maxDuration;
	float hitRadius;
	float yOffset;
	int renderDistance;

	String hitSpellName;
	Subspell spell;
	boolean changeCasterOnReflect = true;

	HomingMissileSpell thisSpell;

	ParticleEffect effect;
	ParticleData data;
	
	boolean useParticles = false;
	
	int intermediateSpecialEffects = 0;

	public HomingMissileSpell(MagicConfig config, String spellName) {
		super(config, spellName);
		thisSpell = this;

		projectileVelocity = getConfigFloat("projectile-velocity", 5F);
		projectileInertia = getConfigFloat("projectile-inertia", 1.5F);
		tickInterval = getConfigInt("tick-interval", 2);
		ticksPerSecond = 20F / (float)tickInterval;
		velocityPerTick = projectileVelocity / ticksPerSecond;
		specialEffectInterval = getConfigInt("special-effect-interval", 0);
		particleName = getConfigString("particle-name", "reddust");
		particleSpeed = getConfigFloat("particle-speed", 0.3F);
		particleCount = getConfigInt("particle-count", 15);
		particleHorizontalSpread = getConfigFloat("particle-horizontal-spread", 0.3F);
		particleVerticalSpread = getConfigFloat("particle-vertical-spread", 0.3F);
		maxDuration = getConfigInt("max-duration", 20) * (int)TimeUtil.MILLISECONDS_PER_SECOND;
		hitRadius = getConfigFloat("hit-radius", 1.5F);
		yOffset = getConfigFloat("y-offset", 0.6F);
		renderDistance = getConfigInt("render-distance", 32);
		hitSpellName = getConfigString("spell", "");
		useParticles = getConfigBoolean("use-particles", false);
		changeCasterOnReflect = getConfigBoolean("change-caster-on-reflect", true);
		
		intermediateSpecialEffects = getConfigInt("intermediate-special-effect-locations", 0);
		if (intermediateSpecialEffects < 0) intermediateSpecialEffects = 0;
		
		EffectPackage pkg = ParticleNameUtil.findEffectPackage(particleName);
		effect = pkg.effect;
		data = pkg.data;
	}

	@Override
	public void initialize() {
		super.initialize();

		Subspell s = new Subspell(hitSpellName);
		if (s.process()) {
			spell = s;
		} else {
			MagicSpells.error("ParticleProjectileSpell " + internalName + " has an invalid spell defined!");
		}
	}

	@Override
	public PostCastAction castSpell(Player player, SpellCastState state, float power, String[] args) {
		if (state == SpellCastState.NORMAL) {
			ValidTargetChecker checker = spell != null ? spell.getSpell().getValidTargetChecker() : null;
			TargetInfo<LivingEntity> target = getTargetedEntity(player, power, checker);
			if (target == null) return noTarget(player);
			new MissileTracker(player, target.getTarget(), target.getPower());
			sendMessages(player, target.getTarget());
			return PostCastAction.NO_MESSAGES;
		}
		return PostCastAction.HANDLE_NORMALLY;
	}

	@Override
	public boolean castAtEntity(Player caster, LivingEntity target, float power) {
		if (!validTargetList.canTarget(caster, target)) return false;
		new MissileTracker(caster, target, power);
		return true;
	}

	@Override
	public boolean castAtEntity(LivingEntity target, float power) {
		if (!validTargetList.canTarget(target)) return false;
		new MissileTracker(null, target, power);
		return true;
	}

	@Override
	public boolean castAtEntityFromLocation(Player caster, Location from, LivingEntity target, float power) {
		if (!validTargetList.canTarget(caster, target)) return false;
		new MissileTracker(caster, from, target, power);
		return true;
	}

	@Override
	public boolean castAtEntityFromLocation(Location from, LivingEntity target, float power) {
		if (!validTargetList.canTarget(target)) return false;
		new MissileTracker(null, from, target, power);
		return true;
	}

	class MissileTracker implements Runnable {

		Player caster;
		LivingEntity target;
		float power;
		long startTime;
		Location currentLocation;
		Vector currentVelocity;
		int taskId;

		int counter = 0;
		
		public MissileTracker(Player caster, LivingEntity target, float power) {
			this.currentLocation = caster.getLocation().clone().add(0, yOffset, 0);
			this.currentVelocity = currentLocation.getDirection();
			init(caster, target, power);
			playSpellEffects(EffectPosition.CASTER, caster);
		}
		
		public MissileTracker(Player caster, Location startLocation, LivingEntity target, float power) {
			this.currentLocation = startLocation.clone().add(0, yOffset, 0);
			this.currentVelocity = target.getLocation().toVector().subtract(currentLocation.toVector()).normalize();
			init(caster, target, power);
			if (caster != null) {
				playSpellEffects(EffectPosition.CASTER, caster);
			} else {
				playSpellEffects(EffectPosition.CASTER, startLocation);
			}
		}
		
		private void init(Player caster, LivingEntity target, float power) {
			this.currentVelocity.multiply(velocityPerTick);
			this.caster = caster;
			this.target = target;
			this.power = power;
			this.startTime = System.currentTimeMillis();
			this.taskId = MagicSpells.scheduleRepeatingTask(this, 0, tickInterval);
		}

		@Override
		public void run() {
			// Check for valid and alive caster and target
			if ((caster != null && !caster.isValid()) || !target.isValid()) {
				stop();
				return;
			}

			// Check if target has left the world
			if (!currentLocation.getWorld().equals(target.getWorld())) {
				stop();
				return;
			}

			// Check if duration is up
			if (maxDuration > 0 && startTime + maxDuration < System.currentTimeMillis()) {
				stop();
				return;
			}
			Location oldLocation = currentLocation.clone();
			
			// Move projectile and calculate new vector
			currentLocation.add(currentVelocity);
			Vector oldVelocity = new Vector(currentVelocity.getX(), currentVelocity.getY(), currentVelocity.getZ());
			currentVelocity.multiply(projectileInertia);
			currentVelocity.add(target.getLocation().add(0, yOffset, 0).subtract(currentLocation).toVector().normalize());
			currentVelocity.normalize().multiply(velocityPerTick);

			if (intermediateSpecialEffects > 0) {
				// Time to put extra effects in between
				playIntermediateEffectLocations(oldLocation, oldVelocity);
			}
			
			// Show particle
			playMissileEffect(currentLocation);

			// Play effects
			if (specialEffectInterval > 0 && counter % specialEffectInterval == 0) {
				playSpellEffects(EffectPosition.SPECIAL, currentLocation);
			}
			counter++;

			// Check for hit
			if (hitRadius > 0 && spell != null) {
				BoundingBox hitBox = new BoundingBox(currentLocation, hitRadius);
				if (hitBox.contains(target.getLocation().add(0, yOffset, 0))) {
					// Fire off a preimpact event so reflect spells can still let us have our animation
					SpellPreImpactEvent preImpact = new SpellPreImpactEvent(spell.getSpell(), thisSpell, caster, target, power);
					EventUtil.call(preImpact);
					// Should we bounce the missile back?
					if (!preImpact.getRedirected()) {
						// Apparently didn't get redirected, carry out the plans
						if (spell.isTargetedEntitySpell()) {
							spell.castAtEntity(caster, target, power);
						} else if (spell.isTargetedLocationSpell()) {
							spell.castAtLocation(caster, target.getLocation(), power);
						}
						playSpellEffects(EffectPosition.TARGET, target);
						stop();
					} else {
						// If it got redirected, redirect it!
						redirect();
						power = preImpact.getPower();
						
					}
				}
			}
		}
		
		private void playIntermediateEffectLocations(Location old, Vector movement) {
			int divideFactor = intermediateSpecialEffects + 1;
			movement.setX(movement.getX()/divideFactor);
			movement.setY(movement.getY()/divideFactor);
			movement.setZ(movement.getZ()/divideFactor);
			for (int i = 0; i < intermediateSpecialEffects; i++) {
				old = old.add(movement).setDirection(movement);
				playMissileEffect(old);
			}
		}
		
		private void playMissileEffect(Location loc) {
			// Show particle
			if (useParticles) {
				//MagicSpells.getVolatileCodeHandler().playParticleEffect(currentLocation, particleName, particleHorizontalSpread, particleVerticalSpread, particleSpeed, particleCount, renderDistance, 0F);
				
				effect.display(data, loc, null, renderDistance, particleHorizontalSpread, particleVerticalSpread, particleHorizontalSpread, particleSpeed, particleCount);
				//ParticleData data, Location center, Color color, double range, float offsetX, float offsetY, float offsetZ, float speed, int amount
			} else {
				playSpellEffects(EffectPosition.SPECIAL, loc);
			}
		}
		
		private void redirect() {
			Player c = caster;
			Player t = (Player) target;
			caster = t;
			target = c;
			currentVelocity.multiply(-1F);
		}

		public void stop() {
			playSpellEffects(EffectPosition.DELAYED, currentLocation);
			MagicSpells.cancelTask(taskId);
			caster = null;
			target = null;
			currentLocation = null;
			currentVelocity = null;
		}

	}

}
