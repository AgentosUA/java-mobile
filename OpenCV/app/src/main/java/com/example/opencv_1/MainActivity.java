package com.example.opencv_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class MainActivity extends Activity implements JavaCameraView.CvCameraViewListener2 {
    private static final String TAG = "MainActivity";
    JavaCameraView javaCameraView;
    Mat mRgba;
    Mat mRgbaFiltered;
    private boolean runIdentification = false;
    private Button btnStart, btnStop;

    BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case BaseLoaderCallback.SUCCESS: {
                    javaCameraView.enableView();
                    break;
                }
                default: {
                    super.onManagerConnected(status);
                    break;
                }

            }


        }

    };

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        javaCameraView = (JavaCameraView)findViewById(R.id.java_camera_view);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(this);
        btnStart = (Button) findViewById(R.id.btn_str);
        btnStop = (Button) findViewById(R.id.btn_stp);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runIdentification = true;
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runIdentification = false;
            }
        });




    }
    @Override
    protected void onPause() {
        super.onPause();
        if (javaCameraView != null) ;
        javaCameraView.disableView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCameraView != null) ;
        javaCameraView.disableView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()) {
            Log.i(TAG, "OpenCV loaded Succes");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        } else {
            Log.i(TAG, "OpenCv gagal");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        }

    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mRgbaFiltered = new Mat(height, width, CvType.CV_8UC4);


    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();




        if(runIdentification) {
            Size size = new Size(45,45);
            Point point = new Point(20,30);
            Imgproc.blur(mRgba,mRgbaFiltered,size,point, Core.BORDER_DEFAULT);
            return mRgbaFiltered;

        }



        return mRgba;
    }

}