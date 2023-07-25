package kovalenko.vika;

import kovalenko.vika.common.constant.PathsJsp;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractFilterServletTest {
    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;
    @Mock
    protected HttpSession session;
    @Mock
    protected ServletContext context;
    @Mock
    protected RequestDispatcher dispatcher;

    protected abstract void init() throws ServletException;

    protected void whenSession() {
        when(request.getSession()).thenReturn(session);
    }

    protected void whenDispatcher() {
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    protected void verifyRequestNeverGetContext() {
        verify(request, never()).getServletContext();
    }

    protected void verifyContextGetDispatcher(PathsJsp path) {
        verify(context, times(1)).getRequestDispatcher(path.toString());
    }

    protected void verifyDispatcherForward() throws ServletException, IOException {
        verify(dispatcher, times(1)).forward(request, response);
    }

    protected void verifyResponseRedirect(String link) throws IOException {
        verify(response, times(1)).sendRedirect(link);
    }

    protected void verifyResponseNeverRedirect(String link) throws IOException {
        verify(response, never()).sendRedirect(link);
    }
}
