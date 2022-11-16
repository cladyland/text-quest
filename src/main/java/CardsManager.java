import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.Defeat;
import lombok.Getter;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

public class CardsManager {
    private static CardsManager cardsManager;
    private final String CARDS_YML_PATH = "src/main/resources/cards.yml";
    private final String DEFEATS_YML_PATH = "src/main/resources/defeats.yml";
    @Getter
    private final String VICTORY = "You came back home.";
    private List<Defeat> defeats;
    private List<Card> cards;

    private CardsManager() {
        fillCards();
        fillDefeatsList();
    }

    public static CardsManager getInstance() {
        if (isNull(cardsManager)) {
            cardsManager = new CardsManager();
        }
        return cardsManager;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public List<Defeat> getDefeats() {
        return Collections.unmodifiableList(defeats);
    }

    private void fillCards() {
        deserializeYamlFile(CARDS_YML_PATH);
    }

    private void fillDefeatsList() {
        deserializeYamlFile(DEFEATS_YML_PATH);
    }

    private void deserializeYamlFile(String filePath) {
        try (var reader = new FileReader(filePath)) {
            var mapper = new YAMLMapper();
            if (filePath.equals(CARDS_YML_PATH)) {
                cards = mapper.readValue(reader, new TypeReference<>() {
                });
            } else if (filePath.equals(DEFEATS_YML_PATH)) {
                defeats = mapper.readValue(reader, new TypeReference<>() {
                });
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read file " + filePath, ex);
        }
    }
}
