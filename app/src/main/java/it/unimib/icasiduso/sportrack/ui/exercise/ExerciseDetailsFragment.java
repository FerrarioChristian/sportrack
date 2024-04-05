package it.unimib.icasiduso.sportrack.ui.exercise;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.OnConflictStrategy;

import it.unimib.icasiduso.sportrack.data.database.ExerciseDao;
import it.unimib.icasiduso.sportrack.data.database.ExerciseRoomDatabase;
import it.unimib.icasiduso.sportrack.data.database.ScheduledExerciseDao;
import it.unimib.icasiduso.sportrack.databinding.FragmentExerciseDetailsBinding;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.ScheduledExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;

public class ExerciseDetailsFragment extends Fragment {
    private static final String TAG = ExerciseDetailsFragment.class.getSimpleName();
    private FragmentExerciseDetailsBinding binding;
    private Application application;
    private ExerciseDao exerciseDao;
    private ScheduledExerciseDao scheduledExerciseDao;


    public ExerciseDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExerciseDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.application = getActivity().getApplication();
        ExerciseRoomDatabase exerciseRoomDatabase = ServiceLocator.getInstance().getExerciseDatabase(application);
        this.exerciseDao = exerciseRoomDatabase.exerciseDao();
        this.scheduledExerciseDao = exerciseRoomDatabase.scheduledExerciseDao();

        super.onViewCreated(view, savedInstanceState);

        Exercise exercise = ExerciseDetailsFragmentArgs.fromBundle(getArguments()).getExercise();

        binding.textViewExerciseName.setText(exercise.getName());
        binding.textViewExerciseType.setText(exercise.getType());
        binding.textViewExerciseMuscle.setText(exercise.getMuscle());
        binding.textViewExerciseEquipment.setText(exercise.getEquipment());
        binding.textViewExerciseDifficulty.setText(exercise.getDifficulty());
        binding.textViewExerciseDescription.setText(exercise.getInstructions());

        binding.addExerciseToSchedule.setOnClickListener(v -> {
            ExerciseRoomDatabase.databaseWriteExecutor.execute(() -> {
                long exerciseId = exerciseDao.getExerciseIdByName(exercise.getName());
                String series = binding.textViewSeries.getText().toString();
                String reps = binding.textViewReps.getText().toString();
                ScheduledExercise scheduledExercise = new ScheduledExercise(series, reps, exerciseId, 1);

                long id = scheduledExerciseDao.insertScheduledExercise(scheduledExercise);
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
