package io.github.bmf.util;

public class Box<T> {

    public T value;

    public Box(T value) {
        this.value = value;
    }

    public Box() {
        this.value = null;
    }

}
