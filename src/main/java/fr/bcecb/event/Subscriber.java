package fr.bcecb.event;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

public class Subscriber implements Comparable {
    private final WeakReference<Object> objectRef;
    private final Method method;
    private final int priority;

    public Subscriber(Object object, Method method, int priority) {
        this.objectRef = new WeakReference<>(object);
        this.method = method;
        this.priority = priority;
    }

    public Object getObject() {
        return objectRef.get();
    }

    public Method getMethod() {
        return method;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Object o) {
        return compareTo((Subscriber)o);
    }

    public int compareTo(Subscriber subscriber) {
        return priority - subscriber.priority;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean equals(Subscriber subscriber) {
        return this.objectRef.get() == subscriber.objectRef.get() && this.method.equals(subscriber.method) && this.priority == subscriber.priority;
    }
}