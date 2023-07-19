package kovalenko.vika.filter;

import lombok.extern.slf4j.Slf4j;

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
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.common.constant.LinkConstant.HOME_LINK;
import static kovalenko.vika.common.constant.LinkConstant.QUEST_LINK;
import static kovalenko.vika.common.constant.PathsJsp.INDEX_JSP;

@Slf4j
@WebFilter(filterName = "AuthenticationFilter", value = HOME_LINK)
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("'Authentication Filter' is initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        var currentSession = ((HttpServletRequest) request).getSession();
        var player = currentSession.getAttribute(PLAYER);

        if (currentSession.isNew() || isNull(player)) {
            request
                    .getServletContext()
                    .getRequestDispatcher(INDEX_JSP.toString())
                    .forward(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect(QUEST_LINK);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("'Authentication Filter' is destroyed");
    }
}
