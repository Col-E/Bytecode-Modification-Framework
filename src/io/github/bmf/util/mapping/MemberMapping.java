package io.github.bmf.util.mapping;

import io.github.bmf.util.Box;
import io.github.bmf.util.descriptors.MemberDescriptor;

public class MemberMapping {
    public final Box<String> name;
    public final MemberDescriptor desc;

    public MemberMapping(String name, MemberDescriptor desc) {
        this.name = new Box<>(name);
        this.desc = desc;
    }
}
