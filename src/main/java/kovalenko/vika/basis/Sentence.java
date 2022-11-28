package kovalenko.vika.basis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
public abstract class Sentence {
    private static final Logger LOG = LoggerFactory.getLogger(Sentence.class);
    @Getter
    private Integer id;
    @Getter
    private String context;

    public Sentence(@NonNull Integer id, @NonNull String context) {
        if (id <= 0) {
            LOG.error("Cannot instantiate the class {}: id less or equals 0", this.getClass().getSimpleName());
            throw new IllegalArgumentException("ID must be greater than zero!");
        }
        if (context.isBlank()) {
            LOG.error("Cannot instantiate the class {}: context is blank", this.getClass().getSimpleName());
            throw new IllegalArgumentException("Context cannot be blank!");
        }
        this.id = id;
        this.context = context;

        LOG.debug("{} created: id='{}' context='{}'", this.getClass().getSimpleName(), this.id, this.context);
    }
}
