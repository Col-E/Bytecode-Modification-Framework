package io.github.bmf.util.mapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.bmf.type.Type;
import io.github.bmf.util.Box;
import io.github.bmf.util.ImmutableBox;
import io.github.bmf.util.descriptors.MemberDescriptor;

public class Mapping {
    private final Map<String, ClassMapping> mappings = new HashMap<String, ClassMapping>();
    private final Map<ClassMapping, ClassMapping> parents = new HashMap<ClassMapping, ClassMapping>();
    private final Map<ClassMapping, List<ClassMapping>> interfaces = new HashMap<ClassMapping, List<ClassMapping>>();

    // TODO: Figure out approach:
    // 
    // This isn't permanent and just serves as an example.
    // But it shows an idea that will be important.
    // For mappings of methods to understand inheritcance
    // the basic classes will have to be mapped.
    // The issue becomes how to read them while being greedy
    // and not reading all of the default classpath (rt.jar).
    public Mapping() {
        setup(java.lang.Comparable.class);
        setup(java.lang.Object.class);
        setup(java.util.Iterator.class);
        setup(java.util.Collection.class);
    }

    private void setup(Class<?> c) {
        String name = c.getName().replace(".", "/");
        if (!mappings.containsKey(name)) {
            ClassMapping cm = new ClassMapping(name);
            for (Method m : c.getMethods()) {
                cm.members.add(new MemberMapping(new ImmutableBox<String>(m.getName()), Type.method(this, Type.getMethodDescriptor(m))));
            }
        }
    }

    public Box<String> getClassName(String name) {
        if (hasMapping(name)) {
            return mappings.get(name).name;
        } else {
            mappings.put(name, new ClassMapping(name));
            return getClassName(name);
        }
    }

    public void addMapping(ClassMapping mapping) {
        mappings.put(mapping.name.getValue(), mapping);
    }

    public ClassMapping getMapping(String name) {
        return mappings.get(name);
    }

    public boolean hasMapping(String name) {
        return mappings.containsKey(name);
    }

    // ------------------------------------------- //
    // ------------------------------------------- //

    public MemberDescriptor getDesc(String name, String v) {
        if (hasMapping(name)) {
            ClassMapping cm = getMapping(name);
            for (MemberMapping mm : cm.members) {
                if (mm.desc.original.equals(v)) { return mm.desc; }
            }
        }
        return null;
    }

    public boolean hasDesc(String name, String v) {
        if (hasMapping(name)) {
            ClassMapping cm = getMapping(name);
            for (MemberMapping mm : cm.members) {
                if (mm.desc.original.equals(v)) { return true; }
            }
        }
        return false;
    }

    // ------------------------------------------- //
    // ------------------------------------------- //

    public ClassMapping getParent(ClassMapping child) {
        return parents.get(child);
    }

    public void setParent(ClassMapping child, ClassMapping parent) {
        parents.put(child, parent);
    }

    public boolean hasParent(ClassMapping child) {
        return parents.containsKey(child);
    }

    // ------------------------------------------- //
    // ------------------------------------------- //

    public List<ClassMapping> getInterfaces(ClassMapping child) {
        return interfaces.get(child);
    }

    public void addInterface(ClassMapping child, ClassMapping interfacee) {
        if (!hasInterfaces(child)) {
            interfaces.put(child, new ArrayList<ClassMapping>());
        }
        interfaces.get(child).add(interfacee);
    }

    public boolean hasInterfaces(ClassMapping child) {
        return interfaces.containsKey(child);
    }
}
