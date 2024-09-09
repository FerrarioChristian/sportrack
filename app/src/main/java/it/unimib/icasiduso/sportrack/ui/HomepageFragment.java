package it.unimib.icasiduso.sportrack.ui;

import static it.unimib.icasiduso.sportrack.utils.Constants.CAROUSEL_IMAGES;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.CarouselRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentHomepageBinding;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCompleted;
import it.unimib.icasiduso.sportrack.utils.DeviceUtils;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.utils.TextParser;
import it.unimib.icasiduso.sportrack.utils.TimeUtils;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.WorkoutExerciseViewModel;

public class HomepageFragment extends Fragment {
    private static final String TAG = HomepageFragment.class.getSimpleName();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int[] activityData;
    private FragmentHomepageBinding binding;
    private WorkoutExerciseViewModel workoutExerciseViewModel;
    private ExerciseViewModel exerciseViewModel;
    private List<ExerciseCompleted> exerciseCompletedList = new ArrayList<>();

    public HomepageFragment() {
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
        ExerciseViewModel.Factory exerciseViewModelFactory = new ExerciseViewModel.Factory(
                exercisesRepository);
        exerciseViewModel = new ViewModelProvider(requireActivity(), exerciseViewModelFactory).get(
                ExerciseViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomepageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Imposto il logo in base al tema
        boolean isNightMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        if (isNightMode) binding.imageViewUser.setColorFilter(requireContext().getColor(R.color.md_theme_onPrimary));

        observeViewModel();
        initializeCarousel();
    }

    // Osservo il viewmodel per aggiornare la UI
    private void observeViewModel() {
        assert user != null;
        workoutExerciseViewModel.getExercisesCompleted(user.getUid())
                .observe(getViewLifecycleOwner(), result -> {
                    exerciseCompletedList.clear();

                    // Se ci sono dati presenti eseguo i vari calcoli e inizializzo la Heatmap e le relative card
                    if (result != null && !result.isEmpty()) {
                        binding.muscleCard.setVisibility(View.VISIBLE);
                        binding.dayCard.setVisibility(View.VISIBLE);
                        exerciseCompletedList = result;
                        activityData = countExercisesPerDate(exerciseCompletedList);
                        int dayIndex = findDayWithMostExercises();
                        initializeHeatmap(dayIndex);

                        String finalDate = TimeUtils.getDateFromDayIndex(dayIndex, 34);
                        String dayNameTemp = TimeUtils.getDayNameFromDate(finalDate);

                        binding.dayName.setText(dayNameTemp);
                        binding.dayDate.setText(finalDate);
                        exerciseViewModel.getExerciseById(result.get(0).getExerciseId());
                    }
                });

        exerciseViewModel.getExerciseLiveData().observe(getViewLifecycleOwner(), exercise -> {
            if (exercise != null) {
                findMostUsedMuscle(exercise);
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    //Prende la lista di esercizi e restituisce il muscle più usato
    private void findMostUsedMuscle(Exercise exercise) {
        // Crea una mappa per contare le occorrenze dei muscoli
        Map<String, Integer> muscleCountMap = new HashMap<>();

        // Ottiene il muscolo associato all'esercizio e aggiunge il muscolo alla mappa o incrementa il suo contatore se già presente
        String muscle = exercise.getMuscle();
        muscleCountMap.put(muscle, muscleCountMap.getOrDefault(muscle, 0) + 1);

        String mostUsedMuscle = null;
        int maxCount = 0;

        // Itera su ogni entry della mappa per trovare il muscolo più usato
        for (Map.Entry<String, Integer> entry : muscleCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostUsedMuscle = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        // Imposta il nome del muscolo nel TextView dopo averlo formattato
        if (mostUsedMuscle != null) {
            binding.muscleName.setText(TextParser.parseText(mostUsedMuscle));
        }
    }

    @SuppressWarnings("ConstantConditions")
    //Restituisce un array contenente il numero di esercizi per data
    private int[] countExercisesPerDate(List<ExerciseCompleted> exerciseCompletedList) {
        // Crea una mappa per contare le occorrenze di ogni data e imposta il formatter
        Map<LocalDate, Integer> dateCountMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Itera su ogni esercizio completato e conta quante volte appare ogni data
        for (ExerciseCompleted exercise : exerciseCompletedList) {
            LocalDate date = LocalDate.parse(exercise.getDate(), formatter);
            dateCountMap.put(date, dateCountMap.getOrDefault(date, 0) + 1);
        }

        // Ottieni la data di oggi come data finale
        LocalDate endDate = LocalDate.now();
        // Calcola la data di inizio, 34 giorni prima di oggi
        LocalDate startDate = endDate.minusDays(34);

        int[] countsArray = new int[35];

        LocalDate currentDate = startDate;
        int index = 0;

        // Itera attraverso ogni giorno dal giorno di inizio fino a quello finale
        while (!currentDate.isAfter(endDate)) {
            // Popola l'array con il conteggio degli esercizi per ogni data
            countsArray[index] = dateCountMap.getOrDefault(currentDate, 0);
            currentDate = currentDate.plusDays(1);
            index++;
        }

        // Restituisce l'array con i conteggi degli esercizi per ciascun giorno
        return countsArray;
    }

    @SuppressWarnings("ConstantConditions")
    private void initializeHeatmap(int bestDay) {
        // Rimuove tutte le viste esistenti nella griglia della heatmap
        binding.heatmapGrid.removeAllViews();

        // Definisce la data finale e quella di inizio per l'intervallo di 35 giorni
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(34);

        // Crea una mappa per memorizzare il livello di attività per ogni data
        Map<LocalDate, Integer> activityMap = new HashMap<>();
        // Itera attraverso gli esercizi completati per popolare la mappa
        for (ExerciseCompleted exercise : exerciseCompletedList) {
            LocalDate date = LocalDate.parse(exercise.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int level = activityMap.getOrDefault(date, 0);
            activityMap.put(date, Math.min(level + 1, 30));
        }

        // Calcola le dimensioni di ciascella nella griglia
        int cellDims = DeviceUtils.getScreenWidthMinusPadding(requireActivity(), 130) / 7;


        // Data corrente per la generazione della heatmap
        LocalDate currentDate = startDate;
        for (int i = 0; i < 35; i++) {
            // Ottiene il livello di attività per la data corrente
            int activityLevel = activityMap.getOrDefault(currentDate, 0);

            // Crea e configura una TextView per rappresentare un giorno nella griglia
            TextView dayView = new TextView(requireContext());
            dayView.setText(String.valueOf(currentDate.getDayOfMonth()));
            dayView.setGravity(Gravity.CENTER);

            dayView.setTextColor(Color.BLACK);

            int color;
            switch (activityLevel) {
                case 0:
                    color = requireContext().getColor(R.color.md_theme_surfaceContainer);
                    dayView.setTextColor(requireContext().getColor(R.color.md_theme_onSurface));
                    break;
                case 1:
                case 2:
                    color = Color.parseColor("#aae4a5");
                    break;
                case 3:
                case 4:
                    color = Color.parseColor("#85d87d");
                    break;
                case 5:
                case 6:
                    color = Color.parseColor("#43bc38");
                    break;
                case 7:
                case 8:
                case 9:
                default:
                    color = Color.parseColor("#389d2f");
                    break;
            }

            if (i == bestDay) color = Color.parseColor("#FFD700");  //Colore oro per il giorno migliore
            if (i == 34) {
                color = Color.parseColor("#8591ff"); // Colore azzurro per la data odierna
                dayView.setTextColor(Color.BLACK);
            }

            // Configura i parametri di layout per la TextView
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = cellDims;
            params.height = cellDims;
            params.setMargins(8, 8, 8, 8); // Adjust margins as needed

            // Crea un Drawable per la forma della cella
            float[] radii = new float[8];
            Arrays.fill(radii, 16);
            ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(radii, null, null));
            drawable.getPaint().setColor(color);
            dayView.setBackground(drawable);

            dayView.setLayoutParams(params);
            binding.heatmapGrid.addView(dayView, params);

            // Imposta un listener per visualizzare un Toast al clic della cella
            dayView.setOnClickListener(v -> Toast.makeText(requireContext(), getContext().getString(R.string.exercise_done_toast) + " " + activityLevel, Toast.LENGTH_SHORT).show());

            currentDate = currentDate.plusDays(1);
        }
    }

    //Dall'array activityData restituisce l'indice del giorno con il maggior numero di esercizi
    private int findDayWithMostExercises() {
        int maxCount = 0;
        int dayIndex = -1;
        int numberOfDays = 34;
        // Calcola l'indice di partenza, considerando solo gli ultimi 35 giorni
        int startIndex = Math.max(activityData.length - numberOfDays, 0);

        // Itera sugli ultimi giorni dell'array per trovare il giorno con il massimo numero di esercizi
        for (int i = startIndex; i < activityData.length; i++) {
            if (activityData[i] > maxCount) {
                // Aggiorna il conteggio massimo e l'indice del giorno corrispondente
                maxCount = activityData[i];
                dayIndex = i;
            }
        }

        // Calcola l'indice relativo all'interno dei 35 giorni considerati
        if (dayIndex >= startIndex) {
            dayIndex = dayIndex - startIndex;
        }

        // Restituisce l'indice del giorno con il maggior numero di esercizi (basato su 1 come primo giorno)
        return dayIndex + 1;
    }


    //Inizializza il Carousel usando una recyclerView
    private void initializeCarousel() {
        ArrayList<String> carouselImages = new ArrayList<>(Arrays.asList(CAROUSEL_IMAGES));

        CarouselRecyclerViewAdapter adapter = new CarouselRecyclerViewAdapter(getContext(),
                carouselImages);
        binding.recycler.setAdapter(adapter);
    }


}