package com.system.bhouse.Custom;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.system.bhouse.bhouse.CommonTask.TechnologyExecution.entity.Orderbean;
import com.system.bhouse.bhouse.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-07-13.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Custom
 */

public class SpinnerDialog<T> {
    ArrayList<T> items;
    Activity context;
    String dTitle,closeTitle="";
    OnSpinerItemClick onSpinerItemClick;
    BaseCustomDialog alertDialog;
    int pos;
    int style;


    public SpinnerDialog(Activity activity, ArrayList<T> items, String dialogTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
    }

    public SpinnerDialog(Activity activity, ArrayList<T> items, String dialogTitle,String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.closeTitle=closeTitle;
    }

    public SpinnerDialog(Activity activity, ArrayList<T> items, String dialogTitle, int style) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
    }

    public SpinnerDialog(Activity activity, ArrayList<T> items, String dialogTitle, int style,String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle=closeTitle;
    }

    public void bindOnSpinerListener(OnSpinerItemClick onSpinerItemClick1) {
        this.onSpinerItemClick = onSpinerItemClick1;
    }

    public void showSpinerDialog() {
        View v = context.getLayoutInflater().inflate(R.layout.spinner_dialog_layout, null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView title = (TextView) v.findViewById(R.id.spinerTitle);
        rippleViewClose.setText(context.getResources().getString(R.string.text_close));
        title.setText(dTitle);
        final ListView listView = (ListView) v.findViewById(R.id.list);
        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);

        alertDialog= new BaseCustomDialog.Builder().setContext(this.context).setContentView(v).setTitle(context.getResources().getString(R.string.lookup_order_id)).builder();

        ArrayList<String> oriderNumbers =new ArrayList<>();
        for (T bean:items)
        {
            oriderNumbers.add(((Orderbean)bean).oriderNumber);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinnerdialog_items_view, oriderNumbers);
        listView.setAdapter(adapter);
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.selector_layout_common_radius_v8);
        alertDialog.setCancelable(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = (TextView) view.findViewById(R.id.text1);
                T orderbean=null;
                for (int j = 0; j < items.size(); j++) {
                    if (t.getText().toString().equalsIgnoreCase(((Orderbean)items.get(j)).oriderNumber)) {
                        pos = j;
                        orderbean=((T)items.get(j));
                    }
                }
                onSpinerItemClick.onClick(orderbean, pos);
                alertDialog.dismiss();
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(searchBox.getText().toString());
            }
        });

        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
