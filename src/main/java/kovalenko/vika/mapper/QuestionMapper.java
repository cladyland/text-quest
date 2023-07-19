package kovalenko.vika.mapper;

import kovalenko.vika.basis.sentence.Question;
import kovalenko.vika.dto.QuestionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper extends BasicMapper<Question, QuestionDTO> {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);
}
