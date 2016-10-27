package io.github.bmf.util;

import io.github.bmf.util.descriptors.MemberDescriptor;

public class MemberData {
    public final Box<String> name;
    public final MemberDescriptor desc;

    public MemberData(String name, MemberDescriptor desc) {
        this.name = new Box<>(name);
        this.desc = desc;
    }
}
