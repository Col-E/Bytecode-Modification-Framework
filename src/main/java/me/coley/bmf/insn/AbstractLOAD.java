package me.coley.bmf.insn;

public class AbstractLOAD extends SingleValueInstruction<Integer> {
    public AbstractLOAD(int opcode, int index) {
        super(OpType.VARIABLE, opcode, 2, index);
    }
}
