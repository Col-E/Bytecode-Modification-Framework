package me.coley.bmf.insn;

public class AbstractFieldInstruction extends Instruction {
    public int fieldIndex;

    public AbstractFieldInstruction(int opcode, int fieldIndex) {
        super(OpType.STACK, opcode, 3);
        this.fieldIndex = fieldIndex;
    }

}
