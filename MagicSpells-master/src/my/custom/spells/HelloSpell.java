/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.custom.spells;

import org.bukkit.entity.Player;
 
import com.nisovin.magicspells.spells.InstantSpell;
import com.nisovin.magicspells.util.MagicConfig;

/**
 * Learn how to make new spells at http://nisovin.com/magicspells/customspellcreation
 * @author Travis
 */
public class HelloSpell extends InstantSpell {
    public HelloSpell(MagicConfig config, String spellName) {
	super(config, spellName);
	// TODO Auto-generated constructor stub
    }
 
    @Override
    public PostCastAction castSpell(Player player, SpellCastState state, float power, String[] args) {
	if (state == SpellCastState.NORMAL) {
            sendMessage(player, "Hello World!");
	}
	return PostCastAction.HANDLE_NORMALLY;
    }   
}
