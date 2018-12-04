package com.taoQlegoupeisongduanandroid.delivery.module.fragment.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.WorkStatusBean;
import com.taoQlegoupeisongduanandroid.delivery.module.fragment.BaseFragment;
import com.taoQlegoupeisongduanandroid.delivery.module.init.AcountDetailActivity;
import com.taoQlegoupeisongduanandroid.delivery.module.init.GetCashActivity;
import com.taoQlegoupeisongduanandroid.delivery.module.init.MyAccountActivity;
import com.taoQlegoupeisongduanandroid.delivery.module.init.OrderStatisticsActivity;
import com.taoQlegoupeisongduanandroid.delivery.module.init.SettingActivity;
import com.taoQlegoupeisongduanandroid.delivery.service.NetChangeListener;
import com.taoQlegoupeisongduanandroid.delivery.utils.customview.CircleImageView;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.Subscriber;

/**
 * Created by changxing on 2016/7/27.
 */
public class MineFragment extends BaseFragment implements NetChangeListener{
    private Context mContext ;
    private static MineFragment instance;
    private BitmapUtils bitmapUtils;

    @BindView(R.id.mine_userimage)CircleImageView userImage;
    @BindView(R.id.mine_username)TextView userName;
    @BindView(R.id.mine_restcheck)SwitchCompat restCheck;
    @BindView(R.id.mine_resttitle)TextView restStatus;

    String logs;
    public static MineFragment getInstance(){
        if(instance == null)instance = new MineFragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        bitmapUtils = new BitmapUtils(mContext);
    }

    @Override
    protected View initContentView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_mine,null);
        ButterKnife.bind(this,view);
        setRefreshEnable(false);
        userName.setText((String)SP.get(mContext,"nickname",""));
        loadUserImage();
        restCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeWorkStatus(isChecked);
            }
        });
        return view;
    }

    public void changeWorkStatus(boolean Isworking){
        //i="+API_UNIACID+"&c=entry&do=dyset&op=work_status&m=we7_wmall
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyset");
        map.put("op","work_status");
        map.put("token", (String) SP.get(mContext,"token",""));
        map.put("work_status",Isworking ? "1":"0");
        toSubscribe(apiManager.getApiService().workStatus(map).map(new HttpResultFunc<WorkStatusBean>()),
                new Subscriber<WorkStatusBean>() {
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
                    public void onNext(WorkStatusBean workStatusBean) {
                        hideRefresh();
                        restStatus.setText(workStatusBean.getWork_status_cn());
                        Set<String> tags = new HashSet<String>();
                        for(int i=0;i<workStatusBean.getTags().size();i++){
                            tags.add(workStatusBean.getTags().get(i));
                        }
                        //设置jpush别名
                        JPushInterface.setAliasAndTags(
                                mContext, workStatusBean.getAlias(),
                                tags,
                                new TagAliasCallback() {
                                    @Override
                                    public void gotResult(int code, String s, Set<String> set) {
                                        switch (code) {
                                            case 0:
                                                logs = "Set tag and alias success";
                                                System.out.println( logs);
                                                // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                                break;
                                            case 6002:
                                                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                                                System.out.println( logs);
                                                // 延迟 60 秒来调用 Handler 设置别名
                                                //mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                                                break;
                                            default:
                                                logs = "Failed with errorCode = " + code;
                                                System.out.println( logs);
                                        }
                                    }
                                });
                    }
                });
    }

    private void loadUserImage() {
        String imageUrl = (String) SP.get(mContext,"avatar","");
        if(!imageUrl.equals("")){
            bitmapUtils.display(userImage, imageUrl, new BitmapLoadCallBack<CircleImageView>() {
                @Override
                public void onLoadCompleted(CircleImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                    container.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadFailed(CircleImageView container, String uri, Drawable drawable) {
                    container.setImageResource(R.mipmap.image_user);
                }
            });
        }
    }

    @OnClick({R.id.mine_acountdetail,R.id.mine_getcash,R.id.mine_myacount,
            R.id.mine_restcheck,R.id.mine_setting,R.id.mine_userimage,
            R.id.mine_orderstatistics
    })
    void onClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.mine_userimage:

                break;
            case R.id.mine_myacount:
                intent = new Intent(mContext,MyAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_orderstatistics:
                intent = new Intent(mContext,OrderStatisticsActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_getcash:
                intent = new Intent(mContext, GetCashActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_acountdetail:
                intent = new Intent(mContext, AcountDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_setting:
                intent = new Intent(mContext, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onNetChange(boolean available) {
        if(available){
            loadUserImage();
            restCheck.setEnabled(true);
            changeWorkStatus(restCheck.isChecked());
        }else{
            restCheck.setEnabled(false);
        }

    }
}
