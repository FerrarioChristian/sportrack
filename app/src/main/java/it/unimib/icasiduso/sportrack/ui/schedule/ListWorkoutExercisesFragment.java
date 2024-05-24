package it.unimib.icasiduso.sportrack.ui.schedule;

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
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.ui.exercise.ListExercisesFragmentArgs;
import it.unimib.icasiduso.sportrack.ui.exercise.ListExercisesFragmentDirections;

public class ListWorkoutExercisesFragment extends Fragment {
/*
    private static final String TAG = ListWorkoutExercisesFragment.class.getSimpleName();

    private List<WorkoutExercise> workoutExercises;
    private WorkoutExercisesRepository workoutExercisesRepository;
    private WorkoutExerciseRecyclerViewAdapter workoutExerciseRecyclerViewAdapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutExercisesRepository = new WorkoutExercisesRepository(requireActivity().getApplication(), this);
        workoutExercises = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_workout_exercises, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        if(workoutExercises.isEmpty()){
            progressBar.setVisibility(View.VISIBLE);
        }


        RecyclerView recyclerViewExerciseList = view.findViewById(R.id.recyclerview_exercise_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        Long scheduleId = ListExercisesFragmentArgs.fromBundle(getArguments()).getScheduleId();
        WorkoutExerciseRecyclerViewAdapter = new WorkoutExerciseRecyclerViewAdapter(workoutExercises, requireActivity().getApplication(), exercise -> {
            ListExercisesFragmentDirections.ActionListExercisesFragmentToExerciseDetails action = ListExercisesFragmentDirections.actionListExercisesFragmentToExerciseDetails(scheduleId, exercise);
            Navigation.findNavController(view).navigate(action);
        });
        recyclerViewExerciseList.setLayoutManager(layoutManager);
        recyclerViewExerciseList.setAdapter(WorkoutExerciseRecyclerViewAdapter);

        String muscle = ListExercisesFragmentArgs.fromBundle(getArguments()).getMuscle();
        WorkoutExercisesRepository.fetchExercises(muscle);
    }

    @Override
    public void onSuccess(List<Exercise> exercises) {
        if(exercises != null){
            this.workoutExercises.clear();
            this.workoutExercises.addAll(exercises);
            Activity activity = getActivity();
            if(activity != null){
                activity.runOnUiThread(() -> {
                    workoutExerciseRecyclerViewAdapter.notifyDataSetChanged();
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
*/
}
