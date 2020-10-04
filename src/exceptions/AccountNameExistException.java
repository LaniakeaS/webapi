package exceptions;


/**
 * @author Qi, Hanzhong<br>
 * E-Mail: 17722018@bjtu.edu.cn<br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
public class AccountNameExistException extends UserAPIException {

    public AccountNameExistException() {

        super("The name is already exist.");

    }

}
