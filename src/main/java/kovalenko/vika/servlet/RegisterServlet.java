package kovalenko.vika.servlet;

import kovalenko.vika.basis.Player;
import kovalenko.vika.service.PlayerService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kovalenko.vika.constant.AttributeConstant.NICK_NAME;
import static kovalenko.vika.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.constant.AttributeConstant.PLAYER_SERVICE;
import static kovalenko.vika.constant.AttributeConstant.WRONG_NICK_NAME;
import static kovalenko.vika.constant.LinkConstant.QUEST_LINK;
import static kovalenko.vika.constant.LinkConstant.REGISTER_LINK;
import static kovalenko.vika.db.PathsJsp.INDEX_JSP;

@Slf4j
@WebServlet(name = "RegisterServlet", value = REGISTER_LINK)
public class RegisterServlet extends HttpServlet {
    private PlayerService playerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        playerService = (PlayerService) servletContext.getAttribute(PLAYER_SERVICE);

        log.info("'Player Service' is initialized");
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
        String newNickName = req.getParameter(NICK_NAME);
        Player newPlayer = playerService.register(newNickName);

        if (playerService.isDefaultPlayer(newPlayer)) {
            String busyName = "Sorry, this name is already taken";
            req.setAttribute(WRONG_NICK_NAME, busyName);
            doGet(req, resp);
            return;
        }

        var session = req.getSession();
        session.setAttribute(PLAYER, newPlayer);
        session.setAttribute(NICK_NAME, newNickName);

        resp.sendRedirect(QUEST_LINK);
    }
}
