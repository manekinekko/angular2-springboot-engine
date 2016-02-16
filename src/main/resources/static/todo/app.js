var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_d_1 = require('../node_modules/angular2/core.d');
var router_d_1 = require('../node_modules/angular2/router.d');
var TodoStore_1 = require('./services/TodoStore');
var TodoApp = (function () {
    function TodoApp(todoStore, factory) {
        this.todoStore = todoStore;
        this.factory = factory;
        this.todoEdit = null;
        this.selected = 0;
    }
    TodoApp.prototype.ngOnInit = function () {
        this.addTodo('Universal JavaScript');
        this.addTodo('Run Angular 2 in Web Workers');
        this.addTodo('Upgrade the web');
        this.addTodo('Release Angular 2');
    };
    TodoApp.prototype.enterTodo = function ($event, inputElement) {
        if (!inputElement.value) {
            return;
        }
        if ($event.which !== 13) {
            return;
        }
        this.addTodo(inputElement.value);
        inputElement.value = '';
    };
    TodoApp.prototype.editTodo = function (todo) {
        this.todoEdit = todo;
    };
    TodoApp.prototype.doneEditing = function ($event, todo) {
        var which = $event.which;
        var target = $event.target;
        if (which === 13) {
            todo.title = target.value;
            this.todoEdit = null;
        }
        else if (which === 27) {
            this.todoEdit = null;
            target.value = todo.title;
        }
    };
    TodoApp.prototype.addTodo = function (newTitle) {
        this.todoStore.add(this.factory.create(newTitle, false));
    };
    TodoApp.prototype.completeMe = function (todo) {
        todo.completed = !todo.completed;
    };
    TodoApp.prototype.deleteMe = function (todo) {
        this.todoStore.remove(todo);
    };
    TodoApp.prototype.toggleAll = function ($event) {
        var isComplete = $event.target.checked;
        this.todoStore.list.forEach(function (todo) { return todo.completed = isComplete; });
    };
    TodoApp.prototype.clearCompleted = function () {
        this.todoStore.removeBy(function (todo) { return todo.completed; });
    };
    TodoApp.prototype.pluralize = function (count, word) {
        return "word" + (count === 1 ? '' : 's');
    };
    TodoApp.prototype.remainingCount = function () {
        return this.todoStore.list.filter(function (todo) { return !todo.completed; }).length;
    };
    TodoApp = __decorate([
        core_d_1.Component({
            selector: 'app',
            providers: [TodoStore_1.Store, TodoStore_1.TodoFactory],
            encapsulation: core_d_1.ViewEncapsulation.None,
            directives: [router_d_1.ROUTER_DIRECTIVES],
            styles: [],
            template: "\n<section id=\"todoapp\">\n\n  <header id=\"header\">\n    <h1>todos</h1>\n      <input\n        type=\"text\"\n        id=\"new-todo\"\n        placeholder=\"What needs to be done?\"\n        autofocus\n        #newtodo\n        (keyup)=\"enterTodo($event, newtodo)\">\n  </header>\n\n  <section id=\"main\">\n    <input\n      id=\"toggle-all\"\n      type=\"checkbox\"\n      (click)=\"toggleAll($event)\"\n      [class.hidden]=\"todoStore.list.length == 0\">\n    <label for=\"toggle-all\">Mark all as complete</label>\n\n    <ul id=\"todo-list\">\n\n      <li\n        *ngFor=\"var todo of todoStore.list\"\n        [class.editing]=\"todoEdit == todo\"\n        [class.completed]=\"todo.completed == true\">\n\n        <div class=\"view\"\n            [class.hidden]=\"todoEdit == todo\">\n\n          <input class=\"toggle\"\n                 type=\"checkbox\"\n                 (click)=\"completeMe(todo)\"\n                 [checked]=\"todo.completed\">\n\n          <label (dblclick)=\"editTodo(todo)\">{{ todo.title }}</label>\n          <button class=\"destroy\" (click)=\"deleteMe(todo)\"></button>\n\n        </div>\n\n        <div *ngIf=\"todoEdit == todo\">\n\n          <input class=\"edit\"\n            [class.visible]=\"todoEdit == todo\"\n            [value]=\"todo.title\"\n            (keyup)=\"doneEditing($event, todo)\"\n            autofocus>\n\n        </div>\n\n      </li>\n    </ul>\n  </section>\n\n  <footer id=\"footer\" *ngIf=\"todoStore.list.length\">\n    <span id=\"todo-count\">\n      <strong>{{ remainingCount() }}</strong>\n      {{ pluralize(remainingCount(), 'item') }} left\n    </span>\n    <ul id=\"filters\">\n      <li>\n        <a href=\"/#/\"\n          [class.selected]=\"selected === 0\"\n          (click)=\"selected = 0\">\n          All\n        </a>\n      </li>\n      <li>\n        <a href=\"/#/active\"\n          [class.selected]=\"selected === 1\"\n          (click)=\"selected = 1\">\n          Active\n        </a>\n      </li>\n      <li>\n        <a href=\"/#/completed\"\n          [class.selected]=\"selected === 2\"\n          (click)=\"selected = 2\">\n          Completed\n        </a>\n      </li>\n    </ul>\n    <button id=\"clear-completed\" (click)=\"clearCompleted()\">Clear completed</button>\n  </footer>\n\n</section>\n  "
        })
    ], TodoApp);
    return TodoApp;
})();
exports.TodoApp = TodoApp;
//# sourceMappingURL=app.js.map