package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractClassPointer;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class INSTANCEOF extends AbstractClassPointer {

    public INSTANCEOF(int classIndex) {
        super(OpType.TYPE_CONVERSION, Instruction.INSTANCEOF, 3,classIndex);
    }
}
