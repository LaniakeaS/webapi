import exceptions.ParameterException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author Scott Piao
 * @author Hanzhong Qi<br>
 * E-Mail: <a href=mailto:17722018@bjtu.edu.cn>17722018@bjtu.edu.cn</a><br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
@WebServlet(urlPatterns = {"/user/home"})
public class HomeAPI extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.<br>
     * <br>
     * @param request servlet request
     * @param response servlet response<br>
     * <br>
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String ip = request.getRemoteAddr();
        String accountName = request.getParameter("accountName");

        PrintWriter out = response.getWriter();
        StringBuffer json = new StringBuffer();
        try {

            if (accountName == null)
                throw new ParameterException();
            
            Client client = new Client();

            //Local Host Address of service
            String responseContent = client.getLocation(ip);
            json.append("{");
            json.append("\"location\":");
            json.append(LocationAPI.parseJSON(responseContent)).append(",");

            // TODO 根据用户名从数据库读取
            json.append("    \"avatar\": \"ICON\",");

            json.append("    \"toBuy\":");
            json.append("    [");

            // TODO 读取购物车中的商品，判断商品库存
            json.append("    ");

            json.append("    ],");

            // 已完成部分
            // To sell
            json.append("\"toSell\":");
            json.append(getToSellGoodsNum(accountName));
            json.append(",");


            // Number of discount
            json.append("\"discount\":");
            json.append(getDiscountNum(accountName));
            json.append(",");

            // Top 10 popular
            json.append("\"popular\":");
            List<String> topTen = getTopGoods();
            json.append("[");
            for(int i = 0; i < 10; i++) {
                json.append("\"").append(topTen.get(i)).append("\",");
            }
            json.deleteCharAt(json.length() - 1);
            json.append("]");

            json.append("}");
            out.print(json);

        } catch (Exception e) {

            e.printStackTrace();
            out.println("{");
            out.println("    \"status\": -1,");
            out.println("    \"errMsg\": \"" + e.getMessage() + "\"");
            out.println("}");

        }

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

    /**
     * Get the popular goods list.
     * @return The popular goods list.
     * @throws SQLException MySQL exception
     */
    private List<String> getTopGoods() throws SQLException {
        List<List<String>> orderItem = TestDatabaseAPI.runQuery("SELECT goods_name, goods_count FROM order_item_id");
        Map<String, Integer> temp = new HashMap<>();
        for(List<String> outList : orderItem) {
            if (temp.containsKey(outList.get(0))) {
                temp.put(outList.get(0), temp.get(outList.get(0)) + Integer.parseInt(outList.get(1)));
            } else {
                temp.put(outList.get(0), Integer.parseInt(outList.get(1)));
            }
        }
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
        Stream<Map.Entry<String, Integer>> st = temp.entrySet().stream();
        st.sorted(Map.Entry.comparingByValue()).forEach(e -> linkedHashMap.put(e.getKey(), e.getValue()));
        // st.sorted(Comparator.comparing(e -> e.getValue())).forEach(e -> result.put(e.getKey(), e.getValue()));
        
        List<String> result = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
            result.add(entry.getKey());
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Get user's id from his or her name.
     * @param userName User's name
     * @return User's id
     * @throws SQLException MySQL exception
     */
    private static String getUserId(String userName) throws SQLException {
        return TestDatabaseAPI.runQuery("SELECT customer_id FROM customer WHERE login_name = \"" +
                userName + "\";").get(0).get(0);
    }

    /**
     * Get the number of discount goods in the user's trolley.
     * @param userName User's name
     * @return Number of discount goods in the user's trolley
     * @throws SQLException MySQL exception
     */
    private int getDiscountNum(String userName) throws SQLException {
        String userId = getUserId(userName);
        int disNum = 0;
        List<List<String>> cartTable = TestDatabaseAPI.runQuery("SELECT goods_id" +
                " FROM order_cart_item WHERE user_id = \"" + userId + "\";");
        List<String> cartGoodsIdList = new ArrayList<>();
        for(List<String> temp : cartTable) {
            cartGoodsIdList.add(temp.get(0));
        }
        for(String goodsId : cartGoodsIdList) {
            List<String> radio = TestDatabaseAPI.runQuery("SELECT original_price, selling price" +
                    " FROM goods_info WHERE goods_id = \"" + goodsId + "\";").get(0);
            if(!radio.get(0).equals(radio.get(1))) {
                disNum++;
            }
        }
        return disNum;
    }

    private int getToSellGoodsNum(String userName) throws SQLException {
        String userId = getUserId(userName);
        String shopId = TestDatabaseAPI.runQuery("SELECT shopId FROM shop WHERE userId = \"" +
                userId + "\";").get(0).get(0);
        List<List<String>> userGoods = TestDatabaseAPI.runQuery("SELECT stock_num FROM goods_info WHERE shop_id = \"" +
                shopId + "\";");
        int num = 0;
        for(List<String> temp : userGoods) {
            num += Integer.parseInt(temp.get(0));
        }
        return num;
    }

}
