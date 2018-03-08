package com.filter_app;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.filter_app.utility.Helper;
import com.filter_app.utility.TransformImage;
import com.filter_app.utility.TransformImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;

import java.io.IOException;
import java.nio.file.DirectoryStream;

public class SecondPage extends AppCompatActivity {

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }
    Toolbar mControlToolbar;

    ImageView mCenterImageView;

    TransformImage mTransformImage;
     int mCurrentFilter;
    SeekBar mSeekBar;
    Uri mSselectedImageUri;

    ImageView mTickImageView;

    ImageView cancelImageView;

    int mScreenWidth;
    int mScreenHeight;
    Target mApplySingleFilter = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            int currentFilterValue =mSeekBar.getProgress();

            if (mCurrentFilter == TransformImage.FILTER_BRIGHTNESS) {
                mTransformImage.applyBrightnessSubFilter(currentFilterValue);
                Helper.writeDataIntoExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS),mTransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));
                Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).resize(0,mScreenHeight/2).into(mCenterImageView);
            } else if (mCurrentFilter == TransformImage.FILTER_CONTRAST) {
                mTransformImage.applyBrightnessSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_CONTRAST),mTransformImage.getBitmap(TransformImage.FILTER_CONTRAST));
                Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_CONTRAST))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }else if (mCurrentFilter == TransformImage.FILTER_VIGNETTE){
                mTransformImage.applyBrightnessSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE),mTransformImage.getBitmap(TransformImage.FILTER_VIGNETTE));
                Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }else if (mCurrentFilter == TransformImage.FILTER_SATURATION){
                mTransformImage.applyBrightnessSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_SATURATION),mTransformImage.getBitmap(TransformImage.FILTER_SATURATION));
                Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_SATURATION))).resize(0,mScreenHeight/2).into(mCenterImageView);

            }
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    Target mSmallTarget= new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            mTransformImage=new TransformImage(SecondPage.this,bitmap);
            mTransformImage.applyBrightnessSubFilter(TransformImage.DEFAULT_BRIGHTNESS);

            Helper.writeDataIntoExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS) ,mTransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));
            Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).fit().centerInside().into(mFirstFilterPreviewImageView);


            //
            mTransformImage.applySaturationSubFilter(TransformImage.DEFAULT_SATURATION);

            Helper.writeDataIntoExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_SATURATION) ,mTransformImage.getBitmap(TransformImage.FILTER_SATURATION));
            Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_SATURATION))).fit().centerInside().into(mSecondFilterPreviewImageView);
            //
            mTransformImage.applyVignetteSubFilter(TransformImage.DEFAULT_VIGNETTE);

            Helper.writeDataIntoExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE) ,mTransformImage.getBitmap(TransformImage.FILTER_VIGNETTE));
            Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).fit().centerInside().into(mThirdFilterPreviewImageView);
            //
            mTransformImage.applyContrastSubFilter(TransformImage.DEFAULT_CONTRAST);

            Helper.writeDataIntoExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_CONTRAST) ,mTransformImage.getBitmap(TransformImage.FILTER_CONTRAST));
            Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_CONTRAST))).fit().centerInside().into(mFourthFilterPreviewImageView);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    final static int PICK_Image = 2;
    final static int MY_PERMISSION_REQUEST_STORAGE_PERMISSION = 3;

    ImageView mFirstFilterPreviewImageView;
    ImageView mSecondFilterPreviewImageView;
    ImageView mThirdFilterPreviewImageView;
    ImageView mFourthFilterPreviewImageView;

    private static final String TAG = SecondPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        mControlToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mCenterImageView = (ImageView) findViewById(R.id.center_image);
        mSeekBar =(SeekBar)findViewById(R.id.seekBar2);

        mControlToolbar.setTitle(getString(R.string.app_name));
        mControlToolbar.setNavigationIcon(R.drawable.icon);
        mControlToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        mFirstFilterPreviewImageView = (ImageView) findViewById(R.id.imageView4);
        mSecondFilterPreviewImageView = (ImageView) findViewById(R.id.imageView7);
        mThirdFilterPreviewImageView = (ImageView) findViewById(R.id.imageView8);
        mFourthFilterPreviewImageView = (ImageView) findViewById(R.id.imageView9);

        mTickImageView = (ImageView) findViewById(R.id.imageView6);
        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondPage.this, ImagePreviewActivity.class);
                startActivity(intent);
            }

        });

        mCenterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermission();

                if (ContextCompat.checkSelfPermission(SecondPage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select Picture"), PICK_Image);
            }

        });
        mFirstFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBar.setProgress(TransformImage.DEFAULT_BRIGHTNESS);
                mSeekBar.setMax(TransformImage.MAX_BRIGHTNESS);

                mCurrentFilter= TransformImage.FILTER_BRIGHTNESS;

               Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });
        mSecondFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBar.setProgress(TransformImage.DEFAULT_SATURATION);
                mSeekBar.setMax(TransformImage.MAX_SATURATION);
                mCurrentFilter= TransformImage.FILTER_SATURATION;

                Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_SATURATION))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });
        mThirdFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBar.setProgress(TransformImage.DEFAULT_VIGNETTE);
                mSeekBar.setMax(TransformImage.MAX_VIGNETTE);
                mCurrentFilter= TransformImage.FILTER_VIGNETTE;
                Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });
        mFourthFilterPreviewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSeekBar.setProgress(TransformImage.DEFAULT_CONTRAST);
                mSeekBar.setMax(TransformImage.FILTER_CONTRAST);
                mCurrentFilter= TransformImage.FILTER_CONTRAST;
                Picasso.with(SecondPage.this).load(Helper.getFileFromExternalStorage(SecondPage.this,mTransformImage.getFilename(TransformImage.FILTER_CONTRAST))).resize(0,mScreenHeight/2).into(mCenterImageView);
            }
        });

        mTickImageView.setOnClickListener(new View.OnClickListener(){
         @Override
            public void onClick(View v){
             Picasso.with(SecondPage.this).load(mSselectedImageUri).into(mApplySingleFilter);
         }
        });
          mTickImageView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Picasso.with(SecondPage.this).load(mSselectedImageUri).into(mApplySingleFilter);
              }
          });

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenHeight=displayMetrics.heightPixels;
        mScreenWidth=displayMetrics.widthPixels;
    }
    public void onRequestPermissionResult(int requestCode, String permission[], int[] grantResult) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE_PERMISSION:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    new MaterialDialog.Builder(SecondPage.this).title("Permission granted")
                            .content("Thank you for proving storage permission")
                            .positiveText("ok").canceledOnTouchOutside(true).show();

                } else {
                    Log.d(TAG, "Permission denied!");
                }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_Image && resultCode == Activity.RESULT_OK) {
            mSselectedImageUri = data.getData();
            Picasso.with(SecondPage.this).load(mSselectedImageUri).fit().centerInside().into(mCenterImageView);

            Picasso.with(SecondPage.this).load(mSselectedImageUri).into(mSmallTarget);

        }

    }
    public void requestStoragePermission(){
            if (ContextCompat.checkSelfPermission(SecondPage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(SecondPage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new MaterialDialog.Builder(SecondPage.this).title(R.string.permission_title)
                            .content(R.string.permission_content)
                            .negativeText(R.string.permission_cancel)
                            .positiveText(R.string.permission_agree_setting)
                            .canceledOnTouchOutside(true)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                                }
                            })
                            .show();
                } else {
                    ActivityCompat.requestPermissions(SecondPage.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_STORAGE_PERMISSION);
                }
                return;
            }

    }
}