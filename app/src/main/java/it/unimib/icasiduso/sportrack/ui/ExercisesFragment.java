package it.unimib.icasiduso.sportrack.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import it.unimib.icasiduso.sportrack.R;

import androidx.navigation.Navigation;

import it.unimib.icasiduso.sportrack.databinding.FragmentExercisesBinding;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCollection;
import it.unimib.icasiduso.sportrack.utils.JsonParserUtil;

public class ExercisesFragment extends Fragment {

    private static final String TAG = ExercisesFragment.class.getSimpleName();

    private JsonParserUtil jsonParser;
    private ExerciseCollection exercises;

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

        jsonParser = new JsonParserUtil(requireActivity().getApplication());


        Button abdominals_button = view.findViewById(R.id.abdominals_button);
        abdominals_button.setOnClickListener(v -> {
            try {
                exercises = jsonParser.parseFromFileWithGSON("abdominals.json");
                ExercisesFragmentDirections.ActionFragmentExercisesToFragmentListExercises action = ExercisesFragmentDirections.actionFragmentExercisesToFragmentListExercises(exercises);
                Navigation.findNavController(view).navigate(action);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.d(TAG, exercises.toString());

        });

        Button biceps_button = view.findViewById(R.id.biceps_button);
        biceps_button.setOnClickListener(v -> {
            try {
                exercises = jsonParser.parseFromFileWithGSON("biceps.json");
                ExercisesFragmentDirections.ActionFragmentExercisesToFragmentListExercises action = ExercisesFragmentDirections.actionFragmentExercisesToFragmentListExercises(exercises);
                Navigation.findNavController(view).navigate(action);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.d(TAG, exercises.toString());
        });


    }



}