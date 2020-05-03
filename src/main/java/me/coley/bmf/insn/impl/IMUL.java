package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IMUL extends Instruction {
    public IMUL() {
        super(OpType.MATH, Instruction.IMUL, 1);
    }
}
