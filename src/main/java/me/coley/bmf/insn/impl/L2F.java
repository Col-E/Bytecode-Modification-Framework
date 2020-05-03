package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class L2F extends Instruction {
    public L2F() {
        super(OpType.TYPE_CONVERSION, Instruction.L2F, 1);
    }
}
