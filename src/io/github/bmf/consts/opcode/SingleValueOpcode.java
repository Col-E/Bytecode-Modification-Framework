package io.github.bmf.consts.opcode;

public class SingleValueOpcode<T> extends Opcode {
    public T value;

    public SingleValueOpcode(OpcodeType type, int opcode, int length) {
        this(type, opcode, length, null);
    }

    public SingleValueOpcode(OpcodeType type, int opcode, int length, T value) {
        super(type, opcode, length);
        this.value = value;
    }

}
