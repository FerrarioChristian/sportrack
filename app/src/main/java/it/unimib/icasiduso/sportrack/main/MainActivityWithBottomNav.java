package it.unimib.icasiduso.sportrack.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.icasiduso.sportrack.R;

public class MainActivityWithBottomNav extends AppCompatActivity {
    private static final String TAG = MainActivityWithBottomNav.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_bottom_nav);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        checkSession();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);


        //TODO
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

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    private void checkSession() {
        if (currentUser == null) {
            Intent intent = new Intent(this, MainActivityWithBottomNav.class);
            startActivity(intent);
            finish();
        }
    }
}