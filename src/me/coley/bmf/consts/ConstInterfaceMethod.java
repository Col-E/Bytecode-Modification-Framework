package me.coley.bmf.consts;

public class ConstInterfaceMethod extends AbstractMemberConstant {
    public ConstInterfaceMethod(int clazz, int nameType) {
        super(ConstantType.INTERFACE_METHOD, clazz, nameType);
    }

    public int getClassIndex() {
        return (getValue().intValue() >> 16) & 0xffff;
    }

    public int getNameTypeIndex() {
        return getValue().intValue() & 0xffff;
    }
}
