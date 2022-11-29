package kovalenko.vika.service;

import kovalenko.vika.basis.Answer;
import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.Defeat;
import kovalenko.vika.basis.Status;
import kovalenko.vika.service.exception.QuestDefaultException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@AllArgsConstructor
public class QuestService {
    private static final Logger LOG = LoggerFactory.getLogger(QuestService.class);

    private final Answer defaultAnswer = new Answer(0, "Default", Status.DEFAULT);
    private final Defeat defaultDefeat = new Defeat(0, "Defeat");
    @NonNull
    private List<Card> cardList;
    @NonNull
    private List<Defeat> defeatList;
    @NonNull
    private String victoryMessage;

    public Status getPlayerAnswerStatus(Integer cardId, Integer answerId) {
        String failGetStatus = "Failed to get player answer status: ";
        if (isNull(cardId)) {
            LOG.error(failGetStatus + "cardId is null");
            throw new NullPointerException("cardId cannot be null!");
        }
        if (isNull(answerId)) {
            LOG.error(failGetStatus + "answerId is null");
            throw new NullPointerException("answerId cannot be null!");
        }

        Card playerCard = getCard(cardId);
        Answer playerAnswer = getAnswer(playerCard, answerId);
        Status status = playerAnswer.getStatus();

        if (status == Status.DEFAULT) {
            String answerNotFound = String
                    .format("Answer with id %d on the card with id %d is not found.", answerId, cardId);

            LOG.error(answerNotFound);
            throw new QuestDefaultException(answerNotFound);
        }
        return status;
    }

    public Card getCurrentCard(Integer cardId) {
        return getCard(cardId);
    }

    public Card getNextCard(Integer cardId) {
        int nextCardId = cardId + 1;
        return getCard(nextCardId);
    }

    public String getDefeatMessage(Integer answerId) {
        return defeatMessage(answerId);
    }

    public String getVictoryMessage() {
        return victoryMessage;
    }

    private Card getCard(int cardId) {
        return cardList
                .stream()
                .filter(card -> card.getId() == cardId)
                .findAny()
                .orElse(cardList.get(0));
    }

    private Answer getAnswer(Card card, Integer answerId) {
        return card
                .getAnswers()
                .stream()
                .filter(answer -> Objects.equals(answer.getId(), answerId))
                .findAny()
                .orElse(defaultAnswer);
    }

    private String defeatMessage(int answerId) {
        return defeatList
                .stream()
                .filter(defeat -> Objects.equals(defeat.getId(), answerId))
                .findAny()
                .orElse(defaultDefeat)
                .getContext();
    }

}
