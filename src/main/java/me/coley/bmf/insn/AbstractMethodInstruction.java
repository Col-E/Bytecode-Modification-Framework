package me.coley.bmf.insn;

public class AbstractMethodInstruction extends Instruction {
    public int methodIndex;

    public AbstractMethodInstruction(int opcode, int size, int methodIndex) {
        super(OpType.STACK, opcode, size);
        this.methodIndex = methodIndex;
    }

}
