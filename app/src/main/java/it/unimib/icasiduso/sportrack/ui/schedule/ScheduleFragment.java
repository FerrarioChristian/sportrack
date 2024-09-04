package it.unimib.icasiduso.sportrack.ui.schedule;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapters.ScheduleRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentScheduleBinding;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.ScheduleViewModel;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ScheduleFragment extends Fragment implements ScheduleRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = ScheduleFragment.class.getSimpleName();

    private ScheduleViewModel scheduleViewModel;
    private ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;
    FragmentScheduleBinding binding;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ScheduleFragment() {}

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            scheduleViewModel.deleteSchedule(position);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IScheduleRepository scheduleRepository = ServiceLocator.getInstance().getScheduleRepository();
        ScheduleViewModel.Factory factory = new ScheduleViewModel.Factory(scheduleRepository);
        scheduleViewModel = new ViewModelProvider(requireActivity(), factory).get(ScheduleViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter(this);
        binding.recyclerviewScheduleList.setAdapter(scheduleRecyclerViewAdapter);

        observeViewModel();

        binding.newScheduleFAB.setOnClickListener(v -> showNewScheduleDialog());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewScheduleList);
    }

    private void showNewScheduleDialog() {
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.new_schedule_dialog, null);

        AlertDialog dialog = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.new_schedule))
                .setView(customView)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.add, null)
                .show();

        Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(view -> {
                TextInputLayout scheduleNameInput = customView.findViewById(R.id.scheduleName);
                MaterialButtonToggleGroup scheduleDifficulty = customView.findViewById(R.id.difficultyButton);

                String scheduleName = scheduleNameInput.getEditText().getText() != null ? scheduleNameInput.getEditText().getText().toString() : "";

                if (scheduleName.isEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.invalid_input), Toast.LENGTH_SHORT).show();
                    return;
                }

                MaterialButton selectedButton = customView.findViewById(scheduleDifficulty.getCheckedButtonId());
                String difficulty = selectedButton.getText().toString();
                scheduleViewModel.newSchedule(new Schedule(user.getUid(),scheduleName, difficulty));

                dialog.dismiss();
            });
    }

    public void observeViewModel() {
        scheduleViewModel.getSchedules(user.getUid()).observe(getViewLifecycleOwner(), result -> {
            scheduleRecyclerViewAdapter.setSchedules(result);
            requireView().findViewById(R.id.no_schedule_text).setVisibility(result.isEmpty() ? View.VISIBLE : View.GONE);
        });


        ProgressBar progressBar = requireView().findViewById(R.id.schedule_progress_bar);
        scheduleViewModel.getIsLoadingLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onScheduleClick(Schedule schedule) {
        ScheduleFragmentDirections.ActionScheduleFragmentToListWorkoutExercisesFragment action = ScheduleFragmentDirections.actionScheduleFragmentToListWorkoutExercisesFragment(schedule.getScheduleId());
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}