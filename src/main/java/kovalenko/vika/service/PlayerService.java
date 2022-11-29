package kovalenko.vika.service;

import kovalenko.vika.db.PlayerRepository;
import kovalenko.vika.basis.Player;
import kovalenko.vika.basis.Status;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static java.util.Objects.isNull;

public class PlayerService {
    private static final Logger LOG = LoggerFactory.getLogger(PlayerService.class);

    private final Player defaultPlayer;
    @NonNull
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        defaultPlayer = new Player("Default");
    }

    public Player register(String nickName) {
        if (nickNameIsBusy(nickName) || isNull(nickName)) {
            LOG.debug("The user '{}' is not registered: nickname is already taken or equals null", nickName);
            return defaultPlayer;
        }

        playerRepository.registerNewPlayer(nickName);
        return playerRepository
                .getPlayers()
                .get(nickName);
    }

    public Map<String, Integer> setAndGetPlayerStatistic(Player player, Status status) {
        String failStatsChange = "Failed to change player's statistic: ";

        if (isNull(player)) {
            LOG.error(failStatsChange + "player is null");
            throw new NullPointerException("Player cannot be null!");
        }
        if (isNull(status)) {
            LOG.error(failStatsChange + "status is null");
            throw new NullPointerException("Status cannot be null!");
        }

        player.increaseNumberOfGames(status);
        return player.getPlayerStatistic();
    }

    public boolean isDefaultPlayer(Player player) {
        return player.equals(defaultPlayer);
    }

    private boolean nickNameIsBusy(String name) {
        return playerRepository
                .getPlayers()
                .containsKey(name);
    }
}
