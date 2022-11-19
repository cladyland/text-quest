import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

@WebFilter(filterName = "NewNickNameFilter", value = "/register")
public class NewNickNameFilter implements Filter {
    private String wrongNickName;
    private String underscoreName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        wrongNickName = "Nickname can only contain letters, numbers and underscore symbol";
        underscoreName = "Nickname must contain at least one letter";
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String nickName = servletRequest.getParameter("nickName");

        if (!isWordCharacter(nickName)) {
            forwardWithWrongMessage(servletRequest, servletResponse, wrongNickName);
        }
        if (isUnderscore(nickName)) {
            forwardWithWrongMessage(servletRequest, servletResponse, underscoreName);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
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
                .getRequestDispatcher("/WEB-INF/index.jsp")
                .forward(request, response);
    }
}
