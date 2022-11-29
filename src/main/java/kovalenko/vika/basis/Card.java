package kovalenko.vika.basis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@NoArgsConstructor
public class Card {
    private static final Logger LOG = LoggerFactory.getLogger(Card.class);

    @Getter
    private Integer id;
    @Getter
    private Question question;
    @Getter
    private List<Answer> answers;

    public Card(@NonNull Integer id, @NonNull Question question, @NonNull List<Answer> answers) {
        if (id <= 0) {
            LOG.error("Cannot instantiate the class: id less or equals 0");
            throw new IllegalArgumentException("ID must be greater than zero!");
        }
        this.id = id;
        this.question = question;
        this.answers = answers;

        LOG.debug("Card created: id='{}' questionText='{}' numberOfAnswers='{}'",
                this.id, this.question.getContext(), answers.size());
    }
}
