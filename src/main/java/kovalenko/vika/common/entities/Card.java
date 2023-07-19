package kovalenko.vika.common.entities;

import kovalenko.vika.common.entities.sentence.Answer;
import kovalenko.vika.common.entities.sentence.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@NoArgsConstructor
public class Card {
    @Getter
    private Integer id;
    @Getter
    private Question question;
    @Getter
    private List<Answer> answers;

    public Card(@NonNull Integer id, @NonNull Question question, @NonNull List<Answer> answers) {
        if (id <= 0) {
            log.error("Cannot instantiate the class: id less or equals 0");
            throw new IllegalArgumentException("ID must be greater than zero!");
        }
        this.id = id;
        this.question = question;
        this.answers = answers;

        log.debug("Card created: id='{}' questionText='{}' numberOfAnswers='{}'",
                this.id, this.question.getContext(), answers.size());
    }
}
