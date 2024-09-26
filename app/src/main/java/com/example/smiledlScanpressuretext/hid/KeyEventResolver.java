package com.example.smiledlScanpressuretext.hid;

import android.os.Handler;


/**
 * 扫码枪事件解析类 by chen
 */
public class KeyEventResolver {

    private final static long MESSAGE_DELAY = 50;//延迟50ms，判断扫码是否完成。
    private final StringBuffer mStringBufferResult;//扫码内容
    private final Handler mHandler;
    private final Runnable mScanningFishedRunnable;
    boolean hasShift = false;
    private boolean mCaps;//大小写区分
    private OnKeyEventListener mOnKeyEventListener;

    public KeyEventResolver(OnKeyEventListener onScanSuccessListener) {
        mOnKeyEventListener = onScanSuccessListener;
        mStringBufferResult = new StringBuffer();
        mHandler = new Handler();
        mScanningFishedRunnable = new Runnable() {
            @Override
            public void run() {
                performScanSuccess();
            }
        };
    }

    /**
     * 返回扫码成功后的结果
     */
    private void performScanSuccess() {
        String barcode = mStringBufferResult.toString();
        if (mOnKeyEventListener != null) {
            mOnKeyEventListener.onScanSuccess(barcode);
        }
        mStringBufferResult.setLength(0);
    }

    /**
     * 扫码枪事件解析
     */
    public void analysisKeyEvent(android.view.KeyEvent event) {
        int keyCode = event.getKeyCode();
        //字母大小写判断
        checkLetterStatus(event);
        if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
            //char aChar = getInputCode(event);
            char aChar = ' ';
            if (KeyDownUtil.isLeftShift(event.getKeyCode())) {
                hasShift = true;
                return;
            } else {
                if (hasShift) {
                    aChar = KeyDownUtil.getCharFromDictionary(KeyDownUtil.KEYCODE_SHIFT_LEFT, event.getKeyCode());
                } else {
                    aChar = KeyDownUtil.getCharFromDictionary(event.getKeyCode());
                }
                hasShift = false;
            }
            if (aChar != 0) {
                mStringBufferResult.append(aChar);
            }
            if (keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                //若为回车键，直接返回
                mHandler.removeCallbacks(mScanningFishedRunnable);
                mHandler.post(mScanningFishedRunnable);
            }else if(keyCode == android.view.KeyEvent.KEYCODE_BACK){
                mOnKeyEventListener.onBackPress();
            } else {
                //延迟post，若500ms内，有其他事件
                mHandler.removeCallbacks(mScanningFishedRunnable);
                mHandler.postDelayed(mScanningFishedRunnable, MESSAGE_DELAY);
            }
            //else {
            //    //延迟post，若50ms内，有其他事件
            //    mHandler.removeCallbacks(mScanningFishedRunnable);
            //    mHandler.postDelayed(mScanningFishedRunnable, MESSAGE_DELAY);
            //}
        }
    }

    //检查shift键
    private void checkLetterStatus(android.view.KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == android.view.KeyEvent.KEYCODE_SHIFT_RIGHT || keyCode == android.view.KeyEvent.KEYCODE_SHIFT_LEFT) {
            //按着shift键，表示大写,松开shift键，表示小写
            mCaps = event.getAction() == android.view.KeyEvent.ACTION_DOWN;
        }
    }


    //获取扫描内容
    private char getInputCode(android.view.KeyEvent event) {
        int keyCode = event.getKeyCode();
        char aChar;
        if (keyCode >= android.view.KeyEvent.KEYCODE_A && keyCode <= android.view.KeyEvent.KEYCODE_Z) {
            //字母
            aChar = (char) ((mCaps ? 'A' : 'a') + keyCode - android.view.KeyEvent.KEYCODE_A);
        } else if (keyCode >= android.view.KeyEvent.KEYCODE_0 && keyCode <= android.view.KeyEvent.KEYCODE_9) {
            //数字
            aChar = (char) ('0' + keyCode - android.view.KeyEvent.KEYCODE_0);
        } else {
            //其他符号
            switch (keyCode) {
                case android.view.KeyEvent.KEYCODE_PERIOD:
                    aChar = '.';
                    break;
                case android.view.KeyEvent.KEYCODE_MINUS:
                    aChar = mCaps ? '_' : '-';
                    break;
                case android.view.KeyEvent.KEYCODE_SLASH:
                    aChar = '/';
                    break;
                case android.view.KeyEvent.KEYCODE_BACKSLASH:
                    aChar = mCaps ? '|' : '\\';
                    break;
                default:
                    aChar = 0;
                    break;
            }
        }
        return aChar;
    }

    public void onDestroy() {
        mHandler.removeCallbacks(mScanningFishedRunnable);
        mOnKeyEventListener = null;
    }


    public interface OnKeyEventListener {
        void onScanSuccess(String barcode);
        void onBackPress();
    }
}





