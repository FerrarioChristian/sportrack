package it.unimib.icasiduso.sportrack.ui.schedule;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.WorkoutExerciseRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.data.repository.workout_exercise.IWorkoutExercisesRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentListWorkoutExercisesBinding;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.WorkoutExerciseViewModel;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ListWorkoutExercisesFragment extends Fragment implements WorkoutExerciseRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = ListWorkoutExercisesFragment.class.getSimpleName();
    FragmentListWorkoutExercisesBinding binding;
    private WorkoutExerciseViewModel workoutExerciseViewModel;
    private WorkoutExerciseRecyclerViewAdapter workoutExerciseRecyclerViewAdapter;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            workoutExerciseViewModel.deleteWorkoutExercise(position);
            workoutExerciseRecyclerViewAdapter.notifyItemRemoved(position);
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive).addBackgroundColor(ContextCompat.getColor(requireActivity(),
                            R.color.md_theme_errorContainer_mediumContrast))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .setActionIconTint(ContextCompat.getColor(requireActivity(),
                            R.color.md_theme_onErrorContainer))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public ListWorkoutExercisesFragment() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListWorkoutExercisesBinding.inflate(inflater, container, false);
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

        workoutExerciseRecyclerViewAdapter = new WorkoutExerciseRecyclerViewAdapter(this,
                getViewLifecycleOwner(),
                exerciseViewModel);
        binding.recyclerviewWorkoutExerciseList.setAdapter(workoutExerciseRecyclerViewAdapter);

        setListeners();
        observeViewModel();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewWorkoutExerciseList);
    }

    private void setListeners() {
        Long scheduleId = ListWorkoutExercisesFragmentArgs.fromBundle(getArguments())
                .getScheduleId();
        binding.addExerciseButton.setOnClickListener(v -> {
            ListWorkoutExercisesFragmentDirections.ActionListWorkoutExercisesFragmentToExercises action = ListWorkoutExercisesFragmentDirections.actionListWorkoutExercisesFragmentToExercises(
                    scheduleId);
            Navigation.findNavController(requireView()).navigate(action);
        });
    }

    private void observeViewModel() {
        long scheduleId = ListWorkoutExercisesFragmentArgs.fromBundle(getArguments())
                .getScheduleId();
        workoutExerciseViewModel.getWorkoutExercises(scheduleId)
                .observe(getViewLifecycleOwner(), result -> {
                    workoutExerciseRecyclerViewAdapter.setWorkoutExercises(result);
                    binding.noExercisesText.setVisibility(result.isEmpty() ? View.VISIBLE : View.GONE);
                });

        workoutExerciseViewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result) {
                binding.listWorkoutExercisesProgressBar.setVisibility(View.VISIBLE);
            } else {
                binding.listWorkoutExercisesProgressBar.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onExerciseClick(WorkoutExercise workoutExercise) {
       /* ListWorkoutExercisesFragmentDirections.ActionListExercisesFragmentToExerciseDetails
       action = ListExercisesFragmentDirections.actionListExercisesFragmentToExerciseDetails
       (scheduleId);
        Navigation.findNavController(view).navigate(action);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
