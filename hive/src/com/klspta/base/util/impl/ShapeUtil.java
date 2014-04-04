package com.klspta.base.util.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.Transaction;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.indexed.IndexedShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.WKTReader2;
import org.opengis.feature.simple.SimpleFeatureType;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.api.IShapeUtil;
import com.klspta.base.util.bean.shapeutil.DbfParameters;
import com.klspta.base.util.bean.shapeutil.ShpParameters;

public final class ShapeUtil extends AbstractBaseBean   implements IShapeUtil {

    public static IShapeUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception("请从UtilFacoory获取工具.");
        }
        return new ShapeUtil();
    }

    @Override
    public ShpParameters expShpFile(ShpParameters parameters, String name) {
        //parameters.setFilename(System.currentTimeMillis() + ".shp");
        parameters.setFilepath(UtilFactory.getConfigUtil().getShapefileTempPathFloder() + name);
        Transaction t = new DefaultTransaction();
        try {
            MemoryDataStore mds = new MemoryDataStore();
            SimpleFeatureType featureType = DataUtilities.createType("location", parameters.getFields());
            SimpleFeatureCollection collection = FeatureCollections.newCollection("internal");
            WKTReader2 wkt = new WKTReader2();
            Vector<Map<String, String>> v = parameters.getRecords();
            Map<String, String> record = null;
            Iterator<Map<String, String>> vit = v.iterator();
            Iterator<String> recordit = null;
            String key = "";
            while (vit.hasNext()) {
                record = vit.next();
                recordit = record.keySet().iterator();
                Object[] oo = new Object[Integer.parseInt(record.get("size")) + 1];
                //int i = 1;
                while (recordit.hasNext()) {
                    key = recordit.next();
                    String[] records = (record.get(key)).split(",");
                    if (!(key.equals("size"))) {
                        if (key.equals("geometry")) {
                            oo[0] = wkt.read(record.get(key));
                        } else {
                            for (int i = 1, j = 0; i <= records.length; i++, j++) {
                                oo[i] = records[j];
                            }
                        }
                    }

                }
                collection.add(SimpleFeatureBuilder.build(featureType, oo, null));
            }
            mds.addFeatures(collection);
            FeatureSource<?, ?> featureSource = mds.getFeatureSource("location"); //existing feature source from MemoryDataStore
            SimpleFeatureType ft = (SimpleFeatureType) featureSource.getSchema();
            File file = new File(parameters.getFilepath(), parameters.getFilename());
            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put("url", file.toURI().toURL());
            @SuppressWarnings("deprecation")
            FileDataStoreFactorySpi factory = new IndexedShapefileDataStoreFactory();
            ShapefileDataStore dataStore = (ShapefileDataStore) factory.createNewDataStore(params);
            dataStore.createSchema(featureType);
            dataStore.setStringCharset(Charset.forName("GBK"));
            //CoordinateReferenceSystem crs=CRS.decode("EPSG:2364");
            //dataStore.forceSchemaCRS(DefaultGeographicCRS.);
            FeatureStore<?, ?> featureStore = (FeatureStore<?, ?>) dataStore.getFeatureSource(ft
                    .getTypeName());
            FeatureCollection ff = featureSource.getFeatures(); // grab all features
            featureStore.addFeatures(ff);
            t.commit();
        } catch (IOException e) {
        	responseException(this,"expShpFile", "100009", e);
            e.printStackTrace();
            try {
                t.rollback();
            } catch (IOException ioe) {
            }
        } catch (Exception e) {
        	responseException(this,"expShpFile", "100009", e);
            e.printStackTrace();
        } finally {
            try {
                t.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return parameters;
    }

    @Override
    public DbfParameters parseDbfFile(DbfParameters parameters) {
        return null;
    }

    @Override
    public ShpParameters impShpFile(ShpParameters parameters) {
        return null;
    }

}
