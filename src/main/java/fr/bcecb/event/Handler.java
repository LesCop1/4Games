package fr.bcecb.event;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

public class Handler implements Comparable {
    private final WeakReference<Object> objectRef;
    private final Method method;
    private final int priority;

    Handler(Object object, Method method, int priority) {
        this.objectRef = new WeakReference<>(object);
        this.method = method;
        this.priority = priority;
    }

    Object getObject() {
        return objectRef.get();
    }

    Method getMethod() {
        return method;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Object o) {
        return compareTo((Handler)o);
    }

    private int compareTo(Handler handler) {
        return priority - handler.priority;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean equals(Handler handler) {
        return this.objectRef.get() == handler.objectRef.get() && this.method.equals(handler.method) && this.priority == handler.priority;
    }
}