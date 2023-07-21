package kovalenko.vika.common.entities.sentence;

import kovalenko.vika.common.constant.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Answer extends Sentence {
    @Getter
    private Status status;

    public Answer(Integer id, String context, @NonNull Status status) {
        super(id, context);
        this.status = status;

        log.debug("Answer with id='{}' and status='{}' created", this.getId(), this.status);
    }
}
