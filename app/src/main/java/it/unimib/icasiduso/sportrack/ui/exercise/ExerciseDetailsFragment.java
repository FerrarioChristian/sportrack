package it.unimib.icasiduso.sportrack.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        Exercise exercise = ExerciseDetailsFragmentArgs.fromBundle(getArguments()).getExercise();
        //TODO cambiare scheduleID !!
        //long scheduleId = ExerciseDetailsFragmentArgs.fromBundle(getArguments()).getScheduleId();
        long scheduleId = 1;
        if (scheduleId != 0L) {
            View container = view.findViewById(R.id.scheduleInputContainer);
            container.setVisibility(View.VISIBLE);
        } else {
            View container = view.findViewById(R.id.scheduleInputContainer);
            container.setVisibility(View.GONE);
        }

        //TODO sistemare le stringhe e spostare in Adapter.setItems()
        binding.textViewExerciseName.setText(exercise.getName());
        binding.textViewExerciseType.setText("tipo: " + exercise.getType());
        binding.textViewExerciseMuscle.setText("muscolo: " + exercise.getMuscle());
        binding.textViewExerciseEquipment.setText("attrezzo: " + exercise.getEquipment());
        binding.textViewExerciseDifficulty.setText("livello: " + exercise.getDifficulty());
        binding.textViewExerciseDescription.setText(exercise.getInstructions());

        binding.addExerciseToSchedule.setOnClickListener(v -> {
            String series = binding.textViewSeries.getText().toString();
            String reps = binding.textViewReps.getText().toString();
            WorkoutExercise workoutExercise = new WorkoutExercise(series, reps, exercise.getExerciseId(), scheduleId);
            workoutExerciseViewModel.addWorkoutExerciseToSchedule(workoutExercise);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
