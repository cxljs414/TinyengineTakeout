package com.taoQlegoupeisongduanandroid.delivery.bean;

import android.content.Context;

import com.google.gson.Gson;
import com.taoQlegoupeisongduanandroid.delivery.utils.util.SP;

/**
 * Created by changxing on 2016/7/27.
 */
public class User {


    /**
     * id : 10
     * uniacid : 1
     * title : 杨慧锋
     * nickname : 灯火阑珊的
     * openid : o3nuitws-cow0IwCBnALUQChYEZc
     * avatar : http://wx.qlogo.cn/mmopen/6Fz3aS6k35YD7gaLqD42qJ5xND4VAqGXUFu9oL80IselYY9oRJ1EvVWfNQlHhgJAyylZ2uWqIWPRjXvTtibPIp4vNGfE4mqoC/132
     * mobile : 13453222285
     * password : 7350567eb82ec8da5757e596b394511d
     * salt : cl8Vl0
     * token : l0258gL8xs5VvH2E6tlIG0lS80z21G82
     * sex : 男
     * age : 20
     * addtime : 1463991444
     * credit1 : 0.00
     * credit2 : 105.10
     */

    private String id;
    private String uniacid;
    private String title;
    private String nickname;
    private String openid;
    private String avatar;
    private String mobile;
    private String password;
    private String salt;
    private String token;
    private String sex;
    private String age;
    private String addtime;
    private String credit1;
    private String credit2;


    /**
     * 将类信息保存本地
     * @param user
     */
    public static void saveUsertoSp(Context context,User user){
        SP.put(context,"id",user.getId());
        SP.put(context,"uniacid",user.getUniacid());
        SP.put(context,"title",user.getTitle());
        SP.put(context,"nickname",user.getNickname());
        SP.put(context,"openid",user.getOpenid());
        SP.put(context,"avatar",user.getAvatar());
        SP.put(context,"mobile",user.getMobile());
        SP.put(context,"password",user.getPassword());
        SP.put(context,"salt",user.getSalt());
        SP.put(context,"token",user.getToken());
        SP.put(context,"sex",user.getSex());
        SP.put(context,"age",user.getAge());
        SP.put(context,"addtime",user.getAddtime());
        SP.put(context,"credit1",user.getCredit1());
        SP.put(context,"credit2",user.getCredit2());
    }

    public static User objectFromData(String str) {

        return new Gson().fromJson(str, User.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniacid() {
        return uniacid;
    }

    public void setUniacid(String uniacid) {
        this.uniacid = uniacid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getCredit1() {
        return credit1;
    }

    public void setCredit1(String credit1) {
        this.credit1 = credit1;
    }

    public String getCredit2() {
        return credit2;
    }

    public void setCredit2(String credit2) {
        this.credit2 = credit2;
    }
}