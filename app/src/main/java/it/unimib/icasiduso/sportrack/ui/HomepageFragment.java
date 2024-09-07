package it.unimib.icasiduso.sportrack.ui;

import static it.unimib.icasiduso.sportrack.utils.Constants.CAROUSEL_IMAGES;

import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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

        IExerciseRepository exercisesRepository = ServiceLocator.getInstance()
                .getExercisesRepository();
        ExerciseViewModel.Factory factory = new ExerciseViewModel.Factory(exercisesRepository);
        ExerciseViewModel exerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(
                ExerciseViewModel.class);

        setListeners();
        observeViewModel();
        initializeHeatmap();
        initializeCarousel();
    }

    private void observeViewModel() {
        assert user != null;
        workoutExerciseViewModel.getExercisesCompleted(user.getUid())
                .observe(getViewLifecycleOwner(), result -> {
                    exerciseCompletedList.clear();
                    if (result != null) {
                        exerciseCompletedList = result;
                    }
                });
    }

    private void setListeners() {

    }

    private void initializeHeatmap() {

        // Example activity data for each day of the month (assume 30 days)
        activityData = new int[]{1, 5, 3, 7, 0, 2, 5, 4, 8, 1, 3, 4, 6, 1, 9, 3, 0, 2, 5, 7, 3, 2
                , 4, 6, 0, 8, 1, 3, 4, 9, 3};

        // Calendar to determine the first day of the month
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 0 for Sunday, 1 for
        // Monday, etc.

        // Add empty views for days before the first day of the month
        for (int i = 0; i < firstDayOfWeek; i++) {
            TextView emptyView = new TextView(requireContext());
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
            binding.heatmapGrid.addView(emptyView);
        }

        // Add the days of the month
        for (int i = 0; i < activityData.length; i++) {
            int activityLevel = activityData[i];

            TextView dayView = new TextView(requireContext());
            dayView.setText(String.valueOf(i + 1)); // Set the day number
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
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 110;
            params.height = 110;
            params.setMargins(8, 8, 8, 8);

            binding.heatmapGrid.addView(dayView, params);
        }
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
}