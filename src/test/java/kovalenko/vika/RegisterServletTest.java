package kovalenko.vika;

import kovalenko.vika.basis.Player;
import kovalenko.vika.service.PlayerService;
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

import static kovalenko.vika.db.PathsJsp.INDEX_JSP;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterServletTest {
    private final String nickNameParam = "nickName";
    private final String testNick = "Test";
    @Mock
    private ServletConfig config;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private ServletContext context;
    @Mock
    private PlayerService playerService;
    @Mock
    private Player player;
    private RegisterServlet registerServlet;

    @BeforeEach
    void init() throws ServletException {
        when(config.getServletContext()).thenReturn(context);
        when(context.getAttribute("playerService")).thenReturn(playerService);

        registerServlet = new RegisterServlet();
        registerServlet.init(config);
    }

    @Test
    void doGet_forward_IndexJsp() throws ServletException, IOException {
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(INDEX_JSP.toString())).thenReturn(dispatcher);

        registerServlet.doGet(request, response);
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doPost_call_doGet_if_player_isDefault() throws ServletException, IOException {
        String busyName = "Sorry, this name is already taken";

        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(INDEX_JSP.toString())).thenReturn(dispatcher);
        when(request.getParameter(nickNameParam)).thenReturn(testNick);
        when(playerService.register(testNick)).thenReturn(player);
        when(playerService.isDefaultPlayer(player)).thenReturn(true);

        registerServlet.doPost(request, response);

        verify(request, times(1)).setAttribute("wrongNickName", busyName);
        verify(dispatcher, times(1)).forward(request, response);
        verify(session, never()).setAttribute("player", player);
        verify(session, never()).setAttribute(nickNameParam, testNick);
        verify(response, never()).sendRedirect("/quest");
    }

    @Test
    void doPost_sendRedirect() throws ServletException, IOException {
        when(request.getParameter(nickNameParam)).thenReturn(testNick);
        when(playerService.register(testNick)).thenReturn(player);
        when(playerService.isDefaultPlayer(player)).thenReturn(false);
        when(request.getSession()).thenReturn(session);

        registerServlet.doPost(request, response);

        verify(dispatcher, never()).forward(request, response);
        verify(session, times(1)).setAttribute("player", player);
        verify(session, times(1)).setAttribute(nickNameParam, testNick);
        verify(response, times(1)).sendRedirect("/quest");
    }
}