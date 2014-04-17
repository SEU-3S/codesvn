/**
 * @author Administrator
 */
function formInitAll()
{
	formInit("");
}
function formInit(initElementTypes)
{
  if(initElementTypes=="")
  {	  
	  inputInit("");
	  selectInit();
	
	  textAreaInit();
	 
  }
  else
  {
	  var types=initElementTypes.split(",");
	  var inputtypes=new Array();
	  var index=0;
	  for(var i=0;i<types.length;i++)
	  {
		  if(types[i]=="select")
			  selectInit();
		  else if(types[i]=="textArea")
			  textAreaInit();
		  else
			  inputtypes[index++]=types[i];
	  }
	  if(inputtypes.length>0)
	  {
		  inputInit(inputtypes);
	  }
		 
  }
  
}

function textAreaInit()
{
   var areaArr=document.getElementsByTagName("textArea");
   for(var i=0;i<areaArr.length;i++)
   {
       setArea(areaArr[i]);
   }
}

function selectInit()
{
   var selectArr=document.getElementsByTagName("select");
   for(var i=0;i<selectArr.length;i++)
   {
       setSelect(selectArr[i]);
   }
}

function inputInit(types)
{
  var hasbutton=false;
  var hastext=false;
  var hascheckbox=false;
 
  if(types=="")
   {	  
	  hasbutton=hastext=hascheckbox=true;
   }
  else
  {
	  
	  for(var i=0;i<types.length;i++)
	  {
		  if(types[i]=="button")
			  hasbutton=true;
		  else if(types[i]=="text")
			  hastext=true;
		  else if(types[i]=="checkbox")
			  hascheckbox=true;
	  }
	  
  }
  var inputArr=document.getElementsByTagName("input");
  var inputTag;

  for(var i=0;i<inputArr.length;i++)
  {
  	inputTag=inputArr[i];
	var inputSplit = inputTag.value.split(' ');
	if(inputSplit.length == 2){
	if(inputSplit[1] == "00:00:00"){
	inputTag.value = inputSplit[0];
	}
	}
	if(hasbutton&&(inputTag.type=="button"||inputTag.type=="submit"||inputTag.type=="reset"))
              setButton(inputTag);
	if(hastext&&(inputTag.type=="text"||inputTag.type=="password"))
              setText(inputTag);
	if(hascheckbox&&(inputTag.type=="checkbox"))
              setCheckBox(inputTag);	
  }
}

function setArea(area)
{
 area.className="textArea";
}

function setSelect(select)
{
  select.className="select";
}

function setCheckBox(checkBox)
{
 checkBox.className="checkBox";
}

function setButton(btn)
{

  btn.className="button";
  btn.onmouseover=button_mouserover;
  btn.onmouseout=button_mouserout;

}

function setText(text)
{
	if(text.className!="Wdate")
  		text.className="text";
  	else
 		text.className="dateText";
}

function checkBox_click()
{
  var eve=getEvent();
  var checkBox=eve.srcElement;
 if(checkBox.style.borderColor=="#2E79B5")
     checkBox.style.borderColor= "#87C5F4";
 else
     checkBox.style.borderColor="#2E79B5";
}

function button_mouserout()
{
  var eve=getEvent();
  var btn=getEleByEvent(eve);
  btn.className="button";
}

function button_mouserover()
{
  var eve=getEvent();
  var btn=getEleByEvent(eve);
  btn.className="button_mouse";
}

function getEleByEvent(event)
{
   if(event.srcElement)
       return event.srcElement;
   return event.target;
}

function getEvent()
{	
	if(window.event)
		return window.event;
	func=getEvent.caller;
	while(func!=null)
	{
		var arg0=func.arguments[0];
		if(arg0)
		{
			if((arg0.constructor==Event || arg0.constructor ==MouseEvent)
			||(typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation))
			{
				return arg0;
			}
		}
		func=func.caller;
	}
	return null;
}
function oaAlert(msg)
{
	setTimeout(function()
	{
		alert(msg);
	}	
	,10);
}