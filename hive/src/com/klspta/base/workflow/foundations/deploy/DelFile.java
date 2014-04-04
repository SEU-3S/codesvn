package com.klspta.base.workflow.foundations.deploy;

import java.io.File;

public class DelFile {

	private static DelFile delFile;

	/**
	 * <br>
	 * Description:获取实例 <br>
	 * Author:王峰 <br>
	 * Date:2011-6-27
	 * 
	 * @return
	 */
	public static DelFile getInstance() {
		if (delFile == null) {
			delFile = new DelFile();
		}
		return delFile;
	}

	public void deleteAllFile(File file) {
		this.deleteFile(file);
		file.delete();
	}

	private void deleteFile(File file) {
		File[] temp = file.listFiles();
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].isDirectory()) {
				if (temp[i].listFiles().length != 0)
					this.deleteFile(temp[i]);
				this.deleteDir(temp[i]);
				temp[i].delete();
			} else {
				temp[i].delete();
			}
		}
	}

	private void deleteDir(File file) {
		if (file.listFiles().length == 0)
			file.getAbsoluteFile().delete();
	}

}
