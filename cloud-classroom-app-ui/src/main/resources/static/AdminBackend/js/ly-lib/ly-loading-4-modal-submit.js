/**
 * Created by Jeremie on 2017/09/12.
 */

// 模态框提交操作加载动画效果
(function () {

    document.LY = document.LY || {};
    document.LY.LyLoading4ModalSubmit = document.LY.LyLoading4ModalSubmit || {};
    document.LY.LyLoading4ModalSubmit.flagClass = "ly-loading-4-modal-submit";
    var ThisPageObject = document.LY.LyLoading4ModalSubmit;

    ThisPageObject._data = {
        loadingStack: [],
        lastKey: 0,
    };

    ThisPageObject.makeLoadingByRootNode = function (rootNode, nodeFilter) {
        console.log($(rootNode).find(nodeFilter || '.butn.btn-center'))
        return ThisPageObject.makeLoading($(rootNode).find(nodeFilter || '.butn.btn-center'));
    };
    ThisPageObject.makeLoading = function (JQNode) {
        ++ThisPageObject._data.lastKey;
        var manager = {
            JQNode: JQNode,
            key: ThisPageObject._data.lastKey,
        };
        manager.remove = function () {
            _.remove(ThisPageObject._data.loadingStack, function (T) {
                return T.key === manager.key;
            });
        };
        manager.stop = function () {
            var n = $(manager.JQNode).next();
            if (n.is("." + document.LY.LyLoading4ModalSubmit.flagClass)) {
                n.remove();
            }
            $(manager.JQNode).show();
        };
        manager.start = function () {
            $(manager.JQNode).hide();
            $(manager.JQNode).after(
                "<div " +
                "   class='" + document.LY.LyLoading4ModalSubmit.flagClass + "' " +
                "   style='display: block;text-align: center;'" +
                ">" +
                "   <i class='fa fa-spinner fa-spin fa-3x'></i>" +
                "</div>"
            );
        };
        manager.destroy = function () {
            manager.stop();
            manager.remove();
        };

        ThisPageObject._data.loadingStack.push(manager);
        manager.start();
        return manager;
    };

    ThisPageObject.removeAll = function () {
        _.forEach(ThisPageObject._data.loadingStack, function (T) {
            T.stop();
        });
        ThisPageObject._data.loadingStack = [];
    };

}());
