package kovalenko.vika;

import kovalenko.vika.basis.Player;

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
import static kovalenko.vika.PathsJsp.START_JSP;

@WebFilter(filterName = "LogicFilter", value = "/quest")
public class LogicFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        var session = httpRequest.getSession();
        var player = (Player) session.getAttribute("player");

        if (isNull(player)){
            httpResponse.sendRedirect("/");
            return;
        }

        if (player.isNewcomer()){
            player.setNewcomer(false);

            httpRequest
                    .getServletContext()
                    .getRequestDispatcher(START_JSP.toString())
                    .forward(servletRequest, servletResponse);

        }

        if (isNull(session.getAttribute("cardID"))){
            Integer startCardId = 1;
            session.setAttribute("cardID", startCardId);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
