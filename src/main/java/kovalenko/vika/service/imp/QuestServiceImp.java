package kovalenko.vika.service.imp;

import kovalenko.vika.common.entities.sentence.Answer;
import kovalenko.vika.common.entities.Card;
import kovalenko.vika.common.entities.sentence.Defeat;
import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.dto.CardDTO;
import kovalenko.vika.common.exception.QuestDefaultException;
import kovalenko.vika.common.mapper.CardMapper;
import kovalenko.vika.db.CardsManager;
import kovalenko.vika.service.QuestService;
import kovalenko.vika.util.AppUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class QuestServiceImp implements QuestService {
    private final CardMapper cardMapper = CardMapper.INSTANCE;
    @NonNull
    private final CardsManager cardsManager;

    @Override
    public Status getPlayerAnswerStatus(Integer cardId, Integer answerId) {
        String failGetStatus = "Failed to get player answer status because is null: ";

        AppUtil.ifNullAddLogAndThrowException(cardId, "cardId", log, failGetStatus + "cardId");
        AppUtil.ifNullAddLogAndThrowException(answerId, "answerId", log, failGetStatus + "answerId");

        Card playerCard = getCard(cardId);
        Answer playerAnswer = getAnswer(playerCard, answerId);
        Status status = playerAnswer.getStatus();

        checkIfNotDefaultStatus(status, cardId, answerId);

        return status;
    }

    private void checkIfNotDefaultStatus(Status status, Integer cardId, Integer answerId) {
        if (status == Status.DEFAULT) {
            String answerNotFound = String
                    .format("Answer with id %d on the card with id %d is not found.", answerId, cardId);

            log.error(answerNotFound);
            throw new QuestDefaultException(answerNotFound);
        }
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
        return cardsManager.getVictory();
    }

    private Card getCard(int cardId) {
        return cardsManager.getCards()
                .stream()
                .filter(card -> card.getId() == cardId)
                .findAny()
                .orElse(cardsManager.getCards().get(0));
    }

    private Answer getAnswer(Card card, int answerId) {
        var defaultAnswer = new Answer(0, "Default", Status.DEFAULT);

        return card
                .getAnswers()
                .stream()
                .filter(answer -> answer.getId() == answerId)
                .findAny()
                .orElse(defaultAnswer);
    }

    private String defeatMessage(int answerId) {
        var defaultDefeat = new Defeat(0, "Defeat");

        return cardsManager.getDefeats()
                .stream()
                .filter(defeat -> defeat.getId() == answerId)
                .findAny()
                .orElse(defaultDefeat)
                .getContext();
    }
}
