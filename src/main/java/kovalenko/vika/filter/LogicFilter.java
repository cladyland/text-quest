package kovalenko.vika.filter;

import kovalenko.vika.basis.Player;
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
import static kovalenko.vika.constant.AttributeConstant.CARD_ID;
import static kovalenko.vika.constant.AttributeConstant.PLAYER;
import static kovalenko.vika.constant.LinkConstant.HOME_LINK;
import static kovalenko.vika.constant.LinkConstant.QUEST_LINK;
import static kovalenko.vika.db.PathsJsp.START_JSP;

@Slf4j
@WebFilter(filterName = "LogicFilter", value = QUEST_LINK)
public class LogicFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("'Logic Filter' is initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        var session = httpRequest.getSession();
        var player = (Player) session.getAttribute(PLAYER);

        if (isNull(player)) {
            httpResponse.sendRedirect(HOME_LINK);
            return;
        }

        if (player.isNewcomer()) {
            player.setNewcomer(false);

            httpRequest
                    .getServletContext()
                    .getRequestDispatcher(START_JSP.toString())
                    .forward(servletRequest, servletResponse);
            return;

        }

        if (isNull(session.getAttribute(CARD_ID))) {
            Integer startCardId = 1;
            session.setAttribute(CARD_ID, startCardId);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("'Logic Filter' is destroyed");
    }
}
