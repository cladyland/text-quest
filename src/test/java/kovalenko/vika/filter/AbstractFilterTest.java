package kovalenko.vika.filter;

import kovalenko.vika.AbstractFilterServletTest;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public abstract class AbstractFilterTest extends AbstractFilterServletTest {
    @Mock
    protected FilterConfig config;
    @Mock
    protected FilterChain chain;
    protected Filter filter;

    @Override
    protected void init() throws ServletException {
        filter.init(config);
    }

    @AfterEach
    public void destroy() {
        filter.destroy();
    }

    protected void doFilter() throws ServletException, IOException {
        filter.doFilter(request, response, chain);
    }

    protected void verifyChainDoFilter() throws ServletException, IOException {
        verify(chain, times(1)).doFilter(request, response);
    }

    protected void verifyChainNeverDoFilter() throws ServletException, IOException {
        verify(chain, never()).doFilter(request, response);
    }
}
