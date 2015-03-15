import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.PasswordUtilities;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String redirectUrl;

        if (PasswordUtilities.isValidLogin(username, password)) {
            redirectUrl = "jsp/main.jsp";
            request.getSession(true);
        } else {
            redirectUrl = "jsp/error.jsp";
            request.setAttribute("errorMessage",
                    "Invalid username or password. Please log in again.");
        }

        request.getRequestDispatcher(redirectUrl).forward(request, response);
    }
}
