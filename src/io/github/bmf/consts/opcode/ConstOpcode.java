package io.github.bmf.consts.opcode;

public class ConstOpcode<T> extends Opcode {
    public T value;

    public ConstOpcode(OpcodeType type, int opcode, int length) {
        this(type, opcode, length, null);
    }

    public ConstOpcode(OpcodeType type, int opcode, int length, T value) {
        super(type, opcode, length);
        this.value = value;
    }

}
