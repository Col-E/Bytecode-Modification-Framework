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

    public MemberMapping getMemberMapping(String name, String desc) {
        for (MemberMapping data : members) {
            // TODO: Determine if using the original values is the best
            // practice.
            if (data.name.original.equals(name) && data.desc.original.equals(desc)) { return data; }
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

    /**
     * Returns a list of members matching the given name.
     * 
     * @param name
     * @return
     */
    public List<MemberMapping> getMembersByName(String name) {
        List<MemberMapping> list = new ArrayList<MemberMapping>();
        for (MemberMapping mm : members) {
            if (mm.name.original.equals(name)) list.add(mm);
        }
        return list;
    }

}
