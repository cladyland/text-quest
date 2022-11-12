import lombok.Getter;

public class Answer extends Sentence {
    @Getter
    private final Status status;

    public Answer(int id, String context, Status status) {
        super(id, context);
        this.status = status;
    }
}
