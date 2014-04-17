﻿﻿﻿﻿﻿Ext.onReady(function() {
    var locationForm = new Ext.FormPanel({
        labelWidth: 45, // label settings here cascade unless overridden
        labelAlign:"center",
        frame:true,
        bodyStyle:'padding:0px 0px 0',
        width: 300,
        defaults: {width: 135},
        defaultType: 'textfield',

    submit:function(){
        var xpoint = locationForm.get('x').getValue();
        var ypoint = locationForm.get('y').getValue();
        if(xpoint == null || ypoint == null || xpoint == "" || ypoint == ""){
            return;
        }
        //parent.center.doLocationItWithPoint('location', xpoint, ypoint,true,true);
        parent.center.frames["lower"].swfobject.getObjectById("FxGIS").setCenterAt(xpoint,ypoint);
    },

      reset:function(){
          this.getEl().dom.reset();
          //parent.center.clearMap('location');
      },
        items: [{
                fieldLabel: 'X(E)',
                id: 'x',
                minValue: 0,
                maxValue: 250,
                allowDecimals: true,
                decimalPrecision: 1,
                incrementValue: 0.4,
                alternateIncrementValue: 2.1,
                accelerate: true
            },{
                fieldLabel: 'Y(N)',
                id:'y',
                name: 'y',
                minValue: 0,
                maxValue: 250,
                allowDecimals: true,
                decimalPrecision: 1,
                incrementValue: 0.4,
                alternateIncrementValue: 2.1,
                accelerate: true
            }
        ],
        buttons: [{
            text: '定位',handler: function(){
               // loadData();
                locationForm.form.submit();
            }
        },{
            text: '重置',
            type:'reset',
            handler: function(){
                locationForm.form.reset();
            }
        }]
    });
    
    var myData = [['','','','','','','' ]];

    function loadData(){
        var xpointdi = locationForm.get('x').getValue();
        var ypointdi = locationForm.get('y').getValue();
        if(xpointdi == null || ypointdi == null || xpointdi == "" || ypointdi == ""){
            return;
        }else{
            if(xpointdi > 20000){
                parent.center.putClientCommond("location","changeMe");
                parent.center.putRestParameter("_$sid","aaa");
                parent.center.putRestParameter("x",xpointdi);
                parent.center.putRestParameter("y",ypointdi);
                var xy = parent.center.restRequest();
                locationForm.get('x').setValue(xy[0][0]);
                locationForm.get('y').setValue(xy[0][1]);
                xpointdi = locationForm.get('x').getValue();
                ypointdi = locationForm.get('y').getValue();
            }
        }
    }
    
    var _$ID = '';
    function change(val){
        if(val < 1000){
            return '<span style="color:green;">√</span>';
        }else if(val > 1000){
            return '<span style="color:red;size=20">×</span>';
        }
        return val;
    }
        var store = new Ext.data.ArrayStore({
        proxy: new Ext.ux.data.PagingMemoryProxy(myData),
        remoteSort:true,
        fields: [
           {name: 'objectid'},
           {name: 'no'},
           {name: 'di'},
           {name: 'xzmc'},
           {name: 'area'},
           {name: 'warning'}
           
        ]
    });
    store.loadData(myData);
       var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {header:'', width:0, sortable:true, dataIndex:'objectid'},
            {id:'no', header:'编号', width:15, sortable:false, dataIndex:'no'},
            {id:'no', header:'距离(米)', width:60, sortable:false, dataIndex:'di'},
            {id:'no', header:'政区', width:60, sortable:false, dataIndex:'xzmc'},
            {header:'图斑面积', width:85, sortable:true, dataIndex:'area'},
            {header:'报警', width:35, sortable:true, renderer:change, dataIndex:'warning'}
        ],
        stripeRows: true,
        autoExpandColumn: 'no',
        height: 440,
        width: 310,
        stateful: true,
        stateId: 'grid',
        
        plugins: new Ext.ux.PanelResizer({
            minHeight: 100
        }),

        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: store,
            displayInfo: false,
            plugins: new Ext.ux.ProgressBarPager()
        })
    });
    
        grid.on('cellclick',function(e){
        var row = grid.getSelectionModel().getSelected();
        _$ID = row.data.objectid;
        var url = 'http://wangyingbo:8399/arcgis/rest/services/YZ_WPZFJC/MapServer/0';
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
        query.outSpatialReference = parent.center.getMapSpatialReference();
        var queryTask = new esri.tasks.QueryTask(url);
        dojo.connect(queryTask, "onComplete", function(featureSet) {
            //parent.center.clearHighlight();
            var graphic = featureSet.features[0];
            graphic.setInfoTemplate(infoTemplate);
            //alert(featureSet.features[0].getContent());
            var highlightGraphic = new esri.Graphic(graphic.geometry,parent.center.mark);
            parent.center.addHighlight(highlightGraphic);
            parent.center.setMapExtent(graphic.geometry.getExtent().expand(7));
        });
        queryTask.execute(query);
        
        //fbutton.onEnable(true);
        
    });
    

    
    locationForm.render('form-ct');
    
});