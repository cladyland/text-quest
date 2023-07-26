package kovalenko.vika.common.entities;

import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.exception.PlayerSettingsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static kovalenko.vika.TestConstant.EMPTY_MAP;
import static kovalenko.vika.common.constant.Status.DEFEAT;
import static kovalenko.vika.common.constant.Status.VICTORY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    final String NICKNAME = "default";
    Player player;

    @BeforeEach
    void init() {
        player = new Player(NICKNAME);
    }

    @Test
    void nickname_equals_input_param() {
        String nickName2 = "test";
        var player2 = new Player(nickName2);

        assertEquals(NICKNAME, player.getNickName());
        assertEquals(nickName2, player2.getNickName());
    }

    @Test
    void empty_statistic_after_player_created() {
        assertNotNull(getStatistic());
        assertEquals(EMPTY_MAP, getStatistic());
    }

    @Test
    void change_statistic_status_victory() {
        Map<String, Integer> expected = expectedStatistic(2, 2, 0);

        player.increaseNumberOfGames(VICTORY);
        player.increaseNumberOfGames(VICTORY);

        assertEquals(expected, getStatistic());
    }

    @Test
    void change_statistic_status_defeat() {
        Map<String, Integer> expected = expectedStatistic(1, 0, 1);

        player.increaseNumberOfGames(DEFEAT);

        assertEquals(expected, getStatistic());
    }

    @Test
    void not_change_statistic_when_inappropriate_status() {
        var exceptionClass = PlayerSettingsException.class;

        assertThrows(exceptionClass, () -> player.increaseNumberOfGames(Status.NEXT));
        assertThrows(exceptionClass, () -> player.increaseNumberOfGames(null));

        assertEquals(EMPTY_MAP, getStatistic());
    }

    @Test
    void change_and_get_statistic() {
        Map<String, Integer> expected = expectedStatistic(3, 1, 2);

        player.increaseNumberOfGames(VICTORY);
        player.increaseNumberOfGames(DEFEAT);
        player.increaseNumberOfGames(DEFEAT);

        assertEquals(expected, getStatistic());
    }

    @Test
    void each_player_has_own_statistic() {
        var player2 = new Player("player2");
        Map<String, Integer> expected2 = expectedStatistic(2, 1, 1);

        player2.increaseNumberOfGames(VICTORY);
        player2.increaseNumberOfGames(DEFEAT);

        assertEquals(EMPTY_MAP, getStatistic());
        assertEquals(expected2, player2.getPlayerStatistic());
    }

    private Map<String, Integer> getStatistic() {
        return player.getPlayerStatistic();
    }

    private Map<String, Integer> expectedStatistic(int games, int wins, int defeats) {
        return new HashMap<>() {
            {
                put("Games", games);
                put("Wins", wins);
                put("Defeats", defeats);
            }
        };
    }
}
