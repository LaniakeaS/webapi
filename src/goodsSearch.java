import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/search"})
public class goodsSearch extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        List<SearchedGood> searchedGoodsList = new ArrayList<>();

        try {
            String searchName = request.getParameter("searchName");
            /*
            Get info from database.
             */

            out.print(processJSON(searchedGoodsList));

        } catch (Exception e) {
            out.println("{");
            out.println("    \"status\": -1,");
            out.println("    \"errMsg\":\"" + e.getMessage() + "\"");
            out.println("}");
        }
    }

    private String processJSON(List<SearchedGood> searchedGoodsList) {
        StringBuilder json = new StringBuilder("{");
        json.append("\"status\": 0," +
                "\"goods\": [");
        for (SearchedGood searchedGood : searchedGoodsList) {
            json.append(searchedGood.generateJSON());
            json.append(",");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("]}");
        return  json.toString();
    }
}
