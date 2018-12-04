package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.Cash;
import com.taoQlegoupeisongduanandroid.delivery.bean.CashInfo;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.BaseFragment;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.CommonUtils;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.InputUtil;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.TS;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class GetCashActivity extends BaseActivity implements NetChangeListener {

    @BindView(R.id.getcash_balance)TextView balance;
    @BindView(R.id.getcash_declare1)TextView declare1;
    @BindView(R.id.getcash_declare2)TextView declare2;
    @BindView(R.id.getcash_cash)EditText cash;
    DecimalFormat format = new DecimalFormat("#.##");

    CashInfo info = null;

    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_getcash);
        ButterKnife.bind(this);
        App.getInstance().addHomeTop(this);
        setToolBar(R.string.apply_getcash);

        getCashInfo(false);
        setNetListener(this);
        //小数点后只能输入两位
        cash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = cash.getText().toString();
                String str = InputUtil.pointFilterDouble(editable.toString());
                if(!editable.equals(str)){
                    cash.setText(str);
                    //设置新的光标所在位置
                    cash.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 页面数据初始化
     */
    private void initData(CashInfo cashInfo) {
        balance.setText("￥"+cashInfo.getCredit2());
        declare1.setText("• 最低提现金额为"+cashInfo.getRule().getGet_cash_fee_limit()+"元");
        String string= "• 提现消费率为"+cashInfo.getRule().getGet_cash_fee_rate()
                +"%，最低收取"+cashInfo.getRule().getGet_cash_fee_min()
                +"元";
        if(Integer.parseInt(cashInfo.getRule().getGet_cash_fee_max()) > 0){
            string += ",最高收取"+cashInfo.getRule().getGet_cash_fee_max()+"元";
        }
        declare2.setText(string);


    }

    @Override
    public void onNetChange(boolean available) {
        if(available && info == null){
            getCashInfo(false);
        }
    }

    @OnClick(R.id.getcash_commit)
    void onClick(View view){
        if(isRefreshing())return;
        if(!CommonUtils.isNetworkAvailable(mContext)){
            TS.show(mContext,"网络连接不可用");
        }
        if(cash.getText().toString().equals("")){
            TS.show(mContext,"请输入提现金额");
            return;
        }
        if(info == null){
            //如果info为空，则把两个请求压合到一起重新请求
            getCashInfo(true);
        }else{
            //如果info不为空，则先计算再请求提现接口
            checkFromInfo();
        }

    }

    /**
     * 根据配置计算相关信息
     *
     */
    public void checkFromInfo(){
        //计算
        //提现金额
        double toCash = Double.parseDouble("0"+cash.getText().toString().trim());
        if(toCash > Double.parseDouble(info.getCredit2())){
            TS.show(mContext,"余额不足");
            return;
        }else if(toCash < Double.parseDouble(info.getRule().getGet_cash_fee_limit())){
            TS.show(mContext,"提现金额不能小于最低提现金额");
            return;
        }
        //手续费
        double fee = 0;
        fee = 0.01*toCash * Double.parseDouble(info.getRule().getGet_cash_fee_rate());
        if(fee < Double.parseDouble(info.getRule().getGet_cash_fee_min())){
            fee = Double.parseDouble(info.getRule().getGet_cash_fee_min());
        }else {
            if(!info.getRule().getGet_cash_fee_max().equals("0")){
                if(fee > Double.parseDouble(info.getRule().getGet_cash_fee_max())){
                    fee =  Double.parseDouble(info.getRule().getGet_cash_fee_max());
                }
            }

        }

        //实际到账金额
        double realCash = toCash - fee;
        if(realCash<=0){
            TS.show(mContext,"无法提现");
            return;
        }
        //显示对话框
        showRefirmDialog(toCash,fee,realCash);
    }

    /**
     * 确认对话框
     * @param toCash
     * @param fee
     * @param realCash
     */
    private void showRefirmDialog(final double toCash, double fee, final double realCash) {
        new AlertDialog.Builder(mContext)
                .setMessage("提现金额"+toCash+"元，手续费"+format.format(fee)+"元，实际到账"+format.format(realCash)+"元，确定提现吗？")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getCash(toCash);
                    }
                }).show();
    }

    /**
     * 获取提现相关信息
     */
    private void getCashInfo(final boolean IsRequestCashing) {
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i",App.API_UNIACID+"");
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dytrade");
        map.put("op","getcash_config");
        map.put("token", (String) SP.get(mContext,"token",""));

        toSubscribe(apiManager.getApiService().getCashInfo(map).map(new HttpResultFunc<CashInfo>()),
                new Subscriber<CashInfo>() {
                    @Override
                    public void onStart() {
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
                    public void onNext(CashInfo cashInfo) {
                        hideRefresh();
                        if(cashInfo != null)info = cashInfo;
                        initData(cashInfo);
                        if(IsRequestCashing)checkFromInfo();
                    }
                });
    }


    /**
     * 提现请求
     * @param realCash
     */
    public void getCash(double realCash){
        //i="+API_UNIACID+"&c=entry&do=dytrade&op=getcash_submit&m=we7_wmall
        LinkedHashMap<String,String> cashmap = new LinkedHashMap<>();
        cashmap.put("i",App.API_UNIACID);
        cashmap.put("c","entry");
        cashmap.put("m","we7_wmall");
        cashmap.put("do","dytrade");
        cashmap.put("op","getcash_submit");
        cashmap.put("token", (String) SP.get(mContext,"token",""));
        cashmap.put("fee",realCash+"");

        toSubscribe(apiManager.getApiService().getCash(cashmap).map(new HttpResultFunc<Cash>()),
                new Subscriber<Cash>() {
                    @Override
                    public void onStart() {
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
                    public void onNext(Cash cash) {
                        hideRefresh();
                        getCashInfo(false);
                        Intent intent = new Intent(mContext, CashDetailActivity.class);
                        intent.putExtra("cashdetailId",cash.getGetcash_id());
                        startActivity(intent);
                    }
                }
        );
    }
}
