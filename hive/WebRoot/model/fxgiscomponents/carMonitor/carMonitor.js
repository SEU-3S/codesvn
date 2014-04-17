
Ext.onReady(function(){
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	parent.center.center.putClientCommond("wpzfjc","doit");
	parent.center.center.putRestParameter("_$sid","aaa");
	var myData = parent.center.center.restRequest();
	
	var _$ID = '';
    function change(val){
        if(val > 10000){
            return '<span style="color:green;">√</span>';
        }else if(val < 10000){
            return '<span style="color:red;size=20">¤</span>';
        }
        return val;
    }

    var store = new Ext.data.ArrayStore({
		proxy: new Ext.ux.data.PagingMemoryProxy(myData),
		remoteSort:true,
        fields: [
		   {name: 'objectid'},
           {name: 'no'},
           {name: 'code'},
           {name: 'warning'}
		   
        ]
    });

    store.loadData(myData);

    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
		    {header:'', width:0, sortable:true, renderer:change, dataIndex:'objectid'},
            {id:'no', header:'编号', width:20, sortable:false, dataIndex:'no'},
            {header:'所在政区', width:75, sortable:true, dataIndex:'code'},
            {header:'报警', width:35, sortable:true, renderer:change, dataIndex:'warning'}
        ],
        stripeRows: true,
        autoExpandColumn: 'no',
        height: 480,
        width: 210,
        stateful: true,
        stateId: 'grid',
		
	    plugins: new Ext.ux.PanelResizer({
            minHeight: 100
        }),

        bbar: new Ext.PagingToolbar({
            pageSize: 20,
            store: store,
            displayInfo: true,
            plugins: new Ext.ux.ProgressBarPager()
        })
    });
    
    grid.render('form-ct');

    grid.on('cellclick',function(e){
        var row = grid.getSelectionModel().getSelected();
		_$ID = row.data.objectid;
		var url = 'http://wangyingbo:8399/arcgis/rest/services/YZ_WPZFJC_6/MapServer/0';
		//设置信息窗口模板
		var infoTemplate = new esri.InfoTemplate();
		infoTemplate.setTitle("${NAME}");
        infoTemplate.setContent("<b>2000 Population: </b>${POP2000}<br/>"
                             + "<b>2000 Population per Sq. Mi.: </b>${POP00_SQMI}<br/>"
                             + "<b>2007 Population: </b>${POP2007}<br/>"
                             + "<b>2007 Population per Sq. Mi.: </b>${POP07_SQMI}");
		
		var query = new esri.tasks.Query();
        query.outFields = ["*"];
        query.returnGeometry = true;
        query.where = "objectid = " + _$ID;
        query.outSpatialReference = parent.center.center.getMapSpatialReference();
	    var queryTask = new esri.tasks.QueryTask(url);
	    dojo.connect(queryTask, "onComplete", function(featureSet) {
		    parent.center.center.clearHighlight();
		    var graphic = featureSet.features[0];
			graphic.setInfoTemplate(infoTemplate);
			//alert(featureSet.features[0].getContent());
	        var highlightGraphic = new esri.Graphic(graphic.geometry,parent.center.center.commonsfs);
		    parent.center.center.addHighlight(highlightGraphic);
		    parent.center.center.setMapExtent(graphic.geometry.getExtent().expand(7));
	    });
        queryTask.execute(query);
		
		//fbutton.onEnable(true);
		
    });

	store.load({params:{start:0, limit:20}});
	
	
	var fp = new Ext.FormPanel({
        renderTo: 'form-ct',
        fileUpload: true,
        autoWidth: true,
        autoHeight: true,
		bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 1,
        items: [{
            xtype: 'fileuploadfield',
            id: 'form-file',
            emptyText: '导入外业核查红线',
            name: 'shape-path',
            buttonText: '浏览'
        }],
        buttons: [{
            text: '预览',
            handler: function(){
                if(fp.getForm().isValid()){
	                fp.getForm().submit({
						url: parent.center.center.restUrl + 'importShapefile/perviewShapefile/objectid=' + _$ID,
	                    waitMsg: '正在执行,请稍后...',
	                    success: function(form, action){
	                        var isSuc = action.result.success; 
	                    },
                        failure:function(form, action){
							var polygon = new esri.geometry.Polygon(parent.center.center.getMapSpatialReference());
							for(var i = 0; i < action.result[0].geo.length; i++){
								var geo = action.result[0].geo;
								polygon.addRing(geo[i]);
							}
							var highlightGraphic = new esri.Graphic(polygon,parent.center.center.commonsfs);
		                    parent.center.center.addHighlight(highlightGraphic);
		                    parent.center.center.setMapExtent(polygon.getExtent().expand(3));
                        }
	                });
                }
            }
        },{
            text: '保存',
            handler: function(){
                alert();
            }
        }]
    });
	
	
});