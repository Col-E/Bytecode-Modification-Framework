package io.github.bmf.consts.mapping;

import io.github.bmf.consts.ConstUTF8;
import io.github.bmf.util.descriptors.MemberDescriptor;

public class ConstMemberDesc extends ConstUTF8 {
    public final MemberDescriptor desc;

    public ConstMemberDesc(MemberDescriptor desc) {
        super();
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return desc.toDesc();
    }
}
