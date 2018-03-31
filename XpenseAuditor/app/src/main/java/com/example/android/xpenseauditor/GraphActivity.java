package com.example.android.xpenseauditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {


    float amount[]={30f,80f,50f,60f,40f};
    float xax[]={30f,40f,50f,60f,70f};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        setUpBarChart();
    }

    private void setUpBarChart() {

        BarChart chart=(BarChart) findViewById(R.id.chart);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setFitBars(true);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        List<BarEntry>barEntries = new ArrayList<>();


        for (int i=0;i<amount.length;i++)
        {
           barEntries.add(new BarEntry(i,amount[i]));
        }

        BarDataSet barDataSet=new BarDataSet(barEntries,"AMOUNT SPEND- monthly");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setDrawValues(true);

        ArrayList <String> months=new ArrayList<>();

        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);


        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));
        chart.setData(barData);
        chart.invalidate();
        chart.animateY(1000);
    }
}
