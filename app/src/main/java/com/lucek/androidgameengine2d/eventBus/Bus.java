package com.lucek.androidgameengine2d.eventBus;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.lucek.androidgameengine2d.eventBus.commands.apiCommands.ApiCommand;
import com.lucek.androidgameengine2d.eventBus.commands.dbCommands.DbCommand;
import com.lucek.androidgameengine2d.eventBus.events.Event;

import org.greenrobot.eventbus.EventBus;

public class Bus {

    private static final Bus INSTANCE = new Bus();
    private final EventBus eventBus;
    private final Handler mainHandler, apiHandler, dbHandler;

    private Bus() {
        this.eventBus = EventBus.getDefault();

        mainHandler = new Handler(Looper.getMainLooper());

        HandlerThread backgroundThread1 = new HandlerThread("apiThread");
        backgroundThread1.start();
        Looper looper1 = backgroundThread1.getLooper();
        apiHandler = new Handler(looper1);

        HandlerThread backgroundThread2 = new HandlerThread("dbThread");
        backgroundThread2.start();
        Looper looper2 = backgroundThread2.getLooper();
        dbHandler = new Handler(looper2);
    }

    public void post(final ApiCommand command) {
        apiHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.v("Dispatching ApiCommand", command.getClass().getSimpleName());
                eventBus.post(command);
            }
        });
    }

    public void post(final DbCommand command) {
        dbHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.v("Dispatching DbCommand", command.getClass().getSimpleName());
                eventBus.post(command);
            }
        });
    }

    public void post(final Event event) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.v("Dispatching Event", event.getClass().getSimpleName());
                eventBus.post(event);
            }
        });
    }

    public void register(Object o) {
        Log.v("Bus Joined", o.getClass().getSimpleName());
        eventBus.register(o);
    }

    public void unregister(Object o) {
        Log.v("Bus Left", o.getClass().getSimpleName());
        eventBus.unregister(o);
    }

    public static Bus getInstance() {
        return INSTANCE;
    }
}
