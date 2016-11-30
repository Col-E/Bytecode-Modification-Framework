package io.github.bmf.util.mapping;

import io.github.bmf.type.descriptors.MemberDescriptor;
import io.github.bmf.util.Box;

public class MemberMapping {
    public final Box<String> name;
    public final MemberDescriptor desc;

    public MemberMapping(String name, MemberDescriptor desc) {
        this(new Box<String>(name), desc);
    }

    public MemberMapping(Box<String> name, MemberDescriptor desc) {
        this.name = name;
        this.desc = desc;
    }
}
