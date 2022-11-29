package kovalenko.vika;

import kovalenko.vika.basis.Player;
import kovalenko.vika.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kovalenko.vika.db.PathsJsp.INDEX_JSP;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterServlet.class);

    private PlayerService playerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        playerService = (PlayerService) servletContext.getAttribute("playerService");

        LOG.info("'Player Service' is initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req
                .getServletContext()
                .getRequestDispatcher(INDEX_JSP.toString())
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newNickName = req.getParameter("nickName");
        Player newPlayer = playerService.register(newNickName);

        if (playerService.isDefaultPlayer(newPlayer)) {
            String busyName = "Sorry, this name is already taken";
            req.setAttribute("wrongNickName", busyName);
            doGet(req, resp);
            return;
        }

        var session = req.getSession();
        session.setAttribute("player", newPlayer);
        session.setAttribute("nickName", newNickName);

        resp.sendRedirect("/quest");
    }
}
