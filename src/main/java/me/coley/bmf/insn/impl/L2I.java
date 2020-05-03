package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class L2I extends Instruction {
    public L2I() {
        super(OpType.TYPE_CONVERSION, Instruction.L2I, 1);
    }
}
