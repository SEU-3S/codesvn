package com.klspta.base.workflow.handler.ZFJC_PTANCC;

import java.util.HashMap;

import com.klspta.base.workflow.handler.api.IWfHandler;


public class 决定处理意见 implements IWfHandler {
    public 决定处理意见() {
        System.out.println("aasdasdsad");
    }

    public void preDoNext(HashMap<String, Object> map) {
        System.out.println("在转发前");
        System.out.println("在转发前");
        System.out.println("在转发前");
        System.out.println("在转发前");
        System.out.println("在转发前");
        System.out.println("在转发前");
    }

    public void afterDoNext(HashMap<String, Object> map) {
        System.out.println("转发后");
        System.out.println("转发后");
        System.out.println("转发后");
        System.out.println("转发后");
        System.out.println("转发后");
        System.out.println("转发后");
    }

    public void iDoIt(HashMap<String, Object> map) {
        // TODO Auto-generated method stub

    }

	@Override
	public void afterRollBack(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preRollBack(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

}
