package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LSHL extends Instruction {
    public LSHL() {
        super(OpType.MATH, Instruction.LSHL, 1);
    }
}
