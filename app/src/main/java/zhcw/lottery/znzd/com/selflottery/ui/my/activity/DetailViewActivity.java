package zhcw.lottery.znzd.com.selflottery.ui.my.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import zhcw.lottery.znzd.com.selflottery.R;
import zhcw.lottery.znzd.com.selflottery.base.BaseActivity;
import zhcw.lottery.znzd.com.selflottery.contact.DetailContact;
import zhcw.lottery.znzd.com.selflottery.presenter.DetailPresenter;
import zhcw.lottery.znzd.com.selflottery.ready_entity.bean_info.UserInfo;
import zhcw.lottery.znzd.com.selflottery.ui.my.adapter.DetailAdapter;
import zhcw.lottery.znzd.com.selflottery.ui.my.entity.PatrolGroup;
import zhcw.lottery.znzd.com.selflottery.util.ToastUtil;
import zhcw.lottery.znzd.com.selflottery.widgets.ViewBase;

/**
 * 订单详情
 *
 * @author XPZ
 */
public class DetailViewActivity extends BaseActivity implements DetailContact.IDetailView, DetailAdapter.InquireNumListener {

    final StringBuilder stringBuilder = new StringBuilder("");
    private final LinearLayoutManager manager = new LinearLayoutManager(this);
    @BindView(R.id.info_null)
    AutoLinearLayout infoNull;
    @BindView(R.id.iv_edit_title)
    ImageView mIVEdit;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.rl_control)
    AutoLinearLayout rlControl;
    @BindView(R.id.tv_edit_title)
    TextView tvEditTitle;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AutoLinearLayout mTextFanhui;
    private DetailAdapter mAdapter;
    private ArrayList<MultiItemEntity> mLodeData;
    private DetailPresenter detailPresenter;
    private List<Integer> ints;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        detailPresenter = new DetailPresenter(this, this);
        detailPresenter.getSendDetailsJson(UserInfo.getToken());
        initView();
        initListener();
    }

    private void initView() {
        mTextFanhui = findViewById(R.id.text_fanhui);
        mRecyclerView = findViewById(R.id.recycler_order_list);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));
        OverScrollDecoratorHelper.setUpOverScroll(mRecyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

    private void initListener() {
        mTextFanhui.setOnClickListener(v -> finish());
    }

    @Override
    public void setSucessLodeData(ArrayList<MultiItemEntity> lodeData) {
        mIVEdit.setVisibility(View.VISIBLE);
        tvEditTitle.setVisibility(View.GONE);
        rlControl.setVisibility(View.GONE);
        this.mLodeData = lodeData;
        if (lodeData != null && lodeData.size() > 0) {
            mAdapter = new DetailAdapter(lodeData, this);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                if (!mAdapter.isSelect()) {
                    PatrolGroup item = (PatrolGroup) adapter.getItem(position);
                    ViewBase.DetailCodeDialog(getResources().getString(R.string.detail_qrtxt),
                            ViewBase.encodeAsBitmap(item.getQrcodeStr()), view.getContext());
                }
            });
            InquireSelectNum();
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(manager);
        } else {
            infoNull.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setDefaultError(String message) {
        ToastUtil.showShortToast(message);
        infoNull.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void setDefaultErrorView() {
        infoNull.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void setDeleteOrderView() {
        // 降序
        Collections.sort(ints, Collections.reverseOrder());
        for (int i : ints) {
            mAdapter.remove(i);
        }
        InquireSelectNum();
    }


    /**
     * 用来外部设置全选与否
     *
     * @param b true为选中
     */
    private void SelectALLandNo(boolean b) {
        if (mLodeData != null && mLodeData.size() > 0) {
            for (MultiItemEntity multi : mLodeData) {
                PatrolGroup patrol = (PatrolGroup) multi;
                patrol.setSelect(b);
            }
        }
    }

    /**
     * 返回选中条数
     *
     * @return
     */
    private int getSelectNum() {
        int temp = 0;
        if (mLodeData != null && mLodeData.size() > 0) {
            for (MultiItemEntity multi : mLodeData) {
                PatrolGroup patrol = (PatrolGroup) multi;
                if (patrol.isSelect()) {
                    ++temp;
                }
            }
        }
        return temp;
    }

    @OnClick({R.id.iv_edit_title, R.id.tv_edit_title, R.id.tv_select_all, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit_title:
                if (mLodeData != null && mLodeData.size() > 0) {
                    for (int i = 0; i < mLodeData.size(); i++) {
                        mAdapter.collapse(i);//关闭所有展开的view
                    }
                }
                tvEditTitle.setVisibility(View.VISIBLE);
                mIVEdit.setVisibility(View.GONE);
                rlControl.setVisibility(View.VISIBLE);
                if (mAdapter != null) {
                    mAdapter.setSelect(true);
                    mAdapter.notifyDataSetChanged();
                }
                InquireSelectNum();
                break;
            case R.id.tv_edit_title:
                tvEditTitle.setVisibility(View.GONE);
                mIVEdit.setVisibility(View.VISIBLE);
                rlControl.setVisibility(View.GONE);
                if (mAdapter != null) {
                    mAdapter.setSelect(false);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_select_all:
                if (tvSelectAll.getText().equals("全选")) {
                    SelectALLandNo(true);
                } else {
                    SelectALLandNo(false);
                }
                InquireSelectNum();
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_delete: //删除
                stringBuilder.setLength(0);
                ViewBase.CommonDialog(this, "确认删除所选数据吗?",
                        (view1, dialog) -> {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            ints = new ArrayList<>();
                            for (MultiItemEntity multi : mLodeData) {
                                PatrolGroup patrol = (PatrolGroup) multi;
                                if (patrol.isSelect()) {
                                    stringBuilder.append(",").append(patrol.getOrderNum());
                                    ints.add(mAdapter.getParentPosition(patrol));
                                }
                            }
                            detailPresenter.deleteOrderJson(UserInfo.getToken(),
                                    stringBuilder.toString().substring(1));
                        });
                break;
        }
    }

    @Override
    public void InquireSelectNum() {
        if (getSelectNum() != 0) {
            tvSelectAll.setText(getSelectNum() == mLodeData.size()
                    ? "取消全选" : "全选");
            tvDelete.setText(String.format("删除(%s)", getSelectNum() > 99 ? "99+" : getSelectNum()));
            tvDelete.setTextColor(getResources().getColor(R.color.background_dark));
            tvDelete.setEnabled(true);
        } else {
            tvDelete.setEnabled(false);
            tvDelete.setTextColor(getResources().getColor(R.color.grey_500));
            tvDelete.setText("删除");
        }
    }
}
