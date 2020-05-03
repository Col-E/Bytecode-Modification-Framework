package me.coley.bmf.insn;

/**
 * Opcode that points to a class index. This should be named better eventually.
 * 
 * @author Matt
 */
public class AbstractClassPointer extends Instruction {
    public int classIndex;

    public AbstractClassPointer(OpType type, int opcode, int length, int classIndex) {
        super(type, opcode, 1);
        this.classIndex = classIndex;
    }
}
