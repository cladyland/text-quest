package kovalenko.vika.db.imp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import kovalenko.vika.common.entities.Card;
import kovalenko.vika.common.entities.sentence.Defeat;
import kovalenko.vika.db.CardsManager;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
public class CardsManagerImp implements CardsManager {
    private final String VICTORY = "You came back home.";
    private final String CARDS_PARAM = "cards";
    private final String DEFEATS_PARAM = "defeats";
    private List<Defeat> defeats;
    private List<Card> cards;

    public CardsManagerImp() {
    }

    @Override
    public void createCards(String ymlFileName) {
        deserializeYamlFile(ymlFileName, CARDS_PARAM);
    }

    @Override
    public void createDefeats(String ymlFileName) {
        deserializeYamlFile(ymlFileName, DEFEATS_PARAM);
    }

    @Override
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    @Override
    public List<Defeat> getDefeats() {
        return Collections.unmodifiableList(defeats);
    }

    @Override
    public String getVictory() {
        return this.VICTORY;
    }

    private void deserializeYamlFile(String fileName, String param) {
        var resource = getResourceURL(fileName);

        if (isNull(resource)) {
            var message = String
                    .format("Could not find the file or filename has a non-yml extension: '%s'", fileName);

            log.error(message);
            throw new IllegalArgumentException(message);
        }

        var mapper = new YAMLMapper();
        try {
            if (param.equals(CARDS_PARAM)) {
                cards = mapper.readValue(resource, new TypeReference<>() {
                });
            } else if (param.equals(DEFEATS_PARAM)) {
                defeats = mapper.readValue(resource, new TypeReference<>() {
                });
            } else {
                String message = param + " is not found, file cannot be deserialized";
                log.error(message);
                throw new IllegalArgumentException(message);
            }

            log.info(String.format("File '%s' deserialized successfully", fileName));

        } catch (IOException ex) {
            var message = String.format("Failed to read file '%s'", fileName);
            log.error(message, ex.getCause());
            throw new RuntimeException(message, ex);
        }
    }

    private URL getResourceURL(String fileName) {
        return getClass().getClassLoader().getResource(fileName);
    }
}
