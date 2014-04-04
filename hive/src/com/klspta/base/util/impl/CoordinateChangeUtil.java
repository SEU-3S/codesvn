package com.klspta.base.util.impl;

import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.api.ICoordinateChangeUtil;
import com.klspta.base.wkt.Point;

public class CoordinateChangeUtil implements ICoordinateChangeUtil {
    private static final double L0 = UtilFactory.getConfigUtil().getConfigDouble("L0");

    private static final double eastDistance = UtilFactory.getConfigUtil().getConfigDouble("eastDistance");

    private static final double m_dDeltaNorth = UtilFactory.getConfigUtil().getConfigDouble("m_dDeltaNorth");

    private static final double m_dKvalue = UtilFactory.getConfigUtil().getConfigDouble("m_dKvalue");

    private static final double m_dAlpha = UtilFactory.getConfigUtil().getConfigDouble("m_dAlpha");

    private static final double m_dDeltaEast = UtilFactory.getConfigUtil().getConfigDouble("m_dDeltaEast");

    private Point point84 = null;

    private Point point80 = null;

    private static double PI = 3.14159265358979323846;

    private static double WGS84_A = 6378137; // 椭球（84）的长半轴

    private static double WGS84_B = 6356752.3142; // 椭球（84）的短半轴

    private static double WGS84_E2 = 0.00669437999013; // 椭球（84）的第一偏心率

    private static double WGS84_EE2 = 0.00673949674227; // 椭球（84）的第二偏心率

    static {
        System.out.println("lo:" + L0);
        System.out.println("eastDistance:" + eastDistance);
        System.out.println("m_dDeltaNorth:" + m_dDeltaNorth);
        System.out.println("m_dKvalue:" + m_dKvalue);
        System.out.println("m_dAlpha:" + m_dAlpha);
        System.out.println("m_dDeltaEast:" + m_dDeltaEast);

    }

