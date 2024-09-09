package it.unimib.icasiduso.sportrack.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.yashovardhan99.timeit.Stopwatch;
import com.yashovardhan99.timeit.Timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentTimerBinding;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCompleted;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.utils.TimeUtils;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.WorkoutExerciseViewModel;

public class TimerFragment extends Fragment implements Timer.OnTickListener {

    private static final String TAG = TimerFragment.class.getSimpleName();
    private List<Exercise> timerExercises = new ArrayList<>();
    private List<WorkoutExercise> workoutExercises = new ArrayList<>();
    private boolean dataRetrieved = false;
    private long scheduleId;
    private Timer pause_watch = new Timer(10000);
    private FragmentTimerBinding binding;
    private Stopwatch stopwatch;

    private int totalWorkoutExercisesCount = 0;
    private int exercisesLoadedCount = 0;

    private WorkoutExerciseViewModel workoutExerciseViewModel;
    private ExerciseViewModel exerciseViewModel;

    private Map<Long, Integer> workoutExerciseIndexMap;

    public TimerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IWorkoutExercisesRepository workoutExercisesRepository = ServiceLocator.getInstance()
                .getWorkoutExercisesRepository();
        WorkoutExerciseViewModel.Factory factory = new WorkoutExerciseViewModel.Factory(
                workoutExercisesRepository);
        workoutExerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(
                WorkoutExerciseViewModel.class);

        IExerciseRepository exercisesRepository = ServiceLocator.getInstance()
                .getExercisesRepository();
        ExerciseViewModel.Factory exerciseFactory = new ExerciseViewModel.Factory(exercisesRepository);
        exerciseViewModel = new ViewModelProvider(requireActivity(), exerciseFactory).get(
                ExerciseViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTimerBinding.inflate(inflater, container, false);

        BottomNavigationView bottomNavigationView =
                requireActivity().findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
        observeViewModel();
        initializeStopwatch();
    }

    // Osserva il viewmodel per prelevare i dati da inserire nella pagina
    private void observeViewModel() {
        scheduleId = ListWorkoutExercisesFragmentArgs.fromBundle(getArguments()).getId();

        workoutExerciseViewModel.getWorkoutExercises(scheduleId)
                .observe(getViewLifecycleOwner(), result -> {
                    if (result != null && !result.isEmpty() && !dataRetrieved) {
                        // Salvo i risultati e avvia la creazione degli elementi nella pagina
                        workoutExercises = result;
                        totalWorkoutExercisesCount = workoutExercises.size();
                        dataRetrieved = true;
                        startCreation();
                    }
                });
    }

    // Inizializza la creazione degli elementi prelevando l'esercizio corrispondente dal viewmodel
    private void startCreation() {
        exercisesLoadedCount = 0; // Reset counter
        for (WorkoutExercise workoutExercise : workoutExercises) {
            exerciseViewModel.getExerciseById(workoutExercise.getExerciseId())
                    .observe(getViewLifecycleOwner(), exercise -> {
                        if (exercise != null) {
                            // Aggiunge gli esercizi alla lista
                            timerExercises.add(exercise);
                            exercisesLoadedCount++;

                            // Controlla se tutti gli esercizi sono stati caricati
                            if (exercisesLoadedCount == totalWorkoutExercisesCount) {
                                createNewChildrens();
                            }
                        }
                    });



        }

    }


    private void createIndexMap() {
        // Crea una nuova mappa HashMap per memorizzare gli ID degli esercizi e i loro indici
        workoutExerciseIndexMap = new HashMap<>();
        // Itera attraverso tutti gli esercizi nella lista workoutExercises
        for (int i = 0; i < workoutExercises.size(); i++) {
            // Aggiunge ogni ID di esercizio alla mappa con il suo indice nella lista
            workoutExerciseIndexMap.put(workoutExercises.get(i).getExerciseId(), i);
        }
    }

