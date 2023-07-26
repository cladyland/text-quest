package kovalenko.vika.servlet;

import kovalenko.vika.AbstractFilterServletTest;
import org.mockito.Mock;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import static org.mockito.Mockito.when;

public abstract class AbstractServletTest extends AbstractFilterServletTest {
    @Mock
    protected ServletConfig config;

    protected void init(HttpServlet servlet) throws ServletException {
        when(config.getServletContext()).thenReturn(context);
        servlet.init(config);
    }
}