    public static ICoordinateChangeUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception("请从UtilFacoory获取工具.");
        }
        return new CoordinateChangeUtil();
    }

    @Override
    public Point changePoint(Point point, String changetype) {
        if (GPS84_TO_BALIN80.equals(changetype)) {
            this.point84 = point;
            changeDFMToD();//将gps的度分秒格式转换成度
            changeBLToPlain("WGS84", true);//将度转换成米
            Plain84ToPlain80();//将84转换成80
            return this.point80;
        }

        // 从84BL转换为80BL
        if (BL84_TO_BL80.equals(changetype)) {
            this.point84 = point;
            //changeDFMToD();//将gps的度分秒格式转换成度
            changeBLToPlain("WGS84", false);//将度转换成米
            Plain84ToPlain80();//将84转换成80
            return GaussToBL(point80.getX(), point80.getY());//将80米转换为80经纬度
        }
        if (BL80_TO_PLAIN80.equals(changetype)) {
            this.point84 = point;
            changeBLToPlain("xian80", true);//将度转换成米
            return this.point84;
        }
        if (PLAIN80_TO_BL80.equals(changetype)) {
            point84 = GaussToBL(point.getX(), point.getY());
            return this.point84;
        }
        if (PLAIN84_TO_PLAIN80.equals(changetype)) {
            this.point84 = point;
            Plain84ToPlain80();
            return this.point80;
        }
        if (GRID.equals(changetype)) {
            return null;//UtilFactory.getGridUtil().changePoint(point);
        }
        return null;
    }

    private void changeDFMToD() {
        point84.setPointXY(changeDFMToD(point84.getX()), changeDFMToD(point84.getY()));
    }

    private double preChange(double xory) {
        int ixory = (int) xory;
        return (xory - ixory) * 60 / 100 + ixory;
    }

    private double changeDFMToD(double xory) {
        xory = preChange(xory);
        int ixory = (int) xory;
        double fen = ixory % 100;
        int miao = (int) ((xory - ixory) * 100);
        double dmiao = miao;
        double xiaomiao = ((((xory - ixory) * 100) - miao) * 100) % 100;
        return (ixory / 100 * 1000000 + (fen * 10000 + dmiao * 100 / 0.6 + xiaomiao / 0.6) / 0.6) / 1000000;
    }

    private void changeBLToPlain(String tqt, boolean isadd) {
        double L = point84.getX();
        double B = point84.getY();
        WGS84_A = UtilFactory.getConfigUtil().getConfigDouble(tqt + "_a");
        WGS84_B = UtilFactory.getConfigUtil().getConfigDouble(tqt + "_b");
        WGS84_E2 = UtilFactory.getConfigUtil().getConfigDouble(tqt + "_e2");
        WGS84_EE2 = UtilFactory.getConfigUtil().getConfigDouble(tqt + "_e12");
        double AAS = Math.pow((double) WGS84_A, 2);
        double BBS = Math.pow(WGS84_B, 2);
        double ES = WGS84_E2;
        double E1S = WGS84_EE2;
        double KX = WGS84_A * (1 - ES);
        double E2S = ES * ES;
        double E3S = ES * E2S;
        double E4S = E2S * E2S;
        double E5S = E2S * E3S;
        double E6S = E3S * E3S;
        double KA1 = 1 + 3 * ES / 4 + 45 * E2S / 64 + 175 * E3S / 256 + 11025 * E4S / 16384;
        double KA = KA1 + 43659 * E5S / 65536 + 693693 * E6S / 1048576;
        double KB = KA - 1;
        double KC = 15 * E2S / 32 + 175 * E3S / 384 + 3675 * E4S / 8192 + 14553 * E5S / 32768 + 231231 * E6S
                / 524288;
        double KD = 35 * E3S / 96 + 735 * E4S / 2048 + 14553 * E5S / 40960 + 231231 * E6S / 655360;
        double KE = 315 * E4S / 1024 + 6237 * E5S / 20480 + 99099 * E6S / 327680;
        double KF = 693 * E5S / 2560 + 11011 * E6S / 40960;
        double KG = 1001 * E6S / 4096;
        B = B * PI / 180;
        L = L * PI / 180;
        double Meridian = L0 * PI / 180;
        double SC = Math.sin(B) * Math.cos(B);
        double SS = Math.sin(B) * Math.sin(B);
        double S2S = SS * SS;
        double S3S = SS * S2S;
        double XA0 = KX * KA * B - KX * SC * (KB + SS * (KC + SS * (KD + KE * SS + KF * S2S + KG * S3S)));
        double T = Math.tan(B);
        double StationLd = L - Meridian;
        double TS = T * T;
        double T2S = TS * TS;
        double T3S = TS * T2S;
        double CS = Math.cos(B) * Math.cos(B);
        double NR = AAS / Math.sqrt(AAS * CS + BBS * SS);
        double NAS = E1S * CS;
        double N2S = NAS * NAS;
        double LDS = StationLd * StationLd;
        double FA = NR * SC * LDS;
        double FB = CS * LDS;
        double FC = NR * Math.cos(B) * StationLd;
        double FD = (5 - TS + 9 * NAS + 4 * N2S) / 24;
        double FF = (61 - 58 * TS + T2S + 270 * NAS - 330 * NAS * TS) / 720;
        double FG = (1385 - 3111 * TS + 543 * T2S - T3S) / 40320;
        double FH = (1 - TS + NAS) / 6;
        double FI = (5 - 18 * TS + T2S + 14 * NAS - 58 * NAS * TS) / 120;
        double FJ = (61 - 479 * TS + 179 * T2S - T3S) / 5040;
        double xx = XA0 + FA * (0.5 + FB * (FD + FB * (FF + FB * FG)));
        double yy = FC * (1 + FB * (FH + FB * (FI + FB * FJ)));
        if (isadd) {
            yy = yy + 500000 + eastDistance;
        } else {
            yy = yy + 500000;
        }
        double swap;
        swap = xx;
        xx = yy;
        yy = swap;
        point84.setPointXY(xx, yy);
    }

    private void Plain84ToPlain80() {
        double myx = point84.getX();
        double myy = point84.getY();
        double yy = m_dDeltaNorth + m_dKvalue * Math.cos(m_dAlpha) * myy + m_dKvalue * Math.sin(m_dAlpha)
                * myx;
        double xx = m_dDeltaEast - m_dKvalue * Math.sin(m_dAlpha) * myy + m_dKvalue * Math.cos(m_dAlpha)
                * myx;
        System.out.println("参数" + m_dKvalue * Math.sin(m_dAlpha) * myy);
        System.out.println("参数" + m_dKvalue * Math.cos(m_dAlpha) * myx);
        System.out.println("函数" + Math.sin(m_dAlpha));
        System.out.println("函数" + Math.cos(m_dAlpha));
        //   xx = xx + eastDistance;
        point80 = new Point(xx, yy);
    }

    /**
     * <br>Description:将高斯平面坐标转换为BL
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @param X
     * @param Y
     * @return
     */
    public static Point GaussToBL(double X, double Y) {//, double *longitude, double *latitude)
        int ProjNo;
        int ZoneWide; ////带宽
        double[] output = new double[2];
        double longitude1, latitude1, longitude0, X0, Y0, xval, yval;//latitude0,
        double e1, e2, f, a, ee, NN, T, C, M, D, R, u, fai, iPI;
        iPI = 0.0174532925199433; ////3.1415926535898/180.0;
        //a = 6378245.0; f = 1.0/298.3; //54年北京坐标系参数
        a = 6378137.0;
        f = 0.00335281006247; //80年西安坐标系参数
        ZoneWide = 3; ////6度带宽
        ProjNo = (int) (X / 1000000L); //查找带号
        longitude0 = ProjNo * ZoneWide + ZoneWide / 2 - 1;
        longitude0 = longitude0 * iPI; //中央经线
        X0 = ProjNo * 1000000L + 500000L;
        Y0 = 0;
        xval = X - X0;
        yval = Y - Y0; //带内大地坐标
        e2 = 2 * f - f * f;
        e1 = (1.0 - Math.sqrt(1 - e2)) / (1.0 + Math.sqrt(1 - e2));
        ee = e2 / (1 - e2);
        M = yval;
        u = M / (a * (1 - e2 / 4 - 3 * e2 * e2 / 64 - 5 * e2 * e2 * e2 / 256));
        fai = u + (3 * e1 / 2 - 27 * e1 * e1 * e1 / 32) * Math.sin(2 * u)
                + (21 * e1 * e1 / 16 - 55 * e1 * e1 * e1 * e1 / 32) * Math.sin(4 * u)
                + (151 * e1 * e1 * e1 / 96) * Math.sin(6 * u) + (1097 * e1 * e1 * e1 * e1 / 512)
                * Math.sin(8 * u);
        C = ee * Math.cos(fai) * Math.cos(fai);
        T = Math.tan(fai) * Math.tan(fai);
        NN = a / Math.sqrt(1.0 - e2 * Math.sin(fai) * Math.sin(fai));
        R = a
                * (1 - e2)
                / Math
                        .sqrt((1 - e2 * Math.sin(fai) * Math.sin(fai))
                                * (1 - e2 * Math.sin(fai) * Math.sin(fai))
                                * (1 - e2 * Math.sin(fai) * Math.sin(fai)));
        D = xval / NN;
        //计算经度(Longitude) 纬度(Latitude)
        longitude1 = longitude0
                + (D - (1 + 2 * T + C) * D * D * D / 6 + (5 - 2 * C + 28 * T - 3 * C * C + 8 * ee + 24 * T
                        * T)
                        * D * D * D * D * D / 120) / Math.cos(fai);
        latitude1 = fai
                - (NN * Math.tan(fai) / R)
                * (D * D / 2 - (5 + 3 * T + 10 * C - 4 * C * C - 9 * ee) * D * D * D * D / 24 + (61 + 90 * T
                        + 298 * C + 45 * T * T - 256 * ee - 3 * C * C)
                        * D * D * D * D * D * D / 720);
        //转换为度 DD
        output[0] = longitude1 / iPI;
        output[1] = latitude1 / iPI;
        Point p = new Point(output[0], output[1]);
        return p;
    }

    public Point planeCoordinateTransform(Point point) {
        double myx = point.getX();
        double myy = point.getY();
        double yy = m_dDeltaNorth + m_dKvalue * Math.cos(m_dAlpha) * myy + m_dKvalue * Math.sin(m_dAlpha)
                * myx;
        double xx = m_dDeltaEast - m_dKvalue * Math.sin(m_dAlpha) * myy + m_dKvalue * Math.cos(m_dAlpha)
                * myx;
        //xx = xx + eastDistance;
        return new Point(xx, yy);
    }
}
