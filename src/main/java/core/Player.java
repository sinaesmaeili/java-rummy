package core;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

	public String name = "Default";
	protected Hand hand;
	public Game game;
	public boolean inital30Played = false;
	protected ArrayList<Meld> meldsInHand;
		
	public Player() {
		this.hand = new Hand();
		this.meldsInHand = new ArrayList<Meld>();
	}
	
	public Player(String name, Game game) {
		this.name = name;
		this.hand = new Hand();
		this.game = game;
		this.meldsInHand = new ArrayList<Meld>();
	}
	
	public Hand getHand() {
		return this.hand;
	}
	
	public void playMeld(Board board, ArrayList<Tile> potentialMeld) {
		
		if(isValidMeld(potentialMeld)) {
			Meld meld = new Meld();
			
			for(int i = 0; i < potentialMeld.size();i++) {
				meld.add(potentialMeld.get(i));
			}
			
			board.addMeld(meld);
		}
		
	}
	
	public void playMelds(Board board, ArrayList<Meld> melds) {
		for(int i = 0; i < melds.size(); i++) {
			board.addMeld(melds.get(i));
		}
	}
	
	public boolean isValidMeld(ArrayList<Tile> tiles) {
		
		if(tiles.size() < 3) {
			return false;
		}
		
		boolean validSet = isValidSet(tiles);
		boolean validRun = isValidRun(tiles);
				
		return validSet || validRun;
	}
	
	private boolean isValidSet(ArrayList<Tile> tiles) {
		
		ArrayList<Character> colours = new ArrayList<Character>();
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		
		for(int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			
			//Check color
			if(colours.contains(tile.getColour())){
				return false;
			} else {
				colours.add(tile.getColour());		
			}
			
			//Check rank
			if(ranks.size() == 0) {
				ranks.add(tile.getRank());
			}
			else if(ranks.contains(tile.getRank())) {
				ranks.add(tile.getRank());
			}
			else { //Does not contain same rank
				return false;
			}
			
		}
		
		return true;
	}
	
	private boolean isValidRun(ArrayList<Tile> tiles) {
		
		char color = ' ';
		ArrayList<Integer> ranks = new ArrayList<Integer>();
		
		for(int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			
			//Check color
			if(color == ' ') {
				color = tile.getColour();
			} else if(color != tile.getColour()) {
				return false;
			}
					
			ranks.add(tile.getRank());		
		}
		
		Collections.sort(ranks);
		int prevRank = ranks.get(0);
		
		for(int i = 1; i < ranks.size(); i++) {

			if(ranks.get(i) - prevRank != 1) {			
				return false;
			}	
			prevRank = ranks.get(i);
		}
		
		
		return true;
	}

}
