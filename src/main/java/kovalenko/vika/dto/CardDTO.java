package kovalenko.vika.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CardDTO {
    private Integer id;
    private QuestionDTO question;
    private List<AnswerDTO> answers;
}
