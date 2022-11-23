import kovalenko.vika.basis.Answer;
import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.Defeat;
import kovalenko.vika.basis.Question;
import kovalenko.vika.basis.Status;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static kovalenko.vika.PathsJsp.INDEX_JSP;
import static kovalenko.vika.PathsJsp.QUEST_JSP;
import static kovalenko.vika.PathsJsp.START_JSP;

@WebServlet(name = "LogicServlet", value = "/quest")
public class LogicServlet extends HttpServlet {
    private List<Card> cardList;
    private List<Defeat> defeatList;

    @SuppressWarnings("unchecked")
    @Override
    public void init() throws ServletException {
        super.init();
        cardList = (List<Card>) getServletContext().getAttribute("cards");
        defeatList = (List<Defeat>) getServletContext().getAttribute("defeats");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req
                .getServletContext()
                .getRequestDispatcher(START_JSP.toString())
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        var player = (Player) session.getAttribute("player");
        var playerCardId = (Integer) session.getAttribute("cardID");
        int nextQuestionId = playerCardId + 1;

        if (nonNull(req.getParameter("answer"))) {
            Integer playerAnswerId = Integer.parseInt(req.getParameter("answer"));
            Answer playerAnswer = cardList
                    .get(playerCardId)
                    .getAnswers()
                    .get(playerAnswerId);

        }

        Card card = getCard(nextQuestionId);

        Question question = card.getQuestion();
        List<Answer> answers = card.getAnswers();

        session.setAttribute("question", question.getContext());
        session.setAttribute("answers", answers);

        req
                .getServletContext()
                .getRequestDispatcher(QUEST_JSP.toString())
                .forward(req, resp);
    }

    private Card getCard(int questionId) {
        return cardList
                .stream()
                .filter(card -> card.getId() == questionId)
                .findAny()
                .orElse(cardList.get(0));
    }

}
