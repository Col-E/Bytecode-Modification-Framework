package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LDIV extends Instruction {
    public LDIV() {
        super(OpType.MATH, Instruction.LDIV, 1);
    }
}
