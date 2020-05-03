package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractClassPointer;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class CHECKCAST extends AbstractClassPointer {

    public CHECKCAST(int classIndex) {
        super(OpType.TYPE_CONVERSION, Instruction.CHECKCAST, 3, classIndex);
    }
}
