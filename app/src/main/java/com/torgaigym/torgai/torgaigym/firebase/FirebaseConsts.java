package com.torgaigym.torgai.torgaigym.firebase;

public class FirebaseConsts {

    public static final String dayExerciseId = "dayExercise";
    public static final String dayOne = "dayOne";
    public static final String dayTwo = "dayTwo";
    public static final String dayThree = "dayThree";
    public static final String dayFour = "dayFour";
    public static final String dayFive = "dayFive";
    public static final String exerciseN = "exerciseN";

    public static String getDayConstByDayPosition(int position) {
        switch (position) {
            case 0:
                return dayOne;
            case 1:
                return dayTwo;
            case 2:
                return dayThree;
            case 3:
                return dayFour;
            case 4:
                return dayFive;
        }
        return null;
    }

}
