package com.eapple.noteapp.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.eapple.noteapp.R;
import com.eapple.noteapp.databinding.FragmentHomeBinding;
import com.eapple.noteapp.interfaces.OnItemClickListener;
import com.eapple.noteapp.model.TaskModel;
import com.eapple.noteapp.unit.App;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemClickListener {

    private FragmentHomeBinding binding;
    HomeAdapter adapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    boolean isList = true;
    private int position;
    List<TaskModel> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        edSearch();
        return root;
    }

    private void getDataFromDB() {
        App.getInstance().getTaskDao().getAll().observe(getViewLifecycleOwner(), list -> {
            this.list = list;
            if (list != null) {
                adapter.addListOfModel(list);
            }
        });
    }

    private void edSearch() {
        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filter(String text) {
        ArrayList<TaskModel> filteredList = new ArrayList<>();
        for (TaskModel item : list) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);
        initRecycler();

        getDataFromForm();

        getDataFromDB();
    }

    private void getDataFromForm() {
        getParentFragmentManager().setFragmentResultListener("rv_model", getViewLifecycleOwner(),
                (requestKey, result) -> {
                    TaskModel model = (TaskModel) result.getSerializable("model");
                    TaskModel updatedMod = (TaskModel) result.getSerializable("updatedMod");
                    if (model != null) {
                        if (isList) {
                            binding.rv.setLayoutManager(linearLayoutManager);
                        } else {
                            binding.rv.setLayoutManager(staggeredGridLayoutManager);
                        }
                        adapter.addModel(model);
                    } else {
                        if (isList) {
                            binding.rv.setLayoutManager(linearLayoutManager);
                        } else {
                            binding.rv.setLayoutManager(staggeredGridLayoutManager);
                        }
                        adapter.updateModel(position, updatedMod);
                    }
                });
    }

    private void initRecycler() {
        binding.rv.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        adapter = new HomeAdapter(isList, HomeFragment.this);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
//            if (isList) {
//                item.setIcon(R.drawable.ic_list);
//                binding.rv.setLayoutManager(staggeredGridLayoutManager);
//                binding.rv.setAdapter(adapter);
//                isList = false;
//            } else {
//                item.setIcon(R.drawable.ic_dashboard);
//                binding.rv.setLayoutManager(linearLayoutManager);
//                binding.rv.setAdapter(adapter);
//                isList = true;
//            }
            isList = !isList;
            if (!isList) {
                item.setIcon(R.drawable.ic_list);
            } else {
                item.setIcon(R.drawable.ic_dashboard);
            }
            binding.rv.setLayoutManager(isList ? new LinearLayoutManager(requireContext()) : new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            binding.rv.setAdapter(adapter);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isList", isList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position, TaskModel taskModel) {
        this.position = position;
        Bundle bundle = new Bundle();
        bundle.putSerializable("mod", taskModel);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.action_nav_home_to_formFragment, bundle);
    }

    @Override
    public void onLongClick(int position) {
        this.position = position;

    }
}