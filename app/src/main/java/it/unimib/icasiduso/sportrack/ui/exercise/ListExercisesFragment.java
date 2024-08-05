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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.ExerciseRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;

public class ListExercisesFragment extends Fragment {

    private static final String TAG = ListExercisesFragment.class.getSimpleName();

    private ExerciseViewModel exerciseViewModel;

    private List<Exercise> exercises;
    private ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IExercisesRepository exercisesRepository = ServiceLocator.getInstance().getExercisesRepository(requireActivity().getApplication());
        if (exercisesRepository != null) {
            exerciseViewModel = new ViewModelProvider(requireActivity(), new ExerciseViewModelFactory(exercisesRepository)).get(ExerciseViewModel.class);
        } else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.unexpected_error), Snackbar.LENGTH_LONG).show();
        }
        exercises = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_exercises, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        if (exercises.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
        }

        RecyclerView recyclerViewExerciseList = view.findViewById(R.id.recyclerview_exercise_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        Long scheduleId = ListExercisesFragmentArgs.fromBundle(getArguments()).getScheduleId();
        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter(exercises, requireActivity().getApplication(), exercise -> {
            ListExercisesFragmentDirections.ActionListExercisesFragmentToExerciseDetails action = ListExercisesFragmentDirections.actionListExercisesFragmentToExerciseDetails(scheduleId, exercise);
            Navigation.findNavController(view).navigate(action);
        });
        recyclerViewExerciseList.setLayoutManager(layoutManager);
        recyclerViewExerciseList.setAdapter(exerciseRecyclerViewAdapter);

        String muscle = ListExercisesFragmentArgs.fromBundle(getArguments()).getMuscle();
        //exercisesRepository.fetchExercises(muscle);
        //TODO: getExercisesByMuscle (sistemare, ora è solo placeholder)
        exerciseViewModel.getExercisesByMuscle(muscle).observe(getViewLifecycleOwner(), this::onSuccess);
    }

    public void onSuccess(List<Exercise> exercises) {
        if (exercises != null) {
            this.exercises.clear();
            this.exercises.addAll(exercises);
            Activity activity = getActivity();
            if (activity != null) {
                activity.runOnUiThread(() -> {
                    exerciseRecyclerViewAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                });
            }
        }
    }

    public void onFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, errorMessage);
    }
}
