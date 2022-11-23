import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Player {
    @Getter
    private final String nickName;
    @Getter
    @Setter
    private boolean newcomer;
    @Getter
    @Setter
    private Integer numberOfGames;
    @Getter
    @Setter
    private Integer numberOfWins;
    @Getter
    @Setter
    private Integer numberOfDefeats;
    private Map<String, Integer> playerStatistic;

    public Player(String nickName) {
        this.nickName = nickName;
        newcomer = true;
        numberOfGames = 0;
        numberOfWins = 0;
        numberOfDefeats = 0;
        playerStatistic = new HashMap<>();
    }

    public Map<String, Integer> getPlayerStatistic(){
        playerStatistic.put("Games", numberOfGames);
        playerStatistic.put("Wins", numberOfWins);
        playerStatistic.put("Defeats", numberOfDefeats);

        return playerStatistic;
    }
}
