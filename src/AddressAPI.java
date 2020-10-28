import exceptions.ParameterException;
import exceptions.UserAPIException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.util.Iterator;
import java.util.List;


@WebServlet(urlPatterns = {"/user/profile/address"})
public class AddressAPI extends HttpServlet {

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
            String option = request.getParameter("option");
            String province = request.getParameter("province");
            String city = request.getParameter("city");
            String county = request.getParameter("county");
            String detail = request.getParameter("detail");
            String consignee = request.getParameter("consignee");
            String phoneNumber = request.getParameter("phoneNumber");
            String status = request.getParameter("status");
            String ID = request.getParameter("ID");

            if (accountName == null || option == null)
                throw new ParameterException();

            User user = Daemon.users.get(accountName);

            if (user == null)
                throw new ParameterException();

            switch (option) {

                case "add":
                    if (province == null || city == null || consignee == null || phoneNumber == null ||
                            status == null || detail == null)
                        throw new ParameterException();

                    if (county == null)
                        county = "";

                    user.addAddress(province, city, county, detail, consignee, phoneNumber, status);
                    break;
                case "delete":
                    if (ID == null)
                        throw new ParameterException();

                    user.deleteAddress(ID);
                    break;
                case "modify":
                    if (ID == null || province == null || city == null || consignee == null || phoneNumber == null ||
                            status == null || detail == null)
                        throw new ParameterException();

                    if (county == null)
                        county = "";

                    user.modifyAddress(ID, province, city, county, detail, consignee, phoneNumber, status);
                    break;
                case "get":
                    StringBuilder json = new StringBuilder();
                    json.append("{\n    \"status\": 0,\n    \"addresses\": \n    [\n");
                    List<List<String>> result = TestDatabaseAPI.runQuery(
                            "select * from customer_address where userId = \"" + user.ID + "\";");
                    ResultSetMetaData metaData = TestDatabaseAPI.getResultSet(
                            "select * from customer_address where userId = \"" + user.ID + "\";").getMetaData();
                    Iterator<List<String>> iterator = result.iterator();
                    List<String> key = TestDatabaseAPI.getColumnNames(metaData);

                    if (key == null)
                        throw new UserAPIException("Database Error");

                    while (iterator.hasNext()) {

                        List<String> row = iterator.next();
                        Iterator<String> iterator1 = row.iterator();
                        Iterator<String> keyIterator = key.iterator();
                        json.append("        {\n");

                        while (iterator1.hasNext()) {

                            String keyString = keyIterator.next();
                            json.append("            \"").append(keyString);

                            if (keyString.equals("status"))
                                json.append("\": ").append(iterator1.next());
                            else
                                json.append("\": \"").append(iterator1.next()).append("\"");

                            if (iterator1.hasNext())
                                json.append(",\n");

                        }

                        json.append("\n        }");

                        if (iterator.hasNext())
                            json.append(",\n");

                    }

                    json.append("\n    ]\n}");
                    out.print(json.toString());
                    return;
                default:
                    throw new ParameterException();

            }

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
