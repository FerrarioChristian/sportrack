package it.unimib.icasiduso.sportrack.ui;

import static androidx.core.app.ActivityCompat.recreate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Locale;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.main.MainActivity;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.UserViewModel;

public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private UserViewModel userViewModel;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModel.Factory(userRepository)).get(UserViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //TODO Fix logout (tocca prima sistemare UserViewModel, Login e Register)
        Button signOutButton = view.findViewById(R.id.logoutButton);
        signOutButton.setOnClickListener(v -> userViewModel.logout().observe(getViewLifecycleOwner(), result -> {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
        }));


        //TODO Spostare nel ViewModel
        MaterialSwitch darkModeSwitch = view.findViewById(R.id.darkModeSwitch);
        darkModeSwitch.post(() -> {
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            darkModeSwitch.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);
        });
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO));

        MaterialSwitch languageSwitch = view.findViewById(R.id.languageSwitch);

        Locale currentLocale = getResources().getConfiguration().getLocales().get(0);
        languageSwitch.setChecked(currentLocale.getLanguage().equals("it"));

        languageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Locale newLocale = isChecked ? new Locale("it") : new Locale("en");
            Configuration config = new Configuration();
            config.setLocale(newLocale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            recreate(requireActivity());
        });

    }
}