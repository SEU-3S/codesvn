package com.klspta.base.util.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.api.IWKTUtil;
import com.klspta.base.wkt.Polygon;
import com.klspta.base.wkt.Ring;

public final class WKTUtil extends AbstractBaseBean   implements IWKTUtil {

    private WKTUtil() {
    }

    public static IWKTUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception();
        }
        return new WKTUtil();
    }

    @Override
    public Polygon stringToWKTObject(String string) {
        Polygon plgon = new Polygon();
        try {
            JSONObject json = UtilFactory.getJSONUtil().jsonToObject(string);
            JSONArray rings = json.getJSONArray("rings");
            for (int i = 0; i < rings.size(); i++) {
                JSONArray jring = rings.getJSONArray(i);
                Ring ring = new Ring();
                for (int j = 0; j < jring.size(); j++) {
                    JSONArray point = jring.getJSONArray(j);
                    ring.putPoint(point.getDouble(0), point.getDouble(1));
                }
                plgon.addRing(ring);
            }
        } catch (Exception e) {
        	  responseException(this,"stringToWKTObject", "300004", e);
            e.printStackTrace();
        }
        return plgon;
    }

    public static void main(String[] args) {
        WKTUtil util = new WKTUtil();
        String wkt = "";//request.getParameter("geometry");
        wkt = "{\"type\":\"polygon\",\"rings\":[[[40446762.9465,3591634.0715999994],[40446835.7766,3591622.4068],[40446909.5786,3591607.9586999994],[40446969.717,3591596.546],[40446981.6202,3591594.6547999997],[40447014.9606,3591589.3575],[40447016.0948,3591587.701300001],[40447013.3025,3591571.8082],[40447012.1508,3591537.5703999996],[40447002.1697,3591470.4637],[40446998.4646,3591452.533500001],[40446995.8432,3591436.139799999],[40446994.1353,3591434.3160999995],[40446991.6903,3591434.6993000004],[40446976.6037,3591402.8836000003],[40446942.122,3591340.436899999],[40446924.9065,3591349.319],[40446869.8082,3591376.1602],[40446811.654699996,3591402.8817],[40446786.063,3591412.8461000006],[40446787.4178,3591425.2993],[40446803.1688,3591452.1334000006],[40446804.099700004,3591456.531300001],[40446806.047299996,3591476.615700001],[40446804.2704,3591507.261499999],[40446804.6093,3591518.7957000006],[40446806.981,3591546.909499999],[40446809.0987,3591563.9306000005],[40446812.5682,3591578.5624],[40446812.909,3591592.0512000006],[40446789.6974,3591597.8958],[40446761.687,3591604.6619000006],[40446761.5065,3591616.3684],[40446762.9465,3591634.0715999994]]],\"_ring\":0,\"spatialReference\":{\"wkid\":2364}}";
        Polygon p = util.stringToWKTObject(wkt);
        System.out.println(p.toWKT());
    }

}
