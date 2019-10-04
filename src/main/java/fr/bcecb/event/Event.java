package fr.bcecb.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Event.Cancellable
public abstract class Event {
    private boolean cancelled;

    public final boolean isCancelled() {
        return cancelled;
    }

    public final void setCancelled(boolean cancelled) throws UncancellableEventException {
        if (!isCancellable()) {
            throw new UncancellableEventException();
        }

        this.cancelled = cancelled;
    }

    private boolean isCancellable() {
        return this.getClass().isAnnotationPresent(Cancellable.class);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Cancellable {
    }

    private static class UncancellableEventException extends Exception { }
}