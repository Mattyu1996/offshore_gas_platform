package it.univaq.offshoregasplatform;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentGasPlatform extends Fragment {

    private MainActivity current;
    private GasPlatformViewModel provider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gs_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        provider = ViewModelProviders.of(getActivity()).get(GasPlatformViewModel.class);
        provider.getNearPlatforms().observe(this, new Observer<ArrayList<GasPlatform>>() {

            public void onChanged(ArrayList<GasPlatform> gasPlatforms) {
                ADP adp = new ADP(gasPlatforms);
                recyclerView.setAdapter(adp);

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            current = (MainActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        current = null;
    }

    private class ADP extends RecyclerView.Adapter<ADP.ViewHolder> {

        private ArrayList<GasPlatform> data = new ArrayList<>();

        public ADP(ArrayList<GasPlatform> gasPlatforms) {
            this.data.addAll(gasPlatforms);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.title.setText(data.get(position).getDenominazione());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            TextView title;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.title);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(current != null) {

                            //Setto la piattaforma cliccata come piattaforma corrente nel Provider
                            provider.getCurrentPlatform().setValue(data.get(getAdapterPosition()));
                            current.setMyFragment(new FragmentDetail());

                        }
                    }
                });


            }

        }
    }
}
