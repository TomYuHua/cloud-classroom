/**
 * Created by Jeremie on 2017/07/17.
 */
'use strict';

// one way bind
(function () {

    document.LY = document.LY || {};
    document.LY.LyOneWayBind = document.LY.LyOneWayBind || {};

    document.LY.LyOneWayBind.createBinder = function (config) {
        var binder = {
            config: {
                rootObjectRef: config.rootObjectRef,
                valuePath: config.valuePath,
                JQNode: config.JQNode,
                translator: config.translator,
                type: config.type || 'text',  // undefined | text | html
            },
        };
        binder.flush = function () {
            var d = _.get(binder.config.rootObjectRef, binder.config.valuePath);
            if (_.isFunction(binder.config.translator)) {
                d = binder.config.translator(d);
            }
            switch (binder.config.type) {
                case 'text':
                    $(binder.config.JQNode).text(d);
                    break;
                case 'html':
                    $(binder.config.JQNode).html(d);
                    break;
                default:
                    console.error("LyOneWayBind flush unknown type of " + binder.config.type);
                    break;
            }
        };

        return binder;
    };

    document.LY.LyOneWayBind.createBinderManager = function () {
        var manager = {
            binderList: [],
        };
        manager.appendBinder = function (binder) {
            manager.binderList.push(binder);
        };
        manager.flush = function () {
            _.forEach(manager.binderList, function (T) {
                T.flush();
            });
        };
        manager.createBinder = function () {
            manager.appendBinder(document.LY.LyOneWayBind.createBinder.apply(null, arguments));
        };
        manager.removeBinder = function (binder) {
            manager.binderList = _.filter(manager.binderList, function (T) {
                return T !== binder;
            });
        };
        manager.removeJQNode = function (JQNode) {
            manager.binderList = _.filter(manager.binderList, function (T) {
                return !$(JQNode).is($(T.config.JQNode));
            });
        };

        return manager;
    }

}());
