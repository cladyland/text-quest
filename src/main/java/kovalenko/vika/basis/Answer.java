package kovalenko.vika.basis;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.isNull;

@NoArgsConstructor
public class Answer extends Sentence {
    @Getter
    private Status status;

    public Answer(Integer id, String context, Status status) {
        super(id, context);
        if (isNull(status)){
            throw new NullPointerException("Answer status cannot be null!");
        }
        this.status = status;
    }
}
