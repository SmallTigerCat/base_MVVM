package com.sports2020.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


import com.sports2020.demo.R;

import java.lang.reflect.Method;

/**
 * 公共dialog
 * Created by KongXin on 2020/7/8
 */
public class PopupDialog extends AlertDialog {

    private static final String TAG = "PopupDialog";
    private View view;
    private Context context;
    private TextView title;
    private ImageView close;
    private TextView msg;
    private TextView confirm;
    private TextView cancel;
    private LinearLayout bottomLl;
    private RelativeLayout topRl;
    private View verticalLine;
    private int width;

    protected PopupDialog(Context context, boolean cancelable, boolean canceledOnTouchOutside) {
        super(context, R.style.Dialog_Common);
        this.context = context;
        double deviceWidth = getScreenWidth(this.context);
        width = (int) (deviceWidth * 0.7);
        setCancelable(cancelable);
        setCanceledOnTouchOutside(canceledOnTouchOutside);
        LayoutInflater inflater = LayoutInflater.from(this.context);
        view = inflater.inflate(R.layout.popup_dialog, null);
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams
                .WRAP_CONTENT, 0);
        setContentView(view, params);
    }

    public void setDialogTitle(CharSequence title, boolean closeBtn) {
        if (title == null || "".equals(title)) {
            if (this.title != null) {
                this.title.setVisibility(View.GONE);
            }
        } else {
            if (this.title != null) {
                this.title.setText(title);
            }
        }
        if (closeBtn && this.close != null) {
            this.close.setVisibility(View.VISIBLE);
        }
    }

    public void setDialogMessage(CharSequence msg) {
        if (msg == null || "".equals(msg)) {
            if (this.msg != null) {
                this.msg.setVisibility(View.GONE);
            }
        } else {
            if (this.msg != null) {
                this.msg.setText(msg);
            }
        }
    }

    protected void setDialogButton(int whichButton, CharSequence text, final View.OnClickListener listener) {
        if (text == null || "".equals(text)) {
            switch (whichButton) {
                case DialogInterface.BUTTON_POSITIVE: {
                    if (this.confirm != null) {
                        this.confirm.setVisibility(View.GONE);
                    }
                    break;
                }
                case DialogInterface.BUTTON_NEGATIVE: {
                    if (this.cancel != null) {
                        this.cancel.setVisibility(View.GONE);
                    }
                    break;
                }
                default: {
                    Log.e(TAG, "Button can not be found. whichButton=" + whichButton);
                }
            }
        } else {
            switch (whichButton) {
                case DialogInterface.BUTTON_POSITIVE: {
                    if (this.confirm != null) {
                        this.confirm.setText(text);
                        this.confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.onClick(v);
                                }
                                dismiss();
                            }
                        });
                    }
                    break;
                }
                case DialogInterface.BUTTON_NEGATIVE: {
                    if (this.cancel != null) {
                        this.cancel.setText(text);
                        this.cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.onClick(v);
                                }
                                dismiss();
                            }
                        });
                    }
                    break;
                }
                default: {
                    Log.e(TAG, "Button can not be found. whichButton=" + whichButton);
                }
            }
        }
    }

    public void setDialogButton(String confirm, View.OnClickListener positiveClickListener, String cancel, View
            .OnClickListener negativeClickListener) {
        if ((confirm == null || "".equals(confirm)) && (cancel == null || "".equals(cancel))) {
            if (this.bottomLl != null) {
                this.bottomLl.setVisibility(View.GONE);
            }
        } else if ((confirm != null && !"".equals(confirm)) && (cancel != null && !"".equals(cancel))) {
            setDialogButton(DialogInterface.BUTTON_POSITIVE, confirm, positiveClickListener);
            setDialogButton(DialogInterface.BUTTON_NEGATIVE, cancel, negativeClickListener);
        } else {
            // Hide vertical line
            this.verticalLine.setVisibility(View.GONE);
            // Hide positive button
            setDialogButton(DialogInterface.BUTTON_POSITIVE, null, null);
            if (confirm == null || "".equals(confirm)) {
                setDialogButton(DialogInterface.BUTTON_NEGATIVE, cancel, negativeClickListener);
            } else {
                // confirm is not null and cancel is null
                setDialogButton(DialogInterface.BUTTON_NEGATIVE, confirm, positiveClickListener);
            }

        }
    }

    private void initView() {
        this.title = (TextView) view.findViewById(R.id.common_dialog_title_tv);
        this.close = (ImageView) view.findViewById(R.id.common_dialog_close_iv);
        this.msg = (TextView) view.findViewById(R.id.common_dialog_message_tv);
        // Set Scrollable
        this.msg.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.confirm = (TextView) view.findViewById(R.id.common_dialog_confirm_tv);
        this.cancel = (TextView) view.findViewById(R.id.common_dialog_cancel_tv);
        this.bottomLl = (LinearLayout) view.findViewById(R.id.common_dialog_bottom_ll);
        this.topRl = (RelativeLayout) view.findViewById(R.id.common_dialog_top_rl);
        this.verticalLine = view.findViewById(R.id.common_dialog_vertical_line);

        if (this.close != null) {
            this.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    /**
     * Obtain a confirm dialog instance
     *
     * @param context                context
     * @param title                  对话框的标题，如果不需要标题，则传递null或
     * @param message                要显示的消息
     * @param confirm                确认按钮的名称，如果不需要确认按钮，则传递null或
     * @param positiveClickListener  单击确认按钮的侦听器
     * @param cancel                 取消按钮的名称，如果不需要确认按钮，则传递null或
     * @param negativeClickListener  单击“取消”按钮的侦听器
     * @param cancelable             按后退可取消
     * @param canceledOnTouchOutside 在触摸外部时取消
     * @param closeBtn               是否显示关闭按钮
     *
     * @return PopupDialog
     */
    public static PopupDialog create(Context context, String title, String message, String confirm, View.OnClickListener
            positiveClickListener, String cancel, View.OnClickListener negativeClickListener, boolean cancelable,
                                                               boolean canceledOnTouchOutside, boolean closeBtn) {
        PopupDialog dialog = new PopupDialog(context, cancelable, canceledOnTouchOutside);
        dialog.setDialogTitle(title, closeBtn);
        dialog.setDialogMessage(message);
        dialog.setDialogButton(confirm, positiveClickListener, cancel, negativeClickListener);

        return dialog;
    }

    /**
     * 获取确认对话框实例
     *
     * @param context                context
     * @param titleRes               对话框标题的资源id，如果不需要标题，则传递0
     * @param messageRes             对话框消息的资源id，如果不需要标题，则传递0
     * @param confirmRes             对话框的“确认”按钮的资源id，如果不需要标题，则传递0
     * @param positiveClickListener  单击确认按钮的侦听器
     * @param cancelRes              对话框“取消”按钮的资源id，如果不需要标题，则传递0
     * @param negativeClickListener  单击“取消”按钮的侦听器
     * @param cancelable             按后退可取消
     * @param canceledOnTouchOutside 在触摸外部时取消
     * @param closeBtn               是否显示关闭按钮
     *
     * @return PopupDialog
     */
    public static PopupDialog create(Context context, int titleRes, int messageRes, int confirmRes, View.OnClickListener
            positiveClickListener, int cancelRes, View.OnClickListener negativeClickListener, boolean cancelable,
                                                               boolean canceledOnTouchOutside, boolean closeBtn) {
        return create(context, titleRes, messageRes, confirmRes, positiveClickListener, cancelRes, negativeClickListener,
                cancelable, canceledOnTouchOutside, closeBtn, null);
    }

    public static PopupDialog create(Context context, int titleRes, int messageRes, int confirmRes, View.OnClickListener
            positiveClickListener, int cancelRes, View.OnClickListener negativeClickListener, boolean cancelable,
                                                               boolean canceledOnTouchOutside, boolean closeBtn, OnDismissListener listener) {
        PopupDialog dialog = new PopupDialog(context, cancelable, canceledOnTouchOutside);
        if (listener != null) {
            dialog.setOnDismissListener(listener);
        }
        String title = null;
        try {
            title = titleRes > 0 ? context.getResources().getString(titleRes) : null;
        } catch (Resources.NotFoundException e) {
            Log.w(TAG, "Resource not found. resId=" + titleRes, e);
        }
        dialog.setDialogTitle(title, closeBtn);
        String msg = null;
        try {
            msg = messageRes > 0 ? context.getResources().getString(messageRes) : null;
        } catch (Resources.NotFoundException e) {
            Log.w(TAG, "Resource not found. resId=" + messageRes, e);
        }
        dialog.setDialogMessage(msg);
        String confirm = null;
        String cancel = null;
        try {
            confirm = confirmRes > 0 ? context.getResources().getString(confirmRes) : null;
            cancel = cancelRes > 0 ? context.getResources().getString(cancelRes) : null;
        } catch (Resources.NotFoundException e) {
            Log.w(TAG, "Resource not found.", e);
        }
        dialog.setDialogButton(confirm, positiveClickListener, cancel, negativeClickListener);

        return dialog;
    }

    public void setCancel(int cancelRes) {
        cancel.setText(context.getResources().getString(cancelRes));
    }

    private int getScreenWidth(Context context) {
        return getScreenSize(context)[0];
    }

    private int getScreenHeight(Context context) {
        return getScreenSize(context)[1];
    }

    @SuppressLint("WrongConstant")
    private int[] getScreenSize(Context context) {
        WindowManager windowManager;
        try {
            windowManager = (WindowManager)context.getSystemService("window");
        } catch (Throwable var6) {
            Log.w(TAG, var6);
            windowManager = null;
        }

        if(windowManager == null) {
            return new int[]{0, 0};
        } else {
            Display display = windowManager.getDefaultDisplay();
            if(Build.VERSION.SDK_INT < 13) {
                DisplayMetrics t1 = new DisplayMetrics();
                display.getMetrics(t1);
                return new int[]{t1.widthPixels, t1.heightPixels};
            } else {
                try {
                    Point t = new Point();
                    Method method = display.getClass().getMethod("getRealSize", new Class[]{Point.class});
                    method.setAccessible(true);
                    method.invoke(display, new Object[]{t});
                    return new int[]{t.x, t.y};
                } catch (Throwable var5) {
                    Log.w(TAG, var5);
                    return new int[]{0, 0};
                }
            }
        }
    }
}
