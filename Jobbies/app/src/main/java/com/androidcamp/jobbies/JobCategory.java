package com.androidcamp.jobbies;

import java.util.Arrays;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class JobCategory {

    private static String[] categories = {
        "Animals",
        "Babysitting",
        "Garden",
        "HouseWorks",
        "Fixing",
        "Shopping"
    };

    public static String[] getCategories() {
        return categories;
    }
}
