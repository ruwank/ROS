package com.relianceit.relianceorder.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.relianceit.relianceorder.R;
import com.relianceit.relianceorder.fragment.DatePickerDialogFragment;
import com.relianceit.relianceorder.util.Constants;

import java.util.Calendar;

public class ListOfOrderActivity extends ActionBarActivity implements  DatePickerDialog.OnDateSetListener {
    TextView fromDate,toDate;
    DialogFragment datePickerFragment;
    TableLayout orderListTable;
    TextView tblHeaderCol1,tblHeaderCol2,tblHeaderCol3;
    boolean fromDateSelect;
    private int fromYear;
    private int fromMonth;
    private int fromDay;
    private int toYear;
    private int toMonth;
    private int toDay;
    int itemIndex;
    Button btnGetOrder;
    Constants.Section section;

//
    // test comment sov
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_order);

        Intent intent = getIntent();
        section = (Constants.Section) intent.getSerializableExtra("section");


        itemIndex=0;

        orderListTable=(TableLayout)findViewById(R.id.order_list_table);
        fromDate=(TextView)findViewById(R.id.from_date);
        toDate=(TextView)findViewById(R.id.to_date);

        Calendar cal = Calendar.getInstance();
        fromYear=toYear = cal.get(Calendar.YEAR);
        fromMonth=toMonth = cal.get(Calendar.MONTH);
        fromDay=toDay = cal.get(Calendar.DAY_OF_MONTH);

        StringBuilder dateString=new StringBuilder().append(fromDay).
                append("-").append(fromMonth + 1)
                .append("-").append(fromYear)
                .append(" ");
        fromDate.setText(dateString);
        toDate.setText(dateString);

        datePickerFragment = new DatePickerDialogFragment(ListOfOrderActivity.this);

        fromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fromDateSelect=true;
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fromDateSelect=false;

                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
              //  ((DatePickerDialogFragment)datePickerFragment).updateDate(toYear, toMonth, toDay);

            }
        });

        btnGetOrder=(Button)findViewById(R.id.btnGetOrder);
        btnGetOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showOrderItem();

            }

        });

        tblHeaderCol1=(TextView)findViewById(R.id.tbl_header_col1);
        tblHeaderCol2=(TextView)findViewById(R.id.tbl_header_col2);
        tblHeaderCol3=(TextView)findViewById(R.id.tbl_header_col3);

        updateLabel();
        for (int i = 0; i <4 ; i++) {
            showOrderItem();
        }

    }
    private void customizeActionBar(){
        final ActionBar actionBar = getSupportActionBar();
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_custom_layout, null);
        TextView textViewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);

        String titleText=getString(R.string.app_name);
        if(section == Constants.Section.VIEW_SALE_RETURNS_LIST){
            titleText=titleText+" - "+getString(R.string.section_sales_return);
        }else{
            titleText=titleText+" - "+getString(R.string.section_order_list);

        }
        textViewTitle.setText(titleText);

        actionBar.setCustomView(viewActionBar,params);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


    }
    private  void updateLabel(){
        customizeActionBar();

        if(section == Constants.Section.VIEW_SALE_RETURNS_LIST){
            tblHeaderCol1.setText("Return No");
            tblHeaderCol2.setText("Return Date");
            tblHeaderCol3.setText("Return Value");

        }else{
            tblHeaderCol1.setText("Order No ");
            tblHeaderCol2.setText("Order Date");
            tblHeaderCol3.setText("Order Value");

        }
    }

    private void showOrderItem(){

        itemIndex++;
        TableRow.LayoutParams layoutParamsTableRow = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsTableRow.topMargin=5;
        layoutParamsTableRow.bottomMargin=5;

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(layoutParamsTableRow);
        tableRow.setBackgroundResource(R.drawable.border);

        TableRow.LayoutParams layoutParamsTextView = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
        layoutParamsTextView.gravity = Gravity.CENTER_VERTICAL;
        layoutParamsTextView.setMargins(1,5,1,5);

        TextView productTextView = new TextView(this);
        productTextView.setText("product 1");
        productTextView.setGravity(Gravity.CENTER);
        productTextView.setLayoutParams(layoutParamsTextView);
        productTextView.setTextColor(getResources().getColor(R.color.color_black));
        productTextView.setTextSize(getResources().getDimension(R.dimen.common_text_size));

        TextView batchTextView = new TextView(this);
        batchTextView.setText("000002");
        batchTextView.setGravity(Gravity.CENTER);
        batchTextView.setLayoutParams(layoutParamsTextView);
        batchTextView.setTextColor(getResources().getColor(R.color.color_black));
        batchTextView.setTextSize(getResources().getDimension(R.dimen.common_text_size));

        TextView qtyTextView = new TextView(this);
        qtyTextView.setText("500");
        qtyTextView.setGravity(Gravity.CENTER);
        qtyTextView.setLayoutParams(layoutParamsTextView);
        qtyTextView.setTextColor(getResources().getColor(R.color.color_black));
        qtyTextView.setTextSize(getResources().getDimension(R.dimen.common_text_size));

        tableRow.setId(itemIndex);
        tableRow.addView(productTextView,0);
        tableRow.addView(batchTextView,1);
        tableRow.addView(qtyTextView,2);

        tableRow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadOrderScreen(itemIndex);
            }
        });

        orderListTable.addView(tableRow, 3);

    }
    private  void loadOrderScreen(int index){
        Intent intent = new Intent(getApplicationContext(),
                ViewOrderActivity.class);
        if(section == Constants.Section.VIEW_SALE_RETURNS_LIST){
            intent.putExtra("section", Constants.Section.VIEW_SALE_RETURNS);

        }else{
            intent.putExtra("section", Constants.Section.VIEW_ORDER);
        }
        startActivity(intent);
    }
///
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onDateSet(DatePicker view, int selectedYear,
                          int selectedMonth, int selectedDay) {

        view.updateDate(selectedYear, selectedMonth, selectedDay);

        StringBuilder dateString=new StringBuilder().append(selectedDay).append("-").append(selectedMonth + 1)
                .append("-").append(selectedYear)
                .append(" ");
        if(fromDateSelect){
            fromYear=selectedYear;
            fromMonth=selectedMonth;
            fromDay=selectedDay;
            fromDate.setText(dateString);
        }else{
            toYear=selectedYear;
            toMonth=selectedMonth;
            toDay=selectedDay;
            toDate.setText(dateString);
        }



    }
}
