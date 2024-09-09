package it.unimib.icasiduso.sportrack.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.ExerciseRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentListExercisesBinding;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;

public class ListExercisesFragment extends Fragment implements ExerciseRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = ListExercisesFragment.class.getSimpleName();

    private ExerciseViewModel exerciseViewModel;
    private ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter;
    private FragmentListExercisesBinding binding;

    public ListExercisesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IExerciseRepository exercisesRepository = ServiceLocator.getInstance()
                .getExercisesRepository();
        ExerciseViewModel.Factory factory = new ExerciseViewModel.Factory(exercisesRepository);
        exerciseViewModel = new ViewModelProvider(requireActivity(),
                factory).get(ExerciseViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListExercisesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter(this);
        binding.recyclerviewExerciseList.setAdapter(exerciseRecyclerViewAdapter);

        observeViewModel();
    }

    private void observeViewModel() {
        String muscle = ListExercisesFragmentArgs.fromBundle(getArguments()).getMuscle();
        exerciseViewModel.getExercisesByMuscle(muscle).observe(getViewLifecycleOwner(), result -> {
            if (result.isSuccess()) {
                exerciseRecyclerViewAdapter.setExercises(result.getSuccessData());
            } else {
                exerciseRecyclerViewAdapter.setExercises(null);
                Toast.makeText(getActivity(),
                        getString(R.string.loading_error_check_internet),
                        Toast.LENGTH_SHORT).show();
            }
        });


        exerciseViewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onExerciseClick(Exercise exercise) {
        Long scheduleId = ListExercisesFragmentArgs.fromBundle(getArguments()).getId();
        ListExercisesFragmentDirections.ActionListExercisesFragmentToExerciseDetails action = ListExercisesFragmentDirections.actionListExercisesFragmentToExerciseDetails(
                scheduleId,
                exercise);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
