import model.Product;
import util.JsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/SendInventory")
public class SendInventory extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HashMap<String, String> responseHash = new HashMap<String, String>();

        if (isValidSession(request, response)) {
            String sku = request.getParameter("sku");
            String date = request.getParameter("date");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            try {
                Product product = Product.fetchProductFromDB(sku);
                product.setSentDate(date);
                product.setQuantitySent(quantity);

                product.send();

                responseHash.put("status", "OK");
                responseHash.put("message", "Inventory sent!");

            } catch (Exception e) {
                e.printStackTrace();
                responseHash.put("status", "Error");
                responseHash.put("message", e.getMessage());
            }
        } else {
            responseHash.put("status", "Invalid");
        }

        String jsonResponse = JsonBuilder.toJson(responseHash);

        response.setContentType("application/json");
        PrintWriter output = response.getWriter();
        output.println(jsonResponse);
        output.close();
    }
}
