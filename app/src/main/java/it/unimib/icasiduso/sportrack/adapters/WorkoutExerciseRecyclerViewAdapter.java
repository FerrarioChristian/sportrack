package it.unimib.icasiduso.sportrack.adapters;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.exercise.ExerciseRepositoryCallbackable;
import it.unimib.icasiduso.sportrack.data.repository.exercise.ExercisesRepository;
import it.unimib.icasiduso.sportrack.model.exercise.Exercise;
import it.unimib.icasiduso.sportrack.model.exercise.WorkoutExercise;

public class WorkoutExerciseRecyclerViewAdapter /*extends RecyclerView.Adapter<WorkoutExerciseRecyclerViewAdapter.WorkoutExerciseViewHolder>{
    List<WorkoutExercise> workoutExercises;
    private final Application application;
    private final OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onExerciseClick(WorkoutExercise workoutExercise);
        void onLongExerciseClick(WorkoutExercise workoutExercise);
    }

    public WorkoutExerciseRecyclerViewAdapter(List<WorkoutExercise> workoutExercises, Application application, OnItemClickListener onItemClickListener) {
        this.workoutExercises = workoutExercises;
        this.application = application;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public WorkoutExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_exercise_item, parent, false);
        return new WorkoutExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutExerciseViewHolder holder, int position) {
        holder.bind(workoutExercises.get(position));
    }

    @Override
    public int getItemCount() {
        if (workoutExercises == null)
            return 0;
        return workoutExercises.size();
    }

    public class WorkoutExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExerciseRepositoryCallbackable {
        private final TextView exerciseName;
        private final TextView exerciseSeries;
        private final TextView exerciseRepetitions;
        private final Context context;

        private Exercise exercise;

        public WorkoutExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            exerciseSeries = itemView.findViewById(R.id.exerciseSeries);
            exerciseRepetitions = itemView.findViewById(R.id.exerciseRepetitions);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }



       public void bind(WorkoutExercise workoutExercise) {
           ExercisesRepository exercisesRepository = new ExercisesRepository(application, new ExerciseRepositoryCallbackable() {
               @Override
               public void onSuccess(List<Exercise> exercises) {
                   exercise = exercises.get(0);
                   exerciseName.setText(exercise.getName());
               }

               @Override
               public void onFailure(String errorMessage) {
               }
           });
            exercisesRepository.getExerciseById(workoutExercise.getExternalExerciseId());
            exerciseSeries.setText(context.getString(R.string.series) + workoutExercise.getSeries());
            exerciseRepetitions.setText(context.getString(R.string.repetitions) + workoutExercise.getRepetitions());

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onExerciseClick(workoutExercises.get(getAdapterPosition()));
            onItemClickListener.onLongExerciseClick(workoutExercises.get(getAdapterPosition()));
        }

        @Override
        public void onSuccess(List<Exercise> exercises) {

        }

        @Override
        public void onFailure(String errorMessage) {

        }
    }

} */ {}
