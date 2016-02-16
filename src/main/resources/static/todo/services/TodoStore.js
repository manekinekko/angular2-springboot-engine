var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_d_1 = require('../../node_modules/angular2/core.d');
// base model for RecordStore
var KeyModel = (function () {
    function KeyModel(key) {
        this.key = key;
    }
    return KeyModel;
})();
exports.KeyModel = KeyModel;
var Todo = (function (_super) {
    __extends(Todo, _super);
    function Todo(key, title, completed) {
        _super.call(this, key);
        this.title = title;
        this.completed = completed;
    }
    return Todo;
})(KeyModel);
exports.Todo = Todo;
var TodoFactory = (function () {
    function TodoFactory() {
        this._uid = 0;
    }
    TodoFactory.prototype.nextUid = function () { return ++this._uid; };
    TodoFactory.prototype.create = function (title, isCompleted) {
        return new Todo(this.nextUid(), title, isCompleted);
    };
    TodoFactory = __decorate([
        core_d_1.Injectable()
    ], TodoFactory);
    return TodoFactory;
})();
exports.TodoFactory = TodoFactory;
// store manages any generic item that inherits from KeyModel
var Store = (function () {
    function Store() {
        this.list = [];
    }
    Store.prototype.add = function (record) { this.list.push(record); };
    Store.prototype.remove = function (record) { this._spliceOut(record); };
    Store.prototype.removeBy = function (callback) {
        var records = this.list.filter(callback);
        for (var i = 0; i < records.length; ++i) {
            var index = this.list.indexOf(records[i]);
            this.list.splice(index, 1);
        }
    };
    Store.prototype._spliceOut = function (record) {
        var i = this._indexFor(record);
        if (i > -1) {
            return this.list.splice(i, 1)[0];
        }
        return null;
    };
    Store.prototype._indexFor = function (record) { return this.list.indexOf(record); };
    Store = __decorate([
        core_d_1.Injectable()
    ], Store);
    return Store;
})();
exports.Store = Store;
//# sourceMappingURL=TodoStore.js.map