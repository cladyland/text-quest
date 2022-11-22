import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.Defeat;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class CardsManager {
    @Getter
    private final String VICTORY = "You came back home.";
    private List<Defeat> defeats;
    private List<Card> cards;

    public CardsManager() {
    }

    public void createCards(String ymlFileName) {
        cards = deserializeYamlFile(ymlFileName);
    }

    public void createDefeats(String ymlFileName) {
        defeats = deserializeYamlFile(ymlFileName);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public List<Defeat> getDefeats() {
        return Collections.unmodifiableList(defeats);
    }

    private List deserializeYamlFile(String fileName) {
        List result;
        var resource = getResourceURL(fileName);
        var mapper = new YAMLMapper();
        try {
            result = mapper.readValue(resource, new TypeReference<>() {
            });
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read file " + fileName, ex);
        }
        return result;
    }

    private URL getResourceURL(String fileName) {
        return getClass().getClassLoader().getResource(fileName);
    }
}
