package it.unimib.icasiduso.sportrack.ui.auth;

import static it.unimib.icasiduso.sportrack.utils.Constants.MIN_PASSWORD_LENGTH;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.apache.commons.validator.routines.EmailValidator;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.databinding.FragmentRegisterBinding;
import it.unimib.icasiduso.sportrack.main.MainActivityWithBottomNav;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.UserViewModel;

public class RegisterFragment extends Fragment {
    private static final String TAG = RegisterFragment.class.getSimpleName();

    private UserViewModel userViewModel;
    private FragmentRegisterBinding binding;


    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModel.Factory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
        observeViewModel();

    }

    private void setListeners() {
        binding.goToLoginButton.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.loginFragment));

        binding.registerButton.setOnClickListener(v -> {
            String email = binding.registerEmailEditText.getText() != null ? binding.registerEmailEditText.getText().toString().trim() : "";
            String password = binding.registerPasswordEditText.getText() != null ? binding.registerPasswordEditText.getText().toString().trim() : "";
            String confirmPassword = binding.registerConfirmPasswordEditText.getText() != null ? binding.registerConfirmPasswordEditText.getText().toString().trim() : "";

            if (isEmailOk(email) && isPasswordOk(password, confirmPassword)) {
                userViewModel.getUserData(email, password, false);
            }

        });
    }

    private void observeViewModel() {
        userViewModel.getUserMutableLiveData().observe(
                getViewLifecycleOwner(), result -> {
                    if (result.isSuccess()) {
                        Intent intent = new Intent(getActivity(), MainActivityWithBottomNav.class);
                        startActivity(intent);
                        requireActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), R.string.authentication_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private boolean isPasswordOk(String password, String confirmPassword) {

        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            String pass_error = getString(R.string.invalid_password, MIN_PASSWORD_LENGTH);
            Toast.makeText(getActivity(), pass_error,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), R.string.invalid_confirm_password,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isEmailOk(String email) {
        if (!EmailValidator.getInstance().isValid((email))) {
            Toast.makeText(getActivity(), R.string.invalid_email,
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}