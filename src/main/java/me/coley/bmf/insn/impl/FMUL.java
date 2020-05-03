package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FMUL extends Instruction {
    public FMUL() {
        super(OpType.MATH, Instruction.FMUL, 1);
    }
}
