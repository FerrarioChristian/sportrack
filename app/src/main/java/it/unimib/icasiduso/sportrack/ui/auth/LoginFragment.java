package it.unimib.icasiduso.sportrack.ui.auth;

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

import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import it.unimib.icasiduso.sportrack.R;
import it.unimib.icasiduso.sportrack.data.repository.user.IUserRepository;
import it.unimib.icasiduso.sportrack.main.MainActivityWithBottomNav;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.user.UserViewModel;
import it.unimib.icasiduso.sportrack.viewmodel.user.UserViewModelFactory;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private UserViewModel userViewModel;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModelFactory(userRepository)).get(UserViewModel.class);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout emailTextInput = view.findViewById(R.id.login_name_textfield_layout);

        TextInputEditText emailEditText = (TextInputEditText) emailTextInput.getEditText();
        TextInputLayout passwordTextInput = view.findViewById(R.id.login_label_password_layout);
        TextInputEditText passwordEditText = (TextInputEditText) passwordTextInput.getEditText();


        final Button registerButton = view.findViewById(R.id.go_to_register_button);
        registerButton.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.registerFragment));

        final Button loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {

            String email = emailEditText.getText() != null ? emailEditText.getText().toString().trim() : "";
            String password = passwordEditText.getText() != null ? passwordEditText.getText().toString().trim() : "";

            if (isEmailOk(email) && isPasswordOk(password)) {
                userViewModel.getUserMutableLiveData(email, password, true).observe(
                        getViewLifecycleOwner(), result -> {
                            if (result.isSuccess()) {
                                Intent intent = new Intent(requireContext(), MainActivityWithBottomNav.class);
                                startActivity(intent);
                                requireActivity().finish();
                            } else {
                                Toast.makeText(getActivity(), R.string.authentication_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        final SignInButton googleButton = view.findViewById(R.id.login_with_google);
        googleButton.setOnClickListener(v -> googleLogin());
    }

    private void googleLogin() {
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean isPasswordOk(String password) {

        if (password == null || password.length() < 8) {
            Toast.makeText(getActivity(), R.string.invalid_password,
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