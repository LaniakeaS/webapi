import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = {"/wepapi/user/PutShoppingTrolley"})
public class PutOrderCart extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        try {
            Map<String, String[]> dataMap = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : dataMap.entrySet()) {
                String key = entry.getKey();
                String[] value = entry.getValue();

            }
        } catch (Exception e) {

            e.printStackTrace();
            out.println("{");
            out.println("    \"status\": -1,");
            out.println("    \"errMsg\": \"" + e.getMessage() + "\"");
            out.println("}");

        }
    }
}
