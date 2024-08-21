package it.unimib.icasiduso.sportrack.ui.schedule;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.WorkoutExerciseRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.WorkoutExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.WorkoutExerciseRepositoryCallbackable;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListWorkoutExercisesFragment extends Fragment /*implements WorkoutExerciseRepositoryCallbackable, WorkoutExerciseRecyclerViewAdapter.OnItemClickListener*/ {
/*
    private static final String TAG = ListWorkoutExercisesFragment.class.getSimpleName();

    private List<WorkoutExercise> workoutExercises;
    private WorkoutExerciseRepository workoutExercisesRepository;
    private WorkoutExerciseRecyclerViewAdapter workoutExerciseRecyclerViewAdapter;
    private ProgressBar progressBar;
    private FloatingActionButton addExerciseButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutExercisesRepository = new WorkoutExerciseRepository(requireActivity().getApplication(), this);
        workoutExercises = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_workout_exercises, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Long scheduleId = ListWorkoutExercisesFragmentArgs.fromBundle(getArguments()).getScheduleId();

        progressBar = view.findViewById(R.id.list_workout_exercises_progress_bar);
        if(workoutExercises.isEmpty()){
            progressBar.setVisibility(View.VISIBLE);
        }

        addExerciseButton = view.findViewById(R.id.addExerciseButton);
        addExerciseButton.setOnClickListener(v -> {
            ListWorkoutExercisesFragmentDirections.ActionListWorkoutExercisesFragmentToExercises action = ListWorkoutExercisesFragmentDirections.actionListWorkoutExercisesFragmentToExercises(scheduleId);
            Navigation.findNavController(view).navigate(action);
        });


        RecyclerView recyclerViewExerciseList = view.findViewById(R.id.recyclerview_workout_exercise_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);



        //ExercisesRepository exercisesRepository = new ExercisesRepository(requireActivity().getApplication(),this);


        workoutExerciseRecyclerViewAdapter = new WorkoutExerciseRecyclerViewAdapter(workoutExercises, requireActivity().getApplication(), this);
        recyclerViewExerciseList.setLayoutManager(layoutManager);
        recyclerViewExerciseList.setAdapter(workoutExerciseRecyclerViewAdapter);

        workoutExercisesRepository.fetchWorkoutExercisesByScheduleId(scheduleId);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewExerciseList);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(List<WorkoutExercise> workoutExercises) {
        if(workoutExercises != null){
            this.workoutExercises.clear();
            this.workoutExercises.addAll(workoutExercises);
            Activity activity = getActivity();
            if(activity != null){
                activity.runOnUiThread(() -> {
                    workoutExerciseRecyclerViewAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                });
            }
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, errorMessage);
    }

    @Override
    public void onExerciseClick(WorkoutExercise workoutExercise) {
            //ListWorkoutExercisesFragmentDirections.ActionListExercisesFragmentToExerciseDetails action = ListExercisesFragmentDirections.actionListExercisesFragmentToExerciseDetails(scheduleId,);
            //Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onLongExerciseClick(WorkoutExercise workoutExercise) {

    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            workoutExercisesRepository.deleteWorkoutExercise(workoutExercises.get(position));
            workoutExercises.remove(position);
            workoutExerciseRecyclerViewAdapter.notifyItemRemoved(position);
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

*/

}
