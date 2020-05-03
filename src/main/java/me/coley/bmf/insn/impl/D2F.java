package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class D2F extends Instruction {
    public D2F() {
        super(OpType.TYPE_CONVERSION, Instruction.D2F, 1);
    }
}
