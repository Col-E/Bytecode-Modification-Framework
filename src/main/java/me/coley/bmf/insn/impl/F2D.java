package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class F2D extends Instruction {
    public F2D() {
        super(OpType.TYPE_CONVERSION, Instruction.F2D, 1);
    }
}
