/**
 * Created by Jeremie on 2017/07/16.
 */
'use strict';

// PAGE permissions class
(function () {

    document.PAGE = document.PAGE || {};
    document.PAGE.permissions = {
        // 测试用虚拟数据初始化函数
        _initMockFunctions: {},
        // 私有初始化函数
        _initFunctions: {},
        // 私有内部变量
        _values: {},
        data: {
            ztreeObject: undefined,
        },
        functions: {},
    };
    var ThisPageObject = document.PAGE.permissions;
    ThisPageObject._values = {
        leftMenuManager: null,
    };

    // 初始化函数
    ThisPageObject.onInit = function () {

        ThisPageObject._initFunctions.leftMenuManager();
        ThisPageObject._initFunctions.ztreeTreeMenu();

        // TODO Mock for DEBUG
        ThisPageObject._initMockFunctions.initZtreeTreeData();
    };

    var nowMenu = 'permissions';
    ThisPageObject._initFunctions.leftMenuManager = function () {
        if (!_.isObject(document.LY.LyLeftMenuManager)) {
            console.error("ly-left-menu-manager not init.");
            return;
        } else {
            ThisPageObject._values.leftMenuManager =
                document.LY.LyLeftMenuManager.createManagerEasy(nowMenu);
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
            console.log(treeNode);
        };

        var setting = {
            check: {
                enable: true,
                autoCheckTrigger: true,
                chkStyle: "checkbox",
                chkboxType: {
                    "Y": "ps",
                    "N": "ps",
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
    };

}());

var initThis = function () {
    $(function () {
        document.PAGE.permissions.onInit();
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
                // document.page2jsPath + '/node_modules/flexibility/flexibility.js',
                // document.page2jsPath + '/node_modules/moment/moment.js',
                // document.page2jsPath + '/node_modules/jquery-modal/jquery.modal.js',
                document.page2jsPath + '/node_modules/ztree/js/jquery.ztree.all.js',
            ], 'jsLib');
        } else {
            loadjs([
                document.page2jsPath + '/node_modules/lodash/lodash.min.js',
                // document.page2jsPath + '/node_modules/flexibility/flexibility.js',
                // document.page2jsPath + '/node_modules/moment/min/moment.min.js',
                // document.page2jsPath + '/node_modules/jquery-modal/jquery.modal.min.js',
                document.page2jsPath + '/node_modules/ztree/js/jquery.ztree.all.min.js',
            ], 'jsLib');
        }
    };
    var loadUserLib = function () {
    	  alert(document.page2jsPath + '/js/global-config.js');
        loadjs([
       
           document.page2jsPath + '/js/global-config.js',
            document.page2jsPath + '/js/ly-lib/ly-helper-tools.js',
            // document.page2jsPath + '/js/ly-lib/ly-lib-one-way-bind.js',
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

