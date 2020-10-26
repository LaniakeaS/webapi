import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/good"})
public class GoodDetail extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        try {
            String goodsId = request.getParameter("ProdID");
            String token = request.getParameter("token");

            // TODO get info from db
            String goodsName;
            String goodsCategoryId;
            String goodsCategory;
            String goodsIntro;
            String goodsCoverImg;
            String goodsCarousel;
            String goodsDetailContent;
            int originalPrice;
            int sellingPrice;
            int stockNum;
            String tag;

            // Generate the JSON
            StringBuffer json = new StringBuffer();
            json.append("{");
            json.append("");

        } catch (Exception e) {

        }
    }


}
