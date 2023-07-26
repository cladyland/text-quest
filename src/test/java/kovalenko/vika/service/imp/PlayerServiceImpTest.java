package kovalenko.vika.service.imp;

import kovalenko.vika.common.dto.PlayerDTO;
import kovalenko.vika.common.entities.Player;
import kovalenko.vika.common.exception.RegisterException;
import kovalenko.vika.db.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static kovalenko.vika.TestConstant.EMPTY_MAP;
import static kovalenko.vika.common.constant.Status.VICTORY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImpTest {
    final String NICKNAME = "test nickname";
    @Mock
    PlayerRepository playerRepository;
    PlayerServiceImp service;

    @BeforeEach
    void init() {
        service = new PlayerServiceImp(playerRepository);
    }

    @Test
    void register_new_player() {
        when(playerRepository.getByNickname(NICKNAME)).thenReturn(null);
        when(playerRepository.registerNewPlayer(NICKNAME)).thenReturn(new Player(NICKNAME));

        PlayerDTO registered = service.register(NICKNAME);

        assertEquals(NICKNAME, registered.getNickName());
        assertEquals(EMPTY_MAP, registered.getPlayerStatistic());
    }

    @Test
    void register_throw_exception_when_nickname_is_null() {
        assertThrows(RegisterException.class, () -> service.register(null));

        verify(playerRepository, never()).getByNickname(anyString());
        verify(playerRepository, never()).registerNewPlayer(anyString());
    }

    @Test
    void register_throw_exception_when_nickname_is_busy() {
        when(playerRepository.getByNickname(anyString())).thenReturn(Mockito.mock(Player.class));

        assertThrows(RegisterException.class, () -> service.register(NICKNAME));
        verify(playerRepository, never()).registerNewPlayer(anyString());
    }

    @Test
    void update_player_statistic() {
        service.updatePlayerStatistic(NICKNAME, VICTORY);

        verify(playerRepository, times(1)).increaseNumberOfGames(NICKNAME, VICTORY);
    }

    @Test
    void update_statistic_throw_exception_when_player_is_null() {
        assertThrows(NullPointerException.class, () -> service.updatePlayerStatistic(null, VICTORY));

        verify(playerRepository, never()).increaseNumberOfGames(anyString(), any());
    }

    @Test
    void update_statistic_throw_exception_when_status_is_null() {
        assertThrows(NullPointerException.class, () -> service.updatePlayerStatistic(NICKNAME, null));

        verify(playerRepository, never()).increaseNumberOfGames(anyString(), any());
    }
}
