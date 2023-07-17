package kovalenko.vika.servlet;

import kovalenko.vika.basis.sentence.Answer;
import kovalenko.vika.basis.Card;
import kovalenko.vika.basis.Player;
import kovalenko.vika.basis.sentence.Question;
import kovalenko.vika.basis.Status;
import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;
import kovalenko.vika.servlet.LogicServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kovalenko.vika.db.PathsJsp.END_JSP;
import static kovalenko.vika.db.PathsJsp.QUEST_JSP;
import static kovalenko.vika.db.PathsJsp.START_JSP;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogicServletTest {
    @Mock
    private ServletConfig config;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext context;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private QuestService questService;
    @Mock
    private PlayerService playerService;
    @Mock
    private Player player;
    @Mock
    private Card card;
    private LogicServlet logicServlet;

    @BeforeEach
    void init() throws ServletException {
        when(config.getServletContext()).thenReturn(context);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute("questService")).thenReturn(questService);
        when(context.getAttribute("playerService")).thenReturn(playerService);
        when(request.getSession()).thenReturn(session);

        logicServlet = new LogicServlet();
        logicServlet.init(config);
    }

    @Test
    void doGet_forward_StartJsp() throws ServletException, IOException {
        when(context.getRequestDispatcher(START_JSP.toString())).thenReturn(dispatcher);
        logicServlet.doGet(request, response);

        verify(session, times(1)).removeAttribute("cardID");
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doPost_if_answerStatus_NEXT_forward_QuestJsp() throws ServletException, IOException {
        int cardId = 2;
        int nextCardId = cardId + 1;
        Question nextQuestion = new Question(nextCardId, "next question");
        List<Answer> answers = new ArrayList<>();

        when(context.getRequestDispatcher(QUEST_JSP.toString())).thenReturn(dispatcher);
        when(session.getAttribute("player")).thenReturn(player);
        when(session.getAttribute("cardID")).thenReturn(cardId);
        when(request.getParameter("playerAnswerId")).thenReturn("22");
        when(questService.getCurrentCard(cardId)).thenReturn(card);
        when(questService.getPlayerAnswerStatus(cardId, 22)).thenReturn(Status.NEXT);
        when(questService.getNextCard(cardId)).thenReturn(card);
        when(card.getId()).thenReturn(nextCardId);
        when(card.getQuestion()).thenReturn(nextQuestion);
        when(card.getAnswers()).thenReturn(answers);

        logicServlet.doPost(request, response);

        verify(session, times(1)).setAttribute("cardID", nextCardId);
        verify(request, times(1)).setAttribute("question", nextQuestion.getContext());
        verify(request, times(1)).setAttribute("answers", answers);
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doPost_if_answerStatus_DEFEAT_forward_EndJsp() throws ServletException, IOException {
        int cardId = 3;
        int answerId = 31;
        var playerStatus = Status.DEFEAT;
        Map<String, Integer> playerStatistic = new HashMap<>();

        when(context.getRequestDispatcher(END_JSP.toString())).thenReturn(dispatcher);
        when(session.getAttribute("player")).thenReturn(player);
        when(session.getAttribute("cardID")).thenReturn(cardId);
        when(request.getParameter("playerAnswerId")).thenReturn("31");
        when(questService.getCurrentCard(cardId)).thenReturn(card);
        when(questService.getPlayerAnswerStatus(cardId, answerId)).thenReturn(Status.DEFEAT);
        when(playerService.setAndGetPlayerStatistic(player, playerStatus)).thenReturn(playerStatistic);

        logicServlet.doPost(request, response);

        verify(request, times(1)).setAttribute("statistic", playerStatistic);
        verify(session, times(1)).removeAttribute("cardID");
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doPost_if_answerStatus_VICTORY_forward_EndJsp() throws ServletException, IOException {
        int cardId = 4;
        int answerId = 42;
        var playerStatus = Status.VICTORY;
        Map<String, Integer> playerStatistic = new HashMap<>();

        when(context.getRequestDispatcher(END_JSP.toString())).thenReturn(dispatcher);
        when(session.getAttribute("player")).thenReturn(player);
        when(session.getAttribute("cardID")).thenReturn(cardId);
        when(request.getParameter("playerAnswerId")).thenReturn("42");
        when(questService.getCurrentCard(cardId)).thenReturn(card);
        when(questService.getPlayerAnswerStatus(cardId, answerId)).thenReturn(Status.VICTORY);
        when(playerService.setAndGetPlayerStatistic(player, playerStatus)).thenReturn(playerStatistic);

        logicServlet.doPost(request, response);

        verify(request, times(1)).setAttribute("statistic", playerStatistic);
        verify(session, times(1)).removeAttribute("cardID");
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doPost_forward_QuestJsp_if_answerParam_isNull() throws ServletException, IOException {
        int cardId = 5;
        var question = new Question(cardId, "Question");
        List<Answer> answers = new ArrayList<>();

        when(context.getRequestDispatcher(QUEST_JSP.toString())).thenReturn(dispatcher);
        when(session.getAttribute("player")).thenReturn(player);
        when(session.getAttribute("cardID")).thenReturn(cardId);
        when(request.getParameter("playerAnswerId")).thenReturn(null);
        when(questService.getCurrentCard(cardId)).thenReturn(card);
        when(card.getQuestion()).thenReturn(question);
        when(card.getAnswers()).thenReturn(answers);

        logicServlet.doPost(request, response);

        verify(request, times(1)).setAttribute("question", question.getContext());
        verify(request, times(1)).setAttribute("answers", answers);
        verify(dispatcher, times(1)).forward(request, response);
    }
}