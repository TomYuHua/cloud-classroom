 var degree=document.LY.LyHelperTools.getTeacherEducation();
 document.LY.LyHelperTools.rebuildSelectByStringList($("select[name='highDegree']"),degree);
 $("select[name='highDegree']").prepend("<option value='' selected>请选择 </option>");
 document.getElementById('degreeSelect').value =$("#highDegree").val();
 
 var jobTitle=document.LY.LyHelperTools.getTeacherJobTitle();
 document.LY.LyHelperTools.rebuildSelectByStringList($("select[name='jobTitle']"),jobTitle);
 $("select[name='jobTitle']").prepend("<option value='' selected>请选择 </option>");
 document.getElementById('titleSelect').value =$("#jobTitle").val();