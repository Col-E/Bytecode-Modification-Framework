package io.github.bmf.type;

public class PrimitiveType extends Type {

    public final String desc;

    public PrimitiveType(int type, String desc) {
        super(type);
        this.desc = desc;
    }

    @Override
    public String toDesc() {
        return desc;
    }

}