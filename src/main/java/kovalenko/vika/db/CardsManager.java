package kovalenko.vika.db;

import kovalenko.vika.common.entities.Card;
import kovalenko.vika.common.entities.sentence.Defeat;

import java.util.List;

public interface CardsManager {
    void createResource(String fileName, String param);

    List<Card> getCards();

    List<Defeat> getDefeats();

    String getVictory();
}
