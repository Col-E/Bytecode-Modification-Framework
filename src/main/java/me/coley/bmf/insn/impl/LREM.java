package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LREM extends Instruction {
    public LREM() {
        super(OpType.MATH, Instruction.LREM, 1);
    }
}
