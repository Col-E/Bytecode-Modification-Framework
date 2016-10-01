package io.github.bmf.consts;

public class ConstField extends Constant<Integer> {
    public ConstField(int clazz, int nameType) {
        super(ConstantType.FIELD, (clazz << 16) | nameType);
    }

    public int getClassIndex() {
        return (value.intValue() >> 16) & 0xffff;
    }

    public int getNameTypeIndex() {
        return value.intValue() & 0xffff;
    }
}
