package kovalenko.vika.common.mapper;

import kovalenko.vika.common.entities.Player;
import kovalenko.vika.common.dto.PlayerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper extends BasicMapper<Player, PlayerDTO> {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);
}
