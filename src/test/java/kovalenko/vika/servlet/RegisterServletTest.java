package kovalenko.vika.servlet;

import kovalenko.vika.common.exception.RegisterException;
import kovalenko.vika.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.ServletException;
import java.io.IOException;

import static kovalenko.vika.common.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER_SERVICE;
import static kovalenko.vika.common.constant.LinkConstant.QUEST_LINK;
import static kovalenko.vika.common.constant.PathsJsp.INDEX_JSP;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegisterServletTest extends AbstractServletTest {
    @Mock
    PlayerService playerService;
    RegisterServlet servlet;

    @Override
    @BeforeEach
    protected void init() throws ServletException {
        when(context.getAttribute(PLAYER_SERVICE)).thenReturn(playerService);
        servlet = new RegisterServlet();
        super.init(servlet);
    }

    @Test
    void do_get_forward_to_home_page() throws ServletException, IOException {
        whenDispatcher();
        servlet.doGet(request, response);

        verifyContextGetDispatcher(INDEX_JSP);
        verifyDispatcherForward();
    }

    @Test
    void do_post_forward_when_register_exception() throws ServletException, IOException {
        whenDispatcher();
        when(playerService.register(null)).thenThrow(RegisterException.class);

        servlet.doPost(request, response);

        verifyContextGetDispatcher(INDEX_JSP);
        verifyDispatcherForward();
        verifyResponseNeverRedirect(QUEST_LINK);
    }

    @Test
    void do_post_redirect_to_quest() throws ServletException, IOException {
        whenSession();
        servlet.doPost(request, response);

        verifyRequestNeverGetContext();
        verify(session, times(1)).setAttribute(PLAYER, eq(any()));
        verifyResponseRedirect(QUEST_LINK);
    }
}
