package it.unimib.icasiduso.sportrack.ui.exercise;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.ExerciseRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.data.repository.ExercisesMockRepository;
import it.unimib.icasiduso.sportrack.data.repository.ExercisesRepository;
import it.unimib.icasiduso.sportrack.data.repository.IExercisesRepository;
import it.unimib.icasiduso.sportrack.data.repository.ResponseCallback;

public class ListExercisesFragment extends Fragment implements ResponseCallback {

    private static final String TAG = ListExercisesFragment.class.getSimpleName();
    private List<Exercise> exercises;
    private IExercisesRepository exercisesRepository;
    private ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter;
    private ProgressBar progressBar;



    public ListExercisesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(requireActivity().getResources().getBoolean(R.bool.debug_mode)) {
            exercisesRepository = new ExercisesMockRepository(requireActivity().getApplication(), this);
        } else {
            exercisesRepository = new ExercisesRepository(requireActivity().getApplication(), this);
        }
        exercises = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_exercises, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        if(exercises.isEmpty()){
            progressBar.setVisibility(View.VISIBLE);
        }


        RecyclerView recyclerViewExerciseList = view.findViewById(R.id.recyclerview_exercise_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter(exercises, requireActivity().getApplication(), exercise -> {
            ListExercisesFragmentDirections.ActionListExercisesFragmentToExerciseDetails action = ListExercisesFragmentDirections.actionListExercisesFragmentToExerciseDetails(exercise);
            Navigation.findNavController(view).navigate(action);
        });
        recyclerViewExerciseList.setLayoutManager(layoutManager);
        recyclerViewExerciseList.setAdapter(exerciseRecyclerViewAdapter);

        String muscle = ListExercisesFragmentArgs.fromBundle(getArguments()).getMuscle();
        exercisesRepository.fetchExercises(muscle);
    }

    @Override
    public void onSuccess(List<Exercise> exercises) {
        if(exercises != null){
            this.exercises.clear();
            this.exercises.addAll(exercises);
            Activity activity = getActivity();
            if(activity != null){
                activity.runOnUiThread(() -> {
                    exerciseRecyclerViewAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                });
            }
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, errorMessage);
    }
}
