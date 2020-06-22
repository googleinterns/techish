(function (factory) {
    if (typeof module === "object" && typeof module.exports === "object") {
        var v = factory(require, exports);
        if (v !== undefined) module.exports = v;
    }
    else if (typeof define === "function" && define.amd) {
        define(["require", "exports"], factory);
    }
})(function (require, exports) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    var world = '🗺️';
    function hello(word) {
        if (word === void 0) { word = world; }
        return "Hello " + world + "! ";
    }
    exports.hello = hello;
});
//# sourceMappingURL=index.js.map