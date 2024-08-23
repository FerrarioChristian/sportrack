package it.unimib.icasiduso.sportrack.model;

import java.util.List;

import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public abstract class Result {
    private Result() {}

    public boolean isSuccess() {
        return this instanceof UserResponseSuccess || this instanceof ExercisesResponseSuccess;
    }
    public static final class UserResponseSuccess extends Result {
        private final User user;
        public UserResponseSuccess(User user) {
            this.user = user;
        }
        public User getData() {
            return user;
        }
    }

    public static final class ExercisesResponseSuccess extends Result {
        private final List<Exercise> exercises;

        public ExercisesResponseSuccess(List<Exercise> exercises) {
            this.exercises = exercises;
        }

        public List<Exercise> getData() {
            return this.exercises;
        }
    }


    public static final class Error extends Result {
        private final String message;
        public Error(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }
}
