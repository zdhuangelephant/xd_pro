/*!
 * common_js_plugin_for_jquery
 * version: 1.0.0
 * @requires jQuery v1.3.2 or later
 * @requires jQuery.form.js
 *
 * @author zhaodan
 * @date 16/7/16.
 */
/*global ActiveXObject alert */
;(function ($) {
    $.fn.extend({
        /*! up the form to the server*/
        up2Server: function (callback) {
            function callBackFunc(responseText, statusText) {
                if (statusText == 'success') {
                    alert(responseText);
                } else {
                    alert("error!");
                }
            }
            if (!callback)
                callback = callBackFunc;
            this._form_options = {
                success: callback
            };
            this.ajaxSubmit(this._form_options);
        }
    });
})(jQuery);