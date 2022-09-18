package common;

/**
 * Regular expression constant for user input validation
 */
public class RegexConstants {

    public static final String PASSWORD_REGEX = "/[a-zA-Z0-9]{8}/gm";

    public static final String NAME_REGEX = "[a-zA-Z]{2}";

    public static final String USERNAME_REGEX = "/[a-zA-Z]{2}/gm";

    public static final String PHONE_REGEX = "[0-9]{10}";

}
