import lombok.Getter;

public class Player {
    @Getter
    private String nickName;

    public Player(String nickName) {
        this.nickName = nickName;
    }
}
