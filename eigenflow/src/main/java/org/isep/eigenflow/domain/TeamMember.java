package org.isep.eigenflow.domain;

import java.util.ArrayList;
import java.util.List;

public class TeamMember {
    private final String name;
    private final List<String> tasks;

    public TeamMember(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }


}
