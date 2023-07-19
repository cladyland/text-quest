package kovalenko.vika.common.mapper;

import kovalenko.vika.common.entities.sentence.Question;
import kovalenko.vika.common.dto.QuestionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper extends BasicMapper<Question, QuestionDTO> {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);
}
