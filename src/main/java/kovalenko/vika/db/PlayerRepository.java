package kovalenko.vika.db;

import kovalenko.vika.basis.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class PlayerRepository {
    @Getter
    private final Map<String, Player> players;

    public PlayerRepository(){
        players = new HashMap<>();
    }

    public void registerNewPlayer(String nickName) {
        if (isNull(nickName)) {
            throw new NullPointerException("nickName cannot be null!");
        }
        Player player = new Player(nickName);
        players.put(nickName, player);
    }
}
