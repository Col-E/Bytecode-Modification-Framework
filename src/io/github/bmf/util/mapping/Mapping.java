package io.github.bmf.util.mapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.bmf.ClassNode;
import io.github.bmf.FieldNode;
import io.github.bmf.MethodNode;
import io.github.bmf.type.Type;
import io.github.bmf.type.descriptors.MemberDescriptor;
import io.github.bmf.util.Box;
import io.github.bmf.util.ConstUtil;
import io.github.bmf.util.ImmutableBox;

public class Mapping {

    private final Map<String, ClassMapping> mappings = new HashMap<String, ClassMapping>();
    private final Map<ClassMapping, List<ClassMapping>> parents = new HashMap<ClassMapping, List<ClassMapping>>();
    private final Map<ClassMapping, List<ClassMapping>> interfaces = new HashMap<ClassMapping, List<ClassMapping>>();
    private final Map<ClassMapping, List<ClassMapping>> children = new HashMap<ClassMapping, List<ClassMapping>>();
    private final Map<String, MemberMapping> descToMember = new HashMap<String, MemberMapping>();

    /**
     * Generates a ClassMapping from a given ClassNode. The generated
     * ClassMapping is automatically added to the mappings map.
     * 
     * @param node
     * @return
     */
    public ClassMapping makeMappingFromNode(ClassNode node) {
        String name = ConstUtil.getName(node);
        ClassMapping cm = new ClassMapping(name);
        mappings.put(name, cm);
        return cm;
    }

    public void addMemberMappings(ClassNode node) {
        ClassMapping cm = getMapping(ConstUtil.getName(node));
        for (MethodNode mn : node.methods) {
            cm.addMember(this, new MethodMapping(new Box<String>(ConstUtil.getUTF8String(node, mn.name)),
                    Type.method(this, ConstUtil.getUTF8String(node, mn.desc))));
        }
        for (FieldNode fn : node.fields) {
            cm.addMember(this, new MemberMapping(new Box<String>(ConstUtil.getUTF8String(node, fn.name)),
                    Type.variable(this, ConstUtil.getUTF8String(node, fn.desc))));
        }
    }

    /**
     * Generates a ClassMapping from a class loaded into the classpath. The
     * generated ClassMapping is automatically added to the mappings map.
     * 
     * @param name
     *            Class name
     * @param onlyPublic
     *            Only makes mappings for public members
     * @param fields
     *            Whether to make MemberMappings for fields
     * @return
     * @throws ClassNotFoundException
     */
    public ClassMapping makeMappingFromRuntime(String name, boolean onlyPublic, boolean fields)
            throws ClassNotFoundException {
        Class<?> c = Class.forName(name.replace("/", "."));
        ClassMapping cm = new ClassMapping(name);
        mappings.put(name, cm);
        for (Method m : c.getMethods()) {
            if (!onlyPublic || (m.getModifiers() & Modifier.PRIVATE) != Modifier.PRIVATE) {
            cm.addMember(this, new MethodMapping(new ImmutableBox<String>(m.getName()),
                    Type.method(this, Type.getMethodDescriptor(m))));
             }
        }
        if (fields) {
            for (Field f : c.getFields()) {
                if (!onlyPublic || (f.getModifiers() & Modifier.PRIVATE) != Modifier.PRIVATE) {
                    cm.addMember(this, new MemberMapping(new ImmutableBox<String>(f.getName()),
                            Type.variable(this, Type.getDescriptorForClass(f.getType()))));
                }
            }
        }
        return cm;
    }

