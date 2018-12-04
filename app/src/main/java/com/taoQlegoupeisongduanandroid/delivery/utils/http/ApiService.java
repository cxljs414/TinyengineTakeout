package com.taoQlegoupeisongduanandroid.delivery.utils.http;

import com.taoQlegoupeisongduanandroid.delivery.bean.AllDetailBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.Cash;
import com.taoQlegoupeisongduanandroid.delivery.bean.CashDetail;
import com.taoQlegoupeisongduanandroid.delivery.bean.CashInfo;
import com.taoQlegoupeisongduanandroid.delivery.bean.ChangePsdBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.CommBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.ErrandDetailEntity;
import com.taoQlegoupeisongduanandroid.delivery.bean.ErrandEntity;
import com.taoQlegoupeisongduanandroid.delivery.bean.GrapOrderBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.HttpResult;
import com.taoQlegoupeisongduanandroid.delivery.bean.OrderBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.OrderDetailBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.OrderStatisticsEntity;
import com.taoQlegoupeisongduanandroid.delivery.bean.SendLocationBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.SerialBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.UpdateBean;
import com.taoQlegoupeisongduanandroid.delivery.bean.User;
import com.taoQlegoupeisongduanandroid.delivery.bean.WorkStatusBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by changxing on 2016/7/26.
 */
public interface ApiService {

    /**
     * 获取序列号接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/index.php?i=1&c=entry&do=serial&m=tiny_manage")
    Observable<HttpResult<SerialBean>> getSerial(@FieldMap Map<String,String> map);


    /**
     * 登陆接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<User>> login(@FieldMap Map<String,String> map);

    /**
     * 申请提现配置接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<CashInfo>> getCashInfo(@FieldMap Map<String,String> map);

    /**
     * 申请提现接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<Cash>> getCash(@FieldMap Map<String,String> map);

    /**
     * 提现详情接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<CashDetail>> getCashDetail(@FieldMap Map<String,String> map);

    /**
     * 全部账户明细接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<AllDetailBean>> getAllDetail(@FieldMap Map<String,String> map);

    /**
     * 修改密码接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<ChangePsdBean>> changePsd(@FieldMap Map<String,String> map);

    /**
     * 检查更新接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<UpdateBean>> checkUpdate(@FieldMap Map<String,String> map);

    /**
     * 工作状态接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<WorkStatusBean>> workStatus(@FieldMap Map<String,String> map);

    /**
     * 上报地理位置接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<SendLocationBean>> sendLocation(@FieldMap Map<String,String> map);

    /**
     * 订单列表接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<OrderBean>> getOrderList(@FieldMap Map<String,String> map);

    /**
     * 配送员抢单接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<GrapOrderBean>> getGrapOrder(@FieldMap Map<String,String> map);

    /**
     *确认取货接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<CommBean>> sureGoStore(@FieldMap Map<String,String> map);

    /**
     *确认取货接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<CommBean>> sureSended(@FieldMap Map<String,String> map);

    /**
     *订单详情接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<OrderDetailBean>> getOrderDetail(@FieldMap Map<String,String> map);

    /**
     *跑腿订单列表接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<ErrandEntity>> getErrandOrderList(@FieldMap Map<String,String> map);

    /**
     *跑腿订单详情接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<ErrandDetailEntity>> getErrandOrderDetail(@FieldMap Map<String,String> map);

    /**
     *跑腿订单抢单接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<CommBean>> getErrandCollectOrderDetail(@FieldMap Map<String,String> map);


    /**
     *跑腿订单取货接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<CommBean>> getErrandnstoreOrderDetail(@FieldMap Map<String,String> map);

    /**
     *跑腿订单确认送达接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<CommBean>> getErrandnuccessOrderDetail(@FieldMap Map<String,String> map);


    /**
     *订单统计接口
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<OrderStatisticsEntity>> getOrderStatistics(@FieldMap Map<String,String> map);

    /**
     * 极光推送标签别名同步
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/api.php?")
    Observable<HttpResult<WorkStatusBean>> dyset(@FieldMap Map<String,String> map);
}
