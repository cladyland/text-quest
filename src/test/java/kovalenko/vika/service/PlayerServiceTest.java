package kovalenko.vika.service;

import kovalenko.vika.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;
    private String defaultPlayerName;

    private PlayerService playerService;

    @BeforeEach
    void init(){
        playerRepository = Mockito.mock(PlayerRepository.class);
        playerService = new PlayerService(playerRepository);
        defaultPlayerName = "Default";
        when(playerRepository.getPlayers().containsKey("Test")).thenReturn(true);
    }

    @Test
    void register_return_defaultPlayer_if_name_isNull(){
        String actual = playerService.register(null).getNickName();
        assertEquals(defaultPlayerName, actual);
    }

}