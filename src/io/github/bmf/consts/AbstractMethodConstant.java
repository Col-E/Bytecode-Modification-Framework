package io.github.bmf.consts;

public abstract class AbstractMethodConstant extends Constant<Integer> {
    public AbstractMethodConstant(ConstantType type, int clazz, int nameType) {
        super(type, (clazz << 16) | nameType);
    }

    public int getClassIndex() {
        return (getValue().intValue() >> 16) & 0xffff;
    }

    public int getNameTypeIndex() {
        return getValue().intValue() & 0xffff;
    }
}
