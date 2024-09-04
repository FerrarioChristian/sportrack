package it.unimib.icasiduso.sportrack.ui;

import static androidx.core.app.ActivityCompat.recreate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.schedule.IScheduleRepository;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentSettingsBinding;
import it.unimib.icasiduso.sportrack.main.MainActivity;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.ScheduleViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.UserViewModel;

public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private ScheduleViewModel scheduleViewModel;
    private UserViewModel userViewModel;
    private FragmentSettingsBinding binding;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModel.Factory(userRepository)).get(UserViewModel.class);

        IScheduleRepository scheduleRepository = ServiceLocator.getInstance().getScheduleRepository();
        scheduleViewModel = new ViewModelProvider(requireActivity(), new ScheduleViewModel.Factory(scheduleRepository)).get(ScheduleViewModel.class);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.darkModeSwitch.post(() -> {
            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            binding.darkModeSwitch.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);
        });

        Locale currentLocale = getResources().getConfiguration().getLocales().get(0);
        binding.languageSwitch.setChecked(currentLocale.getLanguage().equals("it"));

        setListeners();
        observeViewModel();
    }

    private void setListeners() {
        binding.logoutButton.setOnClickListener(v -> userViewModel.logout().observe(getViewLifecycleOwner(), result -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }));

        binding.darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO));

        binding.languageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Locale newLocale = isChecked ? new Locale("it") : new Locale("en");
            Configuration config = new Configuration();
            config.setLocale(newLocale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            recreate(requireActivity());
        });

        binding.resetButton.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                new AlertDialog.Builder(requireContext())
                        .setTitle(R.string.confirm_reset)
                        .setMessage(R.string.confirm_reset_message)
                        .setPositiveButton(R.string.confirm, (dialog, which) -> {
                            scheduleViewModel.deleteUserSchedules(user.getUid());
                            Toast.makeText(requireContext(), R.string.data_reset_successfully , Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(R.string.cancel, (dialog, which) -> {
                        })
                        .show();
            }
        });

    }

    private void observeViewModel() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}