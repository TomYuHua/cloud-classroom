/**
 * Created by Jeremie on 2017/07/25.
 */
'use strict';

// video tree menu
(function () {

    document.TEMPLATE = document.TEMPLATE || {};
    document.TEMPLATE.TemplateVideoTreeMenu = document.TEMPLATE.TemplateVideoTreeMenu || {};
    document.TEMPLATE.TemplateVideoTreeMenu.rootSelectString = "div.template-video-tree-menu";
    document.TEMPLATE.TemplateVideoTreeMenu.identifySelectClass = "template-video-tree-menu-identify";
    document.TEMPLATE.TemplateVideoTreeMenu.identifySelectString = "." + document.TEMPLATE.TemplateVideoTreeMenu.identifySelectClass;

    document.TEMPLATE.TemplateVideoTreeMenu.createManager = function (JQNode, templateUrlPath, config) {
        if (!_.isString(templateUrlPath)) {
            console.error('TemplateVideoTreeMenu templateUrlPath invalid.');
            return;
        }
        var manager = {
            rootNode: JQNode,
            templateUrlPath: templateUrlPath,
            config: _.assign({}, config),
            template: {},
            callback: {},
        };
        var isInit = false;
        manager.isInitOk = function () {
            return isInit;
        };
        var initTemplate = function () {
            // TODO
        };

        $(manager.rootNode).load(templateUrlPath, function (response, status, xhr) {
            if (status === 'error') {
                console.error('TemplateVideoTreeMenu load html failed.');
                return;
            }
            initTemplate();
            isInit = true;
        });

        return manager;
    }

}());
