package fr.bcecb.event;

import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import fr.bcecb.util.Log;

import javax.annotation.Nonnull;

public class EventExceptionHandler implements SubscriberExceptionHandler {
    private static final SubscriberExceptionHandler INSTANCE = new EventExceptionHandler();


    @Override
    public void handleException(@Nonnull Throwable exception, SubscriberExceptionContext context) {
        if (context.getEvent() instanceof Event) {
            Event event = (Event) context.getEvent();

            Event.Logging annotation = getAnnotation(event.getClass());

            if (annotation != null) {
                Log.EVENT.severe("Exception during event dispatch in {0} : {1} ({2})",
                        annotation.value().getLogger().getName(),
                        MoreObjects.firstNonNull(exception.getLocalizedMessage(), Throwables.getStackTraceAsString(exception)));
            }
        }
    }

    private static Event.Logging getAnnotation(Class<? extends Event> clazz) {
        if (clazz == Event.class || clazz.isAnnotationPresent(Event.Logging.class)) {
            return clazz.getAnnotation(Event.Logging.class);
        } else return getAnnotation((Class<? extends Event>) clazz.getSuperclass());
    }

    public static SubscriberExceptionHandler getInstance() {
        return INSTANCE;
    }
}
