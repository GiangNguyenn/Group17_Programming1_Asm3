package interfaces;

import Model.User.Member;

import java.io.IOException;

public interface UserInterface extends mainInterface {

    public void login() throws IOException;

    public void register() throws IOException;

    public void logout();

    void printUserProfile(Member currentUser);

}
