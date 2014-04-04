package com.klspta.web.cbd.dtjc.trzqk;

public class TrzqkThread implements Runnable {

	@Override
	public void run() {
		AbstractTrzqkCalculate trzqkCalculate = new TrzqkCalculate();
		trzqkCalculate.calCulateAll();
		//Thread.currentThread();
		//Thread.currentThread().stop();
	}
	
}
