package kovalenko.vika.common.mapper;

import kovalenko.vika.common.entities.Card;
import kovalenko.vika.common.dto.CardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {QuestionMapper.class, AnswerMapper.class})
public interface CardMapper extends BasicMapper<Card, CardDTO> {
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);
}
