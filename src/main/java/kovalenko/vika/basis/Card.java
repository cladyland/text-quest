package kovalenko.vika.basis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @NonNull
    @Getter
    private Integer id;
    @NonNull
    @Getter
    private Question question;
    @NonNull
    @Getter
    private List<Answer> answers;
}
