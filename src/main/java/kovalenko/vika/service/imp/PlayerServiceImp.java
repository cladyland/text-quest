package kovalenko.vika.service.imp;

import kovalenko.vika.db.PlayerRepository;
import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.dto.PlayerDTO;
import kovalenko.vika.common.mapper.PlayerMapper;
import kovalenko.vika.service.PlayerService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;

@Slf4j
public class PlayerServiceImp implements PlayerService {
    private final PlayerMapper playerMapper = PlayerMapper.INSTANCE;
    @NonNull
    private final PlayerRepository playerRepository;

    public PlayerServiceImp(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerDTO register(String nickName) {
        if (nickNameIsBusy(nickName) || isNull(nickName)) {
            log.debug("The user '{}' is not registered: nickname is already taken or equals null", nickName);
            return null;
        }

        return playerMapper.toDTO(playerRepository.registerNewPlayer(nickName));
    }

    @Override
    public PlayerDTO updatePlayerStatistic(String player, Status status) {
        String failStatsChange = "Failed to change player's statistic: ";

        if (isNull(player)) {
            log.error(failStatsChange + "player is null");
            throw new NullPointerException("Player cannot be null!");
        }
        if (isNull(status)) {
            log.error(failStatsChange + "status is null");
            throw new NullPointerException("Status cannot be null!");
        }

        return playerMapper.toDTO(playerRepository.increaseNumberOfGames(player, status));
    }

    private boolean nickNameIsBusy(String name) {
        return playerRepository.getByNickname(name) != null;
    }
}
