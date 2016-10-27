package io.github.bmf.consts;

public class ConstNameType extends Constant<Integer> {

    public ConstNameType(int nameIndex, int descIndex) {
        super(ConstantType.NAME_TYPE, (nameIndex << 16) | descIndex);
    }

    public int getNameIndex() {
        return (getValue().intValue() >> 16) & 0xffff;
    }

    public int getDescIndex() {
        return getValue().intValue() & 0xffff;
    }
}
