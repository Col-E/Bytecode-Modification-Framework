package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LMUL extends Instruction {
    public LMUL() {
        super(OpType.MATH, Instruction.LMUL, 1);
    }
}
