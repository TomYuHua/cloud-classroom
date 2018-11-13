/**
 * Created by Jeremie on 2017/07/15.
 */
'use strict';


// PAGE videos class
(function () {

    document.PAGE = document.PAGE || {};
    document.PAGE.videos = {
        // 测试用虚拟数据初始化函数
        _initMockFunctions: {},
        // 私有初始化函数
        _initFunctions: {},
        // 私有内部变量
        _values: {},
        data: {
            NodeAttachData: undefined,
            AddNodeModalAttachData: undefined,
            ChangeNodeModalAttachData: undefined,
            ztreeObject: undefined,
            VideosTreeStructOrigin: undefined,
            VideosTreeStruct: undefined,
            AnalysisFDIsFileFlag: false,
            reviewModalData: undefined,
            viewModalData: undefined,
            deleteModalData: undefined,
            lastTreePath: [],
            lastTreeNode: undefined,
            ResourcesPathHeader: undefined,
            ResourcesViewPageVideoPathHeader: "/getvideocontent/",
            ResourcesViewPageEBookPathHeader: "/getebookcontent/",
            ShowAllChildren: false,
            TextAddUM: undefined,
            TextChangeUM: undefined,
        },
        functions: {},
    };
    var ThisPageObject = document.PAGE.videos;
    ThisPageObject._values = {
        mainTableManager: null,
        paginationManager: null,
        leftMenuManager: null,
        templateVideoTreeMenu: null,
    };

    // 初始化函数
    ThisPageObject.onInit = function () {

        ThisPageObject._initFunctions.initToastrGlobal();
        ThisPageObject._initFunctions.initModalGlobal();
        ThisPageObject._initFunctions.leftMenuManager();
        ThisPageObject._initFunctions.mainTableManager();
        ThisPageObject._initFunctions.paginationManager();
        // ThisPageObject._initFunctions.templateVideoTreeMenu();
        // ThisPageObject._initFunctions.jstreeVideoTreeMenu();
        ThisPageObject._initFunctions.ztreeVideoTreeMenu();
        ThisPageObject._initFunctions.initAutoClosePopup();
        ThisPageObject._initFunctions.addBookNodePopup();
        ThisPageObject._initFunctions.buttonPanel();
        ThisPageObject._initFunctions.addPDFModal();
        ThisPageObject._initFunctions.addWORDModal();
        ThisPageObject._initFunctions.addVIDEOModal();
        ThisPageObject._initFunctions.addTEXTModal();
        ThisPageObject._initFunctions.changeInfoModal();
        ThisPageObject._initFunctions.viewModal();
        ThisPageObject._initFunctions.addNode();
        ThisPageObject._initFunctions.addNodeModal();
        ThisPageObject._initFunctions.deleteModal();
        ThisPageObject._initFunctions.deleteWarningModal();
        ThisPageObject._initFunctions.reviewModal();
        ThisPageObject._initFunctions.changeNodeModal();
        ThisPageObject._initFunctions.oneWayBinderManager();
        ThisPageObject._initFunctions.initDoubleLineChanger();
        ThisPageObject._initFunctions.initShowAllChildrenChanger();

         ThisPageObject._values.mainTableManager.LyListSelectTableManger.changeCallBackList.push(
             function (NodeList) {
                 // console.log(NodeList);
                 var buttonPanel = $('.button-panel');
                 if (NodeList.length === 0) {
                     $(buttonPanel).find('.b-change').prop('disabled', true);
                 }
                 if (NodeList.length === 1) {
                     $(buttonPanel).find('.b-change').prop('disabled', false);
                 } else {
                     $(buttonPanel).find('.b-delete').prop('disabled', true);
                 }
                 if (NodeList.length > 0) {
                     $(buttonPanel).find('.b-delete').prop('disabled', false);
                 }
             }
         );
         ThisPageObject._values.mainTableManager.LyListSelectTableManger.noticeCallback();
         ThisPageObject._values.mainTableManager.selectedChangeCallback = function (dataList) {
             console.log(dataList);
         };

        ThisPageObject._values.mainTableManager.afterReInitCallback = function (rootNode) {
            // console.log($(ThisPageObject._values.mainTableManager.getAllRowNode()).find('.row-button'));
            // console.log($(rootNode).find('.row-button'));
            ThisPageObject.functions.reInitRowButtonPanel();
        };

        ThisPageObject._values.oneWayBinderManager.flush();

        ThisPageObject.functions.getResourcesPathHeader();
        ThisPageObject.functions.flushTree();

        // Mock for DEBUG
        // ThisPageObject._initMockFunctions.initZtreeVideoTreeData();

        // Mock for DEBUG
        // window.toastr.success("success");
        // window.toastr.error("error");
        // window.toastr.info("info");
        // window.toastr.warning("warning");

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

    var TreeFieldMap = [
        ['id', 'id', _.parseInt],
        ['parentId', 'parentId', _.parseInt],
        ['isDocument', 'isdocument'],
        ['name', 'name'],
        ['types', 'types', _.parseInt],
        ['icon', 'icon'],
        ['createAuthor', 'createauthor'],
        ['userId', 'userid', _.parseInt],
        ['resourcePath', 'resourcepath'],
        ['describes', 'describes'],
        ['scores', 'scores'],
        ['clickCount', 'clickcount', _.parseInt],
        ['isOpen', 'isopen', _.parseInt],
        ['imgPath', 'imgsrc'],
        ['status', 'status', _.parseInt, 0],
        ['createTime', 'createtime'],
        ['contents', 'contents'],
        ['sort', 'sort'],
        // ['directoriesId', 'directoriesid'],
        // ['orderType', 'orderType'],
    ];
    ThisPageObject.functions.flushTree = function () {
        // get data from server and build tree
        return document.LY.LyHelperTools.HTTP.postQuery('Videos_GetAll', {}).then(function (data) {
            console.log(data);
            data = document.LY.LyHelperTools.compactObjectDeep(data);
            console.log(data);
            if (_.isArray(_.get(data, 'list'))) {
                ThisPageObject.data.VideosTreeStructOrigin = _.map(data.list, function (T) {
                    var obj = {};
                    _.forEach(TreeFieldMap, function (F) {
                        _.set(obj, F[0], F[2] ? F[2](_.get(T, F[1], F[3])) : _.get(T, F[1], F[3])
                        );
                    });
                    return obj;
                });
                console.log(ThisPageObject.data.VideosTreeStructOrigin);
                ThisPageObject.functions.BuildVideosTreeStruct();
                console.log(ThisPageObject.data.VideosTreeStruct);
                // build
                var r = ThisPageObject.functions.BuildVideosTreeStructTree();
                console.log(r);
                ThisPageObject.functions.reInitZtreeSimple(r[0]);
                if (r[1]) {
                    var idKey = ThisPageObject._values.TreeBuilderConfig.idKey;
                    var parentIdKey = ThisPageObject._values.TreeBuilderConfig.parentIdKey;
                    var targetNodes = ThisPageObject.data.ztreeObject.getNodesByFilter(function (node) {
                        return _.get(node.dataCustom, idKey) === _.get(r[1].dataCustom, idKey) &&
                            _.get(node.dataCustom, parentIdKey) === _.get(r[1].dataCustom, parentIdKey);
                    }, true);
                    if (ThisPageObject.data.lastTreePath.length !== 0) {
                        if (!targetNodes) {
                            throw Error('getNodesByFilter error. algorithm error!');
                        }
                        console.log(targetNodes);
                        ThisPageObject.data.ztreeObject.selectNode(targetNodes);
                        ThisPageObject.functions.flushPage(targetNodes);
                        return;
                    }
                }
                ThisPageObject.functions.flushPage(null);
            } else {
                toastr.error('服务器错误，请手动刷新页面', '服务器错误');
                ThisPageObject.functions.flushPage(null);
            }
        }).catch(function (E) {
            console.log(E);
            ThisPageObject.functions.flushPage(null);
        }).finally(function () {
            console.log('end');
        });
    };
    ThisPageObject.functions.TreeSendRemoveNode = function (node, manual) {
        // remove a node,
        console.log("node", node);
        if (manual && node.dataCustom.childrenList.length === 0) {
            var data = {
                ids: node.dataCustom.id,
            };
            return document.LY.LyHelperTools.HTTP.postQuery('Videos_Delete', data).then(function (data) {
                console.log(data);
                if (_.get(T, 'string') === "success") {
                    toastr.success('操作成功');
                } else {
                    toastr.error('操作失败');
                }
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                ThisPageObject.functions.flushTree();
            });
        }
        // TODO and implicit remove all children (if the design neeed)
        ThisPageObject.functions.openDeleteWarningModal();
        return Promise.reject();
    };
    ThisPageObject.functions.TreeSendAddNode = function (nodeInfo, rootNode) {
        var data = {
            // root node's parent id are 0
            parentId: rootNode ? rootNode.dataCustom.id : 0,
            name: nodeInfo.name,
            sort: nodeInfo.sort,
        };
        console.log(data);
        return document.LY.LyHelperTools.HTTP.postJson('Videos_AddNode', data).then(function (data) {
            console.log(data);
            if (_.get(T, 'int') === 1) {
                toastr.success('操作成功');
            } else {
                toastr.error('操作失败');
            }
        }).catch(function (E) {
            console.log(E);
        }).finally(function () {
            console.log('end');
            ThisPageObject.functions.flushTree();
        });
    };
    ThisPageObject.functions.TreeSendChangeNodeName = function (nodeInfo, node) {
        // rename a node
        var data = {
            id: "" + node.dataCustom.id,
            name: "" + nodeInfo.name,
            sort: "" + nodeInfo.sort,
        };
        return document.LY.LyHelperTools.HTTP.postQuery('Videos_ChangeNodeName', data).then(function (data) {
            console.log(data);
            if (_.get(data, 'string') === "ok") {
                toastr.success('操作成功');
            } else {
                toastr.error('操作失败');
            }
        }).catch(function (E) {
            console.log(E);
        }).finally(function () {
            console.log('end');
            ThisPageObject.functions.flushTree();
        });
    };
    ThisPageObject.functions.RememberThisTreeNodePath = function (treeNode) {
        ThisPageObject.data.lastTreePath = [];
        ThisPageObject.data.lastTreeNode = treeNode;
        var pNode = treeNode;
        while (_.isObject(pNode)) {
            // console.log(pNode);
            ThisPageObject.data.lastTreePath.push(
                _.get(pNode.dataCustom, ThisPageObject._values.TreeBuilderConfig.idKey)
            );
            pNode = pNode.getParentNode();
        }
        ThisPageObject.data.lastTreePath = _.reverse(ThisPageObject.data.lastTreePath);
        console.log("lastTreePath", ThisPageObject.data.lastTreePath);
    };
    ThisPageObject.functions.flushPage = function (node) {
        // flush page table for a node or clean page table
        if (!node) {
            // clean
            ThisPageObject._values.mainTableManager.rebuild([]);
            $('.flag-content').hide();
            $('.flag-content-placeholder').show();
            return Promise.resolve();
        } else {
            console.log(node.dataCustom.id);

            var fileList = [];
            if (!ThisPageObject.data.ShowAllChildren) {
                // only one level
                fileList = node.dataCustom.childrenList.filter(function (T) {
                    return _.get(T, 'isDocument') === ThisPageObject.data.AnalysisFDIsFileFlag;
                });
            } else {
                // deep
                fileList = ThisPageObject.functions.getAllChildrenListFromTreeStructNode(node.dataCustom)
                    .filter(function (T) {
                        return _.get(T, 'isDocument') === ThisPageObject.data.AnalysisFDIsFileFlag;
                    });
            }
            console.log(fileList);

            // Mock for DEBUG
            // ThisPageObject._initMockFunctions.buildPageTable();

            // lastTreePath
            ThisPageObject.functions.RememberThisTreeNodePath(node);

            ThisPageObject._values.mainTableManager.rebuild(_.map(fileList, function (T) {
                return {
                    id: _.get(T, 'id'),
                    title: _.get(T, 'name'),
                    author: _.get(T, 'createAuthor'),
                    upload: _.get(T, 'createTime'),
                    score: _.get(T, 'scores'),
                    status: (function (T) {
                        switch (T) {
                            case 1:
                                return '通过';
                            case 2:
                                return '不通过';
                            default:
                                return '未审核';
                        }
                    }(_.get(T, 'status'))),
                    data: T,
                }
            }));

            $('.flag-content').show();
            $('.flag-content-placeholder').hide();
            return Promise.resolve();
        }
    };
    ThisPageObject.functions.getResourcesPathHeader = function () {
        return document.LY.LyHelperTools.HTTP.getQuery('Videos_GetResourcesPathHeader', {}).then(function (T) {
            console.log(T);
            if (_.isString(_.get(T, 'string'))) {
                ThisPageObject.data.ResourcesPathHeader = _.get(T, 'string');
            } else {
                toastr.error('服务器错误，请刷新页面重试', '服务器错误');
            }
        }).catch(function (E) {
            console.log(E);
            toastr.error('服务器错误，请刷新页面重试', '服务器错误');
        }).finally(function () {
            console.log('end');
        });
    };

    var TreeBuilderConfig = {
        idKey: 'id',
        parentIdKey: 'parentId',
        nameKey: 'name',
        sortKeyList: ['sort', 'name',],
    };
    ThisPageObject._values.TreeBuilderConfig = TreeBuilderConfig;
    ThisPageObject.functions.BuildVideosTreeStruct = function () {
        // struct tree
        var originData = _.clone(ThisPageObject.data.VideosTreeStructOrigin);
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

        // Analysis whatever value are file, whatever are directory
        var AnalysisFD = {
            true: 0,
            false: 0,
        };
        reverseMap.forEach(function (T) {
            if (T.childNoS.size !== 0) {
                if (_.get(T, 'isDocument')) {
                    AnalysisFD.true += 1;
                } else {
                    AnalysisFD.false += 1;
                }
            }
        });
        ThisPageObject.data.AnalysisFDIsFileFlag = AnalysisFD.true < AnalysisFD.false;

        var noChildMap = new Map();
        var haveChildMap = _.clone(reverseMap);
        var flagContinue = true;
        while (true) {
            flagContinue = false;
            // split node to two map, one not have child, one other
            haveChildMap.forEach(function (V, K, T) {
                if (V.childNoS.size === 0) {
                    noChildMap.set(K, V);
                    T.delete(K);
                    flagContinue = true;
                }
            });
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
        ThisPageObject.data.VideosTreeStruct = [];
        noChildMap.forEach(function (V, K, T) {
            if (_.get(V, TreeBuilderConfig.parentIdKey)) {
                console.warn(
                    "if (_.get(v, '" + TreeBuilderConfig.parentIdKey + "')):",
                    _.get(V, TreeBuilderConfig.parentIdKey)
                );
            }
            ThisPageObject.data.VideosTreeStruct.push(V);
        });
        // now, the node's "childrenList" is the child node list
    };
    ThisPageObject.functions.BuildVideosTreeStructTree = function () {
        var defaultState = {
            // open: true,
        };
        var openPathList = new Map();
        _.forEach(ThisPageObject.data.lastTreePath, function (T, i) {
            openPathList.set(i, T);
        });
        var needOpenNode = undefined;
        var reduceChildren = function (C, level) {
            if (!level) {
                level = 0;
            }
            if (_.isArray(C)) {
                var rList = _.reduce(C, function (S, T) {
                    // don't show the 'isDocument' only when it's the tree's leaf
                    if (
                        _.get(T, 'isDocument') === ThisPageObject.data.AnalysisFDIsFileFlag &&
                        T.childrenList.length === 0
                    ) {
                        return S;
                    }
                    // TODO re-order reduceChildren return

                    // open last open path
                    var isOpen = false;
                    if (
                        !openPathList.has(level - 1) &&
                        openPathList.get(level) === _.get(T, TreeBuilderConfig.idKey)
                    ) {
                        isOpen = true;
                        openPathList.delete(level);
                    }
                    var isTargetNode = (isOpen && openPathList.size === 0);
                    var node = {
                        name: _.get(T, TreeBuilderConfig.nameKey),
                        children: reduceChildren(T.childrenList, level + 1),
                        dataCustom: T,
                        open: isOpen,
                    };
                    needOpenNode = isTargetNode ? node : needOpenNode;
                    S.push(_.assign(node, defaultState));
                    return S;
                }, []);
                rList = _.sortBy(rList, TreeBuilderConfig.sortKeyList.map(function (T) {
                    return function (I) {
                        return _.get(I.dataCustom, T);
                    };
                }));
                // rList = _.sortBy(rList, [function (T) {
                //     return T.dataCustom.name;
                // }]);
                return rList;
            }
            return [];
        };
        return [reduceChildren(_.clone(ThisPageObject.data.VideosTreeStruct)), needOpenNode];
    };
    ThisPageObject.functions.getAllChildrenListFromTreeStructNode = function (node) {
        var stack = _.clone(node.childrenList);
        var outList = [];
        outList.push(node);
        while (stack.length !== 0) {
            var n = stack.shift();
            stack = _.concat(stack, n.childrenList);
            outList.push(n);
        }
        return outList;
    };

    ThisPageObject._initMockFunctions.buildPageTable = function () {
        // TODO Mock
        ThisPageObject._values.mainTableManager.rebuild([
            {
                id: 0,
                title: 'title',
                author: 'author',
                upload: 'upload',
                duration: 'duration',
                score: 'score',
            },
            {
                id: 1,
                title: 'title',
                author: 'author',
                upload: 'upload',
                duration: 'duration',
                score: 'score',
            },
            {
                id: 2,
                title: 'title',
                author: 'author',
                upload: 'upload',
                duration: 'duration',
                score: 'score',
            },
        ]);
    };

    ThisPageObject._initFunctions.mainTableManager = function () {

        var fieldList = [
            'title',
            'author',
            'upload',
            'status',
            'score',
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
            if (field === 'upload') {
                return document.LY.LyHelperTools.UnixTimestampMilliseconds2DateString(data);
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
            var paginationManager = ThisPageObject._values.paginationManager;
            paginationManager.callback.click = function (index) {
                //
            };
            paginationManager.callback.go = function (index) {
                //
            };
        }
    };

    ThisPageObject._initFunctions.initAutoClosePopup = function () {
        // https://stackoverflow.com/questions/1403615/use-jquery-to-hide-a-div-when-the-user-clicks-outside-of-it
        var targetNodeList = [
            $(".popup-container.f-add-book-node")
        ];
        $(document).mouseup(function (e) {
            _.forEach(targetNodeList, function (T) {
                var container = T;
                // if the target of the click isn't the container nor a descendant of the container
                if (!container.is(e.target) && container.has(e.target).length === 0) {
                    container.hide();
                }
            });
        });
    };

    ThisPageObject._initFunctions.addBookNodePopup = function () {
        var rootNode = $(".popup-container.f-add-book-node");

        $(rootNode).find(".add-node").on('click', function () {
            ThisPageObject.data.AddNodeModalAttachData = ThisPageObject.data.NodeAttachData;
            ThisPageObject.functions.showAddNodeModal();
            $(rootNode).hide();
        });
        $(rootNode).find(".rename-node").on('click', function () {
            ThisPageObject.data.ChangeNodeModalAttachData = ThisPageObject.data.NodeAttachData;
            ThisPageObject.functions.showChangeNodeModal();
            $(rootNode).hide();
        });
        $(rootNode).find(".delete-node").on('click', function () {
            var data = ThisPageObject.data.NodeAttachData;
            ThisPageObject.functions.TreeSendRemoveNode(data.treeNode, true).then(function () {
                ThisPageObject.functions.treeRemoveNode(data.treeNode);
            }).finally(function () {
                $(rootNode).hide();
            });
        });
        $(rootNode).find(".add-pdf-node").on('click', function () {
            ThisPageObject.functions.openAddPDFModal();
        });
        $(rootNode).find(".add-word-node").on('click', function () {
            ThisPageObject.functions.openAddWORDModal();
        });
        $(rootNode).find(".add-video-node").on('click', function () {
            ThisPageObject.functions.openAddVIDEOModal();
        });
        $(rootNode).find(".add-text-node").on('click', function () {
            ThisPageObject.functions.openAddTEXTModal();
        });

        ThisPageObject.functions.showAddBookNodeModal = function (X, Y, data) {
            $(rootNode).css("top", Y).css("left", X).show();
            ThisPageObject.data.NodeAttachData = data;
            ThisPageObject.functions.RememberThisTreeNodePath(data.treeNode);
            $(rootNode).find('.delete-node').show();
            if (data.treeNode.isParent) {
                $(rootNode).find('.delete-node').hide();
            }
        };

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

        $(buttonPanel).find('.b-delete').on('click', function () {
            // TODO
        });
        $(buttonPanel).find('.b-add-pdf').on('click', function () {
            ThisPageObject.functions.openAddPDFModal();
        });
        $(buttonPanel).find('.b-add-word').on('click', function () {
            ThisPageObject.functions.openAddWORDModal();
        });
        $(buttonPanel).find('.b-add-video').on('click', function () {
            ThisPageObject.functions.openAddVIDEOModal();
        });
        $(buttonPanel).find('.b-add-text').on('click', function () {
            ThisPageObject.functions.openAddTEXTModal();
        });
    };

    ThisPageObject.functions.reInitRowButtonPanel = function () {
        var rootNode = $(ThisPageObject._values.mainTableManager.getAllRowNode()).find('.row-button');

        $(rootNode).each(function () {
            var ThisPtr = $(this);
            var data = ThisPageObject._values.mainTableManager.getRowDataInRow(ThisPtr);
            if (_.parseInt(data.status) === 1 || _.parseInt(data.status) === 2) {
                ThisPtr.addClass('delete-line');
            }
        });

        $(rootNode).find('.b-review').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.functions.openReviewModal(rowData);
        });
        $(rootNode).find('.b-view').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.functions.openViewModal(rowData);
        });
        $(rootNode).find('.b-change').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.functions.openChangeInfoModal(rowData);
        });
        $(rootNode).find('.b-delete').on('click', function (event) {
            var rowData = ThisPageObject._values.mainTableManager.getRowDataInRow(event.target);
            ThisPageObject.functions.openDeleteModal(rowData);
        });
    };

    var nowMenu = 'videos';
    ThisPageObject._initFunctions.leftMenuManager = function () {
        if (!_.isObject(document.LY.LyLeftMenuManager)) {
            console.error("ly-left-menu-manager not init.");
            return;
        } else {
            ThisPageObject._values.leftMenuManager =
                document.LY.LyLeftMenuManager.createManagerEasy(nowMenu);
        }
    };

    ThisPageObject._initMockFunctions.initZtreeVideoTreeData = function () {
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

    ThisPageObject._initFunctions.ztreeVideoTreeMenu = function () {

        var ztreeObjectName = "ztree-video-tree-menu";

        var onNodeBeSelect = function (event, treeId, treeNode) {
            console.log(event);
            console.log(treeId);
            console.log(treeNode);
            ThisPageObject.functions.flushPage(treeNode);
        };
        var onNodeBeRemove = function (event, treeId, treeNode) {
            console.log(treeNode);
            ThisPageObject.functions.TreeSendRemoveNode(treeNode, false);
        };
        var onNodeBeRightClick = function (event, treeId, treeNode) {
            console.log(event.currentTarget);
            console.log(event.clientX);
            console.log(event.clientY);
            ThisPageObject.functions.showAddBookNodeModal(
                event.clientX,
                event.clientY,
                {
                    treeNode: treeNode,
                    level: treeNode.level,
                    tId: treeNode.tId,
                    parentTId: treeNode.parentTId,
                    isFirstNode: treeNode.isFirstNode,
                    isLastNode: treeNode.isLastNode,
                }
            );
        };

        var setting = {
            callback: {
                onClick: onNodeBeSelect,
                onRightClick: onNodeBeRightClick,
                // onRemove: onNodeBeRemove,
            },
            view: {
                showIcon: false,
                // dblClickExpand: false,
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

        ThisPageObject.functions.treeAddNode = function (parentNode, name) {
            return ThisPageObject.data.ztreeObject.addNodes(parentNode, -1, {
                name: name,
            });
        };
        ThisPageObject.functions.treeAddRootNode = function (name) {
            return ThisPageObject.functions.treeAddNode(null, name);
        };
        ThisPageObject.functions.treeChangeNodeName = function (node, newName) {
            node.name = newName;
            ThisPageObject.data.ztreeObject.updateNode(node);
        };
        ThisPageObject.functions.treeRemoveNode = function (node) {
            ThisPageObject.data.ztreeObject.removeNode(node);
        };
    };

    ThisPageObject._initFunctions.addPDFModal = function () {
        var rootNode = $(".popup-container.f-add-PDF");

        $(rootNode).find('.b-save').on('click', function () {
            if (!document.LY.LyHelperTools.checkFileType('PDF', $(rootNode).find('input.f-file').val())) {
                toastr.error('请选择正确类型的 PDF 文件.', '文件类型不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkFileType('IMAGE', $(rootNode).find('input.f-img').val())) {
                toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                return;
            }
            var data = ThisPageObject.data.NodeAttachData;
            var fd = new FormData($(rootNode).find('form').get()[0]);
            fd.append('Types', 2);  // 1视频 2文档 3电子书
            fd.append('parentid', ThisPageObject.data.lastTreeNode.dataCustom.id);
            var loadingManager = document.LY.LyLoading4ModalSubmit.makeLoadingByRootNode(rootNode);
            document.LY.LyHelperTools.HTTP.sendFormDataReturnJson('Videos_UploadFile', fd).then(function (T) {
                console.log(T);
                if (_.get(T, 'state')) {
                    toastr.success('操作成功');
                } else {
                    toastr.error('操作失败');
                }
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                loadingManager.destroy();
                $.modal.close();
                ThisPageObject.functions.flushTree();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
            document.LY.LyLoading4ModalSubmit.removeAll();
            ThisPageObject.functions.flushTree();
        });
        ThisPageObject.functions.openAddPDFModal = function () {
            $(rootNode).find('form').trigger("reset");
            $(rootNode).find('.f-sort').val(1);
            $(rootNode).modal();
        };

    };
    ThisPageObject._initFunctions.addWORDModal = function () {
        var rootNode = $(".popup-container.f-add-WORD");

        $(rootNode).find('.b-save').on('click', function () {
            if (!document.LY.LyHelperTools.checkFileType('WORD', $(rootNode).find('input.f-file').val())) {
                toastr.error('请选择正确类型的 WORD 文件.', '文件类型不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkFileType('IMAGE', $(rootNode).find('input.f-img').val())) {
                toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                return;
            }
            var data = ThisPageObject.data.NodeAttachData;
            var fd = new FormData($(rootNode).find('form').get()[0]);
            fd.append('Types', 2);  // 1视频 2文档 3电子书
            fd.append('parentid', ThisPageObject.data.lastTreeNode.dataCustom.id);
            var loadingManager = document.LY.LyLoading4ModalSubmit.makeLoadingByRootNode(rootNode);
            document.LY.LyHelperTools.HTTP.sendFormDataReturnJson('Videos_UploadFile', fd).then(function (T) {
                console.log(T);
                if (_.get(T, 'state')) {
                    toastr.success('操作成功');
                } else {
                    toastr.error('操作失败');
                }
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                loadingManager.destroy();
                $.modal.close();
                ThisPageObject.functions.flushTree();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
            document.LY.LyLoading4ModalSubmit.removeAll();
        });
        ThisPageObject.functions.openAddWORDModal = function () {
            $(rootNode).find('form').trigger("reset");
            $(rootNode).find('.f-sort').val(1);
            $(rootNode).modal();
        };

    };
    ThisPageObject._initFunctions.addVIDEOModal = function () {
        var rootNode = $(".popup-container.f-add-VIDEO");

        $(rootNode).find('.b-save').on('click', function () {
            if (!document.LY.LyHelperTools.checkFileType('VIDEO', $(rootNode).find('input.f-file').val())) {
                toastr.error('请选择正确类型的 视频 文件.', '文件类型不正确');
                return;
            }
            if (!document.LY.LyHelperTools.checkFileType('IMAGE', $(rootNode).find('input.f-img').val())) {
                toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                return;
            }
            var data = ThisPageObject.data.NodeAttachData;
            var fd = new FormData($(rootNode).find('form').get()[0]);
            fd.append('Types', 1);  // 1视频 2文档 3电子书
            fd.append('parentid', ThisPageObject.data.lastTreeNode.dataCustom.id);
            var loadingManager = document.LY.LyLoading4ModalSubmit.makeLoadingByRootNode(rootNode);
            document.LY.LyHelperTools.HTTP.sendFormDataReturnJson('Videos_UploadFile', fd).then(function (T) {
                console.log(T);
                if (_.get(T, 'state')) {
                    toastr.success('操作成功');
                } else {
                    toastr.error('操作失败');
                }
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                loadingManager.destroy();
                $.modal.close();
                ThisPageObject.functions.flushTree();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
            document.LY.LyLoading4ModalSubmit.removeAll();
        });
        ThisPageObject.functions.openAddVIDEOModal = function () {
            $(rootNode).find('form').trigger("reset");
            $(rootNode).find('.f-sort').val(1);
            $(rootNode).modal();
        };

    };
    ThisPageObject._initFunctions.addTEXTModal = function () {
        var rootNode = $(".popup-container.f-add-TEXT");

        $(rootNode).find('.b-save').on('click', function () {
            if (!document.LY.LyHelperTools.checkFileType('IMAGE', $(rootNode).find('input.f-img').val())) {
                toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                return;
            }
            var data = ThisPageObject.data.NodeAttachData;
            var fd = new FormData($(rootNode).find('form').get()[0]);
            ThisPageObject.data.TextAddUM.sync();
            var content = ThisPageObject.data.TextAddUM.getContent();
            console.log(content);
            fd.append('Types', 3);  // 1视频 2文档 3电子书
            fd.append('text', content);
            fd.append('parentid', ThisPageObject.data.lastTreeNode.dataCustom.id);
            var loadingManager = document.LY.LyLoading4ModalSubmit.makeLoadingByRootNode(rootNode);
            document.LY.LyHelperTools.HTTP.sendFormDataReturnJson('Videos_UploadFile', fd).then(function (T) {
                console.log(T);
                if (_.get(T, 'state')) {
                    toastr.success('操作成功');
                } else {
                    toastr.error('操作失败');
                }
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                loadingManager.destroy();
                $.modal.close();
                ThisPageObject.functions.flushTree();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
            document.LY.LyLoading4ModalSubmit.removeAll();
        });
        ThisPageObject.functions.openAddTEXTModal = function () {
            $(rootNode).find('form').trigger("reset");
            $(rootNode).find('.f-sort').val(1);
            $(rootNode).modal();

            if (!ThisPageObject.data.TextAddUM) {
                ThisPageObject.data.TextAddUM = UM.getEditor('f-text-add');
            }
            ThisPageObject.data.TextAddUM.setContent("");
        };

    };

    ThisPageObject._initFunctions.viewModal = function () {
        var rootNode = $(".popup-container.f-view");

        $(rootNode).find('.b-save').on('click', function () {
            var data = ThisPageObject.data.viewModalData;
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openViewModal = function (rowData) {
            ThisPageObject.data.viewModalData = rowData;
            $(rootNode).find('.f-img').prop('src',
                ThisPageObject.data.ResourcesPathHeader + _.get(rowData.data, 'imgPath')
            );
            $(rootNode).find('.f-name').val(rowData.data.name);
            $(rootNode).find('.f-note').val(rowData.data.describes);
            $(rootNode).find('.f-sort').val(rowData.data.sort);
            var types = _.get(rowData.data, 'types');
            if (types === 1) {
                $(rootNode).find('.f-link').prop('href',
                    ThisPageObject.data.ResourcesViewPageVideoPathHeader + _.get(rowData.data, 'id')
                );
            } else if (types === 3) {
                $(rootNode).find('.f-link').prop('href',
                    ThisPageObject.data.ResourcesViewPageEBookPathHeader + _.get(rowData.data, 'id')
                );
            } else {
                $(rootNode).find('.f-link').prop('href',
                    ThisPageObject.data.ResourcesPathHeader + _.get(rowData.data, 'resourcePath')
                );
            }
            $(rootNode).modal();
        };

    };

    ThisPageObject._initFunctions.reviewModal = function () {
        var rootNode = $(".popup-container.f-review");

        $(rootNode).find('.b-ok').on('click', function () {
            var d = ThisPageObject.data.reviewModalData;
            var data = {
                id: d.data.id,
                status: 1,
            };
            console.log(data);
            document.LY.LyHelperTools.HTTP.postJson('Videos_Review', data).then(function (T) {
                console.log(T);
                if (_.get(T, 'string') === "ok") {
                    toastr.success('操作成功');
                } else {
                    toastr.error('操作失败');
                }
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                $.modal.close();
                ThisPageObject.functions.flushTree();
            });
        });
        $(rootNode).find('.b-no').on('click', function () {
            var d = ThisPageObject.data.reviewModalData;
            var data = {
                id: d.data.id,
                status: 2,
            };
            console.log(data);
            document.LY.LyHelperTools.HTTP.postJson('Videos_Review', data).then(function (T) {
                console.log(T);
                if (_.get(T, 'string') === "ok") {
                    toastr.success('操作成功');
                } else {
                    toastr.error('操作失败');
                }
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                $.modal.close();
                ThisPageObject.functions.flushTree();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openReviewModal = function (rowData) {
            ThisPageObject.data.reviewModalData = rowData;
            if (rowData.data.status !== 1 && rowData.data.status !== 2) {
                $(rootNode).modal();
            }
        }
    };

    ThisPageObject._initFunctions.deleteModal = function () {
        var rootNode = $(".popup-container.f-delete");

        $(rootNode).find('.b-save').on('click', function () {
            var d = ThisPageObject.data.deleteModalData;
            var data = {
                ids: d.data.id,
            };
            console.log(data);
            document.LY.LyHelperTools.HTTP.postQuery('Videos_Delete', data).then(function (T) {
                console.log(T);
                if (_.get(T, 'string') === "success") {
                    toastr.success('操作成功');
                } else {
                    toastr.error('操作失败');
                }
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                $.modal.close();
                ThisPageObject.functions.flushTree();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openDeleteModal = function (rowData) {
            ThisPageObject.data.deleteModalData = rowData;
            $(rootNode).find('.t-title-name').text(
                ThisPageObject.data.deleteModalData.data.name
            );
            $(rootNode).modal();
        }
    };

    ThisPageObject._initFunctions.deleteWarningModal = function () {
        var rootNode = $(".popup-container.f-delete-warning");

        $(rootNode).find('.b-save').on('click', function () {
            $.modal.close();
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openDeleteWarningModal = function (rowData) {
            $(rootNode).modal();
        }
    };

    ThisPageObject._initFunctions.changeInfoModal = function () {
        var rootNode = $(".popup-container.f-change-info");

        $(rootNode).find('.b-save').on('click', function () {
            var d = ThisPageObject.data.changeInfoModalData;
            var imgFile = $(rootNode).find('input.f-img').val();
            if (_.isString(imgFile)) {
                if (!document.LY.LyHelperTools.checkFileType('IMAGE', $(rootNode).find('input.f-img').val())) {
                    toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                    return;
                }
            }
            if (_.isArray(imgFile)) {
                toastr.error('请选择正确类型的 图像 文件.', '文件类型不正确');
                return;
            }
            var fd = new FormData($(rootNode).find('form').get()[0]);
            ThisPageObject.data.TextChangeUM && ThisPageObject.data.TextChangeUM.sync();
            var content = ThisPageObject.data.TextChangeUM ? ThisPageObject.data.TextChangeUM.getContent() : "";
            console.log(content);
            fd.append('Types', 3);  // 1视频 2文档 3电子书
            fd.append('text', content);
            fd.append("id", "" + d.data.id);
            var loadingManager = document.LY.LyLoading4ModalSubmit.makeLoadingByRootNode(rootNode);
            document.LY.LyHelperTools.HTTP.sendFormDataReturnJson('Videos_Change', fd).then(function (T) {
                console.log(T);
                if (_.get(T, 'string') === "ok") {
                    toastr.success('操作成功');
                } else {
                    toastr.error('操作失败');
                }
            }).catch(function (E) {
                console.log(E);
            }).finally(function () {
                console.log('end');
                loadingManager.destroy();
                $.modal.close();
                ThisPageObject.functions.flushTree();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.openChangeInfoModal = function (rowData) {
            ThisPageObject.data.changeInfoModalData = rowData;
            $(rootNode).find('.f-showImg').prop('src',
                ThisPageObject.data.ResourcesPathHeader + _.get(rowData.data, 'imgPath')
            );
            $(rootNode).find('.f-name').val(rowData.data.name);
            $(rootNode).find('.f-note').val(rowData.data.describes);
            $(rootNode).find('.f-sort').val(rowData.data.sort);
            var types = _.get(rowData.data, 'types');
            $(rootNode).find('.flag-UM').hide();
            if (types === 1) {
                $(rootNode).find('.f-link').prop('href',
                    ThisPageObject.data.ResourcesViewPageVideoPathHeader + _.get(rowData.data, 'id')
                );
                $(rootNode).modal();
            } else if (types === 3) {
                $(rootNode).find('.flag-UM').show();
                $(rootNode).find('.f-link').prop('href',
                    ThisPageObject.data.ResourcesViewPageEBookPathHeader + _.get(rowData.data, 'id')
                );
                if (!ThisPageObject.data.TextChangeUM) {
                    ThisPageObject.data.TextChangeUM = UM.getEditor('f-text-change');
                }

                document.LY.LyHelperTools.HTTP.postQuery("Videos_GetTextContent", {
                    id: _.get(rowData.data, 'id'),
                    Types: 3,
                }).then(function (T) {
                    console.log(T);
                    if (_.get(T, 'state') === true) {
                        ThisPageObject.data.TextChangeUM.setContent(_.get(T, 'content'));
                        $(rootNode).modal();
                    } else {
                        toastr.error('TEXT内容加载失败，请重试', '数据获取失败');
                    }
                }).catch(function (E) {
                    console.log(E);
                }).finally(function () {
                    console.log('end');
                });
            } else {
                $(rootNode).find('.f-link').prop('href',
                    ThisPageObject.data.ResourcesPathHeader + _.get(rowData.data, 'resourcePath')
                );
                $(rootNode).modal();
            }

        }
    };

    ThisPageObject._initFunctions.addNode = function () {
        $(".adding-root").on('click', function () {
            ThisPageObject.data.AddNodeModalAttachData = null;
            ThisPageObject.functions.showAddNodeModal();
        });
    };

    ThisPageObject._initFunctions.addNodeModal = function () {
        var rootNode = $(".popup-container.f-node-name");

        $(rootNode).find('.b-save').on('click', function () {
            var data = ThisPageObject.data.AddNodeModalAttachData;
            var name = $(rootNode).find('.f-name').val();
            var sort = $(rootNode).find('.f-sort').val();
            // var addedNodeList = [];
            // if (data === null) {
            //     // root
            //     addedNodeList = ThisPageObject.functions.treeAddRootNode(name);
            // } else {
            //     // node
            //     addedNodeList = ThisPageObject.functions.treeAddNode(data.treeNode, name);
            // }
            ThisPageObject.functions.TreeSendAddNode({
                name: name,
                sort: sort,
            }, data ? data.treeNode : data).then(function () {
            }).finally(function () {
                ThisPageObject.functions.flushTree();
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.showAddNodeModal = function () {
            $(rootNode).find('.f-name').val("");
            $(rootNode).find('.f-sort').val(1);
            $(rootNode).modal();
        };

    };

    ThisPageObject._initFunctions.changeNodeModal = function () {
        var rootNode = $(".popup-container.f-change-node-name");

        $(rootNode).find('.b-save').on('click', function () {
            var data = ThisPageObject.data.ChangeNodeModalAttachData;
            var name = $(rootNode).find('.f-name').val();
            var sort = $(rootNode).find('.f-sort').val();
            // if (data !== null) {
            //     ThisPageObject.functions.treeChangeNodeName(data.treeNode, name);
            // }
            ThisPageObject.functions.TreeSendChangeNodeName({
                name: name,
                sort: sort,
            }, data.treeNode).then(function () {
            }).finally(function () {
                $.modal.close();
            });
        });
        $(rootNode).find('.b-close').on('click', function () {
            $.modal.close();
        });

        ThisPageObject.functions.showChangeNodeModal = function () {
            var data = ThisPageObject.data.ChangeNodeModalAttachData;
            $(rootNode).find('.f-name').val(data.treeNode.dataCustom.name);
            $(rootNode).find('.f-sort').val(data.treeNode.dataCustom.sort);
            $(rootNode).modal();
        };

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

    ThisPageObject._initFunctions.initDoubleLineChanger = function () {
        var changeTag = $('.tag-double-line-changer');
        var trigger = $('.tag-double-line-changer-trigger');

        $(trigger).on('click', function (event) {
            console.log(event);
            if ($(trigger).prop('checked')) {
                $(changeTag).addClass('double-line');
            } else {
                $(changeTag).removeClass('double-line');
            }
        });
        $(trigger).prop('checked', false);

    };

    ThisPageObject._initFunctions.initShowAllChildrenChanger = function () {
        var trigger = $('.tag-show-all-children-changer');

        $(trigger).on('click', function (event) {
            console.log(event);
            ThisPageObject.data.ShowAllChildren = $(trigger).prop('checked');
            ThisPageObject.functions.flushTree();
        });
        $(trigger).prop('checked', true);
        ThisPageObject.data.ShowAllChildren = $(trigger).prop('checked');

    };

}());

var initThis = function () {
    $(function () {
        document.PAGE.videos.onInit();
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
                // document.page2jsPath + '/node_modules/jstree/dist/jstree.js',
                document.page2jsPath + '/node_modules/ztree/js/jquery.ztree.all.js',
                document.page2jsPath + '/node_modules/jquery-modal/jquery.modal.js',
                document.page2jsPath + '/node_modules/xdan-datetimepicker/build/jquery.datetimepicker.full.js',
                document.page2jsPath + '/node_modules/toastr/toastr.js',
            ], 'jsLib');
        } else {
            loadjs([
                document.page2jsPath + '/node_modules/lodash/lodash.min.js',
                document.page2jsPath + '/node_modules/moment/min/moment.min.js',
                // document.page2jsPath + '/node_modules/jstree/dist/jstree.min.js',
                document.page2jsPath + '/node_modules/ztree/js/jquery.ztree.all.min.js',
                document.page2jsPath + '/node_modules/jquery-modal/jquery.modal.min.js',
                document.page2jsPath + '/node_modules/xdan-datetimepicker/build/jquery.datetimepicker.full.min.js',
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
            document.page2jsPath + '/js/ly-lib/ly-helper-tools.js',
            document.page2jsPath + '/js/ly-lib/ly-lib-one-way-bind.js',
            document.page2jsPath + '/js/ly-lib/ly-list-select.js',
            document.page2jsPath + '/js/ly-lib/ly-table-manager.js',
            document.page2jsPath + '/js/ly-lib/ly-pagination-manager.js',
            document.page2jsPath + '/js/ly-lib/ly-left-menu-manger.js',
            document.page2jsPath + '/js/ly-lib/ly-loading-4-modal-submit.js',
            // document.page2jsPath + '/js/template-loader/template-video-tree-menu.js',
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

