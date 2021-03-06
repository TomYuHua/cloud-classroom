/**
 * Created by Jeremie on 2017/07/15.
 */
'use strict';

function templatePolyfill() {
    if ('content' in document.createElement('template')) {
        return false;
    }

    var templates = document.getElementsByTagName('template');
    var plateLen = templates.length;

    for (var x = 0; x < plateLen; ++x) {
        var template = templates[x];
        var content = template.childNodes;
        var fragment = document.createDocumentFragment();

        while (content[0]) {
            fragment.appendChild(content[0]);
        }

        template.content = fragment;
    }
}

templatePolyfill();
