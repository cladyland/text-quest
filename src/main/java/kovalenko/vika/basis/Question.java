package kovalenko.vika.basis;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Question extends Sentence {

    public Question(Integer id, String context) {
        super(id, context);
    }
}
