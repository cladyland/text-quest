package kovalenko.vika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerRepositoryTest {
    private PlayerRepository playerRepository;

    @BeforeEach
    void init(){
        playerRepository = new PlayerRepository();
    }

    @Test
    void registerNewPlayer_throwException_if_nickName_isNull(){
        assertThrows(NullPointerException.class,
                () -> playerRepository.registerNewPlayer(null));
    }

    @Test
    void registerNewPlayer_nickName_nonNull(){
        String expected = "Test";
        playerRepository.registerNewPlayer(expected);

        String actual = playerRepository.getPlayers().get(expected).getNickName();

        assertEquals(expected, actual);
    }
}