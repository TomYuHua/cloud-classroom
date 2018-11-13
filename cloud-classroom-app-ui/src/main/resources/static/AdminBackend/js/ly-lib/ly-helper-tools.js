/**
 * Created by Jeremie on 2017/07/15.
 */
'use strict';

// 工具函数
(function () {

    document.LY = document.LY || {};
    document.LY.LyHelperTools = document.LY.LyHelperTools || {};
    var ThisPageObject = document.LY.LyHelperTools;

    ThisPageObject.removeNil = function (object) {
        return _.omitBy(object, function (T) {
            return _.isNil(T);
        });
    };
    ThisPageObject.object2queryString = function (object) {
        return $.param(ThisPageObject.removeNil(object));
    };
    ThisPageObject.removeNilEmpty = function (object) {
        return _.omitBy(object, function (T) {
            return _.isNil(T) || T === "";
        });
    };
    ThisPageObject.removeNilDeep = function (object) {
        var remover = function (T) {
            if (_.isObject(T)) {
                return _.omitBy(T, function (T) {
                    return _.isNil(T);
                });
            }
            if (_.isArray(T)) {
                return _.reduce(T, function (S, T) {
                    if (!_.isNil(T)) {
                        S.push(remover(T));
                    }
                    return S;
                }, []);
            }
            return T;
        };
        return remover(object);
    };

    ThisPageObject.getTeacherJobTitle = function () {
        // 职称
        return [
            {"id": "10", "name": "高等学校教师"},
            {"id": "11", "name": "教授"},
            {"id": "12", "name": "副教授"},
            {"id": "13", "name": "讲师（高校）"},
            {"id": "14", "name": "助教（高校）"},
            {"id": "20", "name": "中等专业学校教师"},
            {"id": "22", "name": "高级讲师（中专）"},
            {"id": "23", "name": "讲师（中专）"},
            {"id": "24", "name": "助理讲师（中专）"},
            {"id": "25", "name": "教员（中专）"},
            {"id": "30", "name": "技工学校教师"},
            {"id": "32", "name": "高级讲师（技校）"},
            {"id": "33", "name": "讲师（技校）"},
            {"id": "34", "name": "助理讲师（技校）"},
            {"id": "35", "name": "教员（技校）"},
            {"id": "40", "name": "技工学校教师（实习指导）"},
            {"id": "42", "name": "高级实习指导教师"},
            {"id": "43", "name": "一级实习指导教师"},
            {"id": "44", "name": "二级实习指导教师"},
            {"id": "45", "name": "三级实习指导教师"},
            {"id": "50", "name": "中学教师"},
            {"id": "52", "name": "高级教师（中学）"},
            {"id": "53", "name": "一级教师（中学）"},
            {"id": "54", "name": "二级教师（中学）"},
            {"id": "55", "name": "三级教师（中学）"},
        ].map(function (T) {
            return T.name;
        });
    };

    ThisPageObject.getTeacherEducation = function () {
        // 学历
        return [
            {id: 0, name: "博士研究生"},
            {id: 1, name: "硕士研究生"},
            {id: 2, name: "本科"},
            {id: 3, name: "专科"},
            {id: 4, name: "中专"},
            {id: 5, name: "高中"}
        ].map(function (T) {
            return T.name;
        });
    };

    ThisPageObject.rebuildSelectByStringList = function (selectNode, stringList) {
        selectNode.empty();
        stringList.forEach(function (T) {
            $(selectNode).append('<option value="' + T + '">' + T + '</option>');
        })
    };

    /**
     * Creates an Object with all values removed that remover return false.
     * @param obj
     * @param remover?  (value, key) => boolean   default remove null or undefined when remover===undefined
     * @returns {any|Array|{}}
     */
    ThisPageObject.compactObjectDeep = function (obj, remover) {
        if (!_.isFunction(remover)) {
            remover = function (v, k, org) {
                return _.isUndefined(v) || _.isNull(v);
            };
        }
        var ObjectCompacter = function (old) {
            if (!_.isObject(old) && !_.isArray(old)) {
                return old;
            }
            if (_.isArray(old)) {
                var newArray = [];
                old.forEach(
                    function (T, index) {
                        if (!remover(T, index, old)) {
                            newArray.push(ObjectCompacter(T));
                        }
                    }
                );
                return newArray;
            }
            if (_.isObject(old)) {
                var newObject = {};
                _.forOwn(
                    old,
                    function (v, k) {
                        if (!remover(v, k, old)) {
                            newObject[k] = ObjectCompacter(v);
                        }
                    }
                );
                return newObject;
            }
        };
        return ObjectCompacter(obj);
    };

    var FileExtension = {
        PDF: ['.pdf'],
        WORD: ['.doc', '.docx', '.xml'],
        VIDEO: ['.3gp', '.avi', '.divx', '.flv', '.m4v', '.mkv', '.mov', '.mp4', '.mpeg', '.mpg', '.ogm', '.ogv', '.ogx', '.rm', '.rmvb', '.smil', '.webm', '.wmv', '.xvid'],
        IMAGE: ['.jpe', '.jpeg', '.jpg', '.gif', '.png', '.bmp'],
    };
    ThisPageObject.checkFileType = function (fileType, fileName) {
        if (!_.has(FileExtension, fileType)) {
            throw Error('checkFileExtension fileType unknown.');
        }
        if (!_.isString(fileName)) {
            console.warn('checkFileExtension fileName !isString.');
            return false;
        }
        fileName = fileName.toLowerCase();
        return _.find(_.get(FileExtension, fileType), function (T) {
            return fileName.endsWith(T);
        }) !== undefined;
    };

    ThisPageObject.checkPhoneNumber = function (phoneNumber) {
        var regex = /^\d{11}$/;
        if (!_.isString(phoneNumber)) {
            console.warn('checkPhoneNumber phoneNumber !isString.');
            return false;
        }
        return regex.test(phoneNumber);
    };

    ThisPageObject.checkChineseName = function (chineseName) {
        var regex = /^(?:[\u4E00-\u9FCC\u3400-\u4DB5\uFA0E\uFA0F\uFA11\uFA13\uFA14\uFA1F\uFA21\uFA23\uFA24\uFA27-\uFA29]|[\ud840-\ud868][\udc00-\udfff]|\ud869[\udc00-\uded6\udf00-\udfff]|[\ud86a-\ud86c][\udc00-\udfff]|\ud86d[\udc00-\udf34\udf40-\udfff]|\ud86e[\udc00-\udc1d])+$/;
        if (!_.isString(chineseName)) {
            console.warn('checkChineseName chineseName !isString.');
            return false;
        }
        return regex.test(chineseName);
    };

    ThisPageObject.checkEmail = function (email) {
        var regex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
        if (!_.isString(email)) {
            console.warn('checkEmail email !isString.');
            return false;
        }
        return regex.test(email);
    };

    ThisPageObject.sex2String = function (sex) {
        switch (_.parseInt(sex)) {
            case 0:
                return "男";
            case 1:
                return "女";
            default:
                return "";
        }
    };
    ThisPageObject.reviewState2String = function (reviewState) {
        switch (_.parseInt(reviewState)) {
            case 0:
                return "未审核";
            case 1:
                return "通过";
            case 2:
                return "不通过";
            default:
                return "";
        }
    };
    ThisPageObject.UnixTimestampMilliseconds2DateTimeString = function (timestamp) {
        return moment(timestamp).format("YYYY-MM-DD HH:mm:ss");
    };
    ThisPageObject.UnixTimestampMilliseconds2DateString = function (timestamp) {
        return moment(timestamp).format("YYYY-MM-DD");
    };
    ThisPageObject.list2listStringSplit = function (list, Split, withEndSplit) {
        if (_.isNil(Split)) {
            Split = ",";
        }
        return _.trimEnd(_.reduce(list, function (S, T) {
            return S + T + Split;
        }, ""), withEndSplit ? "" : Split);
    };
    ThisPageObject.AJAX = {};
    ThisPageObject.AJAX.ajax = function (url, config) {
        return new Promise(function (resolve, reject) {
            $.ajax(
                url,
                config
            ).done(function (data) {
                resolve(data);
            }).fail(function (xhr, status) {
                reject({
                    xhr: xhr,
                    status: status,
                });
            }).always(function () {
                if (document.isJsDebug) {
                    console.log({
                        url: url,
                        config: config,
                        debug: "debug",
                    });
                }
            });
        }).catch(function (T) {
            toastr.warning('请检查您的网络连接', '网络错误', {timeout: 0, extendedTimeOut: 0});
            return Promise.reject(T);
        });
    };
    ThisPageObject.AJAX.ajax4FormData = function (url, formData, config) {
        // https://developer.mozilla.org/zh-CN/docs/Web/API/FormData/Using_FormData_Objects
        return ThisPageObject.AJAX.ajax(url, _.assign(config, {
            type: "POST",
            data: formData,
            processData: false,  // 不处理数据
            contentType: false   // 不设置内容类型
        }));
    };
    ThisPageObject.AJAX.get = function (url, config) {
        return ThisPageObject.AJAX.ajax(url, _.assign(config, {method: 'GET', dataType: 'json'}));
    };
    ThisPageObject.AJAX.getQuery = function (url, data, config) {
        return ThisPageObject.AJAX.get(url, _.assign(config, {
            data: data && ThisPageObject.object2queryString(data),
        }));
    };
    ThisPageObject.AJAX.post = function (url, config) {
        return ThisPageObject.AJAX.ajax(url, _.assign(config, {method: 'POST', dataType: 'json'}));
    };
    ThisPageObject.AJAX.postJson = function (url, data, config) {
        return ThisPageObject.AJAX.post(url, _.assign(config, {
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data && ThisPageObject.removeNil(data)),
        }));
    };
    ThisPageObject.AJAX.postQuery = function (url, data, config) {
        return ThisPageObject.AJAX.post(url, _.assign(config, {
            data: data && ThisPageObject.object2queryString(data),
        }));
    };
    ThisPageObject.HTTP = {};
    ThisPageObject.HTTP.getQuery = function (urlName, data, config) {
        var url = document.GLOBAL_CONFIG.HTTP.getUrl(urlName);
        if (url === "")
            throw new Error("getQuery urlName error:", urlName);
        return ThisPageObject.AJAX.getQuery(url, data, config).catch(function (T) {
            toastr.info(urlName, '网络错误', {timeout: 0, extendedTimeOut: 0});
            return Promise.reject(T);
        });
    };
    ThisPageObject.HTTP.postJson = function (urlName, data, config) {
        var url = document.GLOBAL_CONFIG.HTTP.getUrl(urlName);
        if (url === "")
            throw new Error("postJson urlName error:", urlName);
        return ThisPageObject.AJAX.postJson(url, data, config).catch(function (T) {
            toastr.info(urlName, '网络错误', {timeout: 0, extendedTimeOut: 0});
            return Promise.reject(T);
        });
    };
    ThisPageObject.HTTP.postQuery = function (urlName, data, config) {
        var url = document.GLOBAL_CONFIG.HTTP.getUrl(urlName);
        console.log(url);
        if (url === "")
            throw new Error("postQuery urlName error:", urlName);
        return ThisPageObject.AJAX.postQuery(url, data, config).catch(function (T) {
            toastr.info(urlName, '网络错误', {timeout: 0, extendedTimeOut: 0});
            return Promise.reject(T);
        });
    };
    ThisPageObject.HTTP.sendFormData = function (urlName, formData, config) {
        var url = document.GLOBAL_CONFIG.HTTP.getUrl(urlName);
        console.log(url);
        if (url === "")
            throw new Error("SendFormData urlName error:", urlName);
        return ThisPageObject.AJAX.ajax4FormData(url, formData, config).catch(function (T) {
            toastr.info(urlName, '网络错误', {timeout: 0, extendedTimeOut: 0});
            return Promise.reject(T);
        });
    };
    ThisPageObject.HTTP.sendFormDataReturnJson = function (urlName, formData, config) {
        return ThisPageObject.HTTP.sendFormData(urlName, formData, config).then(function (T) {
            return JSON.parse(T);
        });
    }

}());

