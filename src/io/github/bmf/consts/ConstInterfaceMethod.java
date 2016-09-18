package io.github.bmf.consts;

public class ConstInterfaceMethod extends Constant<Integer> {
    public ConstInterfaceMethod(int clazz, int nameType) {
        super(ConstantType.INTERFACE_METHOD, (clazz << 16) | nameType);
    }

    public int getClassIndex() {
        return value.intValue() & 0xffff;
    }

    public int getNameTypeIndex() {
        return (value.intValue() >> 16) & 0xffff;
    }
}
