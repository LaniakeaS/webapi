import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A class to store shops information in search pages
 *
 * @author Chen Yuetao
 */
public class SearchedShop {
    private final String shopId;
    private final String shopName;
    private final int auditStatus;
    private final int status;
    private final String phone;
    private final String address;
    private final LocalTime openTime;
    private final LocalTime endTime;
    private final String instruction;
    private final LocalTime createTime;
    private final String shopHead;
    private final String userId;

    /**
     * Constructor.
     * @param shopId Shop's id
     * @param shopName Shop's name
     * @param auditStatus Shop's audit status
     * @param status Shop's status
     * @param phone Shop's phone number
     * @param address Shop's address
     * @param openTime Shop's open time
     * @param endTime Shop's end time
     * @param instruction Shop's instruction
     * @param createTime Shop's create time
     * @param shopHead Shop's head
     * @param userId User's id
     */
    public SearchedShop(String shopId,
                        String shopName,
                        int auditStatus,
                        int status,
                        String phone,
                        String address,
                        LocalTime openTime,
                        LocalTime endTime,
                        String instruction,
                        LocalTime createTime,
                        String shopHead,
                        String userId) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.auditStatus = auditStatus;
        this.status = status;
        this.phone = phone;
        this.address = address;
        this.openTime = openTime;
        this.endTime = endTime;
        this.instruction = instruction;
        this.createTime = createTime;
        this.shopHead = shopHead;
        this.userId = userId;
    }

    /**
     * Generate the JSON string.
     * @return JSON string
     */
    public String generateJSON() {
        StringBuffer result = new StringBuffer("{");

        result.append("\"shop_id\":");
        result.append("\"").append(shopId).append("\",");

        result.append("\"shop_name\":");
        result.append("\"").append(shopName).append("\",");

        result.append("\"audit_status\":");
        result.append(auditStatus);
        result.append(",");

        result.append("\"status\":");
        result.append(status);
        result.append(",");

        result.append("\"phone\":");
        result.append("\"").append(phone).append("\",");

        result.append("\"address\":");
        result.append("\"").append(address).append("\",");

        result.append("\"open_time\":");
        result.append("\"").append(openTime.format(DateTimeFormatter.ISO_LOCAL_TIME)).append("\",");

        result.append("\"end_time\":");
        result.append("\"").append(endTime.format(DateTimeFormatter.ISO_LOCAL_TIME)).append("\",");

        result.append("\"instruction\":");
        result.append("\"").append(instruction).append("\",");

        result.append("\"create_time\":");
        result.append("\"").append(createTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\",");

        result.append("\"shop_head\":");
        result.append("\"").append(shopHead).append("\",");

        result.append("\"user_id\":");
        result.append("\"").append(userId).append("\",");

        return result.append("}").toString();
    }
}
