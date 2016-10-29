package io.github.bmf;

import com.google.common.collect.Lists;
import io.github.bmf.attribute.*;
import io.github.bmf.attribute.annotation.AttributeAnnotations;
import io.github.bmf.attribute.annotation.AttributeParameterAnnotations;
import io.github.bmf.attribute.clazz.*;
import io.github.bmf.consts.Constant;
import io.github.bmf.util.ConstUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A class.
 *
 * @author Matt
 */
@SuppressWarnings("rawtypes")
public class ClassNode implements AttributeOwner {
    /**
     * Version info.
     */
    public int major = -1, minor = -1;
    /**
     * Access. May be multiple combined.
     */
    public int access;
    /**
     * Indices in the constant pool pointing to a class name.
     */
    public int classIndex, superIndex;
    /**
     * A list of indices in the constant pool pointing to classes that are
     * implemented.
     */
    public List<Integer> interfaceIndices;
    /**
     * The constant pool.
     */
    public List<Constant> constants;
    /**
     * Fields.
     */
    public List<FieldNode> fields;
    /**
     * Methods.
     */
    public List<MethodNode> methods;
    /**
     * Attribute: If not-null, the class is deprecated.
     */
    public AttributeDeprecated deprecated;
    /**
     * Attribute: If not-null, the class is synthetic.
     */
    public AttributeSynthetic synthetic;
    /**
     * Attribute: Signature for generic information.
     */
    public AttributeSignature signature;
    /**
     * Attribute: Annotations
     */
    public AttributeAnnotations runtimeInvisibleAnnotations;
    /**
     * Attribute: Annotations
     */
    public AttributeAnnotations runtimeVisibleAnnotations;
    /**
     * Attribute: Annotations
     */
    public AttributeParameterAnnotations runtimeInvisibleParamAnnotations;
    /**
     * Attribute: Annotations
     */
    public AttributeParameterAnnotations runtimeVisibleParamAnnotations;
    /**
     * Attribute: Source file information
     */
    public AttributeSourceFile sourceFile;
    /**
     * Attribute: The method this <i>(anonymouse)</i> class resides in.
     */
    public AttributeEnclosingMethod enclosingMethod;
    /**
     * Attribute: Inner classes
     */
    public AttributeInnerClasses innerClasses;
    /**
     * Attribute: InvokeDynamic bootstrap method information.
     */
    public AttributeBootstrapMethods bootstrapMethods;
    /**
     * Attribute: Debug information.
     */
    public AttributeSourceDebugExtension sourceDebug;

    /**
     * Adds a constant to the constant pool.
     *
     * @param constant
     */
    public void addConst(Constant constant) {
        constants.add(constant);
    }

    /**
     * Retrieves a constant from the constant pool. Index starts at 1.
     *
     * @param index
     * @return
     */
    public Constant getConst(int index) {
        return constants.get(index - 1);
    }

    /**
     * Sets or extends the constant pool to a given size.
     * 
     * @param size
     */
    public void setPoolSize(int size) {
        if (constants == null) {
            constants = new ArrayList<Constant>(size);
        } else {
            ((ArrayList<Constant>) constants).ensureCapacity(size);
        }
    }

    /**
     * Adds an interface index to the class indices list.
     *
     * @param index
     */
    public void addInterfaceIndex(int index) {
        interfaceIndices.add(index);
    }

    /**
     * Sets or extends the size of the interface index list to a given size.
     * 
     * @param size
     */
    public void setInterfaceCount(int size) {
        if (interfaceIndices == null) {
            interfaceIndices = new ArrayList<Integer>(size);
        } else {
            ((ArrayList<Integer>) interfaceIndices).ensureCapacity(size);
        }
    }

    /**
     * Adds a field to the class.
     *
     * @param field
     */
    public void addField(FieldNode field) {
        fields.add(field);
    }

    /**
     * Sets or extends the size of the field list to a given size.
     * 
     * @param size
     */
    public void setFieldCount(int size) {
        if (fields == null) {
            fields = new ArrayList<FieldNode>(size);
        } else {
            ((ArrayList<FieldNode>) fields).ensureCapacity(size);
        }
    }

