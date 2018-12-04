package com.taoQlegoupeisongduanandroid.delivery.utils.util;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by changxing on 2016/7/27.
 */
public class Sign {
    public static Map<String,String> createSign(SortedMap<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        int  i = 0;
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v &&
                    !"".equals(v) &&
                    !"sign".equals(k)) {
                if(i == (es.size()-1))
                    sb.append(k + "=" + v);
                else
                    sb.append(k + "=" + v + "&");
            }
            i++;
        }
        String sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
        parameters.put("sign",sign);
        return parameters;
    }


    /**
     * 将对象转换成map
     * @param thisObj
     * @return
     */
    public static SortedMap getValue(Object thisObj)
    {
        SortedMap map = new TreeMap();
        Class c;
        try
        {
            c = Class.forName(thisObj.getClass().getName());
            Method[] m = c.getMethods();
            for (int i = 0; i < m.length; i++)
            {
                String method = m[i].getName();
                if (method.startsWith("get"))
                {
                    try{
                        Object value = m[i].invoke(thisObj);
                        if (value != null)
                        {
                            String key=method.substring(3);
                            key=key.substring(0,1).toUpperCase()+key.substring(1);
                            map.put(method, value);
                        }
                    }catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("error:"+method);
                    }
                }
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }
        return map;
    }

    public static void  main(String[] args){
        /*SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("name", "小三");
        parameters.put("age", "20");
        String mySign = createSign("utf-8",parameters);
        System.out.println("sign:"+mySign);*/
        //mySign : C9DE6F8D1626C094A26857DE0F8351E6

        /*SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("age", "20");
        parameters.put("name", "小三");
        parameters.put("key", "5555555");
        String mySign = createSign("utf-8",parameters);
        System.out.println("sign:"+mySign);*/
    }
}
