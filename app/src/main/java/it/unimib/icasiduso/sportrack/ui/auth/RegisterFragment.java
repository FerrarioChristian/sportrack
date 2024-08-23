package it.unimib.icasiduso.sportrack.ui.auth;

import static it.unimib.icasiduso.sportrack.utils.Constants.MIN_PASSWORD_LENGTH;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.main.MainActivityWithBottomNav;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.user.UserViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.user.UserViewModelFactory;

public class RegisterFragment extends Fragment {
    private static final String TAG = RegisterFragment.class.getSimpleName();

    private UserViewModel userViewModel;


    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout emailTextInput = view.findViewById(R.id.register_email_textfield_layout);
        TextInputEditText emailEditText = (TextInputEditText) emailTextInput.getEditText();
        TextInputLayout passwordTextInput = view.findViewById(R.id.register_label_password_layout);
        TextInputEditText passwordEditText = (TextInputEditText) passwordTextInput.getEditText();
        TextInputLayout confirmPasswordTextInput = view.findViewById(R.id.register_label_confirm_password_layout);
        TextInputEditText confirmPasswordEditText = (TextInputEditText) confirmPasswordTextInput.getEditText();

        Button loginButton = view.findViewById(R.id.go_to_login_button);
        loginButton.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.loginFragment);
        });

        Button registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (isEmailOk(email) && isPasswordOk(password, confirmPassword)) {
                userViewModel.getUserMutableLiveData(email, password, false).observe(
                        getViewLifecycleOwner(), result -> {
                            if (result.isSuccess()) {
                                Intent intent = new Intent(getActivity(), MainActivityWithBottomNav.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
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

}