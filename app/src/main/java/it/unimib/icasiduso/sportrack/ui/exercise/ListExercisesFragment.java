package it.unimib.icasiduso.sportrack.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.ExerciseRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExercisesRepository;
import it.unimib.icasiduso.sportrack.model.Result;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;

public class ListExercisesFragment extends Fragment implements ExerciseRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = ListExercisesFragment.class.getSimpleName();

    private ExerciseViewModel exerciseViewModel;
    private ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IExercisesRepository exercisesRepository = ServiceLocator.getInstance().getExercisesRepository();

        ExerciseViewModel.Factory factory = new ExerciseViewModel.Factory(exercisesRepository);
        exerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(ExerciseViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_exercises, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewExerciseList = view.findViewById(R.id.recyclerview_exercise_list);
        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter(this);
        recyclerViewExerciseList.setAdapter(exerciseRecyclerViewAdapter);

        observeViewModel();
    }

    private void observeViewModel() {
        String muscle = ListExercisesFragmentArgs.fromBundle(getArguments()).getMuscle();
        exerciseViewModel.getExercisesByMuscle(muscle).observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                exerciseRecyclerViewAdapter.setExercises(((Result.ExercisesResponseSuccess) result).getData());
            } else {
                Toast.makeText(getActivity(),getString(R.string.loading_error_check_internet),
                        Toast.LENGTH_SHORT).show();
            }
        });


        ProgressBar progressBar = requireView().findViewById(R.id.progress_bar);
        exerciseViewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onExerciseClick(Exercise exercise) {
        Long scheduleId = ListExercisesFragmentArgs.fromBundle(getArguments()).getScheduleId();
        ListExercisesFragmentDirections.ActionListExercisesFragmentToExerciseDetails action = ListExercisesFragmentDirections.actionListExercisesFragmentToExerciseDetails(scheduleId, exercise);
        Navigation.findNavController(requireView()).navigate(action);
    }
}
