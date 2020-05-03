package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class F2I extends Instruction {
    public F2I() {
        super(OpType.TYPE_CONVERSION, Instruction.F2I, 1);
    }
}
