package Service;


import java.io.IOException;
import Model.User.Member;
import common.BaseHelper;
import common.Utils;
import interfaces.UserInterface;

public class UserService implements UserInterface{

	@Override
	public void login() throws IOException {
		
		if(BaseHelper.isLogin()) {
			System.out.println("This user has been logged.");
			return;
		}
		
		// TODO: process get input to login
		System.out.println("Process login:");
		System.out.print("Username: ");
		String userName = "";
		System.out.print("Password: ");
		String password = "";
			
		// TODO: declare new User
		

		/* After successful login, set the flag in Utils to re-use.
		
		ex:
		if(!BaseHelper.isNullOrEmpty(member) && member.checkingUserLoginInfo(userName, password)) {
			Utils.isLogin = true;
			Utils.current_user = member;
			System.out.println("Login success! " + member.toString());
			return;
		}
		
		can be modified if you want.
		*/
	}
	
	@Override
	public void register() throws IOException {
		
		// TODO: process get input to login
		String name = "";
	    String phoneNumber = "";
	    String username = "";
	    String password = "";

	    /*
	     * TODO: Checking exits username
	     * creating new Member if this username is not exits
	     * after that, switch to login page
	     * */
	    
	}
	
	@Override
	public void logout() {
		Utils.isLogin = false;
		Utils.current_user = null;
	}

	@Override
	public void loadData() {
		
		
	}

	@Override
	public void writeData() {
		
	}

}
