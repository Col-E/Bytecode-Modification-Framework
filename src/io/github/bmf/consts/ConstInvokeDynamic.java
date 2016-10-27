package io.github.bmf.consts;

public class ConstInvokeDynamic extends Constant<Integer> {

    public ConstInvokeDynamic(int attribute, int nameType) {
        super(ConstantType.INVOKEDYNAMIC, (attribute << 16) | nameType);
    }

    public int getBootstrapAttribute() {
        return (getValue().intValue() >> 16) & 0xffff;
    }

    public int getNameTypeIndex() {
        return getValue().intValue() & 0xffff;
    }
}
