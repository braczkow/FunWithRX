package com.example.raczkowbar.funwithrx.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raczkowbar.funwithrx.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamplesManager {

    private final RecyclerView m_recycler;
    private final ExamplesAdapter m_adapter;

    public ExamplesManager(RecyclerView recyclerView) {
        m_adapter = new ExamplesAdapter();
        m_recycler = recyclerView;
        m_recycler.setAdapter(m_adapter);
        m_recycler.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void addExample(ExecutableExample example) {
        m_adapter.addItem(example);
    }

    public static class ExamplesAdapter extends RecyclerView.Adapter<ExamplesAdapter.ExampleViewHolder> {

        private final List<ExecutableExample> m_items;

        public void addItem(ExecutableExample example) {
            m_items.add(example);
            notifyDataSetChanged();
        }

        public ExamplesAdapter() {
            m_items = new ArrayList<>();
        }

        @Override
        public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.example_layout, parent, false);
            return new ExampleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ExampleViewHolder holder, final int position) {
            ExecutableExample item = m_items.get(position);
            holder.m_exampleName.setText(item.getName());
            holder.m_exampleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m_items.get(position).execute();
                }
            });
        }

        @Override
        public int getItemCount() {
            return m_items.size();
        }

        public static class ExampleViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.example_name_text)
            TextView m_exampleName;

            View m_exampleLayout;

            public ExampleViewHolder(View itemView) {
                super(itemView);
                m_exampleLayout = itemView;
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
