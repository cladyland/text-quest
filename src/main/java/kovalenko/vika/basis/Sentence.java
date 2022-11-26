package kovalenko.vika.basis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
public abstract class Sentence {
    @NonNull
    @Getter
    private Integer id;
    @NonNull
    @Getter
    private String context;
}
