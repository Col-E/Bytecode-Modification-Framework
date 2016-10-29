package io.github.bmf.util.descriptors;

public abstract class MemberDescriptor {
    public final String original;

    public MemberDescriptor(String original) {
        this.original = original;
    }

    public abstract String toDesc();
}
