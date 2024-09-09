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
import it.unimib.icasiduso.sportrack.databinding.FragmentLoginBinding;
import it.unimib.icasiduso.sportrack.main.MainActivityWithBottomNav;
import it.unimib.icasiduso.sportrack.utils.ServiceLocator;
import it.unimib.icasiduso.sportrack.viewmodel.UserViewModel;

public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private UserViewModel userViewModel;
    private FragmentLoginBinding binding;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository();
        userViewModel = new ViewModelProvider(requireActivity(),
                new UserViewModel.Factory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListeners();
        observeViewModel();

    }

    private void setListeners() {
        binding.goToRegisterButton.setOnClickListener(v -> Navigation.findNavController(requireView())
                .navigate(R.id.registerFragment));

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.loginEmailEditText.getText() != null ? binding.loginEmailEditText.getText()
                    .toString()
                    .trim() : "";
            String password = binding.loginPasswordEditText.getText() != null ? binding.loginPasswordEditText.getText()
                    .toString()
                    .trim() : "";

            if (isEmailOk(email) && isPasswordOk(password)) {
                userViewModel.getUserData(email, password, true);
            }
        });

    }

    private void observeViewModel() {
        userViewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;
            if (result.isSuccess()) {
                Intent intent = new Intent(requireContext(), MainActivityWithBottomNav.class);
                startActivity(intent);
                requireActivity().finish();
            } else if (requireActivity().hasWindowFocus()) {
                Toast.makeText(getActivity(), R.string.authentication_failed, Toast.LENGTH_SHORT)
                        .show();
                userViewModel.clearUserMutableLiveData();
            }
        });
    }

    private boolean isPasswordOk(String password) {

        if (password == null || password.length() < 8) {
            Toast.makeText(getActivity(),
                    getString(R.string.invalid_password, MIN_PASSWORD_LENGTH),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isEmailOk(String email) {
        if (!EmailValidator.getInstance().isValid((email))) {
            Toast.makeText(getActivity(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
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