/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.custom.spells;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
 
import com.nisovin.magicspells.spelleffects.EffectPosition;
import com.nisovin.magicspells.spells.TargetedEntitySpell;
import com.nisovin.magicspells.spells.TargetedSpell;
import com.nisovin.magicspells.util.TargetInfo;
import com.nisovin.magicspells.util.MagicConfig;
/**
 *
 * @author Travis
 */
public class LaunchSpell extends TargetedSpell implements TargetedEntitySpell {
    private int force;
    private String strNoTarget;
    
	public LaunchSpell(MagicConfig config, String spellName) {
            super(config, spellName);
            this.force = getConfigInt("force", 5);
            this.strNoTarget = getConfigString("str-no-target", "No target found.");
	}
 
	@Override
	public PostCastAction castSpell(Player player, SpellCastState state, float power, String[] args) {
		return null;
	}
 
	@Override
	public boolean castAtEntity(Player player, LivingEntity target, float power) {
		return false;
	}
        
        @Override
	public boolean castAtEntity(LivingEntity target, float power) {
            if (!validTargetList.canTarget(target)) {
		return false;
            } else {
                return true;
            }
	}
 
}
