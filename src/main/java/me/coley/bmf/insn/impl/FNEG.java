package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FNEG extends Instruction {
    public FNEG() {
        super(OpType.MATH, Instruction.FNEG, 1);
    }
}
