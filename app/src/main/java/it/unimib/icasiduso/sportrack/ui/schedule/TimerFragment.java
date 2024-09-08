package it.unimib.icasiduso.sportrack.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yashovardhan99.timeit.Stopwatch;
import com.yashovardhan99.timeit.Timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentTimerBinding;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCompleted;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.WorkoutExerciseViewModel;

public class TimerFragment extends Fragment implements Timer.OnTickListener {

    private Stopwatch stopwatch;

    List<Exercise> TimerExercises = new ArrayList<>();
    List<WorkoutExercise> workoutExercises = new ArrayList<>();
    boolean dataRetrieved = false;
    int exerciseNumber = 0;
    long scheduleId;
    Timer pause_watch;
    LinearLayout dynamicListParent;

    private static final String TAG = TimerFragment.class.getSimpleName();

    private WorkoutExerciseViewModel workoutExerciseViewModel;
    FragmentTimerBinding binding;

    public TimerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IWorkoutExercisesRepository workoutExercisesRepository = ServiceLocator.getInstance().getWorkoutExercisesRepository();
        WorkoutExerciseViewModel.Factory factory = new WorkoutExerciseViewModel.Factory(workoutExercisesRepository);
        workoutExerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(WorkoutExerciseViewModel.class);
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

        dynamicListParent = requireView().findViewById(R.id.dynamic_list);

