package it.unimib.icasiduso.sportrack.ui;

import static it.unimib.icasiduso.sportrack.utils.Constants.CAROUSEL_IMAGES;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unimib.icasiduso.sportrack.adapters.CarouselRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentHomepageBinding;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCompleted;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
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

        setListeners();
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

                        initializeHeatmap();

                        int dayIndex = findDayWithMostExercises();

                        String dayNameTemp = getDayNameFromIndex(dayIndex);

                        String finalDate = "";

                        if (!(dayIndex < 0))
                            finalDate = exerciseCompletedList.get(dayIndex).getDate();

                        binding.dayName.setText(dayNameTemp);

                        binding.dayDate.setText(finalDate);
                            exerciseViewModel.getExerciseById(result.get(0)
                                    .getExternalExerciseId());

                    }
                });

        exerciseViewModel.getExerciseLiveData().observe(getViewLifecycleOwner(), exercise -> {
            if (exercise != null) Log.d(TAG, "activityData: " + exercise.getMuscle());
        });
    }


    private int[] countExercisesPerDate(List<ExerciseCompleted> exerciseCompletedList) {
        // Create a map to count occurrences of each date
        Map<LocalDate, Integer> dateCountMap = new HashMap<>();
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        for (ExerciseCompleted exercise : exerciseCompletedList) {
            LocalDate date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = LocalDate.parse(exercise.getDate(), formatter);
            }
            dateCountMap.put(date, dateCountMap.getOrDefault(date, 0) + 1);
        }

        // Convert the map to an array of counts
        int numberOfDays = dateCountMap.size();
        int[] countsArray = new int[numberOfDays];
        Set<Map.Entry<LocalDate, Integer>> entries = dateCountMap.entrySet();
        int index = 0;

        for (Map.Entry<LocalDate, Integer> entry : entries) {
            countsArray[index++] = entry.getValue();
        }
        return countsArray;
    }


    private void setListeners() {

    }

    private void initializeHeatmap() {

        binding.heatmapGrid.removeAllViews();

        // Initialize the end date as today
        LocalDate endDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            endDate = LocalDate.now();
        }
        LocalDate startDate = null; // 35 days in total including today
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startDate = endDate.minusDays(34);
        }

        // Create a Calendar instance for calculating the first day of the month
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 0 for Sunday, 1 for
        // Monday, etc.

        // Add empty views for days before the first day of the month
        for (int i = 0; i < firstDayOfWeek; i++) {
            TextView emptyView = new TextView(requireContext());
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(110, 110));
            binding.heatmapGrid.addView(emptyView);
        }

        // Populate the grid from the start date to the end date
        LocalDate currentDate = startDate;
        for (int i = 34; i >= 0; i--) {
            int activityLevel = (i < activityData.length) ? activityData[i] : 0; // Use default
            // value if index is out of bounds

            TextView dayView = new TextView(requireContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dayView.setText(String.valueOf(currentDate.getDayOfMonth())); // Set the day number
            }
            dayView.setGravity(Gravity.CENTER);

            int color;
            switch (activityLevel) {
                case 0:
                    color = Color.parseColor("#ebedf0");
                    break;
                case 1:
                    color = Color.parseColor("#c6e48b");
                    break;
                case 2:
                    color = Color.parseColor("#7bc96f");
                    break;
                case 3:
                    color = Color.parseColor("#239a3b");
                    break;
                default:
                    color = Color.parseColor("#196127");
                    break;
            }

            dayView.setBackgroundColor(color);
            dayView.setTextColor(Color.BLACK);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 110;
            params.height = 110;
            params.setMargins(8, 8, 8, 8); // Adjust margins as needed

            dayView.setLayoutParams(params);
            binding.heatmapGrid.addView(dayView, params);

            // Move to the previous day
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                currentDate = currentDate.plusDays(1);
            }
        }
    }

    private int findDayWithMostExercises() {
        int maxCount = 0;
        int dayIndex = -1;

        for (int i = 0; i < activityData.length; i++) {
            if (activityData[i] > maxCount) {
                maxCount = activityData[i];
                dayIndex = i;
            }
        }

        return dayIndex; // Index of the day with the most exercises
    }


    private void initializeCarousel() {
        ArrayList<String> carouselImages = new ArrayList<>(Arrays.asList(CAROUSEL_IMAGES));

        CarouselRecyclerViewAdapter adapter = new CarouselRecyclerViewAdapter(getContext(),
                carouselImages);
        binding.recycler.setAdapter(adapter);

        adapter.setOnItemClickListener((imageView, path) -> {
            //TODO probably remove
        });
    }

    public String getDayNameFromIndex(int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dayIndex + 1); // dayIndex is zero-based, so add 1
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Convert the day of the week to a string
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "Unknown";
        }
    }
}