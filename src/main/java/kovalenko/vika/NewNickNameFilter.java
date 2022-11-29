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
import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static kovalenko.vika.db.PathsJsp.INDEX_JSP;

@WebFilter(filterName = "NewNickNameFilter", value = "/register")
public class NewNickNameFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(NewNickNameFilter.class);

    private String wordlessNickName;
    private String underscoreName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        wordlessNickName = "Nickname can only contain letters, numbers and underscore symbol";
        underscoreName = "Nickname must contain at least one letter or numeric";

        LOG.info("'New Nick Name Filter' is initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String nickName = servletRequest.getParameter("nickName");

        if (isNull(nickName)) {
            var httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendRedirect("/");
            return;
        }

        if (!isWordCharacter(nickName)) {
            LOG.warn("Failed to register user '{}': nickname contains invalid characters", nickName);
            forwardWithWrongMessage(servletRequest, servletResponse, wordlessNickName);
            return;
        }
        if (isUnderscore(nickName)) {
            LOG.warn("Failed to register user '{}': nickname contains only underscore symbol", nickName);
            forwardWithWrongMessage(servletRequest, servletResponse, underscoreName);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        LOG.info("'New Nick Name Filter' is destroyed");
    }

    private boolean isWordCharacter(String word) {
        String regex = "\\w*";
        return Pattern.matches(regex, word);
    }

    private boolean isUnderscore(String word) {
        String regex = "\\_*";
        return Pattern.matches(regex, word);
    }

    private void forwardWithWrongMessage(ServletRequest request, ServletResponse response, String message) throws ServletException, IOException {
        var httpRequest = (HttpServletRequest) request;
        request.setAttribute("wrongNickName", message);

        httpRequest
                .getServletContext()
                .getRequestDispatcher(INDEX_JSP.toString())
                .forward(request, response);
    }
}
