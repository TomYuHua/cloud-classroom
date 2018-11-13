/**
 * Created by Jeremie on 2017/07/15.
 */
'use strict';

// table多选器
(function () {

    document.LY = document.LY || {};
    document.LY.LyListSelectTable = document.LY.LyListSelectTable || {};
    document.LY.LyListSelectTable.rootSelectString = 'table.ly-list-select-table';

    document.LY.LyListSelectTable.createManager = function (JQNode) {
        var manager = {
            rootNode: JQNode,
            changeCallBackList: []
        };
        var changeCallBack = function (JQNode) {
            var NodeList = [];
            $(JQNode).each(function () {
                if ($(this).is(':checked')) {
                    NodeList.push($(this));
                }
            });
            _.forEach(manager.changeCallBackList, function (T) {
                T(NodeList);
            });
        };
        manager.noticeCallback = function () {
            changeCallBack($(manager.nodeChild));
        };
        // 注册/重注册函数
        manager.reInit = function () {
            manager.nodeParent = $(manager.rootNode).find('input.ly-list-select-parent');
            manager.nodeChild = $(manager.rootNode).find('input.ly-list-select-child');

            // 恢复选择框到初始状态
            $(manager.nodeParent).off('click');
            $(manager.nodeChild).off('click');
            $(manager.nodeParent).prop('checked', false);
            $(manager.nodeChild).prop('checked', false);

            // 全选框
            $(manager.nodeParent).click(function () {
                var isChecked = $(this).is(':checked');
                $(manager.nodeChild).each(function () {
                    $(this).prop('checked', isChecked);
                });
                manager.noticeCallback();
            });
            // 子选择框
            $(manager.nodeChild).click(function () {
                var isAllSelected = _.every($(manager.nodeChild), function (e) {
                    return $(e).is(':checked');
                });
                $(manager.nodeParent).prop('checked', isAllSelected);
                manager.noticeCallback();
            });
        };

        manager.reInit();
        return manager;
    };

}());
