import exceptions.*;


/**
 * @author Qi, Hanzhong<br>
 * E-Mail: 17722018@bjtu.edu.cn<br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
public class Seller extends User {

    protected Seller(int age, String phoneNumber, String name, String accountName, String nickName, String password,
                     Gender gender, String introduceSign)
            throws UserAPIException {

        super(age, phoneNumber, name, accountName, nickName, password, gender, introduceSign);

    }

}
