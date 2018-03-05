package com.itsite.yicommunity.main.home.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.itsite.abase.common.AudioPlayer;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.common.ScrollingHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.utils.DensityUtils;
import com.itsite.s1.main.home.MainActivity;
import com.itsite.yicommunity.App;
import com.itsite.yicommunity.R;
import com.itsite.yicommunity.common.ApiService;
import com.itsite.yicommunity.common.Constants;
import com.itsite.yicommunity.common.LbsManager;
import com.itsite.yicommunity.common.Params;
import com.itsite.yicommunity.common.UserHelper;
import com.itsite.yicommunity.entity.bean.BannerBean;
import com.itsite.yicommunity.entity.bean.BaseBean;
import com.itsite.yicommunity.entity.bean.FirstLevelBean;
import com.itsite.yicommunity.entity.bean.HomeBean;
import com.itsite.yicommunity.entity.bean.MainDeviceListBean;
import com.itsite.yicommunity.entity.bean.NoticeBean;
import com.itsite.yicommunity.entity.bean.OneKeyDoorBean;
import com.itsite.yicommunity.entity.bean.ServiceBean;
import com.itsite.yicommunity.entity.bean.ServicesTypesBean;
import com.itsite.yicommunity.entity.bean.SubCategoryBean;
import com.itsite.yicommunity.event.EventCommunity;
import com.itsite.yicommunity.main.home.contract.HomeContract;
import com.itsite.yicommunity.main.home.presenter.HomePresenter;
import com.itsite.yicommunity.main.home.view.header.RentalsSunHeaderView;
import com.itsite.yicommunity.main.parking.view.ParkChargeFragment;
import com.itsite.yicommunity.main.picker.PickerActivity;
import com.itsite.yicommunity.main.propery.view.NoticeListFragment;
import com.itsite.yicommunity.main.propery.view.PropertyPayFragment;
import com.itsite.yicommunity.qrcode.QRCodeActivity;
import com.itsite.yicommunity.web.WebActivity;
import com.itsite.yicommunity.widget.OpenDoorDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.itsite.adialog.dialogfragment.SelectorDialogFragment;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by leguang on 2017/4/19 9:15.
 * Email：langmanleguang@qq.com
 * <p>
 * [社区]首页的View层。
 * 打开方式：Start App-->社区
 */
