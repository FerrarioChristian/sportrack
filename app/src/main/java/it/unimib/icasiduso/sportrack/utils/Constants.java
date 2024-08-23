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

    public static final String UNEXPECTED_ERROR = "unexpected_error";

    public static final String FIREBASE_DATABASE = "https://sportrack-5fc02-default-rtdb.europe-west1.firebasedatabase.app/";

    public static final String FIREBASE_USERS_COLLECTION = "users";
    public static final String FIREBASE_EXERCISES_COLLECTION = "exercises";

}
