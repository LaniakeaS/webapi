import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/shop/addGood"})
public class AddGood extends HttpServlet {
    protected static IDGenerator idGenerator = new IDGenerator(3L, 3L);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        try {


            String userName = request.getParameter("accountName");
            String userId = HomeAPI.getUserId(userName);
            String shopId = getShopId(userName);
            String goodName = request.getParameter("goodName");
            String goodIntro = request.getParameter("goodIntro");
            String goodCategoryName = request.getParameter("goodCategoryName");
            String goodCategoryId = TestDatabaseAPI.runQuery("SELECT category_id" +
                    " FROM goods_category WHERE category_name = \""
                    + goodCategoryName + "\";").get(0).get(0);
            String goodCoverImg = request.getParameter("goodCoverImg");
            String goodDetailContent = request.getParameter("goodDetailContent");
            String originalPrice = request.getParameter("originalPrice");
            String sellingPrice = request.getParameter("sellingPrice");
            String stockNum = request.getParameter("stockNum");
            String tag = request.getParameter("tag");
            String goodsSellStatus = request.getParameter("goodsSellStatus");

            TestDatabaseAPI.runModify("insert into goods_info (" +
                    "goods_id, " +
                    "goods_name, " +
                    "goods_intro, " +
                    "goods_category_id, " +
                    "goods_cover_img, " +
                    //"goods_carousel, " +
                    "goods_detail_content, " +
                    "original_price, " +
                    "selling_price, " +
                    "shop_id, " +
                    "stock_num, " +
                    "tag, " +
                    "goods_sell_status, " +
                    "create_user, " +
                    "update_user)" +
                    " values (" +
                    "\"" + String.valueOf(idGenerator.nextId()) + "\", " +
                    "\"" + goodName + "\", " +
                    "\"" + goodIntro + "\", " +
                    goodCategoryId + ", " +
                    "\"" + goodCoverImg + "\", " +
                    "\"" + goodDetailContent + "\", " +
                    originalPrice + ", " +
                    sellingPrice + ", " +
                    "\"" + shopId + "\", " +
                    stockNum + ", " +
                    "\"" + tag + "\", " +
                    goodsSellStatus + ", " +
                    "\"" + userName + "\", " +
                    "\"" + userName + "\");");

            out.print("{\"status\": 0}");

        } catch (SQLException e) {
            out.println("{");
            out.println("    \"status\": -1,");
            out.println("    \"errMsg\": \"" + e.getMessage() + "\"");
            out.println("}");
        }


    }

    private String getShopId(String userName) throws SQLException {
        String userId = HomeAPI.getUserId(userName);
        return TestDatabaseAPI.runQuery("SELECT shopId FROM shop WHERE userId = \"" +
                userId + "\";").get(0).get(0);
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
