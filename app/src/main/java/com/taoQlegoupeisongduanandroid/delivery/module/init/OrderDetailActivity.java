package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.adapter.CommRecyclerAdapter;
import com.taoQlegoupeisongduanandroid.delivery.adapter.ViewHolder;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.CommBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.GrapOrderBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.OrderDetailBean;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.customview.FullyLinearLayoutManager;
import com.taoQlegoupeisongduanandroid.delivery.utils.customview.NoScrollRecyclerview;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.RequestSubscriber;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.CommonUtils;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.DensityUtil;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.LogUtil;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.MapUtil;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class OrderDetailActivity extends BaseActivity implements
        NetChangeListener ,SwipeRefreshLayout.OnRefreshListener,
        AMapNaviListener ,LocationSource,AMapLocationListener {

    @BindView(R.id.orderdetail_addtime)TextView addtime;
    @BindView(R.id.getkm)TextView getkm;
    @BindView(R.id.sendkm)TextView sendkm;
    @BindView(R.id.orderdetail_pay_type_cn)TextView payTeyp;
    @BindView(R.id.orderdetail_income_fee)TextView incomefee;
    @BindView(R.id.orderdetail_getaddress)TextView getAddress;
    @BindView(R.id.orderdetail_sendaddress)TextView sendAddress;
    @BindView(R.id.orderdetail_shopmobile)TextView shopMobile;
    @BindView(R.id.orderdetail_usermobile)TextView userMobile;
    @BindView(R.id.orderdetail_mark)TextView mark;//备注
    @BindView(R.id.orderdetail_username)TextView userName;
    @BindView(R.id.orderdetail_ordersn)TextView orderSn;
    @BindView(R.id.orderdetail_serialsn)TextView serialSn;
    @BindView(R.id.orderdetail_finalfee)TextView finalFee;
    @BindView(R.id.orderdetail_mapview)MapView mMapView;
    @BindView(R.id.orderdetail_pack_fee)TextView packFee;
    @BindView(R.id.orderdetail_box_fee)TextView boxFee;
    @BindView(R.id.orderdetail_delivery_fee)TextView deliveryFee;
    @BindView(R.id.orderdetail_total_fee)TextView totalFee;
    @BindView(R.id.orderdetail_discount_fee)TextView discountFee;
    @BindView(R.id.orderdetail_goodslistview)NoScrollRecyclerview goodsListview;
    @BindView(R.id.orderdetail_suregostore)Button commit;
    @BindView(R.id.orderdetail_transfer)Button transfer;

    private AMap mAMap;
    private AMapNavi aMapNavi;
    // 规划线路
    private RouteOverLay mRouteOverLay;
    private String id = null;
    private OrderDetailBean orderDetailBean;

    private String orderStatus = "7";
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private String getKmstr,sendKmstr;
    private AMapLocation aMapLocation;
    NaviLatLng mStart = null;
    NaviLatLng mEnd = null;
    private long prems = 0;
    private boolean IsOnPase = false;

    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        App.getInstance().addHomeTop(this);
        setToolBar(R.string.title_activity_orderdetail);
        setNetListener(this);
        setRefreshEnable(false);
        setRefreshListener(this);
        id= getIntent().getStringExtra("id");
//        getKmstr = getIntent().getStringExtra("getkm");
//        sendKmstr = getIntent().getStringExtra("sendkm");

        aMapNavi = AMapNavi.getInstance(this);
        aMapNavi.addAMapNaviListener(this);

        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mRouteOverLay = new RouteOverLay(mAMap, null,getApplicationContext());
        //设置起点的图标
        mRouteOverLay.setStartPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.start));
        //设置终点的图标
        mRouteOverLay.setEndPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.end));
        //设置途经点的图标
        mRouteOverLay.setWayPointBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.way));
        setUpMap();
        getOrderDetail();
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(14));//缩放级别
        // aMap.setMyLocationType()
    }

    @OnClick({R.id.orderdetail_usermobilecontainer,
            R.id.orderdetail_shopmobilecontainer,
            R.id.navigation_bt
    })
    void onClick(View view){
        if(isRefreshing())return;
        if(orderDetailBean == null) return;
        switch (view.getId()){
            case R.id.orderdetail_shopmobilecontainer:
                CommonUtils.telphoneCall(mContext,orderDetailBean.getStore().getTelephone());
                break;
            case R.id.orderdetail_usermobilecontainer:
                CommonUtils.telphoneCall(mContext,orderDetailBean.getMobile());
                break;
            case R.id.navigation_bt:
                if(orderStatus.equals("7")) {
                    if(orderDetailBean.getStore().getLocation_x()!=null &&
                            orderDetailBean.getStore().getLocation_y() != null &&
                            !orderDetailBean.getStore().getLocation_x().equals("")&&
                            !orderDetailBean.getStore().getLocation_y().equals("")
                            ) {
                        openGaoDeMap(Double.parseDouble(orderDetailBean.getStore().getLocation_x()),
                                Double.parseDouble(orderDetailBean.getStore().getLocation_y()),
                                "", orderDetailBean.getStore().getAddress()
                        );
                    }else{
                        TS.show(mContext,"无经纬度信息，无法导航！");
                    }
                }else if(orderStatus.equals("4")){
                    if(orderDetailBean.getLocation_x()!=null &&
                            orderDetailBean.getLocation_y() != null &&
                            !orderDetailBean.getLocation_x().equals("")&&
                            !orderDetailBean.getLocation_y().equals("")
                            ) {
                        openGaoDeMap(Double.parseDouble(orderDetailBean.getLocation_x()),
                                Double.parseDouble(orderDetailBean.getLocation_y()),
                                "",orderDetailBean.getAddress()
                        );
                    }else{
                        TS.show(mContext,"无经纬度信息，无法导航！");
                    }
                }else if( orderStatus.equals("5")){
                    TS.show(mContext,"该订单已完成！");
                }
                break;
        }
    }

    private void openGaoDeMap(double lon, double lat, String title, String describle) {
        /*try {
            double[] gd_lat_lon = new double[]{lon,lat};
            StringBuilder loc = new StringBuilder();
            loc.append("androidamap://viewMap?sourceApplication=XX");
            loc.append("&poiname=");
            loc.append(describle);
            loc.append("&lat=");
            loc.append(gd_lat_lon[0]);
            loc.append("&lon=");
            loc.append(gd_lat_lon[1]);
            loc.append("&dev=0");
            Intent intent = Intent.getIntent(loc.toString());
            if (isInstallPackage("com.autonavi.minimap")) {
                startActivity(intent); //启动调用
            } else {
                TS.show(mContext,"请先安装高德地图！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        if (isInstallPackage("com.autonavi.minimap")) {
            Intent intent = new Intent("android.intent.action.VIEW",
                    android.net.Uri.parse("androidamap://navi?sourceApplication="+title+"&poiname="+describle+"+&lat="+lon+"&lon="+lat+"&dev=0&style=2"));
            intent.setPackage("com.autonavi.minimap");
            startActivity(intent);

        } else {
            TS.show(mContext,"请先安装高德地图！");
        }

    }

    private boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 获取订单详情
     */
    private void getOrderDetail() {
        //i="+API_UNIACID+"&c=entry&do=dyorder&op=detail&m=we7_wmall
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyorder");
        map.put("op","detail");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("id",id);
        toSubscribe(apiManager.getApiService().getOrderDetail(map).map(new HttpResultFunc<OrderDetailBean>()),
                new RequestSubscriber<OrderDetailBean>(this,this){

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        finish();
                    }

                    @Override
                    public void onNext(final OrderDetailBean bean) {
                        super.onNext(bean);
                        LogUtil.d(bean.getStatus_cn()+bean.getStatus());
                        orderDetailBean = bean;
                        orderStatus = bean.getDelivery_status();

                        getKmstr=bean.getStore2deliveryer_distance();
                        sendKmstr=bean.getStore2user_distance();
                        getkm.setText("-"+getKmstr+"km-");
                        sendkm.setText("-"+sendKmstr+"km-");
                        addtime.setText(bean.getAddtime_cn());
                        payTeyp.setText(bean.getPay_type_cn());
                        incomefee.setText(bean.getDeliveryer_fee()+"元");
                        getAddress.setText(bean.getStore().getTitle()+"\n"+bean.getStore().getAddress());
                        sendAddress.setText(bean.getAddress());
                        shopMobile.setText(bean.getStore().getTelephone());
                        userMobile.setText(bean.getMobile());
                        userName.setText(bean.getUsername());
                        orderSn.setText(bean.getOrdersn());
                        serialSn.setText(bean.getSerial_sn());
                        finalFee.setText("总计 ￥"+bean.getFinal_fee()+"");
                        packFee.setText("￥"+bean.getPack_fee());
                        boxFee.setText("￥"+bean.getBox_price());
                        deliveryFee.setText("￥"+bean.getDelivery_fee());
                        totalFee.setText("订单 ￥"+bean.getTotal_fee());
                        discountFee.setText("优惠 ￥"+bean.getDiscount_fee());
                        mark.setText(bean.getNote());
                        //商品列表
                        initGoodsListview(bean.getGoods());

                        //按钮
                        controlButton(bean.getDelivery_status());
                        if(bean.getDeliveryer_transfer_status() != null && bean.getDeliveryer_transfer_status().equals("1")){
                            transfer.setVisibility(View.VISIBLE);
                            transfer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showSelectTransferReasonDialog(bean.getDeliveryer_transfer_reason());
                                }
                            });
                        }else{
                            transfer.setVisibility(View.GONE);
                        }
                        /*NaviLatLng mNaviStart = new NaviLatLng(39.989614, 116.481763);
                        NaviLatLng mNaviEnd = new NaviLatLng(39.983456, 116.3154950);*/

                        if(bean.getStore().getLocation_x()!=null &&
                                bean.getStore().getLocation_y()!=null &&
                                bean.getLocation_x() != null &&
                                bean.getLocation_y()!=null &&
                                !bean.getStore().getLocation_x().equals("")&&
                                !bean.getStore().getLocation_y().equals("")&&
                                !bean.getLocation_x().equals("")&&
                                !bean.getLocation_y().equals("")
                                ) {
                            //地图
                            LogUtil.d("起点：x:" + bean.getStore().getLocation_x() + "\n y:" + bean.getStore().getLocation_y());
                            NaviLatLng mNaviStart = new NaviLatLng(
                                    Double.parseDouble(bean.getStore().getLocation_x()),
                                    Double.parseDouble(bean.getStore().getLocation_y()));

                            LogUtil.d("终点：x:" + bean.getLocation_x() + "\n y:" + bean.getLocation_y());
                            NaviLatLng mNaviEnd = new NaviLatLng(
                                    Double.parseDouble(bean.getLocation_x()),
                                    Double.parseDouble(bean.getLocation_y()));

                            mStart = mNaviStart;
                            mEnd = mNaviEnd;
                            //aMapNavi.calculateWalkRoute(mNaviStart, mNaviEnd);
                        }
                    }
                }
        );
    }

    private void showSelectTransferReasonDialog(List<String> reasons) {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view  = View.inflate(mContext,R.layout.dialog_transfer_reason,null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        CommRecyclerAdapter<String> adapter = new CommRecyclerAdapter<String>(
                mContext,reasons,R.layout.list_item_transfer_reason) {
            @Override
            public void convert(ViewHolder holder, final String reason, final int position) {
                holder.setText(R.id.reason,reason);
                holder.setOnClickListener(R.id.reason, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        transgerOrder(reason);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext));
        dialog.setView(view);
        dialog.setCancelable(true);
        dialog.show();

    }


    private void adjustCamera(AMapLocation amapLocation) {
        if(mStart == null || mEnd == null)return;
        List<LatLng> latLngs=new ArrayList<LatLng>();
        LatLng start = new LatLng(mStart.getLatitude(),mStart.getLongitude());
        LatLng end = new LatLng(mEnd.getLatitude(),mEnd.getLongitude());
        LatLng mine = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
        latLngs.add(start);
        latLngs.add(end);
        latLngs.add(mine);
        int screenPx = DensityUtil.dip2px(this,50)+10;
        MapUtil.centerTrackPoints(this,mAMap,latLngs,screenPx);

    }


    /**
     * 控制按钮的显示
     * @param status
     */
    private void controlButton(String status) {
        if(status.equals("3")){
            commit.setText("抢单");
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.showDialog(mContext, "确认抢单？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //i="+API_UNIACID+"&c=entry&do=dyorder&op=collect&m=we7_wmall
                            LinkedHashMap<String,String> map = new LinkedHashMap<>();
                            map.put("i", App.API_UNIACID);
                            map.put("c","entry");
                            map.put("m","we7_wmall");
                            map.put("do","dyorder");
                            map.put("op","collect");
                            map.put("token", (String) SP.get(mContext,"token",""));
                            map.put("id",id);
                            toSubscribe(apiManager.getApiService().getGrapOrder(map).map(new HttpResultFunc<GrapOrderBean>()),
                                    new Subscriber<GrapOrderBean>() {

                                        @Override
                                        public void onStart() {
                                            super.onStart();
                                            showRefresh();
                                        }

                                        @Override
                                        public void onCompleted() {
                                            hideRefresh();
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            hideRefresh();
                                        }

                                        @Override
                                        public void onNext(GrapOrderBean grapOrderBean) {
                                            hideRefresh();
                                            TS.show(mContext,"抢单成功");
                                            setResult(Activity.RESULT_OK);
                                            finish();
                                        }
                                    }
                            );
                        }
                    });
                }
            });
        }else if(status.equals("7")){
            commit.setText("确认取货");
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.showDialog(mContext,"确认已取货？",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sureGoStore();
                        }
                    });
                }
            });
        }else if(status.equals("4")){
            commit.setText("确认送达");
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonUtils.showDialog(mContext,"确认该订单已送达?",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sureSended();
                        }
                    });
                }
            });

        }else if(status.equals("5")){
            commit.setVisibility(View.GONE);
            TS.show(mContext,"此订单已配送成功！");
        }
    }

    /**
     * 商品展示列表
     * @param goods
     */
    private void initGoodsListview(final List<OrderDetailBean.GoodsEntity> goods) {
        goodsListview.setLayoutManager(new LinearLayoutManager(mContext));
        goodsListview.setAdapter(new CommRecyclerAdapter<OrderDetailBean.GoodsEntity>(
                mContext,goods,R.layout.list_item_ordergoodslist ) {
            @Override
            public void convert(ViewHolder holder, OrderDetailBean.GoodsEntity goodsEntity, int position) {
                holder.setText(R.id.item_goodslist_count,"x"+goodsEntity.getGoods_num());
                holder.setText(R.id.item_goodslist_name,goodsEntity.getGoods_title());
                holder.setText(R.id.item_goodslist_sumprice,"￥"+goodsEntity.getGoods_price());
            }
        });
    }

    private void sureGoStore(){
        //i="+API_UNIACID+"&c=entry&do=dyorder&op=instore&m=we7_wmall
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyorder");
        map.put("op","instore");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("id",id);
        toSubscribe(apiManager.getApiService().sureGoStore(map).map(new HttpResultFunc<CommBean>()),
                new Subscriber<CommBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showRefresh();
                    }

                    @Override
                    public void onCompleted() {
                        hideRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideRefresh();
                    }

                    @Override
                    public void onNext(CommBean commBean) {
                        hideRefresh();
                        TS.show(mContext,"确认取货成功");
                        getOrderDetail();
                    }
                });
    }


    private void sureSended(){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyorder");
        map.put("op","success");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("id",id);
        toSubscribe(apiManager.getApiService().sureSended(map).map(new HttpResultFunc<CommBean>()),
                new Subscriber<CommBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        showRefresh();
                    }

                    @Override
                    public void onCompleted() {
                        hideRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideRefresh();
                    }

                    @Override
                    public void onNext(CommBean commBean) {
                        hideRefresh();
                        TS.show(mContext,"确认送达成功");
                        getOrderDetail();
                    }
                });
    }

    /**
     * 转单请求
     * @param reason 转单原因
     */
    private void transgerOrder(String reason){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyorder");
        map.put("op","transfer");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("id",id);
        try {
            map.put("reason", URLEncoder.encode(reason, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            map.put("reason", "");
        }
        toSubscribe(apiManager.getApiService().sureSended(map).map(new HttpResultFunc<CommBean>()),
                new RequestSubscriber<CommBean>(this,this) {
                    @Override
                    public void onNext(CommBean commBean) {
                        hideRefresh();
                        TS.show(mContext,"转单成功");
                        finish();
                    }
                });
    }
    @Override
    public void onNetChange(boolean available) {
        if(available)
            getOrderDetail();
    }

    @Override
    public void onRefresh() {
        getOrderDetail();
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteSuccess() {
        AMapNaviPath naviPath = aMapNavi.getNaviPath();
        if (naviPath == null) {
            return;
        }
        mRouteOverLay.setAMapNaviPath(naviPath);
        mRouteOverLay.addToMap();
    }

    @Override
    public void onCalculateRouteFailure(int i) {
        TS.show(mContext,"路线计算失败");
    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0&&
                    mStart != null && mEnd !=null) {
                aMapLocation = amapLocation;
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点

                int jg = 60*1000;
                long curms = System.currentTimeMillis();
                if(prems == 0 || curms - prems > jg){
                    prems = curms;
                    adjustCamera(amapLocation);
                    ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
                    ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
                    ArrayList<NaviLatLng> mPassPoints = new ArrayList<NaviLatLng>();
                    mStartPoints.add(mStart);

                    mEndPoints.add(mEnd);

                    NaviLatLng pass = new NaviLatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                    mPassPoints.add(pass);
                    aMapNavi.calculateDriveRoute(mPassPoints,mEndPoints, mStartPoints, PathPlanningStrategy.DRIVING_DEFAULT);
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        aMapNavi.destroy();
    }

}
