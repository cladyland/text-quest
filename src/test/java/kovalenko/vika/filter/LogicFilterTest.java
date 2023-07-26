package kovalenko.vika.filter;

import kovalenko.vika.common.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;

import static kovalenko.vika.common.constant.AttributeConstant.CARD_ID;
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.common.constant.LinkConstant.HOME_LINK;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogicFilterTest extends AbstractFilterTest {

    @Override
    @BeforeEach
    protected void init() throws ServletException {
        whenSession();
        filter = new LogicFilter();
        super.init();
    }

    @Test
    void redirect_to_home_page_when_player_is_null() throws ServletException, IOException {
        super.doFilter();

        verifyResponseRedirect(HOME_LINK);
        verify(session, never()).getAttribute(CARD_ID);
        verifyChainNeverDoFilter();
    }

    @Test
    void add_session_attribute_cardId_when_it_is_null() throws ServletException, IOException {
        int startCardId = 1;
        when(session.getAttribute(PLAYER)).thenReturn(Mockito.mock(Player.class));

        super.doFilter();

        verifyResponseNeverRedirect(HOME_LINK);
        verify(session, times(1)).setAttribute(CARD_ID, startCardId);
        verifyChainDoFilter();
    }

    @Test
    void not_add_session_attribute_cardId_when_it_is_not_null() throws ServletException, IOException {
        when(session.getAttribute(PLAYER)).thenReturn(Mockito.mock(Player.class));
        when(session.getAttribute(CARD_ID)).thenReturn(1);

        super.doFilter();

        verifyResponseNeverRedirect(HOME_LINK);
        verify(session, never()).setAttribute(CARD_ID, eq(anyInt()));
        verifyChainDoFilter();
    }
}
