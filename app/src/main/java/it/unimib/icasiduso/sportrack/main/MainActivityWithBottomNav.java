package it.unimib.icasiduso.sportrack.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.exercise.IExerciseRepository;
import it.unimib.icasiduso.sportrack.utils.Constants;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.ExerciseViewModel;

public class MainActivityWithBottomNav extends AppCompatActivity {
    private static final String TAG = MainActivityWithBottomNav.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_bottom_nav);

        downloadExercises();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        checkSession(currentUser);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(
                R.id.nav_host_fragment);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemReselectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.exercises) {
                navController.navigate(R.id.action_global_exercisesFragment);
            } else if (id == R.id.schedule) {
                navController.navigate(R.id.action_global_scheduleFragment);
            }
        });

        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    private void checkSession(FirebaseUser currentUser) {
        if (currentUser == null) {
            Intent intent = new Intent(this, MainActivityWithBottomNav.class);
            startActivity(intent);
            finish();
        }
    }

    private void downloadExercises() {
        IExerciseRepository exerciseRepository = ServiceLocator.getInstance()
                .getExercisesRepository();
        ExerciseViewModel exerciseViewModel = new ViewModelProvider(this,
                new ExerciseViewModel.Factory(exerciseRepository)).get(ExerciseViewModel.class);
        for (String muscle : Constants.MUSCLES) {
            exerciseViewModel.getExercisesByMuscle(muscle);
        }
    }
}