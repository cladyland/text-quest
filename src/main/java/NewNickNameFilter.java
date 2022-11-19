import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "NewNickNameFilter", value = "/regis")
public class NewNickNameFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String nickName = servletRequest.getParameter("nickName");

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
