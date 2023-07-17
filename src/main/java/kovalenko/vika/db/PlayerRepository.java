package kovalenko.vika.db;

import kovalenko.vika.basis.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
public class PlayerRepository {
    private final Map<String, Player> players;

    public PlayerRepository() {
        players = new HashMap<>();
    }

    public void registerNewPlayer(String nickName) {
        if (isNull(nickName)) {
            log.error("Unable to register user because nickName is null");
            throw new NullPointerException("nickName cannot be null!");
        }
        Player player = new Player(nickName);
        players.put(nickName, player);

        log.info(String.format("The user '%s' has been registered", nickName));
    }

    public Map<String, Player> getPlayers() {
        return new HashMap<>(players);
    }
}
