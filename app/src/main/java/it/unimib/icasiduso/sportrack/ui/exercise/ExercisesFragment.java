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

    private FragmentExercisesBinding fragmentExercisesBinding;

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
        fragmentExercisesBinding = FragmentExercisesBinding.inflate(inflater, container, false);
        return fragmentExercisesBinding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button abdominals_button = view.findViewById(R.id.abdominals_button);
        abdominals_button.setOnClickListener(v -> {
            ExercisesFragmentDirections.ActionExercisesFragmentToListExercisesFragment action = ExercisesFragmentDirections.actionExercisesFragmentToListExercisesFragment("abdominals");
            Navigation.findNavController(view).navigate(action);

        });

        Button biceps_button = view.findViewById(R.id.biceps_button);
        biceps_button.setOnClickListener(v -> {
            ExercisesFragmentDirections.ActionExercisesFragmentToListExercisesFragment action = ExercisesFragmentDirections.actionExercisesFragmentToListExercisesFragment("biceps");
            Navigation.findNavController(view).navigate(action);
        });


    }
}