import exceptions.AccountNameExistException;
import exceptions.AgeException;
import exceptions.PasswordFormatException;
import exceptions.PhoneNumberFormatException;



/**
 * @author Qi, Hanzhong<br>
 * E-Mail: 17722018@bjtu.edu.cn<br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
public class Seller extends User {

    protected Seller(int age, String phoneNumber, String name, String account, String password, Gender gender)
            throws AgeException, PhoneNumberFormatException, AccountNameExistException, PasswordFormatException {

        super(age, phoneNumber, name, account, password, gender);

    }

}
