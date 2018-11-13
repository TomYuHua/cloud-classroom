/**
 * Created by Jeremie on 2017/07/16.
 */
'use strict';

// 表格管理器
(function () {

    document.LY = document.LY || {};
    document.LY.LyTableManager = document.LY.LyTableManager || {};
    document.LY.LyTableManager.rootSelectString = 'table.ly-table';
    document.LY.LyTableManager.rowDataKeyName = 'rowData';

    document.LY.LyTableManager.createManager = function (JQNode, fieldList) {
        var manager = {
            rootNode: JQNode,
            fieldList: fieldList || [],
            LyListSelectTableManger: undefined,
            selectedChangeCallback: function (SelectedDataList) {
            },
            data2fieldProcessCallback: function (data, field, row) {
                return data;
            },
            afterReInitCallback: function (rootNode) {
            }
        };

        if (!_.isArray(fieldList)) {
            console.error("ly-list-select-table fieldList not set");
        }

        if (!_.isObject(document.LY.LyListSelectTable)) {
            console.warn("ly-list-select-table not init.");
        } else {
            if ($(manager.rootNode).is(document.LY.LyListSelectTable.rootSelectString)) {
                manager.LyListSelectTableManger =
                    document.LY.LyListSelectTable.createManager($(manager.rootNode));
                manager.LyListSelectTableManger.changeCallBackList.push(function (NodeList) {
                    manager.selectedChangeCallback(_.map(NodeList, function (T) {
                        return $(T).closest('.ly-table-row').data(document.LY.LyTableManager.rowDataKeyName);
                    }));
                });
            }
        }

        manager.tableRowInsertPoint = $(manager.rootNode).find('.ly-template-table-tbody');
        manager.rowTemplate = $($(manager.tableRowInsertPoint).find('.ly-template-table-tbody-row').html());

        manager.rebuild = function (dataList) {
            $(manager.tableRowInsertPoint).empty();
            if (!_.isArray(dataList)) {
                console.error('dataList wrong.');
                return;
            }

            var rowNumber = 0;
            _.forEach(dataList, function (T) {
                var row = $(manager.rowTemplate).clone();

                var defaultString = '';
                _.forEach(manager.fieldList, function (F) {
                    $(row).find('.td-' + F).text(
                        manager.data2fieldProcessCallback(_.get(T, F, defaultString), F, rowNumber)
                    );
                });
                $(row).data(document.LY.LyTableManager.rowDataKeyName, T);
                $(row).addClass('ly-table-row');

                $(manager.tableRowInsertPoint).append(row);
                ++rowNumber;
            });

            // Re Init Select Table
            if (_.isObject(manager.LyListSelectTableManger) &&
                _.isFunction(manager.LyListSelectTableManger.reInit)) {
                manager.LyListSelectTableManger.reInit();
            }

            manager.afterReInitCallback(manager.rootNode);
        };

        manager.getRowDataInRow = function (JQNode) {
            return $(JQNode).closest('.ly-table-row').data(document.LY.LyTableManager.rowDataKeyName);
        };

        manager.getAllRowNode = function () {
            return $(manager.rootNode).find('.ly-table-row');
        };

        manager.getSelectedDataList = function () {
            return _.map($(manager.rootNode).find('.ly-list-select-child:checked').get(), function (T) {
                return manager.getRowDataInRow(T);
            });
        };

        return manager;
    }

}());

