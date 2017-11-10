package com.example.raczkowbar.funwithrx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.raczkowbar.funwithrx.examples.ExamplesManager;
import com.example.raczkowbar.funwithrx.examples.SchedulersPublisher2Example;
import com.example.raczkowbar.funwithrx.examples.SchedulersPublisherExample;
import com.example.raczkowbar.funwithrx.examples.SchedulersWithMapOp2Example;
import com.example.raczkowbar.funwithrx.examples.SchedulersWithMapOpExample;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchedulersExamplesActivity extends AppCompatActivity {
    @BindView(R.id.examples_recycler)
    RecyclerView m_examplesRecycler;
    private ExamplesManager m_examplesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulers);
        ButterKnife.bind(this);

        m_examplesManager = new ExamplesManager(m_examplesRecycler);
        m_examplesManager.addExample(new SchedulersWithMapOpExample());
        m_examplesManager.addExample(new SchedulersWithMapOp2Example());
        m_examplesManager.addExample(new SchedulersPublisherExample());
        m_examplesManager.addExample(new SchedulersPublisher2Example());
    }

}
