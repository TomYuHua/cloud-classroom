select * from resources;
--查询传入rootid及其以下所有子节点
DROP FUNCTION IF EXISTS GetChildNodeList;
CREATE FUNCTION `GetChildNodeList` (rootid INT)
RETURNS VARCHAR(8000)
BEGIN
DECLARE sTemp VARCHAR(8000);
DECLARE sTempChd VARCHAR(8000);

SET sTemp = '$';
SET sTempChd = cast(rootid as char);

WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT group_concat(id) INTO sTempChd FROM resources where FIND_IN_SET(parentId,sTempChd)>0;
END WHILE;
return sTemp;
END;

-调用方式
select GetChildNodeList(0);
select * from resources where FIND_IN_SET(id, GetChildNodeList(1)); 



DROP FUNCTION IF EXISTS GetNLevelChildNode;
CREATE FUNCTION `GetNLevelChildNode` (rootid INT,n INT)
RETURNS VARCHAR(8000)
begin
declare sTemp VARCHAR(4000);
declare sTempChd VARCHAR(4000);
declare i int;
set i=0;
SET sTemp = '$';
SET sTempChd = cast(rootid as char);
 
WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
if i<2 then
 
SELECT group_concat(DirectoriesId) INTO sTempChd FROM ChapterDirectories where FIND_IN_SET(ParentId,sTempChd)>0;
ELSE  
set sTempChd=NULL;
 
end if;
set i=i+1;
  
END WHILE;
return sTemp;
END

DROP FUNCTION IF EXISTS getParentList;
CREATE FUNCTION `getParentList` (rootid INT)
RETURNS VARCHAR(8000)
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);

SET sTemp='$';
SET sTempChd = CAST(areaId AS CHAR);
SET sTemp = CONCAT(sTemp,',',sTempChd);

SELECT parentId INTO sTempChd FROM resources WHERE id = sTempChd;
WHILE sTempChd <> 0 DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT parentId INTO sTempChd FROM resources WHERE id = sTempChd;
END WHILE;
RETURN sTemp;
END;

