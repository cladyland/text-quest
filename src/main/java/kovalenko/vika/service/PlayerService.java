package kovalenko.vika.service;

import kovalenko.vika.basis.Status;
import kovalenko.vika.dto.PlayerDTO;

public interface PlayerService {
    PlayerDTO register(String nickName);

    PlayerDTO updatePlayerStatistic(String player, Status status);
}
