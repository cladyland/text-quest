package kovalenko.vika;

import kovalenko.vika.db.CardsManager;
import kovalenko.vika.db.imp.CardsManagerImp;
import kovalenko.vika.db.PlayerRepository;
import kovalenko.vika.db.imp.PlayerRepositoryImp;
import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;
import kovalenko.vika.service.imp.PlayerServiceImp;
import kovalenko.vika.service.imp.QuestServiceImp;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static kovalenko.vika.common.constant.AttributeConstant.CARDS_PARAM;
import static kovalenko.vika.common.constant.AttributeConstant.DEFEATS_PARAM;
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER_SERVICE;
import static kovalenko.vika.common.constant.AttributeConstant.QUEST_SERVICE;
import static kovalenko.vika.common.constant.LinkConstant.CARDS_CONFIG;
import static kovalenko.vika.common.constant.LinkConstant.DEFEAT_CONFIG;

@Slf4j
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        log.info("'Servlet context' initialization begins...");

        PlayerRepository playerRepository = new PlayerRepositoryImp();
        PlayerService playerService = new PlayerServiceImp(playerRepository);

        CardsManager cardsManager = new CardsManagerImp() {{
            createResource(CARDS_CONFIG, CARDS_PARAM);
            createResource(DEFEAT_CONFIG, DEFEATS_PARAM);
        }};

        QuestService questService = new QuestServiceImp(cardsManager);

        var servletContext = contextEvent.getServletContext();

        servletContext.setAttribute(PLAYER_SERVICE, playerService);
        servletContext.setAttribute(QUEST_SERVICE, questService);

        log.info("'Servlet context' initialized successfully");
    }
}
