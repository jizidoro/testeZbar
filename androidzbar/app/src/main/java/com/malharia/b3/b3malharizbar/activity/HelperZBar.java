//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.malharia.b3.b3malharizbar.activity;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.os.Looper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import me.dm7.barcodescanner.core.BarcodeScannerView;
import me.dm7.barcodescanner.core.DisplayUtils;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

public class HelperZBar extends BarcodeScannerView {
    private static final String TAG = "teste";
    private ImageScanner mScanner;
    private List<me.dm7.barcodescanner.zbar.BarcodeFormat> mFormats;
    private HelperZBar.ResultHandler mResultHandler;

    public HelperZBar(Context context) {
        super(context);
        this.setupScanner();
    }

    public HelperZBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setupScanner();
    }

    public void setFormats(List<me.dm7.barcodescanner.zbar.BarcodeFormat> formats) {
        this.mFormats = formats;
        this.setupScanner();
    }

    public void setResultHandler(HelperZBar.ResultHandler resultHandler) {
        this.mResultHandler = resultHandler;
    }

    public Collection<me.dm7.barcodescanner.zbar.BarcodeFormat> getFormats() {
        return this.mFormats == null ? me.dm7.barcodescanner.zbar.BarcodeFormat.ALL_FORMATS : this.mFormats;
    }

    public void setupScanner() {
        this.mScanner = new ImageScanner();
        this.mScanner.setConfig(0, 256, 3);
        this.mScanner.setConfig(0, 257, 3);
        this.mScanner.setConfig(0, 0, 0);
        Iterator var1 = this.getFormats().iterator();

        while(var1.hasNext()) {
            me.dm7.barcodescanner.zbar.BarcodeFormat format = (me.dm7.barcodescanner.zbar.BarcodeFormat)var1.next();
            this.mScanner.setConfig(format.getId(), 0, 1);
        }

    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        if (this.mResultHandler != null) {
            try {
                Parameters parameters = camera.getParameters();
                Size size = parameters.getPreviewSize();
                int width = size.width;
                int height = size.height;
                if (DisplayUtils.getScreenOrientation(this.getContext()) == 1) {
                    int rotationCount = this.getRotationCount();
                    if (rotationCount == 1 || rotationCount == 3) {
                        int tmp = width;
                        width = height;
                        height = tmp;
                    }

                    data = this.getRotatedData(data, camera);
                }

                Rect rect = this.getFramingRectInPreview(width, height);
                Image barcode = new Image(width, height, "Y800");
                barcode.setData(data);
                barcode.setCrop(rect.left, rect.top, rect.width(), rect.height());
                int result = this.mScanner.scanImage(barcode);
                if (result != 0) {
                    SymbolSet syms = this.mScanner.getResults();
                    final me.dm7.barcodescanner.zbar.Result rawResult = new me.dm7.barcodescanner.zbar.Result();
                    Iterator var12 = syms.iterator();

                    while(var12.hasNext()) {
                        Symbol sym = (Symbol)var12.next();
                        String symData;
                        if (VERSION.SDK_INT >= 19) {
                            symData = new String(sym.getDataBytes(), StandardCharsets.UTF_8);
                        } else {
                            symData = sym.getData();
                        }

                        if (!TextUtils.isEmpty(symData)) {
                            rawResult.setContents(symData);
                            rawResult.setBarcodeFormat(me.dm7.barcodescanner.zbar.BarcodeFormat.getFormatById(sym.getType()));
                            break;
                        }
                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            HelperZBar.ResultHandler tmpResultHandler = HelperZBar.this.mResultHandler;
                            HelperZBar.this.mResultHandler = null;
                            HelperZBar.this.stopCameraPreview();
                            if (tmpResultHandler != null) {
                                tmpResultHandler.handleResult(rawResult);
                            }

                        }
                    });
                } else {
                    camera.setOneShotPreviewCallback(this);
                }
            } catch (RuntimeException var15) {
                Log.e("teste", var15.toString(), var15);
            }

        }
    }

    public void resumeCameraPreview(HelperZBar.ResultHandler resultHandler) {
        this.mResultHandler = resultHandler;
        super.resumeCameraPreview();
    }

    static {
        System.loadLibrary("iconv");
    }

    public interface ResultHandler {
        void handleResult(me.dm7.barcodescanner.zbar.Result var1);
    }
}
