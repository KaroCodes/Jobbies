package com.androidcamp.jobbies;

import java.util.Arrays;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class JobCategory {

    private static String[] categories = {
            "CHOOSE CATEGORY...",
            "ANIMALS",
            "BABYSITTING",
            "GARDEN",
            "HOUSE WORKS",
            "FIXING",
            "SHOPPING"
    };

    public static String[] getCategories() {
        return categories;
    }
}
