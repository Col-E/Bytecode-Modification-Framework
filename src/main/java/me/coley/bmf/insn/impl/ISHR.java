package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class ISHR extends Instruction {
    public ISHR() {
        super(OpType.MATH, Instruction.ISHR, 1);
    }
}
