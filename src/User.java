import exceptions.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * <h3>Description:</h3>
 * A class for describing the basic information of users.<br>
 * <br>
 * <br>
 * @author Qi, Hanzhong<br>
 * E-Mail: <a href=mailto:17722018@bjtu.edu.cn>17722018@bjtu.edu.cn</a><br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
public class User {

    protected String isLoggedIn;
    protected Gender gender;
    protected int age, money, level;
    protected Map<String, Address> addresses;
    protected Map<String, Order> orders;
    protected String accountName, name, password, phoneNumber, nickName, introduceSign, identityNum, ID, creatTime;
    protected File avatar;


    /**
     * Constructor to create a new user.<br>
     * <br>
     * @param age user age
     * @param phoneNumber phone number of the user
     * @param name user's real name
     * @param accountName user's account name (will be used while login, should be exclusive)
     * @param password password for verifying.
     * @param gender male, female or secret.
     */
    protected User(int age, String phoneNumber, String name, String identityNum, String accountName, String nickName,
                   String password, Gender gender, String introduceSign, String ID) {

        this.age = age;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.accountName = accountName;
        this.password = password;
        this.nickName = nickName;
        this.introduceSign = introduceSign;
        this.gender = gender;
        this.identityNum = identityNum;
        this.ID = ID;
        this.money = 0;
        addresses = new HashMap<>();
        orders = new HashMap<>();
        avatar = new File("META_INF/avatar/" + ID + ".jpg");

    }


    /**
     * @param accountName account name
     * @param password password<br>
     * <br>
     * <br>
     * @return return a User that just has been authorized<br>
     * <br>
     * <br>
     * @exception AccountNotRegisteredException Account has not been registered.
     * @exception PasswordIncorrectException Incorrect password.
     */
    protected static User login(String accountName, String password)
            throws AccountNotRegisteredException, PasswordIncorrectException, SQLException {

        // TODO 改为正式数据库接口
        if (!TestDatabaseAPI.isAccountNameExist(accountName))
            throw new AccountNotRegisteredException();

        // TODO 改为正式数据库接口
        if (!TestDatabaseAPI.isPasswordCorrect(accountName, MD5.getInstance().getMD5(password)))
            throw new PasswordIncorrectException();

        // extract information from database
        String ID = TestDatabaseAPI.runQuery("select customer_id from customer where login_name = \"" +
                accountName + "\";").get(0).get(0);
        List<String> result = TestDatabaseAPI.runQuery("select * from customer " +
                "where customer_id = \"" + ID + "\";").get(0);
        List<String> resultInfo = TestDatabaseAPI.runQuery("select * from customer_inf " +
                "where customer_id = \"" + ID + "\";").get(0);
        String phoneNumber = resultInfo.get(5);
        String identityNum = resultInfo.get(4);
        String name = resultInfo.get(2);
        String nickName = result.get(1);
        Gender gender = Gender.valueOf(resultInfo.get(8));
        String introduceSign = result.get(5);
        int age = Integer.parseInt(resultInfo.get(3));

        User loginUser = new User(age, phoneNumber, name, identityNum, accountName, nickName, password, gender,
                introduceSign, ID);
        loginUser.isLoggedIn = result.get(6);
        loginUser.money = Integer.parseInt(resultInfo.get(13));

        // add in users list
        if (!Daemon.users.containsKey(accountName))
            Daemon.users.put(accountName, loginUser);

        return loginUser;

    }


    /**
     * Create a new user in database.<br>
     * <br>
     * <br>
     * @param newUser a instance of user
     */
    protected static void register(User newUser)
            throws UserAPIException, SQLException {

        if (!Pattern.compile("\\d{11}").matcher(newUser.phoneNumber).matches())
            throw new PhoneNumberFormatException();

        if (!Pattern.compile("\\w{4,18}").matcher(newUser.accountName).matches())
            throw new AccountNameFormatException();

        if (!Pattern.compile("\\w{4,18}").matcher(newUser.nickName).matches())
            throw new NickNameFormatException();

        // TODO 改为正式数据库接口
        if (TestDatabaseAPI.isAccountNameExist(newUser.accountName))
            throw new AccountNameExistException();

        // TODO 改为正式数据库接口
        if (TestDatabaseAPI.isIDNumExist(newUser.identityNum))
            throw new IdentityExistException();

        if (!Pattern.compile("^(?!\\d+$)(?![A-Za-z]+$)(?![a-z0-9]+$)(?![A-Z0-9]+$)[a-zA-Z0-9]{8,16}$")
                .matcher(newUser.password).matches())
            throw new PasswordFormatException();

        if (!Pattern.compile("^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$").
                matcher(newUser.identityNum).matches())
            throw new IdentityNumException();

        newUser.password = MD5.getInstance().getMD5(newUser.password);
        newUser.isLoggedIn = "1";

        // TODO 改为正式数据库接口
        TestDatabaseAPI.runModify("insert into customer (customer_id, nick_name, login_name, password_md5, " +
                "introduce_sign, is_status) " + "values (\"" + newUser.ID + "\", \"" + newUser.nickName + "\", \"" +
                newUser.accountName + "\", \"" + newUser.password + "\", \"" + newUser.introduceSign + "\", " +
                newUser.isLoggedIn + ");");

        newUser.creatTime = TestDatabaseAPI.runQuery("select create_time from customer where login_name = \"" +
                newUser.accountName + "\";").get(0).get(0);

        try {

            // TODO 改为正式数据库接口
            TestDatabaseAPI.runModify("insert into customer_inf (age, customer_inf_id, customer_id, " +
                    "customer_name, identity_card_no, mobile_phone, " + "gender, register_time) values (" +
                    newUser.age + ", \"" + newUser.ID + "\", \"" + newUser.ID + "\", \"" + newUser.name + "\", \"" +
                    newUser.identityNum + "\", \"" + newUser.phoneNumber + "\", \"" + newUser.gender.toString() +
                    "\", \"" + newUser.creatTime + "\");");

        } catch (SQLException e) {

            // TODO 改为正式数据库接口
            TestDatabaseAPI.runModify("delete from customer where login_name = \"" + newUser.accountName + "\";");
            throw e;

        }

    }


