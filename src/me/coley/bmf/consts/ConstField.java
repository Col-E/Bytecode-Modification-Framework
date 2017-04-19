package me.coley.bmf.consts;

public class ConstField extends Constant<Integer> {
    public ConstField(int clazz, int nameType) {
        super(ConstantType.FIELD, (clazz << 16) | nameType);
    }

    public int getClassIndex() {
        return (getValue().intValue() >> 16) & 0xffff;
    }

    public int getNameTypeIndex() {
        return getValue().intValue() & 0xffff;
    }
}
