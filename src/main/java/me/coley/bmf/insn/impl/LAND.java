package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LAND extends Instruction {
    public LAND() {
        super(OpType.MATH, Instruction.LAND, 1);
    }
}
