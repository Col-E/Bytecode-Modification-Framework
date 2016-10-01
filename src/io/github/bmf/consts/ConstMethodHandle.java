package io.github.bmf.consts;

public class ConstMethodHandle extends Constant<Integer> {

    public ConstMethodHandle(int kind, int index) {
        super(ConstantType.METHOD_HANDLE, (kind << 16) | index);
    }

    public int getKind() {
        return (value.intValue() >> 16) & 0xffff;
    }

    public int getIndex() {
        return value.intValue() & 0xffff;
    }
}
