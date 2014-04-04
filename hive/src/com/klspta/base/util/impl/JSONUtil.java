package com.klspta.base.util.impl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.api.IJSONUtil;

/**
 * 
 * <br>Title:json字符串工具
 * <br>Description:json字符串和json对象之间的转换工具类
 * <br>Author:王瑛博
 * <br>Date:2011-5-3
 */
public final class JSONUtil extends AbstractBaseBean implements IJSONUtil {
    /**
     * '('
     */
    private static final char LEFTKUOHAO = 123;

    /**
     * ')'
     */
    private static final char RIGHTKUOHAO = 125;

    /**
     * ','  
     */
    private static final char DOUHAO = 44;

    /**
     * '['
     */
    private static final char LEFTZHONGKUOHAO = 91;

    /**
     * ']'
     */
    private static final char RIGHTZHONGKUOHAO = 93;

    /**
     *  ' '
     */
    private static final char KONGGE = 32;

    /**
     * '\n'
     */
    private static final char FANXIEGANGN = 10;

    /**
     * '\r'
     */
    private static final char FANXIEGANGR = 13;

    /**
     * '\t'
     */
    private static final char FANXIEGANGT = 9;

    /**
     * '\\'
     */
    private static final char SHUANGFANXIEGANG = 92;

    /**
     * 格式化时的缩进
     */
    private static final String INDENT = "    ";

    /**
     * 缩进长度
     */
    private static final int INDENT_LENGTH = INDENT.length();

    /**
     * 
     */
    private static final String CARRIAGE_RETURN = System.getProperty("line.separator");

    /**
     * 
     */
    private static final int CARRIAGE_RETURN_LENGTH = CARRIAGE_RETURN.length();

    /**
     * 实例化
     */
    private static IJSONUtil jsonUtil = new JSONUtil();

    /**
     * 
     * <br>Description:构造方法私有化
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     */
    private JSONUtil() {
    }

    /**
     * 
     * <br>Description:取得json工具的实例方法
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @param key
     * @return
     */
    public static IJSONUtil getInstance(String key) {
        return jsonUtil;
    }

    /**
     * 
     * <br>Description:json字符串到json对象的转换
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IJSONUtil#jsonToObject(java.lang.String)
     */
    public JSONObject jsonToObject(String json) throws Exception {
        try {
            return JSONObject.fromObject(json);
        } catch (Exception e) {
        	responseException(this, "jsonToObject","100008", e);
            throw e;
        }
    }

    /**
     * 
     * <br>Description:json字符串到多个json对象的转换
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IJSONUtil#jsonToObjects(java.lang.String)
     */
    public JSONArray jsonToObjects(String json) throws Exception {
        try {
            return JSONArray.fromObject(json);
        } catch (Exception e) {
        	responseException(this, "jsonToObject","100008", e);
            throw e;
        }
    }

    /**
     * 
     * <br>Description:任意对象到json对象的转换
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IJSONUtil#objectToJSON(java.lang.Object)
     */
    public String objectToJSON(Object object) throws Exception {
        try {
            if (JSONUtils.isArray(object)) {
                return JSONArray.fromObject(object).toString();
            } else {
                return JSONObject.fromObject(object).toString();
            }
        } catch (Exception e) {
        	responseException(this, "objectToJSON", "100008", e);
            throw e;
        }
    }

    /**
     * 
     * <br>Description:格式化json字符串
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IJSONUtil#format(java.lang.String)
     */
    public String format(String json) {
        StringBuffer doc = new StringBuffer(json);
        try {
            int indentCount = 0;
            boolean key = false;
            for (int i = 0; i < doc.length();) {
                char ch = doc.charAt(i);
                if (ch == '"' && doc.charAt(i - 1) != '\\') {
                    key = !key;
                }
                if (key) {
                    i++;
                } else {
                    switch (ch) {
                    case LEFTKUOHAO: // '{'
                        doc.replace(i, i + 1, (new StringBuffer("{")).append(CARRIAGE_RETURN).append(
                                indent(++indentCount)).toString());
                        i += CARRIAGE_RETURN_LENGTH + indentCount * INDENT_LENGTH;
                        break;

                    case RIGHTKUOHAO: // '}'
                        doc.replace(i, i + 1, (new StringBuffer(String.valueOf(CARRIAGE_RETURN))).append(
                                indent(--indentCount)).append("}").toString());
                        i += CARRIAGE_RETURN_LENGTH + indentCount * INDENT_LENGTH;
                        if (doc.charAt(i + 1) != ',' && notBracket(doc, i + 1)) {
                            doc.replace(i + 1, i + 1, (new StringBuffer(String.valueOf(CARRIAGE_RETURN)))
                                    .append(indent(indentCount)).toString());
                            i += CARRIAGE_RETURN_LENGTH + indentCount * INDENT_LENGTH;
                        }
                        break;

                    case DOUHAO: // ','
                        doc.replace(i, i + 1, (new StringBuffer(",")).append(CARRIAGE_RETURN).append(
                                indent(indentCount)).toString());
                        i += CARRIAGE_RETURN_LENGTH + indentCount * INDENT_LENGTH;
                        break;

                    case LEFTZHONGKUOHAO: // '['
                        doc.replace(i, i + 1, (new StringBuffer("[")).append(CARRIAGE_RETURN).append(
                                indent(++indentCount)).toString());
                        i += CARRIAGE_RETURN_LENGTH + indentCount * INDENT_LENGTH;
                        break;

                    case RIGHTZHONGKUOHAO: // ']'
                        doc.replace(i, i + 1, (new StringBuffer(String.valueOf(CARRIAGE_RETURN))).append(
                                indent(--indentCount)).append("]").toString());
                        i += CARRIAGE_RETURN_LENGTH + indentCount * INDENT_LENGTH;
                        if (doc.charAt(i + 1) != ',' && notBracket(doc, i + 1)) {
                            doc.replace(i + 1, i + 1, (new StringBuffer(String.valueOf(CARRIAGE_RETURN)))
                                    .append(indent(indentCount)).toString());
                            i += CARRIAGE_RETURN_LENGTH + indentCount * INDENT_LENGTH;
                        }
                        break;

                    case KONGGE: // ' '
                        doc.replace(i, i + 1, "");
                        i--;
                        break;

                    case FANXIEGANGN: // '\n'
                        doc.replace(i, i + 1, "");
                        i--;
                        break;

                    case FANXIEGANGR: // '\r'
                        doc.replace(i, i + 1, "");
                        i--;
                        break;

                    case FANXIEGANGT: // '\t'
                        doc.replace(i, i + 1, "");
                        i--;
                        break;

                    case SHUANGFANXIEGANG: // '\\'
                        if (doc.charAt(i + 1) == '"') {
                            i++;
                        }
                        break;
                    default:
                        break;
                    }

                    i++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            responseException(this,"format", "300004", e);
        }
        return doc.toString();
    }

    /**
     * 
     * <br>Description:是否是转义字符
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @param doc
     * @param pos
     * @return
     */
    private boolean notBracket(StringBuffer doc, int pos) {
        for (int i = pos; i < doc.length(); i++) {
            if (doc.charAt(i) != ' ' && doc.charAt(i) != '\n' && doc.charAt(i) != '\r') {
                return doc.charAt(i) != '}' && doc.charAt(i) != ']';
            }
            doc.replace(i, i + 1, "");
            i--;
        }
        return false;
    }

    /**
     * 
     * <br>Description:缩进
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @param count
     * @return
     */
    private String indent(int count) {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < count; i++) {
            ret.append(INDENT);
        }

        return ret.toString();
    }
}
