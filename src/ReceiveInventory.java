import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product;
import util.JsonBuilder;

/**
 * Servlet implementation class ReceiveInventory
 */
@WebServlet("/ReceiveInventory")
public class ReceiveInventory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sku = request.getParameter("sku");
        String date = request.getParameter("date");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        HashMap<String, String> responseHash = new HashMap<String, String>();

        try {
            Product product = Product.fetchProductFromDB(sku);
            product.setReceivedDate(date);
            product.setQuantityReceived(quantity);

            product.receive();

            responseHash.put("status", "OK");
            responseHash.put("message", "Inventory received!");

        } catch (Exception e) {
            e.printStackTrace();
            responseHash.put("status", "Error");
            responseHash.put("message", e.getMessage());
        }

        String jsonResponse = JsonBuilder.toJson(responseHash);

        response.setContentType("application/json");
        PrintWriter output = response.getWriter();
        output.println(jsonResponse);
        output.close();
    }
}
