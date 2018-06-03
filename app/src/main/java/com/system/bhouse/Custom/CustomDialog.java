package com.system.bhouse.Custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
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


public class CustomDialog extends Dialog {

	public boolean isKeyDown() {
		return isKeyDown;
	}

	public void setIsKeyDown(boolean isKeyDown) {
		this.isKeyDown = isKeyDown;
	}

	private boolean isKeyDown;

	public CustomDialog(Context context) {
		super(context);
	}

	public EditText getEditText_domian() {
		return editText_domian;
	}

	public void setEditText_domian(EditText editText_domian) {
		this.editText_domian = editText_domian;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(isKeyDown){
			return false;
		}else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private  EditText editText_domian;

	public RadioButton getTimeframe_group_btn1() {
		return timeframe_group_btn1;
	}

	public void setTimeframe_group_btn1(RadioButton timeframe_group_btn1) {
		this.timeframe_group_btn1 = timeframe_group_btn1;
	}

	public RadioButton getTimeframe_group_btn2() {
		return timeframe_group_btn2;
	}

	public void setTimeframe_group_btn2(RadioButton timeframe_group_btn2) {
		this.timeframe_group_btn2 = timeframe_group_btn2;
	}

	private RadioButton timeframe_group_btn1;
	private RadioButton timeframe_group_btn2;

	public RadioGroup getTimeframe_group() {
		return timeframe_group;
	}

	public void setTimeframe_group(RadioGroup timeframe_group) {
		this.timeframe_group = timeframe_group;
	}

	private RadioGroup timeframe_group;

	public CustomDialog(Context context, int theme) {
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


		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
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


		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_normal_layout, null);

			 editText_domian = (EditText) layout.findViewById(R.id.edt_domian);
			dialog.setEditText_domian(editText_domian);
			 timeframe_group = (RadioGroup) layout.findViewById(R.id.timeframe_group);
			dialog.setTimeframe_group(timeframe_group);

			timeframe_group_btn1 = (RadioButton) layout.findViewById(R.id.view_daterange_history_rb);
			timeframe_group_btn2 = (RadioButton) layout.findViewById(R.id.view_completehistroy_rb);

			dialog.setTimeframe_group_btn1(timeframe_group_btn1);
			dialog.setTimeframe_group_btn2(timeframe_group_btn2);

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
				((Button) layout.findViewById(R.id.positiveButton))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) layout.findViewById(R.id.positiveButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
						View.GONE);
			}
			// set the content message
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.content))
						.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(
						contentView, new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
			}
			dialog.setContentView(layout);
			return dialog;
		}

	}
}
