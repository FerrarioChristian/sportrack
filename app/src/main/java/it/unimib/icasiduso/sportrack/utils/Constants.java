package it.unimib.icasiduso.sportrack.utils;

public class Constants {
    public static final int MIN_PASSWORD_LENGTH = 8;

    public static final String API_BASE_URL = "https://api.api-ninjas.com/v1/";
    public static final String EXERCISES_ENDPOINT = "exercises";
    public static final String EXERCISES_MUSCLE_PARAMETER = "muscle";

    public static final String EXERCISE_DATABASE_NAME = "exercise_db";
    public static final int DATABASE_VERSION = 1;

    public static final String INVALID_USER_ERROR = "invalidUserError";
    public static final String INVALID_CREDENTIALS_ERROR = "invalidCredentials";
    public static final String USER_COLLISION_ERROR = "userCollisionError";
    public static final String WEAK_PASSWORD_ERROR = "passwordIsWeak";

    public static final String[] MUSCLES = new String[]{"abdominals", "biceps", "chest", "glutes"
            , "hamstrings", "lats", "quadriceps", "triceps", "middle_back", "lower_back"};

    public static final String[] CAROUSEL_IMAGES = {
            "https://img.freepik.com/free-psd/gym-fitness-horizontal-banner-template_23-2149084698.jpg?w=1060&t=st=1725810165~exp=1725810765~hmac=4fabb6a834bfd16816edb749bc2000507c89ead4f51e8ddccf20f24ca5eae547",
            "https://img.freepik.com/free-psd/gradient-sport-banner-template-theme_23-2148531778.jpg?t=st=1725810266~exp=1725813866~hmac=ed6224e52f0a57f847ce9c0c88c8b871e92a2556569461c9af33d489b43ff538&w=996",
            "https://img.freepik.com/free-psd/sport-banner-template_23-2148520659.jpg?t=st=1725810344~exp=1725813944~hmac=b4a077394273dcbfbc2e02fe565f7e8bd5d8815e52554967bedcfce175c617e9&w=996",
            "https://img.freepik.com/premium-psd/gym-training-landing-page_23-2149053492.jpg?w=996",
            "https://img.freepik.com/free-psd/gym-fitness-youtube-cover-template_23-2149584713.jpg?w=1060&t=st=1725810393~exp=1725810993~hmac=24eb9383236304cf550ee569c8bf66656348a232b3cf5a2773018a0d2dc0ce2a",
    };

    public static final String FIREBASE_DATABASE = "users/";

}
