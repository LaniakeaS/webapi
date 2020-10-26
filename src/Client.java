import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * <h3>Description:</h3>
 * This class will use webapi of Baidu to get the location of ip address.<br>
 * <br>
 * @author Hanzhong Qi<br>
 * E-Mail: <a href=mailto:17722018@bjtu.edu.cn>17722018@bjtu.edu.cn</a><br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
public class Client {

    protected String getLocation(String IP) {

        try {

            /* get sn */
            SnCal snCal = new SnCal();
            String ak = "dfDq2fzs2yesYRtNK0LQwKjgIHWlIHxw";
            String sk = "qEAFQdGzU6lDQwZe3dwkipXShqjFQK6u";
            Map<String, String> paramsMap = new LinkedHashMap<>();
            paramsMap.put("ip", IP);
            paramsMap.put("ak", ak);
            String paramsStr = snCal.toQueryString(paramsMap);
            String wholeStr = "/location/ip?" + paramsStr + sk;
            String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
            String sn = snCal.MD5(tempStr);
            System.out.println(sn);
            /* get sn */

            //Create URL instance with Web Service Address
            URL url = new URL("http://api.map.baidu.com/location/ip?ip=" + IP + "&ak=" + ak + "&sn=" + sn);

            //Connect to the Web Service
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Setting service request mode to GET.
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            //Get reply from the sevice
            conn.getInputStream();
            InputStream content = conn.getInputStream();

            //Convert input string into byte array
            byte[] buf = new byte[1024];
            ByteArrayOutputStream sb = new ByteArrayOutputStream();
            int i;

            while ((i = content.read(buf)) != -1)
                sb.write(buf, 0, i);

            //Close the Web Serive connection
            content.close();

            //Convert the byte data into String
            return sb.toString();

        } catch (IOException ex) {

            return null;

        }

    }

}
