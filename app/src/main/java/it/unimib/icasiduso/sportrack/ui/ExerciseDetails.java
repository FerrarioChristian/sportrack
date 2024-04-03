package it.unimib.icasiduso.sportrack.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;

public class ExerciseDetails extends Fragment {
    private static final String TAG = ExerciseDetails.class.getSimpleName();

    public ExerciseDetails() {
    }

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

        // TODO implement binding (?)
        Button addExerciseToScheduleButton = view.findViewById(R.id.add_exercise_to_schedule);
        TextView textViewExerciseName = view.findViewById(R.id.textViewExerciseName);
        TextView textViewExerciseType = view.findViewById(R.id.textViewExerciseType);
        TextView textViewExerciseMuscle = view.findViewById(R.id.textViewExerciseMuscle);
        TextView textViewExerciseEquipment = view.findViewById(R.id.textViewExerciseEquipment);
        TextView textViewExerciseDifficulty = view.findViewById(R.id.textViewExerciseDifficulty);
        TextView textViewExerciseDescription = view.findViewById(R.id.textViewExerciseDescription);

        textViewExerciseName.setText(exercise.getName());
        textViewExerciseType.setText("TIPO: " + exercise.getType());
        textViewExerciseMuscle.setText("GRUPPO: " + exercise.getMuscle());
        textViewExerciseEquipment.setText("ATTREZZATURA: " + exercise.getEquipment());
        textViewExerciseDifficulty.setText("LIVELLO: " + exercise.getDifficulty());
        textViewExerciseDescription.setText(exercise.getInstructions());

        addExerciseToScheduleButton.setOnClickListener(v -> {
            // Ottieni il NavController
            NavController navController = Navigation.findNavController(view);

            // Crea un'istanza del nuovo fragment a cui desideri navigare
            ExerciseDetailsDirections.ActionFragmentExerciseDetailsToFragmentSchedule action = ExerciseDetailsDirections.actionFragmentExerciseDetailsToFragmentSchedule(exercise);
            // Esegui la navigazione passando l'azione e l'oggetto Exercise come argomento
            navController.navigate(action);
        });
    }
}
