package me.coley.bmf.consts;

public class ConstMethod extends AbstractMethodConstant {
    public ConstMethod(int clazz, int nameType) {
        super(ConstantType.METHOD, clazz, nameType);
    }

    public int getClassIndex() {
        return (getValue().intValue() >> 16) & 0xffff;
    }

    public int getNameTypeIndex() {
        return getValue().intValue() & 0xffff;
    }
}
