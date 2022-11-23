package kovalenko.vika.basis;

import lombok.Getter;

public abstract class Sentence {
    @Getter
    private Integer id;
    @Getter
    private String context;

    public Sentence() {
    }

    public Sentence(Integer id, String context) {
        this.id = id;
        this.context = context;
    }
}
