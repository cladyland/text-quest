package kovalenko.vika;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static kovalenko.vika.PathsJsp.INDEX_JSP;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewNickNameFilterTest {
    private final String nickNameParam = "nickName";
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private ServletContext context;
    private NewNickNameFilter nameFilter;

    @BeforeEach
    void init() {
        nameFilter = new NewNickNameFilter();
    }

    @Test
    void doFilter_redirect_if_nickName_isNull() throws ServletException, IOException {
        when(request.getParameter(nickNameParam)).thenReturn(null);
        nameFilter.doFilter(request, response, chain);

        verify(response, times(1)).sendRedirect("/");
    }

    @ParameterizedTest
    @CsvSource({"*&%", "name*/"})
    void doFilter_forward_if_name_nonWordCharacter(String nonWordName) throws ServletException, IOException {
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(INDEX_JSP.toString())).thenReturn(dispatcher);
        when(request.getParameter(nickNameParam)).thenReturn(nonWordName);
        nameFilter.doFilter(request, response, chain);

        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doFilter_forward_if_name_isUnderscoreSymbols() throws ServletException, IOException {
        when(request.getServletContext()).thenReturn(context);
        when(context.getRequestDispatcher(INDEX_JSP.toString())).thenReturn(dispatcher);
        when(request.getParameter(nickNameParam)).thenReturn("_______");
        nameFilter.doFilter(request, response, chain);

        verify(dispatcher, times(1)).forward(request, response);
    }

    @ParameterizedTest
    @CsvSource({"Test", "Name098", "_test_", "1056", "_5_"})
    void doFilter_chain_if_name_isCorrect(String nickName) throws ServletException, IOException {
        when(request.getParameter(nickNameParam)).thenReturn(nickName);
        nameFilter.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(dispatcher, times(0)).forward(request, response);
    }
}