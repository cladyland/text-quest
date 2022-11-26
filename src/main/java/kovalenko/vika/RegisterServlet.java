package kovalenko.vika;

import kovalenko.vika.basis.Player;
import kovalenko.vika.service.PlayerService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kovalenko.vika.PathsJsp.INDEX_JSP;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private PlayerService playerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        playerService = (PlayerService) servletContext.getAttribute("playerService");
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
        Player newPlayer = register(newNickName);

        if (isDefaultPlayer(newPlayer)) {
            String busyName = "Sorry, this name is already taken";
            req.setAttribute("wrongNickName", busyName);
            doGet(req, resp);
        }

        var session = req.getSession();
        session.setAttribute("player", newPlayer);
        session.setAttribute("nickName", newNickName);

        resp.sendRedirect("/quest");
    }

    private Player register(String nickName) {
        return playerService.register(nickName);
    }

    private boolean isDefaultPlayer(Player player) {
        return player.getNickName().equals("Default");
    }
}