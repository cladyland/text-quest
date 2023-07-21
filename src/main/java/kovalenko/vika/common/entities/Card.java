package kovalenko.vika.common.entities;

import kovalenko.vika.common.entities.sentence.Answer;
import kovalenko.vika.common.entities.sentence.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Integer id;
    private Question question;
    private List<Answer> answers;
}
