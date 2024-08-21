package it.unimib.icasiduso.sportrack.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.main.MainActivity;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.user.UserViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.user.UserViewModelFactory;

public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    private Button signOutButton;
    private UserViewModel userViewModel;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //TODO Fix logout (tocca prima sistemare UserViewModel, Login e Register)
        signOutButton = view.findViewById(R.id.logoutButton);
        signOutButton.setOnClickListener(v -> {
            Log.d(TAG, "Logout pressed");
            userViewModel.logout().observe(getViewLifecycleOwner(), result -> {
                Log.d(TAG, "Logout observed");
                if (result.isSuccess()) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                    Log.d(TAG, "Logout successful");
                } else {
                    Log.d(TAG, "Logout failed");
                }
            });
        });

    }
}