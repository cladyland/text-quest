package kovalenko.vika.servlet;

import kovalenko.vika.common.constant.Status;
import kovalenko.vika.common.dto.CardDTO;
import kovalenko.vika.common.dto.PlayerDTO;
import kovalenko.vika.common.dto.QuestionDTO;
import kovalenko.vika.common.exception.QuestDefaultException;
import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogicServletTest extends AbstractServletTest {
    @Mock
    QuestService questService;
    @Mock
    PlayerService playerService;
    @Mock
    CardDTO cardMock;
    @Mock
    QuestionDTO questionMock;
    LogicServlet servlet;

    @Override
    @BeforeEach
    protected void init() throws ServletException {
        whenSession();
        when(context.getAttribute(QUEST_SERVICE)).thenReturn(questService);
        when(context.getAttribute(PLAYER_SERVICE)).thenReturn(playerService);

        servlet = new LogicServlet();
        super.init(servlet);
    }

    @Test
    void do_get_remove_cardId_and_forward_to_start_quest() throws ServletException, IOException {
        whenDispatcher();

        servlet.doGet(request, response);

        verify(session, times(1)).removeAttribute(CARD_ID);
        verifyContextGetDispatcher(START_JSP);
        verifyDispatcherForward();
    }

    @Test
    void do_post_forward_when_answer_is_null() throws ServletException, IOException {
        whenGetCard();
        whenDispatcher();
        when(cardMock.getQuestion()).thenReturn(questionMock);

        servlet.doPost(request, response);

        verify(questService, never()).getPlayerAnswerStatus(anyInt(), anyInt());
        verify(request, times(1)).setAttribute(QUESTION, eq(any()));
        verify(request, times(1)).setAttribute(ANSWERS, eq(any()));

        verifyContextGetDispatcher(QUEST_JSP);
        verifyDispatcherForward();
    }

    @Test
    void do_post_set_next_card_and_forward() throws ServletException, IOException {
        whenGetCard();
        whenGetAnswerId();
        whenDispatcher();

        when(cardMock.getQuestion()).thenReturn(questionMock);
        when(questService.getPlayerAnswerStatus(null, 1)).thenReturn(Status.NEXT);
        when(cardMock.getId()).thenReturn(1);
        when(questService.getNextCard(null)).thenReturn(cardMock);

        servlet.doPost(request, response);

        verify(session, times(1)).setAttribute(CARD_ID, 1);
        verify(request, times(1)).setAttribute(QUESTION, eq(any()));
        verify(request, times(1)).setAttribute(ANSWERS, eq(any()));

        verifyContextGetDispatcher(QUEST_JSP);
        verifyDispatcherForward();
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"DEFEAT", "VICTORY"})
    void do_post_forward_to_end_game(Status status) throws ServletException, IOException {
        whenGetCard();
        whenGetAnswerId();
        whenDispatcher();

        when(session.getAttribute(CARD_ID)).thenReturn(null);
        when(questService.getPlayerAnswerStatus(null, 1)).thenReturn(status);
        when(session.getAttribute(PLAYER)).thenReturn(Mockito.mock(PlayerDTO.class));

        servlet.doPost(request, response);

        verify(request, times(1)).setAttribute(eq(END), anyString());
        verify(session, times(1)).setAttribute(PLAYER, eq(any()));
        verify(context, never()).getRequestDispatcher(QUEST_JSP.toString());

        verifyContextGetDispatcher(END_JSP);
        verifyDispatcherForward();
    }

    @Test
    void do_post_redirect_when_quest_exception() throws ServletException, IOException {
        whenGetAnswerId();
        when(questService.getPlayerAnswerStatus(null, 1)).thenThrow(QuestDefaultException.class);

        servlet.doPost(request, response);

        verifyResponseRedirect(QUEST_LINK);
        verify(context, never()).getRequestDispatcher(QUEST_JSP.toString());
    }

    private void whenGetCard() {
        when(questService.getCurrentCard(null)).thenReturn(cardMock);
    }

    private void whenGetAnswerId() {
        when(request.getParameter(PLAYER_ANSWER_ID)).thenReturn("1");
    }
}
