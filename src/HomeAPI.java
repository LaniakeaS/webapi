import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

        try {

            Client client = new Client();

            //Local Host Address of service
            String responseContent = client.getLocation(ip);
            out.println("{");
            out.println("    \"location\":");
            out.println(LocationAPI.parseJSON(responseContent) + ",");

            // TODO 根据用户名从数据库读取
            out.println("    \"avatar\": ICON,");

            out.println("    \"toBuy\":");
            out.println("    [");

            // TODO 读取购物车中的商品，判断商品库存
            out.println("    ");

            out.println("    ],");
            out.println("    \"toSell\":");
            out.println("    [");

            // TODO 读取想卖清单中的商品
            out.println("    ");

            out.println("    ],");
            out.println("    \"discount\":");
            out.println("    [");

            // TODO 想买的物品打折数量
            out.println("    ");

            out.println("    ],");
            out.println("    \"popular\":");
            out.println("    [");

            // TODO 销量最高top10
            out.println("    ");

            out.println("    ],");
            out.println("    \"guess\":");
            out.println("    [");

            // TODO 推荐商品
            out.println("    ");

            out.println("    ],");
            out.println("}");

        } catch (Exception e) {

            e.printStackTrace();

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

}
