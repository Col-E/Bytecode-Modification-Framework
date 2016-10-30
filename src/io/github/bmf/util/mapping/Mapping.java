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
    private final Map<String, MemberMapping> descToMember = new HashMap<String, MemberMapping>();

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
        setup(java.util.List.class);
    }

    /**
     * Creates immutable mappings from a given class.
     * 
     * @param c
     */
    private void setup(Class<?> c) {
        String name = c.getName().replace(".", "/");
        if (!mappings.containsKey(name)) {
            ClassMapping cm = new ClassMapping(name);
            for (Method m : c.getMethods()) {
                cm.addMember(this, new MemberMapping(new ImmutableBox<String>(m.getName()),
                        Type.method(this, Type.getMethodDescriptor(m))));
            }
        }
    }

    /**
     * Retrieves the Boxed name of a class.
     * 
     * @param name
     * @return
     */
    public Box<String> getClassName(String name) {
        if (hasMapping(name)) {
            return mappings.get(name).name;
        } else {
            mappings.put(name, new ClassMapping(name));
            return getClassName(name);
        }
    }

    /**
     * Adds a ClassMapping to the mappings map.
     * 
     * @param mapping
     */
    public void addMapping(ClassMapping mapping) {
        mappings.put(mapping.original, mapping);
    }

    /**
     * Retrieves a ClassMapping from the mappings map.
     * 
     * @param name
     *            The original name of the class. The value will be the
     *            equivalent of <i>ClassMapping.original</i>.
     * 
     * @return
     */
    public ClassMapping getMapping(String name) {
        return mappings.get(name);
    }

    /**
     * Checks if a mappings map has a given entry.
     * 
     * @param name
     * @return
     */
    public boolean hasMapping(String name) {
        return mappings.containsKey(name);
    }

    // ------------------------------------------- //
    // ------------------------------------------- //

    /**
     * Retrieves a MemberDescriptor given a class's name and the member
     * descriptor and name.
     * 
     * @param className
     * @param name
     *            Member's name
     * @param desc
     *            Member's desc
     * @return <i>null</i> if not such member exists
     */
    public MemberDescriptor getDesc(String className, String desc) {
        ClassMapping cm = getMapping(className);
        if (cm != null) {
            for (MemberMapping mm : cm.getMembers()) {
                if (mm.desc.original.equals(desc)) { return mm.desc; }
            }
        }
        return null;
    }

    public boolean hasDesc(String name, String v) {
        if (hasMapping(name)) {
            ClassMapping cm = getMapping(name);
            for (MemberMapping mm : cm.getMembers()) {
                if (mm.desc.original.equals(v)) { return true; }
            }
        }
        return false;
    }

    // ------------------------------------------- //
    // ------------------------------------------- //

    /**
     * Checks if the given MemberMapping type can be replaced with a stored
     * instance. If no stored instance exists it becomes the stored instance.
     * This is used for synchronizing method modifications across the class
     * hierarchy.
     * 
     * @param mm
     * @return
     */
    public MemberMapping getMemberInstance(MemberMapping mm) {
        String key = mm.original + mm.desc.original;
        if (descToMember.containsKey(key)) {
            return descToMember.get(key);
        } else {
            descToMember.put(key, mm);
        }
        return mm;
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
