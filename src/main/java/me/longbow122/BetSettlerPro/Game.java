package me.longbow122.BetSettlerPro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import me.longbow122.BetSettlerPro.configuration.ConfigurationSection;
import me.longbow122.BetSettlerPro.configuration.file.FileConfiguration;
import me.longbow122.BetSettlerPro.utils.Utils;

public class Game {
	private String player1;
	private String player2;
	private Main main = new Main();
	
	//TODO
	// Make the game. Make sure its bug-free then extensively test
	
	public Game(String player1, String player2) {
		this.player1 = player1;
		this.player2 = player2;
	}
	
	
	//This method will return TRUE if the number is even
	// And it will return FALSE if the number is odd.
	public boolean isEvenNumber(int number) {
		if(number % 2 == 0) {
			return true;
		}
		return false;
	}
	
	public int rollDice() {
		System.out.println("Dice rolling...");
		Random r = new Random();
		int picked = r.nextInt(5) + 1;
		return picked;
	}
	
	public void playGame() throws IOException {
		System.out.println("Game beginning...");
		System.out.println("Player 1 rolls commencing... Rolls: 5");
		int score1 = 0;
		for(int x = 0; x < 5; x++) {
			int i = rollDice();
			int i2 = rollDice();
			int i3 = 0;
			System.out.println("Player 1 current score: " + score1);
			System.out.println("Player 1 roll dice 1: " + i);
			System.out.println("Player 1 roll dice 2: " + i2);
			if(i == i2) {
				System.out.println("Double rolled! Extra dice in play!");
				i3 = rollDice();
				System.out.println("Player 1 roll dice 3: " + i3);
			}
			int iAdd = i + i2 + i3;
			if(isEvenNumber(iAdd)) {
				score1 = score1 + 10;
				System.out.println("Rolled total number was even! 10 bonus points earned!");
			} else {
				score1 = score1 - 5;
				System.out.println("Rolled total number was odd! 5 points lost!");
			}
			score1 = score1 + iAdd;
			System.out.println("Player 1 new score: " + score1);
		}
		System.out.println(player1 + "'s turn is over! Press enter to start " + player2 + "'s turn.");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    in.readLine();
	    System.out.println("Player 2 rolls commencing... Rolls: 5");
		int score2 = 0;
		for(int x = 0; x < 5; x++) {
			int i = rollDice();
			int i2 = rollDice();
			int i3 = 0;
			System.out.println("Player 2 current score: " + score1);
			System.out.println("Player 2 roll dice 1: " + i);
			System.out.println("Player 2 roll dice 2: " + i2);
			if(i == i2) {
				System.out.println("Double rolled! Extra dice in play!");
				i3 = rollDice();
				System.out.println("Player 2 roll dice 3: " + i3);
			}
			int iAdd = i + i2 + i3;
			if(isEvenNumber(iAdd)) {
				score2 = score2 + 10;
				System.out.println("Rolled total number was even! 10 bonus points earned!");
			} else {
				score2 = score2 - 5;
				System.out.println("Rolled total number was odd! 5 points lost!");
			}
			score2 = score2 + iAdd;
			System.out.println("Player 2 new score: " + score2);
	}
		System.out.println("Game over! " + player1 + "'s score: " + score1 + ". " + player2 +  "'s score: " + score2 + ".");
		if(score1 == score2) {
			tieBreaker(player1, player2, score1, score2);
			return;
		}
		if(score1 > score2) {
			System.out.println(player1 + " has won the game! Please press ENTER to exit BSP.");
			addWinner(player1, score1);
		} else {
			System.out.println(player2 + " has won the game! Please press ENTER to exit BSP.");
			addWinner(player2, score2);
		}
		BufferedReader ini = new BufferedReader(new InputStreamReader(System.in));
		ini.readLine();
		sortWinners();
		return;
}
	
	public void tieBreaker(String player1, String player2, int score1, int score2) {
		System.out.println("Tiebreaker Round!");
		System.out.println("Each player will roll one dice. The winner with the highest dice point shall win!");
		int Onescore = 0;
		int Twoscore = 0;
		while(Onescore == Twoscore) {
			Random r = new Random();
			int pick1 = r.nextInt(5) + 1;
			int pick2 = r.nextInt(5) + 1;
			Onescore = Onescore + pick1;
			Twoscore = Twoscore + pick2;
		}
		if(Onescore == Twoscore) {
			tieBreaker(player1, player2, score1, score2);
			return;
		}
		if(Onescore > Twoscore) {
			System.out.println(player1 + " has won the game!");
			score1 = score1 + Onescore;
			addWinner(player1, score1);
			return;
		}
		System.out.println(player2 + " has won the game!");
		score2 = score2 + Twoscore;
		return;
	}
	
	public boolean addWinner(String playerName, int score) {
		FileConfiguration file = main.getFileConfig();
		if(file.contains("winners")) {
			ConfigurationSection winners = main.getFileConfig().getConfigurationSection("winners");
			winners.set(playerName, score);
			main.saveFile();
			return true;
		}
		ConfigurationSection winners = main.getFileConfig().createSection("winners");
		winners.set(playerName, score);
		main.saveFile();
		return true;
	}
	
	// Function to add x in arr 
    public static int[] addX(int arrSize, int arr[], int x) { 
        int i; 
  
        // create a new array of size n+1 
        int newarr[] = new int[arrSize + 1]; 
  
        // insert the elements from 
        // the old array into the new array 
        // insert all elements till n 
        // then insert x at n+1 
        for (i = 0; i < arrSize; i++) 
            newarr[i] = arr[i]; 
  
        newarr[arrSize] = x; 
  
        return newarr; 
    }
    
    public String getKeyFromValue(HashMap<String, Integer> map, int value) {
    	Set<String> keys = map.keySet();
    	for(String x : keys) {
    		int i = map.get(x);
    		if(i == value) {
    			return x;
    		}
    	}
    	return null;
    }

	
	public boolean sortWinners() {
		FileConfiguration file = main.getFileConfig();
		if(file.contains("winners")) {
			ConfigurationSection winners = file.getConfigurationSection("winners");
			HashMap<String, Integer> winnersData = new HashMap<String, Integer>();
			Set<String> winnersKey = winners.getKeys(false);
			int[] winArr = new int[winnersKey.size()];
			for(String x : winnersKey) {
				int pick = winners.getInt(x);
				winnersData.put(x, pick);
				winArr = addX(winArr.length, winArr, pick);
			}
			int[] sortedWinners = Utils.selectionSort(winArr);
			file.set("winners", null);
			winners = file.createSection("winners");
			main.saveFile();
			for(int x : sortedWinners) {
				file.set("winners." + getKeyFromValue(winnersData, x),  x);
			}
			main.saveFile();
			return true;
		}
		return false;
	}
}
