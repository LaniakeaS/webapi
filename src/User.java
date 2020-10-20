import exceptions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    protected Date inDate;
    protected Gender gender;
    protected int ID, age;
    protected List<Address> addresses;
    protected List<Order> orders;
    protected String accountName, name, password, phoneNumber, nickName, introduceSign, identityNum;


    /**
     * Constructor to create a new user.<br>
     * <br>
     * <br>
     * @param age user age
     * @param phoneNumber phone number of the user
     * @param name user's real name
     * @param accountName user's account name (will be used while login, should be exclusive)
     * @param password password for verifying.
     * @param gender male, female or secret.<br>
     * <br>
     * <br>
     * @exception AgeException The age should greater than 18.
     * @exception PhoneNumberFormatException Should not contain non-numeric character.
     * @exception AccountNameExistException The name is already exist.
     * @exception PasswordFormatException Password should combined by 8-20 English capital and small characters and
     *                                    numbers.
     */
    protected User(int age, String phoneNumber, String name, String identityNum, String accountName, String nickName,
                   String password, Gender gender, String introduceSign)
            throws UserAPIException {

        if (age < 18)
            throw new AgeException();

        if (Pattern.compile("\\d{11}").matcher(phoneNumber).matches())
            throw new PhoneNumberFormatException();

        if (!Pattern.compile("\\w{4,18}").matcher(accountName).matches())
            throw new AccountNameFormatException();

        if (!Pattern.compile("\\w{4,18}").matcher(nickName).matches())
            throw new NickNameFormatException();

        // TODO 判断账户名称是否已经存在
        if (false)
            throw new AccountNameExistException();

        if (!Pattern.compile("^(?!\\d+$)(?![A-Za-z]+$)(?![a-z0-9]+$)(?![A-Z0-9]+$)[a-zA-Z0-9]{8,16}$").matcher(password)
                .matches())
            throw new PasswordFormatException();

        if (!Pattern.compile("^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$").
                matcher(identityNum).matches())
            throw new IdentityNumException();

        this.age = age;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.accountName = accountName;
        this.password = password;
        this.nickName = nickName;
        this.introduceSign = introduceSign;
        this.gender = gender;
        this.identityNum = identityNum;
        inDate = new Date(System.currentTimeMillis());
        addresses = new ArrayList<>();
        orders = new ArrayList<>();

        // TODO 在数据库中建立条目，初始化 ID

    }


    /**
     * @param account account name
     * @param password password<br>
     * <br>
     * <br>
     * @return return a User that just has been authorized<br>
     * <br>
     * <br>
     * @exception AccountNotRegisteredException Account has not been registered.
     * @exception PasswordIncorrectException Incorrect password.
     */
    protected static User login(String account, String password)
            throws AccountNotRegisteredException, PasswordIncorrectException {

        // TODO 根据 ID 或者 account name 来确认其是否存在于数据库中，然后验证密码。如果成功返回 User，否则抛出异常。
        return null;

    }


    /**
     * @param ID ID
     * @param password password<br>
     * <br>
     * <br>
     * @return return a User that just has been authorized<br>
     * <br>
     * <br>
     * @exception AccountNotRegisteredException Account has not been registered.
     * @exception PasswordIncorrectException Incorrect password.
     */
    protected static User login(int ID, String password)
            throws AccountNotRegisteredException, PasswordIncorrectException {

        // TODO 根据 ID 或者 account name 来确认数据库是否存在，然后验证密码。如果成功返回 User，否则抛出异常。
        return null;

    }


    /**
     * Create a new user in database.<br>
     * <br>
     * <br>
     * @param newUser a instance of user
     */
    protected static void register(User newUser) {

        // TODO 调用数据库接口的 INSERT 功能在数据库中建立新的用户
        // TODO 查询新建立的用户 ID 并初始化
        newUser.ID = 0;

    }


    protected String inDate() {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(inDate);

    }


    protected void addAddress(String country, String province, String city, String countyOrDistrict, String detail) {

        addresses.add(new Address(country, province, city, countyOrDistrict, detail));

    }


    protected void deleteAddress(int ID) {

        addresses.remove(ID);

    }


    protected void saveChanges() {

        // TODO

    }

}
