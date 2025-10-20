package com.gymapp.net;

import java.util.List;

// Use your existing managers (package names must match yours)
import com.gymapp.AssignmentFileManager;

public class Router {
    private static Router INSTANCE;
    public static synchronized Router get() {
        if (INSTANCE == null) INSTANCE = new Router();
        return INSTANCE;
    }

    public boolean isCoachOfMember(String coachId, String memberId) {
        return AssignmentFileManager.isCoachOfMember(coachId, memberId);
    }

    public String coachForMember(String memberId) {
        return AssignmentFileManager.getCoachForMember(memberId);
    }

    public List<String> membersForCoach(String coachId) {
        return AssignmentFileManager.getMembersForCoach(coachId);
    }
}
