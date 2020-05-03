package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractClassPointer;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class MULTIANEWARRAY extends AbstractClassPointer {
    public int dimensions;

    public MULTIANEWARRAY(int classIndex, int dimensions) {
        super(OpType.ARRAY, Instruction.ANEWARRAY, 3, classIndex);
        this.dimensions = dimensions;
    }
}
