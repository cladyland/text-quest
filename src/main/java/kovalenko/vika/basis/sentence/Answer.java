package kovalenko.vika.basis.sentence;

import kovalenko.vika.basis.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
@NoArgsConstructor
public class Answer extends Sentence {
    @Getter
    private Status status;

    public Answer(Integer id, String context, Status status) {
        super(id, context);
        if (isNull(status)) {
            log.error("Cannot instantiate the class: answer status is null.");
            throw new NullPointerException("Answer status cannot be null!");
        }
        this.status = status;
        log.debug("Answer with id='{}' has status='{}'", this.getId(), this.status);
    }
}
