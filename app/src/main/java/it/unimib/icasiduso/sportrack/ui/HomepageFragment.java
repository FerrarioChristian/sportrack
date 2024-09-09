package it.unimib.icasiduso.sportrack.ui;

import static it.unimib.icasiduso.sportrack.utils.Constants.CAROUSEL_IMAGES;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

        // Check the current night mode
        boolean isNightMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        if (isNightMode) binding.imageViewUser.setColorFilter(getContext().getColor(R.color.md_theme_onPrimary));


        observeViewModel();
        initializeCarousel();
    }

    private void observeViewModel() {
        assert user != null;
        workoutExerciseViewModel.getExercisesCompleted(user.getUid())
                .observe(getViewLifecycleOwner(), result -> {
                    exerciseCompletedList.clear();
                    if (result != null && !result.isEmpty()) {
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
        Map<String, Integer> muscleCountMap = new HashMap<>();

        String muscle = exercise.getMuscle();
        muscleCountMap.put(muscle, muscleCountMap.getOrDefault(muscle, 0) + 1);

        String mostUsedMuscle = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : muscleCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostUsedMuscle = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        if (mostUsedMuscle != null) {
            binding.muscleName.setText(TextParser.parseText(mostUsedMuscle));
        }
    }

    @SuppressWarnings("ConstantConditions")
    //Restituisce un array contenente il numero di esercizi per data
    private int[] countExercisesPerDate(List<ExerciseCompleted> exerciseCompletedList) {
        // Create a map to count occurrences of each date
        Map<LocalDate, Integer> dateCountMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (ExerciseCompleted exercise : exerciseCompletedList) {
            LocalDate date = LocalDate.parse(exercise.getDate(), formatter);
            dateCountMap.put(date, dateCountMap.getOrDefault(date, 0) + 1);
        }

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(34);

        int[] countsArray = new int[35];

        LocalDate currentDate = startDate;
        int index = 0;

        while (!currentDate.isAfter(endDate)) {
            countsArray[index] = dateCountMap.getOrDefault(currentDate, 0);
            currentDate = currentDate.plusDays(1);
            index++;
        }
        return countsArray;
    }

    @SuppressWarnings("ConstantConditions")
    private void initializeHeatmap(int bestDay) {
        binding.heatmapGrid.removeAllViews();

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(34);

        Map<LocalDate, Integer> activityMap = new HashMap<>();
        for (ExerciseCompleted exercise : exerciseCompletedList) {
            LocalDate date = null;
            date = LocalDate.parse(exercise.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int level = activityMap.getOrDefault(date, 0);
            activityMap.put(date, Math.min(level + 1, 30));
        }

        int cellDims = calculateCellDims();

        LocalDate currentDate = startDate;
        for (int i = 0; i < 35; i++) {
            int activityLevel = activityMap.getOrDefault(currentDate, 0);

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


            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = cellDims;
            params.height = cellDims;
            params.setMargins(8, 8, 8, 8); // Adjust margins as needed

            float[] radii = new float[8];
            Arrays.fill(radii, 16);
            ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(radii, null, null));
            drawable.getPaint().setColor(color);
            dayView.setBackground(drawable);

            dayView.setLayoutParams(params);
            binding.heatmapGrid.addView(dayView, params);

            dayView.setOnClickListener(v -> {
                        Toast.makeText(requireContext(), getContext().getString(R.string.exercise_done_toast) + " " + activityLevel, Toast.LENGTH_SHORT).show();
                    });

            currentDate = currentDate.plusDays(1);
        }
    }

    private int calculateCellDims() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (getActivity() != null) {
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        int screenWidth = displayMetrics.widthPixels - Math.round(120 * (displayMetrics.densityDpi / 160f));
        int cellDims = screenWidth / 7;
        return cellDims;
    }

    //Dall'array activityData restituisce l'indice del giorno con il maggior numero di esercizi
    private int findDayWithMostExercises() {
        int maxCount = 0;
        int dayIndex = -1;
        int numberOfDays = 34;
        int startIndex = Math.max(activityData.length - numberOfDays, 0);

        for (int i = startIndex; i < activityData.length; i++) {
            if (activityData[i] > maxCount) {
                maxCount = activityData[i];
                dayIndex = i;
            }
        }
        if (dayIndex >= startIndex) {
            dayIndex = dayIndex - startIndex;
        }

        return dayIndex + 1;
    }


    //Inizializza il Carousel usando recyclerView
    private void initializeCarousel() {
        ArrayList<String> carouselImages = new ArrayList<>(Arrays.asList(CAROUSEL_IMAGES));

        CarouselRecyclerViewAdapter adapter = new CarouselRecyclerViewAdapter(getContext(),
                carouselImages);
        binding.recycler.setAdapter(adapter);

    }


}