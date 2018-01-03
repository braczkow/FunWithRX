package com.example.raczkowbar.funwithrx.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raczkowbar.funwithrx.App;
import com.example.raczkowbar.funwithrx.R;
import com.example.raczkowbar.funwithrx.base.CustomDisposableSubscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class DbActivity extends AppCompatActivity {
    @BindView(R.id.db_store)
    View m_maybeUpdate;

    @BindView(R.id.db_recycler)
    RecyclerView m_dbRecycler;
    private DbRecyclerAdapter m_adapter;

    SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd hh:mm:ss");
    private AppDao m_appDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ButterKnife.bind(this);
        m_appDao = App.DB.appDao();

        m_adapter = new DbRecyclerAdapter();
        m_dbRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        m_dbRecycler.setAdapter(m_adapter);


        m_appDao.getDbEntryAllRx()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CustomDisposableSubscriber<List<DbEntry>>() {
                    @Override
                    public void onNext(List<DbEntry> dbEntries) {
                        Timber.d("-> update thread");
                        m_adapter.setItems(dbEntries);
                    }
                });
    }

    @OnClick(R.id.db_store)
    public void onStoreDBClicked() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Timber.d("-> store thread.");

                DbEntry e = new DbEntry(sdf.format(new Date()));
                m_appDao.storeDbEntry(e);
            }
        });
    }

    public static class DbRecyclerAdapter extends RecyclerView.Adapter<DbRecyclerAdapter.DbViewHolder> {
        List<DbEntry> m_items = new ArrayList<>();

        public void setItems(List<DbEntry> items) {
            m_items = items;
            notifyDataSetChanged();
        }

        public static class DbViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.db_item_text)
            TextView dbItemText;

            public DbViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        @Override
        public DbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.db_item, parent, false);

            return new DbViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(DbViewHolder holder, int position) {
            DbEntry item = m_items.get(position);
            holder.dbItemText.setText(item.info);
        }

        @Override
        public int getItemCount() {
            return m_items.size();
        }
    }

}
