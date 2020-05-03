package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LSHR extends Instruction {
    public LSHR() {
        super(OpType.MATH, Instruction.LSHR, 1);
    }
}
