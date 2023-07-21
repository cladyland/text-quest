package kovalenko.vika.common.entities;

import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.exception.PlayerSettingsException;
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
    @Getter
    private final Map<String, Integer> playerStatistic;
    private int numberOfGames;
    private int numberOfWins;
    private int numberOfDefeats;

    {
        playerStatistic = new HashMap<>();
    }

    public Player(String nickName) {
        this.nickName = nickName;

        log.debug("New player created: nickName='{}'; statistic: '{}'; games='{}', wins='{}', defeats='{}'",
                this.nickName, this.playerStatistic, this.numberOfGames, this.numberOfWins, this.numberOfDefeats);
    }

    public void increaseNumberOfGames(Status status) {
        checkIfStatusToChangeStatistic(status);

        numberOfGames++;
        if (isVictory(status)) {
            numberOfWins++;
        } else {
            numberOfDefeats++;
        }
        updatePlayerStatistic();

        log.debug("'{}' statistic updated: '{}'", this.nickName, this.playerStatistic);
    }

    private void checkIfStatusToChangeStatistic(Status status) {
        boolean toChange = isVictory(status) || status == Status.DEFEAT;

        if (!toChange) {
            var exceptionMessage = String
                    .format("Status '%s' does not affect the change of the player's statistics", status);

            log.error(exceptionMessage);
            throw new PlayerSettingsException(exceptionMessage);
        }
    }

    private boolean isVictory(Status status) {
        return status == Status.VICTORY;
    }

    private void updatePlayerStatistic() {
        playerStatistic.put("Games", numberOfGames);
        playerStatistic.put("Wins", numberOfWins);
        playerStatistic.put("Defeats", numberOfDefeats);
    }
}
