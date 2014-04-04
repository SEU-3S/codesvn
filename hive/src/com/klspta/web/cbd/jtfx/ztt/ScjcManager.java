package com.klspta.web.cbd.jtfx.ztt;

import javax.servlet.http.HttpServletRequest;

import com.klspta.base.AbstractBaseBean;

public class ScjcManager extends AbstractBaseBean{
    public void getScjc(){
        HttpServletRequest request = this.request;
        response(new ScjcData().getAllList(request));
    }
    public void updateScjc() {
        HttpServletRequest request = this.request;
        if (new ScjcData().updateScjc(request)) {
            response("{success:true}");
        } else {
            response("{success:false}");
        }
       
    }

}
