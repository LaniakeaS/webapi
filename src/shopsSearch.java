import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/shopsSearch"})
public class shopsSearch extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        List<SearchedShop> searchedShopList = new ArrayList<>();

        try {
            String searchName = request.getParameter("searchName");
            /*

             */
            out.print(processJSON(searchedShopList));
        } catch (Exception e) {
            out.println("{");
            out.println("    \"status\": -1,");
            out.println("    \"errMsg\":\"" + e.getMessage() + "\"");
            out.println("}");
        }
    }

    private String processJSON(List<SearchedShop> searchedShopList) {
        StringBuilder json = new StringBuilder("{");
        json.append("\"status\": 0," +
                "\"shops\": [");
        for (SearchedShop searchedShop : searchedShopList) {
            json.append(searchedShop.generateJSON());
            json.append(",");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("]}");
        return  json.toString();
    }
}
