package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class L2D extends Instruction {
    public L2D() {
        super(OpType.TYPE_CONVERSION, Instruction.L2D, 1);
    }
}
