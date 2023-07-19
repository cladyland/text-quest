package kovalenko.vika.service.imp;

import kovalenko.vika.basis.sentence.Answer;
import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.sentence.Defeat;
import kovalenko.vika.basis.Status;
import kovalenko.vika.dto.CardDTO;
import kovalenko.vika.exception.QuestDefaultException;
import kovalenko.vika.mapper.CardMapper;
import kovalenko.vika.service.QuestService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;

@Slf4j
@AllArgsConstructor
public class QuestServiceImp implements QuestService {
    private final Answer defaultAnswer = new Answer(0, "Default", Status.DEFAULT);
    private final Defeat defaultDefeat = new Defeat(0, "Defeat");
    private final CardMapper cardMapper = CardMapper.INSTANCE;
    @NonNull
    private List<Card> cardList;
    @NonNull
    private List<Defeat> defeatList;
    @NonNull
    private String victoryMessage;

    @Override
    public Status getPlayerAnswerStatus(Integer cardId, Integer answerId) {
        String failGetStatus = "Failed to get player answer status: ";
        if (isNull(cardId)) {
            log.error(failGetStatus + "cardId is null");
            throw new NullPointerException("cardId cannot be null!");
        }
        if (isNull(answerId)) {
            log.error(failGetStatus + "answerId is null");
            throw new NullPointerException("answerId cannot be null!");
        }

        Card playerCard = getCard(cardId);
        Answer playerAnswer = getAnswer(playerCard, answerId);
        Status status = playerAnswer.getStatus();

        if (status == Status.DEFAULT) {
            String answerNotFound = String
                    .format("Answer with id %d on the card with id %d is not found.", answerId, cardId);

            log.error(answerNotFound);
            throw new QuestDefaultException(answerNotFound);
        }
        return status;
    }

    @Override
    public CardDTO getCurrentCard(Integer cardId) {
        return cardMapper.toDTO(getCard(cardId));
    }

    @Override
    public CardDTO getNextCard(Integer cardId) {
        int nextCardId = cardId + 1;
        return cardMapper.toDTO(getCard(nextCardId));
    }

    @Override
    public String getDefeatMessage(Integer answerId) {
        return defeatMessage(answerId);
    }

    @Override
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
