package kovalenko.vika.common.entities.sentence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public abstract class Sentence {
    @Getter
    private Integer id;
    @Getter
    private String context;

    public Sentence(@NonNull Integer id, @NonNull String context) {
        if (id < 0) {
            log.error("Cannot instantiate the class {}: id less than 0", this.getClass().getSimpleName());
            throw new IllegalArgumentException("ID must be greater than zero!");
        }
        if (context.isBlank()) {
            log.error("Cannot instantiate the class {}: context is blank", this.getClass().getSimpleName());
            throw new IllegalArgumentException("Context cannot be blank!");
        }
        this.id = id;
        this.context = context;

        log.debug("{} created: id='{}' context='{}'", this.getClass().getSimpleName(), this.id, this.context);
    }
}
