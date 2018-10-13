package com.hlub.dev.bookshop;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hlub.dev.bookshop.dao.BillDAO;
import com.hlub.dev.bookshop.dao.BillDetailDAO;
import com.hlub.dev.bookshop.database.DatabaseManager;
import com.hlub.dev.bookshop.model.Bill;
import com.hlub.dev.bookshop.model.BillDetail;

import java.util.ArrayList;
import java.util.List;

public class StatisticalActivity extends AppCompatActivity implements OnChartValueSelectedListener, AdapterView.OnItemSelectedListener {
    private Toolbar toolbarStatistical;
    private CombinedChart mChart;
    double tien = 0;
    private Spinner spinnerMonth;
    public DatabaseManager manager;
    private String monthItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        manager = new DatabaseManager(this);
        spinnerMonth = findViewById(R.id.spinnerMonth);

        //toolbar
        toolbarStatistical = findViewById(R.id.toolbarStatistical);
        setSupportActionBar(toolbarStatistical);
        toolbarStatistical.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //load spinner
        loadSpinner();

        //get Item Spinner when you selected
        spinnerMonth.setOnItemSelectedListener(this);


        mChart = (CombinedChart) findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        //Định nghĩa nhãn cho trục hoành X-Axis(trục nằm ngang) là các tháng
        final List<String> xLabel = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            xLabel.add("" + i);
        }


        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart());

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        //Tạo dữ liệu cho biểu đồ:
        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        monthItem = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, "" + monthItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void loadSpinner() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add(String.valueOf(i));
            }
        }
        // Creating adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
//        Toast.makeText(this, "Value: "
//                + e.getY()
//                + ", index: "
//                + h.getX()
//                + ", DataSet index: "
//                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    public DataSet dataChart() {

        BillDetailDAO billDetailDAO;
        BillDAO billDAO;
        LineData d = new LineData();
        int[] data = new int[31];
        ArrayList<Entry> entries = new ArrayList<Entry>();

        billDetailDAO = new BillDetailDAO(manager);
        billDAO = new BillDAO(manager);
            int tien = 0;
            for (int i = 0; i <= 30; i++) {
                String day = String.valueOf("" + i);
                if (i < 10) {
                    day = "0" + String.valueOf("" + i);
                }
                List<Bill> list = billDAO.getListBillByMonth(day, "10");

                for (Bill bill : list) {

                    for (BillDetail billDetail : billDetailDAO.getListBillDetailByBillId(bill.getBillId())) {

                        tien += (billDetail.getBook().getBookPrice() * billDetail.getQuantityBuy());
                    }
                }
                data[i] = tien;
                entries.add(new Entry(i, data[i]));
                tien = 0;
                Log.e("PRICE", String.valueOf(data[i]));
            }



        LineDataSet set = new LineDataSet(entries, "Request Ots approved");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }


}
