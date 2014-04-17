/**
*根据cookie_name，获取cookie_value； add by 郭润沛 2011-5-31
 * @param {string} 名称
*/
//function getCookie(cookie_name)   
//{   
//	var allcookies = document.cookie;   
//	var cookie_pos = allcookies.indexOf(cookie_name);   
//	var cookie_value='';
	// 如果找到了索引，就代表cookie存在，   
	// 反之，就说明不存在。   
//	if (cookie_pos != -1) 
//		{   
		// 把cookie_pos放在值的开始，只要给值加1即可。   
//		cookie_pos += cookie_name.length + 1;   
//		var cookie_end = allcookies.indexOf(";", cookie_pos);   
//			if (cookie_end == -1)   
//				{   
//				cookie_end = allcookies.length;   
//				}   
//		cookie_value = unescape(allcookies.substring(cookie_pos, cookie_end));   
//		}   
//	return cookie_value;   
//} 


function getCookie(sName) 
{ 
var aCookie = document.cookie.split("; "); 
for (var i=0; i < aCookie.length; i++) 
{ 
   
  var aCrumb = aCookie[i].split("="); 
  if (sName == aCrumb[0]) 
  return unescape(aCrumb[1]); 
} 

}





/**
*设置cookie； add by 郭润沛 2011-5-31
 * @param {string} cookie_name：名称
 * @param {string} cookie_value：值
 * @param {int} expration_day:保存天数
*/
function setCookie(cookie_name,cookie_value,expration_day){
	var expiration = new Date((new Date()).getTime() + expration_day*24*60 * 60000);
	document.cookie=cookie_name+"="+escape(cookie_value)+"; expires ="+ expiration.toGMTString() ;
}