package kovalenko.vika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static kovalenko.vika.PathsJsp.INDEX_JSP;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthenticationFilterTest {
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

    private AuthenticationFilter authenticationFilter;

    @BeforeEach
    void init(){
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        chain = Mockito.mock(FilterChain.class);
        session = Mockito.mock(HttpSession.class);
        dispatcher = Mockito.mock(RequestDispatcher.class);
        context = Mockito.mock(ServletContext.class);

        authenticationFilter = new AuthenticationFilter();

        when(request.getServletContext()).thenReturn(context);
        when(request.getSession()).thenReturn(session);
        when(context.getRequestDispatcher(eq(INDEX_JSP.toString()))).thenReturn(dispatcher);
    }

    @Test
    void doFilter_dispatcher_IndexJsp_when_session_is_new() throws ServletException, IOException {
        when(session.isNew()).thenReturn(true);
        authenticationFilter.doFilter(request, response, chain);

        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doFilter_dispatcher_IndexJsp_when_nickName_isNull() throws ServletException, IOException {
        when(session.getAttribute("nickName")).thenReturn(null);
        authenticationFilter.doFilter(request, response, chain);

        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doFilter_sendRedirect_to_quest() throws ServletException, IOException {
        when(session.getAttribute("nickName")).thenReturn("Test");
        authenticationFilter.doFilter(request, response, chain);

        verify(response, times(1)).sendRedirect("/quest");
    }
}