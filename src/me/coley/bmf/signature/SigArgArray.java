package me.coley.bmf.signature;

public class SigArgArray extends SigArg {
    private final SigArg inner;

    public SigArgArray(SigArg inner) {
        this.inner = inner;
    }

    @Override
    String toArg() {
        return "[" + inner.toArg();
    }
}
