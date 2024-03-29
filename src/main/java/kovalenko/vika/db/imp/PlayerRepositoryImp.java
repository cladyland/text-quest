package kovalenko.vika.db.imp;

import kovalenko.vika.common.entities.Player;
import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.exception.RegisterException;
import kovalenko.vika.db.PlayerRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
public class PlayerRepositoryImp implements PlayerRepository {
    private final Map<String, Player> players;

    public PlayerRepositoryImp() {
        players = new HashMap<>();
    }

    @Override
    public Player registerNewPlayer(String nickName) {
        checkIfNickNameNotNull(nickName);

        var player = new Player(nickName);

        Optional.ofNullable(players.get(nickName))
                .ifPresentOrElse(user -> {
                    throw new RegisterException("nickname is busy");
                }, () -> players.put(nickName, player));

        log.info("The user '{}' has been registered", nickName);
        return player;
    }

    @Override
    public Player increaseNumberOfGames(String nickname, Status status) {
        Player player = getByNickname(nickname);
        player.increaseNumberOfGames(status);
        return player;
    }

    @Override
    public Player getByNickname(String nickname) {
        return players.get(nickname);
    }

    private void checkIfNickNameNotNull(String nickName) {
        if (isNull(nickName)) {
            log.error("Unable to register user because nickName is null");
            throw new NullPointerException("nickName cannot be null!");
        }
    }
}
