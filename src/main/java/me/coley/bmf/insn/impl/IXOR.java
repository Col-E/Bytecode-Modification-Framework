package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IXOR extends Instruction {
    public IXOR() {
        super(OpType.MATH, Instruction.IXOR, 1);
    }
}
