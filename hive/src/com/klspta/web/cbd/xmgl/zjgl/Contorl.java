package com.klspta.web.cbd.xmgl.zjgl;

public class Contorl {
	// 资金流入
	public static String ZJLR = "ZJLR";
	// Ⅱ.资金支出\2.1 一级开发支出\前期费用\拆迁费用\市政费用\财务费用\管理费用\筹融资金返还\ 其他支出\Ⅱ.资金支出
	public static String ZJZC = "ZJZC";
	public static String YJKFZC = "YJKFZC";
	public static String QQFY = "QQFY";
	public static String CQFY = "CQFY";
	public static String SCFY = "SCFY";
	public static String SZFY = "SZFY";
	public static String CWFY = "CWFY";
	public static String GLFY = "GLFY";
	public static String CRZJFH = "CRZJFH";
	public static String QTZC = "QTZC";

	
	private String yw_guid;
	private String year;
	private String type[];
	private String Edit[];
	private String rolename;
	/***************************************************************************
	 * 资金流入状态标识,编辑状态
	 */
	private String zjlr_status = "n";
	private String zjlr_editor = "n";

	private ZjglThread ZJZCThread;
	private ZjglThread YIKFZCThread;
	private ZjglThread QQFYThread;
	private ZjglThread CQFYThread;
	private ZjglThread SZFYThread;
	private ZjglThread CWFYThread;
	private ZjglThread GLFYThread;
	private ZjglThread CRZJFHThread;
	private ZjglThread QTZCHThread;
	private ZjglThread SCFYThread;

	private StringBuffer buffer = new StringBuffer();

