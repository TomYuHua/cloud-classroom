/**
 * Created by Jeremie on 2017/07/15.
 */
'use strict';

// PAGE users class
(function () {

    document.PAGE = document.PAGE || {};
    document.PAGE.users = {
        // 测试用虚拟数据初始化函数
        _initMockFunctions: {},
        // 私有初始化函数
        _initFunctions: {},
        // 私有内部变量
        _values: {},
        data: {
            deleteUserNameList: [],
            userResetNameList: [],
            userChangeTypeDataList: [],
            filterFormData: {},
            userChangeStudentFormData: {},
            userChangeTeacherFormData: {},
            userChangeRoleFormData: {},
            FileRootPath: undefined,
            UserRoleList: [],
            IDValidator: undefined,
        },
        functions: {},
        toolFunctions: {},
    };
    var ThisPageObject = document.PAGE.users;
    ThisPageObject._values = {
        mainTableManager: null,
        paginationManager: null,
        leftMenuManager: null,
        oneWayBinderManager: null,
    };

    // 初始化函数
    ThisPageObject.onInit = function () {

        ThisPageObject._initFunctions.initIDValidator();
        ThisPageObject._initFunctions.initToastrGlobal();
        ThisPageObject._initFunctions.initModalGlobal();
        ThisPageObject._initFunctions.initDatetimepickerGlobal();
        ThisPageObject._initFunctions.initAllTeacherJobTitle();
        ThisPageObject._initFunctions.initAllTeacherEducation();
        ThisPageObject._initFunctions.leftMenuManager();
        ThisPageObject._initFunctions.mainTableManager();
        ThisPageObject._initFunctions.paginationManager();
        ThisPageObject._initFunctions.oneWayBinderManager();
        ThisPageObject._initFunctions.filterForm();
        ThisPageObject._initFunctions.buttonPanel();
        ThisPageObject._initFunctions.userAddStudentForm();
        ThisPageObject._initFunctions.userAddTeacherForm();
        ThisPageObject._initFunctions.userChangeStudentForm();
        ThisPageObject._initFunctions.userChangeTeacherForm();
        ThisPageObject._initFunctions.userDeleteForm();
        ThisPageObject._initFunctions.userReviewForm();
        ThisPageObject._initFunctions.userChangeRoleForm();
        ThisPageObject._initFunctions.userTypeChangeLoadingForm();
        ThisPageObject._initFunctions.userTypeChangeStudentForm();
        ThisPageObject._initFunctions.userTypeChangeTeacherForm();
        ThisPageObject._initFunctions.userResetPasswdForm();

        ThisPageObject._values.mainTableManager.LyListSelectTableManger.changeCallBackList.push(
            function (NodeList) {
                // console.log(NodeList);
                var buttonPanel = $('.button-panel');
                if (NodeList.length === 0) {
                    $(buttonPanel).find('.b-delete').prop('disabled', true);
                    $(buttonPanel).find('.b-changeRole').prop('disabled', true);
                    $(buttonPanel).find('.b-review').prop('disabled', true);
                }
                if (NodeList.length === 1) {
                    $(buttonPanel).find('.b-reset').prop('disabled', false);
                    var list = ThisPageObject._values.mainTableManager.getSelectedDataList();
                    if (list[0].userType === 0) {
                        $(buttonPanel).find('.b-change').prop('disabled', true);
                        $(buttonPanel).find('.b-set-user-type').prop('disabled', false);
                    } else {
                        $(buttonPanel).find('.b-change').prop('disabled', false);
                        $(buttonPanel).find('.b-set-user-type').prop('disabled', true);
                    }
                    if (list[0].review !== 1 && list[0].review !== 2) {
                        $(buttonPanel).find('.b-review').prop('disabled', false);
                    } else {
                        $(buttonPanel).find('.b-review').prop('disabled', true);
                    }
                } else {
                    $(buttonPanel).find('.b-change').prop('disabled', true);
                    $(buttonPanel).find('.b-reset').prop('disabled', true);
                    $(buttonPanel).find('.b-set-user-type').prop('disabled', true);
                }
                if (NodeList.length > 0) {
                    $(buttonPanel).find('.b-delete').prop('disabled', false);
                    $(buttonPanel).find('.b-changeRole').prop('disabled', false);
                }
                if (NodeList.length > 1) {
                    $(buttonPanel).find('.b-review').prop('disabled', false);
                }
            }
        );
        ThisPageObject._values.mainTableManager.LyListSelectTableManger.noticeCallback();
        ThisPageObject._values.mainTableManager.selectedChangeCallback = function (dataList) {
            console.log(dataList);
        };
        ThisPageObject._values.mainTableManager.afterReInitCallback = function (rootNode) {
            ThisPageObject.functions.reInitRowButtonPanel();
        };

        /*
        // var paginationManager = ThisPageObject._values.paginationManager;
        // ThisPageObject._values.paginationManager.callback.click = function (index) {
        //     // mock
        //     paginationManager.flush({
        //         nowPageNumber: index
        //     });
        //     ThisPageObject.functions.flushForm();
        // };
        // ThisPageObject._values.paginationManager.callback.go = function (index) {
        //     // mock
        //     switch (index) {
        //         case 'next':
        //             paginationManager.config.nowPageNumber += 1;
        //             break;
        //         case 'prev':
        //             paginationManager.config.nowPageNumber -= 1;
        //             break;
        //         case 'first':
        //             paginationManager.config.nowPageNumber = 1;
        //             break;
        //         case 'last':
        //             paginationManager.config.nowPageNumber = paginationManager.config.totalPageNumber;
        //             break;
        //     }
        //     if (paginationManager.config.nowPageNumber < 1) {
        //         paginationManager.config.nowPageNumber = 1;
        //     }
        //     if (paginationManager.config.nowPageNumber > paginationManager.config.totalPageNumber) {
        //         paginationManager.config.nowPageNumber = paginationManager.config.totalPageNumber;
        //     }
        //     paginationManager.flush();
        //     ThisPageObject.functions.flushForm();
        // };
        */
        ThisPageObject._values.paginationManager.callback.nextGo = function (index) {
            ThisPageObject.functions.flushForm();
        };

        ThisPageObject._values.oneWayBinderManager.createBinder({
            rootObjectRef: ThisPageObject.data,
            valuePath: 'deleteUserNameList',
            JQNode: $('#delete-user-name-list'),
            type: 'html',
            translator: function (D) {
                return _.reduce(D, function (S, T) {
                    return S + (T || '') + "<br/>";
                }, "");
            }
        });
        ThisPageObject._values.oneWayBinderManager.createBinder({
            rootObjectRef: ThisPageObject.data,
            valuePath: 'userResetNameList',
            JQNode: $('#user-reset-name-list'),
            type: 'html',
            translator: function (D) {
                return _.reduce(D, function (S, T) {
                    return S + (T || '') + "<br/>";
                }, "");
            }
        });

        ThisPageObject._values.oneWayBinderManager.flush();
        ThisPageObject.functions.flushUserRoleList().finally(function () {
            ThisPageObject.functions.flushForm();
        });

        // Mock
        // ThisPageObject._initMockFunctions.initTableData();
        console.log(ThisPageObject.data.IDValidator.makeID());
    };

    ThisPageObject._initFunctions.initAllTeacherJobTitle = function () {
        var selectNode = $('select.f-jobTitle');
        selectNode.empty();
        _.forEach(document.LY.LyHelperTools.getTeacherJobTitle(), function (T) {
            $(selectNode).append('<option value="' + T + '">' + T + '</option>');
        });
    };
    ThisPageObject._initFunctions.initAllTeacherEducation = function () {
        var selectNode = $('select.f-highDegree');
        selectNode.empty();
        _.forEach(document.LY.LyHelperTools.getTeacherEducation(), function (T) {
            $(selectNode).append('<option value="' + T + '">' + T + '</option>');
        });
    };

    ThisPageObject._initFunctions.initIDValidator = function () {
        ThisPageObject.data.IDValidator = new IDValidator();
    };

    ThisPageObject._initFunctions.initToastrGlobal = function () {
        window.toastr.options = {
            "closeButton": true,
            "debug": false,
            "newestOnTop": true,
            "progressBar": false,
            "positionClass": "toast-bottom-right",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "5000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        };
    };

    ThisPageObject._initFunctions.mainTableManager = function () {

        var fieldList = [
            'username',
            'name',
            'sex',
            'phone',
            'email',
            'userType',
            'userRole',
            'time',
            'review',
        ];
        if (!_.isObject(document.LY.LyTableManager)) {
            console.error("ly-table-manager not init.");
            return;
        } else {
            ThisPageObject._values.mainTableManager =
                document.LY.LyTableManager.createManager(
                    $(document.LY.LyTableManager.rootSelectString),
                    fieldList
                );
        }

        // init data2fieldProcessCallback
        ThisPageObject._values.mainTableManager.data2fieldProcessCallback = function (data, field, row) {
            if (field === 'sex') {
                return document.LY.LyHelperTools.sex2String(data);
            }
            if (field === 'review') {
                return document.LY.LyHelperTools.reviewState2String(data);
            }
            if (field === 'time') {
                return document.LY.LyHelperTools.UnixTimestampMilliseconds2DateTimeString(data);
            }
            if (field === 'userType') {
                switch (data) {
                    case 1:
                        return "学生";
                    case 2:
                        return "教师";
                    default:
                        return "其他";
                }
            }
            if (field === 'userRole') {
                var o = _.find(ThisPageObject.data.UserRoleList, function (T) {
                    return T.id === data;
                });
                if (o) {
                    return o.name;
                }
                return "未知角色";
            }
            return data;
        };

    };

    ThisPageObject._initFunctions.paginationManager = function () {


        if (!_.isObject(document.LY.LyPaginationManager)) {
            console.error("ly-pagination-manager not init.");
            return;
        } else {
            var pmr = $(document.LY.LyPaginationManager.rootSelectString)[0];
            ThisPageObject._values.paginationManager = document.LY.LyPaginationManager.createManagerEasy($(pmr));
        }
    };

    ThisPageObject.functions.reInitRowButtonPanel = function () {
        var rootNode = $(ThisPageObject._values.mainTableManager.getAllRowNode()).find('.row-button');

        $(rootNode).each(function () {
            var ThisPtr = $(this);
            var data = ThisPageObject._values.mainTableManager.getRowDataInRow(ThisPtr);
            console.log(data.review);
            if (_.parseInt(data.review) === 1 || _.parseInt(data.review) === 2) {
                ThisPtr.addClass('delete-line');
            }
        });

        $(rootNode).find('.b-review').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            if (rowData.review !== 1 && rowData.review !== 2) {
                ThisPageObject.functions.flushUserReviewForm(rowData.id, rowData.userType);
                $('#f-user-review').modal();
            }
        });
        $(rootNode).find('.b-reset').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.data.userResetNameList = [rowData.username];
            ThisPageObject._values.oneWayBinderManager.flush();
            $('#f-user-reset').modal();
        });
        $(rootNode).find('.b-change').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            switch (rowData.userType) {
                case 1:
                    ThisPageObject.functions.openUserChangeStudentForm(rowData);
                    break;
                case 2:
                    ThisPageObject.functions.openUserChangeTeacherForm(rowData);
                    break;
                default:
                    break;
            }
        });
        $(rootNode).find('.b-changeRole').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.functions.openUserChangeRoleForm([rowData]);
        });
        $(rootNode).find('.b-delete').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.data.deleteUserNameList = [rowData.username];
            ThisPageObject._values.oneWayBinderManager.flush();
            $('#f-user-delete').modal();
        });
    };

    ThisPageObject._initFunctions.filterForm = function () {

        var filterForm = $('#filter-form');

        var nameList = [
            'role',
            'username',
            'name',
            // 'year',
            'sex',
            'email',
            'class',
        ];
        _.forEach(nameList, function (T) {
            $(filterForm).find('.filter-' + T).val();
        });

        var getFormData = function () {
            var data = {};
            _.forEach(nameList, function (T) {
                data[T] = $(filterForm).find('.filter-' + T).val();
            });
            return document.LY.LyHelperTools.removeNilEmpty(data);
        };

        ThisPageObject.functions.flushFilterForm = function () {
            ThisPageObject.data.filterFormData = getFormData();
        };

        $('#flush').on('click', function () {
            console.log('flush');
            ThisPageObject.functions.flushFilterForm();
            ThisPageObject.functions.flushForm();
        });
        ThisPageObject.functions.flushFilterForm();

    };

    ThisPageObject._initMockFunctions.initTableData = function () {
        // Mock
        ThisPageObject._values.mainTableManager.rebuild([
            {
                id: 0,
                username: 'username',
                name: 'name',
                sex: 'sex',
                year: 'year',
                phone: 'phone',
                email: 'email',
                time: '2017-09-07',
                review: 'review',
                userType: 0,
            },
            {
                id: 1,
                username: 'username',
                name: 'name',
                sex: 'sex',
                year: 'year',
                phone: 'phone',
                email: 'email',
                time: '2017-09-07',
                review: 'review',
                userType: 1,
            },
            {
                id: 2,
                username: 'username',
                name: 'name',
                sex: 'sex',
                year: 'year',
                phone: 'phone',
                email: 'email',
                time: '2017-09-07',
                review: 'review',
                userType: 2,
            },
            {
                id: 3,
                username: 'username',
                name: 'name',
                sex: 'sex',
                year: 'year',
                phone: 'phone',
                email: 'email',
                time: '2017-09-07',
                review: 'review',
                userType: 3,
            },
            {
                id: 4,
                username: 'username',
                name: 'name',
                sex: 'sex',
                year: 'year',
                phone: 'phone',
                email: 'email',
                time: '2017-09-07',
                review: 'review',
                userType: 0,
            },
        ]);
    };

    ThisPageObject.functions.flushUserRoleList = function () {
        return document.LY.LyHelperTools.HTTP.postQuery("Role_ListAll").then(function (T) {
            console.log(T);
            if (_.isArray(_.get(T, 'list'))) {
                ThisPageObject.data.UserRoleList = _.map(T.list, function (T) {
                    return {
                        id: _.get(T, 'roleid'),
                        name: _.get(T, 'rolename'),
                    };
                })
            }
        }).catch(function (E) {
            console.log(E);
        }).finally(function () {
            console.log("end");
            console.log("UserRoleList", ThisPageObject.data.UserRoleList);
        });
    };

    ThisPageObject.functions.flushForm = function () {
        ThisPageObject.functions.flushFilterForm();
        var data = {
            userType: ThisPageObject.data.filterFormData.role,
            userName: ThisPageObject.data.filterFormData.username,
            name: ThisPageObject.data.filterFormData.name,
            sex: ThisPageObject.data.filterFormData.sex,
            email: ThisPageObject.data.filterFormData.email,
            // pagination state
            page: ThisPageObject._values.paginationManager.getConfig().nowPageNumber,
        };
        document.LY.LyHelperTools.HTTP.postQuery(
            'Users_GetUserList',
            data
        ).then(function (data) {
            console.log(data);
            var information = _.get(data, 'information', undefined);
            if (information) {
                ThisPageObject._values.mainTableManager.rebuild(
                    _.map(_.get(information, 'dataPage', []), function (T) {
                        return {
                            id: _.get(T, 'userId', 0),
                            username: _.get(T, 'userName', ''),
                            name: _.get(T, 'name', ''),
                            sex: _.get(T, 'sex', null),
                            userType: _.get(T, 'userType', ''),
                            userRole: _.get(T, 'userRoleId', ''),
                            email: _.get(T, 'email', ''),
                            phone: _.get(T, 'phone', ''),
                            time: _.get(T, 'createTime', ''),
                            review: _.get(T, 'state', ''),
                            ImagePath: _.get(T, 'imgSrc'),
                            originData: T,
                        }
                    })
                );
                var pagination = _.get(information, 'pageInfro', undefined);
                if (pagination) {
                    ThisPageObject._values.paginationManager.flush({
                        totalPageNumber: _.parseInt(_.get(pagination, 'pages', 1)),
                        nowPageNumber: _.parseInt(_.get(pagination, 'pageNum', 1)),
                    });
                }
            } else {
                toastr.error('服务器错误，请手动刷新页面', '服务器错误');
            }
            if (_.isString(_.get(data, 'filesystem'))) {
                ThisPageObject.data.FileRootPath = _.get(data, 'filesystem');
            }
        }).catch(function (err) {
            console.log(err);
        }).finally(function () {
            console.log('end');
            ThisPageObject._values.mainTableManager.LyListSelectTableManger.noticeCallback();
        });
    };

    ThisPageObject._initFunctions.initModalGlobal = function () {
        // set modal lib global value
        _.set($.modal.defaults, 'closeExisting', false);
        _.set($.modal.defaults, 'escapeClose', false);
        _.set($.modal.defaults, 'clickClose', false);
        _.set($.modal.defaults, 'showClose', false);
    };

    ThisPageObject._initFunctions.buttonPanel = function () {
        var buttonPanel = $('.button-panel');

        $(buttonPanel).find('.b-set-user-type').on('click', function () {
            var list = ThisPageObject._values.mainTableManager.getSelectedDataList();
            ThisPageObject.functions.openUserTypeChangeLoadingForm();
        });

        $(buttonPanel).find('.b-review').on('click', function () {
            var list = ThisPageObject._values.mainTableManager.getSelectedDataList();
            if (list.length > 1) {
                ThisPageObject.functions.flushUserReviewForm(_.map(list, function (T) {
                    return T.id;
                }));
                $('#f-user-review').modal();
            } else {
                if (list[0].review !== 1 && list[0].review !== 2) {
                    ThisPageObject.functions.flushUserReviewForm(list[0].id, list[0].userType);
                    $('#f-user-review').modal();
                }
            }
        });

        $(buttonPanel).find('.b-reset').on('click', function () {
            ThisPageObject.data.userResetNameList =
                _.map(ThisPageObject._values.mainTableManager.getSelectedDataList(), function (T) {
                    return T.username;
                });
            ThisPageObject._values.oneWayBinderManager.flush();
            $('#f-user-reset').modal();
        });

        $(buttonPanel).find('.b-add-student').on('click', function () {
            ThisPageObject.functions.openUserAddStudentForm();
        });
        $(buttonPanel).find('.b-add-teacher').on('click', function () {
            ThisPageObject.functions.openUserAddTeacherForm();
        });

        $(buttonPanel).find('.b-change').on('click', function () {
            var l = ThisPageObject._values.mainTableManager.getSelectedDataList();
            console.log(l);
            if (l.length === 1) {
                switch (l[0].userType) {
                    case 1:
                        ThisPageObject.functions.openUserChangeStudentForm(l[0]);
                        break;
                    case 2:
                        ThisPageObject.functions.openUserChangeTeacherForm(l[0]);
                        break;
                    default:
                        break;
                }
            }
        });

        $(buttonPanel).find('.b-changeRole').on('click', function () {
            var l = ThisPageObject._values.mainTableManager.getSelectedDataList();
            ThisPageObject.functions.openUserChangeRoleForm(l);
        });

        $(buttonPanel).find('.b-delete').on('click', function () {
            ThisPageObject.data.deleteUserNameList =
                _.map(ThisPageObject._values.mainTableManager.getSelectedDataList(), function (T) {
                    return T.username;
                });
            ThisPageObject._values.oneWayBinderManager.flush();
            $('#f-user-delete').modal();
        });
    };

    ThisPageObject._initFunctions.initDatetimepickerGlobal = function () {
        jQuery.datetimepicker.setLocale('zh');
    };

    ThisPageObject._initFunctions.userAddTeacherForm = function () {
        var rootNode = $('#f-user-add-teacher');
        var SSXSelectorManager = document.LY.LySelectorSSX.createManager(
            $(rootNode).find('.f-province'),
            $(rootNode).find('.f-city'),
            $(rootNode).find('.f-area')
        );
        $(rootNode).find('input[type="date"]').datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            maxDate: new Date(),
            defaultDate: new Date(),
        });

        $(rootNode).find('.b-save').on('click', function () {
            if (!document.LY.LyHelperTools.checkFileType('IMAGE', $(rootNode).find('.f-file').val())) {
                toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkEmail($(rootNode).find('.f-email').val())) {
                toastr.error('请输入正确的 邮箱 .', '邮箱 输入不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkPhoneNumber($(rootNode).find('.f-phone').val())) {
                toastr.error('请输入正确的 11位电话号码 .', '11位电话号码 输入不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkChineseName($(rootNode).find('.f-name').val())) {
                toastr.error('请输入正确的 中文姓名 .', '中文姓名 输入不正确');
                return;
            }
            if (!ThisPageObject.data.IDValidator.isValid($(rootNode).find('.f-idNumber').val())) {
                toastr.error('请输入正确的身份证号', '身份证号不合法');
                return;
            }
            // submit form
            var fd = new FormData($(rootNode).find('form').get()[0]);
            fd.append('userType', '2');
            var loadingManager = document.LY.LyLoading4ModalSubmit.makeLoadingByRootNode(rootNode);
            document.LY.LyHelperTools.HTTP.sendFormDataReturnJson('Users_AddUser', fd, {}).then(function (T) {
                console.log(T);
                toastr.success('默认用户密码为：123456', '操作成功');
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                loadingManager.destroy();
                ThisPageObject.functions.flushForm();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
            document.LY.LyLoading4ModalSubmit.removeAll();
        });
        ThisPageObject.functions.openUserAddTeacherForm = function () {
            $(rootNode).find('form').trigger("reset");
            $(rootNode).find('input[type="text"]').val("");
            $(rootNode).find('input[type="date"]').val("");
            ThisPageObject.functions.reInitSelect2RoleSelect($(rootNode).find('.f-role'));
            $(rootNode).modal();
        };

        ThisPageObject.toolFunctions.userNameCheckInit(rootNode);
        ThisPageObject.toolFunctions.IDNumberCheckInit(rootNode);
    };

    ThisPageObject._initFunctions.userAddStudentForm = function () {
        var rootNode = $('#f-user-add-student');
        var SSXSelectorManager = document.LY.LySelectorSSX.createManager(
            $(rootNode).find('.f-province'),
            $(rootNode).find('.f-city'),
            $(rootNode).find('.f-area')
        );
        $(rootNode).find('input[type="date"]').datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            maxDate: new Date(),
            defaultDate: new Date(),
        });

        $(rootNode).find('.b-save').on('click', function () {
            if (!document.LY.LyHelperTools.checkFileType('IMAGE', $(rootNode).find('.f-file').val())) {
                toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkEmail($(rootNode).find('.f-email').val())) {
                toastr.error('请输入正确的 邮箱 .', '邮箱 输入不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkPhoneNumber($(rootNode).find('.f-phone').val())) {
                toastr.error('请输入正确的 11位电话号码 .', '11位电话号码 输入不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkChineseName($(rootNode).find('.f-name').val())) {
                toastr.error('请输入正确的 中文姓名 .', '中文姓名 输入不正确');
                return;
            }
            if (!ThisPageObject.data.IDValidator.isValid($(rootNode).find('.f-idNumber').val())) {
                toastr.error('请输入正确的身份证号', '身份证号不合法');
                return;
            }
            // submit form
            var fd = new FormData($(rootNode).find('form').get()[0]);
            fd.append('userType', '1');
            var loadingManager = document.LY.LyLoading4ModalSubmit.makeLoadingByRootNode(rootNode);
            document.LY.LyHelperTools.HTTP.sendFormDataReturnJson('Users_AddUser', fd, {}).then(function (T) {
                console.log(T);
                toastr.success('默认用户密码为：123456', '操作成功');
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                loadingManager.destroy();
                ThisPageObject.functions.flushForm();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
            document.LY.LyLoading4ModalSubmit.removeAll();
        });
        ThisPageObject.functions.openUserAddStudentForm = function () {
            $(rootNode).find('form').trigger("reset");
            $(rootNode).find('input[type="text"]').val("");
            $(rootNode).find('input[type="date"]').val("");
            ThisPageObject.functions.reInitSelect2RoleSelect($(rootNode).find('.f-role'));
            $(rootNode).modal();
        };

        ThisPageObject.toolFunctions.userNameCheckInit(rootNode);
        ThisPageObject.toolFunctions.IDNumberCheckInit(rootNode);
    };

    ThisPageObject.toolFunctions.userNameCheckInit = function (rootNode) {

        $(rootNode).find('.n-g-userName').hide();
        $(rootNode).find('.n-c-userName').hide();
        $(rootNode).find('.n-w-userName').hide();
        Rx.Observable.fromEvent($(rootNode).find('.f-userName'), 'keyup')
            .pluck('target', 'value')
            .debounceTime(500 /* ms */)
            .distinctUntilChanged()
            .flatMap(function (value) {
                console.log('test userName');
                $(rootNode).find('.n-g-userName').hide();
                $(rootNode).find('.n-c-userName').show();
                $(rootNode).find('.n-w-userName').hide();
                return document.LY.LyHelperTools.HTTP.getQuery("Users_TestUserNameBeforeAddUser", {userName: value})
                    .then(function (T) {
                        return {
                            state: 0,
                            T: T,
                            E: null,
                        };
                    }).catch(function (E) {
                        return {
                            state: 1,
                            T: null,
                            E: E,
                        };
                    });
            })
            .subscribe(function (data) {
                console.log(data);
                if (data.state === 1) {
                    console.log('1');
                    $(rootNode).find('.n-g-userName').hide();
                    $(rootNode).find('.n-c-userName').show();
                    $(rootNode).find('.n-w-userName').hide();
                } else if (data.state === 0) {
                    console.log('2');
                    if (data.T.result !== "success") {
                        console.log('3');
                        $(rootNode).find('.n-w-userName').show();
                        $(rootNode).find('.n-g-userName').hide();
                        $(rootNode).find('.n-c-userName').hide();
                    } else {
                        console.log('4');
                        $(rootNode).find('.n-w-userName').hide();
                        $(rootNode).find('.n-g-userName').show();
                        $(rootNode).find('.n-c-userName').hide();
                    }
                }
                console.log('5');
            }, function (err) {
                console.error(err);
                $(rootNode).find('.n-g-userName').hide();
                $(rootNode).find('.n-c-userName').show();
                $(rootNode).find('.n-w-userName').hide();
            });

    };

    ThisPageObject.toolFunctions.IDNumberCheckInit = function (rootNode) {

        $(rootNode).find('.n-w-idNumber').hide();
        Rx.Observable.fromEvent($(rootNode).find('.f-idNumber'), 'keyup')
            .pluck('target', 'value')
            .debounceTime(50 /* ms */)
            .distinctUntilChanged()
            .map(function (value) {
                return ThisPageObject.data.IDValidator.isValid(value);
            })
            .subscribe(function (data) {
                console.log(data);
                if (!data) {
                    $(rootNode).find('.n-w-idNumber').show();
                } else {
                    $(rootNode).find('.n-w-idNumber').hide();
                }
            }, function (err) {
                console.error(err);
            });

    };

    ThisPageObject._initFunctions.userChangeStudentForm = function () {
        var rootNode = $('#f-user-change-student');
        $(rootNode).find('input[type="text"]').val("");
        $(rootNode).find('input[type="date"]').val("");
        var SSXSelectorManager = document.LY.LySelectorSSX.createManager(
            $(rootNode).find('.f-province'),
            $(rootNode).find('.f-city'),
            $(rootNode).find('.f-area')
        );

        $(rootNode).find('input[type="date"]').datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            maxDate: new Date(),
            defaultDate: new Date(),
        });

        var nameList = [
            ['userName'],
            ['name'],
            ['sex'],
            ['nickName'],
            ['email'],
            ['phone'],
            ['birthday', 'student.birthday'],
            ['idNumber', 'student.idNumber'],
            ['province', 'student.province'],
            ['city', 'student.city'],
            ['area', 'student.area'],
            ['addr', 'student.addr'],
            ['file'],
            ['studentNo', 'student.studentNo'],
            ['className', 'student.className'],
            ['gradeName', 'student.gradeName'],
            ['description', 'student.description'],
        ];

        $(rootNode).find('.b-save').on('click', function () {
            if (!_.isString(ThisPageObject.data.userChangeStudentFormData.imgSrc) &&
                !document.LY.LyHelperTools.checkFileType('IMAGE', $(rootNode).find('.f-file').val())) {
                toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkEmail($(rootNode).find('.f-email').val())) {
                toastr.error('请输入正确的 邮箱 .', '邮箱 输入不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkPhoneNumber($(rootNode).find('.f-phone').val())) {
                toastr.error('请输入正确的 11位电话号码 .', '11位电话号码 输入不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkChineseName($(rootNode).find('.f-name').val())) {
                toastr.error('请输入正确的 中文姓名 .', '中文姓名 输入不正确');
                return;
            }
            if (!ThisPageObject.data.IDValidator.isValid($(rootNode).find('.f-idNumber').val())) {
                toastr.error('请输入正确的身份证号', '身份证号不合法');
                return;
            }
            var fd = new FormData($(rootNode).find('form').get()[0]);
            fd.append('id', ThisPageObject.data.userChangeStudentFormData.id);
            fd.append('studentId', ThisPageObject.data.userChangeStudentFormData.studentId);
            fd.append('userType', 1);
            document.LY.LyHelperTools.HTTP.sendFormDataReturnJson('Users_ChangeUserStudent', fd, {}).then(function (T) {
                console.log(T);
                toastr.success('操作成功');
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushForm();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });
        ThisPageObject.functions.openUserChangeStudentForm = function (user) {
            ThisPageObject.data.userChangeStudentFormData = user.id;
            document.LY.LyHelperTools.HTTP.getQuery('Users_GetUserInfoForChange', {
                userId: user.id,
                userType: 1,
            }).then(function (T) {
                console.log(T);
                if (_.isObject(T)) {
                    _.forEach(nameList, function (N) {
                        $(rootNode).find('.f-' + N[0]).val(_.get(T, N[1] ? N[1] : N[0]));
                    });
                }
                ThisPageObject.data.userChangeStudentFormData = {
                    id: user.id,
                    studentId: _.get(T, 'student.id'),
                    imgSrc: _.get(T, 'imgSrc'),
                };
                $(rootNode).find('.f-birthday').val(
                    document.LY.LyHelperTools.UnixTimestampMilliseconds2DateString(_.get(T, 'student.birthday'))
                );
                console.log('src', ThisPageObject.data.FileRootPath + _.get(T, 'imgSrc'));
                if (_.get(T, 'imgSrc')) {
                    $(rootNode).find('.f-img').prop('src', ThisPageObject.data.FileRootPath + _.get(T, 'imgSrc'));
                    $(rootNode).find('.f-file').prop('required', false);
                } else {
                    $(rootNode).find('.f-file').prop('required', true);
                }
                ThisPageObject.functions.reInitSelect2RoleSelect($(rootNode).find('.f-role'));
                $(rootNode).find('.f-role').val(_.get(T, 'userRoleId'));
                SSXSelectorManager.resetSelected(
                    _.get(T, 'student.province'),
                    _.get(T, 'student.city'),
                    _.get(T, 'student.area')
                );
                $(rootNode).modal();
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
            });
        };

        ThisPageObject.toolFunctions.IDNumberCheckInit(rootNode);
    };

    ThisPageObject._initFunctions.userChangeTeacherForm = function () {
        var rootNode = $('#f-user-change-teacher');
        $(rootNode).find('input[type="text"]').val("");
        $(rootNode).find('input[type="date"]').val("");
        var SSXSelectorManager = document.LY.LySelectorSSX.createManager(
            $(rootNode).find('.f-province'),
            $(rootNode).find('.f-city'),
            $(rootNode).find('.f-area')
        );

        $(rootNode).find('input[type="date"]').datetimepicker({
            format: 'Y-m-d',
            timepicker: false,
            maxDate: new Date(),
            defaultDate: new Date(),
        });

        var nameList = [
            ['userName'],
            ['name'],
            ['sex'],
            ['nickName'],
            ['email'],
            ['phone'],
            ['birthday', 'teacher.birthday'],
            ['idNumber', 'teacher.idNumber'],
            ['province', 'teacher.province'],
            ['city', 'teacher.city'],
            ['area', 'teacher.area'],
            ['addr', 'teacher.addr'],
            ['file'],
            ['highDegree', 'teacher.highDegree'],
            ['teacherIntroduction', 'teacher.teacherIntroduction'],
            ['jobNum', 'teacher.jobNum'],
            ['jobTitle', 'teacher.jobTitle'],
        ];

        $(rootNode).find('.b-save').on('click', function () {
            if (!_.isString(ThisPageObject.data.userChangeTeacherFormData.imgSrc) &&
                !document.LY.LyHelperTools.checkFileType('IMAGE', $(rootNode).find('.f-file').val())) {
                toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkEmail($(rootNode).find('.f-email').val())) {
                toastr.error('请输入正确的 邮箱 .', '邮箱 输入不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkPhoneNumber($(rootNode).find('.f-phone').val())) {
                toastr.error('请输入正确的 11位电话号码 .', '11位电话号码 输入不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkChineseName($(rootNode).find('.f-name').val())) {
                toastr.error('请输入正确的 中文姓名 .', '中文姓名 输入不正确');
                return;
            }
            if (!ThisPageObject.data.IDValidator.isValid($(rootNode).find('.f-idNumber').val())) {
                toastr.error('请输入正确的身份证号', '身份证号不合法');
                return;
            }
            var fd = new FormData($(rootNode).find('form').get()[0]);
            fd.append('id', ThisPageObject.data.userChangeTeacherFormData.id);
            fd.append('teacherId', ThisPageObject.data.userChangeTeacherFormData.teacherId);
            fd.append('userType', 2);
            document.LY.LyHelperTools.HTTP.sendFormDataReturnJson('Users_ChangeUserTeacher', fd, {}).then(function (T) {
                console.log(T);
                toastr.success('操作成功');
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushForm();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });
        ThisPageObject.functions.openUserChangeTeacherForm = function (user) {
            document.LY.LyHelperTools.HTTP.getQuery('Users_GetUserInfoForChange', {
                userId: user.id,
                userType: 2,
            }).then(function (T) {
                console.log(T);
                if (_.isObject(T)) {
                    _.forEach(nameList, function (N) {
                        $(rootNode).find('.f-' + N[0]).val(_.get(T, N[1] ? N[1] : N[0]));
                    });
                }
                ThisPageObject.data.userChangeTeacherFormData = {
                    id: user.id,
                    teacherId: _.get(T, 'teacher.teacherId'),
                    imgSrc: _.get(T, 'imgSrc'),
                };
                $(rootNode).find('.f-birthday').val(
                    document.LY.LyHelperTools.UnixTimestampMilliseconds2DateString(_.get(T, 'teacher.birthday'))
                );
                console.log('src', ThisPageObject.data.FileRootPath + _.get(T, 'imgSrc'));
                if (_.get(T, 'imgSrc')) {
                    $(rootNode).find('.f-img').prop('src', ThisPageObject.data.FileRootPath + _.get(T, 'imgSrc'));
                    $(rootNode).find('.f-file').prop('required', false);
                } else {
                    $(rootNode).find('.f-file').prop('required', true);
                }
                ThisPageObject.functions.reInitSelect2RoleSelect($(rootNode).find('.f-role'));
                $(rootNode).find('.f-role').val(_.get(T, 'userRoleId'));
                $(rootNode).find('.f-highDegree').val(_.get(T, 'teacher.highDegree'));
                $(rootNode).find('.f-jobTitle').val(_.get(T, 'teacher.jobTitle'));
                SSXSelectorManager.resetSelected(
                    _.get(T, 'teacher.province'),
                    _.get(T, 'teacher.city'),
                    _.get(T, 'teacher.area')
                );
                $(rootNode).modal();
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
            });
        };

        ThisPageObject.toolFunctions.IDNumberCheckInit(rootNode);
    };

    ThisPageObject._initFunctions.userDeleteForm = function () {
        var rootNode = $('#f-user-delete');

        $(rootNode).find('.b-save').on('click', function () {
            var list = _.map(ThisPageObject._values.mainTableManager.getSelectedDataList(), function (T) {
                return T.id;
            });

            var data = {
                items: _.trimEnd(_.reduce(list, function (S, T) {
                    return S + T + ",";
                }, ""), ','),
            };
            console.log(data);

            document.LY.LyHelperTools.HTTP.postQuery(
                'Users_DeleteUser',
                data
            ).then(function (data) {
                console.log(data);
                toastr.success('操作成功');
            }).catch(function (err) {
                console.log(err);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushForm();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

    };

    ThisPageObject._initFunctions.userReviewForm = function () {
        var rootNode = $('#f-user-review');

        var nameList = [
            'role',
            'username',
            'name',
            'grade',
            'class',
        ];

        ThisPageObject.functions.flushUserReviewForm = function (id, userType) {
            if (_.isArray(id)) {
                $(rootNode).find('.f-review-body').hide();
                return;
            } else {
                $(rootNode).find('.f-review-body').show();
                var data = {
                    userId: id,
                    userType: userType,
                };
                document.LY.LyHelperTools.HTTP.postQuery(
                    'Users_GetUserInfoForReview',
                    data
                ).then(function (data) {
                    console.log(data);
                    var info = {
                        'role': 'userType',
                        'username': 'userName',
                        'name': 'name',
                        'grade': 'student.gradeName',
                        'class': 'student.className',
                    };
                    _.forEach(nameList, function (T) {
                        $(rootNode).find('.f-' + T).val(_.get(data, info[T]));
                    });
                }).catch(function (err) {
                    console.log(err);
                }).finally(function () {
                    console.log('end');
                });
            }
        };

        var submitFunc = function (mode) {
            var data = {
                type: mode ? 1 : 2,
                items: document.LY.LyHelperTools.list2listStringSplit(_.map(
                    ThisPageObject._values.mainTableManager.getSelectedDataList(), function (T) {
                        return T.id;
                    }
                ), null, true),
            };

            document.LY.LyHelperTools.HTTP.postQuery(
                'Users_SubmitReview',
                data
            ).then(function (data) {
                console.log(data);
                toastr.success('操作成功');
            }).catch(function (err) {
                console.log(err);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushForm();
            });
        };

        $(rootNode).find('.b-save').on('click', function () {
            submitFunc(true);
            $.modal.close();
        });
        $(rootNode).find('.b-del').on('click', function () {
            submitFunc(false);
            $.modal.close();
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

    };

    ThisPageObject._initFunctions.userTypeChangeLoadingForm = function () {
        var rootNode = $('#f-user-change-type-loading');

        var nameList = [
            'role',
        ];

        $(rootNode).find('.b-save').on('click', function () {
            console.log($(rootNode).find('.f-role').val());
            if ($(rootNode).find('.f-role').val() === '1') {
                $.modal.close();
                ThisPageObject.functions.openUserTypeChangeStudentForm();
            }
            if ($(rootNode).find('.f-role').val() === '2') {
                $.modal.close();
                ThisPageObject.functions.openUserTypeChangeTeacherForm();
            }
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });
        ThisPageObject.functions.openUserTypeChangeLoadingForm = function () {
            $(rootNode).modal();
        };

    };
    ThisPageObject._initFunctions.userTypeChangeStudentForm = function () {
        var rootNode = $('#f-user-change-type-student');

        var nameList = [
            ['idNumber', 'student.idNumber'],
            ['studentNo', 'student.studentNo'],
            ['className', 'student.className'],
            ['gradeName', 'student.gradeName'],
            ['description', 'student.description'],
        ];

        $(rootNode).find('.b-save').on('click', function () {
            var item = ThisPageObject._values.mainTableManager.getSelectedDataList()[0];
            if (!ThisPageObject.data.IDValidator.isValid($(rootNode).find('.f-idNumber').val())) {
                toastr.error('请输入正确的身份证号', '身份证号不合法');
                return;
            }
            // var fd = new FormData($(rootNode).find('form').get()[0]);
            // fd.append('id', item.id);
            // fd.append('userType', 1);
            var data = {
                userId: item.id,
                userType: 1,
                userName: item.username,
                name: item.name,
            };
            _.forEach(nameList, function (T) {
                _.set(data, T[1], $(rootNode).find(".f-" + T[0]).val());
            });
            document.LY.LyHelperTools.HTTP.postJson('Users_ChangeUserType2Student', data).then(function (data) {
                console.log(data);
                toastr.success('操作成功');
            }).catch(function (err) {
                console.log(err);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushForm();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });
        ThisPageObject.functions.openUserTypeChangeStudentForm = function () {
            $(rootNode).modal();
        };

    };
    ThisPageObject._initFunctions.userTypeChangeTeacherForm = function () {
        var rootNode = $('#f-user-change-type-teacher');

        var nameList = [
            ['idNumber', 'teacher.idNumber'],
            ['jobNum', 'teacher.jobNum'],
            ['jobTitle', 'teacher.jobTitle'],
            ['highDegree', 'teacher.highDegree'],
            ['teacherIntroduction', 'teacher.teacherIntroduction'],
        ];

        $(rootNode).find('.b-save').on('click', function () {
            var item = ThisPageObject._values.mainTableManager.getSelectedDataList()[0];
            if (!ThisPageObject.data.IDValidator.isValid($(rootNode).find('.f-idNumber').val())) {
                toastr.error('请输入正确的身份证号', '身份证号不合法');
                return;
            }
            // var fd = new FormData($(rootNode).find('form').get()[0]);
            // fd.append('id', item.id);
            // fd.append('userType', 2);
            var data = {
                userId: item.id,
                userType: 2,
                userName: item.username,
                name: item.name,
            };
            _.forEach(nameList, function (T) {
                _.set(data, T[1], $(rootNode).find(".f-" + T[0]).val());
            });
            document.LY.LyHelperTools.HTTP.postJson('Users_ChangeUserType2Teacher', data).then(function (data) {
                console.log(data);
                toastr.success('操作成功');
            }).catch(function (err) {
                console.log(err);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushForm();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });
        ThisPageObject.functions.openUserTypeChangeTeacherForm = function () {
            $(rootNode).modal();
        };

    };

    ThisPageObject._initFunctions.userResetPasswdForm = function () {
        var rootNode = $('#f-user-reset');

        $(rootNode).find('.b-save').on('click', function () {

            var data = {
                userName: ThisPageObject._values.mainTableManager.getSelectedDataList()[0].username,
            };

            document.LY.LyHelperTools.HTTP.postQuery(
                'Users_ResetPasswd',
                data
            ).then(function (data) {
                console.log(data);
                toastr.success('新的密码已经设置为：123456', '操作成功');
            }).catch(function (err) {
                console.log(err);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushForm();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

    };

    ThisPageObject._initFunctions.userChangeRoleForm = function () {
        var rootNode = $('#f-user-change-role');

        $(rootNode).find('.b-save').on('click', function () {

            var data = {
                items: document.LY.LyHelperTools.list2listStringSplit(_.map(
                    ThisPageObject.data.userChangeRoleFormData, function (T) {
                        return T.id;
                    }
                ), null, true),
                userRoleId: _.parseInt($(rootNode).find('.f-role').val()),
            };

            document.LY.LyHelperTools.HTTP.postQuery(
                'Users_ChangeRole',
                data
            ).then(function (data) {
                console.log(data);
                toastr.success('操作成功');
            }).catch(function (err) {
                console.error(err);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushForm();
                $.modal.close();
            });

        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openUserChangeRoleForm = function (userList) {
            ThisPageObject.data.userChangeRoleFormData = userList;
            ThisPageObject.functions.reInitSelect2RoleSelect($(rootNode).find('.f-role'));
            if (userList.length === 1) {
                $(rootNode).find('.f-role').val(userList[0].userRole);
            }
            $(rootNode).modal();
        };
    };

    ThisPageObject.functions.reInitSelect2RoleSelect = function (selectNode) {
        $(selectNode).empty();
        _.forEach(ThisPageObject.data.UserRoleList, function (T) {
            $(selectNode).append('<option value="' + T.id + '">' + T.name + '</option>');
        });
    };

    var nowMenu = 'users';
    ThisPageObject._initFunctions.leftMenuManager = function () {
        if (!_.isObject(document.LY.LyLeftMenuManager)) {
            console.error("ly-left-menu-manager not init.");
            return;
        } else {
            ThisPageObject._values.leftMenuManager =
                document.LY.LyLeftMenuManager.createManagerEasy(nowMenu);
        }
    };

    ThisPageObject._initFunctions.oneWayBinderManager = function () {

        if (!_.isObject(document.LY.LyOneWayBind)) {
            console.error("ly-one-way-bind not init.");
            return;
        } else {
            ThisPageObject._values.oneWayBinderManager =
                document.LY.LyOneWayBind.createBinderManager();
        }

    };

}());

var initThis = function () {
    $(function () {
        document.PAGE.users.onInit();
    });
};


(function () {

    if (!document.page2jsPath) {
        console.warn('document.page2jsPath are not set, maybe cannot load other lib');
    }

    var loadLib = function () {
        if (document.isJsDebug) {
            loadjs([
                document.page2jsPath + '/node_modules/lodash/lodash.js',
                document.page2jsPath + '/node_modules/moment/moment.js',
                document.page2jsPath + '/node_modules/jquery-modal/jquery.modal.js',
                document.page2jsPath + '/node_modules/xdan-datetimepicker/build/jquery.datetimepicker.full.js',
                document.page2jsPath + '/node_modules/toastr/toastr.js',
                // document.page2jsPath + '/lib/IDValidator/GB2260.js',
                document.page2jsPath + '/lib/IDValidator/IDValidator.js',
            ], 'jsLib');
        } else {
            loadjs([
                document.page2jsPath + '/node_modules/lodash/lodash.min.js',
                document.page2jsPath + '/node_modules/moment/min/moment.min.js',
                document.page2jsPath + '/node_modules/jquery-modal/jquery.modal.min.js',
                document.page2jsPath + '/node_modules/xdan-datetimepicker/build/jquery.datetimepicker.full.min.js',
                document.page2jsPath + '/node_modules/toastr/build/toastr.min.js',
                // document.page2jsPath + '/lib/IDValidator/GB2260.min.js',
                document.page2jsPath + '/lib/IDValidator/IDValidator.min.js',
            ], 'jsLib');
        }
    };
    var loadUserConfig = function () {
        loadjs([
            document.page2jsPath + '/js/global-config.js',
        ], 'userConfig');
    };
    var loadUserLib = function () {
        loadjs([
            document.page2jsPath + '/js/ly-lib/ly-helper-tools.js',
            document.page2jsPath + '/js/ly-lib/ly-lib-one-way-bind.js',
            document.page2jsPath + '/js/ly-lib/ly-list-select.js',
            document.page2jsPath + '/js/ly-lib/ly-table-manager.js',
            document.page2jsPath + '/js/ly-lib/ly-pagination-manager.js',
            document.page2jsPath + '/js/ly-lib/ly-left-menu-manger.js',
            document.page2jsPath + '/js/ly-lib/ly-selector-ssx.js',
            document.page2jsPath + '/js/ly-lib/ly-loading-4-modal-submit.js',
        ], 'userLib');
    };

    loadLib();
    loadjs.ready('jsLib',
        {
            success: function () {
                console.log('jsLib');
                loadUserConfig();
            },
            error: function () {

            },
        }
    );
    loadjs.ready('userConfig',
        {
            success: function () {
                console.log('userConfig');
                loadUserLib();
            },
            error: function () {

            },
        }
    );
    loadjs.ready('userLib',
        {
            success: function () {
                console.log('userLib Init');
                initThis();
            },
            error: function () {

            },
        }
    );

}());

