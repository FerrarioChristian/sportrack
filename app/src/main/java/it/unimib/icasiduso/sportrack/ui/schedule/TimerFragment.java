package it.unimib.icasiduso.sportrack.ui.schedule;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yashovardhan99.timeit.Stopwatch;
import com.yashovardhan99.timeit.Timer;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentTimerBinding;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.WorkoutExerciseViewModel;

public class TimerFragment extends Fragment implements Timer.OnTickListener {

    private Stopwatch stopwatch;

    List<Exercise> TimerExercises = new ArrayList<>();
    List<WorkoutExercise> workoutExercises;
    int counter = 0;
    int exerciseNumber = 0;

    private static final String TAG = TimerFragment.class.getSimpleName();

    private WorkoutExerciseViewModel workoutExerciseViewModel;
    private ExerciseViewModel exerciseViewModel;
    FragmentTimerBinding binding;

    public TimerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IWorkoutExercisesRepository workoutExercisesRepository = ServiceLocator.getInstance().getWorkoutExercisesRepository();
        WorkoutExerciseViewModel.Factory factory = new WorkoutExerciseViewModel.Factory(workoutExercisesRepository);
        workoutExerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(WorkoutExerciseViewModel.class);
        exerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(ExerciseViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = FragmentTimerBinding.inflate(inflater, container, false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IExerciseRepository exercisesRepository = ServiceLocator.getInstance().getExercisesRepository();
        ExerciseViewModel.Factory factory = new ExerciseViewModel.Factory(exercisesRepository);
        ExerciseViewModel exerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(ExerciseViewModel.class);

        setListeners();

        observeViewModel(new OnExercisesFetchedCallback() {
            @Override
            public void onExercisesFetched(List<WorkoutExercise> exercises) {
                // Handle the fetched exercises here
                if (exercises != null && !exercises.isEmpty()) {
                    for (int x = 0; x < workoutExercises.size(); x++) {
                        int finalX = x;
                        int finalX1 = x;
                        exerciseViewModel.getExerciseById(workoutExercises.get(x).getExternalExerciseId()).observe(getViewLifecycleOwner(), exercise -> {
                            TimerExercises.add(exercise);
                            exerciseNumber++;
                            // Inflate the child view
                            LayoutInflater inflater = requireActivity().getLayoutInflater();
                            LinearLayout parentView = requireView().findViewById(R.id.dynamic_list);
                            View inflatedView = inflater.inflate(R.layout.workout_exercise_timer_item, parentView, false);

                            TextView seriesTextView = inflatedView.findViewById(R.id.exerciseSeries);
                            int series = workoutExercises.get(finalX1).getSeries();
                            String seriesString = "Serie rimanenti: " + String.valueOf(series);
                            seriesTextView.setText(seriesString);

                            TextView repTextView = inflatedView.findViewById(R.id.exerciseRepetitions);
                            int reps = workoutExercises.get(finalX1).getRepetitions();
                            String repsString = "da "+ String.valueOf(reps)+ " Ripetizioni";
                            repTextView.setText(repsString);

                            // Find the TextView in the inflated layout
                            TextView nameTextView = inflatedView.findViewById(R.id.exerciseName);
                            nameTextView.setText(exercise.getName());

                            TextView additionalInfoTextView = inflatedView.findViewById(R.id.additionalInfo);
                            String instrString = "\n\nFurther details: " + exercise.getInstructions();
                            additionalInfoTextView.setText(instrString);

                            parentView.addView(inflatedView);

                            inflatedView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ConstraintLayout additionalContent = inflatedView.findViewById(R.id.additionalContent);
                                    if (additionalContent.getVisibility() == View.GONE) {
                                        additionalContent.setVisibility(View.VISIBLE);
                                    } else {
                                        additionalContent.setVisibility(View.GONE);
                                    }
                                }
                            });

                            returnFirstChildren().findViewById(R.id.statusIcon).setVisibility(View.VISIBLE);

                        });
                    }
                }
            }
        });
        // Initialize and start the total stopwatch
        initializeStopwatch(view);

    }

    private void observeViewModel(OnExercisesFetchedCallback callback) {
        long scheduleId = ListWorkoutExercisesFragmentArgs.fromBundle(getArguments()).getScheduleId();
        workoutExerciseViewModel.getWorkoutExercises(scheduleId).observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                workoutExercises = result;
                counter++;
                // Notify that the exercises have been fetched
                if (callback != null && counter == 1) {
                    callback.onExercisesFetched(result);
                }
            }
        });
    }

    public interface OnExercisesFetchedCallback {
        void onExercisesFetched(List<WorkoutExercise> exercises);
    }

    private void setListeners() {
        MaterialButton playButton = binding.playButton;
        MaterialButton skipButton = binding.skipButton;
        MaterialButton nextButton = binding.nextButton;
        FloatingActionButton exitButton = binding.exitButton;
        playButton.setOnClickListener(v -> {
            if(playButton.getText().equals("Pause")) {
                if (stopwatch.isStarted()) {
                    stopwatch.pause();
                    playButton.setText("Play");
                    binding.nextButton.setClickable(false);
                }
            } else {
                if (stopwatch.isPaused()) {
                    stopwatch.resume();
                    playButton.setText("Pause");
                    binding.nextButton.setClickable(true);
                }
            }
        });
        skipButton.setOnClickListener(v -> {
            deleteChildren();
        });
        nextButton.setOnClickListener(v -> {
            nextExercise(binding.getRoot());
        });
        exitButton.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }

    private void nextExercise(View rootView){
        if (childrenLeft() != 0) {
            TextView pause_timer = binding.pauseTimerText;
            Timer pause_watch = new Timer(5000);
            binding.nextButton.setClickable(false);
            pause_watch.setTextView(rootView.findViewById(R.id.pause_timer_text));
            pause_timer.setVisibility(View.VISIBLE);
            pause_watch.start();
            LinearLayout parent = requireView().findViewById(R.id.dynamic_list);
            View firstChild = parent.getChildAt(0);
            TextView seriesTextView = firstChild.findViewById(R.id.exerciseSeries);
            String seriesText = seriesTextView.getText().toString();
            String[] parts = seriesText.split(": ");
            int remainingSeries = Integer.parseInt(parts[1]);
            if (remainingSeries > 1) {
                seriesTextView.setText("Serie rimanenti: " + (remainingSeries - 1));
            } else {
                deleteChildren();
            }
            ImageView statIcon = returnFirstChildren().findViewById(R.id.statusIcon);
            statIcon.setImageResource(R.drawable.baseline_airline_seat_recline_normal_24);

            pause_watch.setOnTickListener(new Timer.OnTickListener() {
                @Override
                public void onTick(Timer timer) {
                    // Do nothing on each tick
                }

                @Override
                public void onComplete(Timer timer) {
                    binding.nextButton.setClickable(true);
                    pause_timer.setVisibility(View.INVISIBLE);
                    statIcon.setImageResource(R.drawable.baseline_directions_run_24);
                }
            });
        }
    }

    private View returnFirstChildren(){
        LinearLayout parent = requireView().findViewById(R.id.dynamic_list);
        return parent.getChildAt(0);
    }

    private int childrenLeft(){
        LinearLayout parent = requireView().findViewById(R.id.dynamic_list);
        return parent.getChildCount();
    }

    private void deleteChildren(){
        LinearLayout parent = requireView().findViewById(R.id.dynamic_list);
        View firstChild = parent.getChildAt(0);
        parent.removeView(firstChild);

        if (childrenLeft() == 0) {
            //ROOM
            Toast.makeText(requireContext(), "Workout finished!", Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        } else returnFirstChildren().findViewById(R.id.statusIcon).setVisibility(View.VISIBLE);
    }

    private void initializeStopwatch(View rootView) {
        stopwatch = new Stopwatch();
        stopwatch.setTextView(rootView.findViewById(R.id.timer_text));
        stopwatch.setClockDelay(10);
        stopwatch.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //handler.removeCallbacks(updateTimeTask);
    }

    private void backFragment() {
        // Implement fragment back navigation logic
    }

    @Override
    public void onTick(Timer timer) {
        // Handle timer tick
    }

    @Override
    public void onComplete(Timer timer) {
        // Handle timer completion
    }
}