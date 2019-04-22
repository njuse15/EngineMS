package cn.nju.se15.enginems.view.widget;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by 果宝 on 2018/3/17.
 */

public class SoftKeyboardListener {

    private View mRootView;                  //activity的根视图
    private int mRootViewVisibleHeight;      //记录根视图的显示高度
    private OnSoftKeyboardChangeListener mOnSoftKeyboardChangeListener;

    public interface OnSoftKeyboardChangeListener {
        void keyBoardShow();

        void keyBoardHide();
    }

    public static void setListener(Activity activity, OnSoftKeyboardChangeListener onSoftKeyboardChangeListener) {
        new SoftKeyboardListener(activity, onSoftKeyboardChangeListener);
    }

    private SoftKeyboardListener(Activity activity, OnSoftKeyboardChangeListener onSoftKeyboardChangeListener) {
        //获取activity的根视图
        mRootView = activity.getWindow().getDecorView();
        mOnSoftKeyboardChangeListener = onSoftKeyboardChangeListener;

        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获取当前根视图在屏幕上显示的大小
                Rect r = new Rect();
                mRootView.getWindowVisibleDisplayFrame(r);

                int visibleHeight = r.height();

                if (mRootViewVisibleHeight == 0) {
                    mRootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (mRootViewVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过200，可以看作软键盘显示了
                if (mRootViewVisibleHeight - visibleHeight > 200) {
                    if (mOnSoftKeyboardChangeListener != null) {
                        mOnSoftKeyboardChangeListener.keyBoardShow();
                    }
                    mRootViewVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度变大超过200，可以看作软键盘隐藏了
                if (visibleHeight - mRootViewVisibleHeight > 200) {
                    if (mOnSoftKeyboardChangeListener != null) {
                        mOnSoftKeyboardChangeListener.keyBoardHide();
                    }
                    mRootViewVisibleHeight = visibleHeight;
                    return;
                }

            }
        });
    }
}
