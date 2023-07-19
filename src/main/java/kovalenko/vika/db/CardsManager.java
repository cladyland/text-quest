package kovalenko.vika.db;

import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.sentence.Defeat;

import java.util.List;

public interface CardsManager {
    void createCards(String fileName);

    void createDefeats(String fileName);

    List<Card> getCards();

    List<Defeat> getDefeats();

    String getVictory();
}
