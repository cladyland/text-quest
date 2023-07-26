package kovalenko.vika.common.entities.sentence;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class Question extends Sentence {
    public Question(@NonNull Integer id, @NonNull String context) {
        super(id, context);
    }
}
