package com.klspta.web.jizeNW.dtxc.cgdr;

import java.io.File;
import java.rmi.server.UID;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.bean.xzqhutil.XzqhBean;
import com.klspta.base.wkt.Point;
import com.klspta.base.wkt.Polygon;
import com.klspta.base.wkt.Ring;
import com.klspta.console.ManagerFactory;
import com.klspta.console.user.User;

/**
 * 
 * <br>Title:pad成果回传
 * <br>Description:
 * <br>Author:陈强峰
 * <br>Date:2012-7-2
 */
@Component
public class ResultImp extends AbstractBaseBean {
    /**
     * 临时位置
     */
    private String tempPath;
    private String userName;
    private String userXzq;
    private String userId;
    private String xzqId;
    
    public ResultImp() {
        tempPath = getTemp();
    }

    /**
     * 
     * <br>Description:成果数据保存
     * <br>Author:陈强峰
     * <br>Date:2012-7-2
     */
    public void saveData() {
        String uid=request.getParameter("userid");
        try {
            User user=ManagerFactory.getUserManager().getUserWithId(uid);
            userName=user.getFullName();
            userId=user.getUserID();
            String xzqh =user.getXzqh();
            XzqhBean xzq=UtilFactory.getXzqhUtil().getBeanById(xzqh);
            userXzq =xzq.getCatonname();
            xzqId=xzq.getCatoncode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String zipPath = tempFile();
        if (zipPath != null) {
            File zipFile = new File(zipPath);
            String folderpath = tempPath + zipFile.getParentFile().getName();
            //解压缩
            UtilFactory.getZIPUtil().unZip(zipPath, folderpath);
            //xml读取,并返回成果精简信息
            int count = importData(folderpath + "//exp");
            //上传附件
            importAccessoryField(folderpath + "//exp");
            try {
				response("{success:true,msg:\"" + count + "\"}");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            response("{success:false}");
        }
    }

    /**
     * 
     * <br>Description:临时文件
     * <br>Author:陈强峰
     * <br>Date:2012-7-4
     * @return
     */
    private String tempFile() {
        try {
            List<String> list = UtilFactory.getFileUtil().upload(request, 0, 0);
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        temppath = UtilFactory.getConfigUtil().getShapefileTempPathFloder();
        return temppath;
    }

    /**
     * 
     * <br>Description:文本信息入库 附件上传
     * <br>Author:陈强峰
     * <br>Date:2012-7-4
     * @param path
     */
    private int importData(String path) {
        File f = new File(path);
        File[] files = f.listFiles();
        int count = 0;
        for (int i = 0; i < files.length; i++) {
            File file = new File(files[i].getAbsolutePath());
            File[] childList = file.listFiles();
            for (int j = 0; j < childList.length; j++) {
                String str = childList[j].getAbsolutePath();
                String fileName = str.substring(str.lastIndexOf("\\")+1);
                if (fileName.toLowerCase().endsWith("xml") && (fileName.contains("XC")||fileName.contains("WP"))) {
                    count += parseXml(str);
                }else if(fileName.toLowerCase().endsWith("xml")){
                    parseXcrzXml(str);
                }
            }
        }
        return count;
    }

    /**
     * 
     * <br>Description:解析xml信息入库
     * <br>Author:陈强峰
     * <br>Date:2013-7-5
     */
    private int parseXml(String xmlPath) {
        int count = 0;
        String yw_guid = "";
        try {
            File f = new File(xmlPath);
            SAXReader sax = new SAXReader();
            Document document = sax.read(f);
            Element root = document.getRootElement();
            Element baselist = (Element) root.selectNodes("/WYHC/base").get(0);
            yw_guid = baselist.element("YW_GUID").getText();
            String ydsj = baselist.element("YDSJ").getText();
            String yddw = baselist.element("YDDW").getText();
            String mj = baselist.element("MJ").getText();
            String zb = baselist.element("ZB").getText();
            String jsqk = baselist.element("JSQK").getText();
            String wfwglx = baselist.element("WFWGLX").getText();
            String dfccqk = baselist.element("DFCCQK").getText();
            String xcms = baselist.element("XCMS").getText();
            String hcrq = baselist.element("HCRQ").getText();
            String spsj = baselist.element("SPSJ").getText();
            String spxmmc = baselist.element("SPXMMC").getText();
            String spwh = baselist.element("SPWH").getText();
            String gdsj = baselist.element("GDSJ").getText();
            String gdxmmc = baselist.element("GDXMMC").getText();
            String gdwh = baselist.element("GDWH").getText();
            String ydqk = baselist.element("YDQK").getText();
            String status = baselist.element("STATUS").getText();
            String ygspmj = baselist.element("YGSPMJ").getText();
            String ygspbl = baselist.element("YGSPBL").getText();
            String yggdmj = baselist.element("YGGDMJ").getText();
            String yggdbl = baselist.element("YGGDBL").getText();
            String nyd = baselist.element("NYD").getText();
            String gengd = baselist.element("GENGD").getText();
            String jsyd = baselist.element("JSYD").getText();
            String wlyd = baselist.element("WLYD").getText();
            String fhgh = baselist.element("FHGH").getText();
            String bfhgh = baselist.element("BFHGH").getText();
            String zyjbnt = baselist.element("ZYJBNT").getText();
            String xmmc = baselist.element("XMMC").getText();
            String pfwh = baselist.element("PFWH").getText();
            String pzsj = baselist.element("PZSJ").getText();
            String yxjsq = baselist.element("YXJSQ").getText();
            String ytjjsq = baselist.element("YTJJSQ").getText();
            String xzjsq = baselist.element("XZJSQ").getText();
            String jzjsq = baselist.element("JZJSQ").getText();
            String xcr = baselist.element("XCR").getText();
            String xcdw = baselist.element("XCDW").getText();
            String ordertime = baselist.element("ORDERTIME").getText();
            
            /*
             * 鸡泽个性化需求，当导入内网人员为县级人员时，取出xml中的impuserid,impxzqbm,impxzq,impuser
             */
            String impuserid = "";
            String impxzqbm = "";
            String impxzq = "";
            String impuser = ""; 
            if("130431".equals(xzqId)){
            	impuserid = baselist.element("IMPUSERID").getText();
                impxzqbm = baselist.element("IMPXZQBM").getText();
                impxzq = baselist.element("IMPXZQ").getText();
                impuser = baselist.element("IMPUSER").getText();
            }
            
            //坐标
            Element zblist = (Element) root.selectNodes("/WYHC/zb").get(0);
            String jwzb = zblist.element("JWZB").getText();
            String pmzb = zblist.element("PMZB").getText();
            String padid = root.element("pad").getText();
            String shi = root.element("shi").getText();
            String xian = root.element("xian").getText();
            //入库
            String sql = "select yw_guid from dc_ydqkdcb where yw_guid=?";
            List<Map<String, Object>> list = query(sql, YW, new Object[] { yw_guid });
            if (list.size() == 0) {
                sql = "insert into dc_ydqkdcb(yw_guid, ydsj, yddw, mj, zb, jsqk, wfwglx, dfccqk,"
                        + "xcms, hcrq, spsj, spxmmc, spwh, gdsj, gdxmmc, gdwh, ydqk, status, ygspmj, ygspbl,"
                        + "yggdmj, yggdbl, nyd, gengd, jsyd, wlyd, fhgh, bfhgh, zyjbnt, xmmc, pfwh, pzsj, yxjsq,"
                        + "ytjjsq, xzjsq, jzjsq, xcr, xcdw, ordertime, jwzb, pmzb, shi, xian, padid,impuser,impxzq,impuserid,impxzqbm) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                if("130431".equals(xzqId)){
                	count = update(sql, YW, new Object[] { yw_guid, ydsj, yddw, mj, zb, jsqk, wfwglx, dfccqk,
                            xcms, hcrq, spsj, spxmmc, spwh, gdsj, gdxmmc, gdwh, ydqk, status, ygspmj, ygspbl,
                            yggdmj, yggdbl, nyd, gengd, jsyd, wlyd, fhgh, bfhgh, zyjbnt, xmmc, pfwh, pzsj, yxjsq,
                            ytjjsq, xzjsq, jzjsq, xcr, xcdw, ordertime, jwzb, pmzb, shi, xian, padid,impuser,impxzq,impuserid,impxzqbm});
                }else{
                	count = update(sql, YW, new Object[] { yw_guid, ydsj, yddw, mj, zb, jsqk, wfwglx, dfccqk,
                            xcms, hcrq, spsj, spxmmc, spwh, gdsj, gdxmmc, gdwh, ydqk, status, ygspmj, ygspbl,
                            yggdmj, yggdbl, nyd, gengd, jsyd, wlyd, fhgh, bfhgh, zyjbnt, xmmc, pfwh, pzsj, yxjsq,
                            ytjjsq, xzjsq, jzjsq, xcr, xcdw, ordertime, jwzb, pmzb, shi, xian, padid,userName,userXzq,userId,xzqId});
                }
            } else {
                delAcc(yw_guid);
                sql = "update dc_ydqkdcb set ydsj=?,yddw=?,mj=?,zb=?,jsqk=?,wfwglx=?,dfccqk=?,"
                        + "xcms=?,hcrq=?,spsj=?,spxmmc=?,spwh=?,gdsj=?,gdxmmc=?,gdwh=?,ydqk=?,status=?,ygspmj=?,ygspbl=?,"
                        + "yggdmj=?,yggdbl=?,nyd=?,gengd=?,jsyd=?,wlyd=?,fhgh=?,bfhgh=?,zyjbnt=?,xmmc=?,pfwh=?,pzsj=?,yxjsq=?,"
                        + "ytjjsq=?,xzjsq=?,jzjsq=?,xcr=?,xcdw=?,ordertime=?,jwzb=?,pmzb=?,shi=?,xian=?,padid=?,impuser=?,impxzq=?,impuserid=?,impxzqbm=? where yw_guid=?";
                if("130431".equals(xzqId)){
                	count = update(sql, YW, new Object[] { ydsj, yddw, mj, zb, jsqk, wfwglx, dfccqk, xcms, hcrq,
                            spsj, spxmmc, spwh, gdsj, gdxmmc, gdwh, ydqk, status, ygspmj, ygspbl, yggdmj, yggdbl,
                            nyd, gengd, jsyd, wlyd, fhgh, bfhgh, zyjbnt, xmmc, pfwh, pzsj, yxjsq, ytjjsq, xzjsq,
                            jzjsq, xcr, xcdw, ordertime, jwzb, pmzb, shi, xian, padid,impuser,impxzq,impuserid,impxzqbm,yw_guid });
                }else{
                	count = update(sql, YW, new Object[] { ydsj, yddw, mj, zb, jsqk, wfwglx, dfccqk, xcms, hcrq,
                            spsj, spxmmc, spwh, gdsj, gdxmmc, gdwh, ydqk, status, ygspmj, ygspbl, yggdmj, yggdbl,
                            nyd, gengd, jsyd, wlyd, fhgh, bfhgh, zyjbnt, xmmc, pfwh, pzsj, yxjsq, ytjjsq, xzjsq,
                            jzjsq, xcr, xcdw, ordertime, jwzb, pmzb, shi, xian, padid,userName,userXzq,userId,xzqId,yw_guid });
                }
            }
            //现状
            sql = "delete from xz_xxdl where yw_guid=?";
            update(sql, YW, new Object[] { yw_guid });
            Element xzs = root.element("xz");
            int index=0;
            Element xz = xzs.element("num" + index);
            while(xz!=null){
                String xzGuid = xz.element("YW_GUID").getText();
                String xztbbh = xz.element("TBBH").getText();
                String xzqsdwmc = xz.element("QSDWMC").getText();
                String xzdlmc = xz.element("DLMC").getText();
                String xzmj = xz.element("MJ").getText();
                //插入数据库       
                sql = "insert into  xz_xxdl  values(?,?,?,?,?)";
                update(sql, YW, new Object[] { xzGuid, xztbbh, xzqsdwmc, xzdlmc, xzmj });
                index++;
                xz = xzs.element("num" + index);
            }
            //规划
            sql = "delete from gh_xxdl where yw_guid=?";
            update(sql, YW, new Object[] { yw_guid });
            Element ghs = root.element("gh");
            index=0;
            Element gh = ghs.element("num" + index);
            while(gh!=null){
                String ghGuid = gh.element("YW_GUID").getText();
                String ghtdytfqdm = gh.element("TDYTFQDM").getText();
                String ghdlmc = gh.element("GHDLMC").getText();
                String ghxzqmc = gh.element("XZQMC").getText();
                String ghmj = gh.element("MJ").getText();
                //插入数据库
                sql = "insert into  gh_xxdl  values(?,?,?,?,?)";
                update(sql, YW, new Object[] { ghGuid, ghtdytfqdm, ghdlmc, ghxzqmc, ghmj });
                index++;
                gh = ghs.element("num" + index);
            }
            //审批
            sql = "delete from sp_xxxm where yw_guid=?";
            update(sql, YW, new Object[] { yw_guid });
            Element sps = root.element("sp");
            index=0;
            Element sp = sps.element("num" + index);
            while(sp!=null){
                String spGuid = sp.element("YW_GUID").getText();
                String spXmmc = sp.element("SPXMMC").getText();
                String spWh = sp.element("SPWH").getText();
                String spSj = sp.element("SPSJ").getText();
                String spYgbl = sp.element("SPYGBL").getText();
                String spYgmj = sp.element("SPYGMJ").getText();
                //插入数据库
                sql = "insert into  sp_xxxm  values(?,?,?,?,?,?)";
                update(sql, YW, new Object[] { spGuid, spXmmc, spWh, spSj, spYgbl, spYgmj });
                index++;
                sp = sps.element("num" + index);
            }
            //供地
            sql = "delete from gd_xxxm where yw_guid=?";
            update(sql, YW, new Object[] { yw_guid });
            Element gds = root.element("gd");
            index=0;
            Element gd = gds.element("num" + index);
            while(gd!=null){
                String gdGuid = gd.element("YW_GUID").getText();
                String gdXmmc = gd.element("GDXMMC").getText();
                String gdWh = gd.element("GDWH").getText();
                String gdSj = gd.element("GDSJ").getText();
                String gdYgbl = gd.element("GDYGBL").getText();
                String gdYgmj = gd.element("GDYGMJ").getText();
                //插入数据库
                sql = "insert into  gd_xxxm  values(?,?,?,?,?,?)";
                update(sql, YW, new Object[] { gdGuid, gdXmmc, gdWh, gdSj, gdYgbl, gdYgmj });
                index++;
                gd = gds.element("num" + index);
            }

        } catch (DocumentException e) {
            System.out.println(e.toString());
        }
        return count;
    }
    
    /**
     * 
     * <br>Description:解析巡查日志xml文件入库
     * <br>Author:王雷
     * <br>Date:2013-9-26
     * @param xmlPath
     */
    private void parseXcrzXml(String xmlPath){
        try {
            File f = new File(xmlPath);
            SAXReader sax = new SAXReader();
            Document document = sax.read(f);;
            Element root = document.getRootElement();      
            Element base = (Element) root.selectNodes("/XCRZ/base").get(0);
            String[] elements = {"yw_guid","xcbh","xcdw","xcrq","xcqy","xcry","xclx","sfywf","clyj","bz","writerxzqh","userid","writedate","spqk","allnum"};
            String[] elements2 = {"xcbh","xcdw","xcrq","xcqy","xcry","xclx","sfywf","clyj","bz","writerxzqh","userid","writedate","spqk","allnum","yw_guid"};
            String[] values = new String[elements.length];
            String[] values2 = new String[elements2.length];
            for(int i=0;i<elements.length;i++){
                values[i] = base.element(elements[i].toUpperCase()).getText();
                values2[i] = base.element(elements2[i].toUpperCase()).getText();
            }            
            String selectSql = "select * from xcrz t where t.yw_guid = ?";
            List<Map<String,Object>> list = query(selectSql,YW,new Object[]{values[0]});
            String updateSql = "update xcrz t set xcbh=?,xcdw=?,xcrq=?,xcqy=?,xcry=?,xclx=?,sfywf=?,clyj=?,bz=?,writerxzqh=?,userid=?,writedate=to_date(?,'yyyy-mm-dd hh24:mi:ss'),spqk=?,allnum=? where t.yw_guid=?";
            String insertSql = "insert into xcrz(yw_guid,xcbh,xcdw,xcrq,xcqy,xcry,xclx,sfywf,clyj,bz,writerxzqh,userid,writedate,spqk,allnum) values(?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),?,?)";
            if(list.size()>0){
                update(updateSql,YW,values2);
            }else{
                update(insertSql,YW,values);
            }
        } catch (DocumentException e) {
            System.out.println(e.toString()); 
        }    
    }
    
    /**
     * 
     * <br>Description:删除附件信息
     * <br>Author:陈强峰
     * <br>Date:2012-7-29
     * @param rwbh
     */
    private void delAcc(String rwbh) {
        String sql = "select file_id,file_path from atta_accessory where yw_guid=? ";
        List<Map<String, Object>> list = query(sql, CORE, new Object[] { rwbh });
        for (int i = 0; i < list.size(); i++) {
            String ftpFileName = list.get(i).get("file_path").toString();
            String fileId = list.get(i).get("file_id").toString();
            UtilFactory.getFtpUtil().deleteFile(ftpFileName);
            sql = "delete from atta_accessory where file_id=?";
            update(sql, CORE, new Object[] { fileId });
        }
    }

    /**
     * 
     * <br>Description:遍历任务文件夹
     * <br>Author:陈强峰
     * <br>Date:2012-7-4
     * @param path
     */
    private void importAccessoryField(String path) {
        File f = new File(path);
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getAbsolutePath().endsWith(".txt")) {
                continue;
            }
            accUpload(files[i]);
        }
    }

