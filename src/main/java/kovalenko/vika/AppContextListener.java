package kovalenko.vika;

import kovalenko.vika.db.CardsManager;
import kovalenko.vika.db.PlayerRepository;
import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static kovalenko.vika.constant.AttributeConstant.PLAYER_SERVICE;
import static kovalenko.vika.constant.AttributeConstant.QUEST_SERVICE;
import static kovalenko.vika.constant.LinkConstant.CARDS_CONFIG;
import static kovalenko.vika.constant.LinkConstant.DEFEAT_CONFIG;

@Slf4j
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        log.info("'Servlet context' initialization begins...");

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

        servletContext.setAttribute(PLAYER_SERVICE, playerService);
        servletContext.setAttribute(QUEST_SERVICE, questService);

        log.info("'Servlet context' initialized successfully");
    }
}
