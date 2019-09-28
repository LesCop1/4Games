package fr.bcecb.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * The EventManager class watches all the GLFW events (such as user input, window events...) and game-fired events.
 * When such an event is fired, the EventManager notifies all the subscribed objects so that they can be handled.
 *
 * @author Antoine
 */
public class EventManager {
    /**
     * Contains a set of subscribers for registered Event classes
     */
    private static final Map<Class<? extends Event>, SortedSet<Subscriber>> SUBSCRIBERS = new HashMap<>();

    private EventManager() {

    }

    /**
     * Registers the object as a potential subscriber of an event later fired.
     * Event subscriber methods inside the passed object must be annotated with @{@link Subscribe}, and contain only one parameter that is a subclass of Event.
     *
     * @param subscriber the object to be registered
     */
    public static synchronized void register(Object subscriber) {
        for (Method method : subscriber.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                if (method.getParameterCount() == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    method.setAccessible(true);

                    Class<? extends Event> clazz = (Class<? extends Event>) method.getParameterTypes()[0];

                    if (!SUBSCRIBERS.containsKey(clazz)) {
                        SUBSCRIBERS.put(clazz, new TreeSet<>());
                    }

                    SUBSCRIBERS.get(clazz).add(new Subscriber(subscriber, method, method.getDeclaredAnnotation(Subscribe.class).priority()));
                }
            }
        }
    }

    /**
     * Unregisters the object from the event subscribers.
     *
     * @param subscriber the object to be unregistered
     */
    public static synchronized void unregister(Object subscriber) {
        for (SortedSet<Subscriber> subscriberSet : SUBSCRIBERS.values()) {
            subscriberSet.removeIf(s -> subscriber.equals(s.getObject()));
        }
    }

    public static synchronized void fireEvent(Event event) {
        if (!SUBSCRIBERS.containsKey(event.getClass())) {
            return;
        }

        SUBSCRIBERS.get(event.getClass()).removeIf(s -> s.getObject() == null);

        for (Subscriber subscriber : SUBSCRIBERS.get(event.getClass())) {
            try {
                subscriber.getMethod().invoke(subscriber.getObject(), event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}