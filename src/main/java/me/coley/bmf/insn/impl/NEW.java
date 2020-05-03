package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractClassPointer;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class NEW extends AbstractClassPointer {

    public NEW(int classIndex) {
        super(OpType.ALLOCATION, Instruction.NEW, 3,classIndex);
    }
}
