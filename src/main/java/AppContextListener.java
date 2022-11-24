import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        CardsManager cardsManager = new CardsManager();
        cardsManager.createCards("cards.yml");
        cardsManager.createDefeats("defeats.yml");

        var servletContext = contextEvent.getServletContext();

        servletContext.setAttribute("playerRepository", new PlayerRepository());
        servletContext.setAttribute("cards", cardsManager.getCards());
        servletContext.setAttribute("defeats", cardsManager.getDefeats());
        servletContext.setAttribute("victory", cardsManager.getVICTORY());
    }
}
