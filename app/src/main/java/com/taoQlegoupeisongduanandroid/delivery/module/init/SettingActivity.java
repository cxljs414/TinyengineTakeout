package com.taoQlegoupeisongduanandroid.delivery.module.init;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.taoQlegoupeisongduanandroid.delivery.R;
import com.taoQlegoupeisongduanandroid.delivery.app.App;
import com.taoQlegoupeisongduanandroid.delivery.bean.UpdateBean;
import com.taoQlegoupeisongduanandroid.delivery.module.base.BaseActivity;
import com.taoQlegoupeisongduanandroid.delivery.utils.http.RequestSubscriber;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.setting_voicecheck)SwitchCompat voiceCheck;
    @BindView(R.id.setting_vibratecheck)SwitchCompat vibrateCheck;
    @BindView(R.id.setting_versiontip)TextView versionTip;
    @BindView(R.id.setting_version)TextView versionCode;

    String updateUrl = "";
    @Override
    protected void initConentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        App.getInstance().addHomeTop(this);
        setToolBar(R.string.title_activity_setting);
        initView();
    }

    private void initView() {
        voiceCheck.setChecked((boolean)SP.get(mContext,"voiceNotice",true));
        vibrateCheck.setChecked((boolean)SP.get(mContext,"vibrateNotice",true));
        versionCode.setText("版本 v"+getVersionName());
        checkUpdate(false);

        voiceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SP.put(mContext,"voiceNotice",isChecked);
            }
        });

        vibrateCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SP.put(mContext,"vibrateNotice",isChecked);
            }
        });

    }

    /**
     * 检查更新
     */
    private void checkUpdate(final boolean IsClick) {
        //i="+API_UNIACID+"&c=entry&do=dyset&op=update&m=we7_wmall
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("i", App.API_UNIACID);
        map.put("c","entry");
        map.put("m","we7_wmall");
        map.put("do","dyset");
        map.put("op","update");
        map.put("token", (String) SP.get(mContext,"token",""));
        toSubscribe(apiManager.getApiService().checkUpdate(map).map(new HttpResultFunc<UpdateBean>()),
                new RequestSubscriber<UpdateBean>(this,this){
                    @Override
                    public void onNext(UpdateBean o) {
                        super.onNext(o);
                        try {
                            int versionCode = Integer.parseInt(o.getVersion());
                            if(versionCode > getVersionCode()){
                                updateUrl = o.getDownloadUrl();
                                versionTip.setText("有新版本");
                                versionTip.setTextColor(getResources().getColor(R.color.red));
                                if(IsClick){
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(updateUrl));
                                    startActivity(intent);
                                }
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
    }

    @OnClick({R.id.setting_logout,R.id.setting_changepassword,R.id.setting_versionupdate})
    void onClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.setting_logout:
                SP.clear(mContext);
                app.exit();
                JPushInterface.stopPush(this);
                NotificationManager nm =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                nm.cancelAll();
                intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.setting_changepassword:
                intent = new Intent(mContext,ChangePasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.setting_versionupdate:
                if(!updateUrl.equals("")){
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(updateUrl));
                    startActivity(intent);
                }else{
                    checkUpdate(true);
                }

                break;
        }
    }
}
