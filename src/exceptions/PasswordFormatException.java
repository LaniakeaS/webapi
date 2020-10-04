package exceptions;


/**
 * @author Qi, Hanzhong<br>
 * E-Mail: 17722018@bjtu.edu.cn<br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
public class PasswordFormatException extends UserAPIException {

    public PasswordFormatException() {

        super("Password should combined by 8-20 English capital and small characters and numbers.");

    }

}
