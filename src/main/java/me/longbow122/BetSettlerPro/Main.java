package me.longbow122.BetSettlerPro;

import java.io.File;
import java.io.IOException;

import me.longbow122.BetSettlerPro.configuration.file.FileConfiguration;
import me.longbow122.BetSettlerPro.configuration.file.YamlConfiguration;
import me.longbow122.BetSettlerPro.utils.Utils;

public class Main {
	private static Utils u = new Utils();
	private static File folder = new File("BetSettlerPro");
	private static File file = new File("BetSettlerPro\\bsp.yml");
	private static FileConfiguration fileConfig;

	
	/*
	 * Main method which is set to the run configuration. This is the initial code that will
	 * run when the jar file is ran.
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("BetSettlerPro launched!");
		if(!(folder.exists())) {
			// If the BetSettlerPro folder doesn't exist, it is created.
			folder.mkdir();
			System.out.println("Data folder did not exist, and so, it was created!");
		}
		if(!(file.exists())) {
			// If the bsp.yml file doesn't exist, it is created.
			file.createNewFile();
			System.out.println("BetSettler file did not exist, and so, it was created!");
		}
		// Load the main file configuration
		fileConfig = YamlConfiguration.loadConfiguration(file);
		//Runs the main startup method in Utils.java
		u.startup();
		return;	
	}
	
	//Gets the main bsp.yml file
	public File getFile() {
		return file;
	}
	
	//Accesses the object in which I am able to write into the file
	public FileConfiguration getFileConfig() {
		return fileConfig;
	}
	
	
	//Saves the file safely while catching any Exceptions
	public void saveFile() {
		if(fileConfig == null || file == null) {
			return;
		} 
		try {
			fileConfig.save(file);
		} catch (IOException e) {
			System.out.println("Could not save the file! " + e);
			e.printStackTrace();
		}
	}
}
