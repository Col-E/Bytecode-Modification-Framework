package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractRETURN;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;
import me.coley.bmf.type.Type;

public class ARETURN extends AbstractRETURN {
    public ARETURN() {
        super(OpType.RETURN, Instruction.DRETURN, Type.OBJECT);
    }
}