    private List<Exercise> reorderTimerExercises() {
        // Crea una nuova lista di esercizi con la stessa dimensione della lista workoutExercises
        List<Exercise> reorderedTimerExercises = new ArrayList<>(Collections.nCopies(workoutExercises.size(), null));
        // Itera attraverso tutti gli esercizi nella lista timerExercises
        for (Exercise exercise : timerExercises) {
            // Recupera l'indice dell'esercizio dalla mappa workoutExerciseIndexMap usando l'ID dell'esercizio
            Integer index = workoutExerciseIndexMap.get(exercise.getId());
            // Se l'indice esiste nella mappa, inserisce l'esercizio nella lista ordinata all'indice corretto
            if (index != null) {
                reorderedTimerExercises.set(index, exercise);
            }
        }
        return reorderedTimerExercises;
    }

    // Crea gli elementi nella pagina settandone i dati e l'onclick
    private void createNewChildrens(/*int x*/) {
        createIndexMap(); // Crea la mappa degli indici
        List<Exercise> reorderedTimerExercises = reorderTimerExercises(); // Ordina l'array timerExercises in base al workoutExercise

        // Usa la lista per creare gli elementi
        for (int x = 0; x < reorderedTimerExercises.size(); x++) {
            Exercise exercise = reorderedTimerExercises.get(x);
            if (exercise != null) {
                WorkoutExercise workoutExercise = workoutExercises.get(x);

                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View inflatedView = inflater.inflate(R.layout.workout_exercise_timer_item, binding.dynamicList, false);

                TextView seriesTextView = inflatedView.findViewById(R.id.exerciseSeries);
                String seriesString = requireContext().getString(R.string.series_left) + " " + workoutExercise.getSeries();
                seriesTextView.setText(seriesString);

                TextView repTextView = inflatedView.findViewById(R.id.exerciseRepetitions);
                String repsString = requireContext().getString(R.string.of) + " " + workoutExercise.getRepetitions() + " " + requireContext().getString(R.string.rep_left);
                repTextView.setText(repsString);

                TextView nameTextView = inflatedView.findViewById(R.id.exerciseName);
                nameTextView.setText(exercise.getName());

                TextView additionalInfoTextView = inflatedView.findViewById(R.id.additionalInfo);
                String instrString = "\n\n" + requireContext().getString(R.string.further_details) + "\n" + exercise.getInstructions();
                additionalInfoTextView.setText(instrString);

                binding.dynamicList.addView(inflatedView);

                inflatedView.setOnClickListener(v -> {
                    ConstraintLayout additionalContent =
                            inflatedView.findViewById(R.id.additionalContent);
                    if (additionalContent.getVisibility() == View.GONE) {
                        additionalContent.setVisibility(View.VISIBLE);
                    } else {
                        additionalContent.setVisibility(View.GONE);
                    }
                });

                returnFirstChildren().findViewById(R.id.statusIcon).setVisibility(View.VISIBLE);
            }
        }

    }

