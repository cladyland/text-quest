package kovalenko.vika.service;

import kovalenko.vika.basis.Answer;
import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.Defeat;
import kovalenko.vika.basis.Question;
import kovalenko.vika.basis.Status;
import kovalenko.vika.service.exception.QuestDefaultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class QuestServiceTest {
    private final String victoryMessage = "You win";
    @Mock
    private Card card;
    @Mock
    private Answer answer;
    @Mock
    private Defeat defeat;
    private QuestService questService;
    private List<Card> cardList;
    private List<Defeat> defeatList;
    private List<Answer> answers;
    @BeforeEach
    void init() {
        cardList = new ArrayList<>();
        defeatList = new ArrayList<>();
        answers = new ArrayList<>();
        card = Mockito.mock(Card.class);
        answer = Mockito.mock(Answer.class);
        answers.add(answer);
        cardList.add(new Card(1, new Question(), answers));
        questService = new QuestService(cardList, defeatList, victoryMessage);

        when(card.getId()).thenReturn(1);
        when(answer.getId()).thenReturn(1);
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
    void getPlayerAnswerStatus_ThrowException_when_status_Default() {
        when(answer.getStatus()).thenReturn(Status.DEFAULT);
        assertThrows(QuestDefaultException.class,
                () -> questService.getPlayerAnswerStatus(1, 1));
    }

    @Test
    void getPlayerAnswerStatus_return_status() {
        when(answer.getStatus()).thenReturn(Status.NEXT);
        Status actual = questService.getPlayerAnswerStatus(1, 1);
        assertEquals(Status.NEXT, actual);
    }

    @Test
    void getCurrentCard_return_currentCard(){
        cardList.add(new Card(2, new Question(), answers));
        cardList.add(new Card(3, new Question(), answers));
        int expected = 2;
        int actual = questService.getCurrentCard(2).getId();
        assertEquals(expected, actual);
    }

    @Test
    void getNextCard_return_nextCard(){
        cardList.add(new Card(2, new Question(), answers));
        cardList.add(new Card(3, new Question(), answers));
        int expected = 3;
        int actual = questService.getNextCard(2).getId();
        assertEquals(expected, actual);
    }

    @Test
    void getDefeatMessage_return_correct_defeat_message(){
        defeat = Mockito.mock(Defeat.class);
        defeatList.add(defeat);
        String expected = "You lose";
        when(defeat.getId()).thenReturn(2);
        when(defeat.getContext()).thenReturn(expected);
        String actual = questService.getDefeatMessage(2);

        assertEquals(expected, actual);
    }

    @Test
    void getDefeatMessage_return_default_message(){
        String expected = "Defeat";
        String actual = questService.getDefeatMessage(0);
        assertEquals(expected, actual);
    }

    @Test
    void getVictoryMessage_return_correct_message(){
        String actual = questService.getVictoryMessage();
        assertEquals(victoryMessage, actual);
    }
}