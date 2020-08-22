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

	public static void main(String[] args) throws IOException {
		System.out.println("BetSettlerPro launched!");
		if(!(folder.exists())) {
			folder.mkdir();
			System.out.println("Data folder did not exist, and so, it was created!");
		}
		if(!(file.exists())) {
			file.createNewFile();
			System.out.println("BetSettler file did not exist, and so, it was created!");
		}
		fileConfig = YamlConfiguration.loadConfiguration(file);
		u.startup();
		return;	
	}
	
	public File getFile() {
		return file;
	}
	
	public FileConfiguration getFileConfig() {
		return fileConfig;
	}
	
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
