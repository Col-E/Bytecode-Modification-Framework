package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DMUL extends Instruction {
    public DMUL() {
        super(OpType.MATH, Instruction.DMUL, 1);
    }
}
