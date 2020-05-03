package me.coley.bmf.insn;

public class AbstractSTORE extends SingleValueInstruction<Integer> {
    public AbstractSTORE(int opcode, int index) {
        super(OpType.VARIABLE, opcode, 2, index);
    }
}
