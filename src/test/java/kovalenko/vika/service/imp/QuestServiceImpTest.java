package kovalenko.vika.service.imp;

import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.dto.CardDTO;
import kovalenko.vika.common.entities.Card;
import kovalenko.vika.common.entities.sentence.Defeat;
import kovalenko.vika.common.exception.QuestDefaultException;
import kovalenko.vika.db.CardsManager;
import kovalenko.vika.db.imp.CardsManagerImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static kovalenko.vika.TestConstant.CARDS_YML;
import static kovalenko.vika.TestConstant.DEFEATS_YML;
import static kovalenko.vika.common.constant.AttributeConstant.CARDS_PARAM;
import static kovalenko.vika.common.constant.AttributeConstant.DEFEATS_PARAM;
import static kovalenko.vika.common.constant.AttributeConstant.VICTORY_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestServiceImpTest {
    CardsManager cardsManager = Mockito.mock(CardsManager.class);
    QuestServiceImp service;
    List<Card> cards;
    Integer cardId;
    Integer answerId;

    {
        cards = new CardsManagerImp() {{
            createResource(CARDS_YML, CARDS_PARAM);
        }}.getCards();

        service = new QuestServiceImp(cardsManager);
    }

    @Test
    void get_player_answer_status() {
        Status expected = Status.NEXT;
        cardId = 2;
        answerId = 21;

        whenManagerGetCards();
        assertEquals(expected, getAnswerStatus());
    }

    @Test
    void get_status_throw_exception_when_cardId_is_null() {
        cardId = null;
        answerId = 1;

        assertThrows(NullPointerException.class, this::getAnswerStatus);
        verify(cardsManager, never()).getCards();
    }

    @Test
    void get_status_throw_exception_when_answerId_is_null() {
        cardId = 1;
        answerId = null;

        assertThrows(NullPointerException.class, this::getAnswerStatus);
        verify(cardsManager, never()).getCards();
    }

    @Test
    void get_status_throw_exception_when_answer_not_found() {
        cardId = 1;
        answerId = 3;

        whenManagerGetCards();
        assertThrows(QuestDefaultException.class, this::getAnswerStatus);
    }

    @Test
    void get_current_card() {
        int cardId = 3;
        whenManagerGetCards();

        CardDTO card = service.getCurrentCard(cardId);
        assertEquals(cardId, card.getId());
    }

    @Test
    void get_next_card() {
        cardId = 2;
        int expected = 3;

        whenManagerGetCards();

        CardDTO nextCard = service.getNextCard(cardId);
        assertEquals(expected, nextCard.getId());
    }

    @Test
    void get_defeat_message() {
        String expected = "defeat 2";
        answerId = 22;
        List<Defeat> defeats = new CardsManagerImp() {{
            createResource(DEFEATS_YML, DEFEATS_PARAM);
        }}.getDefeats();

        when(cardsManager.getDefeats()).thenReturn(defeats);

        String actual = service.getDefeatMessage(answerId);
        assertEquals(expected, actual);
    }

    @Test
    void get_victory_message() {
        when(cardsManager.getVictory()).thenReturn(VICTORY_MESSAGE);

        String actual = service.getVictoryMessage();
        assertEquals(VICTORY_MESSAGE, actual);
    }

    private void whenManagerGetCards() {
        when(cardsManager.getCards()).thenReturn(cards);
    }

    private Status getAnswerStatus() {
        return service.getPlayerAnswerStatus(cardId, answerId);
    }
}
