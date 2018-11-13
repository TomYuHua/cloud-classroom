/**
 * Created by Jeremie on 2017/08/21.
 */

// GLOBAL_CONFIG
// @warning !!! must polyfill core-js/client/core.min.js before this
(function () {

    document.GLOBAL_CONFIG = document.GLOBAL_CONFIG || {};
    var ThisPageObject = document.GLOBAL_CONFIG;

    ThisPageObject.TEMPLATE_PATH = document.templatePath;


    ThisPageObject.HTTP = ThisPageObject.HTTP || {};

    ThisPageObject.HTTP.hostTable = new Map([
        ["test1", {
            protocol: "http",
            host: "127.0.0.1",
            port: "888",
        }],
        ["test2", {
            protocol: "http",
            host: "127.0.0.1",
            port: "888",
        }],
        // ["test1", {
        //     protocol: "http",
        //     host: "192.168.1.109",
        //     port: "888",
        // }],
        // ["test2", {
        //     protocol: "http",
        //     host: "192.168.1.109",
        //     port: "888",
        // }],
        // ["test1", {
        //     protocol: "http",
        //     host: "192.168.1.101",
        //     port: "888",
        // }],
        // ["test2", {
        //     protocol: "http",
        //     host: "192.168.1.101",
        //     port: "888",
        // }],
        // ["test1", {
        //     protocol: "http",
        //     host: "192.168.1.195",
        //     port: "888",
        // }],
        // ["test2", {
        //     protocol: "http",
        //     host: "192.168.1.195",
        //     port: "888",
        // }],
    ]);
    ThisPageObject.HTTP.getRootUrl = function (hostName) {
        var o = ThisPageObject.HTTP.hostTable.get(hostName);
        if (o)
            return o.protocol + "://" + o.host + ":" + o.port;
        return "";
    };

    ThisPageObject.HTTP.pathTable = new Map([
        ["Users_GetUserList", {
            hostName: "test1",
            path: "/backuser/getuserpage",
        }],
        ["Users_DeleteUser", {
            hostName: "test1",
            path: "/backuser/deleteuser",
        }],
        ["Users_ChangeUserTeacher", {
            hostName: "test1",
            path: "/userui/uploadTeacher",
        }],
        ["Users_ChangeUserStudent", {
            hostName: "test1",
            path: "/userui/uploadStudent",
        }],
        ["Users_ChangeUserType2Teacher", {
            hostName: "test1",
            path: "/binduser/bindType",
        }],
        ["Users_ChangeUserType2Student", {
            hostName: "test1",
            path: "/binduser/bindType",
        }],
        ["Users_AddUser", {
            hostName: "test1",
            path: "/backuser/addUser",
        }],
        ["Users_TestUserNameBeforeAddUser", {
            hostName: "test1",
            path: "/backuser/selectTypes",
        }],
        ["Users_ResetPasswd", {
            hostName: "test1",
            path: "/backuser/resetPassword",
        }],
        ["Users_GetUserInfoForReview", {
            hostName: "test1",
            path: "/backuser/auditInfor",
        }],
        ["Users_GetUserInfoForChange", {
            hostName: "test1",
            path: "/backuser/auditInfor",
        }],
        ["Users_SubmitReview", {
            hostName: "test1",
            path: "/backuser/audituser",
        }],
        ["Users_ChangeRole", {
            hostName: "test1",
            path: "/backuser/changeUserRole",
        }],
        ["Role_ListAll", {
            hostName: "test2",
            path: "/Role/selectAll",
        }],
        ["Role_Delete", {
            hostName: "test2",
            path: "/Role/delete",
        }],
        ["Role_Change", {
            hostName: "test2",
            path: "/Role/change",
        }],
        ["Role_Add", {
            hostName: "test2",
            path: "/Role/insert",
        }],
        ["Role_GetRolePermission", {
            hostName: "test2",
            path: "/privilege/selectByRoleId",
        }],
        ["Role_UpdateRolePermission", {
            hostName: "test2",
            path: "/privilege/change",
        }],
        ["Permission_GetPermissionTreeStruct", {
            hostName: "test2",
            path: "/Menu/selectAll",
        }],
        ["Videos_GetAll", {
            hostName: "test2",
            path: "/Resources/selectAll",
        }],
        ["Videos_Review", {
            hostName: "test2",
            path: "/Resources/check",
        }],
        ["Videos_Change", {
            hostName: "test2",
            path: "/Resources/change",
        }],
        ["Videos_ChangeNodeName", {
            hostName: "test2",
            path: "/Resources/changefile",
        }],
        ["Videos_Delete", {
            hostName: "test2",
            path: "/Resources/delete",
        }],
        ["Videos_AddNode", {
            hostName: "test2",
            path: "/Resources/insertfile",
        }],
        ["Videos_GetResourcesPathHeader", {
            hostName: "test2",
            path: "/Resources/resourceRight",
        }],
        ["Videos_UploadFile", {
            hostName: "test2",
            path: "/Resources/uploadbigfiles",
        }],
        ["Videos_GetTextContent", {
            hostName: "test2",
            path: "/Resources/getcontent",   // TODO
        }],
    ]);
    ThisPageObject.HTTP.getUrl = function (pathName) {
        var o = ThisPageObject.HTTP.pathTable.get(pathName);
        if (document.isJsDebug) {
            if (o)
                return ThisPageObject.HTTP.getRootUrl(o.hostName) + o.path;
        } else {
            if (o)
                return window.location.protocol
                    + "//" + window.location.hostname
                    + ":" + window.location.port + o.path;
            // window.location.protocol
            // window.location.hostname
            // window.location.port
        }
        return "";
    };
}());