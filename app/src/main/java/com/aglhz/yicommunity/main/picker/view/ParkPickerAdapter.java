package com.aglhz.yicommunity.main.picker.view;

import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.entity.bean.ParkSelectBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:39
 * 选择停车场模块。
 */

public class ParkPickerAdapter extends BaseMultiItemQuickAdapter<ParkSelectBean.DataBean.ParkPlaceListBean, BaseViewHolder> {
    public static final int TYPE_CONTENT = 0;
    public static final int TYPE_NAME = 1;
    public List<ParkSelectBean.DataBean.ParkPlaceListBean> listHistory;
    private String searchKey = "";

    public ParkPickerAdapter(List<ParkSelectBean.DataBean.ParkPlaceListBean> data) {
        super(data);
        addItemType(TYPE_NAME, R.layout.item_rv_park_selector_type);
        addItemType(TYPE_CONTENT, R.layout.item_rv_park_selector);
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    @Override
    protected void convert(BaseViewHolder holder, ParkSelectBean.DataBean.ParkPlaceListBean item) {
//        if (bean == null) {
//            return;
//        }
//        int nameIndex = bean.getName().indexOf(searchKey);
//        SpannableString nameSpan = new SpannableString(bean.getName());
//        if (nameIndex != -1) {
//            nameSpan.setSpan(new ForegroundColorSpan(Color.RED), nameIndex, nameIndex + searchKey.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        helper.setText(android.R.id.text1, nameSpan);


        switch (holder.getItemViewType()) {
            case TYPE_NAME:
                holder.setText(R.id.tv_name_item_rv_park_selector_type, item.getName())
                        .setVisible(R.id.iv_clean_item_rv_park_selector_type,
                                holder.getLayoutPosition() == 0 && item.getName().equals("历史记录"))
                        .addOnClickListener(R.id.iv_clean_item_rv_park_selector_type);
                break;
            case TYPE_CONTENT:
                holder.setText(R.id.tv_name_item_rv_park_selector, item.getName())
                        .setText(R.id.tv_address_item_rv_park_selector, item.getAddress())
                        .addOnClickListener(R.id.cv_item_rv_park_selector);
                break;
            default:
        }
    }

    public void notifyHistory(List<ParkSelectBean.DataBean.ParkPlaceListBean> listHistory) {
        this.listHistory = new ArrayList<>(listHistory);
        if (!listHistory.isEmpty()) {
            ParkSelectBean.DataBean.ParkPlaceListBean history = new ParkSelectBean.DataBean.ParkPlaceListBean();
            history.setItemType(1);
            history.setName("历史记录");
            listHistory.add(0, history);
        }
        setNewData(listHistory);
    }

    public void removeHistory() {
        mData.remove(0);
        mData.removeAll(listHistory);
        notifyDataSetChanged();
    }
}
