package it.unimib.icasiduso.sportrack.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.unimib.icasiduso.sportrack.databinding.FragmentExerciseDetailsBinding;
import it.unimib.icasiduso.sportrack.model.Exercise;

public class ExerciseDetailsFragment extends Fragment {
    private static final String TAG = ExerciseDetailsFragment.class.getSimpleName();
    private FragmentExerciseDetailsBinding binding;

    public ExerciseDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Exercise exercise = ExerciseDetailsFragmentArgs.fromBundle(getArguments()).getExercise();

        binding.textViewExerciseName.setText(exercise.getName());
        binding.textViewExerciseType.setText(exercise.getType());
        binding.textViewExerciseMuscle.setText(exercise.getMuscle());
        binding.textViewExerciseEquipment.setText(exercise.getEquipment());
        binding.textViewExerciseDifficulty.setText(exercise.getDifficulty());
        binding.textViewExerciseDescription.setText(exercise.getInstructions());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
