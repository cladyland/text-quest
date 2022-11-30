package kovalenko.vika.db;

import kovalenko.vika.basis.Player;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class PlayerRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PlayerRepository.class);

    @Getter
    private final Map<String, Player> players;

    public PlayerRepository(){
        players = new HashMap<>();
    }

    public void registerNewPlayer(String nickName) {
        if (isNull(nickName)) {
            LOG.error("Unable to register user because nickName is null");
            throw new NullPointerException("nickName cannot be null!");
        }
        Player player = new Player(nickName);
        players.put(nickName, player);

        LOG.info(String.format("The user '%s' has been registered", nickName));
    }
}
