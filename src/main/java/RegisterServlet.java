import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kovalenko.vika.PathsJsp.INDEX_JSP;

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
                .getRequestDispatcher(INDEX_JSP.getPath())
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newNickName = req.getParameter("nickName");
        if (!nickNameIsFree(newNickName)){
            String busyName = "Sorry, this nickname is already taken";
            req.setAttribute("wrongNickName", busyName);
            doGet(req, resp);
        }

        playerRepository.registerNewPlayer(newNickName);

        var session = req.getSession();
        int startQuestionId = 0;
        session.setAttribute("nickName", newNickName);
        session.setAttribute("questionID", startQuestionId);
        resp.sendRedirect("/quest");
    }

    private boolean nickNameIsFree(String name){
        return playerRepository
                .getPlayers()
                .keySet()
                .stream()
                .noneMatch(key -> key.equals(name));
    }
}