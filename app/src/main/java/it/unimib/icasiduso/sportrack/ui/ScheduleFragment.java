package it.unimib.icasiduso.sportrack.ui;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.checkerframework.checker.nullness.qual.NonNull;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapter.ExerciseRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.main.MainActivityWithBottomNav;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.schedule.Schedule;
import it.unimib.icasiduso.sportrack.repository.ScheduleRepository;

public class ScheduleFragment extends Fragment {

    private Schedule schedule;

    private ScheduleRepository scheduleRepository;

    public ScheduleFragment(){
        this.scheduleRepository = ((MainActivityWithBottomNav) getActivity()).getScheduleRepository();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ottengo l'oggetto Exercise dall'argomento passato
        Exercise exercise = ScheduleFragmentArgs.fromBundle(getArguments()).getExercise();
        // Aggiungo l'oggetto Exercise alla lista



        RecyclerView recyclerViewExerciseList = view.findViewById(R.id.recyclerview_exercise_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter(schedule.getExerciseCollection(), requireActivity().getApplication(), new ExerciseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onExerciseClick(Exercise exercise) {
                ScheduleFragmentDirections.ActionFragmentScheduleToFragmentExerciseDetails action = ScheduleFragmentDirections.actionFragmentScheduleToFragmentExerciseDetails(exercise);
                Navigation.findNavController(view).navigate(action);
            }
        });

        recyclerViewExerciseList.setLayoutManager(layoutManager);
        recyclerViewExerciseList.setAdapter(exerciseRecyclerViewAdapter);

    }


}