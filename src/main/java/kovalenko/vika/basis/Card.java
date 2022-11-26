package kovalenko.vika.basis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Getter
    private Integer id;
    @Getter
    private Question question;
    @Getter
    private List<Answer> answers;
}
