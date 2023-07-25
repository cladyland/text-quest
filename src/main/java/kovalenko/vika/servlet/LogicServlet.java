package kovalenko.vika.servlet;

import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.constant.PathsJsp;
import kovalenko.vika.common.dto.CardDTO;
import kovalenko.vika.common.dto.PlayerDTO;
import kovalenko.vika.common.exception.QuestDefaultException;
import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static kovalenko.vika.common.constant.AttributeConstant.ANSWERS;
import static kovalenko.vika.common.constant.AttributeConstant.CARD_ID;
import static kovalenko.vika.common.constant.AttributeConstant.END;
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER_ANSWER_ID;
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER_SERVICE;
import static kovalenko.vika.common.constant.AttributeConstant.QUESTION;
import static kovalenko.vika.common.constant.AttributeConstant.QUEST_SERVICE;
import static kovalenko.vika.common.constant.LinkConstant.QUEST_LINK;
import static kovalenko.vika.common.constant.PathsJsp.END_JSP;
import static kovalenko.vika.common.constant.PathsJsp.QUEST_JSP;
import static kovalenko.vika.common.constant.PathsJsp.START_JSP;

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
        var playerCardId = (Integer) session.getAttribute(CARD_ID);
        String answerParam = req.getParameter(PLAYER_ANSWER_ID);

        CardDTO playerCard = questService.getCurrentCard(playerCardId);

        if (nonNull(answerParam)) {
            int playerAnswerId = Integer.parseInt(answerParam);

            try {
                Status playerStatus = questService.getPlayerAnswerStatus(playerCardId, playerAnswerId);

                if (playerStatus == Status.NEXT) {
                    playerCard = questService.getNextCard(playerCardId);
                    session.setAttribute(CARD_ID, playerCard.getId());
                } else {
                    setEndgameMessage(playerStatus, playerAnswerId, req);

                    session.setAttribute(PLAYER, updatePlayerStatistic(session, playerStatus));
                    session.removeAttribute(CARD_ID);

                    forwardTo(END_JSP, req, resp);
                    return;
                }
            } catch (QuestDefaultException ex) {
                resp.sendRedirect(QUEST_LINK);
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

    private PlayerDTO updatePlayerStatistic(HttpSession session, Status playerStatus) {
        var player = (PlayerDTO) session.getAttribute(PLAYER);
        return playerService.updatePlayerStatistic(player.getNickName(), playerStatus);
    }

    private void forwardTo(PathsJsp jsp, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var forwardPath = jsp.toString();
        req
                .getServletContext()
                .getRequestDispatcher(forwardPath)
                .forward(req, resp);
    }
}
