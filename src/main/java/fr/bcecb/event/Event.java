package fr.bcecb.event;

import fr.bcecb.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Event.Logging
public abstract class Event {
    private boolean cancelled;

    public final boolean isCancelled() {
        return cancelled;
    }

    public final void setCancelled(boolean cancelled) {
        this.cancelled = isCancellable() && cancelled;
    }

    private boolean isCancellable() {
        return this.getClass().isAnnotationPresent(Cancellable.class);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Cancellable {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Logging {
        public Log value() default Log.SYSTEM;
    }
}