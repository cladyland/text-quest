package kovalenko.vika.service;

import kovalenko.vika.db.PlayerRepository;
import kovalenko.vika.basis.Player;
import kovalenko.vika.basis.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    private final String defaultPlayerName = "Default";
    private final String testName = "Test";
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private Player player;
    private Map<String, Player> players;
    private PlayerService playerService;

    @BeforeEach
    void init() {
        players = new HashMap<>();
        playerService = new PlayerService(playerRepository);
    }

    @Test
    void register_return_defaultPlayer_if_name_isNull() {
        String actual = playerService.register(null).getNickName();
        assertEquals(defaultPlayerName, actual);
    }

    @Test
    void register_return_defaultPlayer_if_name_isBusy() {
        players.put(testName, player);
        when(playerRepository.getPlayers()).thenReturn(players);
        String actual = playerService.register(testName).getNickName();

        assertEquals(defaultPlayerName, actual);
    }

    @Test
    void register_added_and_return_newPlayer2() {
        players = Mockito.mock(Map.class);
        when(playerRepository.getPlayers()).thenReturn(players);
        when(playerRepository.getPlayers().containsKey(testName)).thenReturn(false);
        when(playerRepository.getPlayers().get(testName)).thenReturn(new Player(testName));
        String actual = playerService.register(testName).getNickName();

        verify(playerRepository, times(1)).registerNewPlayer(testName);
        assertEquals(testName, actual);
    }

    @Test
    void setAndGetPlayerStatistic_return_playerStatistic() {
        Map<String, Integer> statistic = new HashMap<>();
        statistic.put("Games", 5);
        statistic.put("Wins", 3);
        statistic.put("Defeats", 2);

        when(player.getPlayerStatistic()).thenReturn(statistic);

        assertEquals(statistic, playerService.setAndGetPlayerStatistic(player, Status.VICTORY));
        verify(player).increaseNumberOfGames(Status.VICTORY);
    }

    @Test
    void setAndGetPlayerStatistic_ThrowException_when_first_arg_isNull() {
        assertThrows(NullPointerException.class,
                () -> playerService.setAndGetPlayerStatistic(null, Status.VICTORY));
    }

    @Test
    void setAndGetPlayerStatistic_ThrowException_when_second_arg_isNull() {
        assertThrows(NullPointerException.class,
                () -> playerService.setAndGetPlayerStatistic(new Player(testName), null));
    }

    @Test
    void setAndGetPlayerStatistic_ThrowException_when_both_args_Null() {
        assertThrows(NullPointerException.class,
                () -> playerService.setAndGetPlayerStatistic(null, null));
    }
}