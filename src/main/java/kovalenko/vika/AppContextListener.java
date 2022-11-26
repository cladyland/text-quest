package kovalenko.vika;

import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        var playerRepository = new PlayerRepository();
        var playerService = new PlayerService(playerRepository);
        var cardsManager = new CardsManager();
        cardsManager.createCards("cards.yml");
        cardsManager.createDefeats("defeats.yml");

        var questService = new QuestService(
                cardsManager.getCards(),
                cardsManager.getDefeats(),
                cardsManager.getVICTORY());

        var servletContext = contextEvent.getServletContext();

        servletContext.setAttribute("playerService", playerService);
        servletContext.setAttribute("questService", questService);
    }
}
