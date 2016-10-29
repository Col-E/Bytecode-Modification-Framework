package io.github.bmf.util.mapping;

import java.util.HashMap;
import java.util.Map;

import io.github.bmf.consts.ConstantType;
import io.github.bmf.util.Box;
import io.github.bmf.util.descriptors.MemberDescriptor;

public class Mapping {
    private final Map<String, ClassMapping> mappings = new HashMap<String, ClassMapping>();
    // TODO: Idea is to given a class find way of detecting parent
    // Unsure if it should be <Class, String> or <Class, Class>
    // Unsure of exact future usage
    private final Map<ClassMapping, ClassMapping> parents = new HashMap<ClassMapping, ClassMapping>();

    public void addMapping(ClassMapping mapping) {
        mappings.put(mapping.name.value, mapping);
    }

    public Box<String> getClassName(String name) {
        if (hasClass(name)) {
            return mappings.get(name).name;
        } else {
            mappings.put(name, new ClassMapping(name));
            return getClassName(name);
        }
    }

    public ClassMapping getMapping(String name) {
        return mappings.get(name);
    }

    public boolean hasClass(String name) {
        return mappings.containsKey(name);
    }

    public MemberDescriptor getDesc(String name, String v) {
        if (hasClass(name)){
            ClassMapping cm = getMapping(name);
            for (MemberMapping mm : cm.members){
                if (mm.desc.original.equals(v)){
                    return mm.desc;
                }
            }
        }
        return null;
    }

    public boolean hasDesc(String name, String v) {
        if (hasClass(name)){
            ClassMapping cm = getMapping(name);
            for (MemberMapping mm : cm.members){
                if (mm.desc.original.equals(v)){
                    return true;
                }
            }
        }
        return false;
    }

}
