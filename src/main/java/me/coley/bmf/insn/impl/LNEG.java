package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LNEG extends Instruction {
    public LNEG() {
        super(OpType.MATH, Instruction.LNEG, 1);
    }
}
