package ir.nrdc.utils;

import java.util.ArrayList;
import java.util.List;

public enum AgeGroup {
    A, B, C, D, E;

    public static List<String> getStringList() {
        List<String> ageGroupStringValues = new ArrayList<>();
        for (AgeGroup ag : AgeGroup.values()) {
            ageGroupStringValues.add(ag.name());
        }
        return ageGroupStringValues;
    }
}