package kovalenko.vika.basis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Objects.isNull;

@NoArgsConstructor
public class Answer extends Sentence {
    private static final Logger LOG = LoggerFactory.getLogger(Answer.class);

    @Getter
    private Status status;

    public Answer(Integer id, String context, Status status) {
        super(id, context);
        if (isNull(status)) {
            LOG.error("Cannot instantiate the class: answer status is null.");
            throw new NullPointerException("Answer status cannot be null!");
        }
        this.status = status;
        LOG.debug("Answer with id='{}' has status='{}'", this.getId(), this.status);
    }
}
