/**
 * @author Qi, Hanzhong<br>
 * E-Mail: 17722018@bjtu.edu.cn<br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
public class Address {

    protected int ID;
    protected String country, province, city, countyOrDistrict, detail;


    protected Address(String country, String province, String city, String countyOrDistrict, String detail) {

        this.country = country;
        this.province = province;
        this.city = city;
        this.countyOrDistrict = countyOrDistrict;
        this.detail = detail;

    }

}
