package com.lucek.androidgameengine2d.eventBus.events;

import com.lucek.androidgameengine2d.game.Field;

public class GameOverEvent extends Event {
    public Field winningColor;

    public GameOverEvent(Field winningColor) {
        this.winningColor = winningColor;
    }
}
