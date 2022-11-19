import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", value = "/")
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var currentSession = httpRequest.getSession();
        var httpResponse = (HttpServletResponse) servletResponse;

        if (currentSession.isNew()){
            httpResponse.sendRedirect("/register");
        } else {
          //  servletRequest.setAttribute();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
