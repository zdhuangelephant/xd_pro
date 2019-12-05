(function() {
    var TableButton,
        __hasProp = {}.hasOwnProperty,
        __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

    TableButton = (function(_super) {
        __extends(TableButton, _super);

        function TableButton() {
            return TableButton.__super__.constructor.apply(this, arguments);
        }

        TableButton.prototype.name = 'table';

        TableButton.prototype.icon = 'table';

        TableButton.prototype.htmlTag = 'table';

        TableButton.prototype.disableTag = 'pre, li, blockquote';

        TableButton.prototype.menu = true;

        TableButton.prototype._init = function() {
            TableButton.__super__._init.call(this);
            $.merge(this.editor.formatter._allowedTags, ['tbody', 'tr', 'td', 'colgroup', 'col']);
            $.extend(this.editor.formatter._allowedAttributes, {
                td: ['rowspan', 'colspan'],
                col: ['width']
            });
            this._initShortcuts();
            this.editor.on('decorate', (function(_this) {
                return function(e, $el) {
                    return $el.find('table').each(function(i, table) {
                        return _this.decorate($(table));
                    });
                };
            })(this));
            this.editor.on('undecorate', (function(_this) {
                return function(e, $el) {
                    return $el.find('table').each(function(i, table) {
                        return _this.undecorate($(table));
                    });
                };
            })(this));
            this.editor.on('selectionchanged.table', (function(_this) {
                return function(e) {
                    var $container, range;
                    _this.editor.body.find('.simditor-table td').removeClass('active');
                    range = _this.editor.selection.getRange();
                    if (range == null) {
                        return;
                    }
                    $container = $(range.commonAncestorContainer);
                    if (range.collapsed && $container.is('.simditor-table')) {
                        if (_this.editor.selection.rangeAtStartOf($container)) {
                            $container = $container.find('td:first');
                        } else {
                            $container = $container.find('td:last');
                        }
                        _this.editor.selection.setRangeAtEndOf($container);
                    }
                    return $container.closest('td', _this.editor.body).addClass('active');
                };
            })(this));
            this.editor.on('blur.table', (function(_this) {
                return function(e) {
                    return _this.editor.body.find('.simditor-table td').removeClass('active');
                };
            })(this));
            this.editor.inputManager.addKeystrokeHandler('38', 'td', (function(_this) {
                return function(e, $node) {
                    var $prevTr, $tr, index;
                    $tr = $node.parent('tr');
                    $prevTr = $tr.prev('tr');
                    if (!($prevTr.length > 0)) {
                        return true;
                    }
                    index = $tr.find('td').index($node);
                    _this.editor.selection.setRangeAtEndOf($prevTr.find('td').eq(index));
                    return true;
                };
            })(this));
            return this.editor.inputManager.addKeystrokeHandler('40', 'td', (function(_this) {
                return function(e, $node) {
                    var $nextTr, $tr, index;
                    $tr = $node.parent('tr');
                    $nextTr = $tr.next('tr');
                    if (!($nextTr.length > 0)) {
                        return true;
                    }
                    index = $tr.find('td').index($node);
                    _this.editor.selection.setRangeAtEndOf($nextTr.find('td').eq(index));
                    return true;
                };
            })(this));
        };

        TableButton.prototype.initResize = function($table) {
            var $colgroup, $resizeHandle, $wrapper;
            $wrapper = $table.parent('.simditor-table');
            $colgroup = $table.find('colgroup');
            if ($colgroup.length < 1) {
                $colgroup = $('<colgroup/>').prependTo($table);
                $table.find('tr:first td').each((function(_this) {
                    return function(i, td) {
                        var $col;
                        return $col = $('<col/>').appendTo($colgroup);
                    };
                })(this));
                this.refreshTableWidth($table);
            }
            $resizeHandle = $('<div class="simditor-resize-handle" contenteditable="false"></div>').appendTo($wrapper);
            $wrapper.on('mousemove', 'td', (function(_this) {
                return function(e) {
                    var $col, $td, index, x, _ref, _ref1;
                    if ($wrapper.hasClass('resizing')) {
                        return;
                    }
                    $td = $(e.currentTarget);
                    x = e.pageX - $(e.currentTarget).offset().left;
                    if (x < 5 && $td.prev().length > 0) {
                        $td = $td.prev();
                    }
                    if ($td.next('td').length < 1) {
                        $resizeHandle.hide();
                        return;
                    }
                    if ((_ref = $resizeHandle.data('td')) != null ? _ref.is($td) : void 0) {
                        $resizeHandle.show();
                        return;
                    }
                    index = $td.parent().find('td').index($td);
                    $col = $colgroup.find('col').eq(index);
                    if ((_ref1 = $resizeHandle.data('col')) != null ? _ref1.is($col) : void 0) {
                        $resizeHandle.show();
                        return;
                    }
                    return $resizeHandle.css('left', $td.position().left + $td.outerWidth() - 5).data('td', $td).data('col', $col).show();
                };
            })(this));
            $wrapper.on('mouseleave', (function(_this) {
                return function(e) {
                    return $resizeHandle.hide();
                };
            })(this));
            return $wrapper.on('mousedown', '.simditor-resize-handle', (function(_this) {
                return function(e) {
                    var $handle, $leftCol, $leftTd, $rightCol, $rightTd, minWidth, startHandleLeft, startLeftWidth, startRightWidth, startX, tableWidth;
                    $handle = $(e.currentTarget);
                    $leftTd = $handle.data('td');
                    $leftCol = $handle.data('col');
                    $rightTd = $leftTd.next('td');
                    $rightCol = $leftCol.next('col');
                    startX = e.pageX;
                    startLeftWidth = $leftTd.outerWidth() * 1;
                    startRightWidth = $rightTd.outerWidth() * 1;
                    startHandleLeft = parseFloat($handle.css('left'));
                    tableWidth = $leftTd.closest('table').width();
                    minWidth = 50;
                    $(document).on('mousemove.simditor-resize-table', function(e) {
                        var deltaX, leftWidth, rightWidth;
                        deltaX = e.pageX - startX;
                        leftWidth = startLeftWidth + deltaX;
                        rightWidth = startRightWidth - deltaX;
                        if (leftWidth < minWidth) {
                            leftWidth = minWidth;
                            deltaX = minWidth - startLeftWidth;
                            rightWidth = startRightWidth - deltaX;
                        } else if (rightWidth < minWidth) {
                            rightWidth = minWidth;
                            deltaX = startRightWidth - minWidth;
                            leftWidth = startLeftWidth + deltaX;
                        }
                        $leftCol.attr('width', (leftWidth / tableWidth * 100) + '%');
                        $rightCol.attr('width', (rightWidth / tableWidth * 100) + '%');
                        return $handle.css('left', startHandleLeft + deltaX);
                    });
                    $(document).one('mouseup.simditor-resize-table', function(e) {
                        $(document).off('.simditor-resize-table');
                        return $wrapper.removeClass('resizing');
                    });
                    $wrapper.addClass('resizing');
                    return false;
                };
            })(this));
        };

        TableButton.prototype._initShortcuts = function() {
            this.editor.inputManager.addShortcut('ctrl+alt+up', (function(_this) {
                return function(e) {
                    _this.editMenu.find('.menu-item[data-param=insertRowAbove]').click();
                    return false;
                };
            })(this));
            this.editor.inputManager.addShortcut('ctrl+alt+down', (function(_this) {
                return function(e) {
                    _this.editMenu.find('.menu-item[data-param=insertRowBelow]').click();
                    return false;
                };
            })(this));
            this.editor.inputManager.addShortcut('ctrl+alt+left', (function(_this) {
                return function(e) {
                    _this.editMenu.find('.menu-item[data-param=insertColLeft]').click();
                    return false;
                };
            })(this));
            return this.editor.inputManager.addShortcut('ctrl+alt+right', (function(_this) {
                return function(e) {
                    _this.editMenu.find('.menu-item[data-param=insertColRight]').click();
                    return false;
                };
            })(this));
        };

        TableButton.prototype.decorate = function($table) {
            if ($table.parent('.simditor-table').length > 0) {
                this.undecorate($table);
            }
            $table.wrap('<div class="simditor-table"></div>');
            this.initResize($table);
            return $table.parent();
        };

        TableButton.prototype.undecorate = function($table) {
            if (!($table.parent('.simditor-table').length > 0)) {
                return;
            }
            return $table.parent().replaceWith($table);
        };

        TableButton.prototype.renderMenu = function() {
            $("<div class=\"menu-create-table\">\n</div>\n<div class=\"menu-edit-table\">\n  <ul>\n    <li><a tabindex=\"-1\" unselectable=\"on\" class=\"menu-item\" href=\"javascript:;\" data-param=\"deleteRow\"><span>" + (this._t('deleteRow')) + "</span></a></li>\n    <li><a tabindex=\"-1\" unselectable=\"on\" class=\"menu-item\" href=\"javascript:;\" data-param=\"insertRowAbove\"><span>" + (this._t('insertRowAbove')) + " ( Ctrl + Alt + ↑ )</span></a></li>\n    <li><a tabindex=\"-1\" unselectable=\"on\" class=\"menu-item\" href=\"javascript:;\" data-param=\"insertRowBelow\"><span>" + (this._t('insertRowBelow')) + " ( Ctrl + Alt + ↓ )</span></a></li>\n    <li><span class=\"separator\"></span></li>\n    <li><a tabindex=\"-1\" unselectable=\"on\" class=\"menu-item\" href=\"javascript:;\" data-param=\"deleteCol\"><span>" + (this._t('deleteColumn')) + "</span></a></li>\n    <li><a tabindex=\"-1\" unselectable=\"on\" class=\"menu-item\" href=\"javascript:;\" data-param=\"insertColLeft\"><span>" + (this._t('insertColumnLeft')) + " ( Ctrl + Alt + ← )</span></a></li>\n    <li><a tabindex=\"-1\" unselectable=\"on\" class=\"menu-item\" href=\"javascript:;\" data-param=\"insertColRight\"><span>" + (this._t('insertColumnRight')) + " ( Ctrl + Alt + → )</span></a></li>\n    <li><span class=\"separator\"></span></li>\n    <li><a tabindex=\"-1\" unselectable=\"on\" class=\"menu-item\" href=\"javascript:;\" data-param=\"deleteTable\"><span>" + (this._t('deleteTable')) + "</span></a></li>\n  </ul>\n</div>").appendTo(this.menuWrapper);
            this.createMenu = this.menuWrapper.find('.menu-create-table');
            this.editMenu = this.menuWrapper.find('.menu-edit-table');
            this.createTable(6, 6).appendTo(this.createMenu);
            this.createMenu.on('mouseenter', 'td', (function(_this) {
                return function(e) {
                    var $td, $tr, num;
                    _this.createMenu.find('td').removeClass('selected');
                    $td = $(e.currentTarget);
                    $tr = $td.parent();
                    num = $tr.find('td').index($td) + 1;
                    return $tr.prevAll('tr').addBack().find('td:lt(' + num + ')').addClass('selected');
                };
            })(this));
            this.createMenu.on('mouseleave', (function(_this) {
                return function(e) {
                    return $(e.currentTarget).find('td').removeClass('selected');
                };
            })(this));
            return this.createMenu.on('mousedown', 'td', (function(_this) {
                return function(e) {
                    var $closestBlock, $table, $td, $tr, colNum, rowNum;
                    _this.wrapper.removeClass('menu-on');
                    if (!_this.editor.inputManager.focused) {
                        return;
                    }
                    $td = $(e.currentTarget);
                    $tr = $td.parent();
                    colNum = $tr.find('td').index($td) + 1;
                    rowNum = $tr.prevAll('tr').length + 1;
                    $table = _this.createTable(rowNum, colNum, true);
                    $closestBlock = _this.editor.util.closestBlockEl();
                    if (_this.editor.util.isEmptyNode($closestBlock)) {
                        $closestBlock.replaceWith($table);
                    } else {
                        $closestBlock.after($table);
                    }
                    _this.decorate($table);
                    _this.editor.selection.setRangeAtStartOf($table.find('td:first'));
                    _this.editor.trigger('valuechanged');
                    return false;
                };
            })(this));
        };

        TableButton.prototype.createTable = function(row, col, phBr) {
            var $table, $tbody, $td, $tr, c, r, _i, _j;
            $table = $('<table/>');
            $tbody = $('<tbody/>').appendTo($table);
            for (r = _i = 0; 0 <= row ? _i < row : _i > row; r = 0 <= row ? ++_i : --_i) {
                $tr = $('<tr/>').appendTo($tbody);
                for (c = _j = 0; 0 <= col ? _j < col : _j > col; c = 0 <= col ? ++_j : --_j) {
                    $td = $('<td/>').appendTo($tr);
                    if (phBr) {
                        $td.append(this.editor.util.phBr);
                    }
                }
            }
            return $table;
        };

        TableButton.prototype.refreshTableWidth = function($table) {
            var cols, tableWidth;
            tableWidth = $table.width();
            cols = $table.find('col');
            return $table.find('tr:first td').each((function(_this) {
                return function(i, td) {
                    var $col;
                    $col = cols.eq(i);
                    return $col.attr('width', ($(td).outerWidth() / tableWidth * 100) + '%');
                };
            })(this));
        };

        TableButton.prototype.setActive = function(active) {
            TableButton.__super__.setActive.call(this, active);
            if (active) {
                this.createMenu.hide();
                return this.editMenu.show();
            } else {
                this.createMenu.show();
                return this.editMenu.hide();
            }
        };

        TableButton.prototype.deleteRow = function($td) {
            var $newTr, $tr, index;
            $tr = $td.parent('tr');
            if ($tr.siblings('tr').length < 1) {
                return this.deleteTable($td);
            } else {
                $newTr = $tr.next('tr');
                if (!($newTr.length > 0)) {
                    $newTr = $tr.prev('tr');
                }
                index = $tr.find('td').index($td);
                $tr.remove();
                return this.editor.selection.setRangeAtEndOf($newTr.find('td').eq(index));
            }
        };

        TableButton.prototype.insertRow = function($td, direction) {
            var $newTr, $table, $tr, colNum, i, index, _i;
            if (direction == null) {
                direction = 'after';
            }
            $tr = $td.parent('tr');
            $table = $tr.closest('table');
            colNum = 0;
            $table.find('tr').each((function(_this) {
                return function(i, tr) {
                    return colNum = Math.max(colNum, $(tr).find('td').length);
                };
            })(this));
            $newTr = $('<tr/>');
            for (i = _i = 1; 1 <= colNum ? _i <= colNum : _i >= colNum; i = 1 <= colNum ? ++_i : --_i) {
                $('<td/>').append(this.editor.util.phBr).appendTo($newTr);
            }
            $tr[direction]($newTr);
            index = $tr.find('td').index($td);
            return this.editor.selection.setRangeAtStartOf($newTr.find('td').eq(index));
        };

        TableButton.prototype.deleteCol = function($td) {
            var $newTd, $table, $tr, index;
            $tr = $td.parent('tr');
            if ($tr.siblings('tr').length < 1 && $td.siblings('td').length < 1) {
                return this.deleteTable($td);
            } else {
                index = $tr.find('td').index($td);
                $newTd = $td.next('td');
                if (!($newTd.length > 0)) {
                    $newTd = $tr.prev('td');
                }
                $table = $tr.closest('table');
                $table.find('col').eq(index).remove();
                $table.find('tr').each((function(_this) {
                    return function(i, tr) {
                        return $(tr).find('td').eq(index).remove();
                    };
                })(this));
                this.refreshTableWidth($table);
                return this.editor.selection.setRangeAtEndOf($newTd);
            }
        };

        TableButton.prototype.insertCol = function($td, direction) {
            var $col, $newCol, $newTd, $table, $tr, index, tableWidth, width;
            if (direction == null) {
                direction = 'after';
            }
            $tr = $td.parent('tr');
            index = $tr.find('td').index($td);
            $table = $td.closest('table');
            $col = $table.find('col').eq(index);
            $table.find('tr').each((function(_this) {
                return function(i, tr) {
                    var $newTd;
                    $newTd = $('<td/>').append(_this.editor.util.phBr);
                    return $(tr).find('td').eq(index)[direction]($newTd);
                };
            })(this));
            $newCol = $('<col/>');
            $col[direction]($newCol);
            tableWidth = $table.width();
            width = Math.max(parseFloat($col.attr('width')) / 2, 50 / tableWidth * 100);
            $col.attr('width', width + '%');
            $newCol.attr('width', width + '%');
            this.refreshTableWidth($table);
            $newTd = direction === 'after' ? $td.next('td') : $td.prev('td');
            return this.editor.selection.setRangeAtStartOf($newTd);
        };

        TableButton.prototype.deleteTable = function($td) {
            var $block, $table;
            $table = $td.closest('.simditor-table');
            $block = $table.next('p');
            $table.remove();
            if ($block.length > 0) {
                return this.editor.selection.setRangeAtStartOf($block);
            }
        };

        TableButton.prototype.command = function(param) {
            var $td, range;
            range = this.editor.selection.getRange();
            $td = $(range.commonAncestorContainer).closest('td');
            if (!($td.length > 0)) {
                return;
            }
            if (param === 'deleteRow') {
                this.deleteRow($td);
            } else if (param === 'insertRowAbove') {
                this.insertRow($td, 'before');
            } else if (param === 'insertRowBelow') {
                this.insertRow($td);
            } else if (param === 'deleteCol') {
                this.deleteCol($td);
            } else if (param === 'insertColLeft') {
                this.insertCol($td, 'before');
            } else if (param === 'insertColRight') {
                this.insertCol($td);
            } else if (param === 'deleteTable') {
                this.deleteTable($td);
            } else {
                return;
            }
            return this.editor.trigger('valuechanged');
        };

        return TableButton;

    })(Button);

    Simditor.Toolbar.addButton(TableButton);

}).call(this);
