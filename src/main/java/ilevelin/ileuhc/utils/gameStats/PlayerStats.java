package ilevelin.ileuhc.utils.gameStats;

import ilevelin.ileuhc.utils.enums.AppleType;
import ilevelin.ileuhc.utils.enums.DeathSource;

import java.util.HashMap;
import java.util.Map;

public class PlayerStats {

    private String playerName = "";
    private int place = 0;
    private float normalizedPlace = 0.0f;
    private Map<AppleType, Integer> applesConsumed = new HashMap<>();
    private float lifeHealed = 0.0f,
            damageDealtToPlayers = 0.0f,
            damageReceivedFromPlayers = 0.0f,
            totalDamageReceived = 0.0f;
    private int playersKilled = 0;
    private DeathSource deathSource = DeathSource.ALIVE;

    public PlayerStats(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() { return playerName; }

    public PlayerStats setPlace(int place, int participants) {
        this.place = place;
        this.normalizedPlace = ((((float)place - 1.0f) * 9.0f) / ((float)participants - 1.0f)) + 1.0f;
        return this;
    }

    public PlayerStats addConsumedApple(AppleType apple) {
        if (applesConsumed.containsKey(apple))
            applesConsumed.put(apple, applesConsumed.get(apple)+1);
        else
            applesConsumed.put(apple, 1);
        return this;
    }

    public PlayerStats addLifeHealed(float healed) { lifeHealed += healed; return this; }
    public PlayerStats addDamageToPlayers(float damage) { damageDealtToPlayers += damage; return this; }
    public PlayerStats addReceivedFromPlayers(float damage) { damageReceivedFromPlayers += damage; return this; }
    public PlayerStats addReceivedFromOther(float damage) { totalDamageReceived += damage; return this; }
    public PlayerStats addKilledPlayer() { playersKilled ++; return this; }
    public PlayerStats setDeathSource(DeathSource deathSource) {this.deathSource = deathSource; return this; }

    @Override
    public String toString() {
        return playerName + '\t' + place + '\t' + normalizedPlace + '\t' + "deathcausehere" + '\t' + "0" + '\t' +
                getApplesAsString() + '\t' + totalDamageReceived + '\t' + lifeHealed + '\t' +
                damageDealtToPlayers + '\t' + damageReceivedFromPlayers + '\t' +
                playersKilled + '\t' + (deathSource == DeathSource.PLAYER ? 1 : 0);
    }

    private String getApplesAsString() {
        return (applesConsumed.get(AppleType.GOLDEN_APPLE) == null ? 0 : applesConsumed.get(AppleType.GOLDEN_APPLE)) + "\t" +
                (applesConsumed.get(AppleType.WITHER_APPLE) == null ? 0 : applesConsumed.get(AppleType.WITHER_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.PLAYER_HEAD_APPLE) == null ? 0 : applesConsumed.get(AppleType.PLAYER_HEAD_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.SUPER_GOLD_APPLE) == null ? 0 : applesConsumed.get(AppleType.SUPER_GOLD_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.DIAMOND_APPLE) == null ? 0 : applesConsumed.get(AppleType.DIAMOND_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.BLUE_GOLD_APPLE) == null ? 0 : applesConsumed.get(AppleType.BLUE_GOLD_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.ROYAL_APPLE) == null ? 0 : applesConsumed.get(AppleType.ROYAL_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.ALLOYED_APPLE) == null ? 0 : applesConsumed.get(AppleType.ALLOYED_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.MIDAS_APPLE) == null ? 0 : applesConsumed.get(AppleType.MIDAS_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.GOD_APPLE) == null ? 0 : applesConsumed.get(AppleType.GOD_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.COLLECTOR_APPLE) == null ? 0 : applesConsumed.get(AppleType.COLLECTOR_APPLE)) + '\t' +
                (applesConsumed.get(AppleType.ENCHANTED_GOLDEN_APPLE) == null ? 0 : applesConsumed.get(AppleType.ENCHANTED_GOLDEN_APPLE));
    }
}