/**
 * Created by Jeremie on 2017/07/15.
 */
'use strict';

// 翻页器管理器
(function () {

    document.LY = document.LY || {};
    document.LY.LyPaginationManager = document.LY.LyPaginationManager || {};
    document.LY.LyPaginationManager.rootSelectString = "div.ly-pagination";
    document.LY.LyPaginationManager.identifySelectClass = "ly-pagination-identify";
    document.LY.LyPaginationManager.identifySelectString = "." + document.LY.LyPaginationManager.identifySelectClass;

    var templateUrlPathDef = document.GLOBAL_CONFIG.TEMPLATE_PATH + './ly-pagination-manager.html';
    document.LY.LyPaginationManager.createManagerEasy = function (JQNode, templateUrlPath, config) {
        if (!_.isString(templateUrlPath)) {
            templateUrlPath = templateUrlPathDef;
        }
        return document.LY.LyPaginationManager.createManager(JQNode, templateUrlPath, config);
    };

    document.LY.LyPaginationManager.createManager = function (JQNode, templateUrlPath, config) {
        if (!_.isString(templateUrlPath)) {
            console.error('LyPaginationManager templateUrlPath invalid.');
            return;
        }
        var manager = {
            rootNode: JQNode,
            templateUrlPath: templateUrlPath,
            config: _.assign({
                totalPageNumber: 100,
                maxShowPageSize: 5,
                nowPageNumber: 1,
            }, config),
            template: {},
            callback: {
                click: function (index) {
                    console.log(index);
                },
                go: function (index) {
                    // first|prev|next|last
                    console.log(index);
                },
                nextGo: function (index) {
                    console.log(index);
                },
            }
        };
        var fOnClick = function (index) {
            _.isFunction(manager.callback.click) && manager.callback.click(index);
            fNextGo(index);
        };
        var fOnGo = function (index) {
            _.isFunction(manager.callback.go) && manager.callback.go(index);
            fNextGo(index);
        };
        var fNextGo = function (index) {
            // first|prev|next|last| number
            if (_.isNumber(index)) {
                manager.config.nowPageNumber = index;
            } else {
                switch (index) {
                    case 'next':
                        manager.config.nowPageNumber += 1;
                        break;
                    case 'prev':
                        manager.config.nowPageNumber -= 1;
                        break;
                    case 'first':
                        manager.config.nowPageNumber = 1;
                        break;
                    case 'last':
                        manager.config.nowPageNumber = manager.config.totalPageNumber;
                        break;
                }
            }
            if (manager.config.nowPageNumber < 1) {
                manager.config.nowPageNumber = 1;
            }
            if (manager.config.nowPageNumber > manager.config.totalPageNumber) {
                manager.config.nowPageNumber = manager.config.totalPageNumber;
            }
            _.isFunction(manager.callback.nextGo) && manager.callback.nextGo(manager.config.nowPageNumber);
        };
        var isInit = false;
        var initTemplate = function () {
            var template = $($(JQNode).find('.ly-pagination-template').html());
            manager.template.container = $(template.find('.ly-pagination-template-container').html());
            manager.template.things = {
                first: {
                    active: $($(template).find('.ly-pagination-template-things-first-active').html()),
                    inactive: $($(template).find('.ly-pagination-template-things-first-inactive').html()),
                },
                last: {
                    active: $($(template).find('.ly-pagination-template-things-last-active').html()),
                    inactive: $($(template).find('.ly-pagination-template-things-last-inactive').html()),
                },
                page: {
                    prev: {
                        disabled: $($(template).find('.ly-pagination-template-things-page-prev-disabled').html()),
                        enabled: $($(template).find('.ly-pagination-template-things-page-prev-enabled').html()),
                    },
                    next: {
                        disabled: $($(template).find('.ly-pagination-template-things-page-next-disabled').html()),
                        enabled: $($(template).find('.ly-pagination-template-things-page-next-enabled').html()),
                    },
                    number: {
                        active: $($(template).find('.ly-pagination-template-things-page-number-active').html()),
                        enabled: $($(template).find('.ly-pagination-template-things-page-number-enabled').html()),
                    }
                }
            };
            // console.log(manager.template)
        };
        manager.flush = function (newConfig) {
            // remove old
            var last = $(JQNode).prev();
            if (last.hasClass(document.LY.LyPaginationManager.identifySelectClass)) {
                last.remove();
            }

            manager.config = _.assign(manager.config, newConfig);
            console.log(manager.config);
            if (!isInit) {
                console.warn('LyPaginationManager temporary not init compile, but config set ok and will work when init end.');
            }

            // pre append new
            var NE = manager.template.container.clone();
            $(NE).addClass(document.LY.LyPaginationManager.identifySelectClass);
            var insertPoint = $(NE).find('.ly-pagination-template-insert-point');
            var nodeList = [];

            var firstShow = manager.config.nowPageNumber - Math.floor(manager.config.maxShowPageSize / 2);
            firstShow = firstShow < 1 ? 1 : firstShow;
            var lastShow = firstShow + manager.config.maxShowPageSize - 1;
            lastShow = lastShow > manager.config.totalPageNumber ? manager.config.totalPageNumber : lastShow;

            if (manager.config.nowPageNumber === 1) {
                nodeList.push(manager.template.things.first.inactive.clone());
            } else {
                nodeList.push(manager.template.things.first.active.clone());
                //
                $(_.last(nodeList)).find('a').on('click', fOnGo.bind(undefined, 'first'));
            }
            if (manager.config.nowPageNumber > 1) {
                nodeList.push(manager.template.things.page.prev.enabled.clone());
                //
                $(_.last(nodeList)).find('a').on('click', fOnGo.bind(undefined, 'prev'));
            } else {
                nodeList.push(manager.template.things.page.prev.disabled.clone());
            }
            for (var i = firstShow; i <= lastShow; ++i) {
                if (manager.config.nowPageNumber !== i) {
                    nodeList.push(manager.template.things.page.number.enabled.clone());
                    var a = $(_.last(nodeList)).find('a');
                    a.text(i);
                    a.on('click', fOnClick.bind(undefined, i));
                } else {
                    nodeList.push(manager.template.things.page.number.active.clone());
                    $(_.last(nodeList)).text(i);
                }
            }
            if (manager.config.nowPageNumber < manager.config.totalPageNumber) {
                nodeList.push(manager.template.things.page.next.enabled.clone());
                //
                $(_.last(nodeList)).find('a').on('click', fOnGo.bind(undefined, 'next'));
            } else {
                nodeList.push(manager.template.things.page.next.disabled.clone());
            }
            if (manager.config.nowPageNumber < manager.config.totalPageNumber) {
                nodeList.push(manager.template.things.last.active.clone());
                //
                $(_.last(nodeList)).find('a').on('click', fOnGo.bind(undefined, 'last'));
            } else {
                nodeList.push(manager.template.things.last.inactive.clone());
            }

            _.map(nodeList, function (T) {
                insertPoint.append(T);
            });
            $(JQNode).before(NE);
        };

        manager.getConfig = function () {
            return _.clone(manager.config);
        };

        $(manager.rootNode).load(templateUrlPath, function (response, status, xhr) {
            if (status === 'error') {
                console.error('LyPaginationManager load html failed.');
                return;
            }
            initTemplate();
            isInit = true;
            manager.flush();
        });
        return manager;
    }

}());
