package kovalenko.vika.servlet;

import kovalenko.vika.common.dto.PlayerDTO;
import kovalenko.vika.common.exception.RegisterException;
import kovalenko.vika.service.PlayerService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kovalenko.vika.common.constant.AttributeConstant.NICK_NAME;
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER_SERVICE;
import static kovalenko.vika.common.constant.AttributeConstant.WRONG_NICK_NAME;
import static kovalenko.vika.common.constant.LinkConstant.QUEST_LINK;
import static kovalenko.vika.common.constant.LinkConstant.REGISTER_LINK;
import static kovalenko.vika.common.constant.PathsJsp.INDEX_JSP;

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
        PlayerDTO newPlayer;

        try {
            newPlayer = playerService.register(newNickName);
        } catch (RegisterException ex) {
            req.setAttribute(WRONG_NICK_NAME, ex.getMessage());
            doGet(req, resp);
            return;
        }

        req.getSession().setAttribute(PLAYER, newPlayer);
        resp.sendRedirect(QUEST_LINK);
    }
}
