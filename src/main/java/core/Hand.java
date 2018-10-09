package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Hand {
	public int count = 0;
	private ArrayList<Tile> hands;
	
	public Hand() {
		hands = new ArrayList<Tile>();
	}
	
	public Tile getTile(int i) {
		return this.hands.get(i);
	}
	
	public ArrayList<Tile> getTiles() {
		return this.hands;
	}
	
	public void add(Tile t) {
		hands.add(count++, t);
	}
	
	public void remove(Tile t) {
		for(int i = 0; i < count; i++) {
			if(hands.get(i).getRank() == t.getRank() && hands.get(i).getColour() == t.getColour()) {
				hands.remove(i);
			}
		}
	}
	
	public boolean win() {
		if(hands.size() == 0) {
			return true;
		}
		return false;
	}
	
	public int size() {
		return this.hands.size();
	}
	
	public void printHand() {
		if(size() == 0) {
			System.out.println("Player doesn't have any tile.");
		} else {
			System.out.print("Player's tile: ");
			for(int i = 0; i < count; i++) {
				String colour = "";
				if(hands.get(i).getColour() == 'R') {
					colour = "Red";
				}
				if(hands.get(i).getColour() == 'B') {
					colour = "Blue";
				}
				if(hands.get(i).getColour() == 'G') {
					colour = "Green";
				}
				if(hands.get(i).getColour() == 'O') {
					colour = "Orange";
				}
				System.out.print(colour + hands.get(i).getRank() + " ");
			}
			System.out.println();
		}
	}
	
	//sort the hands by number
	public void sortTilesByNumber() {
		ArrayList<Tile> newHands = hands;
		Collections.sort(newHands, new SortByNumber());
	}
	
	//sort the hands by colour.
	public void sortTilesByColour(){
		ArrayList<Hand> colouredTiles = new ArrayList<Hand>();
		ArrayList<Tile> newHand = new ArrayList<Tile>();	
		colouredTiles = this.separateTilesByColour();
		
		for(int i = 0; i < 4; i++){
			colouredTiles.get(i).sortTilesByNumber();
			newHand.addAll(colouredTiles.get(i).getTiles());
		}
		this.hands = newHand;
	}
	
	//Separate Tiles by Colour and put these groups of tiles into an arraylist
	public ArrayList<Hand> separateTilesByColour() {
		ArrayList<Hand> colouredTiles = new ArrayList<Hand>();
		Tile current;
		
		for(int i = 0; i < 4; i++) {
			colouredTiles.add(new Hand());
		}
		
		for(int i = 0; i < count; i++){
			current = this.getTile(i);
			
			if(current.getColour() == 'R') {
				colouredTiles.get(0).add(current);
			} else if(current.getColour() == 'B') {
				colouredTiles.get(1).add(current);
			} else if(current.getColour() == 'G') {
				colouredTiles.get(2).add(current);
			}else if(current.getColour() == 'O'){
				colouredTiles.get(3).add(current);
			}
		}
		return colouredTiles;
	}
	
	//Separate Tiles by number and put these groups of tiles into an arraylist
	public ArrayList<Hand> separateTilesByNumber(){
		ArrayList<Hand> tiles = new ArrayList<Hand>();
		this.sortTilesByNumber();
		
		for(int i = 0; i < 13; i++)
			tiles.add(new Hand());
		
		for(int i = 0; i < count; i++)
			tiles.get(this.getTile(i).getRank()).add(this.getTile(i));
		
		return tiles;
	}
	
	//Discard the redundant tile.
	public void discardRedundantTiles() {
		ArrayList<Tile> deck = new ArrayList<Tile>();
		for(Tile tile : this.getTiles()){
			if(!deck.contains(tile))
				deck.add(tile);
		}
		this.hands = deck;
	}
	
	//Separate all tiles of the same number into an arraylist
	public ArrayList<Meld> getSets(){
		ArrayList<Meld> groups = new ArrayList<Meld>();;
		ArrayList<Tile> tiles; 
		
		if(count > 2) {
			groups.add(new Meld(this.getTiles()));
		}
		
		if(count > 3){
			for(Tile removedTile : this.getTiles()){
				tiles = new ArrayList<Tile>(this.getTiles());
				tiles.remove(removedTile);
				groups.add(new Meld(tiles));
			}
		}
		
		return groups;
	}
	
	//Find all of the possible meld of sets that this hand can get
	public ArrayList<Meld> getMeldSets() {
		ArrayList<Hand> tiles = separateTilesByNumber();
		ArrayList<Meld> groups = new ArrayList<Meld>(); 
		
		for(Hand currentSet : tiles){
			currentSet.discardRedundantTiles();
			groups.addAll(currentSet.getSets());
		}
		
		return groups;
	}
	
	//Find all of the possible meld of runs that this hand can get
	public ArrayList<Meld> getMeldRuns(){
		ArrayList<Hand> colouredTiles = separateTilesByColour();
		ArrayList<Meld> runs = new ArrayList<Meld>();;
		ArrayList<Tile> possibleRun;
		
		for(Hand hand : colouredTiles){
			hand.discardRedundantTiles();
			hand.sortTilesByNumber();
			
			for(int i = 0; i < count; i++){
				possibleRun = new ArrayList<Tile>();
				possibleRun.add(hand.getTile(i));
				
				for(int j = i+1; j < count; j++){
					if(hand.getTile(j-1).getRank() == hand.getTile(j).getRank() -1) {
						possibleRun.add(hand.getTile(j));
					}
					else 
						break;
					
					if(possibleRun.size() > 2)
						runs.add(new Meld(possibleRun));
				}
			}
		}
		return runs;
	}
	
	class SortByNumber implements Comparator<Tile> {
		public int compare(Tile t1, Tile t2) {
			return t1.getRank() - t2.getRank();
		}
	}
}