    /**
     * Adds a method to the class.
     *
     * @param method
     */
    public void addMethod(MethodNode method) {
        methods.add(method);
    }

    /**
     * Sets or extends the size of the method list to a given size.
     * 
     * @param size
     */
    public void setMethodCount(int size) {
        if (methods == null) {
            methods = new ArrayList<MethodNode>(size);
        } else {
            ((ArrayList<MethodNode>) methods).ensureCapacity(size);
        }

    }

    @Override
    public void addAttribute(Attribute attribute) {
        switch (attribute.type) {
        case SOURCE_FILE:
            sourceFile = (AttributeSourceFile) attribute;
            break;
        case SOURCE_DEBUG_EXTENSION:
            sourceDebug = (AttributeSourceDebugExtension) attribute;
            break;
        case ENCLOSING_METHOD:
            enclosingMethod = (AttributeEnclosingMethod) attribute;
            break;
        case INNER_CLASSES:
            innerClasses = (AttributeInnerClasses) attribute;
            break;
        case BOOTSTRAP_METHODS:
            bootstrapMethods = (AttributeBootstrapMethods) attribute;
            break;
        case SYNTHETIC:
            synthetic = (AttributeSynthetic) attribute;
            break;
        case SIGNATURE:
            signature = (AttributeSignature) attribute;
            break;
        case DEPRECATED:
            deprecated = (AttributeDeprecated) attribute;
            break;
        case RUNTIME_VISIBLE_ANNOTATIONS:
            runtimeVisibleAnnotations = (AttributeAnnotations) attribute;
            break;
        case RUNTIME_INVISIBLE_ANNOTATIONS:
            runtimeInvisibleAnnotations = (AttributeAnnotations) attribute;
            break;
        case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
            runtimeVisibleParamAnnotations = (AttributeParameterAnnotations) attribute;
            break;
        case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
            runtimeInvisibleParamAnnotations = (AttributeParameterAnnotations) attribute;
            break;
        default:
            break;
        }
    }

    @Override
    public List<Attribute> getAttributes() {
        List<Attribute> attributes = Lists.newArrayList();
        if (sourceFile != null) attributes.add(sourceFile);
        if (bootstrapMethods != null) attributes.add(bootstrapMethods);
        if (innerClasses != null) attributes.add(innerClasses);
        if (signature != null) attributes.add(signature);
        if (runtimeInvisibleAnnotations != null) attributes.add(runtimeInvisibleAnnotations);
        if (runtimeVisibleAnnotations != null) attributes.add(runtimeVisibleAnnotations);
        if (runtimeVisibleParamAnnotations != null) attributes.add(runtimeVisibleParamAnnotations);
        if (runtimeInvisibleParamAnnotations != null) attributes.add(runtimeInvisibleParamAnnotations);
        if (deprecated != null) attributes.add(deprecated);
        if (synthetic != null) attributes.add(synthetic);
        if (enclosingMethod != null) attributes.add(enclosingMethod);
        if (sourceDebug != null) attributes.add(sourceDebug);
        return attributes;
    }

    @Override
    public String toString() {
        String out = "  Version: " + major + "." + minor + "\n";
        out += "  Access: " + access + "\n";
        out += "  Class Index: " + ConstUtil.getName(this) + "\n";
        out += "  Super Index: " + ConstUtil.getSuperName(this) + "\n";
        out += "  Interfaces { ";
        for (int index : interfaceIndices) {
            out += getConst(index + 1).toString() + ", ";
        }
        int interfaceCut = interfaceIndices.size() == 0 ? 0 : 2;
        out = out.substring(0, out.length() - interfaceCut) + " }\n";
        out += "  Constant Pool {\n";
        for (Constant constant : constants) {
            out += "    " + constant.toString() + "\n";
        }
        out += "  }\n";
        out += "  Fields { \n";
        for (FieldNode field : fields) {
            out += "    " + field.toString() + "\n";
        }
        out += "  }\n";
        out += "  Methods { \n";
        for (MethodNode method : methods) {
            out += "    " + method.toString() + "\n";
        }
        out += "  }\n";
        return out;
    }
}