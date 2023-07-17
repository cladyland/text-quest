package kovalenko.vika;

import kovalenko.vika.db.CardsManager;
import kovalenko.vika.db.PlayerRepository;
import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(AppContextListener.class);
    private final String CARDS_CONFIG = "cards.yml";
    private final String DEFEAT_CONFIG = "defeats.yml";

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        LOG.info("'Servlet context' initialization begins...");

        var playerRepository = new PlayerRepository();
        var playerService = new PlayerService(playerRepository);
        var cardsManager = new CardsManager();
        cardsManager.createCards(CARDS_CONFIG);
        cardsManager.createDefeats(DEFEAT_CONFIG);

        var questService = new QuestService(
                cardsManager.getCards(),
                cardsManager.getDefeats(),
                cardsManager.getVICTORY());

        var servletContext = contextEvent.getServletContext();

        servletContext.setAttribute("playerService", playerService);
        servletContext.setAttribute("questService", questService);

        LOG.info("'Servlet context' initialized successfully");
    }
}
