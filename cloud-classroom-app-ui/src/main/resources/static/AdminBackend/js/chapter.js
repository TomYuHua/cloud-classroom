/**
 * Created by Jeremie on 2017/07/16.
 */
'use strict';

// PAGE chapter class
(function () {

    document.PAGE = document.PAGE || {};
    document.PAGE.chapter = {
        // 私有初始化函数
        _initFunctions: {},
        // 私有内部变量
        _values: {},
    };
    var ThisPageObject = document.PAGE.chapter;
    ThisPageObject._values = {
        leftMenuManager: null,
    };

    // 初始化函数
    ThisPageObject.onInit = function () {

        ThisPageObject._initFunctions.leftMenuManager();

    };

    var nowMenu = 'chapter';
    ThisPageObject._initFunctions.leftMenuManager = function () {
        var menuMap = [
            ['users', 'users.html'],
            ['role', 'role.html'],
            ['videos', 'videos.html'],
            ['chapter', 'chapter.html'],
            ['permissions', 'permissions.html'],
            ['information', 'information.html'],
        ];
        if (!_.isObject(document.LY.LyLeftMenuManager)) {
            console.error("ly-left-menu-manager not init.");
            return;
        } else {
            ThisPageObject._values.leftMenuManager =
                document.LY.LyLeftMenuManager.createManager(
                    $(document.LY.LyLeftMenuManager.rootSelectString),
                    './template/template-left-menu.html',
                    {
                        nowMenu: nowMenu,
                        menuMap: menuMap,
                    }
                );
        }
    };

}());

$(function () {
    document.PAGE.chapter.onInit();
});

