package com.klspta.model.CBDReport.bean;

public class TDBean {
	private String colspan = "1";
	private String rowspan = "1";
	private String data = "";
	private String dataType = "";
	private String text = "";
	private String thirdKey = "";
	private String YWGuid = "";
	private String width = "10";
	private String height = "10";
	private String style = ""; 
	private String editable = ""; 
	
    public TDBean(ThirdBean tb) {
        this.colspan = tb.getColsPan();
        this.rowspan = tb.getRowsPan();
        this.data = tb.getData();
        this.dataType = tb.getDataType();
        this.thirdKey = tb.getThirdKey();
        this.YWGuid = tb.getYWGuid();
        this.text = tb.getData();
        this.width = tb.getWidth();
        this.height = tb.getHeight();
        setWidth(width);
        setHeight(height);
	}
	
	public TDBean(String text, String width, String height, String editable){
	    this.text = text;
	    setWidth(width);
	    setHeight(height);
	    setEditable(editable);
	}
	
	public TDBean(String text, String width, String height){
	    this(text,width,height,"false");
	}

	private void setWidth(String width){
	    this.width = width.equals("") ? "10": width;
	}
	private void setHeight(String height){
	    this.height = height.equals("") ? "10": height;
	}

    public String getColspan() {
        return colspan;
    }

    public String getRowspan() {
        return rowspan;
    }

    public String getData() {
        return data;
    }

    public String getDataType() {
        return dataType;
    }

    public String getText() {
        return text;
    }

    public String getThirdKey() {
        return thirdKey;
    }

    public String getYWGuid() {
        return YWGuid;
    }
    
    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}
	
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setRowspan(String rowspan) {
        this.rowspan = rowspan;
    }

    public void setColspan(String colspan) {
        this.colspan = colspan;
    }
}
