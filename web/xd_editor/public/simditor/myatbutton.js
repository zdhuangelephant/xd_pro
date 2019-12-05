(function() {
    var MyAtButton,
        __hasProp = {}.hasOwnProperty,
        __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

    MyAtButton = (function(superClass) {
        __extends(MyAtButton, superClass);

        function MyAtButton() {
            return MyAtButton.__super__.constructor.apply(this, arguments);
        }

        MyAtButton.prototype.name = 'myat';

        MyAtButton.prototype.icon = 'undo';

        MyAtButton.prototype.htmlTag = '艾特';
        MyAtButton.prototype.needFocus  = false;

        MyAtButton.prototype.status = function($node) {
            return true;
        };

        MyAtButton.prototype.command = function() {
            console.log("dianji")

            return $("#at").toggle();
        };

        return MyAtButton;

    })(Simditor.Button);

    Simditor.Toolbar.addButton(MyAtButton);

}).call(this);
