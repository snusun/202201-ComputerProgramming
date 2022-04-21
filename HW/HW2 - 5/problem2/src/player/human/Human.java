package relay.player.human;

import relay.map.Map;
import relay.player.Player;

public abstract class Human extends Player{

    Human(double position, double velocity, Map map) {
        super(position, velocity, map,"Human");
    }

}
