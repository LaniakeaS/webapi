import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <h3>Description:</h3>
 * This class provide interfaces to accomplish several operations about users.<br>
 * <br>
 * @author Scott Piao
 * @author Hanzhong Qi<br>
 * E-Mail: <a href=mailto:17722018@bjtu.edu.cn>17722018@bjtu.edu.cn</a><br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
@WebServlet(urlPatterns = {"/webapi/geo/ip"})
public class LocationAPI extends HttpServlet {

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

            Client client = new Client();

            //Local Host Address of service
            String ip = request.getParameter("ip");
            String responseContent = client.getLocation(ip);
            out.println("{");
            out.println("    \"ip\": " + ip + ",");
            out.println("    \"location\":");
            out.println(parseJSON(responseContent));
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


    /**
     * Returns a short description of the servlet.<br>
     * <br>
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {

        return "Short description";

    }


    /**
     * This method is for extracting a string in a format of json to get location detail of ip.<br>
     * <br>
     * @param json a json string<br>
     * <br>
     * @return a string
     */
    protected String parseJSON(String json) {

        JSONObject jsonObject = JSONObject.fromObject(json);
        JSONObject content = jsonObject.getJSONObject("content");
        JSONObject detail = content.getJSONObject("address_detail");
        return ("    {\n" + "        \"city\": " + detail.getString("city") + ",\n        \"province\": " +
                detail.getString("province") + "\n    }");

    }

}