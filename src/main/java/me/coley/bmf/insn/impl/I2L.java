package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class I2L extends Instruction {
    public I2L() {
        super(OpType.TYPE_CONVERSION, Instruction.I2L, 1);
    }
}
