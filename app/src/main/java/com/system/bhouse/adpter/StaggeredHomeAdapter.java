package com.system.bhouse.adpter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.TenUtils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class StaggeredHomeAdapter extends RecyclerView.Adapter<StaggeredHomeAdapter.MyViewHolder>
{


	private List<String> mDatas;
	private LayoutInflater mInflater;

	private List<Integer> mHeights;
	private Context context;
	private String[] stringArray;

	private List<Integer> mWith;
	private List<Integer> mtop;
	private List<Integer> mWithright;
	private List<Integer> mtv_color;
	private int[] mtv_colorsuzu={R.color.tv_zijinfenxi,R.color.tv_yusuanfenxi,R.color.tv_chengbenfenxi,R.color.tv_chaigoufenxi,R.color.tv_kucunfenxi,R.color.tv_xiaoshoufenxi,R.color.tv_zicanfenxi,R.color.tv_kaohefenxi};

	private int[] mtv_colorsuzutwo={R.drawable.item_bg,R.drawable.item_bg1,R.drawable.item_bg2,R.drawable.item_bg3,R.drawable.item_bg4,R.drawable.item_bg5,R.drawable.item_bg6,R.drawable.item_bg7};

	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);

		void onItemLongClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}

	public StaggeredHomeAdapter(Context context, List<String> datas)
	{
		this.context=context;
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
		mHeights = new ArrayList<Integer>();
		mWith = new ArrayList<Integer>();
		mtop= new ArrayList<Integer>();
		mWithright= new ArrayList<Integer>();
		mtv_color=new ArrayList<>();

		Resources resources = context.getResources();
		 stringArray = resources.getStringArray(R.array.colortext);

		for (int i = 0; i < mDatas.size(); i+=2)
		{
//			mHeights.add( (int) (80 + Math.random() * 20));
//			mWith.add( (int) (50 + Math.random() * 80));
//			mtop.add( (int) (5 + Math.random() * 3));
//			mWithright.add( (int) (20 + Math.random() * 120));
//			mHeights.add(88);
//			mWith.add(68);
//			mWithright.add(110);
//			mtop.add(14);
//			if(i%2==0){
//				mHeights.add(88);
//				mWith.add(110);
//				mWithright.add(68);
//				mtop.add(3);
//			}

			int dimension = (int) (context.getResources().getDimension(R.dimen.a15x));
			int dimensionmWith = (int) (context.getResources().getDimension(R.dimen.a15x));
			int dimension1right = (int) (context.getResources().getDimension(R.dimen.a15x));
			int dimensiontop1 = (int) (context.getResources().getDimension(R.dimen.a15x));

			mHeights.add((int)(context.getResources().getDimension(R.dimen.a15x)));
			mWith.add((int)(context.getResources().getDimension(R.dimen.a15x)));
			mWithright.add((int)(context.getResources().getDimension(R.dimen.a15x)));
			mtop.add((int)(context.getResources().getDimension(R.dimen.a15x)));
			if(i%2==0){
				mHeights.add((int)(context.getResources().getDimension(R.dimen.a15x)));
				mWith.add((int)(context.getResources().getDimension(R.dimen.a15x)));
				mWithright.add((int)(context.getResources().getDimension(R.dimen.a15x)));
				mtop.add((int)(context.getResources().getDimension(R.dimen.a15x)));
			}
//			mtv_color.add(mtv_colorsuzu[i]);
		}


	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		MyViewHolder holder = new MyViewHolder(mInflater.inflate(
				R.layout.item_staggered_home, parent, false));
		return holder;
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position)
	{
		ViewGroup.LayoutParams lp = holder.tv.getLayoutParams();
		lp.height = mHeights.get(position);
		ViewGroup.LayoutParams layoutParams1 = holder.fra.getLayoutParams();
		FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(layoutParams1);
		layoutParams.setMargins(mWith.get(position),mtop.get(position),mWithright.get(position),mtop.get(position));
//		layoutParams.setMargins(R.dimen.x88,1,1,1);
		int screenWidth = ScreenUtils.getScreenWidth(context);
		lp.width=mWith.get(position);
//		layoutParams.height=mHeights.get(position);

		layoutParams.width=(screenWidth);
//		ColorStateList colorStateList=new ColorStateList()
		layoutParams.gravity= Gravity.CENTER_HORIZONTAL;
		holder.tv.setLayoutParams(lp);
		holder.fra.setLayoutParams(layoutParams);

		holder.tv.setText(mDatas.get(position));
		holder.fra.setBackgroundResource(mtv_colorsuzutwo[position]);
		holder.maintv.setText(stringArray[position]);
//		holder.fra.setBackgroundTintList(createColorStateList(0xffff0000));
		// 如果设置了回调，则设置点击事件
		if (mOnItemClickLitener != null)
		{
			holder.itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemClick(holder.itemView, pos);
				}
			});

			holder.itemView.setOnLongClickListener(new OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
					removeData(pos);
					return false;
				}
			});
		}
	}

	@Override
	public int getItemCount()
	{
		return mDatas.size();
	}

	public void addData(int position)
	{
		mDatas.add(position, "Insert One");
		mHeights.add( (int) (100 + Math.random() * 300));
		notifyItemInserted(position);
	}

	public void removeData(int position)
	{
		mDatas.remove(position);
		notifyItemRemoved(position);
	}

	class MyViewHolder extends RecyclerView.ViewHolder
	{

		TextView tv;
		FrameLayout fra;
		TextView maintv;

		public MyViewHolder(View view)
		{
			super(view);
			tv = (TextView) view.findViewById(R.id.id_num);
			fra=(FrameLayout)view.findViewById(R.id.fram_item);
			maintv = (TextView) view.findViewById(R.id.id_num_main);
		}
	}
}