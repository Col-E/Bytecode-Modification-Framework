package io.github.bmf.util.mapping;

import java.util.ArrayList;
import java.util.List;

import io.github.bmf.util.Box;

public class ClassMapping {
    public final String original;
    public final Box<String> name;
    private final List<MemberMapping> members = new ArrayList<MemberMapping>();

    public ClassMapping(String name) {
        this.original = name;
        this.name = new Box<String>(name);
    }

    public MemberMapping getMemberData(String name, String desc) {
        for (MemberMapping data : members) {
            // TODO: Instead of name.getValue should the original value (field:
            // original) be used? Would make it easier for users to get the
            // correct member after renaming it.
            // Could make this idea into a boolean parameter
            if (data.name.getValue().equals(name) && data.desc.toString().equals(desc)) { return data; }
        }
        return null;
    }

    public List<MemberMapping> getMembers() {
        return members;
    }

    /**
     * Adds a member to the class. The passed member is replaced with another
     * Member instance if the name and descriptor match the stored version.
     * 
     * @param mapping
     * @param mm
     */
    public void addMember(Mapping mapping, MemberMapping mm) {
        members.add(mapping.getMemberInstance(mm));
    }

}
