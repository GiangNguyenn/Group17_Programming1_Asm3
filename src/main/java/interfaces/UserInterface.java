package interfaces;

import Model.User.Member;

import java.io.IOException;

/**
 * this interface inherit the main interface
 * define method signatures associated with User's features
 * implemented by the UserService class
 */

public interface UserInterface extends mainInterface {

    void login() throws IOException;

    void register() throws IOException;

    void logout();

    void printUserProfile(Member currentUser);

}
