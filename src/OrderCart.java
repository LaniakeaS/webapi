import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/wepapi/user/RequestShoppingTrolley"})
public class OrderCart extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("UserName");
        // TODO get userId from database by userName

        // TODO get cartInfo from database by userID

        // TODO generate json
    }

    private String generateJSON(List<OrderCartItem> orderCartItemList) {
        StringBuffer json = new StringBuffer("{");
        json.append("\"goods\":[");
        for(int i = 1; i <= orderCartItemList.length(); i++) {
            json.append("{");

            json.append("\"id\":");
            json.append(i);
            json.append(",");

            json.append("\"name\":");
            //
            json.append(",");

            json.append("\"picture\":");
            //
            json.append(",");

            json.append("\"description\":");
            //
            json.append(",");

            json.append("\"price\":");
            //
            json.append(",");

            json.append("\"number\":");
            //
            json.append(",");

            json.append("\"discount\":");
            //
            json.append(",");

            json.append("},");
        }
        json.append("]}");
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
