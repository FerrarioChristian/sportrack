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
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
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
    private List<Exercise> exerciseList = new ArrayList<>();

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




                        int dayIndex = findDayWithMostExercises();


                        Log.d(TAG,  dayIndex+ "activityData: " + exerciseCompletedList.size());

                        initializeHeatmap(dayIndex);

                        String dayNameTemp = getDayNameFromIndex(dayIndex);

                        String finalDate = calculateDate(dayIndex);

                        if (!(dayIndex < 0 || dayIndex >= exerciseCompletedList.size())) finalDate = exerciseCompletedList.get(dayIndex).getDate();

                        binding.dayName.setText(dayNameTemp);



                        binding.dayDate.setText(finalDate);
                            exerciseViewModel.getExerciseById(result.get(0)
                                    .getExternalExerciseId());

                    }
                });

        exerciseViewModel.getExerciseLiveData().observe(getViewLifecycleOwner(), exercise -> {
            if (exercise != null) {
                exerciseList.clear(); // Clear existing list
                exerciseList.add(exercise); // Add new data

                findMostUsedMuscle();
            }
        });
    }

    public static String calculateDate(int additionalDays) {
        // Define the date formatter
        additionalDays++;
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        // Get the current date
        LocalDate today = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            today = LocalDate.now();
        }

        // Calculate the start date (34 days ago)
        LocalDate startDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startDate = today.minusDays(34);
        }

        // Add the additional days
        LocalDate resultDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resultDate = startDate.plusDays(additionalDays);
        }

        String resultDateStr = null;

        // Format the result date to "dd/MM/yyyy"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            resultDateStr = resultDate.format(formatter);
        }

        return resultDateStr;
    }

    private void findMostUsedMuscle() {
        Map<String, Integer> muscleCountMap = new HashMap<>();

        // Count the occurrences of each muscle
        for (Exercise exercise : exerciseList) {
            String muscle = exercise.getMuscle();
            muscleCountMap.put(muscle, muscleCountMap.getOrDefault(muscle, 0) + 1);
        }

        // Find the most used muscle
        String mostUsedMuscle = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : muscleCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostUsedMuscle = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        // Use or display the most used muscle
        if (mostUsedMuscle != null) {
            // For example, show it in a TextView or log it
            binding.muscleName.setText(mostUsedMuscle);
        }

    }


    private int[] countExercisesPerDate(List<ExerciseCompleted> exerciseCompletedList) {
        // Create a map to count occurrences of each date
        Map<LocalDate, Integer> dateCountMap = new HashMap<>();
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        for (ExerciseCompleted exercise : exerciseCompletedList) {
            LocalDate date = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date = LocalDate.parse(exercise.getDate(), formatter);
            }
            dateCountMap.put(date, dateCountMap.getOrDefault(date, 0) + 1);
        }

        // Initialize the date range
        LocalDate endDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            endDate = LocalDate.now();
        }
        LocalDate startDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startDate = endDate.minusDays(34);
        }

        // Initialize counts array
        int[] countsArray = new int[35];

        // Populate the counts array with values from the dateCountMap
        LocalDate currentDate = startDate;
        int index = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (!currentDate.isAfter(endDate)) {
                countsArray[index] = dateCountMap.getOrDefault(currentDate, 0);
                currentDate = currentDate.plusDays(1);
                index++;
            }
        }

        return countsArray;
    }


    private void setListeners() {

    }

    private void initializeHeatmap(int bestDay) {

        binding.heatmapGrid.removeAllViews();

        // Initialize the end date as today
        LocalDate endDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            endDate = LocalDate.now();
        }
        LocalDate startDate = null; // 35 days total including today
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startDate = endDate.minusDays(34);
        }

        // Map dates to their activity levels
        Map<LocalDate, Integer> activityMap = new HashMap<>();
        for (ExerciseCompleted exercise : exerciseCompletedList) {
            LocalDate date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = LocalDate.parse(exercise.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            int level = activityMap.getOrDefault(date, 0);
            activityMap.put(date, Math.min(level + 1, 9)); // Limit to max level 9
        }

        // Populate the grid from the start date to the end date
        LocalDate currentDate = startDate;
        for (int i = 0; i < 35; i++) { // 35 days including today
            int activityLevel = activityMap.getOrDefault(currentDate, 0); // Default to 0 if no data

            TextView dayView = new TextView(requireContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dayView.setText(String.valueOf(currentDate.getDayOfMonth())); // Set the day number
            }
            dayView.setGravity(Gravity.CENTER);

            // Determine color based on activity level
            int color;
            switch (activityLevel) {
                case 0:
                    color = Color.WHITE;
                    break;
                case 1:
                    color = Color.parseColor("#aae4a5");
                    break;
                case 2:
                    color = Color.parseColor("#aae4a5");
                    break;
                case 3:
                    color = Color.parseColor("#85d87d");
                    break;
                case 4:
                    color = Color.parseColor("#85d87d");
                    break;
                case 5:
                    color = Color.parseColor("#43bc38");
                    break;
                case 6:
                    color = Color.parseColor("#43bc38");
                    break;
                case 7:
                    color = Color.parseColor("#389d2f");
                    break;
                case 8:
                    color = Color.parseColor("#389d2f");
                    break;
                case 9:
                    color = Color.parseColor("#389d2f");
                    break;
                default:
                    color = Color.parseColor("#cc0000");
                    break;
            }

            if (i == bestDay+1) {
                color = Color.parseColor("#FFD700");
            }

            // Highlight today's cell
            if (i == 34) {
                dayView.setBackgroundColor(Color.parseColor("#8591ff")); // Gold color for today
                dayView.setTextColor(Color.BLACK);
            } else {
                dayView.setBackgroundColor(color);
                dayView.setTextColor(Color.BLACK);
            }

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

        // Define the number of elements to consider
        int numberOfDays = 34;
        int startIndex = Math.max(activityData.length - numberOfDays, 0);

        for (int i = startIndex; i < activityData.length; i++) {
            if (activityData[i] > maxCount) {
                maxCount = activityData[i];
                dayIndex = i;
            }
        }

        // Adjust dayIndex relative to the last 34 elements
        if (dayIndex >= startIndex) {
            dayIndex = dayIndex - startIndex;
        } else {
            dayIndex = -1; // In case no valid day is found
        }

        Log.d(TAG, "findDayWithMostExercises: " + dayIndex);

        return dayIndex; // Index of the day with the most exercises within the last 34 days
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