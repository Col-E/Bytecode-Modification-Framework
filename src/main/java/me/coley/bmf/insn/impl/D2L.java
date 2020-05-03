package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class D2L extends Instruction {
    public D2L() {
        super(OpType.TYPE_CONVERSION, Instruction.D2L, 1);
    }
}
