package com.system.bhouse.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.SaleAnalysisActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Homepagefragment extends Fragment {
    @Bind(R.id.gridview)
    GridView gridview;
    @Bind(R.id.text_title)
    TextView textTitle;

    private int[] ints = {R.drawable.caigou, R.drawable.chengben, R.drawable.xiaoshou, R.drawable.yusuan, R.drawable.zichan, R.drawable.zijin, R.drawable.kaohe, R.drawable.kucun};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.homepagefragment, null);
        ButterKnife.bind(this, inflate);


        gridview.setAdapter(new Myadapter(Homepagefragment.this.getActivity()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Homepagefragment.this.getActivity(), position + "", 0).show();
                Intent intent;
                switch (position){
                    case 0:
//                        intent=new Intent(Homepagefragment.this.getActivity(),StaggeredGridLayoutFragment.class);
//                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:

                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        intent=new Intent(Homepagefragment.this.getActivity(),SaleAnalysisActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        Toast.makeText(Homepagefragment.this.getActivity(),"errro",0).show();
                        break;
                }

            }
        });
        textTitle.setText(R.string.first_page);
        return inflate;
    }



    class Myadapter extends BaseAdapter {
        private ImageView imgviewitem;
        private TextView tviditem;

        @Override
        public int getCount() {
            return ints.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.gridviewitem, null);
                holder = new ViewHolder();
                holder.imgViewitem = (ImageView) convertView.findViewById(R.id.img_viewitem);
                holder.tvIditem = (TextView) convertView.findViewById(R.id.tv_iditem);
                holder.imgViewitem.setAdjustViewBounds(false);
                holder.imgViewitem.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.imgViewitem.setPadding(8, 8, 8, 8);
                convertView.setTag(holder);
            }
            else {

                holder = (ViewHolder) convertView.getTag();
            }
            holder.imgViewitem.setImageResource(ints[position]);
            holder.tvIditem.setText(Sin[position]);
            return convertView;
        }

        private Context context;

        Myadapter(Context context) {
            this.context = context;
        }

        class ViewHolder {
            ImageView imgViewitem;
            TextView tvIditem;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private int[] Sin = {R.string.zijing, R.string.yusuan, R.string.chengben,
            R.string.caigou, R.string.kucun, R.string.xiaoshou, R.string.zican, R.string.kaohe};

}
