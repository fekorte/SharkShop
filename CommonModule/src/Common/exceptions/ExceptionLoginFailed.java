package Common.exceptions;

import Common.Person;

public class ExceptionLoginFailed extends Exception {
        public ExceptionLoginFailed(String userName) {
            super("Username: " + userName + " wasn't able to log in. ");
        }
}
