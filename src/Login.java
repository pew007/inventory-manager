import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import util.PasswordUtilities;

@WebServlet("/Login")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String redirectUrl;

        request.getSession();
        if (PasswordUtilities.isValidLogin(username, password)) {
            redirectUrl = "jsp/main.jsp";
            Cookie cookie = new Cookie("jadrn048", username);
            cookie.setMaxAge(60 * 30);
            response.addCookie(cookie);
            response.sendRedirect(redirectUrl);
        } else {
            redirectUrl = "jsp/error.jsp";
            request.setAttribute("errorMessage", "Invalid username or password. Please log in again.");
            request.getRequestDispatcher(redirectUrl).forward(request, response);
        }
    }
}
