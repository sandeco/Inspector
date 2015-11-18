package com.inspector.util;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private static EventManager singleton;
    private List<Class> events;

    private EventManager() {
        events = new ArrayList<>();
    }

    public static EventManager getInstance() {
        if (singleton == null)
            singleton = new EventManager();

        return singleton;
    }

    public void registerEvent(Class clazz) {
        events.add(clazz);
    }

    public void unregisterEvent(Class clazz) {
        events.remove(clazz);
    }

    public synchronized boolean isHappening(Class clazz) {
        for (Class c : events) {
            if (clazz == c)
                return true;
        }

        return false;
    }
}
