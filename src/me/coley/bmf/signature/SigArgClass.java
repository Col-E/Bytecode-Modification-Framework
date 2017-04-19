package me.coley.bmf.signature;

import java.util.List;

import me.coley.bmf.util.Box;

public class SigArgClass extends SigArg {
    /**
     * The class's name
     */
    public final Box<String> className;
    /**
     * Generic args of classes with generic types.
     */
    public final List<SigArg> genericArgs;

    public SigArgClass(Box<String> className, List<SigArg> genericArgs) {
        this.className = className;
        this.genericArgs = genericArgs;
    }

    @Override
    String toArg() {
        if (genericArgs == null) {
            return "L" + className.getValue() + ";";
        } else {
            StringBuilder combo = new StringBuilder();
            for (SigArg arg : genericArgs) {
                combo.append(arg.toArg()) ;
            }
            return "L" + className.getValue() + "<" + combo.toString() + ">;";
        }
    }
}
