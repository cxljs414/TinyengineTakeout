package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
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
import com.taoQlegoupeisongduanandroid.delivery.bean.ErrandDetailEntity;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.customview.FullyLinearLayoutManager;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.RequestSubscriber;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.CommonUtils;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.DensityUtil;
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

public class ErrandOrderDetailActivity extends BaseActivity implements
        NetChangeListener ,SwipeRefreshLayout.OnRefreshListener,
        AMapNaviListener ,LocationSource,AMapLocationListener {

    @BindView(R.id.orderdetail_mapview)MapView mMapView;
    @BindView(R.id.getkm)TextView getkm;
    @BindView(R.id.sendkm)TextView sendkm;
    @BindView(R.id.orderdetail_addtime)TextView addtime;
    @BindView(R.id.orderdetail_pay_type_cn)TextView payTeyp;
    @BindView(R.id.orderdetail_income_fee)TextView incomefee;

    @BindView(R.id.errandorder_deliveryfee)TextView deliveryFee;
    @BindView(R.id.errandorder_deliverytips)TextView deliveryTips;
    @BindView(R.id.errandorder_totlefee)TextView totleFee;

    //随意购
    @BindView(R.id.errandorder_goodinfoll_syg)LinearLayout sygGoodInfoLL;
    @BindView(R.id.errandorder_goodname_syg)TextView goodNameSyg;
    @BindView(R.id.errandorder_syg_acceptaddress)TextView acceptAddressSyg;
    @BindView(R.id.detail_callsend)TextView detail_callsend;

    @BindView(R.id.errandorder_goodinfoll_kss)LinearLayout kssGoodInfoLL;
    @BindView(R.id.errandorder_kss_name)TextView kssGoodName;
    @BindView(R.id.errandorder_kss_price)TextView kssGoodPrice;
    @BindView(R.id.errandorder_kss_weight)TextView kssGoodWeight;
    @BindView(R.id.errandorder_kss_acceptaddress)TextView acceptAddress;
    @BindView(R.id.errandorder_acceptmen)TextView acceptMen;//第一个联系人
    @BindView(R.id.errandorder_sendaddress)TextView sendAddress;
    @BindView(R.id.errandorder_sendmen)TextView sendMen;

    @BindView(R.id.errandorder_sendtime)TextView sendTime;
    @BindView(R.id.errandorder_orderid)TextView orderId;
    @BindView(R.id.orderdetail_ordersn)TextView orderSn;
    @BindView(R.id.errandorder_payway)TextView payWay;
    @BindView(R.id.errandorder_note)TextView node;
    @BindView(R.id.itemwaitorder_grab)Button grab;
    @BindView(R.id.itemwaitorder_transfer)Button transfer;
    @BindView(R.id.itemwaitorder_sureget)Button sureget;
    @BindView(R.id.itemwaitorder_getthing)Button getthing;



    private AMap mAMap;
    private AMapNavi aMapNavi;
    // 规划线路
    private RouteOverLay mRouteOverLay;
    private String id = null;
    private ErrandDetailEntity errandDetailEntity;

    private String orderStatus = "7";
    private String verification_code;//1需要收获码 0不需要

    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private String getKmstr,sendKmstr;
    private AMapLocation aMapLocation;
    NaviLatLng mStart = null;
    NaviLatLng mEnd = null;
    private long prems = 0;
    public static String GET_ACTION="get_action";//确认送达
    public static String GETTHING_ACTION="getthing_action";//确认取货
    public static String TRANSFER_ACTION="transfer_action";//转单
    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_errandorder_detail);
        ButterKnife.bind(this);
        App.getInstance().addHomeTop(this);
        setToolBar(R.string.title_activity_orderdetail);
        setNetListener(this);
        setRefreshEnable(false);
        setRefreshListener(this);
        id= getIntent().getStringExtra("id");

        getKmstr = getIntent().getStringExtra("getkm");
        sendKmstr = getIntent().getStringExtra("sendkm");

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
        // aMap.setMyLocationType()

    }

    @OnClick({R.id.detail_callaccept, R.id.detail_navigationaccetp,R.id.detail_callsend,
            R.id.detail_navigationsend,R.id.navigation_bt,R.id.itemwaitorder_grab,R.id.itemwaitorder_sureget,R.id.itemwaitorder_getthing})
    void onClick(View view){
        if(isRefreshing())return;
        if(errandDetailEntity == null) return;
        switch (view.getId()){
            case R.id.detail_callaccept:
                CommonUtils.telphoneCall(mContext,errandDetailEntity.getBuy_mobile());
                break;
            case R.id.detail_navigationaccetp:
                if(orderStatus.equals("4")){
                    TS.show(mContext,"该订单已完成！");
                }else{
                    if(errandDetailEntity.getBuy_location_x()!=null &&
                            errandDetailEntity.getBuy_location_y() != null &&
                            !errandDetailEntity.getBuy_location_x().equals("")&&
                            !errandDetailEntity.getBuy_location_y().equals("")
                            ){
                        openGaoDeMap(Double.parseDouble(errandDetailEntity.getBuy_location_x()),
                                Double.parseDouble(errandDetailEntity.getBuy_location_y()),
                                "",errandDetailEntity.getBuy_address());
                    }else{
                        TS.show(mContext,"无经纬度信息，无法导航！");
                    }

                }

                break;
            case R.id.detail_callsend:
                CommonUtils.telphoneCall(mContext,errandDetailEntity.getAccept_mobile());
                break;
            case R.id.detail_navigationsend:
                if(orderStatus.equals("4")){
                    TS.show(mContext,"该订单已完成！");
                }else {
                    if(errandDetailEntity.getAccept_location_x()!=null &&
                            errandDetailEntity.getAccept_location_y() != null &&
                            !errandDetailEntity.getAccept_location_x().equals("")&&
                            !errandDetailEntity.getAccept_location_y().equals("")
                            ) {
                        openGaoDeMap(Double.parseDouble(errandDetailEntity.getAccept_location_x()),
                                Double.parseDouble(errandDetailEntity.getAccept_location_y()),
                                "", errandDetailEntity.getAccept_address()
                        );
                    }else{
                        TS.show(mContext,"无经纬度信息，无法导航！");
                    }
                }
                break;
            case R.id.navigation_bt:
                if(orderStatus.equals("2")) {
                    if(errandDetailEntity.getBuy_location_x()!=null &&
                            errandDetailEntity.getBuy_location_y() != null &&
                            !errandDetailEntity.getBuy_location_x().equals("")&&
                            !errandDetailEntity.getBuy_location_y().equals("")
                            ) {
                        openGaoDeMap(Double.parseDouble(errandDetailEntity.getBuy_location_x()),
                                Double.parseDouble(errandDetailEntity.getBuy_location_y()),
                                "", errandDetailEntity.getBuy_address()
                        );
                    }else{
                        TS.show(mContext,"无经纬度信息，无法导航！");
                    }
                }else if(orderStatus.equals("3")){
                    if(errandDetailEntity.getAccept_location_x()!=null &&
                            errandDetailEntity.getAccept_location_y() != null &&
                            !errandDetailEntity.getAccept_location_x().equals("")&&
                            !errandDetailEntity.getAccept_location_y().equals("")
                            ) {
                        openGaoDeMap(Double.parseDouble(errandDetailEntity.getAccept_location_x()),
                                Double.parseDouble(errandDetailEntity.getAccept_location_y()),
                                "", errandDetailEntity.getAccept_address()
                        );
                    }else{
                        TS.show(mContext,"无经纬度信息，无法导航！");
                    }
                }else if(orderStatus.equals("4")){
                    TS.show(mContext,"该订单已完成！");
                }
                break;
            case R.id.itemwaitorder_grab:
                CommonUtils.showDialog(mContext, "确认抢单？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //i="+API_UNIACID+"&c=entry&do=dyorder-errander&op=collect&m=we7_wmall
                        LinkedHashMap<String,String> map = new LinkedHashMap<>();
                        map.put("i", App.API_UNIACID);
                        map.put("c","entry");
                        map.put("m","we7_wmall");
                        map.put("do","dyorder-errander");
                        map.put("op","collect");
                        map.put("token", (String) SP.get(mContext,"token",""));
                        map.put("id",id);
                        toSubscribe(apiManager.getApiService().getErrandCollectOrderDetail(map).map(new HttpResultFunc<CommBean>()),
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
                                    public void onNext(CommBean grapOrderBean) {
                                        hideRefresh();
                                        TS.show(mContext,"抢单成功");
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                }
                        );
                    }
                });
            case R.id.itemwaitorder_sureget:
                if ("1".equals(verification_code)) {//需要收获码

                    final Dialog dialog = new Dialog(mContext, R.style.MyDialog);
                    View v = View.inflate(mContext, R.layout.dialog_input, null);
                    final EditText content = (EditText) v.findViewById(R.id.contentet);
                    TextView cancel = (TextView) v.findViewById(R.id.cancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    TextView sure = (TextView) v.findViewById(R.id.sure);
                    sure.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String code = content.getText().toString();
                            if (code.equals("")) {
                                TS.show(mContext, "收货码不能为空！");
                            } else {
                                dialog.dismiss();
                                //i="+API_UNIACID+"&c=entry&do=dyorder-errander&op=success&m=we7_wmall
                                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                map.put("i", App.API_UNIACID);
                                map.put("c", "entry");
                                map.put("m", "we7_wmall");
                                map.put("do", "dyorder-errander");
                                map.put("op", "success");
                                map.put("token", (String) SP.get(mContext, "token", ""));
                                map.put("id", id);
                                map.put("code", code);
                                toSubscribe(apiManager.getApiService().getErrandnuccessOrderDetail(map).map(new HttpResultFunc<CommBean>()),
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
                                                TS.show(mContext, "确认送达成功");
                                                sureget.setVisibility(View.GONE);
                                                Intent intent = new Intent(GET_ACTION);
                                                //发送广播
                                                sendBroadcast(intent);
                                            }
                                        });
                            }

                        }
                    });
                    dialog.setContentView(v);
                    dialog.setCancelable(true);
                    dialog.show();

                }else if("0".equals(verification_code)){
                    CommonUtils.showDialog(mContext, "确定该订单已送达？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LinkedHashMap<String, String> map = new LinkedHashMap<>();
                            map.put("i", App.API_UNIACID);
                            map.put("c", "entry");
                            map.put("m", "we7_wmall");
                            map.put("do", "dyorder-errander");
                            map.put("op", "success");
                            map.put("token", (String) SP.get(mContext, "token", ""));
                            map.put("id", id);
                            map.put("code", "");
                            toSubscribe(apiManager.getApiService().getErrandnuccessOrderDetail(map).map(new HttpResultFunc<CommBean>()),
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
                                            TS.show(mContext, "确认送达成功");
                                            sureget.setVisibility(View.GONE);
                                            Intent intent = new Intent(GET_ACTION);
                                            //发送广播
                                            sendBroadcast(intent);
                                        }
                                    });
                        }
                    });


                }
                break;
            case R.id.orderdetail_transfer:
                break;
            case R.id.itemwaitorder_getthing:
                CommonUtils.showDialog(mContext,"确认已取货？",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //i="+API_UNIACID+"&c=entry&do=dyorder-errander&op=instore&m=we7_wmall
                        LinkedHashMap<String,String> map = new LinkedHashMap<>();
                        map.put("i", App.API_UNIACID);
                        map.put("c","entry");
                        map.put("m","we7_wmall");
                        map.put("do","dyorder-errander");
                        map.put("op","instore");
                        map.put("token", (String) SP.get(mContext,"token",""));
                        map.put("id",id);
                        toSubscribe(apiManager.getApiService().getErrandnstoreOrderDetail(map).map(new HttpResultFunc<CommBean>()),
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
                                        getthing.setVisibility(View.GONE);
                                        Intent intent = new Intent(GETTHING_ACTION);
                                        //发送广播
                                        sendBroadcast(intent);
                                    }
                                });
                    }
                });
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
        //i="+API_UNIACID+"&c=entry&do=dyorder-errander&op=detail&m=we7_wmall
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyorder-errander");
        map.put("op","detail");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("id",id);
        toSubscribe(apiManager.getApiService().getErrandOrderDetail(map).map(new HttpResultFunc<ErrandDetailEntity>()),
                new RequestSubscriber<ErrandDetailEntity>(this,this){

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        //finish();
                    }

                    @Override
                    public void onNext(final ErrandDetailEntity entity) {
                        super.onNext(entity);
                        orderStatus = entity.getDelivery_status();
                        verification_code=entity.getVerification_code();
                        errandDetailEntity = entity;
                        getkm.setText("-"+getKmstr+"km-");
                        sendkm.setText("-"+sendKmstr+"km-");
                        addtime.setText(entity.getAddtime_cn());
                        payTeyp.setText(entity.getPay_type_cn());
                        //incomefee.setText(entity.getDeliveryer_total_fee()+"元");
                        incomefee.setText(entity.getDeliveryer_total_fee()+"元");
                        deliveryFee.setText("￥"+entity.getDeliveryer_fee());
                        deliveryTips.setText("￥"+entity.getDelivery_tips());
                        totleFee.setText("￥"+entity.getDeliveryer_total_fee());

                        if(orderStatus.equals("1")){
                            grab.setVisibility(View.VISIBLE);
                        }else{
                            grab.setVisibility(View.GONE);
                        }
                        if(entity.getDeliveryer_transfer_status() != null && entity.getDeliveryer_transfer_status().equals("1")){
                            transfer.setVisibility(View.VISIBLE);
                            transfer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showSelectTransferReasonDialog(entity.getDeliveryer_transfer_reason());
                                }
                            });
                        }else{
                            transfer.setVisibility(View.GONE);
                        }

                        if(orderStatus.equals("3")){//配送中
                            sureget.setVisibility(View.VISIBLE);
                        }else{
                            sureget.setVisibility(View.GONE);
                        }
                        if(orderStatus.equals("2")){//待取货
                            getthing.setVisibility(View.VISIBLE);
                        }else{
                            getthing.setVisibility(View.GONE);
                        }
                        String orderTyype = entity.getOrder_type_cn();
                        if(orderTyype.equals("随意购")){
                            sygGoodInfoLL.setVisibility(View.VISIBLE);
                            kssGoodInfoLL.setVisibility(View.GONE);
                            goodNameSyg.setText(entity.getGoods_name());
                            acceptAddressSyg.setText(entity.getBuy_address());
                            detail_callsend.setText("呼叫顾客");
                        }else{
                            sygGoodInfoLL.setVisibility(View.GONE);
                            kssGoodInfoLL.setVisibility(View.VISIBLE);
                            kssGoodName.setText(entity.getGoods_name());
                            kssGoodPrice.setText(entity.getGoods_price());
                            kssGoodWeight.setText(entity.getGoods_weight()+"kg");
                            acceptAddress.setText(entity.getBuy_address());
                        }


                        acceptMen.setText(entity.getBuy_username());
                        sendMen.setText(entity.getAccept_username());
                        sendAddress.setText(entity.getAccept_address());
                        sendTime.setText(entity.getDelivery_time());
                        orderId.setText("#"+entity.getId());
                        orderSn.setText(entity.getOrder_sn());
                        payWay.setText(entity.getPay_type_cn());
                        node.setText(entity.getNote());
                        /*NaviLatLng mNaviStart = new NaviLatLng(39.689614, 116.481763);
                        NaviLatLng mNaviEnd = new NaviLatLng(39.983456, 116.3154950);*/

                        System.out.println("起点：x:"+entity.getAccept_location_x()+"\n y:"+entity.getAccept_location_y());

                        System.out.println("终点：x:"+entity.getDeliveryer().getLocation_x()+"\n y:"+entity.getDeliveryer().getLocation_x());
                        if(TextUtils.isEmpty(entity.getAccept_location_x())||TextUtils.isEmpty(entity.getAccept_location_y())){
                            return;
                        }else{
                            NaviLatLng mNaviEnd = new NaviLatLng(
                                    Double.parseDouble(entity.getAccept_location_x()),
                                    Double.parseDouble(entity.getAccept_location_y()));
                            mEnd = mNaviEnd;
                        }
                        if(TextUtils.isEmpty(entity.getDeliveryer().getLocation_x())||TextUtils.isEmpty(entity.getDeliveryer().getLocation_y())){
                            return;
                        }else{

                            NaviLatLng mNaviStart = new NaviLatLng(
                                    Double.parseDouble(entity.getDeliveryer().getLocation_x()),
                                    Double.parseDouble(entity.getDeliveryer().getLocation_y()));
                            mStart = mNaviStart;

                        }

                        if(TextUtils.isEmpty(entity.getBuy_location_x())||TextUtils.isEmpty(entity.getBuy_location_y())){
                            aMapNavi.calculateWalkRoute(mStart, mEnd);
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

    /**
     * 转单请求
     * @param reason 转单原因
     */
    private void transgerOrder(String reason){
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyorder-errander");
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
                        Intent intent = new Intent(TRANSFER_ACTION);
                        //发送广播
                        sendBroadcast(intent);
                        finish();
                    }
                });
    }

    private void adjustCamera(AMapLocation amapLocation) {
        if(mStart == null || mEnd == null)return;
        LatLng start = new LatLng(mStart.getLatitude(),mStart.getLongitude());
        LatLng end = new LatLng(mEnd.getLatitude(),mEnd.getLongitude());
        LatLng mine = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());

        //找到三点中最大的距离
        float dis1 = AMapUtils.calculateLineDistance(start, end);
        float dis2 = AMapUtils.calculateLineDistance(start, mine);
        float dis3 = AMapUtils.calculateLineDistance(end, mine);

        float disMax = dis1;//最大距离单位m
        if(dis2 > disMax)disMax = dis2;
        if(dis3 > disMax)disMax = dis3;

        //计算屏幕宽度单位像素,宽高取小的，所以是200dp
        int screenPx = DensityUtil.dip2px(this,200);

        int[] scales = new int[]{10,25,50,100,200,500,1*1000,2*1000,5*1000,10*1000,20*1000,30*1000,50*1000,100*1000,200*1000,500*1000,1000*1000};
        int[] levels = new int[]{19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3};

        int level = 14;
        for(int i = 0 ;i<scales.length;i++){
            if(disMax > scales[i]*screenPx){
                continue;
            }else{
                level = levels[i+7];
                break;
            }
        }
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(level));//缩放级别

        //确定三点中心，最大最小xy取中
        double minx = getMinValue(start.latitude,end.latitude,mine.latitude);
        double maxx = getMaxValue(start.latitude,end.latitude,mine.latitude);
        double mminy = getMinValue(start.longitude,end.longitude,mine.longitude);
        double maxy = getMaxValue(start.longitude,end.longitude,mine.longitude);


        LatLng marker1 = new LatLng((minx+maxx)/2, (mminy+maxy)/2);
        //设置中心点和缩放比例
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));

    }

    private double getMinValue(double latitude, double latitude1, double latitude2) {

        double min = latitude;
        if(latitude1 < min)min = latitude1;
        if(latitude2 < min)min = latitude2;
        return min;
    }

    private double getMaxValue(double longitude, double longitude1, double longitude2) {
        double max = longitude;
        if(longitude1 > max)max = longitude1;
        if(longitude2 > max)max = longitude2;
        return max;
    }


    @Override
    public void onNetChange(boolean available) {
        if(available){
            getOrderDetail();
        }

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
        // 获取路径规划线路，显示到地图上
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
                    && amapLocation.getErrorCode() == 0 &&
                    mStart != null && mEnd !=null
                    ) {
                aMapLocation = amapLocation;
                int jg = 60*1000;
                long curms = System.currentTimeMillis();
                if(prems == 0 || curms - prems > jg){
                    prems = curms;
                    mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                    adjustCamera(amapLocation);
                    ArrayList<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
                    ArrayList<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
                    ArrayList<NaviLatLng> mPassPoints = new ArrayList<NaviLatLng>();
                    mStartPoints.add(mStart);

                    mEndPoints.add(mEnd);

                    NaviLatLng pass = new NaviLatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                    mPassPoints.add(pass);
                    aMapNavi.calculateDriveRoute(mPassPoints,mEndPoints, mStartPoints,PathPlanningStrategy.DRIVING_DEFAULT);
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
