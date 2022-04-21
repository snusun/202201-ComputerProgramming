package relay.player;

import relay.map.Map;
import relay.simulator.Message;

public abstract class Player implements Comparable<Player>{
    private double position;
    private double velocity;
    private boolean currentPlayer;
    private double nextPlayerPosition;
    private Eyesight eyesight;
    private Map map;
    private int playerNum;
	private final String playerType;

    public Player(double velocity, Map map, String playerType){
        this(0, velocity, map, playerType);
    }
    public Player(double position, double velocity, Map map, String playerType) {
        this.position = position;
        this.velocity = velocity;
        this.map = map;
        currentPlayer = position == 0;
        this.eyesight = new Eyesight(map);
		this.playerType = playerType;
    }

    public double getPosition(){
        return position;
    }

    public void setPosition(double position){
        this.position = position;
    }

    public Eyesight getEyesight(){
        return eyesight;
    }

    public Map getMap(){
        return map;
    }

    public double getVelocity(){
        return velocity;
    }

    public void setVelocity(double velocity){
        this.velocity = velocity;
    }

    public boolean getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(boolean isCurrent){
        currentPlayer = isCurrent;
    }

    public double getNextPlayerPosition(){
        return nextPlayerPosition;
    }

    public void setNextPlayerPosition(double position) {
        nextPlayerPosition = position;
		eyesight.setNextPlayerPosition(position);
        }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int num) {
        playerNum = num;
    }

    abstract public void move();
    abstract public boolean getThrowUp();
    abstract public void hear(Message message);

    public void passBaton(Player nextPlayer){
        //TODO: Problem 2.2
        currentPlayer = false;
        nextPlayer.currentPlayer = true;
    }

    protected double getMovableDistance(double velocity){
        //TODO: Problem 2.2
        // do i understand correctly?
        double distanceToBoundary = eyesight.getDistanceToBoundary(position);
        double distanceToNextPlayer = eyesight.getDistanceToNextPlayer(position);
        double dis = distanceToBoundary < distanceToNextPlayer ? distanceToBoundary : distanceToNextPlayer;
        return dis<velocity ? dis : velocity;
    }

    @Override
    public String toString() {
        return toCustomString();
    }
    public abstract String toCustomString();

}
