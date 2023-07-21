package kovalenko.vika.common.entities.sentence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public abstract class Sentence {
    private final String CLASS_NAME = this.getClass().getSimpleName();
    @Getter
    private Integer id;
    @Getter
    private String context;

    public Sentence(@NonNull Integer id, @NonNull String context) {
        if (id < 0) {
            addFailLogAndThrowArgumentEx("id must be greater than zero");
        }
        if (context.isBlank()) {
            addFailLogAndThrowArgumentEx("context cannot be blank");
        }

        this.id = id;
        this.context = context;

        log.debug("{} created: id='{}' context='{}'", CLASS_NAME, this.id, this.context);
    }

    private void addFailLogAndThrowArgumentEx(String message) {
        log.error("Cannot instantiate the class {}: {}", CLASS_NAME, message);
        throw new IllegalArgumentException(message);
    }
}
