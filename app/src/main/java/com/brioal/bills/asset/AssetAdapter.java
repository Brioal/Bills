package com.brioal.bills.asset;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.bills.R;
import com.brioal.bills.base.BaseViewHolder;
import com.brioal.bills.bean.AssetBean;
import com.brioal.bills.interfaces.OnAssetItemListener;
import com.brioal.bills.util.MoneyFormatUtil;
import com.brioal.swipemenuitem.OnMenuItemClickListener;
import com.brioal.swipemenuitem.SwipeMenuItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/19.
 */

public class AssetAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private Context mContext;
    private List<AssetBean> mList = new ArrayList<>();
    private OnAssetItemListener mItemListener;

    public AssetAdapter(Context context) {
        mContext = context;
    }

    public void showList(List<AssetBean> list) {
        mList.clear();
        mList.addAll(list);
    }


    public void setItemListener(OnAssetItemListener itemListener) {
        mItemListener = itemListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AssetViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_asset, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class AssetViewHolder extends BaseViewHolder {

        @BindView(R.id.item_tv_money)
        TextView mTvMoney;
        @BindView(R.id.item_tv_name)
        TextView mTvName;
        @BindView(R.id.item_tv_time)
        TextView mTvTime;

        SwipeMenuItemView mItemView;

        public AssetViewHolder(View itemView) {
            super(itemView);
            mItemView = (SwipeMenuItemView) itemView;
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindView(List list, final int position) {
            final AssetBean bean = (AssetBean) list.get(position);
            mTvMoney.setText(MoneyFormatUtil.getFormatMoney(bean.getMoney()));
            mTvName.setText(bean.getName());
            mTvTime.setText(bean.getUpdatedAt());
            if (mItemListener == null) {
                return;
            }
            mItemView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public void onClicked(int i) {
                    switch (i) {
                        case 0:
                            //编辑操作
                            mItemListener.change(bean, position);
                            break;
                        case 1:
                            //删除操作
                            mItemListener.delete(bean, position);
                            break;
                    }
                }
            });
        }
    }
}
