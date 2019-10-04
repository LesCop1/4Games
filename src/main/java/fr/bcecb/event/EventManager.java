package fr.bcecb.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * The EventManager class watches all the GLFW events (such as user input, window events...) and game-fired events.
 * When such an event is fired, the EventManager invokes all the associated event handlers so that they can be handled.
 *
 * @author Antoine
 */
public class EventManager {
    /**
     * Contains a set of subscribers for registered Event classes
     */
    private static final Map<Class<? extends Event>, SortedSet<Handler>> EVENT_HANDLERS = new HashMap<>();

    private EventManager() {

    }

    /**
     * Registers the object's methods as potential handlers of an event later fired.
     * Event handler methods inside the passed object must be annotated with @{@link Handle}, and contain only one parameter that is a subclass of Event.
     *
     * @param object the object to be registered
     */
    public static synchronized void register(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Handle.class)) {
                if (method.getParameterCount() == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    method.setAccessible(true);

                    @SuppressWarnings("unchecked")
                    Class<? extends Event> clazz = (Class<? extends Event>) method.getParameterTypes()[0];

                    if (!EVENT_HANDLERS.containsKey(clazz)) {
                        EVENT_HANDLERS.put(clazz, new TreeSet<>());
                    }

                    EVENT_HANDLERS.get(clazz).add(new Handler(object, method, method.getDeclaredAnnotation(Handle.class).priority()));
                }
            }
        }
    }

    /**
     * Unregisters the object from the event handlers.
     *
     * @param object the object to be unregistered
     */
    public static synchronized void unregister(Object object) {
        for (SortedSet<Handler> handlerSet : EVENT_HANDLERS.values()) {
            handlerSet.removeIf(s -> object.equals(s.getObject()));
        }
    }

    public static synchronized void fireEvent(Event event) {
        if (!EVENT_HANDLERS.containsKey(event.getClass())) {
            return;
        }

        EVENT_HANDLERS.get(event.getClass()).removeIf(s -> s.getObject() == null);

        for (Handler handler : EVENT_HANDLERS.get(event.getClass())) {
            try {
                handler.getMethod().invoke(handler.getObject(), event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}