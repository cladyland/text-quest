package kovalenko.vika.service.imp;

import kovalenko.vika.common.entities.Player;
import kovalenko.vika.common.exception.RegisterException;
import kovalenko.vika.db.PlayerRepository;
import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.dto.PlayerDTO;
import kovalenko.vika.common.mapper.PlayerMapper;
import kovalenko.vika.service.PlayerService;
import kovalenko.vika.util.AppUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@AllArgsConstructor
public class PlayerServiceImp implements PlayerService {
    private final PlayerMapper playerMapper = PlayerMapper.INSTANCE;
    @NonNull
    private final PlayerRepository playerRepository;

    @Override
    public PlayerDTO register(String nickName) {
        if (isNull(nickName) || nickNameIsBusy(nickName)) {
            log.debug("The user '{}' is not registered: nickname is already taken or equals null", nickName);
            throw new RegisterException("Sorry, this name is already taken");
        }

        Player newPlayer = playerRepository.registerNewPlayer(nickName);
        return playerMapper.toDTO(newPlayer);
    }

    @Override
    public PlayerDTO updatePlayerStatistic(String player, Status status) {
        String failStatsChange = "Failed to change player's statistic because is null: ";

        AppUtil.ifNullAddLogAndThrowException(player, "Player", log, failStatsChange + "player");
        AppUtil.ifNullAddLogAndThrowException(status, "Status", log, failStatsChange + "status");

        Player updated = playerRepository.increaseNumberOfGames(player, status);
        return playerMapper.toDTO(updated);
    }

    private boolean nickNameIsBusy(String name) {
        return nonNull(playerRepository.getByNickname(name));
    }
}
