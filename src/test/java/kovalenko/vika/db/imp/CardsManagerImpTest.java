package kovalenko.vika.db.imp;

import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.entities.Card;
import kovalenko.vika.common.entities.sentence.Defeat;
import kovalenko.vika.common.exception.CardsManagerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kovalenko.vika.TestConstant.CARDS_YML;
import static kovalenko.vika.TestConstant.DEFEATS_YML;
import static kovalenko.vika.common.constant.AttributeConstant.CARDS_PARAM;
import static kovalenko.vika.common.constant.AttributeConstant.DEFEATS_PARAM;
import static kovalenko.vika.common.constant.AttributeConstant.VICTORY_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardsManagerImpTest {
    static CardsManagerImp cardsManager;

    @BeforeAll
    static void init() {
        cardsManager = new CardsManagerImp();
    }

    @Test
    void create_resource_cards_and_get_unmodifiable_list() {
        int expSize = 3;
        int winCardIndex = 2;
        int winAnswerIndex = 0;

        cardsManager.createResource(CARDS_YML, CARDS_PARAM);

        List<Card> actual = cardsManager.getCards();
        Card winCard = actual.get(winCardIndex);

        assertEquals(expSize, actual.size());
        assertEquals(Status.VICTORY, winCard.getAnswers().get(winAnswerIndex).getStatus());

        assertThrows(UnsupportedOperationException.class, () -> actual.add(new Card()));
    }

    @Test
    void create_resource_defeats_and_get_unmodifiable_list() {
        int expSize = 3;
        int defeatIndex = 1;
        int defeatId = 22;

        cardsManager.createResource(DEFEATS_YML, DEFEATS_PARAM);

        List<Defeat> actual = cardsManager.getDefeats();

        assertEquals(expSize, actual.size());
        assertEquals(defeatId, actual.get(defeatIndex).getId());

        assertThrows(UnsupportedOperationException.class, () -> actual.add(new Defeat()));
    }

    @Test
    void throw_exception_when_create_from_not_exist_file() {
        assertThrows(IllegalArgumentException.class, () ->
                cardsManager.createResource("not-exist.yml", CARDS_PARAM));
    }

    @Test
    void throw_exception_when_create_with_not_exist_param() {
        assertThrows(CardsManagerException.class, () ->
                cardsManager.createResource(DEFEATS_YML, "not exist"));
    }

    @Test
    void throw_exception_when_reading_fail() {
        assertThrows(CardsManagerException.class, () -> cardsManager.createResource(CARDS_YML, DEFEATS_PARAM));
    }

    @Test
    void get_victory_message() {
        assertEquals(VICTORY_MESSAGE, cardsManager.getVictory());
    }
}
