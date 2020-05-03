package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class D2I extends Instruction {
    public D2I() {
        super(OpType.TYPE_CONVERSION, Instruction.D2I, 1);
    }
}
