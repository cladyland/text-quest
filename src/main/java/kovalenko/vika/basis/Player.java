package kovalenko.vika.basis;

import kovalenko.vika.exception.PlayerSettingsException;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Player {
    @Getter
    @NonNull
    private final String nickName;
    private final Map<String, Integer> playerStatistic;
    private Integer numberOfGames;
    private Integer numberOfWins;
    private Integer numberOfDefeats;

    {
        numberOfGames = 0;
        numberOfWins = 0;
        numberOfDefeats = 0;
        playerStatistic = new HashMap<>();
    }

    public Player(String nickName) {
        this.nickName = nickName;

        log.debug("New player created: nickName='{}' games='{}' wins='{}' defeats='{}'",
                this.nickName, this.numberOfGames, this.numberOfWins, this.numberOfDefeats);
    }

    public Map<String, Integer> getPlayerStatistic() {
        setPlayerStatistic();
        return playerStatistic;
    }

    public void increaseNumberOfGames(Status status) {
        if (!isStatusToChangeStatistic(status)) {
            var exceptionMessage = String
                    .format("Status '%s' does not affect the change of the player's statistics", status);

            log.error(exceptionMessage);
            throw new PlayerSettingsException(exceptionMessage);
        }

        numberOfGames++;
        if (isVictory(status)) {
            numberOfWins++;
        } else {
            numberOfDefeats++;
        }
    }

    private boolean isStatusToChangeStatistic(Status status) {
        return isVictory(status) || status == Status.DEFEAT;
    }

    private boolean isVictory(Status status) {
        return status == Status.VICTORY;
    }

    private void setPlayerStatistic() {
        playerStatistic.put("Games", numberOfGames);
        playerStatistic.put("Wins", numberOfWins);
        playerStatistic.put("Defeats", numberOfDefeats);
    }
}
