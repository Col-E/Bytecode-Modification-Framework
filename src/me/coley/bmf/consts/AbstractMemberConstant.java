package me.coley.bmf.consts;

public class AbstractMemberConstant extends Constant<Integer> {
    public AbstractMemberConstant(ConstantType type, int clazz, int nameType) {
        super(type, (clazz << 16) | nameType);
    }

    public int getClassIndex() {
        return (getValue().intValue() >> 16) & 0xffff;
    }

    public int getNameTypeIndex() {
        return getValue().intValue() & 0xffff;
    }
}
