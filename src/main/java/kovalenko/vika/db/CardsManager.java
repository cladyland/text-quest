package kovalenko.vika.db;

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
    private final String CARDS_PARAM = "cards";
    private final String DEFEATS_PARAM = "defeats";
    private List<Defeat> defeats;
    private List<Card> cards;

    public CardsManager() {
    }

    public void createCards(String ymlFileName) {
        deserializeYamlFile(ymlFileName, CARDS_PARAM);
    }

    public void createDefeats(String ymlFileName) {
        deserializeYamlFile(ymlFileName, DEFEATS_PARAM);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public List<Defeat> getDefeats() {
        return Collections.unmodifiableList(defeats);
    }

    private void deserializeYamlFile(String fileName, String param) {
        var resource = getResourceURL(fileName);
        var mapper = new YAMLMapper();
        try {
            if (param.equals(CARDS_PARAM)) {
                cards = mapper.readValue(resource, new TypeReference<>() {
                });
            } else if (param.equals(DEFEATS_PARAM)) {
                defeats = mapper.readValue(resource, new TypeReference<>() {
                });
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read file " + fileName, ex);
        }
    }

    private URL getResourceURL(String fileName) {
        return getClass().getClassLoader().getResource(fileName);
    }
}
