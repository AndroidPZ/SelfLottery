package zhcw.lottery.znzd.com.selflottery.ui.my.adapter;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.ui.my.activity.RechargeActivity;
import zhcw.lottery.znzd.com.selflottery.ready_entity.local_entity.ItemModel;
import zhcw.lottery.znzd.com.selflottery.util.Logger;
import zhcw.lottery.znzd.com.selflottery.widgets.pswkeyboard.widget.VirtualKeyboardView;

/**
 * Created by XPZ on 2018/8/25.
 * 
 * 
 */
public class KeyMoneyAdapter extends RecyclerView.Adapter<KeyMoneyAdapter.BaseViewHolder> {

    protected static String money;
    private static int lastPressIndex = -1;
    private static EditText et;
    private final int maxTopMoney = 10000; //充值上限
    private final RechargeActivity mActivity;
    private final VirtualKeyboardView mVirtualKeyboardView;
    private ArrayList<ItemModel> dataList = new ArrayList<>();
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;
    private Animation enterAnim;
    private Animation exitAnim;
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮

                String amount = et.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                et.setText(amount);

                Editable ea = et.getText();
                et.setSelection(ea.length());
            } else {

    /*            if (position == 9) {      // 点击 .
                    String amount = et.getText().toString().trim();
                    if (!amount.contains(".")) {
                        amount = amount + valueList.get(position).get("name");
                        et.setText(amount);

                        Editable ea = et.getText();
                        et.setSelection(ea.length());
                    }
                }*/

                if (position == 11) {      //点击退格键
                    String amount = et.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        et.setText(amount);

                        Editable ea = et.getText();
                        et.setSelection(ea.length());
                    }
                }
            }
        }
    };

    public KeyMoneyAdapter(RechargeActivity rechargeActivity, VirtualKeyboardView virtualKeyboardView) {
        this.mActivity = rechargeActivity;
        this.mVirtualKeyboardView = virtualKeyboardView;

        keyBoardAnim();
        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVirtualKeyboardView.startAnimation(exitAnim);
                mVirtualKeyboardView.setVisibility(View.GONE);
                et.setCursorVisible(false);
            }
        });

        valueList = virtualKeyboardView.getValueList();

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);

    }

    public static String getMoney() {
        if (lastPressIndex != -1) {
            return money != null ? money : "0";
        } else {
            return et.getText().toString().trim();
        }
    }

    private void keyBoardAnim() {
        enterAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_bottom_out);
    }

    public void replaceAll(ArrayList<ItemModel> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public KeyMoneyAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemModel.ONE:
                return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.one, parent, false));
            case ItemModel.TWO:
                return new TWoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.two, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(KeyMoneyAdapter.BaseViewHolder holder, int position) {

        holder.setData(dataList.get(position).data);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object data) {
        }
    }

    public class OneViewHolder extends BaseViewHolder {
        public TextView tv;

        public OneViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d("XPZ", "OneViewHolder: ");
                    int position = getAdapterPosition();
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        void setData(Object data) {
            if (data != null) {
                String text = (String) data;
                tv.setText(text);
                if (getAdapterPosition() == lastPressIndex) {
                    if (et != null) {
                        et.setText("");
                        et.setCursorVisible(false);
                        if (mVirtualKeyboardView != null && mVirtualKeyboardView.getVisibility() == View.VISIBLE) {
                            mVirtualKeyboardView.startAnimation(exitAnim);
                            mVirtualKeyboardView.setVisibility(View.GONE);
                        }
                    }
                    tv.setSelected(true);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary));
                    money = tv.getText().toString();
                } else {
                    tv.setSelected(false);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                }
            }
        }
    }

    public class TWoViewHolder extends BaseViewHolder {
        @SuppressLint("ClickableViewAccessibility")
        TWoViewHolder(View view) {
            super(view);
            et = (EditText) view.findViewById(R.id.et);

            // 设置不调用系统键盘
            if (android.os.Build.VERSION.SDK_INT <= 15) {
                et.setInputType(InputType.TYPE_NULL);
            } else {
                mActivity.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                try {
                    Class<EditText> cls = EditText.class;
                    Method setShowSoftInputOnFocus;
                    setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                            boolean.class);
                    setShowSoftInputOnFocus.setAccessible(true);
                    setShowSoftInputOnFocus.invoke(et, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            et.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEvent.ACTION_DOWN == event.getAction()) {
                        Logger.d("XPZ", "TwoViewHolder: ACTION_DOWN ");
                        lastPressIndex = -1;
                        notifyDataSetChanged();
                        // 再次点击显示光标
                        et.setCursorVisible(true);
                        //获得焦点
                        et.setFocusable(true);
                        et.setFocusableInTouchMode(true);
                        et.requestFocus();
                        //显示软键盘
                        if (mVirtualKeyboardView != null && mVirtualKeyboardView.getVisibility() != View.VISIBLE) {
                            mVirtualKeyboardView.setFocusable(true);
                            mVirtualKeyboardView.setFocusableInTouchMode(true);
                            mVirtualKeyboardView.startAnimation(enterAnim);
                            mVirtualKeyboardView.setVisibility(View.VISIBLE);
                        }
//                        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.showSoftInput(et, 0);
                    }
                    return true;
                }
            });

            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (start == 0) {
                        if (!"".equals(s.toString()) && Integer.valueOf(s.toString()) == 0) {
                            et.setText("");
                        } else {
                            if ("".equals(s.toString())) {
                                et.setSelected(false);
                            } else {
                                et.setSelected(true);
                            }
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Logger.d("XPZ", "afterTextChanged: " + s);
                    if (!"".equals(s.toString()) && Integer.valueOf(s.toString()) > maxTopMoney) {
                        et.setText(String.valueOf(maxTopMoney));
                        et.setSelection(String.valueOf(maxTopMoney).length());
                    }
                }
            });
        }

        @Override
        void setData(Object data) {
            super.setData(data);
        }
    }
}
