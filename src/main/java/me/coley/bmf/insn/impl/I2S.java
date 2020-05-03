package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class I2S extends Instruction {
    public I2S() {
        super(OpType.TYPE_CONVERSION, Instruction.I2S, 1);
    }
}
