package it.unimib.icasiduso.sportrack.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    public static void setMuscleOnClickListener(View view, String muscle) {
        view.setOnClickListener(v -> {
            ExercisesFragmentDirections.ActionExercisesFragmentToListExercisesFragment action = ExercisesFragmentDirections.actionExercisesFragmentToListExercisesFragment(muscle);
            Navigation.findNavController(v).navigate(action);
        });
    }

    public void setOnClickListeners() {
        setMuscleOnClickListener(binding.abdominalsCard.cardview, "abdominals");
        setMuscleOnClickListener(binding.bicepsCard.cardview, "biceps");
        setMuscleOnClickListener(binding.chestCard.cardview, "chest");
        setMuscleOnClickListener(binding.glutesCard.cardview, "glutes");
        setMuscleOnClickListener(binding.hamstringsCard.cardview, "hamstrings");
        setMuscleOnClickListener(binding.latsCard.cardview, "lats");
        setMuscleOnClickListener(binding.quadricepsCard.cardview, "quadriceps");
        setMuscleOnClickListener(binding.tricepsCard.cardview, "triceps");
        setMuscleOnClickListener(binding.middleBackCard.cardview, "middle_back");
        setMuscleOnClickListener(binding.lowerBackCard.cardview, "lower_back");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}