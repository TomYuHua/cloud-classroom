// common variables
var uploadedSize = 0;
var totalSize = 0;
var alreadyLoadedByte = 0;
var iMaxFilesize = 1024 * 1024 * 1024 * 5; // 1MB
var oTimer = 0;
var sResultFileSize = '';

function secondsToTime(secs) {
	// in normal time format
	var hr = Math.floor(secs / 3600);
	var min = Math.floor((secs - (hr * 3600)) / 60);
	var sec = Math.floor(secs - (hr * 3600) - (min * 60));

	if (hr < 10) {
		hr = "0" + hr;
	}
	if (min < 10) {
		min = "0" + min;
	}
	if (sec < 10) {
		sec = "0" + sec;
	}
	if (hr) {
		hr = "00";
	}
	return hr + ':' + min + ':' + sec;
};

function bytesToSize(bytes) {
	var sizes = ['Bytes', 'KB', 'MB'];
	if (bytes == 0)
		return 'n/a';
	var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
	return (bytes / Math.pow(1024, i)).toFixed(1) + ' ' + sizes[i];
};

var docSuffix = new Array(".doc", ".docx");

String.prototype.extension = function() {
	var suffixName = null;
	var name = this.toLowerCase();
	var index = name.lastIndexOf(".");
	if (index > -1) {
		var suffixName = name.substring(index);
	}
	return suffixName;
}

Array.prototype.isContain = function(obj) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] === obj) {
			return true;
		}
	}
	return false;
};

String.prototype.suffixMatch = function(suffixType) {
	if (suffixType.isContain(this.extension())) {
		return true;
	} else {
		return false;
	}
}

function fileSelected() {
	console.log($("#uploadfile")[0].value);

	if ($("#uploadfile")[0].value.suffixMatch(docSuffix)) {
		alert("选的文件格式符合要求");
	} else {
		alert("选的文件格式···不····符合要求");
	}

	// hide different warnings
	document.getElementById('upload_response').style.display = 'none';
	document.getElementById('error').style.display = 'none';
	document.getElementById('error2').style.display = 'none';
	document.getElementById('abort').style.display = 'none';
	document.getElementById('warnsize').style.display = 'none';

	// get selected file element
	var fileList = document.getElementById('uploadfile');
	var list = $('#uploadfile').files;
	// filter for image files
	var filter = /^(image\/bmp|image\/gif|image\/jpeg|image\/png|image\/tiff)$/i;
	var show = true;

	for (var i = 0; i < list.length; i++) {

	}
	for (var i = 0; i < fileList.childNodes.length; i++) {

		if (filter.test(fileList.childNodes[i].type) && show == true) {
			show = false;
			document.getElementById('error').style.display = 'block';
			return;
		}

		// little test for filesize
		if (oFile.size > iMaxFilesize && show == true) {
			show = false;
			document.getElementById('warnsize').style.display = 'block';
			return;
		}
	}

}

function startUpload() {
	console.log($("#uploadfile")[0].value);
	// cleanup all temp states
	alreadyLoadedByte = 0;
	document.getElementById('upload_response').style.display = 'none';
	document.getElementById('error').style.display = 'none';
	document.getElementById('error2').style.display = 'none';
	document.getElementById('abort').style.display = 'none';
	document.getElementById('warnsize').style.display = 'none';
	document.getElementById('progressbar').style.display = 'block';
	document.getElementById('progress_percent').innerHTML = '';
	var progressbar = document.getElementById('imgprogressbar');
	progressbar.style.display = 'block';
	progressbar.style.width = '0px';

	// var fromData = document.getElementById('upload_form').getFormData();

	var fromData = new FormData(document.getElementById('upload_form'));

	var xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.upload.addEventListener('progress', uploadProgress, true);
	xmlHttpRequest.addEventListener('load', uploadFinish, true);
	xmlHttpRequest.addEventListener('error', uploadError, true);
	xmlHttpRequest.addEventListener('abort', uploadAbort, true);
	xmlHttpRequest.open('POST', '/hello/uploadbigfiles');
	xmlHttpRequest.send(fromData);

	oTimer = setInterval(doInnerUpdates, 400);
}
// 显示上传速度
function doInnerUpdates() {
	var m = uploadedSize;
	var iDiff = m - alreadyLoadedByte;

	// 没变化返回
	if (iDiff == 0)
		return;

	alreadyLoadedByte = m;
	iDiff = iDiff * 3;
	var iBytesRem = totalSize - alreadyLoadedByte;
	var secondsRemaining = iBytesRem / iDiff;

	// update speed info
	var iSpeed = iDiff.toString() + 'B/s';
	if (iDiff > 1024 * 1024) {
		iSpeed = (Math.round(iDiff * 100 / (1024 * 1024)) / 100).toString()
				+ 'MB/s';
	} else if (iDiff > 1024) {
		iSpeed = (Math.round(iDiff * 100 / 1024) / 100).toString() + 'KB/s';
	}

	document.getElementById('speed').innerHTML = iSpeed;
	document.getElementById('remaining').innerHTML = '| '
			+ secondsToTime(secondsRemaining);
}
// 显示上传进度信息
function uploadProgress(e) {
	if (e.lengthComputable) {
		uploadedSize = e.loaded;
		totalSize = e.total;
		console.log("已经上传大小：" + uploadedSize);
		var completePercent = Math.round(e.loaded * 100 / e.total);
		console.log("已经上传百分比：" + completePercent + "%");
		var transferedSize = bytesToSize(uploadedSize);

		document.getElementById('progress_percent').innerHTML = completePercent
				.toString()
				+ '%';
		var progressbarWidth = document.getElementById('progressbar').style.width
				.replace("px", "");

		console.log("设置宽度："
				+ parseInt(progressbarWidth * completePercent / 100) + 'px');
		document.getElementById('imgprogressbar').style.width = parseInt(progressbarWidth
				* completePercent / 100)
				+ 'px';
		document.getElementById('b_transfered').innerHTML = transferedSize;
		if (completePercent != 100) {
			var oUploadResponse = document.getElementById('upload_response');
			oUploadResponse.innerHTML = '<h1>上传中...请等待</h1>';
			oUploadResponse.style.display = 'block';
		}
	} else {
		document.getElementById('progressbar').innerHTML = '上传中...请等待';
	}
}

function uploadFinish(e) {
	var oUploadResponse = document.getElementById('upload_response');
	// 解析 返回文本

	oUploadResponse.innerHTML = e.target.responseText;
	oUploadResponse.style.display = 'block';

	document.getElementById('progress_percent').innerHTML = '100%';
	document.getElementById('imgprogressbar').style.width = '400px';
	// document.getElementById('filesize').innerHTML = sResultFileSize;
	document.getElementById('remaining').innerHTML = '| 00:00:00';

	clearInterval(oTimer);
}

function uploadError(e) {
	document.getElementById('error2').style.display = 'block';
	clearInterval(oTimer);
}

function uploadAbort(e) {
	document.getElementById('abort').style.display = 'block';
	clearInterval(oTimer);
}