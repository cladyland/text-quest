package kovalenko.vika;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;
import static kovalenko.vika.db.PathsJsp.INDEX_JSP;

@WebFilter(filterName = "AuthenticationFilter", value = "/")
public class AuthenticationFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        LOG.info("'Authentication Filter' is initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        var currentSession = httpRequest.getSession();
        var playerNickName = currentSession.getAttribute("nickName");

        if (currentSession.isNew() || isNull(playerNickName)) {
            httpRequest
                    .getServletContext()
                    .getRequestDispatcher(INDEX_JSP.toString())
                    .forward(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect("/quest");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        LOG.info("'Authentication Filter' is destroyed");
    }
}
