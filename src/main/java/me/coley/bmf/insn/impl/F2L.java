package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class F2L extends Instruction {
    public F2L() {
        super(OpType.TYPE_CONVERSION, Instruction.F2L, 1);
    }
}
