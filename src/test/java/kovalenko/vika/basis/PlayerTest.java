package kovalenko.vika.basis;

import kovalenko.vika.service.exception.PlayerSettingsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PlayerTest {
    private Player player;
    private int expectedGames;
    private int expectedWins;
    private int expectedDefeats;

    @BeforeEach
    void init(){
        String nickName = "Default";
        player = new Player(nickName);
    }

    @Test
    void test_increaseNumberOfGames_verify(){
        Status status = Status.DEFAULT;
        Player mockPlayer = Mockito.spy(player);
        mockPlayer.increaseNumberOfGames(status);

        verify(mockPlayer, times(1)).increaseNumberOfGames(status);
    }

    @Test
    void test_increaseNumberOfGames_status_Victory(){
        player.increaseNumberOfGames(Status.VICTORY);
        expectedGames = 1;
        expectedWins = 1;
        expectedDefeats = 0;

        assertEquals(expectedGames, numberOfGames());
        assertEquals(expectedWins, numberOfWins());
        assertEquals(expectedDefeats, numberOfDefeats());
    }

    @Test
    void test_increaseNumberOfGames_status_Defeat(){
        player.increaseNumberOfGames(Status.DEFEAT);
        expectedGames = 1;
        expectedWins = 0;
        expectedDefeats = 1;

        assertEquals(expectedGames, numberOfGames());
        assertEquals(expectedWins, numberOfWins());
        assertEquals(expectedDefeats, numberOfDefeats());
    }

    @Test
    void test_increaseNumberOfGames_inappropriate_status(){
        assertThrows(PlayerSettingsException.class,
                () -> player.increaseNumberOfGames(Status.NEXT));
    }

    private int numberOfGames(){
        return player.getNumberOfGames();
    }

    private int numberOfWins(){
        return player.getNumberOfWins();
    }

    private int numberOfDefeats(){
        return player.getNumberOfDefeats();
    }
}
