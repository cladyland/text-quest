import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        var servletContext = contextEvent.getServletContext();
        //   servletContext.setAttribute("cards", new CardsManager());
        servletContext.setAttribute("playerRepository", new PlayerRepository());
    }
}
