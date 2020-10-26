import exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    protected int age;
    protected List<Address> addresses;
    protected List<Order> orders;
    protected String accountName, name, password, phoneNumber, nickName, introduceSign, identityNum, ID, creatTime;


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
        addresses = new ArrayList<>();
        orders = new ArrayList<>();

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


    protected void addAddress(String country, String province, String city, String countyOrDistrict, String detail) {

        addresses.add(new Address(country, province, city, countyOrDistrict, detail));

    }


    protected void deleteAddress(int ID) {

        addresses.remove(ID);

    }


    protected void saveChanges() {

        // TODO save changes

    }

}
