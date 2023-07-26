package kovalenko.vika;

import kovalenko.vika.service.PlayerService;
import kovalenko.vika.service.QuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static kovalenko.vika.common.constant.AttributeConstant.PLAYER_SERVICE;
import static kovalenko.vika.common.constant.AttributeConstant.QUEST_SERVICE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppContextListenerTest {
    @Mock
    ServletContextEvent event;
    @Mock
    ServletContext context;
    AppContextListener listener;

    @BeforeEach
    void init() {
        listener = new AppContextListener();
    }

    @Test
    void context_initialized() {
        when(event.getServletContext()).thenReturn(context);

        listener.contextInitialized(event);

        verify(context, times(1)).setAttribute(eq(PLAYER_SERVICE), any(PlayerService.class));
        verify(context, times(1)).setAttribute(eq(QUEST_SERVICE), any(QuestService.class));
    }
}
