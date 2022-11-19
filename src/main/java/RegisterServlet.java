import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private PlayerRepository playerRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        var servletContext = config.getServletContext();
        playerRepository = (PlayerRepository) servletContext.getAttribute("playerRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request
                .getServletContext()
                .getRequestDispatcher("/WEB-INF/index.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickName = req.getParameter("nickName");
        playerRepository.registerNewPlayer(nickName);

        var session = req.getSession();
        session.setAttribute("nickName", nickName);
        req.setAttribute("nickName", nickName);

        req.getRequestDispatcher("/WEB-INF/index2.jsp").forward(req, resp);
    }
}
