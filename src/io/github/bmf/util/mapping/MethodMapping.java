package io.github.bmf.util.mapping;

import java.util.ArrayList;
import java.util.List;

import io.github.bmf.util.Box;
import io.github.bmf.util.descriptors.MemberDescriptor;

public class MethodMapping extends MemberMapping {
    private final List<MemberMapping> variables = new ArrayList<MemberMapping>();

    public MethodMapping(Box<String> name, MemberDescriptor desc) {
        super(name, desc);
    }
    
    public void addVariable(Mapping mapping, MemberMapping mm) {
        variables.add(mapping.getMemberInstance(mm));
    }

}
