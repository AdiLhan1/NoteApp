package com.eapple.noteapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.eapple.noteapp.R;
import com.eapple.noteapp.databinding.FragmentFormBinding;
import com.eapple.noteapp.model.TaskModel;
import com.eapple.noteapp.unit.App;
import com.eapple.noteapp.unit.PreferencesHelper;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormFragment extends Fragment {
    private FragmentFormBinding binding;
    TextView title, date;
    Button save;
    ImageView back;
    TaskModel model;
    TaskModel mod;
    String tit, times;
    NavController navController;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        initView();
        getModel();
        initClickListener();
        getTime();
        return binding.getRoot();
    }

    private void getModel() {
        if (getArguments() != null) {
            mod = (TaskModel) getArguments().getSerializable("mod");
            if (mod != null) {
                title.setText(mod.getTitle());
                date.setText(mod.getDate());
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    void getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM HH:mm");
        Date currentTime = Calendar.getInstance().getTime();
        times = dateFormat.format(currentTime);
        date.setText(times);
        Log.e("TAG", "getTime: " + currentTime);
    }

    private void initClickListener() {
        save.setOnClickListener(v -> {
            tit = title.getText().toString();
            model = new TaskModel(tit, times, save.getBackground().toString());
            App.getInstance().getTaskDao().insert(model);

            Bundle bundle = new Bundle();
            if (mod == null) {
                bundle.putSerializable("model", model);
            } else {
                mod.setTitle(tit);
                bundle.putSerializable("updatedMod", mod);
            }

            getParentFragmentManager().setFragmentResult("rv_model", bundle);
            close();
        });
        back.setOnClickListener(v -> {
            close();
        });
    }

    private void close() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreferencesHelper.addProperty("title", tit);
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ActionBar supportActionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.show();
    }

    private void initView() {
        title = binding.titleEt;
        save = binding.btnSave;
        back = binding.icBack;
        date = binding.dateTime;
    }
}