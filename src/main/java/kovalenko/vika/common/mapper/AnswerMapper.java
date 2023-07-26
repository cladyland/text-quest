package kovalenko.vika.common.mapper;

import kovalenko.vika.common.entities.sentence.Answer;
import kovalenko.vika.common.dto.AnswerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnswerMapper extends BasicMapper<Answer, AnswerDTO> {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);
}
