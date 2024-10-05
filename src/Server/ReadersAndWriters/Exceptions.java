package Server.ReadersAndWriters;

public class Exceptions extends Exception{

    public Exceptions(String message) {
        super(message);
    }
    public static class UserAlreadyExistsExceptions extends Exceptions {
        public UserAlreadyExistsExceptions() {
            super("Sorry the username already exists");
        }
    }

    public static class PasswordMismatchException extends Exceptions {

        public PasswordMismatchException() {
            super("Password does not match");

        }
    }
    public static class IncorrectPasswordException extends Exceptions{
        public IncorrectPasswordException(){
            super("Incorrect Password");
        }
    }

    public static class UserNotFoundException extends Exceptions {

        public UserNotFoundException() {
            super("User does not exist");
        }
    }




}
