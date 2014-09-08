package com.bookcell.encoder;

import android.graphics.Bitmap;
import android.os.Handler;

import com.bookcell.encoder.camera.CameraManager;
import com.google.zxing.Result;

public interface IDecoderActivity {

    public ViewfinderView getViewfinder();

    public Handler getHandler();

    public CameraManager getCameraManager();

    public void handleDecode(Result rawResult, Bitmap barcode);
}
