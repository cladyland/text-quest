package kovalenko.vika.db.imp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import kovalenko.vika.common.entities.Card;
import kovalenko.vika.common.entities.sentence.Defeat;
import kovalenko.vika.common.exception.CardsManagerException;
import kovalenko.vika.db.CardsManager;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static kovalenko.vika.common.constant.AttributeConstant.CARDS_PARAM;
import static kovalenko.vika.common.constant.AttributeConstant.DEFEATS_PARAM;
import static kovalenko.vika.common.constant.AttributeConstant.VICTORY_MESSAGE;

@Slf4j
public class CardsManagerImp implements CardsManager {
    private List<Defeat> defeats;
    private List<Card> cards;

    public CardsManagerImp() {
    }

    @Override
    public void createResource(String ymlFileName, String param) {
        deserializeYamlFile(ymlFileName, param);
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
        return VICTORY_MESSAGE;
    }

    private void deserializeYamlFile(String fileName, String param) {
        var resource = getResourceURL(fileName);

        checkIfResourceNotNull(resource, fileName);

        var mapper = new YAMLMapper();
        try {
            switch (param) {
                case CARDS_PARAM -> cards = mapper.readValue(resource, new TypeReference<>() {
                });
                case DEFEATS_PARAM -> defeats = mapper.readValue(resource, new TypeReference<>() {
                });
                default -> {
                    String message = param + " is not found, file cannot be deserialized";
                    log.error(message);
                    throw new CardsManagerException(message);
                }
            }
            log.info("File '{}' deserialized successfully", fileName);

        } catch (IOException ex) {
            var message = String.format("Failed to read file '%s'", fileName);
            log.error(message, ex.getCause());
            throw new CardsManagerException(message);
        }
    }

    private URL getResourceURL(String fileName) {
        return getClass().getClassLoader().getResource(fileName);
    }

    private void checkIfResourceNotNull(URL resource, String fileName) {
        if (isNull(resource)) {
            var message = String
                    .format("Could not find the file or filename has a non-yml extension: '%s'", fileName);

            log.error(message);
            throw new IllegalArgumentException(message);
        }
    }
}
