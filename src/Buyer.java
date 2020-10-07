import exceptions.AccountNameExistException;
import exceptions.AgeException;
import exceptions.PasswordFormatException;
import exceptions.PhoneNumberFormatException;

public class Buyer extends User {

    public Buyer(int age, String phoneNumber, String name, String account, String password, Gender gender)
            throws AgeException, PhoneNumberFormatException, AccountNameExistException, PasswordFormatException {

        super(age, phoneNumber, name, account, password, gender);

    }

}
