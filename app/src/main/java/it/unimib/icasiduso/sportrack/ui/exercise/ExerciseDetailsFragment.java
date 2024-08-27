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

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentExerciseDetailsBinding;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.workout_exercise.WorkoutExerciseViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.workout_exercise.WorkoutExerciseViewModelFactory;

public class ExerciseDetailsFragment extends Fragment {
    private static final String TAG = ExerciseDetailsFragment.class.getSimpleName();

    private WorkoutExerciseViewModel workoutExerciseViewModel;
    private FragmentExerciseDetailsBinding binding;


    public ExerciseDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWorkoutExercisesRepository workoutExercisesRepository = ServiceLocator.getInstance().getWorkoutExercisesRepository();
        WorkoutExerciseViewModelFactory workoutExerciseViewModelFactory = new WorkoutExerciseViewModelFactory(workoutExercisesRepository);
        workoutExerciseViewModel = new ViewModelProvider(requireActivity(), workoutExerciseViewModelFactory).get(WorkoutExerciseViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExerciseDetailsFragmentArgs args = ExerciseDetailsFragmentArgs.fromBundle(getArguments());
        Exercise exercise = args.getExercise();
        long scheduleId = args.getScheduleId();

        binding.setExercise(exercise);

        View scheduleInputContainer = view.findViewById(R.id.scheduleInputContainer);
        scheduleInputContainer.setVisibility(scheduleId != 0L ? View.VISIBLE : View.GONE);

        binding.addExerciseToSchedule.setOnClickListener(v -> {
            String series = binding.textViewSeries.getText() != null ? binding.textViewSeries.getText().toString() : "";
            String reps = binding.textViewReps.getText() != null ? binding.textViewReps.getText().toString() : "";
            if (!isValidInput(series, reps)) {
                Toast.makeText(requireContext(), getString(R.string.invalid_input), Toast.LENGTH_SHORT).show();
                return;
            }
            WorkoutExercise workoutExercise = new WorkoutExercise(series, reps, exercise.getExerciseId(), scheduleId);
            workoutExerciseViewModel.addWorkoutExerciseToSchedule(workoutExercise);
            //TODO show toast and back to ListExerciseFragment
        });
    }

    private boolean isValidInput(String series, String reps) {
        return !series.isEmpty() && !reps.isEmpty();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
