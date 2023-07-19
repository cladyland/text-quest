package kovalenko.vika.db;

import kovalenko.vika.basis.Player;
import kovalenko.vika.basis.Status;

public interface PlayerRepository {
    Player registerNewPlayer(String nickName);

    Player increaseNumberOfGames(String nickname, Status status);

    Player getByNickname(String nickname);
}
