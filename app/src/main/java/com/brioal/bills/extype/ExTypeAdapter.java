package com.brioal.bills.extype;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.bills.R;
import com.brioal.bills.base.BaseViewHolder;
import com.brioal.bills.bean.ExchaType;
import com.brioal.bills.interfaces.OnItemSwipeListener;
import com.brioal.swipemenuitem.OnMenuItemClickListener;
import com.brioal.swipemenuitem.SwipeMenuItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/21.
 */

public class ExTypeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context mContext;
    private List<ExchaType> mList = new ArrayList<>();
    private OnItemSwipeListener mSwipeListener;

    public ExTypeAdapter(Context context) {
        mContext = context;
    }

    public void showList(List<ExchaType> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setSwipeListener(OnItemSwipeListener swipeListener) {
        mSwipeListener = swipeListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TypeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_extype, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(mList, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TypeViewHolder extends BaseViewHolder {
        @BindView(R.id.item_extype_tv_name)
        TextView mTvName;
        SwipeMenuItemView mItemView;

        public TypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mItemView = (SwipeMenuItemView) itemView;
        }

        @Override
        public void bindView(final List list, final int position) {
            final ExchaType type = (ExchaType) list.get(position);
            //菜单点击事件
            mItemView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public void onClicked(int i) {
                    if (mSwipeListener == null) {
                        return;
                    }
                    switch (i) {
                        case 0:
                            mSwipeListener.change(type, position);
                            break;
                        case 1:
                            mSwipeListener.delete(type, position);
                            break;
                    }
                }
            });
            //显示
            mTvName.setText(type.getName());
            if (type.isOut()) {
                mTvName.setTextColor(mContext.getResources().getColor(R.color.colorRed));
            } else {
                mTvName.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
            }
        }
    }
}
