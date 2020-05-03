package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractClassPointer;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class ANEWARRAY extends AbstractClassPointer {

    public ANEWARRAY(int classIndex) {
        super(OpType.ARRAY, Instruction.ANEWARRAY, 3, classIndex);
    }
}
