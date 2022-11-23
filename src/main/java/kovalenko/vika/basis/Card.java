package kovalenko.vika.basis;

import lombok.Getter;

import java.util.List;

public class Card {
    @Getter
    private Integer id;
    @Getter
    private Question question;
    @Getter
    private List<Answer> answers;

    public Card() {
    }

    public Card(Integer id, Question question, List<Answer> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }
}
