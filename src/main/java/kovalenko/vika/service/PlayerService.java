package kovalenko.vika.service;

import kovalenko.vika.PlayerRepository;
import kovalenko.vika.basis.Player;
import kovalenko.vika.basis.Status;
import lombok.NonNull;

import java.util.Map;

import static java.util.Objects.isNull;

public class PlayerService {
    private final Player defaultPlayer;
    @NonNull
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        defaultPlayer = new Player("Default");
    }

    public Player register(String nickName){
        if (nickNameIsBusy(nickName) || isNull(nickName)){
            return defaultPlayer;
        }

        playerRepository.registerNewPlayer(nickName);
        return playerRepository
                .getPlayers()
                .get(nickName);
    }

    public Map<String, Integer> setAndGetPlayerStatistic(Player player, Status status){
        player.increaseNumberOfGames(status);
        return player.getPlayerStatistic();
    }

    private boolean nickNameIsBusy(String name) {
        return playerRepository
                .getPlayers()
                .containsKey(name);
    }
}
