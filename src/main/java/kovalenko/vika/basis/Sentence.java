package kovalenko.vika.basis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public abstract class Sentence {
    @Getter
    private Integer id;
    @Getter
    private String context;
}
