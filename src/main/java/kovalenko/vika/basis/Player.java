package kovalenko.vika.basis;

import kovalenko.vika.service.exception.PlayerSettingsException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private static final Logger LOG = LoggerFactory.getLogger(Player.class);

    @NonNull
    @Getter
    private final String nickName;
    private final Map<String, Integer> playerStatistic;
    @Getter
    @Setter
    private boolean newcomer;
    @Getter
    private Integer numberOfGames;
    @Getter
    private Integer numberOfWins;
    @Getter
    private Integer numberOfDefeats;

    public Player(String nickName) {
        this.nickName = nickName;
        newcomer = true;
        numberOfGames = 0;
        numberOfWins = 0;
        numberOfDefeats = 0;
        playerStatistic = new HashMap<>();

        LOG.debug("New player created: nickName='{}' isNewcomer='{}' games='{}' wins='{}' defeats='{}'",
                this.nickName, this.newcomer, this.numberOfGames, this.numberOfWins, this.numberOfDefeats);
    }

    public Map<String, Integer> getPlayerStatistic() {
        setPlayerStatistic();
        return playerStatistic;
    }

    public void increaseNumberOfGames(Status status) {
        if (!isStatusToChangeStatistic(status)){
            var exceptionMessage = String
                    .format("Status '%s' does not affect the change of the player's statistics", status);

            LOG.error(exceptionMessage);
            throw new PlayerSettingsException(exceptionMessage);
        }

        numberOfGames++;
        if (isVictory(status)) {
            numberOfWins++;
        } else {
            numberOfDefeats++;
        }
    }

    private boolean isStatusToChangeStatistic(Status status){
        return status == Status.VICTORY || status == Status.DEFEAT;
    }

    private boolean isVictory(Status status) {
        return status.equals(Status.VICTORY);
    }

    private void setPlayerStatistic(){
        playerStatistic.put("Games", numberOfGames);
        playerStatistic.put("Wins", numberOfWins);
        playerStatistic.put("Defeats", numberOfDefeats);
    }
}