        setListeners();
        observeViewModel();
        initializeStopwatch(view);
    }

    private void observeViewModel() {
        scheduleId = ListWorkoutExercisesFragmentArgs.fromBundle(getArguments()).getId();

        workoutExerciseViewModel.getWorkoutExercises(scheduleId).observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (!dataRetrieved) {
                    workoutExercises = result;
                    dataRetrieved = true;
                    startCreation(result);
                }
            }
        });
    }

    private void startCreation(List<WorkoutExercise> exercises) {

        IExerciseRepository exercisesRepository = ServiceLocator.getInstance().getExercisesRepository();
        ExerciseViewModel.Factory factory = new ExerciseViewModel.Factory(exercisesRepository);
        ExerciseViewModel exerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(ExerciseViewModel.class);

        if (exercises != null && !exercises.isEmpty()) {
            for (int x = 0; x < workoutExercises.size(); x++) {
                int finalX = x;
                exerciseViewModel.getExerciseById(workoutExercises.get(x).getExerciseId()).observe(getViewLifecycleOwner(), exercise -> {
                    TimerExercises.add(exercise);
                    createNewChildrens(exercise, finalX);
                });
            }
        }
    }

    //possibile con recyclerview ma sus || ordine non rispecchia pagina precedente
    private void createNewChildrens(Exercise exercise, int x){
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View inflatedView = inflater.inflate(R.layout.workout_exercise_timer_item, dynamicListParent, false);

        TextView seriesTextView = inflatedView.findViewById(R.id.exerciseSeries);
        String seriesString = requireContext().getString(R.string.series_left) + " " + workoutExercises.get(x).getSeries();
        seriesTextView.setText(seriesString);

        TextView repTextView = inflatedView.findViewById(R.id.exerciseRepetitions);
        String repsString = requireContext().getString(R.string.of) + " "+ workoutExercises.get(x).getRepetitions() + " " + requireContext().getString(R.string.rep_left);
        repTextView.setText(repsString);

        TextView nameTextView = inflatedView.findViewById(R.id.exerciseName);
        nameTextView.setText(exercise.getName());

        TextView additionalInfoTextView = inflatedView.findViewById(R.id.additionalInfo);
        String instrString = "\n\n" + requireContext().getString(R.string.further_details) + "\n" + exercise.getInstructions();
        additionalInfoTextView.setText(instrString);

        dynamicListParent.addView(inflatedView);

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
    }

    private void setListeners() {
        scheduleId = ListWorkoutExercisesFragmentArgs.fromBundle(getArguments()).getId();
        MaterialButton playButton = binding.playButton;

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
        binding.skipButton.setOnClickListener(v -> {
            deleteChildren(0);
        });
        binding.nextButton.setOnClickListener(v -> {
            nextExercise(binding.getRoot());
        });
        binding.exitButton.setOnClickListener(v -> {
            TimerFragmentDirections.ActionTimerFragmentToListWorkoutExercisesFragment action = TimerFragmentDirections.actionTimerFragmentToListWorkoutExercisesFragment(scheduleId);
            Navigation.findNavController(requireView()).navigate(action);
        });
    }

    private void nextExercise(View rootView){
        if (childrenLeft() != 0) {
            binding.nextButton.setClickable(false);

            TextView pause_timer = binding.pauseTimerText;
            pause_watch = new Timer(5000);
            pause_watch.setTextView(rootView.findViewById(R.id.pause_timer_text));
            pause_timer.setVisibility(View.VISIBLE);

            View firstChild = returnFirstChildren();
            TextView seriesTextView = firstChild.findViewById(R.id.exerciseSeries);
            String seriesText = seriesTextView.getText().toString();
            String[] parts = seriesText.split(": ");
            int remainingSeries = Integer.parseInt(parts[1]);

            ImageView statIcon = firstChild.findViewById(R.id.statusIcon);

            if (remainingSeries > 1) {
                String seriesString = requireContext().getString(R.string.series_left) + " " + (remainingSeries - 1);
                seriesTextView.setText(seriesString);
                statIcon.setImageResource(R.drawable.baseline_airline_seat_recline_normal_24);
                pause_watch.start();
            } else {
                deleteChildren(1);
                saveObject();
            }

            pause_watch.setOnTickListener(new Timer.OnTickListener() {
                @Override
                public void onTick(Timer timer) {
                }

                @Override
                public void onComplete(Timer timer) {
                    binding.nextButton.setClickable(true);
                    pause_timer.setVisibility(View.INVISIBLE);
                    ImageView statIcon = returnFirstChildren().findViewById(R.id.statusIcon);
                    statIcon.setImageResource(R.drawable.baseline_directions_run_24);
                }
            });
        }
    }

    //Salva l'esercizio eseguito nella lista di esercizi eseguiti
    private void saveObject(){
        String userId = workoutExercises.get(exerciseNumber).getUserId();
        long workoutExerciseId = workoutExercises.get(exerciseNumber).getId();
        long externalExerciseId = TimerExercises.get(exerciseNumber).getId();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(date);
        ExerciseCompleted newSave = new ExerciseCompleted(userId, workoutExerciseId, externalExerciseId, formattedDate);

        workoutExerciseViewModel.saveExerciseCompleted(newSave);
        exerciseNumber++;
    }

    //Ritorna la view del primo elemento nella lista dinamica
    private View returnFirstChildren(){
        return dynamicListParent.getChildAt(0);
    }

    //Ritorna il numero di figli nella lista dinamica
    private int childrenLeft(){
        return dynamicListParent.getChildCount();
    }

    //Elimina il primo figlio della lista
    private void deleteChildren(int i){
        View firstChild = dynamicListParent.getChildAt(0);
        dynamicListParent.removeView(firstChild);

        //In caso ci siano 0 figli rimasti il timer termina e viene mostrato il toast
        if (childrenLeft() == 0) {
            Toast.makeText(requireContext(),requireContext().getString(R.string.workout_end) + " " + convertMsToTime(stopwatch.getElapsedTime()) + "!", Toast.LENGTH_SHORT).show();
            TimerFragmentDirections.ActionTimerFragmentToListWorkoutExercisesFragment action = TimerFragmentDirections.actionTimerFragmentToListWorkoutExercisesFragment(scheduleId);
            Navigation.findNavController(requireView()).navigate(action);
        } else {
            returnFirstChildren().findViewById(R.id.statusIcon).setVisibility(View.VISIBLE);
            ImageView statIcon = returnFirstChildren().findViewById(R.id.statusIcon);
            statIcon.setImageResource(R.drawable.baseline_airline_seat_recline_normal_24);
            if (i == 1) pause_watch.start();
        }
    }

    //Utility per convertire da ms a hh:mm:ss
    public String convertMsToTime(long milliseconds) {
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    //Avvia il cronometro una volta creata la view
    private void initializeStopwatch(View rootView) {
        stopwatch = new Stopwatch();
        stopwatch.setTextView(rootView.findViewById(R.id.timer_text));
        stopwatch.setClockDelay(10);
        stopwatch.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onTick(Timer timer) {
    }

    @Override
    public void onComplete(Timer timer) {
    }
}