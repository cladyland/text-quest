import lombok.Getter;

public abstract class Sentence {
    @Getter
    private int id;
    @Getter
    private String context;

    public Sentence() {
    }

    public Sentence(int id, String context) {
        this.id = id;
        this.context = context;
    }
}
