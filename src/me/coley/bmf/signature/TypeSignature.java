package me.coley.bmf.signature;

import java.util.Map;
import java.util.Map.Entry;

import me.coley.bmf.util.Box;

public class TypeSignature extends Signature {

    public TypeSignature(SigArg type) {
        this(type, null);
    }

    public TypeSignature(SigArg type, Map<String, Box<String>> genericLabelMap) {
        this.genericLabelMap = genericLabelMap;
        this.type = type;
    }

    @Override
    public String toSignature() {
        StringBuilder labelMapStr = new StringBuilder();
        if (genericLabelMap != null && genericLabelMap.size() > 0) {
            labelMapStr.append("<");
            for (Entry<String, Box<String>> entry : genericLabelMap.entrySet()) {
                labelMapStr.append(entry.getKey() + ":L" + entry.getValue().getValue() + ";");
            }
            labelMapStr.append(">");
        }
        return labelMapStr.toString() + type.toArg();
    }

}
