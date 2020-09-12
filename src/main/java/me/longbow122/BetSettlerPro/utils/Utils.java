package me.longbow122.BetSettlerPro.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import me.longbow122.BetSettlerPro.Game;
import me.longbow122.BetSettlerPro.Main;

public class Utils {
	private Main main = new Main();
	private UserUtils uu = new UtilsDep().getUserUtilsClass();
	
	
	/*
	 * The main startup method. Runs the login menu if a player selects login.
	 * If a player sleects /adminbypass, the admin menu is run.
	 */
	public boolean startup() throws IOException {
		System.out.println("Welcome to BetSettlerPro! Please type /login and enter your username and password.");
		BufferedReader mainReader = new BufferedReader(new InputStreamReader(System.in));
		String beginRead = mainReader.readLine();
		if(beginRead.equalsIgnoreCase("/login")) {
			System.out.println("Player 1: Please log in.");
			String player1 = login();
			System.out.println("Player 2: Please log in.");
			String player2 = login();
			Game g = new Game(player1, player2);
			g.playGame();
			return true;
		}
		if(beginRead.equalsIgnoreCase("/adminbypass")) {
			makeAdminMenu();
			return true;
		}
		return true;
	}
	
	
	/*
	 * The main login menu. Checks the file for the users input and checks it for the right
	 * password. Returns the name of the main user who logs in. 
	 * 
	 */
	public String login() throws IOException {
		System.out.println("Please enter your username. (WARNING: CASE SENSITIVE)");
		BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
		String username = userReader.readLine();
		Set<String> usernames = main.getFileConfig().getConfigurationSection("users").getKeys(false);
		if(usernames.contains(username)) {
			System.out.println("Username accepted!");
			System.out.println("Please enter your password. (WARNING: CASE SENSITIVE)");
			BufferedReader passReader = new BufferedReader(new InputStreamReader(System.in));
			String password = passReader.readLine();
			String userPass = main.getFileConfig().getConfigurationSection("users").getString(username);
			if(password.equals(userPass)) {
				return username;
			}
			System.out.println("The password you entered was wrong! Please try again.");
			String login = login();
			return login;
		}
		System.out.println("The username you entered was wrong! Please try again.");
		return login();
	}
	
	/*
	 * Runs the main user additions menu. Using this, you can easily add a user to the main
	 * file. 
	 */
	public void addUserMenu(String adminName) throws IOException {
		System.out.println("Welcome to the user addition menu!");
		System.out.println("To add a user, please enter a username and a password seperated by a comma, like so 'DPatel123,AHS'");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String read = reader.readLine();
		String[] details = read.split(",");
		if(details.length > 2) {
			System.out.println("You have entered too many arguments! Please try again.");
			addUserMenu(adminName);
			return;
		}
		if(details.length < 2) {
			System.out.println("You have entered too little arguments! Please try again.");
			addUserMenu(adminName);
			return;
		}
		if(details.length == 2) {
			System.out.println("Username: " + details[0]);
			System.out.println("Password: " + details[1]);
			if(uu.addUser(details[0], details[1]) == false) {
				addUserMenu(adminName);
				return;
			}
			adminAlreadyLoggedIn(adminName);
			return;
		}
	}
	
	/*
	 * Runs the main user removal menu. Using this, you can easily remove a user from the main
	 * file. 
	 */
	public void removeUserMenu(String adminName) throws IOException {
		System.out.println("Welcome to the user removal menu!");
		System.out.println("To remove a user, please enter their username, like so 'DPatel123'");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String read = reader.readLine();
		if(uu.removeUser(read) == false) {
			adminAlreadyLoggedIn(adminName);
			return;
		}
		return;
	}
	
	/*
	 * Runs the main admin menu. Uses integers to make a selection to ensure the user
	 * can easily navigate a menu. 
	 */
	public void makeAdminMenu() throws IOException {
		System.out.println("Welcome to the admin menu! Please specify which user you are.");
		BufferedReader adminReader = new BufferedReader(new InputStreamReader(System.in));
		String adminRead = adminReader.readLine();
		if(adminRead.equalsIgnoreCase("D Patel") || adminRead.equalsIgnoreCase("D Hughes")) {
			System.out.println("Welcome to the Admin menu! User logged in: " + adminRead + ". Please enter one of the below options:");
			System.out.println("1) Add user");
			System.out.println("2) Remove user");
			BufferedReader adminMenuReader = new BufferedReader(new InputStreamReader(System.in));
			String adminMenuRead = adminMenuReader.readLine();
			if(StringUtils.isNumericSpace(adminMenuRead)) {
				if(adminMenuRead.equalsIgnoreCase("1")) {
					addUserMenu(adminRead);
					return;
				}
				if(adminMenuRead.equalsIgnoreCase("2")) {
					removeUserMenu(adminRead);
					return;
				}
			}
			System.out.println("Sorry! Your input was not recognised as a valid number. Please enter a valid number.");
			adminAlreadyLoggedIn(adminRead);
			return;
		}
	}
	
	/*
	 * Same thing as the user admin menu, but just with the login process skipped.
	 * This will allow for any bad input to be skipped over without a login so the user doesn't
	 * need to go through the whole process.
	 */
	public void adminAlreadyLoggedIn(String adminName) throws IOException {
		System.out.println("Welcome to the Admin menu! User logged in: " + WordUtils.capitalizeFully(adminName) + " Please enter one of the below options:");
		System.out.println("1) Add user");
		System.out.println("2) Remove user");
		BufferedReader adminMenuReader = new BufferedReader(new InputStreamReader(System.in));
		String adminMenuRead = adminMenuReader.readLine();
		if(StringUtils.isNumericSpace(adminMenuRead)) {
			if(adminMenuRead.equalsIgnoreCase("1")) {
				addUserMenu(adminName);
				return;
			}
			if(adminMenuRead.equalsIgnoreCase("2")) {
				removeUserMenu(adminName);
				return;
			}
		}
		System.out.println("Sorry! Your input was not recognised as a valid number. Please enter a valid number.");
		adminAlreadyLoggedIn(adminName);
		return;
	}
	
	//Basic selection sort method.
	public static int[] selectionSort(int[] array) {
	     for (int i = 0; i < array.length - 1; i++) {
	         int maxIndex = i;
	         for (int j = i + 1; j < array.length; j++) {
	             if (array[j] > array[maxIndex]) maxIndex = j;
	         }
	         int tmp = array[i];
	         array[i] = array[maxIndex];
	         array[maxIndex] = tmp;
	     }
	     return array;
	 }

}
