/**
 * 将Object rings 转为JSON
 * {\"rings\":[[[40449368,3620962],[40449375,3620791],[40449368,3620962]]],\"spatialReference\":{\"wkid\":2364}}
 */
function toJsonString(rings) {
	if (rings.rings != null && rings.spatialReference != null) {
		var jsonStr = '{\"rings\":[';
		var arr = rings.rings;
		for (var i = 0; i < arr.length; i++) {
			jsonStr += '[';
			for (var y = 0; y < arr[i].length; y++) {
				jsonStr += '[' + arr[i][y][0] + ',' + arr[i][y][1] + ']';
				if (y < arr[i].length - 1) {
					jsonStr += ',';
				}
			}
			jsonStr += ']';
			if (i < arr.length - 1) {
				jsonStr += ',';
			}
		}
		jsonStr += '],\"spatialReference\":{\"wkid\":'
				+ rings.spatialReference.wkid + '}';
		return jsonStr + "}";
	} else {
		return null;
	}
}
