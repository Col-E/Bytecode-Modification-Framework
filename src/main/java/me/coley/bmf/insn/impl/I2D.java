package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class I2D extends Instruction {
    public I2D() {
        super(OpType.TYPE_CONVERSION, Instruction.I2D, 1);
    }
}
