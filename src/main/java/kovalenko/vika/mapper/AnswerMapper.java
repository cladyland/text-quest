package kovalenko.vika.mapper;

import kovalenko.vika.basis.sentence.Answer;
import kovalenko.vika.dto.AnswerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnswerMapper extends BasicMapper<Answer, AnswerDTO> {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);
}
