package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LUSHR extends Instruction {
    public LUSHR() {
        super(OpType.MATH, Instruction.LUSHR, 1);
    }
}
