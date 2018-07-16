package com.system.bhouse.Custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import com.system.bhouse.bhouse.R;

/**
 * Created by Administrator on 2018-07-13.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.Custom
 */

public class BaseCustomDialog extends Dialog{

    private Context context;
    private String title;
    private String message;
    private String positiveButtonText;
    private String negativeButtonText;
    private View contentView;
    private OnClickListener positiveButtonClickListener;
    private OnClickListener negativeButtonClickListener;
    private int StyleRes;

    public BaseCustomDialog(Builder builder) {
        super(builder.context);
        this.context=builder.context;
        this.title=builder.title;
        this.message=builder.message;
        this.positiveButtonText=builder.positiveButtonText;
        this.negativeButtonText=builder.negativeButtonText;
        this.contentView=builder.contentView;
        this.positiveButtonClickListener=builder.positiveButtonClickListener;
        this.negativeButtonClickListener=builder.negativeButtonClickListener;
        this.StyleRes=builder.StyleRes;
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
        private int StyleRes;

        public Builder setContext(Context context)
        {
            this.context=context;
            return this;
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

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText,OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public BaseCustomDialog builder(){

            BaseCustomDialog baseCustomDialog = new BaseCustomDialog(this);

            Button positiveView= (Button)contentView.findViewById(R.id.positiveButton);
            Button negativeView= (Button)contentView.findViewById(R.id.negativeButton);

            baseCustomDialog.setTitle(title);

            if (positiveView==null && negativeView==null)
            {
                baseCustomDialog.setContentView(contentView);

                return baseCustomDialog;
            }

            if (positiveButtonText!=null &&positiveButtonClickListener!=null) {
                positiveView.setText(positiveButtonText);
            }else{
                positiveView.setVisibility(View.GONE);
            }

            if (negativeButtonText!=null && negativeButtonClickListener!=null) {
                negativeView.setText(negativeButtonText);
            }else{
                negativeView.setVisibility(View.GONE);
            }


            if (positiveButtonClickListener!=null)
            {
                positiveView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        positiveButtonClickListener.onClick(baseCustomDialog, DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }

            if (negativeButtonClickListener!=null)
            {
                negativeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        negativeButtonClickListener.onClick(baseCustomDialog,DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }

            baseCustomDialog.setContentView(contentView);

            return baseCustomDialog;
        }
    }
}
