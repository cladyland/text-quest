package kovalenko.vika.service;

import kovalenko.vika.basis.Answer;
import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.Defeat;
import kovalenko.vika.basis.Status;
import kovalenko.vika.service.exception.QuestDefaultException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class QuestService {
    private List<Card> cardList;
    private List<Defeat> defeatList;
    private String victoryMessage;

    public Status getPlayerAnswerStatus(Integer cardId, Integer answerId){
        Card playerCard = getCard(cardId);
        Answer playerAnswer = getAnswer(playerCard, answerId);
        Status status = playerAnswer.getStatus();

        if (status == Status.DEFAULT){
            String answerNotFound = "Answer with id %d on the card with id %d is not found.";

            throw new QuestDefaultException(String.format(answerNotFound, answerId, cardId));
        }
        return status;
    }

    public Card getCurrentCard(Integer cardId){
        return getCard(cardId);
    }

    public Card getNextCard(Integer cardId){
        int nextCardId = cardId + 1;
        return getCard(nextCardId);
    }

    public String getDefeatMessage(Integer answerId){
        return defeatMessage(answerId);
    }

    public String getVictoryMessage(){
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
        var defaultAnswer = new Answer(0, "Default", Status.DEFAULT);
        return card
                .getAnswers()
                .stream()
                .filter(answer -> Objects.equals(answer.getId(), answerId))
                .findAny()
                .orElse(defaultAnswer);
    }

    private String defeatMessage(int answerId) {
        var defaultDefeat = new Defeat(0, "Defeat");
        return defeatList
                .stream()
                .filter(defeat -> Objects.equals(defeat.getId(), answerId))
                .findAny()
                .orElse(defaultDefeat)
                .getContext();
    }

}
