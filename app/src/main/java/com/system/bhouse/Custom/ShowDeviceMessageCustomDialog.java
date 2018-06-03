package com.system.bhouse.Custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.utils.sharedpreferencesuser;


public class ShowDeviceMessageCustomDialog extends Dialog {

	public boolean isKeyDown() {
		return isKeyDown;
	}

	public void setIsKeyDown(boolean isKeyDown) {
		this.isKeyDown = isKeyDown;
	}

	private boolean isKeyDown;

	public ShowDeviceMessageCustomDialog(Context context) {
		super(context);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(isKeyDown){
			return false;
		}else {
			return super.onKeyDown(keyCode, event);
		}
	}


	public ShowDeviceMessageCustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private OnClickListener positiveButtonClickListener;
		private OnClickListener negativeButtonClickListener;

		private EditText editText_domian;
		private RadioGroup timeframe_group;

		private RadioButton timeframe_group_btn1;
		private RadioButton timeframe_group_btn2;

		private static final int EDTCHANGE=1;
		private static final int RadioGroupChange=2;

		private int StyleRes;
		private String middleButtonText;
		private OnClickListener middleButtonClickListener;
		private boolean isShowMiddle=false;

		public String getEdittextcontent() {
			return Edittextcontent;
		}

		public void setEdittextcontent(String edittextcontent) {
			Edittextcontent = edittextcontent;
		}

		private String Edittextcontent;


		public Builder(Context context) {
			this.context = context;
		}

		public Builder setStyle(Integer styleRes)
		{
			this.StyleRes=styleRes;
			return this;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * param title
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setMiddleButton(int middleButtonText,
										 OnClickListener listener) {
			this.middleButtonText = (String) context
					.getText(middleButtonText);
			this.middleButtonClickListener = listener;
			return this;
		}

		public Builder setMiddleButton(String middleButtonText,
										 OnClickListener listener) {
			this.middleButtonText = middleButtonText;
			this.middleButtonClickListener = listener;
			return this;
		}
		public Builder setMiddleShow(boolean isShowMiddle){
			this.isShowMiddle =isShowMiddle;
			return this;
		}

		Handler myhandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==EDTCHANGE){
					timeframe_group.setEnabled(false);
					timeframe_group.setFocusable(false);
					timeframe_group_btn1.setEnabled(false);
					timeframe_group_btn1.setFocusable(false);
					timeframe_group_btn1.setClickable(false);
					timeframe_group_btn2.setEnabled(false);
					timeframe_group_btn2.setFocusable(false);
					timeframe_group_btn2.setClickable(false);
				}else if(msg.what==RadioGroupChange){
					editText_domian.setEnabled(false);
					editText_domian.setFocusable(false);
				}
			}
		};

		private void setEdtlistener(){
			editText_domian.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					myhandler.sendEmptyMessage(EDTCHANGE);
				}
			});


			timeframe_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					myhandler.sendEmptyMessage(RadioGroupChange);
				}
			});
		}


		public ShowDeviceMessageCustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			StyleRes=StyleRes==0?R.style.Dialog:StyleRes;
			// instantiate the dialog with the custom Theme
			final ShowDeviceMessageCustomDialog dialog = new ShowDeviceMessageCustomDialog(context, StyleRes);

			View layout = inflater.inflate(R.layout.dialog_midshow_layout, null);

			 editText_domian = (EditText) layout.findViewById(R.id.edt_domian);
			 timeframe_group = (RadioGroup) layout.findViewById(R.id.timeframe_group);

			timeframe_group_btn1 = (RadioButton) layout.findViewById(R.id.view_daterange_history_rb);
			timeframe_group_btn2 = (RadioButton) layout.findViewById(R.id.view_completehistroy_rb);

			//把存好的域名取出展示
			editText_domian.setText(sharedpreferencesuser.getUserdomain(context));
			dialog.setCanceledOnTouchOutside(false);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			((TextView) layout.findViewById(R.id.title)).setText(title);
			// set the confirm button
			//set listerner
			setEdtlistener();
			//
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.cancel))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.cancel))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.cancel).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.download))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.download))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.download).setVisibility(
						View.GONE);
			}

			// set the middle button
			if (middleButtonText != null&&isShowMiddle) {
				((Button) layout.findViewById(R.id.middle))
						.setText(middleButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.middle))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									middleButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEUTRAL);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.middle).setVisibility(
						View.GONE);
			}

			// set the content message
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			}
			if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT));
				EditText viewEditText = (EditText)contentView.findViewById(R.id.edt_domian);
//				viewEditText.setText("");
				viewEditText.requestFocus();
				if (viewEditText!=null)
				{
					if (!TextUtils.isEmpty(Edittextcontent)) {
						//把存好的域名取出展示
						viewEditText.setText(Edittextcontent);
					}
				}
			}
			dialog.setContentView(layout);
			return dialog;
		}

	}
}
