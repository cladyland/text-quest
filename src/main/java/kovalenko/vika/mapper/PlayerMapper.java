package kovalenko.vika.mapper;

import kovalenko.vika.basis.Player;
import kovalenko.vika.dto.PlayerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlayerMapper extends BasicMapper<Player, PlayerDTO> {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);
}