	/***************************************************************************
	 * 
	 * <br>
	 * Description:构造函数1 <br>
	 * Author:朱波海 <br>
	 * Date:2014-1-16
	 * 
	 * @param yw_guid
	 * @param year
	 */
	public Contorl(String yw_guid, String year) {
		this.yw_guid = yw_guid;
		this.year = year;
		new ZjglData().setMX(this.yw_guid, this.year);
		Init();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:构造函数2 <br>
	 * Author:朱波海 <br>
	 * Date:2014-1-16
	 * 
	 * @param yw_guid
	 * @param year
	 * @param type
	 * @param Edit
	 */
	public Contorl(String yw_guid, String year, String[] type, String[] Edit,String rolename) {
		this.yw_guid = yw_guid;
		this.year = year;
		this.type = type;
		this.Edit = Edit;
		this.rolename = rolename;
		new ZjglData().setMX(this.yw_guid, this.year);
		Init(this.type, this.Edit);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:初始化编辑new <br>
	 * Author:朱波海 <br>
	 * Date:2014-1-16
	 */
	public void Init(String[] type, String[] Edit) {
		for (int i = 0; i < type.length; i++) {
			if (type[i].equals(ZJLR)) {
				zjlr_status = "y";
				zjlr_editor = Edit[i];
			} else {
				if (type[i].equals(ZJZC)) {
					ZJZCThread = new ZjglThread(this.yw_guid,  this.year,ZJZC,
							Edit[i],this.rolename);
					Thread thread = new Thread(ZJZCThread);
					thread.start();
				} else if (type[i].equals(YJKFZC)) {
					YIKFZCThread = new ZjglThread(this.yw_guid,  this.year,YJKFZC,
							Edit[i],this.rolename);
					Thread thread = new Thread(YIKFZCThread);
					thread.start();
				} else if (type[i].equals(QQFY)) {
					QQFYThread = new ZjglThread(this.yw_guid,  this.year,QQFY,
							Edit[i],this.rolename);
					Thread thread = new Thread(QQFYThread);
					thread.start();
				} else if (type[i].equals(CQFY)) {
					CQFYThread = new ZjglThread(this.yw_guid,  this.year,CQFY,
							Edit[i],this.rolename);
					Thread thread = new Thread(CQFYThread);
					thread.start();
				} else if (type[i].equals(SZFY)) {
					SZFYThread = new ZjglThread(this.yw_guid,  this.year,SZFY,
							Edit[i],this.rolename);
					Thread thread = new Thread(SZFYThread);
					thread.start();
				} else if (type[i].equals(SCFY)) {
					SCFYThread = new ZjglThread(this.yw_guid,  this.year,SCFY,
							Edit[i],this.rolename);
					Thread thread = new Thread(SCFYThread);
					thread.start();
				}else if (type[i].equals(CWFY)) {
					CWFYThread = new ZjglThread(this.yw_guid,  this.year,CWFY,
							Edit[i],this.rolename);
					Thread thread = new Thread(CWFYThread);
					thread.start();
				} else if (type[i].equals(GLFY)) {
					GLFYThread = new ZjglThread(this.yw_guid,  this.year,GLFY,
							Edit[i],this.rolename);
					Thread thread = new Thread(GLFYThread);
					thread.start();
				} else if (type[i].equals(CRZJFH)) {
					CRZJFHThread = new ZjglThread(this.yw_guid,  this.year,CRZJFH,
							Edit[i],this.rolename);
					Thread thread = new Thread(CRZJFHThread);
					thread.start();
				} else if (type[i].equals(QTZC)) {
					QTZCHThread = new ZjglThread(this.yw_guid,this.year,QTZC,
							Edit[i],this.rolename);
					Thread thread = new Thread(QTZCHThread);
					thread.start();
				}
			}
		}
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:初始化old -----全部编辑<br>
	 * Author:朱波海 <br>
	 * Date:2014-1-16
	 */
	public void Init() {
		ZJZCThread = new ZjglThread(this.yw_guid, ZJZC, this.year,this.rolename);
		Thread thread = new Thread(ZJZCThread);
		thread.start();
		YIKFZCThread = new ZjglThread(this.yw_guid, YJKFZC, this.year,this.rolename);
		Thread thread1 = new Thread(YIKFZCThread);
		thread1.start();
		QQFYThread = new ZjglThread(this.yw_guid, QQFY, this.year,this.rolename);
		Thread thread2 = new Thread(QQFYThread);
		thread2.start();
		CQFYThread = new ZjglThread(this.yw_guid, CQFY, this.year,this.rolename);
		Thread thread3 = new Thread(CQFYThread);
		thread3.start();
		SCFYThread = new ZjglThread(this.yw_guid, SCFY, this.year,this.rolename);
		Thread thread4 = new Thread(SCFYThread);
		thread4.start();
		SZFYThread = new ZjglThread(this.yw_guid, SZFY, this.year,this.rolename);
		Thread thread5 = new Thread(SZFYThread);
		thread5.start();
		CWFYThread = new ZjglThread(this.yw_guid, CWFY, this.year,this.rolename);
		Thread thread6 = new Thread(CWFYThread);
		thread6.start();
		GLFYThread = new ZjglThread(this.yw_guid, GLFY, this.year,this.rolename);
		Thread thread7 = new Thread(GLFYThread);
		thread7.start();
		CRZJFHThread = new ZjglThread(this.yw_guid, CRZJFH, this.year,this.rolename);
		Thread thread8 = new Thread(CRZJFHThread);
		thread8.start();
		QTZCHThread = new ZjglThread(this.yw_guid, QTZC, this.year,this.rolename);
		Thread thread9 = new Thread(QTZCHThread);
		thread9.start();

	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:获取模块总入口1-old <br>
	 * Author:朱波海 <br>
	 * Date:2014-1-16
	 * 
	 * @return
	 */

	public String getTextMode() {
		// 是否项目初始化
		new ZjglData().setMX(this.yw_guid, this.year);
		StringBuffer title = ZjglBuild.buildTitle(this.year);
		buffer.append(title);
		// 资金流入
		StringBuffer zjlr = TrFactory.getmod(this.yw_guid, this.year,this.rolename);
		buffer.append(zjlr);
		buffer.append(ZJZCThread.getBuffer());
		buffer.append(YIKFZCThread.getBuffer());
		buffer.append(QQFYThread.getBuffer());
		buffer.append(CQFYThread.getBuffer());
		buffer.append(SCFYThread.getBuffer());
		buffer.append(SZFYThread.getBuffer());
		buffer.append(CWFYThread.getBuffer());
		buffer.append(GLFYThread.getBuffer());
		buffer.append(CRZJFHThread.getBuffer());
		buffer.append(QTZCHThread.getBuffer());
		return this.buffer.toString();
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:获取模块总入口2-new <br>
	 * Author:朱波海 <br>
	 * Date:2014-1-16
	 * 
	 * @return
	 */

	public String getTextMode_new() {
		// 是否项目初始化
		
		StringBuffer title = ZjglBuild.buildTitle(this.year);
		buffer.append(title);
		// 资金流入
		if (zjlr_status.equals("y")) {
			if (zjlr_editor.equals("y")) {
				StringBuffer zjlr = TrFactory.getmod(this.yw_guid, this.year,this.rolename);
				buffer.append(zjlr);
			} else {
				StringBuffer zjlr = TrFactory.getmod_view(this.yw_guid,
						this.year,this.rolename);
				buffer.append(zjlr);
			}

		}
		for (int i = 0; i < type.length; i++) {
			if (!type[i].equals(ZJLR)) {
				buffer.append(getZjglThread(type[i]).getBuffer());
			}
		}
		return this.buffer.toString();
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:获取线程对象 <br>
	 * Author:朱波海 <br>
	 * Date:2014-1-16
	 * 
	 * @param type
	 * @return
	 */
	
	public ZjglThread getZjglThread(String type) {
		if (type.equals(ZJZC)) {
			return ZJZCThread;
		} else if (type.equals(YJKFZC)) {
			return YIKFZCThread;
		} else if (type.equals(QQFY)) {
			return QQFYThread;
		} else if (type.equals(CQFY)) {
			return CQFYThread;
		} else if (type.equals(SCFY)) {
			return SCFYThread;
		} else if (type.equals(SZFY)) {
			return SZFYThread;
		} else if (type.equals(CWFY)) {
			return CWFYThread;
		} else if (type.equals(GLFY)) {
			return GLFYThread;
		} else if (type.equals(CRZJFH)) {
			return CRZJFHThread;
		} else if (type.equals(QTZC)) {
			return QTZCHThread;
		} 
		return null;

	}
}
