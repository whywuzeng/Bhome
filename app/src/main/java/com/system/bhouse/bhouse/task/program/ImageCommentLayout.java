package com.system.bhouse.bhouse.task.program;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.bhouse.bhouse.R;
import com.system.bhouse.bhouse.setup.utils.FileUtil;

import java.util.ArrayList;

import io.github.lijunguan.imgselector.model.entity.ImageInfo;

import static com.system.bhouse.base.App.SetPicassopic;

/**
 * Created by Administrator on 2017-11-17.
 * 一个带 图片 带 评论 带 表情的 输入框
 * 这个  该怎么写
 *
 */

public class ImageCommentLayout {
    public static final int RESULT_REQUEST_COMMENT_IMAGE = 1100;
    public static final int RESULT_REQUEST_COMMENT_IMAGE_DETAIL = 1101;

    private EnterLayout mEnterLayout;
    private View mRootLayout;

    private ViewGroup mFlowLayout;
    private ArrayList<ImageView> mImageViews = new ArrayList<>();
    private Activity mActivity;
    private ArrayList<ImageInfo> mArrayImages = new ArrayList<>();
    private View.OnClickListener mClickImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            int pos = (int) v.getTag(R.id.image);
//            Intent intent = new Intent(mActivity, PhotoPickDetailActivity.class);
//            intent.putExtra(PhotoPickDetailActivity.PHOTO_BEGIN, pos);
//            intent.putExtra(PhotoPickDetailActivity.EXTRA_MAX, MaopaoAddActivity.PHOTO_MAX_COUNT);
//            intent.putExtra(PhotoPickDetailActivity.PICK_DATA, mArrayImages);
//            intent.putExtra(PhotoPickDetailActivity.ALL_DATA, mArrayImages);
//            mActivity.startActivityForResult(intent, RESULT_REQUEST_COMMENT_IMAGE_DETAIL);
        }
    };

    public ImageCommentLayout(Activity activity, View.OnClickListener onClickSend ) {
        mEnterLayout = new EnterLayout(activity, onClickSend, EnterLayout.Type.TextOnly) {

            @Override
            protected boolean sendButtonEnable() {
                return getContent().length() > 0 ||
                        !mArrayImages.isEmpty();
            }
        };

        mActivity = activity;

        View v = activity.findViewById(R.id.commonEnterRoot);
        mRootLayout = v;
        v.findViewById(R.id.commentImageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                Intent intent = new Intent(mActivity, PhotoPickActivity.class);
//                intent.putExtra(PhotoPickActivity.EXTRA_PICKED, mArrayImages);
                mActivity.startActivityForResult(intent, RESULT_REQUEST_COMMENT_IMAGE);

            }
        });

        mFlowLayout = (ViewGroup) v.findViewById(R.id.imageLayout);
        mFlowLayout.setVisibility(View.GONE);
    }

    public EnterLayout getEnterLayout() {
        return mEnterLayout;
    }

    public void hide() {
        mRootLayout.setVisibility(View.GONE);
    }

    //
    public void onActivityResult(int RESULT_TYPE, Intent data) {
        if (data == null) {
            return;
        }

        if (RESULT_TYPE == RESULT_REQUEST_COMMENT_IMAGE) {
            @SuppressWarnings("unchecked")
            ArrayList<ImageInfo> images = (ArrayList) data.getSerializableExtra("data");
            if (data == null) {
                return;
            }
            Uri fileUri = data.getData();
            String filePath = FileUtil.getPath(mActivity, fileUri);
            ImageInfo imageInfo=new ImageInfo();
            imageInfo.setPath(filePath);
            mArrayImages.clear();
            mArrayImages.add(imageInfo);
            updateCommentImage();

        } else if (RESULT_TYPE == RESULT_REQUEST_COMMENT_IMAGE_DETAIL) {
            @SuppressWarnings("unchecked")
            ArrayList<ImageInfo> images = (ArrayList) data.getSerializableExtra("data");
            mArrayImages = images;
            updateCommentImage();
        }
    }

    public void clearContent() {
        mEnterLayout.hideKeyboard();
        mEnterLayout.clearContent();
        mArrayImages.clear();
        updateCommentImage();
    }

    private void updateCommentImage() {
        final int imageUrlCount = mArrayImages.size();
        if (imageUrlCount == 0) {
            mFlowLayout.setVisibility(View.GONE);
            mFlowLayout.removeAllViews();
            return;
        }

        mFlowLayout.setVisibility(View.VISIBLE);
        int count = mFlowLayout.getChildCount();

        if (imageUrlCount > count) {
            int need = imageUrlCount - count;
            LayoutInflater inflater = LayoutInflater.from(mFlowLayout.getContext());
            for (int i = 0; i < need; ++i) {
                inflater.inflate(R.layout.comment_image, mFlowLayout);
        }
        } else if (imageUrlCount < count) {
            int release = count - imageUrlCount;
            for (int i = 0; i < release; ++i) {
                mFlowLayout.removeViewAt(count - 1 - i);
            }
        }

        for (int i = 0; i < imageUrlCount; ++i) {
            ImageView image = (ImageView) mFlowLayout.getChildAt(i);
            image.setOnClickListener(mClickImage);
            image.setTag(R.id.image, i);

            SetPicassopic(mArrayImages.get(i).getPath(),image);
//            mImageLoader.loadImage(image, mArrayImages.get(i).path, ContentAreaMuchImages.imageOptions);

        }

        mEnterLayout.updateSendButtonStyle();
    }

    public ArrayList<ImageInfo> getPickPhotos() {
        return mArrayImages;
    }

}
