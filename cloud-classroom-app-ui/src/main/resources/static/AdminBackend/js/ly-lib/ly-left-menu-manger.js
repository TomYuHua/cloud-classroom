/**
 * Created by Jeremie on 2017/07/16.
 */
'use strict';

// 翻页器管理器
(function () {

    document.LY = document.LY || {};
    document.LY.LyLeftMenuManager = document.LY.LyLeftMenuManager || {};
    document.LY.LyLeftMenuManager.rootSelectString = "div.ly-left-menu";
    document.LY.LyLeftMenuManager.identifySelectClass = "ly-left-menu-identify";
    document.LY.LyLeftMenuManager.identifySelectString = "." + document.LY.LyLeftMenuManager.identifySelectClass;
    var templateUrlPathDef = document.GLOBAL_CONFIG.TEMPLATE_PATH + './template-left-menu.html';
    var menuMap = [
        ['users', 'users.html'],
        ['role', 'role.html'],
        ['videos', 'videos.html'],
        // ['chapter', 'chapter.html'],
        // ['permissions', 'permissions.html'],
        // ['information', 'information.html'],
    ];
    if (!document.isJsDebug) {
        menuMap = [
            ['users', '/backuser/users'],
            ['role', '/Role/role'],
            ['videos', '/Resources/videos'],
        ];
    }
    document.LY.LyLeftMenuManager.createManagerEasy = function (nowMenu, templateUrlPath) {
        return document.LY.LyLeftMenuManager.createManager(
            $(document.LY.LyLeftMenuManager.rootSelectString),
            templateUrlPath || templateUrlPathDef,
            {
                nowMenu: nowMenu,
                menuMap: menuMap,
            }
        );
    };

    document.LY.LyLeftMenuManager.createManager = function (JQNode, templateUrlPath, config) {
        if (!_.isString(templateUrlPath)) {
            console.error('LyLeftMenuManager templateUrlPath invalid.');
            return;
        }
        var manager = {
            rootNode: JQNode,
            templateUrlPath: templateUrlPath,
            config: _.assign({
                menuMap: [
                    ['example', './example.html'],
                ],
                nowMenu: 'example',
            }, config),
            template: {},
            callback: {},
        };
        var isInit = false;
        manager.isInitOk = function () {
            return isInit;
        };
        var initTemplate = function () {
            _.forEach(manager.config.menuMap, function (T) {
                if (T[0] !== manager.config.nowMenu) {
                    $(manager.rootNode).find('.ly-left-menu-href-' + T[0]).on('click', function () {
                        window.location.href = T[1];
                    });
                } else {
                    $(manager.rootNode).find('.ly-left-menu-href-' + T[0]).addClass('menu-current');
                }
            });
        };

        $(manager.rootNode).load(templateUrlPath, function (response, status, xhr) {
            if (status === 'error') {
                console.error('LyLeftMenuManager load html failed.');
                return;
            }
            initTemplate();
            isInit = true;
        });
        return manager;
    };

}());
