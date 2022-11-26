package kovalenko.vika.basis;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Player {
    @NonNull
    @Getter
    private final String nickName;
    @Getter
    @Setter
    private boolean newcomer;
    @Getter
    private Integer numberOfGames;
    @Getter
    private Integer numberOfWins;
    @Getter
    private Integer numberOfDefeats;
    private Map<String, Integer> playerStatistic;

    public Player(String nickName) {
        this.nickName = nickName;
        newcomer = true;
        numberOfGames = 0;
        numberOfWins = 0;
        numberOfDefeats = 0;
        playerStatistic = new HashMap<>();
    }

    public Map<String, Integer> getPlayerStatistic() {
        playerStatistic.put("Games", numberOfGames);
        playerStatistic.put("Wins", numberOfWins);
        playerStatistic.put("Defeats", numberOfDefeats);

        return playerStatistic;
    }

    public void increaseNumberOfGames(Status status) {
        numberOfGames++;
        if (isVictory(status)) {
            numberOfWins++;
        } else {
            numberOfDefeats++;
        }
    }

    private boolean isVictory(Status status) {
        return status.equals(Status.VICTORY);
    }
}
