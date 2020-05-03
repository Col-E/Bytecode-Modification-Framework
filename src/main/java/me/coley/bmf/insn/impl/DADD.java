package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DADD extends Instruction {
    public DADD() {
        super(OpType.MATH, Instruction.DADD, 1);
    }
}
