package it.unimib.icasiduso.sportrack.ui.exercise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.unimib.icasiduso.sportrack.R;

import androidx.navigation.Navigation;

import it.unimib.icasiduso.sportrack.databinding.FragmentExercisesBinding;

public class ExercisesFragment extends Fragment {

    private static final String TAG = ExercisesFragment.class.getSimpleName();

    private FragmentExercisesBinding binding;

    public ExercisesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExercisesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setOnClickListeners();

    }

    public static void setMuscleOnClickListener(Button button, String muscle) {
        button.setOnClickListener(v -> {
            ExercisesFragmentDirections.ActionExercisesFragmentToListExercisesFragment action = ExercisesFragmentDirections.actionExercisesFragmentToListExercisesFragment(muscle);
            Navigation.findNavController(v).navigate(action);
        });
    }

    public void setOnClickListeners() {
        setMuscleOnClickListener(binding.abdominalsButton, "abdominals");
        setMuscleOnClickListener(binding.bicepsButton, "biceps");
        setMuscleOnClickListener(binding.chestButton, "chest");
        setMuscleOnClickListener(binding.glutesButton, "glutes");
        setMuscleOnClickListener(binding.hamstringsButton, "hamstrings");
        setMuscleOnClickListener(binding.latsButton, "lats");
        setMuscleOnClickListener(binding.quadricepsButton, "quadriceps");
        setMuscleOnClickListener(binding.tricepsButton, "triceps");
        setMuscleOnClickListener(binding.middleBack, "middle_back");
        setMuscleOnClickListener(binding.lowerBackButton, "lower_back");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}