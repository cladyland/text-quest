package kovalenko.vika.servlet;

import kovalenko.vika.basis.Status;
import kovalenko.vika.db.PathsJsp;
import kovalenko.vika.dto.CardDTO;
import kovalenko.vika.dto.PlayerDTO;
import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static kovalenko.vika.constant.AttributeConstant.ANSWERS;
import static kovalenko.vika.constant.AttributeConstant.CARD_ID;
import static kovalenko.vika.constant.AttributeConstant.END;
import static kovalenko.vika.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.constant.AttributeConstant.PLAYER_ANSWER_ID;
import static kovalenko.vika.constant.AttributeConstant.PLAYER_SERVICE;
import static kovalenko.vika.constant.AttributeConstant.QUESTION;
import static kovalenko.vika.constant.AttributeConstant.QUEST_SERVICE;
import static kovalenko.vika.constant.LinkConstant.QUEST_LINK;
import static kovalenko.vika.db.PathsJsp.END_JSP;
import static kovalenko.vika.db.PathsJsp.QUEST_JSP;
import static kovalenko.vika.db.PathsJsp.START_JSP;

@Slf4j
@WebServlet(name = "LogicServlet", value = QUEST_LINK)
public class LogicServlet extends HttpServlet {
    private QuestService questService;
    private PlayerService playerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        questService = (QuestService) servletContext.getAttribute(QUEST_SERVICE);
        playerService = (PlayerService) servletContext.getAttribute(PLAYER_SERVICE);

        log.info("'Logic Servlet' is initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req
                .getSession()
                .removeAttribute(CARD_ID);

        forwardTo(START_JSP, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        var player = (PlayerDTO) session.getAttribute(PLAYER);
        var playerCardId = (Integer) session.getAttribute(CARD_ID);
        String answerParam = req.getParameter(PLAYER_ANSWER_ID);

        CardDTO playerCard = questService.getCurrentCard(playerCardId);

        if (nonNull(answerParam)) {
            int playerAnswerId = Integer.parseInt(answerParam);
            Status playerStatus = questService.getPlayerAnswerStatus(playerCardId, playerAnswerId);

            if (playerStatus == Status.NEXT) {
                playerCard = questService.getNextCard(playerCardId);
                session.setAttribute(CARD_ID, playerCard.getId());
            } else {
                setEndgameMessage(playerStatus, playerAnswerId, req);

                session.setAttribute(PLAYER, playerService.updatePlayerStatistic(player.getNickName(), playerStatus));
                session.removeAttribute(CARD_ID);

                forwardTo(END_JSP, req, resp);
                return;
            }
        }

        req.setAttribute(QUESTION, playerCard.getQuestion().getContext());
        req.setAttribute(ANSWERS, playerCard.getAnswers());

        forwardTo(QUEST_JSP, req, resp);
    }

    private void setEndgameMessage(Status playerStatus, Integer answerId, HttpServletRequest request) {
        String endMessage = "";
        if (playerStatus == Status.DEFEAT) {
            endMessage = questService.getDefeatMessage(answerId) + "\nYOU LOSE";
        } else if (playerStatus == Status.VICTORY) {
            endMessage = questService.getVictoryMessage() + "\nYOU WIN";
        }
        request.setAttribute(END, endMessage);
    }

    private void forwardTo(PathsJsp jsp, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var forwardPath = jsp.toString();
        req
                .getServletContext()
                .getRequestDispatcher(forwardPath)
                .forward(req, resp);
    }
}
