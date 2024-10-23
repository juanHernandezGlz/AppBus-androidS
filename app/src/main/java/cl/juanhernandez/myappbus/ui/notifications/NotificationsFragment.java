package cl.juanhernandez.myappbus.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cl.juanhernandez.myappbus.Login;
import cl.juanhernandez.myappbus.R;
import cl.juanhernandez.myappbus.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root; */

        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button cerrarSesion = rootView.findViewById(R.id.button_logout);

        TextView textUserName = rootView.findViewById(R.id.text_username);
        TextView textEmail = rootView.findViewById(R.id.text_email);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null){
            String email = currentUser.getEmail();
            String username = email.substring(0, email.indexOf('@'));

            username = username.substring(0,1).toUpperCase() + username.substring(1);

            textUserName.setText(username);

            textEmail.setText(email);
        }

        //Configuración el listener del botón cerrar sesión
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}