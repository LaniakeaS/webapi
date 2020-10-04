package exceptions;


/**
 * @author Qi, Hanzhong<br>
 * E-Mail: 17722018@bjtu.edu.cn<br>
 * ID: 17722018<br>
 * LU ID: 34648127<br>
 */
public class AgeException extends UserAPIException {

    public AgeException() {

        super("The age should greater than 18.");

    }

}
