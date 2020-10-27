/**
 * @author Qi, Hanzhong<br>
 * E-Mail: 17722018@bjtu.edu.cn<br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
public class Address {

    protected String ID, province, city, countyOrDistrict, detail, consignee, phoneNum, status;


    protected Address(String ID, String province, String city, String countyOrDistrict, String detail,
                      String consignee, String phoneNum, String status) {

        this.ID = ID;
        this.province = province;
        this.city = city;
        this.countyOrDistrict = countyOrDistrict;
        this.detail = detail;
        this.consignee = consignee;
        this.phoneNum = phoneNum;
        this.status = status;

    }

}