    // Imposta i listener dei vari button nella pagina con i relativi controlli
    private void setListeners() {
        scheduleId = ListWorkoutExercisesFragmentArgs.fromBundle(getArguments()).getId();

        MaterialButton playButton = binding.playButton;
        playButton.setOnClickListener(v -> {
            ImageView statIcon = returnFirstChildren().findViewById(R.id.statusIcon);
            if (pause_watch.isStarted()) Toast.makeText(getContext(), requireContext().getString(R.string.end_pause_before), Toast.LENGTH_SHORT).show();
            else {
                if (playButton.getText().equals(requireContext().getString(R.string.pause))) {
                    if (stopwatch.isStarted()) {
                        stopwatch.pause();
                        playButton.setText(requireContext().getString(R.string.play));
                        statIcon.setImageResource(R.drawable.baseline_alarm_off_24);
                    }
                } else {
                    if (stopwatch.isPaused()) {
                        stopwatch.resume();
                        playButton.setText(requireContext().getString(R.string.pause));
                        statIcon.setImageResource(R.drawable.baseline_directions_run_24);
                    }
                }
            }
        });
        binding.skipButton.setOnClickListener(v -> {
            if (pause_watch.isStarted()) Toast.makeText(getContext(), requireContext().getString(R.string.end_pause_before), Toast.LENGTH_SHORT).show();
            else deleteChildren(0);
        });
        binding.nextButton.setOnClickListener(v -> {
            if (stopwatch.isPaused()) Toast.makeText(getContext(), requireContext().getString(R.string.start_timer_before), Toast.LENGTH_SHORT).show();
            else nextExercise();
        });
        binding.exitButton.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.timerFragment, true)  // Replace with the correct ID of TimerFragment
                    .build();
            TimerFragmentDirections.ActionTimerFragmentToListWorkoutExercisesFragment action =
                    TimerFragmentDirections.actionTimerFragmentToListWorkoutExercisesFragment(
                            scheduleId);
            Navigation.findNavController(requireView()).navigate(action, navOptions);
        });
    }

    // Gestisce il next exercise, se ci sono esercizi rimasti avvio il timer, valuta quante serie sono rimaste ed in caso passa al figlio successivo eleminando quello terminato e salvandolo nel database
    private void nextExercise() {
        if (!workoutExercises.isEmpty()) {
            binding.nextButton.setClickable(false);

            pause_watch.setTextView(binding.pauseTimerText);
            binding.pauseTimerText.setVisibility(View.VISIBLE);

            TextView seriesTextView = returnFirstChildren().findViewById(R.id.exerciseSeries);

            int remainingSeries = workoutExercises.get(0).getSeries();

            ImageView statIcon = returnFirstChildren().findViewById(R.id.statusIcon);

            if (remainingSeries > 1) {
                String seriesString =
                        requireContext().getString(R.string.series_left) + " " + (remainingSeries - 1);
                seriesTextView.setText(seriesString);
                statIcon.setImageResource(R.drawable.baseline_airline_seat_recline_normal_24);

                workoutExercises.get(0).setSeries(remainingSeries - 1);
                pause_watch.start();
            } else {
                saveObject();
                deleteChildren(1);
            }

            pause_watch.setOnTickListener(new Timer.OnTickListener() {
                @Override
                public void onTick(Timer timer) {
                }

                @Override
                public void onComplete(Timer timer) {
                    binding.nextButton.setClickable(true);
                    binding.pauseTimerText.setVisibility(View.INVISIBLE);
                    ImageView statIcon = returnFirstChildren().findViewById(R.id.statusIcon);
                    statIcon.setImageResource(R.drawable.baseline_directions_run_24);
                }
            });
        }
    }

    // Salva l'esercizio eseguito nel database
    private void saveObject() {
        String userId = workoutExercises.get(0).getUserId();
        long workoutExerciseId = workoutExercises.get(0).getId();
        long externalExerciseId = timerExercises.get(0).getId();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String formattedDate = dateFormat.format(date);
        ExerciseCompleted newSave = new ExerciseCompleted(userId,
                workoutExerciseId,
                externalExerciseId,
                formattedDate);

        workoutExerciseViewModel.saveExerciseCompleted(newSave);
        //exerciseNumber++;
    }

    //Ritorna la view del primo elemento nella lista dinamica
    private View returnFirstChildren() {
        return binding.dynamicList.getChildAt(0);
    }

    //Elimina il primo figlio della lista
    private void deleteChildren(int i) {
        View firstChild = binding.dynamicList.getChildAt(0);
        binding.dynamicList.removeView(firstChild);
        timerExercises.remove(0);
        workoutExercises.remove(0);

        //In caso ci siano 0 figli rimasti il timer termina, viene mostrato il toast con il tempo di esecuzione e ritorna al fragment precedente
        if (workoutExercises.isEmpty()) {
            Toast.makeText(requireContext(),requireContext().getString(R.string.workout_end) + " " + TimeUtils.convertMsToTime(stopwatch.getElapsedTime()) + "!",
                    Toast.LENGTH_SHORT).show();
            NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.timerFragment, true)  // Replace with the correct ID of TimerFragment
                    .build();
            TimerFragmentDirections.ActionTimerFragmentToListWorkoutExercisesFragment action = TimerFragmentDirections.actionTimerFragmentToListWorkoutExercisesFragment(
                    scheduleId);
            Navigation.findNavController(requireView()).navigate(action, navOptions);
        } else {
            returnFirstChildren().findViewById(R.id.statusIcon).setVisibility(View.VISIBLE);
            ImageView statIcon = returnFirstChildren().findViewById(R.id.statusIcon);
            statIcon.setImageResource(R.drawable.baseline_airline_seat_recline_normal_24);
            if (i == 1) pause_watch.start();
        }
    }


    //Avvia il cronometro una volta creata la view
    private void initializeStopwatch() {
        stopwatch = new Stopwatch();
        stopwatch.setTextView(binding.timerText);
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