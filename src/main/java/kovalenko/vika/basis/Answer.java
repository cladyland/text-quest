package kovalenko.vika.basis;

import lombok.Getter;

public class Answer extends Sentence {
    @Getter
    private Status status;

    public Answer() {
    }

    public Answer(int id, String context, Status status) {
        super(id, context);
        this.status = status;
    }
}