    protected void changeOtherAuthority(String accountName, int level)
            throws SQLException, NoAuthorityException, AccountNotRegisteredException {

        if (level != 0)
            throw new NoAuthorityException();

        List<List<String>> result = TestDatabaseAPI.runQuery("select customer_id from customer where login_name = \"" + accountName +
                "\";");

        if (result.size() == 0)
            throw new AccountNotRegisteredException();

        String ID = result.get(0).get(0);
        TestDatabaseAPI.runModify("update customer_inf set customer_level = " + level + "where customer_id = \"" +
                ID + "\";");

    }


    protected void changeAuthority(int level) throws SQLException {

        this.level = level;
        TestDatabaseAPI.runModify("update customer_inf set customer_level = " + level + "where customer_id = \"" +
                ID + "\";");

    }


    protected void addAddress(String province, String city, String countyOrDistrict, String detail,
                              String consignee, String phoneNumber, String status) throws SQLException {

        // TODO 改为正式数据库接口
        List<List<String>> modifyID = TestDatabaseAPI.runQuery("select addid from customer_address where status = 1;");

        if (status.equals("1")) {

            if (modifyID.size() != 0) {

                // TODO 改为正式数据库接口
                TestDatabaseAPI.runModify("update customer_address set status = 2 where addid = \"" +
                        modifyID.get(0).get(0) + "\";");

            }

        }

        String ID = String.valueOf(new IDGenerator(2L, 2L).nextId());

        // TODO 改为正式数据库接口
        TestDatabaseAPI.runModify("insert into customer_address (addid, userId, consignee, phone, province, " +
                "city, area, address, status) values (\"" + ID + "\", \"" + this.ID + "\", \"" + consignee + "\", \"" +
                phoneNumber + "\", \"" + province + "\", \"" + city + "\", \"" + countyOrDistrict + "\", \"" + detail +
                "\", " + status + ");");

    }


    protected void modifyAddress(String ID, String province, String city, String countyOrDistrict, String detail,
                                 String consignee, String phoneNumber, String status) throws SQLException {

        // TODO 改为正式数据库接口
        List<List<String>> modifyID = TestDatabaseAPI.runQuery("select addid from customer_address where status = 1;");

        if (status.equals("1")) {

            if (modifyID.size() != 0) {

                // TODO 改为正式数据库接口
                TestDatabaseAPI.runModify("update customer_address set status = 2 where addid = \"" +
                        modifyID.get(0).get(0) + "\";");

            }

        }

        try {

            // TODO 改为正式数据库接口
            TestDatabaseAPI.runModify("update customer_address set consignee = \"" + consignee + "\", phone = \"" +
                    phoneNumber + "\", province = \"" + province + "\", city = \"" + city + "\", area = \"" +
                    countyOrDistrict + "\", address = \"" + detail + "\", status = " + status + " where addid = \"" + ID +
                    "\";");

        } catch (SQLException e) {

            if (modifyID.size() != 0) {

                // TODO 改为正式数据库接口
                TestDatabaseAPI.runModify("update customer_address set status = 1 where addid = \"" +
                        modifyID.get(0).get(0) + "\";");

            }

        }

    }


    protected void deleteAddress(String ID) throws SQLException {

        // TODO 改为正式数据库接口
        TestDatabaseAPI.runModify("delete from customer_address where addid = \"" + ID + "\" and userId = \"" +
                this.ID + "\";");

    }


    protected void changeInfo(String nickName, String password, String introduceSign, int age, String phoneNumber,
                              Gender gender, String imageBase64)
            throws SQLException, IOException {

        // TODO 改为正式数据库接口
        TestDatabaseAPI.runModify("update customer set nick_name = \"" + nickName + "\", password_md5 = \"" +
                password + "\", introduce_sign = \"" + introduceSign + "\", user_avatar = \"META_INF/avatar/" + ID +
                ".jpg\" where customer_id = \"" + ID + "\";");

        // TODO 改为正式数据库接口
        TestDatabaseAPI.runModify("update customer_inf set age = " + age + ", mobile_phone = \"" + phoneNumber +
                "\", gender = \"" + gender.toString() + "\" where customer_id = \"" + ID + "\";");

        this.nickName = nickName;
        this.password = password;
        this.introduceSign = introduceSign;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.gender = gender;

        //decode base64
        byte[] bytes = new BASE64Decoder().decodeBuffer(imageBase64);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
        avatar.deleteOnExit();
        ImageIO.write(bufferedImage, "jpg", avatar);

    }


    protected String getAvatar() throws IOException {

        BufferedImage bufferedImage = ImageIO.read(avatar);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return new BASE64Encoder().encodeBuffer(bytes).trim();

    }

}
