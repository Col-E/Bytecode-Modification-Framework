package io.github.bmf.consts.mapping;

import io.github.bmf.consts.ConstUTF8;
import io.github.bmf.util.descriptors.MemberDescriptor;

public class ConstMemberDesc extends ConstUTF8 {
    public final MemberDescriptor desc;

    public ConstMemberDesc(MemberDescriptor desc) {
        super();
        // Mainly for testing. 
        // TODO: Decide if this practice should be applied globally
        if (desc == null) throw new RuntimeException("Cannot have a null value for ConstMemberDesc!");
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return desc.toDesc();
    }
}
