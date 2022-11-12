import lombok.Getter;

public abstract class Sentence {
    @Getter
    private final int id;
    @Getter
    private final String context;

    public Sentence(int id, String context) {
        this.id = id;
        this.context = context;
    }
}
