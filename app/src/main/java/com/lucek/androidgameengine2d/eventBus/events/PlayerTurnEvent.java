package com.lucek.androidgameengine2d.eventBus.events;

import com.lucek.androidgameengine2d.controllers.AbstractPlayerController;

public class PlayerTurnEvent extends Event {
    public int currentPlayerString;

    public PlayerTurnEvent(int currentPlayerString) {
        this.currentPlayerString = currentPlayerString;
    }
}
