import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Scott Piao
 * @author Hanzhong Qi<br>
 * E-Mail: <a href=mailto:17722018@bjtu.edu.cn>17722018@bjtu.edu.cn</a><br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
@WebServlet(urlPatterns = {"/user/login"})
public class LoginAPI extends HttpServlet {

    protected static Map<String, User> users = new HashMap<>();

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
        PrintWriter out = response.getWriter();

        try {

            String accountName = request.getParameter("accountName");
            String password = request.getParameter("password");
            User newUser = users.get(accountName);
            String isLoggedIn = "true";

            if (newUser == null) {

                newUser = User.login(accountName, password);
                users.put(accountName, newUser);
                isLoggedIn = "false";

            }

            out.println("{");
            out.println("    \"status\": 0,");
            out.println("    \"ID\": " + newUser.ID + ",");
            out.println("    \"accountName\": " + newUser.accountName + ",");
            out.println("    \"nickName\": " + newUser.nickName + ",");
            out.println("    \"lease\": null,");
            out.println("    \"isLoggedIn\": " + isLoggedIn);
            out.println("}");

        } catch (Exception e) {

            e.printStackTrace();
            out.println("{");
            out.println("    \"status\": -1,");
            out.println("    \"errMsg\": " + e.getMessage());
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

}
