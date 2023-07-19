package kovalenko.vika.mapper;

import kovalenko.vika.basis.Card;
import kovalenko.vika.dto.CardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {QuestionMapper.class, AnswerMapper.class})
public interface CardMapper extends BasicMapper<Card, CardDTO> {
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);
}