    /**
     * Retrieves the Boxed name of a class. Throws an exception if the boxed
     * name could not be located.
     * 
     * @param name
     *            Class name
     * 
     * @return
     */
    public Box<String> getClassNameOrCreate(String name) {
        if (hasMapping(name)) {
            return mappings.get(name).name;
        } else {
            try {
                ClassMapping cm = makeMappingFromRuntime(name, false, false);
                if (cm != null) return cm.name;
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Requested unmapped class: " + name);
        }
    }

    /**
     * Retrieves the Boxed name of a class.
     * 
     * @param name
     * @return
     */
    public Box<String> getClassName(String name) {
        return getClassName(name, false);
    }

    /**
     * Retrieves the Boxed name of a class.
     * 
     * @param name
     * @param throwIfNotFound
     *            RuntimeExcepton thrown if the class coult not be found.
     * @return
     */
    public Box<String> getClassName(String name, boolean throwIfNotFound) {
        // Return if cached
        if (hasMapping(name)) return mappings.get(name).name;
        // If the class is in the default classpath
        // but not mapped create it on the fly.
        if (name.startsWith("java")) return getClassNameOrCreate(name);
        // We have a problem...
        else {
            if (throwIfNotFound) throw new RuntimeException(name);
            ClassMapping cm = new ClassMapping(new ImmutableBox<String>(name));
            addMapping(cm);
            return cm.name;
        }
    }

    /**
     * Adds a ClassMapping to the mappings map.
     * 
     * @param mapping
     */
    public void addMapping(ClassMapping mapping) {
        mappings.put(mapping.name.original, mapping);
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
    public MemberDescriptor getDesc(String className, String name, String desc) {
        ClassMapping cm = getMapping(className);
        if (cm != null) {
            for (MemberMapping mm : cm.getMembers()) {
                if (mm.desc.original.equals(desc) && mm.name.original.equals(name)) { return mm.desc; }
            }
        }
        return null;
    }

    public List<MemberDescriptor> getDescs(String className, String desc) {
        List<MemberDescriptor> list = new ArrayList<MemberDescriptor>();
        ClassMapping cm = getMapping(className);
        if (cm != null) {
            for (MemberMapping mm : cm.getMembers()) {
                if (mm.desc.original.equals(desc)) {
                    list.add(mm.desc);
                }
            }
        }
        return list;
    }

    public boolean hasDesc(String className, String name, String desc) {
        if (hasMapping(className)) {
            ClassMapping cm = getMapping(className);
            for (MemberMapping mm : cm.getMembers()) {
                if (mm.desc.original.equals(desc) && mm.name.original.equals(name)) { return true; }
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

    /**
     * Returns the first <i>(the only for non-interfaces)</i> parent of a given
     * ClassMapping.
     * 
     * @param child
     * @return
     */
    public ClassMapping getParent(ClassMapping child) {
        if (!hasParents(child)) return null;
        return parents.get(child).get(0);
    }

    /**
     * Returns a list of parents <i>(only interfaces have multiple parents)</i>
     * of a given ClassMapping.
     * 
     * @param child
     * @return
     */
    public List<ClassMapping> getParents(ClassMapping child) {
        return parents.get(child);
    }

    public void addParent(ClassMapping child, ClassMapping interfacee) {
        if (!hasInterfaces(child)) {
            parents.put(child, new ArrayList<ClassMapping>());
        }
        parents.get(child).add(interfacee);
    }

    public boolean hasParents(ClassMapping child) {
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

    // ------------------------------------------- //
    // ------------------------------------------- //

    public List<ClassMapping> getChildren(ClassMapping parent) {
        return children.get(parent);
    }

    public void addChild(ClassMapping parent, ClassMapping child) {
        if (!hasChildren(parent)) {
            children.put(parent, new ArrayList<ClassMapping>());
        }
        children.get(parent).add(child);
    }

    public boolean hasChildren(ClassMapping parent) {
        return children.containsKey(parent);
    }

    // ------------------------------------------- //
    // ------------------------------------------- //

    public MemberMapping getMemberMapping(ClassMapping owner, String name, String desc) {
        // Fail
        if (owner == null) return null;
        // Check given class
        MemberMapping m = owner.getMemberMapping(name, desc);
        if (m != null) return m;
        // Check parents
        if (parents.containsKey(owner)) {
            for (ClassMapping parentMapping : parents.get(owner)) {
                MemberMapping mp = getMemberMapping(parentMapping, name, desc);
                if (mp != null) return mp;
            }
        }
        // Check interfaces
        if (interfaces.containsKey(owner)) {
            for (ClassMapping interfaceMapping : interfaces.get(owner)) {
                MemberMapping mi = getMemberMapping(interfaceMapping, name, desc);
                if (mi != null) return mi;
            }
        }
        // Fugg
        return null;

    }
}
