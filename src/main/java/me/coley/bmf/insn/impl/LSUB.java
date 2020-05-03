package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LSUB extends Instruction {
    public LSUB() {
        super(OpType.MATH, Instruction.LSUB, 1);
    }
}
