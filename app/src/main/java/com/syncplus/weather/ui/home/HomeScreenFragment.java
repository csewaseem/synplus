package com.syncplus.weather.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.syncplus.weather.MyApplication;
import com.syncplus.weather.R;
import com.syncplus.weather.databinding.FragmentHomeBinding;
import com.syncplus.weather.databinding.ItemHomeBinding;
import com.syncplus.weather.model.CityWeather;
import com.syncplus.weather.viewModel.HomeScreenViewModel;
import com.syncplus.weather.viewModel.ViewModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
public class HomeScreenFragment extends Fragment {

private FragmentHomeBinding binding;
    // Storing a string
    private String key = "location";
    private ArrayList<String> locationList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    @Inject
    ViewModelFactory viewModelFactory;
    private HomeScreenViewModel homeScreenViewModel;
    private NavController navController;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private EditText mEtCityName;
    private Button mBtnAdd;
    private String mCity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) this.requireActivity().getApplicationContext()).getApplicationComponent().inject(this);
        navController = NavHostFragment.findNavController(this);
        homeScreenViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeScreenViewModel.class);

        sharedPreferences = this.requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        homeScreenViewModel.getWeather().observe(this.requireActivity(), new Observer<CityWeather>() {
            @Override
            public void onChanged(CityWeather cityWeather) {
                System.out.println("cityWeather "+cityWeather.name);

                NavDirections action = HomeScreenFragmentDirections.actionNavHomeScreenToNavCityScreen(cityWeather);
                navController.navigate(action);

                //Toast.makeText(getContext(), "Selected item: " + cityWeather.name, Toast.LENGTH_SHORT).show();
                // Handle the updated CityWeather object here
                // This block will be called whenever the LiveData value changes
                // You can update your UI or perform any other action based on the new data
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        homeScreenViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeScreenViewModel.class);
        recyclerView = binding.recyclerViewCity;
        mBtnAdd = binding.addButton;
        mEtCityName = binding.editText;

        itemAdapter = new ItemAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(itemAdapter);

//        AppBarConfiguration mAppBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        mBtnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String cityName = binding.editText.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    itemAdapter.addItem(cityName);
                    locationList.add(cityName);

                    binding.editText.setText("");
                }
            }
        });

        // Observe LiveData

        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                homeScreenViewModel.fetchCityWeather(item);
                // Handle the click event, e.g., display a toast or start an activity
//                Toast.makeText(getContext(), "Selected item: " + item, Toast.LENGTH_SHORT).show();

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
        private List<String> itemList = new ArrayList<>();

        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }

        public interface OnItemClickListener {
            void onItemClick(String item);
        }

        public void addItem(String item) {
            itemList.add(item);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            ItemHomeBinding binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()));
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            String item = itemList.get(position);
            holder.bind(item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(item);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView itemTextView;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                itemTextView = itemView.findViewById(R.id.text_view_item_transform);
            }

            public void bind(String item) {
                itemTextView.setText(item);
            }
        }
    }

    private void saveLocation(ArrayList location){
        // Convert the ArrayList to a single String with delimiter
        String delimitedString = TextUtils.join(",", location);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, delimitedString);
        editor.apply();
    }

    private ArrayList<String> getLocation(){
        // Get the delimited String from SharedPreferences
        String delimitedString = sharedPreferences.getString("stringArrayList", "");
        // Convert the delimited String back to a String ArrayList
        ArrayList<String> retrievedList = new ArrayList<>(Arrays.asList(delimitedString.split(",")));
        return retrievedList;
    }

}