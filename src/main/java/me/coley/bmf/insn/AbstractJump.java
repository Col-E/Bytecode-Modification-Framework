package me.coley.bmf.insn;

public class AbstractJump extends Instruction {
    // TODO: Figure out a clean way to automate updating this whenever somebody
    // messes with the instructions of the method.
    public int jumpIndex;

    public AbstractJump(OpType optype, int opcode, int jumpIndex) {
        super(optype, opcode, 1);
        this.jumpIndex = jumpIndex;
    }
}
