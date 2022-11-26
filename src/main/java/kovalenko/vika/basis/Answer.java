package kovalenko.vika.basis;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Answer extends Sentence {
    @Getter
    private Status status;

    public Answer(Integer id, String context, Status status) {
        super(id, context);
        this.status = status;
    }
}
