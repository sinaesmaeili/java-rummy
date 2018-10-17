package core;

public interface GameStrategy<T extends Player> {
	
	public void executeStrategy(T player);
}
