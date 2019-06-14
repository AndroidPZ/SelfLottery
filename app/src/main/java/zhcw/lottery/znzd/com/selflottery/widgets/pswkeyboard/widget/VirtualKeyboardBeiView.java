package zhcw.lottery.znzd.com.selflottery.widgets.pswkeyboard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.widgets.pswkeyboard.adapter.KeyBoardBeiAdapter;

/**
 * 虚拟键盘
 *
 * @author XPZ
 */
public class VirtualKeyboardBeiView extends RelativeLayout {

    Context context;

    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能

    private ArrayList<Map<String, String>> valueList;
    //用Adapter中适配，方便adapter中填充

    private RelativeLayout layoutBack;
    private TextView beiMsg;

    public VirtualKeyboardBeiView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardBeiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.layout_bei_keyboard, null);
        valueList = new ArrayList<>();
        layoutBack = (RelativeLayout) view.findViewById(R.id.layoutBack);
        beiMsg = (TextView) view.findViewById(R.id.tv_bei_msg);
        gridView = (GridView) view.findViewById(R.id.gv_keybord);
        
        initValueList();
        setupView();
        addView(view);      //必须要，不然不显示控件
    }

    public RelativeLayout getLayoutBack() {
        return layoutBack;
    }

    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    private void initValueList() {

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 16; i++) {
            Map<String, String> map = new HashMap<>();
            if (4 <= i && i < 13) {
                map.put("name", String.valueOf(i - 3));
            } else {
                if (i == 1) {
                    map.put("name", "5");
                } else if (i == 2) {
                    map.put("name", "10");
                } else if (i == 3) {
                    map.put("name", "50");
                } else if (i == 13) {
                    map.put("name", "");
                } else if (i == 14) {
                    map.put("name", String.valueOf(0));
                } else {
                    map.put("name", "");
                }
            }
            valueList.add(map);
        }
    }

    public GridView getGridView() {
        return gridView;
    }

    public TextView getBeiMsg() {
        return beiMsg;
    }

    private void setupView() {
        KeyBoardBeiAdapter keyBoardAdapter = new KeyBoardBeiAdapter(context, valueList);
        gridView.setAdapter(keyBoardAdapter);
    }
}
