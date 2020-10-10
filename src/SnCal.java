import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * <h3>Description:</h3>
 * An algorithm from Baidu to get signature.<br>
 * <br>
 * @author Baidu
 */
public class SnCal {

    public static void main(String[] args) {

        SnCal snCal = new SnCal();
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("ip", "112.54.81.12");
        paramsMap.put("ak", "dfDq2fzs2yesYRtNK0LQwKjgIHWlIHxw");
        String paramsStr = snCal.toQueryString(paramsMap);
        String wholeStr = "/location/ip?" + paramsStr + "qEAFQdGzU6lDQwZe3dwkipXShqjFQK6u";
        String tempStr = URLEncoder.encode(wholeStr, StandardCharsets.UTF_8);
        System.out.println(snCal.MD5(tempStr));

    }


    protected String toQueryString(Map<?, ?> data) {

        StringBuilder queryString = new StringBuilder();

        for (Entry<?, ?> pair : data.entrySet()) {

            queryString.append(pair.getKey()).append("=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    StandardCharsets.UTF_8)).append("&");

        }
        if (queryString.length() > 0)
            queryString.deleteCharAt(queryString.length() - 1);

        return queryString.toString();

    }


    protected String MD5(String md5) {

        try {

            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : array)
                sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);

            return sb.toString();

        } catch (java.security.NoSuchAlgorithmException ignored) {
        }

        return null;

    }
}