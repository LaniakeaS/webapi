import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/user/RequestShoppingTrolley"})
public class OrderCart extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("UserName");
        List<CartItem> cartItemList = new ArrayList<>();

        try {
            String userId = TestDatabaseAPI.runQuery("SELECT customer_id FROM customer WHERE login_name = \"" +
                    userName + "\";").get(0).get(0);
            List<List<String>> resultCart = TestDatabaseAPI.runQuery("SELECT cart_item_id, goods_id, goods_count, is_deleted" +
                    " FROM order_cart_item WHERE user_id = \"" + userId + "\";");
            List<List<String>> goodInfo = new ArrayList<>();
            for(List<String> list : resultCart) {
                String id = list.get(1);
                goodInfo.add(TestDatabaseAPI.runQuery("SELECT " +
                        "goods_name, " +
                        "goods_intro, " +
                        "goods_cover_img, " +
                        "original_price, " +
                        "selling_price FROM goods_info WHERE goods_id = \"" +
                        id + "\";").get(0));
            }

            for(int i = 0; i < resultCart.size(); i++) {
                double discount = Double.parseDouble(goodInfo.get(i).get(4)) / Double.parseDouble(goodInfo.get(i).get(3));
                cartItemList.add(new CartItem(resultCart.get(i).get(0),
                        goodInfo.get(i).get(0),
                        goodInfo.get(i).get(2),
                        goodInfo.get(i).get(1),
                        Integer.parseInt(goodInfo.get(i).get(3)),
                        Integer.parseInt(resultCart.get(i).get(2)),
                        discount));
            }

        } catch (SQLException e) {
            out.println("{");
            out.println("    \"status\": -1,");
            out.println("    \"errMsg\": \"" + e.getMessage() + "\"");
            out.println("}");
            return;
        }

        out.print(generateJSON(cartItemList));

        // TODO get cartInfo from database by userID

        // TODO generate json
    }

    private String generateJSON(List<CartItem> cartItemList) {
        StringBuffer json = new StringBuffer("{");
        json.append("\"goods\":[");
        for (CartItem cartItem : cartItemList) {
            json.append("{");

            json.append("\"id\":");
            json.append("\"");
            json.append(cartItem.getCartId());
            json.append("\"");
            json.append(",");

            json.append("\"name\":");
            json.append("\"");
            json.append(cartItem.getName());
            json.append("\"");
            json.append(",");

            json.append("\"picture\":");
            json.append("\"");
            json.append(cartItem.getPicture());
            json.append("\"");
            json.append(",");

            json.append("\"description\":");
            json.append("\"");
            json.append(cartItem.getDescription());
            json.append("\"");
            json.append(",");

            json.append("\"price\":");
            json.append(cartItem.getPrice());
            json.append(",");

            json.append("\"number\":");
            json.append(cartItem.getNumber());
            json.append(",");

            json.append("\"discount\":");
            json.append(cartItem.getDiscount());
            json.append(",");

            json.append("},");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("]}");
        return json.toString();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        processRequest(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
