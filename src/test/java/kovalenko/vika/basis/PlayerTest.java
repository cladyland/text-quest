package kovalenko.vika.basis;

import kovalenko.vika.exception.PlayerSettingsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

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
    void increaseNumberOfGames_verify(){
        Status status = Status.VICTORY;
        Player mockPlayer = Mockito.spy(player);
        mockPlayer.increaseNumberOfGames(status);

        verify(mockPlayer, times(1)).increaseNumberOfGames(status);
    }

    @Test
    void increaseNumberOfGames_status_Victory(){
        player.increaseNumberOfGames(Status.VICTORY);
        expectedGames = 1;
        expectedWins = 1;
        expectedDefeats = 0;

        assertEquals(expectedGames, numberOfGames());
        assertEquals(expectedWins, numberOfWins());
        assertEquals(expectedDefeats, numberOfDefeats());
    }

    @Test
    void increaseNumberOfGames_status_Defeat(){
        player.increaseNumberOfGames(Status.DEFEAT);
        expectedGames = 1;
        expectedWins = 0;
        expectedDefeats = 1;

        assertEquals(expectedGames, numberOfGames());
        assertEquals(expectedWins, numberOfWins());
        assertEquals(expectedDefeats, numberOfDefeats());
    }

    @Test
    void increaseNumberOfGames_inappropriate_status(){
        assertThrows(PlayerSettingsException.class,
                () -> player.increaseNumberOfGames(Status.NEXT));
    }

    @Test
    void increaseNumberOfGames_status_Null(){
        assertThrows(PlayerSettingsException.class,
                () -> player.increaseNumberOfGames(null));
    }

    @Test
    void getPlayerStatistic_change_statistic(){
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Games", 3);
        expected.put("Wins", 1);
        expected.put("Defeats", 2);

        player.increaseNumberOfGames(Status.VICTORY);
        player.increaseNumberOfGames(Status.DEFEAT);
        player.increaseNumberOfGames(Status.DEFEAT);

        assertEquals(expected, player.getPlayerStatistic());
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
