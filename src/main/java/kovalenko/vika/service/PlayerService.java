package kovalenko.vika.service;

import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.dto.PlayerDTO;

public interface PlayerService {
    PlayerDTO register(String nickName);

    PlayerDTO updatePlayerStatistic(String player, Status status);
}
