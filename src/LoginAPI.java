import exceptions.ParameterException;

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
@WebServlet(urlPatterns = {"/user/login"})
public class LoginAPI extends HttpServlet {

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

        if (!Daemon.getInstance().isAlive())
            Daemon.getInstance().start();

        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        try {

            String accountName = request.getParameter("accountName");
            String password = request.getParameter("password");

            if (accountName == null || password == null)
                throw new ParameterException();

            User newUser = User.login(accountName, password);
            String wasLoggedIn = newUser.isLoggedIn;

            // set login status to 0
            if (newUser.isLoggedIn.equals("1")) {

                TestDatabaseAPI.runModify("update customer set is_status = 0 where login_name = \"" + accountName +
                        "\";");
                newUser.isLoggedIn = "0";

            }

            out.println("{");
            out.println("    \"status\": 0,");
            out.println("    \"ID\": \"" + newUser.ID + "\",");
            out.println("    \"accountName\": \"" + newUser.accountName + "\",");
            out.println("    \"nickName\": \"" + newUser.nickName + "\",");
            out.println("    \"lease\": \"null\",");
            out.println("    \"isLoggedIn\": \"" + wasLoggedIn + "\"");
            out.println("}");

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

}
