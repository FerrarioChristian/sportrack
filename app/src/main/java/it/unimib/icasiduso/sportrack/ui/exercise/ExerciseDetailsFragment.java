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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentExerciseDetailsBinding;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.WorkoutExerciseViewModel;

public class ExerciseDetailsFragment extends Fragment {
    private static final String TAG = ExerciseDetailsFragment.class.getSimpleName();

    private WorkoutExerciseViewModel workoutExerciseViewModel;
    private FragmentExerciseDetailsBinding binding;


    public ExerciseDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IWorkoutExercisesRepository workoutExercisesRepository = ServiceLocator.getInstance()
                .getWorkoutExercisesRepository();
        WorkoutExerciseViewModel.Factory workoutExerciseViewModelFactory = new WorkoutExerciseViewModel.Factory(
                workoutExercisesRepository);
        workoutExerciseViewModel = new ViewModelProvider(requireActivity(),
                workoutExerciseViewModelFactory).get(WorkoutExerciseViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExerciseDetailsFragmentArgs args = ExerciseDetailsFragmentArgs.fromBundle(getArguments());
        Exercise exercise = args.getExercise();
        long scheduleId = args.getId();

        binding.setExercise(exercise);

        binding.scheduleInputContainer.setVisibility(scheduleId != 0L ? View.VISIBLE : View.GONE);

        binding.addExerciseToSchedule.setOnClickListener(v -> {
            String series = binding.textViewSeries.getEditText()
                    .getText() != null ? binding.textViewSeries.getEditText()
                    .getText()
                    .toString() : "";
            String reps = binding.textViewReps.getEditText()
                    .getText() != null ? binding.textViewReps.getEditText()
                    .getText()
                    .toString() : "";
            if (!isValidInput(series, reps)) {
                Toast.makeText(requireContext(),
                        getString(R.string.invalid_input),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            WorkoutExercise workoutExercise = new WorkoutExercise(series,
                    reps,
                    exercise.getId(),
                    scheduleId,
                    user.getUid());
            workoutExerciseViewModel.addWorkoutExercise(workoutExercise);
            Toast.makeText(getActivity(), R.string.saved_workout_exercise, Toast.LENGTH_SHORT)
                    .show();
            Navigation.findNavController(view)
                    .popBackStack(R.id.listWorkoutExercisesFragment, false);
        });
    }

    private boolean isValidInput(String series, String reps) {
        if (series.isEmpty() || reps.isEmpty()) return false;
        return Integer.parseInt(series) > 0 && Integer.parseInt(reps) > 0;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
