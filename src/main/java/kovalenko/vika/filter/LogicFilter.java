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
import static kovalenko.vika.common.constant.AttributeConstant.CARD_ID;
import static kovalenko.vika.common.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.common.constant.LinkConstant.HOME_LINK;
import static kovalenko.vika.common.constant.LinkConstant.QUEST_LINK;

@Slf4j
@WebFilter(filterName = "LogicFilter", value = QUEST_LINK)
public class LogicFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("'Logic Filter' is initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var session = ((HttpServletRequest) request).getSession();
        var player = session.getAttribute(PLAYER);

        if (isNull(player)) {
            ((HttpServletResponse) response).sendRedirect(HOME_LINK);
            return;
        }

        if (isNull(session.getAttribute(CARD_ID))) {
            Integer startCardId = 1;
            session.setAttribute(CARD_ID, startCardId);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("'Logic Filter' is destroyed");
    }
}
