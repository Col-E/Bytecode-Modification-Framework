package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractRETURN;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;
import me.coley.bmf.type.Type;

public class FRETURN extends AbstractRETURN {
    public FRETURN() {
        super(OpType.RETURN, Instruction.FRETURN, Type.FLOAT);
    }
}
