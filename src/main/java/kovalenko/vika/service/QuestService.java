package kovalenko.vika.service;

import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.dto.CardDTO;

public interface QuestService {
    Status getPlayerAnswerStatus(Integer cardId, Integer answerId);

    CardDTO getCurrentCard(Integer cardId);

    CardDTO getNextCard(Integer cardId);

    String getDefeatMessage(Integer answerId);

    String getVictoryMessage();
}
