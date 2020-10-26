package exceptions;

public class IdentityExistException extends UserAPIException {

    public IdentityExistException() {

        super("Identity number already exist");

    }

}
