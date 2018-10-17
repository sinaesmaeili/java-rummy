package core;

import java.util.ArrayList;

public class AIStrategyOne implements GameStrategy<AIPlayer> {
	
	public void executeStrategy(AIPlayer player) {
		
		
		ArrayList<Meld> runsThenSets = player.meldRunsFirst();
		ArrayList<Meld> setsThenRuns = player.meldSetsFirst();
		
		player.meldsInHand = player.findBestPlay(runsThenSets, setsThenRuns);
		
		if(player.meldsInHand != null && player.meldsInHand.size() > 0) {
			
			System.out.println("AI played melds");
			//Discard used tiles from hand
			for(int i = 0; i < player.meldsInHand.size(); i++) {
				
				for(int j = 0; j < player.meldsInHand.get(i).size(); j++) {					
					player.hand.remove(player.meldsInHand.get(i).getTile(j));
				}
			}			
			
			player.playMelds(player.game.getBoard(), player.meldsInHand);
		}
		else {
			//Draw tile
			Tile newTile = player.game.getDeck().drawTile();
			player.hand.add(newTile);
		}
	}

}
