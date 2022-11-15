package kovalenko.vika.basis;

import lombok.Getter;

public class Card {
    @Getter
    private Question question;
    @Getter
    private Answer[] answers;

    public Card() {
    }

    public Card(Question question, Answer[] answers) {
        this.question = question;
        this.answers = answers;
    }
}
