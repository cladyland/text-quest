package kovalenko.vika;

import kovalenko.vika.basis.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class PlayerRepository {
    @Getter
    private Map<String, Player> players = new HashMap<>();

    public void registerNewPlayer(String nickName){
        if (nonNull(nickName)) {
            Player player = new Player(nickName);
            players.put(nickName, player);
        }
    }
}
