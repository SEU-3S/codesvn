package com.klspta.model.resourcetree;

import java.util.HashMap;

/**
 * 
 * <br>Title:define resourceTree`s interface
 * <br>Description:
 * <br>Author:李亚栓
 * <br>Date:2012-8-6
 */
public interface ITreeOperation {
    /**
     * 
     * <br>Description:根据tree_name获得tree对象
     * <br>Author:李亚栓
     * <br>Date:2012-8-6
     * @param tree_name
     * @return
     */
public TreeBean getTree(HashMap<String, String> map);
}
