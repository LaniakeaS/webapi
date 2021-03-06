import exceptions.*;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Scott Piao
 * @author Hanzhong Qi<br>
 * E-Mail: <a href=mailto:17722018@bjtu.edu.cn>17722018@bjtu.edu.cn</a><br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
@WebServlet(urlPatterns = {"/user/register"})
public class RegisterAPI extends HttpServlet {

    protected static IDGenerator idGenerator = new IDGenerator(5L, 3L);

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

        try {

            String accountName = request.getParameter("accountName");
            String nickName = request.getParameter("nickName");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String phoneNumber = request.getParameter("phoneNumber");
            int age = Integer.parseInt(request.getParameter("age"));

            String genderString = request.getParameter("gender");
            if (genderString == null)
                genderString = "secret";
            Gender gender = Gender.valueOf(genderString);

            String introduceSign = request.getParameter("introduceSign");
            if (introduceSign == null)
                introduceSign = "";

            String identityNum = request.getParameter("identityNum");
            if (identityNum == null)
                identityNum = "";

            User user = new User(age, phoneNumber, name, identityNum, accountName, nickName, password, gender,
                    introduceSign, String.valueOf(idGenerator.nextId()));
            User.register(user);

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
     * Handles the HTTP <code>POST</code> method.<br>
     * <br>
     * @param request servlet request
     * @param response servlet response<br>
     * <br>
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        processRequest(request, response);

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