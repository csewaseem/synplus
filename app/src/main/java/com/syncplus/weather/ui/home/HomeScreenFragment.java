package com.syncplus.weather.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import java.util.Map;

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
    private String keyPrefix = "location_";
    private SharedPreferences sharedPreferences;
    @Inject
    ViewModelFactory viewModelFactory;
    private HomeScreenViewModel homeScreenViewModel;
    private NavController navController;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private Button mBtnAdd;

    private static SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) this.requireActivity().getApplicationContext()).getApplicationComponent().inject(this);
        navController = NavHostFragment.findNavController(this);
        homeScreenViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeScreenViewModel.class);

        sharedPreferences = this.requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        homeScreenViewModel.getWeather().observe(this.requireActivity(), new Observer<CityWeather>() {
            @Override
            public void onChanged(CityWeather cityWeather) {
                System.out.println("cityWeather "+cityWeather.name);

                NavDirections action = HomeScreenFragmentDirections.actionNavHomeScreenToNavCityScreen(cityWeather);
                navController.navigate(action);
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerViewCity;
        mBtnAdd = binding.addButton;
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        itemAdapter = new ItemAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(itemAdapter);

        if(!getLocation().isEmpty()){
            for(String  location : getLocation()){
                itemAdapter.addItem(location);
            }
        }

        mBtnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String cityName = binding.editText.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    saveLocation(cityName);
                    itemAdapter.addItem(cityName);
                    binding.editText.setText("");
                }
            }
        });

        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                homeScreenViewModel.fetchCityWeather(item);
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
            return new ItemViewHolder(itemView).linkAdapter(this);
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

            private ItemAdapter itemAdapter;
            TextView itemTextView;
            Button btnDelete;


            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                itemTextView = itemView.findViewById(R.id.text_view_item_location);
                btnDelete = itemView.findViewById(R.id.delete);
                btnDelete.setOnClickListener(v -> {
                    itemAdapter.itemList.remove(getAbsoluteAdapterPosition());
                    itemAdapter.notifyItemRemoved(getAbsoluteAdapterPosition());

                    remove(itemTextView.getText().toString().toUpperCase());
                    System.out.println(itemTextView.getText().toString().toUpperCase());
                });
            }
            public ItemViewHolder linkAdapter(ItemAdapter itemAdapter){
                this.itemAdapter = itemAdapter;
                return this;
            }

            public void bind(String item) {
                itemTextView.setText(item);
            }
        }
    }

    private void saveLocation(String location){
//        int count = sharedPreferences.getInt("string_count", 0); // Get the current count
//        String key = keyPrefix + (count + 1); // Create a unique key
//        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(location.toUpperCase(), location);
//        editor.putInt("string_count", count + 1); // Update the count
        editor.apply(); // Save changes
    }

    private ArrayList<String> getLocation(){

        Map<String, ?> allKeyValues = sharedPreferences.getAll();
        ArrayList<String> retrievedList = new ArrayList<>();

        for (Map.Entry<String, ?> entry : allKeyValues.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                String stringValue = (String) value;
                retrievedList.add(stringValue);

                // Handle string value
                Log.d("SharedPreferences", key + " : " + stringValue);
            } /*else if (value instanceof Integer) {
                int intValue = (Integer) value;
                // Handle integer value
                Log.d("SharedPreferences", key + " : " + intValue);
            } else if (value instanceof Boolean) {
                boolean booleanValue = (Boolean) value;
                // Handle boolean value
                Log.d("SharedPreferences", key + " : " + booleanValue);
            }*/
            // Add more else if clauses for other data types if needed
        }
        /*ArrayList<String> retrievedList = new ArrayList<>();
        int totalCount = sharedPreferences.getInt("string_count", 0);

        for (int i=1; i<= totalCount; i++){
            String key = keyPrefix + i;
            retrievedList.add(sharedPreferences.getString(key, ""));
        }*/

        return retrievedList;
    }

    private static void remove(String item){
//        boolean itemExists = sharedPreferences.contains(item);
//        if(itemExists) {
            editor.remove(item);
            editor.apply();
//        }
    }
}