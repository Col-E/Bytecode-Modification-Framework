package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IDIV extends Instruction {
    public IDIV() {
        super(OpType.MATH, Instruction.IDIV, 1);
    }
}
