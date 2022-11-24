import kovalenko.vika.PathsJsp;
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
import java.util.Objects;

import static java.util.Objects.nonNull;
import static kovalenko.vika.PathsJsp.END_JSP;
import static kovalenko.vika.PathsJsp.QUEST_JSP;
import static kovalenko.vika.PathsJsp.START_JSP;

@WebServlet(name = "LogicServlet", value = "/quest")
public class LogicServlet extends HttpServlet {
    private List<Card> cardList;
    private List<Defeat> defeatList;
    private String victoryMessage;

    @SuppressWarnings("unchecked")
    @Override
    public void init() throws ServletException {
        super.init();
        cardList = (List<Card>) getServletContext().getAttribute("cards");
        defeatList = (List<Defeat>) getServletContext().getAttribute("defeats");
        victoryMessage = (String) getServletContext().getAttribute("victory");
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
        var playerCardId = (Integer) session.getAttribute("cardID");
        Card playerCard = getCard(playerCardId);

        int nextQuestionId = 1;
        String answerParam = req.getParameter("playerAnswer");

        if (nonNull(answerParam)) {
            Integer playerAnswerId = Integer.parseInt(answerParam);
            Answer playerAnswer = getAnswer(playerCard, playerAnswerId);

            Status answerStatus = playerAnswer.getStatus();

            if (answerStatus == Status.NEXT) {
                nextQuestionId = playerCardId + 1;
                session.setAttribute("cardID", nextQuestionId);
            } else if (answerStatus == Status.DEFEAT) {
                String defeatMessage = defeatList
                        .stream()
                        .filter(defeat -> Objects.equals(defeat.getId(), playerAnswerId))
                        .findAny()
                        .get()
                        .getContext();

                req.setAttribute("defeat", defeatMessage);
                session.removeAttribute("cardID");
                forwardTo(END_JSP, req, resp);
            } else if (answerStatus == Status.VICTORY) {
                req.setAttribute("victory", victoryMessage);
                session.removeAttribute("cardID");
                forwardTo(END_JSP, req, resp);
            }

        }

        Card nextCard = getCard(nextQuestionId);

        Question question = nextCard.getQuestion();
        List<Answer> answers = nextCard.getAnswers();

        req.setAttribute("question", question.getContext());
        req.setAttribute("answers", answers);

        forwardTo(QUEST_JSP, req, resp);
    }

    private Card getCard(int cardId) {
        return cardList
                .stream()
                .filter(card -> card.getId() == cardId)
                .findAny()
                .orElse(cardList.get(0));
    }

    private Answer getAnswer(Card card, Integer answerId) {
        var defaultAnswer = new Answer(0, "Default", Status.DEFAULT);
        return card
                .getAnswers()
                .stream()
                .filter(answer -> Objects.equals(answer.getId(), answerId))
                .findAny()
                .orElse(defaultAnswer);
    }

    private void forwardTo(PathsJsp jsp, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var forwardPath = jsp.toString();
        req
                .getServletContext()
                .getRequestDispatcher(forwardPath)
                .forward(req, resp);
    }

}
