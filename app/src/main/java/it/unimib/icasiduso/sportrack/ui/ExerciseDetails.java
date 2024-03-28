package it.unimib.icasiduso.sportrack.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.main.MainActivityWithBottomNav;
import it.unimib.icasiduso.sportrack.model.Exercise;

public class ExerciseDetails extends Fragment {
    private static final String TAG = ExerciseDetails.class.getSimpleName();
    public ExerciseDetails(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise_details, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Exercise exercise = ExerciseDetailsArgs.fromBundle(getArguments()).getExercise();

        //TODO implement binding (?)
        TextView textViewExerciseName = view.findViewById(R.id.textViewExerciseName);
        TextView textViewExerciseType = view.findViewById(R.id.textViewExerciseType);
        TextView textViewExerciseMuscle = view.findViewById(R.id.textViewExerciseMuscle);
        TextView textViewExerciseEquipment = view.findViewById(R.id.textViewExerciseEquipment);
        TextView textViewExerciseDifficulty = view.findViewById(R.id.textViewExerciseDifficulty);
        TextView textViewExerciseDescription = view.findViewById(R.id.textViewExerciseDescription);

        textViewExerciseName.setText(exercise.getName());
        textViewExerciseType.setText(exercise.getType());
        textViewExerciseMuscle.setText(exercise.getMuscle());
        textViewExerciseEquipment.setText(exercise.getEquipment());
        textViewExerciseDifficulty.setText(exercise.getDifficulty());
        textViewExerciseDescription.setText(exercise.getInstructions());

    }
}
