package kovalenko.vika.db;

import kovalenko.vika.common.entities.Player;
import kovalenko.vika.common.constant.Status;

public interface PlayerRepository {
    Player registerNewPlayer(String nickName);

    Player increaseNumberOfGames(String nickname, Status status);

    Player getByNickname(String nickname);
}