public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    private static final String TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ptrFrameLayout)
    PtrFrameLayout ptrFrameLayout;
    @BindView(R.id.tv_location_home_fragment)
    TextView tvLocation;
    @BindView(R.id.view_toolbar_bg)
    View viewToolbarBg;
    Unbinder unbinder;
    private HomeRVAdapter adapter;
    private LinearLayoutManager layoutManager;
    private String normalNotice = "欢迎来到亿社区！";
    private Params params = Params.getInstance();
    private OpenDoorDialog openDoorialog;
    private int totalDy = 0;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @NonNull
    @Override
    protected HomeContract.Presenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
        initPtrFrameLayout();
    }

    private void initData() {
        setCommunity();

        layoutManager = new LinearLayoutManager(_mActivity);
        recyclerView.setLayoutManager(layoutManager);
        List<HomeBean> data = new ArrayList<>();
        //Banner
        HomeBean bannerBean = new HomeBean();
        bannerBean.setItemType(HomeBean.TYPE_COMMUNITY_BANNER);
        data.add(bannerBean);
        //Notice
        HomeBean noticeBean = new HomeBean();
        noticeBean.setItemType(HomeBean.TYPE_COMMUNITY_NOTICE);
        noticeBean.notice = normalNotice;
        data.add(noticeBean);
        //CommunityService
        HomeBean functioneBean = new HomeBean();
        functioneBean.setItemType(HomeBean.TYPE_COMMUNITY_FUNCTION);
        data.add(functioneBean);
        //
        HomeBean serviceBeans = new HomeBean();
        serviceBeans.setItemType(HomeBean.TYPE_COMMUNITY_SERVICE);
        data.add(serviceBeans);
        //品质服务
        ServiceBean qualityLifeBean0 = new ServiceBean("闲置交换", "社区闲置物品不得闲", R.drawable.bg_xianzhijiaohuan_346px_450px);
        ServiceBean qualityLifeBean1 = new ServiceBean("快递查询", "对接各物流快递公司", R.drawable.bg_expressdelivery_345px_450px);
        ServiceBean qualityLifeBean2 = new ServiceBean("拼车服务", "社区拼车方便快捷", R.drawable.bg_pinchefuwu_345px_450px);

        HomeBean lifes = new HomeBean();
        List<ServiceBean> lifeList = new ArrayList<>();
        lifeList.add(qualityLifeBean0);
        lifeList.add(qualityLifeBean1);
        lifeList.add(qualityLifeBean2);

        lifes.setQualityLifes(lifeList);
        lifes.setItemType(HomeBean.TYPE_COMMUNITY_QUALITY_LIFE);
        data.add(lifes);

        HomeBean wisdoms = new HomeBean();
        wisdoms.setItemType(HomeBean.TYPE_COMMUNITY_WISDOM_LIFE);
        data.add(wisdoms);

        adapter = new HomeRVAdapter(data);
        adapter.setFragment(this);
        recyclerView.setAdapter(adapter);
        //add footer
        View footerView = LayoutInflater.from(_mActivity).inflate(R.layout.footer_no_anymore, null, false);
        adapter.addFooterView(footerView);
    }

    private void setCommunity() {
        String address = UserHelper.city + UserHelper.communityName;
        if (TextUtils.isEmpty(address)) {
            address = "请选择社区";
        }
        tvLocation.setText(address);
    }

    private void initPtrFrameLayout() {
        // header
        final RentalsSunHeaderView header = new RentalsSunHeaderView(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DensityUtils.dp2px(_mActivity, 15), 0, DensityUtils.dp2px(_mActivity, 10));
        header.setUp(ptrFrameLayout);
        ptrFrameLayout.setLoadingMinTime(1000);
        ptrFrameLayout.setDurationToCloseHeader(1500);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.postDelayed(() -> ptrFrameLayout.autoRefresh(true), 100);

        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return ScrollingHelper.isRecyclerViewToTop(recyclerView);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                AudioPlayer.getInstance(_mActivity).play(1);
                mPresenter.requestBanners(params);
                mPresenter.requestHomeNotices(params);
                mPresenter.requestServiceTypes(params);
                mPresenter.requestFirstLevel(params);
            }
        });
    }

    private void initListener() {

        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            int viewType = adapter1.getItemViewType(position);
            switch (viewType) {
//                case HomeBean.TYPE_COMMUNITY_BANNER:
//                    switch (view.getId()) {
//                        case R.id.fl_item_banner:
//
//                            break;
//                    }
//                    break;
                case HomeBean.TYPE_COMMUNITY_NOTICE:
                    _mActivity.start(NoticeListFragment.newInstance());
                    break;
                case HomeBean.TYPE_COMMUNITY_FUNCTION:
                    switch (view.getId()) {
                        case R.id.ll_quick_open_door:
                            //请求列表
                            showLoading();
                            //请求列表
                            mPresenter.requestOneKeyOpenDoorDeviceList(params);
                            break;
                        case R.id.ll_property_payment:
                            _mActivity.start(PropertyPayFragment.newInstance());
                            break;
                        case R.id.ll_equipment_contral:
                            params.powerCode = "SmartEquipment";
                            showLoading();
                            mPresenter.requestCommEquipmentList(params);
                            break;
                        case R.id.ll_temporary_parking:
//                            go2Web("临时停车", ApiService.TEMP_PARKING);
//                            _mActivity.start(TemporaryParkPayFragment.newInstance());
                            _mActivity.start(ParkChargeFragment.newInstance(null));

                            break;
                        case R.id.ll_life_supermarket:
                            ALog.e(UserHelper.communityLongitude);
                            ALog.e(UserHelper.communityLatitude);
                            go2Web("生活超市", ApiService.SUPERMARKET
                                    .replace("%1", UserHelper.token)
                                    .replace("%2", UserHelper.communityLongitude)
                                    .replace("%3", UserHelper.communityLatitude));
//                            _mActivity.start(StoreListFragment.newInstance());
                            break;
                        default:
                    }
                    break;
                default:
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
                Log.d(TAG, "dy---------:" + totalDy);
                int offsetToStart = totalDy;
                int bannerHeight = DensityUtils.getDisplayWidth(_mActivity) / 2;
                if (offsetToStart <= bannerHeight) {
                    float alpha = (offsetToStart * 1.0f / bannerHeight) * 0.9f;
                    setToolbarAlpha(alpha);
                } else {
                    setToolbarAlpha(0.9f);
                }
            }
        });
    }

    public void setToolbarAlpha(float alpha) {
        Log.d(TAG, "alpha:" + alpha);
        viewToolbarBg.setAlpha(alpha);
    }

    private void openDoor(String dir) {
        UserHelper.dir = dir;
        showQuickOpenDoorDialog();
        recyclerView.postDelayed(() -> mPresenter.requestOpenDoor(), 1000);
    }

    public void go2Web(String title, String link) {
        Intent intent = new Intent(_mActivity, WebActivity.class);
        intent.putExtra(Constants.KEY_TITLE, title);
        intent.putExtra(Constants.KEY_LINK, link);
        _mActivity.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        AudioPlayer.getInstance(_mActivity).clear();
        if (adapter != null) {
            adapter = null;
        }
    }

    @Override
    public void error(String errorMessage) {
        super.error(errorMessage);
        ALog.e(TAG, "error:" + errorMessage);
        if (openDoorialog != null) {
            openDoorialog.setError();
        }
        dismissLoading();
        ptrFrameLayout.refreshComplete();
        adapter.notifyItemChanged(0);
        adapter.notifyItemChanged(1);
        DialogHelper.warningSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseBanners(List<BannerBean.DataBean.AdvsBean> banners) {
        ALog.e(TAG, "responseBanners:" + banners.size());
        ptrFrameLayout.refreshComplete();
        adapter.getData().get(0).setBanners(banners);
        adapter.notifyItemChanged(0);
    }

    @Override
    public void responseHomeNotices(List<NoticeBean.DataBean.NoticeListBean> notices) {
        ALog.e(TAG, "responseHomeNotices:" + notices.size());
        ptrFrameLayout.refreshComplete();
        if (notices.size() > 0) {
            adapter.getData().get(1).notice = notices.get(0).getTitle();
            adapter.notifyItemChanged(1);
        } else {
            adapter.getData().get(1).notice = normalNotice;
            adapter.notifyItemChanged(1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCommunity event) {
        //把地址转换成经纬度
        LbsManager.getInstance().geocode(UserHelper.address, UserHelper.city,
                (longitude, latitude) -> {
                    UserHelper.setCommunityLongitude(String.valueOf(longitude));
                    UserHelper.setCommunityLatitude(String.valueOf(latitude));
                });
        setCommunity();
        Params.cmnt_c = UserHelper.communityCode;
        recyclerView.scrollToPosition(0);
        totalDy = 0;//让滑动标记置0，不然在切换社区时，由于通过代码滑动到顶部，这个状态字段就没有归0，会出现顶部栏颜色不变的Bug。
        ptrFrameLayout.autoRefresh();
    }

    @Override
    public void responseOpenDoor() {
        if (openDoorialog != null) {
            openDoorialog.setSuccess();
        }
        DialogHelper.successSnackbar(getView(), "开门成功，欢迎回家，我的主人！");
    }

    @Override
    public void responseServiceClassifyList(List<ServicesTypesBean.DataBean.ClassifyListBean> classifys) {
        ptrFrameLayout.refreshComplete();
        adapter.getData().get(3).setServicesClassifyList(classifys);
        adapter.notifyItemChanged(3);
    }

    @Override
    public void responseOneKeyOpenDoorDeviceList(List<OneKeyDoorBean.DataBean.ItemListBean> doorList) {
        dismissLoading();
        if (doorList.size() == 0) {
            DialogHelper.errorSnackbar(getView(), "该社区没有指定开门");
        } else if (doorList.size() == 1) {
            openDoor(doorList.get(0).getDir());
        } else {
            //创建对话框。
            new SelectorDialogFragment()
                    .setTitle("请选择门禁")
                    .setItemLayoutId(R.layout.item_rv_door_selector)
                    .setData(doorList)
                    .setOnItemConvertListener((holder, position, dialog) -> {
                        OneKeyDoorBean.DataBean.ItemListBean item = doorList.get(position);
                        holder.setText(R.id.tv_name_item_rv_door_selector, item.getName())
                                .setText(R.id.tv_community_item_rv_door_selector, UserHelper.communityName)
                                .setText(R.id.tv_online_item_rv_door_selector, item.isOnline() ? "在线" : "离线")
                                .setTextColor(R.id.tv_online_item_rv_door_selector,
                                        item.isOnline() ? Color.parseColor("#999999") : Color.parseColor("#FF0000"));
                    })
                    .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                        dialog.dismiss();
                        openDoor(doorList.get(position).getDir());
                    })
                    .setAnimStyle(R.style.SlideAnimation)
                    .setGravity(Gravity.BOTTOM)
                    .setHeight(350)
                    .show(getChildFragmentManager());
        }
    }

    @Override
    public void responseFirstLevel(List<FirstLevelBean.DataBean> datas) {
        if (datas.size() > 0) {
            for (FirstLevelBean.DataBean bean:datas){
                if("智能家居".equals(bean.getName())){
                    params.id = bean.getId();
                    break;
                }
                ALog.e(TAG,"id:"+bean.getId());
            }
            mPresenter.requestSubCategoryList(params);
        }
    }

    @Override
    public void responseSubCategoryList(List<SubCategoryBean.DataBean> datas) {
        if (datas.size() > 0) {
            ptrFrameLayout.refreshComplete();
            adapter.getData().get(5).setWisdomLife(datas);
            adapter.notifyItemChanged(5);
        }
    }

    @Override
    public void responseCommEquipmentList(MainDeviceListBean bean) {
        dismissLoading();
        if (bean.getData().size() == 0) {
            new AlertDialog.Builder(_mActivity)
                    .setTitle("温馨提示")
                    .setMessage("您当前暂无添加任何智能设备，可在【管家】的【智能家居】中添加。")
                    .setPositiveButton("确定", null)
                    .show();
        } else {
            new SelectorDialogFragment()
                    .setTitle("请选择智能控制")
                    .setItemLayoutId(R.layout.item_comm_equipment)
                    .setData(bean.getData())
                    .setOnItemConvertListener((holder, position, dialog) -> {
                        holder.setText(R.id.tv_device_name, bean.getData().get(position).getName())
                                .setText(R.id.tv_address, bean.getData().get(position).getResidence().getAddr());
                    })
                    .setOnItemClickListener((view, baseViewHolder, position, dialog) -> {
                        dialog.dismiss();
                        UserHelper.deviceSn = bean.getData().get(position).getNo();
                        UserHelper.deviceName = bean.getData().get(position).getName();
                        Intent intent = new Intent(App.mContext, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        App.mContext.startActivity(intent);
                    })
                    .setAnimStyle(R.style.SlideAnimation)
                    .setGravity(Gravity.BOTTOM)
                    .setHeight(350)
                    .show(getChildFragmentManager());
        }
    }

    @Override
    public void responseScanOpenDoor(BaseBean baseBean) {
        if (openDoorialog != null) {
            openDoorialog.setSuccess();
        }
        DialogHelper.successSnackbar(getView(), baseBean.getOther().getMessage());
    }

    public void go2TopAndRefresh() {
        if (recyclerView == null || ptrFrameLayout == null) {
            return;
        }
        recyclerView.scrollToPosition(0);
        ptrFrameLayout.autoRefresh();
    }

    public void showQuickOpenDoorDialog() {
        if (openDoorialog == null) {
            openDoorialog = new OpenDoorDialog(_mActivity);
        }
        openDoorialog.setOpenDoor();
        openDoorialog.show();
    }

    @OnClick({R.id.tv_location_home_fragment, R.id.tv_scan_home_fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location_home_fragment:
                _mActivity.startActivity(new Intent(_mActivity, PickerActivity.class));
                break;
            case R.id.tv_scan_home_fragment:
                Intent intent = new Intent(_mActivity, QRCodeActivity.class);
                _mActivity.startActivityForResult(intent, QRCodeActivity.QRCODE_REQUEST);
                break;
            default:
        }
    }

    public void scanOpenDoor(Params params) {
        showQuickOpenDoorDialog();
        mPresenter.requestScanOpenDoor(params);
    }
}
