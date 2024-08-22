package it.unimib.icasiduso.sportrack.ui.schedule;

import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.ScheduleRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.schedule.ScheduleRepository;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ScheduleFragment extends Fragment {} /*implements ScheduleRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = ScheduleFragment.class.getSimpleName();
    private List<Schedule> scheduleList;
    private ScheduleRepository scheduleRepository;
    private ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton newScheduleButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleRepository = new ScheduleRepository(requireActivity().getApplication(), this);
        scheduleList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.schedule_progress_bar);
        if (scheduleList.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
        }

        newScheduleButton = view.findViewById(R.id.newScheduleFAB);
        newScheduleButton.setOnClickListener(v -> {
            View customView = LayoutInflater.from(getContext()).inflate(R.layout.new_schedule_dialog, null);

            new MaterialAlertDialogBuilder(getContext()).setTitle(getString(R.string.new_schedule)).setView(customView).setNegativeButton("Close", (dialog, which) -> {
                    })
                    .setPositiveButton(R.string.add, (dialog, which) -> {
                        TextInputLayout scheduleName = customView.findViewById(R.id.scheduleName);
                        MaterialButtonToggleGroup scheduleDifficulty = customView.findViewById(R.id.difficultyButton);

                        MaterialButton selectedButton = customView.findViewById(scheduleDifficulty.getCheckedButtonId());
                        String difficulty = selectedButton.getText().toString();

                        scheduleRepository.insertSchedule(new Schedule(scheduleName.getEditText().getText().toString(), difficulty));
                    })
                    .show();

        });


        RecyclerView recyclerViewScheduleList = view.findViewById(R.id.recyclerview_schedule_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(scheduleList, requireActivity().getApplication(), schedule -> {
            ScheduleFragmentDirections.ActionScheduleFragmentToListWorkoutExercisesFragment action = ScheduleFragmentDirections.actionScheduleFragmentToListWorkoutExercisesFragment(schedule.getScheduleId());
            Navigation.findNavController(view).navigate(action);
        });
        recyclerViewScheduleList.setLayoutManager(layoutManager);
        recyclerViewScheduleList.setAdapter(scheduleRecyclerViewAdapter);

        scheduleRepository.fetchSchedules();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewScheduleList);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            WorkoutExerciseRepository workoutExerciseRepository = new WorkoutExerciseRepository(requireActivity().getApplication(), new WorkoutExerciseRepositoryCallbackable() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onSuccess(List<WorkoutExercise> workoutExercises) {
                }

                @Override
                public void onFailure(String errorMessage) {
                }
            });

            workoutExerciseRepository.deleteWorkoutExercisesByScheduleId(scheduleList.get(position).getScheduleId());
            scheduleRepository.deleteSchedule(scheduleList.get(position));
            scheduleList.remove(position);
            scheduleRecyclerViewAdapter.notifyItemRemoved(position);

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.md_theme_errorContainer_mediumContrast))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .setActionIconTint(ContextCompat.getColor(requireActivity(), R.color.md_theme_onErrorContainer))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onSuccess(List<Schedule> scheduleList) {
        if (scheduleList != null) {
            this.scheduleList.clear();
            this.scheduleList.addAll(scheduleList);
            Activity activity = getActivity();
            if (activity != null) {

                activity.runOnUiThread(() -> {
                    scheduleRecyclerViewAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                });
            }
        }
    }

    @Override
    public void onFailure(String errorMessage) {

    }
}*/