    /**
     * 
     * <br>Description:根据文件夹名上传相应文件夹下的附件
     * <br>Author:陈强峰
     * <br>Date:2012-7-4
     * @param file
     */
    private void accUpload(File file) {
        File[] files = file.listFiles();
        String ywguid = file.getName();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getAbsolutePath().endsWith(".xml")) {
                continue;
            }
            dealAcc(files[i], ywguid);
        }
        String yw_guid = file.getName();
        String sql = "select file_id,file_name from atta_accessory where yw_guid=?";
        List<Map<String, Object>> list = query(sql, CORE, new Object[] { yw_guid });
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            if (map.get("file_name").toString().endsWith("jpg")) {
                if (i == list.size() - 1) {
                    sb.append(map.get("file_id"));
                } else {
                    sb.append(map.get("file_id")).append(",");
                }
            }
        }
        sql = "update dc_ydqkdcb set ZPBH=? where yw_guid=?";
        update(sql, YW, new Object[] { sb.toString(), yw_guid });
    }

    /**
     * 
     * <br>Description:处理附件名等参数
     * <br>Author:陈强峰
     * <br>Date:2012-7-4
     * @param file
     */
    private void dealAcc(File file, String ywguid) {
        String filename = file.getName();
        String fileSuffix = filename.substring(filename.lastIndexOf("."));
        String file_id = new UID().toString().replaceAll(":", "-");
        String newFilePath = file_id + fileSuffix;
        File newFile = new File(newFilePath);
        file.renameTo(newFile);
        if (UtilFactory.getFtpUtil().uploadFile(newFilePath)) {
            String sql = "insert into atta_accessory(file_id,file_type,file_name,file_path,yw_guid) values(?,?,?,?,?)";
            update(sql, CORE, new Object[] { file_id, "file", filename, newFilePath, ywguid });
        }
    }

    /**
     * 
     * <br>Description:上图操作
     * <br>Author:王雷
     * <br>Date:2012-10-18
     * @param file
     */
    public void savePolygon(String cjzb, String yw_guid) {
        String wkt = "";
        String[] allPoint = cjzb.split(",");
        Polygon polygon = new Polygon();
        Ring ring = new Ring();
        if (allPoint.length > 2) {
            for (int i = 0; i < allPoint.length; i += 2) {
                double x = Double.parseDouble(allPoint[i]);
                double y = Double.parseDouble(allPoint[i + 1]);
                Point p = new Point(x, y);
                ring.putPoint(p);
            }
            Point p2 = new Point(Double.parseDouble(allPoint[0]), Double.parseDouble(allPoint[1]));
            ring.putPoint(p2);
            polygon.addRing(ring);
            wkt = polygon.toWKT();
        }

        String querySrid = "select t.srid from sde.st_geometry_columns t where t.table_name = 'WYXCHC'";
        String srid = null;
        List<Map<String, Object>> rs = query(querySrid, GIS);
        try {
            if (rs.size() > 0) {
                srid = rs.get(0).get("srid") + "";
            }
            String sql = "INSERT INTO WYXCHC(OBJECTID,yw_guid,SHAPE) VALUES ((select nvl(max(OBJECTID)+1,1) from WYXCHC),'"
                    + yw_guid + "',sde.st_geometry ('" + wkt + "', " + srid + "))";
            String selSql = "select * from WYXCHC where yw_guid='" + yw_guid + "'";
            List<Map<String, Object>> rs1 = query(selSql, GIS);
            if (rs1.size() > 0) {
                sql = "update WYXCHC set shape=sde.st_geometry ('" + wkt + "', " + srid + ") where yw_guid='"
                        + yw_guid + "'";
            }
            update(sql, GIS);

        } catch (Exception e) {
            System.out.println("采集坐标出错");
        }

    }

    /**
     * 
     * <br>Description:保存图层属性
     * <br>Author:王雷
     * <br>Date:2012-10-18
     * @param file
     */
    public void saveProperties(String yw_guid) {
        String sql1 = "select t.JSXM,t.JSDW,t.XCDD,t.CJMJ,t.JSQK,t.XCX||t.XCS XCR,t.XCSJ from XCXCQKB t where yw_guid='"
                + yw_guid + "'";
        List<Map<String, Object>> list = query(sql1, YW);
        if (list.size() > 0) {
            Map<String, Object> map = (Map<String, Object>) list.get(0);
            String sql2 = "update wyxchc set yw_xmmc=?,yw_dwmc=?,yw_tdwz=?,yw_sjzdmj=?,yw_xcqkms=?,yw_xcr=?,yw_xcsj=to_date(?,'rrrr-mm-dd') where yw_guid='"
                    + yw_guid + "'";
            String cjmj = (String) map.get("CJMJ");
            if (cjmj != null && !"null".equals(cjmj)) {
                cjmj = cjmj.substring(0, cjmj.length() - 2);
            }
            update(sql2, GIS, new Object[] { map.get("JSXM"), map.get("JSDW"), map.get("XCDD"), cjmj,
                    map.get("JSQK"), map.get("XCR"), map.get("XCSJ") });
        }
    }

}
