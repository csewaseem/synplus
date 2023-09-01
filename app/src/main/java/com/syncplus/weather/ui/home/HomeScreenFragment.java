package com.syncplus.weather.ui.home;

import android.content.Context;
import android.os.Bundle;
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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.syncplus.weather.R;
import com.syncplus.weather.databinding.FragmentHomeBinding;
import com.syncplus.weather.databinding.ItemHomeBinding;
import com.syncplus.weather.viewModel.HomeScreenViewModel;
import com.syncplus.weather.viewModel.ViewModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Scope;

/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
public class HomeScreenFragment extends Fragment {

private FragmentHomeBinding binding;

//    @Inject
//    ViewModelFactory viewModelFactory;

//    private HomeScreenViewModel homeScreenViewModel;

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private EditText mEtCityName;
    private Button mBtnAdd;

    private String mCity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        homeScreenViewModel = new ViewModelProvider(this, viewModelFactory).get(HomeScreenViewModel.class);

        HomeScreenViewModel homeScreenViewModel =
                new ViewModelProvider(this).get(HomeScreenViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerViewCity;

        mBtnAdd = binding.addButton;
        mEtCityName = binding.editText;

        itemAdapter = new ItemAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(itemAdapter);
//        ArrayAdapter<String> adapter = new TransformAdapter();
//        recyclerView.setAdapter(adapter);

        mBtnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String cityName = binding.editText.getText().toString().trim();
                if (!cityName.isEmpty()) {
                    itemAdapter.addItem(cityName);
                    binding.editText.setText("");
                }
            }
        });

        itemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {

                // Handle the click event, e.g., display a toast or start an activity
                Toast.makeText(getContext(), "Selected item: " + item, Toast.LENGTH_SHORT).show();

            }
        });
//        transformViewModel.getTexts().observe(getViewLifecycleOwner(), adapter::submitList);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

/*
    private static class TransformAdapter extends ArrayAdapter<String> {


        public TransformAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }
*/

/*        @NonNull
        @Override
        public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemHomeBinding binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()));
            return new LocationViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
            holder.textView.setText(getItem(position));
            holder.imageView.setImageDrawable(
                    ResourcesCompat.getDrawable(holder.imageView.getResources(),
                            drawables.get(position),
                            null));
        }
    }*/

/*    private static class LocationViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public LocationViewHolder(ItemHomeBinding binding) {
            super(binding.getRoot());
            imageView = binding.imageViewItemTransform;
            textView = binding.textViewItemTransform;
        }
    }*/


    public static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
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

}