package it.unimib.icasiduso.sportrack.ui;

import static java.lang.Integer.parseInt;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.CarouselRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentHomepageBinding;
import it.unimib.icasiduso.sportrack.databinding.FragmentTimerBinding;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCompleted;
import it.unimib.icasiduso.sportrack.ui.schedule.TimerFragment;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.WorkoutExerciseViewModel;

public class HomepageFragment extends Fragment {
    private static final String TAG = HomepageFragment.class.getSimpleName();

    private FragmentHomepageBinding binding;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private WorkoutExerciseViewModel workoutExerciseViewModel;
    private ExerciseViewModel exerciseViewModel;
    List<ExerciseCompleted> exerciseCompletedList = new ArrayList<>();
    int[] activityData;

    public HomepageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IWorkoutExercisesRepository workoutExercisesRepository = ServiceLocator.getInstance().getWorkoutExercisesRepository();
        WorkoutExerciseViewModel.Factory factory = new WorkoutExerciseViewModel.Factory(workoutExercisesRepository);
        workoutExerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(WorkoutExerciseViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomepageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IExerciseRepository exercisesRepository = ServiceLocator.getInstance().getExercisesRepository();
        ExerciseViewModel.Factory factory = new ExerciseViewModel.Factory(exercisesRepository);
        ExerciseViewModel exerciseViewModel = new ViewModelProvider(requireActivity(), factory).get(ExerciseViewModel.class);

        setListeners();

        observeViewModel();

        initializeHeatmap();

        initializeCarousel();
    }

    private void observeViewModel() {
        workoutExerciseViewModel.getExercisesCompleted(user.getUid()).observe(getViewLifecycleOwner(), result -> {
            Log.d(TAG, "observeViewModel: " + result.size());
        });
    }

    private void setListeners() {

    }

    private void initializeHeatmap(){
        GridLayout heatmapGrid = requireView().findViewById(R.id.heatmapGrid);

        // Example activity data for each day of the month (assume 30 days)
        activityData = new int[]{1, 5, 3, 7, 0, 2, 5, 4, 8, 1, 3, 4, 6, 1, 9, 3, 0, 2, 5, 7, 3, 2, 4, 6, 0, 8, 1, 3, 4, 9, 3};

        // Calendar to determine the first day of the month
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 0 for Sunday, 1 for Monday, etc.

        // Add empty views for days before the first day of the month
        for (int i = 0; i < firstDayOfWeek; i++) {
            TextView emptyView = new TextView(requireContext());
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
            heatmapGrid.addView(emptyView);
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

            heatmapGrid.addView(dayView, params);
        }
    }

    private void initializeCarousel() {
        // Inflate the layout for this fragment
        RecyclerView recyclerView = requireView().findViewById(R.id.recycler);
        ArrayList<String> arrayList = new ArrayList<>();

        //Add multiple images to arraylist.
        arrayList.add("https://images.unsplash.com/photo-1692528131755-d4e366b2adf0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzNXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692862582645-3b6fd47b7513?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692584927805-d4096552a5ba?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0Nnx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692854236272-cc49076a2629?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1681207751526-a091f2c6a538?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODF8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692610365998-c628604f5d9f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODZ8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60");


        CarouselRecyclerViewAdapter adapter = new CarouselRecyclerViewAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CarouselRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                //Do something like opening the image in new activity or showing it in full screen or something else.
            }
        });
    }
}