/**
 * Created by Jeremie on 2017/07/16.
 */
'use strict';

// PAGE role class
(function () {

    document.PAGE = document.PAGE || {};
    document.PAGE.role = {
        // 测试用虚拟数据初始化函数
        _initMockFunctions: {},
        // 私有初始化函数
        _initFunctions: {},
        // 私有内部变量
        _values: {},
        data: {
            deleteRoleNameList: "",
            roleDeleteFormDataList: "",
            ztreeObject: undefined,
            roleChangeFormData: undefined,
            rolePermissionFormData: undefined,
            PermissionTreeStruct: undefined,
            PermissionTreeStructOrigin: undefined,
            RolePermissionStateOrigin: undefined,
            RolePermissionStateMap: new Map(),
        },
        functions: {},
    };
    var ThisPageObject = document.PAGE.role;
    ThisPageObject._values = {
        mainTableManager: null,
        paginationManager: null,
        leftMenuManager: null,
    };

    // 初始化函数
    ThisPageObject.onInit = function () {

        ThisPageObject._initFunctions.initToastrGlobal();
        ThisPageObject._initFunctions.initModalGlobal();
        ThisPageObject._initFunctions.leftMenuManager();
        ThisPageObject._initFunctions.mainTableManager();
        // ThisPageObject._initFunctions.paginationManager();
        ThisPageObject._initFunctions.buttonPanel();
        ThisPageObject._initFunctions.roleViewForm();
        ThisPageObject._initFunctions.roleAddForm();
        ThisPageObject._initFunctions.roleChangeForm();
        ThisPageObject._initFunctions.rolePermissionForm();
        ThisPageObject._initFunctions.roleDeleteForm();
        ThisPageObject._initFunctions.oneWayBinderManager();
        ThisPageObject._initFunctions.ztreeTreeMenu();
        ThisPageObject._initFunctions.initTriggerChanger();

        ThisPageObject._values.mainTableManager.LyListSelectTableManger.changeCallBackList.push(
            function (NodeList) {
                // console.log(NodeList);
                var buttonPanel = $('.button-panel');
                if (NodeList.length === 0) {
                    $(buttonPanel).find('.b-delete').prop('disabled', true);
                }
                if (NodeList.length === 1) {
                    $(buttonPanel).find('.b-change').prop('disabled', false);
                    $(buttonPanel).find('.b-set-permission').prop('disabled', false);
                } else {
                    $(buttonPanel).find('.b-change').prop('disabled', true);
                    $(buttonPanel).find('.b-set-permission').prop('disabled', true);
                }
                if (NodeList.length > 0) {
                    $(buttonPanel).find('.b-delete').prop('disabled', false);
                }
            }
        );
        ThisPageObject._values.mainTableManager.LyListSelectTableManger.noticeCallback();
        ThisPageObject._values.mainTableManager.afterReInitCallback = function (rootNode) {
            // console.log($(ThisPageObject._values.mainTableManager.getAllRowNode()).find('.row-button'));
            // console.log($(rootNode).find('.row-button'));
            ThisPageObject.functions.reInitRowButtonPanel();
        };

        ThisPageObject._values.oneWayBinderManager.createBinder({
            rootObjectRef: ThisPageObject.data,
            valuePath: 'deleteRoleNameList',
            JQNode: $('#delete-role-name-list'),
            type: 'html',
            translator: function (D) {
                return _.reduce(D, function (S, T) {
                    return S + (T || '') + "<br/>";
                }, "");
            }
        });

        ThisPageObject._values.oneWayBinderManager.flush();

        // Mock for DEBUG
        // ThisPageObject._initMockFunctions.initZtreeTreeData();
        // ThisPageObject._initMockFunctions.initTableData();

        ThisPageObject.functions.flushList();
        ThisPageObject.functions.loadPermissionTree();

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

    var PermissionTreeFieldMap = [
        ['Id', 'menuid', _.parseInt],
        ['ApplicationId', 'applicationid'],
        ['ApplicationCode', 'applicationcode'],
        ['MenuNo', 'menuno'],
        ['MenuParentNo', 'menuparentno'],
        ['MenuOrder', 'menuorder'],
        ['MenuName', 'menuname'],
        ['MenuUrl', 'menuurl'],
        ['IsVisible', 'isvisible'],
    ];
    ThisPageObject.functions.loadPermissionTree = function () {
        document.LY.LyHelperTools.HTTP.postQuery('Permission_GetPermissionTreeStruct', {}).then(function (T) {
            console.log(T);
            console.log(_.isArray(T.list));
            if (_.isArray(T.list)) {
                ThisPageObject.data.PermissionTreeStructOrigin = _.map(T.list, function (D) {
                    var data = {};
                    _.forEach(PermissionTreeFieldMap, function (M) {
                        _.set(data, M[0], M[2] ? M[2](_.get(D, M[1])) : _.get(D, M[1]));
                    });
                    return data;
                });
                console.log("PermissionTreeStructOrigin: ", ThisPageObject.data.PermissionTreeStructOrigin);

                // // build tree
                // ThisPageObject.functions.BuildPermissionTreeStructSimple();
                // console.log(ThisPageObject.data.PermissionTreeStruct);
                // // TODO TEST
                // ThisPageObject.functions.reInitZtreeSimple(ThisPageObject.data.PermissionTreeStruct);

                ThisPageObject.functions.BuildPermissionTreeStruct();
                console.log(ThisPageObject.data.PermissionTreeStruct);
                // TODO TEST
                // ThisPageObject.functions.reInitZtreeSimple(ThisPageObject.functions.BuildPermissionTreeStructTree());
            } else {
                toastr.error('服务器错误，请手动刷新页面', '服务器错误');
            }
        }).catch(function (E) {
            console.log(E);
        }).finally(function () {
            console.log('end');
        });
    };

    var TreeBuilderConfig = {
        idKey: 'MenuNo',
        parentIdKey: 'MenuParentNo',
        nameKey: 'MenuName',
    };
    ThisPageObject.functions.BuildPermissionTreeStruct = function () {
        // 一个节点，要么是子节点，要么是根节点
        // struct tree
        var originData = _.clone(ThisPageObject.data.PermissionTreeStructOrigin);
        // reverse mode
        var reverseMap = new Map();
        _.forEach(originData, function (T) {
            T.childNoS = new Set();
            T.childrenList = [];
            reverseMap.set(_.get(T, TreeBuilderConfig.idKey), T);
        });
        // register to parent's childNoS list
        _.forEach(originData, function (T) {
            var ref = reverseMap.get(_.get(T, TreeBuilderConfig.parentIdKey));
            if (ref) {
                ref.childNoS.add(_.get(T, TreeBuilderConfig.idKey));
            }
        });

        var noChildMap = new Map();
        var haveChildMap = _.clone(reverseMap);
        // console.log("noChildMap", noChildMap);
        // console.log("haveChildMap", haveChildMap);
        // console.log("------------------");
        var flagContinue = true;
        while (true) {
            // console.log("noChildMap", noChildMap);
            // console.log("haveChildMap", haveChildMap);
            // console.log("------------------");
            flagContinue = false;
            // split node to two map, one not have child, one other
            haveChildMap.forEach(function (V, K, T) {
                if (V.childNoS.size === 0) {
                    noChildMap.set(K, V);
                    T.delete(K);
                    flagContinue = true;
                }
            });
            // console.log("noChildMap", noChildMap);
            // console.log("haveChildMap", haveChildMap);
            // console.log("++++++++++++++++++");
            if (!flagContinue) {
                break;
            }
            // add node to parent's node child list,
            // remove it name from parent's childNoS,
            // remove it from noChildMap,
            noChildMap.forEach(function (v, k, T) {
                var ref = haveChildMap.get(_.get(v, TreeBuilderConfig.parentIdKey));
                if (ref) {
                    ref.childrenList.push(v);
                    if (!ref.childNoS.has(k)) {
                        console.error('!ref.childNoS.has(k)');
                    }
                    ref.childNoS.delete(k);
                    T.delete(k);
                }
            });
        }

        // now , all the node are not have parent and childNoS
        // if a node have parent which is a data error
        // if a node have childNoS that means the algorithm error
        if (haveChildMap.size !== 0) {
            console.error('haveChildMap.size !== 0');
        }
        ThisPageObject.data.PermissionTreeStruct = [];
        noChildMap.forEach(function (V, K, T) {
            if (_.get(V, TreeBuilderConfig.parentIdKey)) {
                console.warn(
                    "if (_.get(v, '" + TreeBuilderConfig.parentIdKey + "')):",
                    _.get(V, TreeBuilderConfig.parentIdKey)
                );
            }
            ThisPageObject.data.PermissionTreeStruct.push(V);
        });
        // now, the node's "childrenList" is the child node list
    };

    ThisPageObject.functions.BuildPermissionTreeStructTree = function () {
        // Note: fill ThisPageObject.data.RolePermissionStateMap; before call this function
        var defaultState = {
            // open: true,
        };
        var reduceChildren = function (C) {
            if (_.isArray(C))
                return _.reduce(C, function (S, T) {
                    var node = {
                        name: _.get(T, TreeBuilderConfig.nameKey),
                        children: reduceChildren(T.childrenList),
                        dataCustom: T,
                        checked: ThisPageObject.data.RolePermissionStateMap.has(_.get(T, TreeBuilderConfig.idKey)),
                    };
                    S.push(_.assign(node, defaultState));
                    return S;
                }, []);
            return [];
        };
        return reduceChildren(_.clone(ThisPageObject.data.PermissionTreeStruct));
    };

    ThisPageObject._initMockFunctions.initTableData = function () {
        // Mock
        ThisPageObject._values.mainTableManager.rebuild([
            {
                id: 0,
                role: 'role',
                note: 'note',
            },
            {
                id: 1,
                role: '学生',
                note: '没有操作功能，只能查看课程',
            },
            {
                id: 2,
                role: '教师',
                note: '拥有上传课程，对自己上传课程进行编辑和删除的功能',
            },
            {
                id: 3,
                role: '学校管理员',
                note: '全部',
            },
            {
                id: 4,
                role: '超级管理员',
                note: '全部',
            },
        ]);
    };

    ThisPageObject._initFunctions.mainTableManager = function () {

        var fieldList = [
            'role',
            'note',
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

    };

    ThisPageObject._initFunctions.paginationManager = function () {


        if (!_.isObject(document.LY.LyPaginationManager)) {
            console.error("ly-pagination-manager not init.");
            return;
        } else {
            var pmr = $(document.LY.LyPaginationManager.rootSelectString)[0];
            ThisPageObject._values.paginationManager = document.LY.LyPaginationManager.createManagerEasy($(pmr));
            var paginationManager = ThisPageObject._values.paginationManager;
            paginationManager.callback.click = function (index) {
                //
            };
            paginationManager.callback.go = function (index) {
                //
            };
        }
    };

    ThisPageObject._initFunctions.initModalGlobal = function () {
        // set modal lib global value
        _.set($.modal.defaults, 'closeExisting', false);
        _.set($.modal.defaults, 'escapeClose', false);
        _.set($.modal.defaults, 'clickClose', false);
        _.set($.modal.defaults, 'showClose', false);
    };

    ThisPageObject.functions.reInitRowButtonPanel = function () {
        var rootNode = $(ThisPageObject._values.mainTableManager.getAllRowNode()).find('.row-button');

        $(rootNode).find('.b-view').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.functions.openRoleViewForm(rowData);
        });
        $(rootNode).find('.b-change').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.functions.openRoleChangeForm(rowData);
        });
        $(rootNode).find('.b-set-permission').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            // TODO set-permission
            ThisPageObject.functions.openRolePermissionForm(rowData);
        });
        $(rootNode).find('.b-delete').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.functions.openDeleteModal([rowData]);
        });
    };

    ThisPageObject._initFunctions.buttonPanel = function () {
        var buttonPanel = $('.button-panel');

        $(buttonPanel).find('.b-add').on('click', function () {
            ThisPageObject.functions.openRoleAddForm();
        });

        $(buttonPanel).find('.b-change').on('click', function () {
            ThisPageObject.functions.openRoleChangeForm();
        });
        $(buttonPanel).find('.b-set-permission').on('click', function () {
            ThisPageObject.functions.openRolePermissionForm();
        });
        $(buttonPanel).find('.b-delete').on('click', function () {
            ThisPageObject.functions.openDeleteModal(
                ThisPageObject._values.mainTableManager.getSelectedDataList()
            );
        });
    };

    ThisPageObject._initFunctions.roleViewForm = function () {
        var rootNode = $('#f-role-view');

        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openRoleViewForm = function (rowData) {
            $(rootNode).find('.f-role').text(rowData.role);
            $(rootNode).find('.f-note').text(rowData.note);
            $('#f-role-view').modal();
        };

    };

    ThisPageObject._initFunctions.roleAddForm = function () {
        var rootNode = $('#f-role-add');

        var nameList = [
            'role',
            'note',
        ];
        _.forEach(nameList, function (T) {
            $(rootNode).find('.f-' + T).val("");
        });

        $(rootNode).find('.b-save').on('click', function () {
            var data = {
                rolename: $(rootNode).find('.f-role').val(),
                describes: $(rootNode).find('.f-note').val(),
            };

            document.LY.LyHelperTools.HTTP.postJson('Role_Add', data).then(function (T) {
                console.log(T);
                toastr.success('操作成功');
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('finally');
                ThisPageObject.functions.flushList();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openRoleAddForm = function () {
            // clear data
            _.forEach(nameList, function (T) {
                $(rootNode).find('.f-' + T).val("");
            });
            $(rootNode).modal();
        };

    };

    ThisPageObject._initFunctions.roleChangeForm = function () {
        var rootNode = $('#f-role-change');

        var nameList = [
            'role',
            'note',
        ];
        _.forEach(nameList, function (T) {
            $(rootNode).find('.f-' + T).val("");
        });

        $(rootNode).find('.b-save').on('click', function () {
            var dataO = ThisPageObject.data.roleChangeFormData;
            // TODO submit

            var data = {
                "roleid": _.get(dataO, 'id'),
                "rolename": $(rootNode).find('.f-role').val(),
                "describes": $(rootNode).find('.f-note').val(),
            };

            document.LY.LyHelperTools.HTTP.postJson('Role_Change', data).then(function (T) {
                console.log(T);
                toastr.success('操作成功');
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('finally');
                ThisPageObject.functions.flushList();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openRoleChangeForm = function (rowData) {
            if (rowData) {
                ThisPageObject.data.roleChangeFormData = rowData;
                $(rootNode).find('.f-role').val(rowData.role);
                $(rootNode).find('.f-note').val(rowData.note);
            } else {
                // set data
                var data = ThisPageObject._values.mainTableManager.getSelectedDataList()[0];
                ThisPageObject.data.roleChangeFormData = data;
                $(rootNode).find('.f-role').val(data.role);
                $(rootNode).find('.f-note').val(data.note);
            }
            $(rootNode).modal();
        };

    };

    ThisPageObject._initFunctions.roleDeleteForm = function () {
        var rootNode = $('#f-role-delete');

        $(rootNode).find('.b-save').on('click', function () {
            var list = _.map(ThisPageObject.data.roleDeleteFormDataList, function (T) {
                return T.id;
            });

            var data = {
                ids: _.trimEnd(_.reduce(list, function (S, T) {
                    return S + T + ",";
                }, ""), ','),
                // ids: list,
            };

            document.LY.LyHelperTools.HTTP.postQuery('Role_Delete', data).then(function (data) {
                console.log(data);
                toastr.success('操作成功');
            }).catch(function (err) {
                console.log(err);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushList();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openDeleteModal = function (list) {
            ThisPageObject.data.roleDeleteFormDataList = list;
            ThisPageObject.data.deleteRoleNameList = _.map(list, function (T) {
                return T.role;
            });
            ThisPageObject._values.oneWayBinderManager.flush();
            $(rootNode).modal();
        };

    };

    ThisPageObject._initFunctions.rolePermissionForm = function () {
        var rootNode = $('#f-role-permission');

        $(rootNode).find('.b-save').on('click', function () {
            var id = ThisPageObject.data.rolePermissionFormData.id;

            var list = ThisPageObject.functions.ZtreeGetCheckedNodes();

            var data = {
                id: id,
                list: _.map(list, function (T) {
                    return _.get(T, 'dataCustom.MenuNo');
                }),   // read tree
            };

            console.log(data);
            document.LY.LyHelperTools.HTTP.postJson('Role_UpdateRolePermission', data).then(function (T) {
                console.log(T);
                toastr.success('操作成功');
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openRolePermissionForm = function (rowData) {
            ThisPageObject.data.rolePermissionFormData = rowData;
            var data = {
                id: rowData.id,
            };
            document.LY.LyHelperTools.HTTP.postQuery('Role_GetRolePermission', data).then(function (T) {
                console.log(T);
                // make tree
                ThisPageObject.data.RolePermissionStateMap = new Map();
                _.forEach(T.list, function (T) {
                    ThisPageObject.data.RolePermissionStateMap.set(_.get(T, 'privilegeaccessvalue'), T);
                });
                var l = ThisPageObject.functions.BuildPermissionTreeStructTree();
                ThisPageObject.functions.reInitZtreeSimple(l);
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                $(rootNode).modal();
            });
        };

    };

    var nowMenu = 'role';
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

    ThisPageObject._initMockFunctions.initZtreeTreeData = function () {
        // TODO debug mock
        var dataTree = {
            nodeName: '',
            children: [],
        };

        var maxDeep = 5;
        var mockDT = function (nodeP, deep) {
            if (deep === maxDeep) {
                return;
            }
            for (var i = 0; i !== 3; ++i) {
                var node = {
                    nodeName: nodeP.nodeName + (i + 1) + '.',
                    children: [],
                    dataCustom: {d1: nodeP.nodeName},
                };
                mockDT(node, deep + 1);
                node.nodeName += '12345678901234567890';
                nodeP.children.push(node);
            }
        };
        mockDT(dataTree, 0);

        ThisPageObject.functions.reInitZtree(dataTree);
    };

    ThisPageObject._initFunctions.ztreeTreeMenu = function () {

        var ztreeObjectName = "ztree-role-tree-menu";

        var onCkeckBoxClick = function (event, treeId, treeNode) {
            // console.log(event);
            // console.log(treeId);
            // console.log(treeNode);
        };

        var setting = {
            check: {
                enable: true,
                autoCheckTrigger: true,
                chkStyle: "checkbox",
                chkboxType: {
                    "Y": "s",
                    "N": "",
                },
            },
            callback: {
                onCheck: onCkeckBoxClick,
            },
            view: {
                // showIcon: false,
                dblClickExpand: false,
            },
        };

        var defaultState = {
            // open: true,
        };

        var dataTree2jstreeData = function (dt) {
            var rootNode = dt;
            var reduceChildren = function (C) {
                if (_.isArray(C))
                    return _.reduce(C, function (S, T) {
                        var node = {
                            name: T.nodeName,
                            children: reduceChildren(T.children),
                        };
                        S.push(_.assign(T, node, defaultState));
                        return S;
                    }, []);
                return [];
            };
            return reduceChildren(rootNode.children);
        };

        ThisPageObject.functions.reInitZtree = function (treeData) {
            $.fn.zTree.destroy(ztreeObjectName);
            ThisPageObject.data.ztreeObject = $.fn.zTree.init($('#' + ztreeObjectName), setting, dataTree2jstreeData(treeData));
        };
        ThisPageObject.functions.reInitZtreeSimple = function (treeData) {
            $.fn.zTree.destroy(ztreeObjectName);
            ThisPageObject.data.ztreeObject = $.fn.zTree.init($('#' + ztreeObjectName), setting, treeData);
        };
        ThisPageObject.functions.ZtreeGetCheckedNodes = function (checked) {
            return ThisPageObject.data.ztreeObject.getCheckedNodes(_.isBoolean(checked) ? checked : true);
        };
    };

    ThisPageObject.functions.flushList = function () {
        console.log('flushList');
        document.LY.LyHelperTools.HTTP.postQuery('Role_ListAll').then(function (T) {
            console.log(T);
            if (_.isArray(T.list)) {
                ThisPageObject._values.mainTableManager.rebuild(
                    _.map(T.list, function (T) {
                        return {
                            id: _.get(T, 'roleid', ''),
                            role: _.get(T, 'rolename', ''),
                            note: _.get(T, 'describes', ''),
                            createTime: _.get(T, 'createtime', 0),
                        }
                    })
                );
            } else {
                toastr.error('服务器错误，请手动刷新页面', '服务器错误');
            }
        }).catch(function (E) {
            console.log(E);
        }).finally(function () {
            console.log('finally');
        });
    };

    ThisPageObject._initFunctions.initTriggerChanger = function () {
        var triggerAdd = $('.tag-trigger-add');
        var triggerRemove = $('.tag-trigger-remove');

        $(triggerAdd).on('click', function (event) {
            console.log("Y", $(triggerAdd).prop('checked'));
            ThisPageObject.data.ztreeObject.setting.check.chkboxType["Y"] =
                $(triggerAdd).prop('checked') ? "s" : "";
        });
        $(triggerAdd).prop('checked', true);
        // ThisPageObject.data.ztreeObject.setting.check.chkboxType["Y"] = "s";

        $(triggerRemove).on('click', function (event) {
            console.log("N", $(triggerAdd).prop('checked'));
            ThisPageObject.data.ztreeObject.setting.check.chkboxType["N"] =
                $(triggerRemove).prop('checked') ? "s" : "";
        });
        $(triggerRemove).prop('checked', false);
        // ThisPageObject.data.ztreeObject.setting.check.chkboxType["Y"] = "";

    };

}());

var initThis = function () {
    $(function () {
        document.PAGE.role.onInit();
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
                document.page2jsPath + '/node_modules/ztree/js/jquery.ztree.all.js',
                document.page2jsPath + '/node_modules/toastr/toastr.js',
            ], 'jsLib');
        } else {
            loadjs([
                document.page2jsPath + '/node_modules/lodash/lodash.min.js',
                document.page2jsPath + '/node_modules/moment/min/moment.min.js',
                document.page2jsPath + '/node_modules/jquery-modal/jquery.modal.min.js',
                document.page2jsPath + '/node_modules/ztree/js/jquery.ztree.all.min.js',
                document.page2jsPath + '/node_modules/toastr/build/toastr.min.js',
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
            document.page2jsPath + '/js/global-config.js',
            document.page2jsPath + '/js/ly-lib/ly-helper-tools.js',
            document.page2jsPath + '/js/ly-lib/ly-lib-one-way-bind.js',
            document.page2jsPath + '/js/ly-lib/ly-list-select.js',
            document.page2jsPath + '/js/ly-lib/ly-table-manager.js',
            document.page2jsPath + '/js/ly-lib/ly-pagination-manager.js',
            document.page2jsPath + '/js/ly-lib/ly-left-menu-manger.js',
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

