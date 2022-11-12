import lombok.Getter;

public class Card {
    @Getter
    private final Question question;
    @Getter
    private final Answer[] answers;

    public Card(Question question, Answer[] answers) {
        this.question = question;
        this.answers = answers;
    }
}
