package it.unimib.icasiduso.sportrack.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.adapter.ExerciseRecyclerViewAdapter;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.ExerciseCollection;

public class ListExercisesFragment extends Fragment {
    private ExerciseCollection exercises;

    public ListExercisesFragment() {
    }

    public ListExercisesFragment(ExerciseCollection exercises) {
        this.exercises = exercises;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_exercises, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exercises = ListExercisesFragmentArgs.fromBundle(getArguments()).getExercises();

        RecyclerView recyclerViewExerciseList = view.findViewById(R.id.recyclerview_exercise_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter(exercises, requireActivity().getApplication(), new ExerciseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onExerciseClick(Exercise exercise) {
                ListExercisesFragmentDirections.ActionFragmentListExercisesToFragmentExerciseDetails action = ListExercisesFragmentDirections.actionFragmentListExercisesToFragmentExerciseDetails(exercise);
                Navigation.findNavController(view).navigate(action);
            }
        });

        recyclerViewExerciseList.setLayoutManager(layoutManager);
        recyclerViewExerciseList.setAdapter(exerciseRecyclerViewAdapter);
    }
}
