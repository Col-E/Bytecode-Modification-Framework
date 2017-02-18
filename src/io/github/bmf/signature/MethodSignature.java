package io.github.bmf.signature;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.github.bmf.util.Box;

public class MethodSignature extends Signature {
    private List<SigArg> parameters;

    public MethodSignature(Map<String, Box<String>> genericLabelMap, List<SigArg> parameters, SigArg retType) {
        this.genericLabelMap = genericLabelMap;
        this.parameters = parameters;
        this.type = retType;
    }

    public List<SigArg> getMethodParams() {
        return parameters;
    }

    @Override
    public String toSignature() {
        // Build map of generic types is defined by the method signature
        StringBuilder labelMapStr = new StringBuilder();
        if (genericLabelMap != null && genericLabelMap.size() > 0) {
            labelMapStr.append("<");
            for (Entry<String, Box<String>> entry : genericLabelMap.entrySet()) {
                labelMapStr.append(entry.getKey() + ":L" + entry.getValue().getValue() + ";");
            }
            labelMapStr.append(">");
        }
        // List args
        StringBuilder args = new StringBuilder();
        for (SigArg arg : parameters) {
            args.append(arg.toArg());
        }
        // Combine
        return labelMapStr.toString() + "(" + args.toString() + ")" + type.toArg();
    }
}
