package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            RaidResult result = new RaidResult();
            result.setWinner("ERROR");
            result.addLine("Invalid input: null parameter");
            return result;
        }
        
        if (!teamA.isAlive() || !teamB.isAlive()) {
            RaidResult result = new RaidResult();
            result.setWinner("ERROR");
            result.addLine("Invalid input: team not alive at start");
            return result;
        }
        
        RaidResult result = new RaidResult();
        int round = 0;
        final int maxRounds = 100;
        
        while (teamA.isAlive() && teamB.isAlive() && round < maxRounds) {
            round++;
            result.addLine("--- Round " + round + " ---");
            
            teamASkill.cast(teamB);
            result.addLine(teamA.getName() + " attacks " + teamB.getName() + 
                          " with " + teamASkill.getSkillName() + " (" + teamASkill.getEffectName() + 
                          "). " + teamB.getName() + " HP: " + teamB.getHealth());
            
            if (teamB.isAlive()) {
                teamBSkill.cast(teamA);
                result.addLine(teamB.getName() + " attacks " + teamA.getName() + 
                              " with " + teamBSkill.getSkillName() + " (" + teamBSkill.getEffectName() + 
                              "). " + teamA.getName() + " HP: " + teamA.getHealth());
            } else {
                result.addLine(teamB.getName() + " has been defeated!");
            }
        }
        
        result.setRounds(round);
        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (!teamA.isAlive() && teamB.isAlive()) {
            result.setWinner(teamB.getName());
        } else if (!teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner("DRAW");
        } else {
            result.setWinner("MAX_ROUNDS");
        }
        
        return result;
    }
}
