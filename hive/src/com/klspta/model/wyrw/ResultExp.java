package com.klspta.model.wyrw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.server.UID;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 需要在conf下的applicationContext-bean.xml中，增加配置信息：
 * <bean name="simpleExample" class="com.klspta.model.SimpleExample" scope="prototype"/>
 * @author wang
 *
 */
@Component
public class ResultExp extends AbstractBaseBean {
    /**
     * 临时位置
     */
    private String tempPath;

    public ResultExp() {
        tempPath = getTemp();
    }

    /**
     * 
     * <br>Description:任务成果导出
     * <br>Author:陈强峰
     * <br>Date:2012-8-13
     */
    public void expResult() {
        String ids = request.getParameter("ids");
        String[] ywids;
        if (ids.contains("@")) {
            ywids = ids.split("@");
        } else {
            ywids = new String[] { ids };
        }
        String file_id = new UID().toString().replaceAll(":", "-");
        String filepath = tempPath + file_id + "//exp";
        File dirs = new File(filepath);
        if (!dirs.isFile()) {
            dirs.mkdirs();
        }
        resultToTemp(filepath, ywids);
        resultToZip(tempPath + file_id, tempPath + file_id);
        response(tempPath + file_id + ".zip");
    }

    /**
     * 
     * <br>Description:数据信息写入临时位置
     * <br>Author:陈强峰
     * <br>Date:2012-8-13
     * @param filepath
     * @param guids
     */
    private void resultToTemp(String filepath, String[] guids) {
        for (int i = 0; i < guids.length; i++) {
            //写入文本
            String folder = filepath + "//" + guids[i];
            File dir = new File(folder);
            dir.mkdirs();
            String sql = "select GUID,XMMC,DWMC,RWLX,WFDD,RWMS,SFWF,XCQKMS,SJZDMJ,XCR,XCRQ,CJZB,JWZB,GPSID from WY_DEVICE_DATA where guid=?";
            List<Map<String, Object>> list = query(sql, YW, new Object[] { guids[i] });
            OutputStreamWriter writer = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(folder + "//" + guids[i] + ".txt"),
                        "UTF-8");
                writer.write(new String(UtilFactory.getJSONUtil().objectToJSON(list).getBytes("UTF-8"),
                        "UTF-8"));
                writer.flush();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //附件下载
            downloadByGuid(guids[i], folder + "//");
        }
    }

    /**
     * 
     * <br>Description:根据guid 下载附件
     * <br>Author:陈强峰
     * <br>Date:2012-8-13
     * @param guid
     * @param ftpmap
     * @param folder
     */
    private void downloadByGuid(String guid, String folder) {
        String sql = "select file_id,file_name,file_path from atta_accessory where yw_guid=?";
        List<Map<String, Object>> list = query(sql, CORE, new Object[] { guid });
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> accessory = list.get(i);
            String remoteFileName = accessory.get("file_path").toString();
            String file_name = accessory.get("file_name").toString();
            try {
                UtilFactory.getFtpUtil().downloadFile(remoteFileName, folder + "//" + file_name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * <br>Description:成果压缩
     * <br>Author:陈强峰
     * <br>Date:2012-8-13
     * @param zipPath
     * @param path
     */
    private void resultToZip(String zipPath, String path) {
        UtilFactory.getZIPUtil().zip(zipPath + ".zip", path);
    }

    /**
     * 
     * <br>
     * Description:获取临时路径 <br>
     * Author:陈强峰 <br>
     * Date:2012-2-24
     * 
     * @return
     */
    private String getTemp() {
        String temppath = "";
        String sql = "select t.child_name from public_code t where t.id='TEMPFILEPATH' and t.in_flag='1'";
        List<Map<String, Object>> list = query(sql, YW);
        if (list.size() > 0) {
            temppath = list.get(0).get("child_name").toString();
        }
        return temppath;
    }
}
