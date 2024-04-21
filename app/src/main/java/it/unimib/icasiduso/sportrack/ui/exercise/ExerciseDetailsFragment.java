package it.unimib.icasiduso.sportrack.ui.exercise;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.WorkoutExerciseDao;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.WorkoutExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.WorkoutExerciseRepositoryCallbackable;
import it.unimib.icasiduso.sportrack.databinding.FragmentExerciseDetailsBinding;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;

public class ExerciseDetailsFragment extends Fragment implements WorkoutExerciseRepositoryCallbackable {
    private static final String TAG = ExerciseDetailsFragment.class.getSimpleName();
    private FragmentExerciseDetailsBinding binding;
    private Application application;

    private WorkoutExerciseRepository workoutExerciseRepository;
    private WorkoutExerciseDao workoutExerciseDao;
    private View view;


    public ExerciseDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutExerciseRepository = new WorkoutExerciseRepository(requireActivity().getApplication(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.application = getActivity().getApplication();
        ExerciseRoomDatabase exerciseRoomDatabase = ServiceLocator.getInstance().getExerciseDatabase(application);
        this.workoutExerciseDao = exerciseRoomDatabase.workoutExerciseDao();
        this.view = view;

        super.onViewCreated(view, savedInstanceState);

        Exercise exercise = ExerciseDetailsFragmentArgs.fromBundle(getArguments()).getExercise();

        binding.textViewExerciseName.setText(exercise.getName());
        binding.textViewExerciseType.setText(exercise.getType());
        binding.textViewExerciseMuscle.setText(exercise.getMuscle());
        binding.textViewExerciseEquipment.setText(exercise.getEquipment());
        binding.textViewExerciseDifficulty.setText(exercise.getDifficulty());
        binding.textViewExerciseDescription.setText(exercise.getInstructions());

        binding.addExerciseToSchedule.setOnClickListener(v -> {
            String series = binding.textViewSeries.getText().toString();
            String reps = binding.textViewReps.getText().toString();
            WorkoutExercise workoutExercise = new WorkoutExercise(series, reps, exercise.getExerciseId(), 1);
            workoutExerciseRepository.saveWorkoutExercise(workoutExercise);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSuccess() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                Toast.makeText(getActivity(), R.string.saved_workout_exercise,
                        Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();

            });
        }
    }

    @Override
    public void onSuccess(List<WorkoutExercise> exercises) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }
}
