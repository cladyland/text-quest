package kovalenko.vika.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.servlet.ServletException;
import java.io.IOException;

import static kovalenko.vika.common.constant.AttributeConstant.NICK_NAME;
import static kovalenko.vika.common.constant.LinkConstant.HOME_LINK;
import static kovalenko.vika.common.constant.PathsJsp.INDEX_JSP;
import static org.mockito.Mockito.when;

class NewNickNameFilterTest extends AbstractFilterTest {
    @Override
    @BeforeEach
    protected void init() throws ServletException {
        filter = new NewNickNameFilter();
        super.init();
    }

    @Test
    void chain_do_filter() throws ServletException, IOException {
        when(request.getParameter(NICK_NAME)).thenReturn("name");

        super.doFilter();

        verifyResponseNeverRedirect(HOME_LINK);
        verifyRequestNeverGetContext();
        verifyChainDoFilter();
    }

    @Test
    void redirect_to_home_page_when_nickname_is_null() throws ServletException, IOException {
        super.doFilter();

        verifyResponseRedirect(HOME_LINK);
        verifyRequestNeverGetContext();
        verifyChainNeverDoFilter();
    }

    @ParameterizedTest
    @ValueSource(strings = {"***", "_______", "    ", ""})
    void forward_when_nickname_is_wrong_characters(String nickname) throws ServletException, IOException {
        whenDispatcher();
        when(request.getParameter(NICK_NAME)).thenReturn(nickname);

        super.doFilter();

        verifyContextGetDispatcher(INDEX_JSP);
        verifyDispatcherForward();
        verifyChainNeverDoFilter();
    }
}
