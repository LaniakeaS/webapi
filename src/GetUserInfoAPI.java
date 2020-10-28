import exceptions.ParameterException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/user/profile/get"})
public class GetUserInfoAPI extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        String accountName = request.getParameter("accountName");

        try {

            if (accountName == null || !Daemon.users.containsKey(accountName))
                throw new ParameterException();

            User user = Daemon.users.get(accountName);
            String nickName = user.nickName;
            String introduceSign = user.introduceSign;
            String name = user.name;
            String identityNum = user.identityNum;
            String phoneNumber = user.phoneNumber;
            String gender = user.gender.toString();
            int age = user.age;
            out.println("{");
            out.println("    \"status\": 0,");
            out.println("    \"accountName\": \"" + accountName + "\",");
            out.println("    \"nickName\": \"" + nickName + "\",");
            out.println("    \"introduceSign\": \"" + introduceSign + "\",");
            out.println("    \"name\": \"" + name + "\",");
            out.println("    \"identityNum\": " + identityNum + "\",");
            out.println("    \"phoneNumber\": " + phoneNumber + "\",");
            out.println("    \"gender\": " + gender + "\",");
            out.println("    \"age\": " + age);
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
