package kovalenko.vika;

import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.Player;
import kovalenko.vika.basis.Status;
import kovalenko.vika.db.PathsJsp;
import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static kovalenko.vika.db.PathsJsp.END_JSP;
import static kovalenko.vika.db.PathsJsp.QUEST_JSP;
import static kovalenko.vika.db.PathsJsp.START_JSP;

@WebServlet(name = "LogicServlet", value = "/quest")
public class LogicServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LogicServlet.class);

    private QuestService questService;
    private PlayerService playerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        questService = (QuestService) servletContext.getAttribute("questService");
        playerService = (PlayerService) servletContext.getAttribute("playerService");

        LOG.info("'Logic Servlet' is initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req
                .getSession()
                .removeAttribute("cardID");

        forwardTo(START_JSP, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        var player = (Player) session.getAttribute("player");
        var playerCardId = (Integer) session.getAttribute("cardID");
        String answerParam = req.getParameter("playerAnswerId");

        Card playerCard = questService.getCurrentCard(playerCardId);

        if (nonNull(answerParam)) {
            int playerAnswerId = Integer.parseInt(answerParam);
            Status playerStatus = questService.getPlayerAnswerStatus(playerCardId, playerAnswerId);

            if (playerStatus == Status.NEXT) {
                playerCard = questService.getNextCard(playerCardId);
                session.setAttribute("cardID", playerCard.getId());
            } else {
                setEndgameMessage(playerStatus, playerAnswerId, req);

                req.setAttribute("statistic", playerService.setAndGetPlayerStatistic(player, playerStatus));
                session.removeAttribute("cardID");

                forwardTo(END_JSP, req, resp);
                return;
            }
        }

        req.setAttribute("question", playerCard.getQuestion().getContext());
        req.setAttribute("answers", playerCard.getAnswers());

        forwardTo(QUEST_JSP, req, resp);
    }

    private void setEndgameMessage(Status playerStatus, Integer answerId, HttpServletRequest request){
        if (playerStatus == Status.DEFEAT) {
            String defeatMessage = questService.getDefeatMessage(answerId) + "\nYOU LOSE";
            request.setAttribute("defeat", defeatMessage);
        } else if (playerStatus == Status.VICTORY) {
            String winMessage = questService.getVictoryMessage() + "\nYOU WIN";
            request.setAttribute("victory", winMessage);
        }
    }

    private void forwardTo(PathsJsp jsp, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var forwardPath = jsp.toString();
        req
                .getServletContext()
                .getRequestDispatcher(forwardPath)
                .forward(req, resp);
    }
}
