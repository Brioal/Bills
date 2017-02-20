package com.brioal.bills.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.bills.R;
import com.brioal.bills.base.BaseViewHolder;
import com.brioal.bills.bean.ExchaBean;
import com.brioal.bills.interfaces.OnExLongClickListener;
import com.brioal.bills.util.DateUtils;
import com.brioal.bills.util.MoneyFormatUtil;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/19.
 */

public class MainAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final int TYPE_IN = 0;
    private final int TYPE_OUT = 1;


    private Context mContext;
    private List<ExchaBean> mList = new ArrayList<>();
    private OnExLongClickListener mLongClickListener;

    public MainAdapter(Context context) {
        mContext = context;
    }

    public void showList(List<ExchaBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setLongClickListener(OnExLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IN) {
            return new ExInViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ex_in, parent, false));
        }
        return new ExOutViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ex_out, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        KLog.e(mList.get(position).getExchaType().isOut());
        if (mList.get(position).getExchaType().isOut()) {
            return TYPE_OUT;
        }
        return TYPE_IN;
    }

    //收入ViewHolder
    class ExInViewHolder extends BaseViewHolder {
        @BindView(R.id.ex_in_tv_money)
        TextView mTvMoney;
        @BindView(R.id.ex_in_tv_type)
        TextView mTvType;
        @BindView(R.id.ex_in_tv_asset)
        TextView mTvAsset;
        @BindView(R.id.ex_tv_in_money_before)
        TextView mTvMoneyBefore;
        @BindView(R.id.ex_tv_in_money_after)
        TextView mTvMoneyAfter;
        @BindView(R.id.ex_in_tv_time)
        TextView mTvTime;
        @BindView(R.id.ex_in_tv_desc)
        TextView mTvDesc;

        public ExInViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(List list, final int position) {
            final ExchaBean bean = mList.get(position);
            mTvMoney.setText("+"+MoneyFormatUtil.getFormatMoney(bean.getMoney()));
            mTvType.setText(bean.getExchaType().getName());
            mTvAsset.setText(bean.getAsset().getName());
            mTvMoneyBefore.setText(MoneyFormatUtil.getFormatMoney((bean.getAsset().getMoney() - bean.getMoney())));
            mTvMoneyAfter.setText(MoneyFormatUtil.getFormatMoney(bean.getAsset().getMoney()));
            mTvTime.setText(DateUtils.convertTime(bean.getCreatedAt()));
            mTvDesc.setText(bean.getDesc());
            if (mLongClickListener != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mLongClickListener.longClick(bean, position);
                        return true;
                    }
                });
            }
        }
    }

    //支出ViewHolder
    class ExOutViewHolder extends BaseViewHolder {

        @BindView(R.id.ex_out_tv_money)
        TextView mTvMoney;
        @BindView(R.id.ex_out_tv_type)
        TextView mTvType;
        @BindView(R.id.ex_out_tv_asset)
        TextView mTvAsset;
        @BindView(R.id.ex_tv_out_money_before)
        TextView mTvMoneyBefore;
        @BindView(R.id.ex_tv_out_money_after)
        TextView mTvMoneyAfter;
        @BindView(R.id.ex_out_tv_time)
        TextView mTvTime;
        @BindView(R.id.ex_out_tv_desc)
        TextView mTvDesc;


        public ExOutViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(List list, final int position) {
            final ExchaBean bean = mList.get(position);
            mTvMoney.setText("-" + MoneyFormatUtil.getFormatMoney(bean.getMoney()));
            mTvType.setText(bean.getExchaType().getName());
            mTvAsset.setText(bean.getAsset().getName());
            mTvMoneyBefore.setText(MoneyFormatUtil.getFormatMoney((bean.getAsset().getMoney() + bean.getMoney())) + "");
            mTvDesc.setText(bean.getDesc());
            mTvTime.setText(DateUtils.convertTime(bean.getCreatedAt()));
            mTvMoneyAfter.setText(MoneyFormatUtil.getFormatMoney(bean.getAsset().getMoney()) + "");
            if (mLongClickListener != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mLongClickListener.longClick(bean, position);
                        return true;
                    }
                });
            }
        }
    }
}
