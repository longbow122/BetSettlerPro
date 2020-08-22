package me.longbow122.BetSettlerPro.utils;

import me.longbow122.BetSettlerPro.Main;
import me.longbow122.BetSettlerPro.configuration.ConfigurationSection;
import me.longbow122.BetSettlerPro.configuration.file.FileConfiguration;


public class UserUtils {
	private Main main = new Main();
	
	public boolean addUser(String userName, String password) {
		FileConfiguration file = main.getFileConfig();
		if(file.contains("users")) {
			ConfigurationSection users = file.getConfigurationSection("users");
			if(users.contains(userName)) {
				System.out.println("You cannot add a user with the same username! Please enter a different username.");
				return false;
			}
			users.set(userName, password);
			main.saveFile();
			System.out.println("User added!");
			return true;
	}
		ConfigurationSection users = file.createSection("users");
		users.set(userName, password);
		main.saveFile();
		System.out.println("User added!");
		return true;
	}
	
	public boolean removeUser(String userName) {
		FileConfiguration file = main.getFileConfig();
		if(file.contains("users")) {
			ConfigurationSection users = file.getConfigurationSection("users");
			if(users.contains(userName)) {
				String password = users.getString(userName);
				System.out.println("User to delete: " + userName);
				System.out.println(userName + "'s password: " + password);
				users.set(userName, null);
				main.saveFile();
				System.out.println("User deleted!");
				return true;
			}
			System.out.println(userName + " is not a valid username! Please enter in a username which exists in bsp.yml.");
			removeUser(userName);
			return true;
		}
		System.out.println("There are currently no valid users to remove! Please add a user before you are able to remove them.");
		return false;
	}

}
