package me.coley.bmf.signature;

public class SigArgGeneric extends SigArg {
    /**
     * The name of the generic (T, K, V, etc.)
     */
    public final String genericName;

    public SigArgGeneric(String genericName) {
        this.genericName = genericName;
    }

    @Override
    String toArg() {
        return "T" + genericName + ";";
    }
}
