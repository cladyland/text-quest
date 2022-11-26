package kovalenko.vika.service;

import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.Defeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class QuestServiceTest {
    @Mock
    private List<Card> cardList;

    @Mock
    private List<Defeat> defeatList;

    private String victoryMessage;

    private QuestService questService;

    @BeforeEach
    void init(){
        cardList = Mockito.mock(List.class);
        defeatList = Mockito.mock(List.class);
        victoryMessage = "You win";
        questService = new QuestService(cardList, defeatList, victoryMessage);
    }

    @Test
    void getPlayerAnswerStatus_ThrowException_when_first_arg_isNull() {
        assertThrows(NullPointerException.class,
                () -> questService.getPlayerAnswerStatus(null, 1));
    }

    @Test
    void getPlayerAnswerStatus_ThrowException_when_second_arg_isNull() {
        assertThrows(NullPointerException.class,
                () -> questService.getPlayerAnswerStatus(1, null));
    }

    @Test
    void getPlayerAnswerStatus_ThrowException_when_both_args_Null() {
        assertThrows(NullPointerException.class,
                () -> questService.getPlayerAnswerStatus(null, null));
    }

    @Test
    void getPlayerAnswerStatus_ThrowException_when_status_Default(){

    }
}