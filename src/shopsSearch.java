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
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        List<SearchedShop> searchedShopList = new ArrayList<>();

        try {
            String searchName = request.getParameter("searchName");
            List<List<String>> searchResultList = TestDatabaseAPI.runQuery("SELECT " +
                    "shopId, " +
                    "shopName, " +
                    "auditStatus, " +
                    "status, " +
                    "phone, " +
                    "address, " +
                    "openTime, " +
                    "endTime, " +
                    "instruction, " +
                    "createTime, " +
                    "shopHead, " +
                    "userId" +
                    " FROM shop WHERE shopName = \"%" + searchName + "%\";");
            for(List<String> searchResult : searchResultList) {
                searchedShopList.add(new SearchedShop(
                        searchResult.get(0),
                        searchResult.get(1),
                        Integer.parseInt(searchResult.get(2)),
                        Integer.parseInt(searchResult.get(3)),
                        searchResult.get(4),
                        searchResult.get(5),
                        searchResult.get(6),
                        searchResult.get(7),
                        searchResult.get(8),
                        searchResult.get(9),
                        searchResult.get(10),
                        searchResult.get(11)
                ));
            }


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

    /**
     * Handles the HTTP <code>GET</code> method.<br>
     * <br>
     * @param request servlet request
     * @param response servlet response<br>
     * <br>
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        processRequest(request, response);

    }
}
