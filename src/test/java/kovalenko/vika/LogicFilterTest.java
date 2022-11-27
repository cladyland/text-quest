package kovalenko.vika;

import kovalenko.vika.basis.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static kovalenko.vika.PathsJsp.START_JSP;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogicFilterTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private ServletContext context;
    @Mock
    private Player player;
    private LogicFilter logicFilter;

    @BeforeEach
    void init() {
        logicFilter = new LogicFilter();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("player")).thenReturn(player);
    }

    @Test
    void doFilter_redirect_if_player_isNull() throws ServletException, IOException {
        when(session.getAttribute("player")).thenReturn(null);
        logicFilter.doFilter(request, response, chain);
        verify(response, times(1)).sendRedirect("/");
    }

    @Test
    void doFilter_dispatcher_StartJsp_if_player_isNewcomer() throws ServletException, IOException {
        when(player.isNewcomer()).thenReturn(true);
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(START_JSP.toString())).thenReturn(dispatcher);

        logicFilter.doFilter(request, response, chain);

        verify(player, times(1)).setNewcomer(false);
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doFilter_setAttribute_if_cardId_isNull() throws ServletException, IOException {
        String attrKey = "cardID";
        Integer attrValue = 1;
        when(session.getAttribute("cardID")).thenReturn(null);

        logicFilter.doFilter(request, response, chain);

        verify(session, times(1)).setAttribute(attrKey, attrValue);
        verify(chain, times(1)).doFilter(request, response);
    }
}