package fr.bcecb.event;

import fr.bcecb.Game;

public abstract class GameEvent extends Event {

    public GameEvent() {

    }

    public Game getGame() {
        return Game.instance();
    }

    public static class Close extends GameEvent {
        public Close() {

        }
    }

    public static class Tick extends GameEvent {
        public Tick() {

        }
    }
}