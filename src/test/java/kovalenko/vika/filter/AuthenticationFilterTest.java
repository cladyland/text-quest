package kovalenko.vika.filter;

import kovalenko.vika.common.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;

import static kovalenko.vika.common.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.common.constant.LinkConstant.QUEST_LINK;
import static kovalenko.vika.common.constant.PathsJsp.INDEX_JSP;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthenticationFilterTest extends AbstractFilterTest {

    @Override
    @BeforeEach
    protected void init() throws ServletException {
        whenSession();
        filter = new AuthenticationFilter();
        super.init();
    }

    @Test
    void redirect_when_player_is_registered() throws ServletException, IOException {
        when(session.getAttribute(PLAYER)).thenReturn(Mockito.mock(Player.class));

        super.doFilter();

        verifyRequestNeverGetContext();
        verifyResponseRedirect(QUEST_LINK);
    }

    @Test
    void forward_to_index_when_new_session() throws ServletException, IOException {
        whenDispatcher();
        when(session.isNew()).thenReturn(true);

        super.doFilter();
        verifyForwardToIndex();
    }

    @Test
    void forward_to_index_when_player_is_null() throws ServletException, IOException {
        whenDispatcher();
        when(session.isNew()).thenReturn(false);

        super.doFilter();
        verifyForwardToIndex();
    }

    private void verifyForwardToIndex() throws IOException, ServletException {
        verifyContextGetDispatcher(INDEX_JSP);
        verifyDispatcherForward();
        verify(response, never()).sendRedirect(anyString());
    }
}
