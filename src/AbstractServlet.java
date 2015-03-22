import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AbstractServlet")
public class AbstractServlet extends HttpServlet {

    protected boolean isValidSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cookieValue = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jadrn048")) {
                    cookieValue = cookie.getValue();
                }
            }
        }

        return cookieValue != null;
    }
}
