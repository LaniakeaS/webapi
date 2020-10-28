import exceptions.ParameterException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/user/profile/modify"})
public class ModifyUserInfoAPI extends HttpServlet {

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
        String nickName = request.getParameter("nickName");
        String password = request.getParameter("password");
        String introduceSign = request.getParameter("introduceSign");
        String age = request.getParameter("age");
        String phoneNumber = request.getParameter("phoneNumber");
        String gender = request.getParameter("gender");
        String avatar = request.getParameter("avatar");


        try {

            if (accountName == null || !Daemon.users.containsKey(accountName) || nickName == null || password == null ||
                    introduceSign == null || age == null || phoneNumber == null || gender == null)
                throw new ParameterException();

            User user = Daemon.users.get(accountName);
            user.changeInfo(nickName, MD5.getInstance().getMD5(password), introduceSign, Integer.parseInt(age),
                    phoneNumber, Gender.valueOf("gender"), avatar);
            out.println("{");
            out.println("    \"status\": 0");
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
