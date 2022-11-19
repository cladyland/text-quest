import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {
    @Getter
    private Map<String, Player> players = new HashMap<>();

    public void registerNewPlayer(String nickName){
        Player player = new Player(nickName);
        players.put(nickName, player);
    }
}
