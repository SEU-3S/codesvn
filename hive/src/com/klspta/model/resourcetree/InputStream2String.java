package com.klspta.model.resourcetree;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description:将InputStream转成String
 * <br>Date:2012-08-06
 * @author 李亚栓
 * @since JDK1.6
 * @version 1.0
 */
public class InputStream2String {
    /**
     * <br>Description:将InputStream转成String
     * <br>Author:李亚栓
     * <br>Date:2012-08-06
     * @param is
     * @return
     */
    public static String transfer(InputStream is) {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        try {
            for (int n; (n = is.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toString();
    }
}
