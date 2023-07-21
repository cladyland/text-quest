package kovalenko.vika.db.imp;

import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.entities.Player;
import kovalenko.vika.common.exception.RegisterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerRepositoryImpTest {
    final String NICK_NAME = "test";
    PlayerRepositoryImp playerRepository;

    @BeforeEach
    void init() {
        playerRepository = new PlayerRepositoryImp();
    }

    @Test
    void register_throw_exception_if_nickName_null() {
        assertThrows(NullPointerException.class, () -> playerRepository.registerNewPlayer(null));
    }

    @Test
    void register_new_player() {
        Player player = registerTestPlayer();

        assertEquals(NICK_NAME, player.getNickName());
        assertEquals(new HashMap<>(), player.getPlayerStatistic());
    }

    @Test
    void register_throw_exception_if_nickname_busy() {
        registerTestPlayer();

        assertThrows(RegisterException.class, this::registerTestPlayer);
    }

    @Test
    void change_player_statistic() {
        var emptyMap = new HashMap<>();

        Player player = registerTestPlayer();
        assertEquals(emptyMap, player.getPlayerStatistic());

        playerRepository.increaseNumberOfGames(NICK_NAME, Status.VICTORY);
        assertNotEquals(emptyMap, player.getPlayerStatistic());
    }

    @Test
    void get_by_nickname() {
        registerTestPlayer();

        assertNotNull(playerRepository.getByNickname(NICK_NAME));
        assertNull(playerRepository.getByNickname("not exist"));
    }

    private Player registerTestPlayer() {
        return playerRepository.registerNewPlayer(NICK_NAME);
    }
}
