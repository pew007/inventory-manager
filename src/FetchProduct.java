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
 * Servlet implementation class FetchProduct
 */
@WebServlet("/FetchProduct")
public class FetchProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sku = request.getParameter("sku");
        Product product = Product.fetchProductFromDB(sku);
        HashMap<String, String> responseHash = new HashMap<String, String>();

        if (product != null) {
            responseHash.put("status", "OK");
            responseHash.put("vendor", product.getVendor());
            responseHash.put("category", product.getCategory());
            responseHash.put("productName", product.getProductName());
        } else {
            responseHash.put("status", "Error");
            responseHash.put("message", "Could not find product with SKU " + sku);
        }

        String jsonResponse = JsonBuilder.toJson(responseHash);

        response.setContentType("application/json");
        PrintWriter output = response.getWriter();
        output.println(jsonResponse);
        output.close();
    }
}
