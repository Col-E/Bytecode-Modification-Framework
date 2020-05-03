package me.coley.bmf.insn;

public class SingleValueInstruction<T extends Number> extends Instruction {
    public T value;

    public SingleValueInstruction(OpType type, int opcode, int length) {
        this(type, opcode, length, null);
    }

    public SingleValueInstruction(OpType type, int opcode, int length, T value) {
        super(type, opcode, length);
        this.value = value;
    }

}
