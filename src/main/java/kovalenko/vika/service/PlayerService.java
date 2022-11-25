package kovalenko.vika.service;

import kovalenko.vika.PlayerRepository;
import kovalenko.vika.basis.Player;

import static java.util.Objects.isNull;

public class PlayerService {
    private final Player defaultPlayer;
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

    private boolean nickNameIsBusy(String name) {
        return playerRepository
                .getPlayers()
                .containsKey(name);
    }
}
