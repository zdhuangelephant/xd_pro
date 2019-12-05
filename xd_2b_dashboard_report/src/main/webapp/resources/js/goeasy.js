var millis = 1498626560809;
var goEasy_V = '0.13.21';
(function t(e, r) {
    if (typeof exports === "object" && typeof module === "object")
        module.exports = r(require("JSON"));
    else if (typeof define === "function" && define.amd)
        define(["JSON"], r);
    else if (typeof exports === "object")
        exports["io"] = r(require("JSON"));
    else
        e["io"] = r(e["JSON"])
}
)(this, function(t) {
    return function(t) {
        var e = {};
        function r(n) {
            if (e[n])
                return e[n].exports;
            var i = e[n] = {
                exports: {},
                id: n,
                loaded: false
            };
            t[n].call(i.exports, i, i.exports, r);
            i.loaded = true;
            return i.exports
        }
        r.m = t;
        r.c = e;
        r.p = "";
        return r(0)
    }([function(t, e, r) {
        "use strict";
        var n = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function(t) {
            return typeof t
        }
        : function(t) {
            return t && typeof Symbol === "function" && t.constructor === Symbol && t !== Symbol.prototype ? "symbol" : typeof t
        }
        ;
        var i = r(1);
        var o = r(4);
        var s = r(10);
        var a = r(3)("socket.io-client");
        t.exports = e = f;
        var c = e.managers = {};
        function f(t, e) {
            if ((typeof t === "undefined" ? "undefined" : n(t)) === "object") {
                e = t;
                t = undefined
            }
            e = e || {};
            var r = i(t);
            var o = r.source;
            var a = r.id;
            var f = r.path;
            var u = c[a] && f in c[a].nsps;
            var p = e.forceNew || e["force new connection"] || false === e.multiplex || u;
            var l;
            if (p) {
                l = s(o, e)
            } else {
                if (!c[a]) {
                    c[a] = s(o, e)
                }
                l = c[a]
            }
            if (r.query && !e.query) {
                e.query = r.query
            } else if (e && "object" === n(e.query)) {
                e.query = h(e.query)
            }
            return l.socket(r.path, e)
        }
        function h(t) {
            var e = [];
            for (var r in t) {
                if (t.hasOwnProperty(r)) {
                    e.push(encodeURIComponent(r) + "=" + encodeURIComponent(t[r]))
                }
            }
            return e.join("&")
        }
        e.protocol = o.protocol;
        e.connect = f;
        e.Manager = r(10);
        e.Socket = r(38)
    }
    , function(t, e, r) {
        (function(e) {
            "use strict";
            var n = r(2);
            var i = r(3)("socket.io-client:url");
            t.exports = o;
            function o(t, r) {
                var i = t;
                r = r || e.location;
                if (null == t)
                    t = r.protocol + "//" + r.host;
                if ("string" === typeof t) {
                    if ("/" === t.charAt(0)) {
                        if ("/" === t.charAt(1)) {
                            t = r.protocol + t
                        } else {
                            t = r.host + t
                        }
                    }
                    if (!/^(https?|wss?):\/\//.test(t)) {
                        if ("undefined" !== typeof r) {
                            t = r.protocol + "//" + t
                        } else {
                            t = "https://" + t
                        }
                    }
                    i = n(t)
                }
                if (!i.port) {
                    if (/^(http|ws)$/.test(i.protocol)) {
                        i.port = "80"
                    } else if (/^(http|ws)s$/.test(i.protocol)) {
                        i.port = "443"
                    }
                }
                i.path = i.path || "/";
                var o = i.host.indexOf(":") !== -1;
                var s = o ? "[" + i.host + "]" : i.host;
                i.id = i.protocol + "://" + s + ":" + i.port;
                i.href = i.protocol + "://" + s + (r && r.port === i.port ? "" : ":" + i.port);
                return i
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e) {
        var r = /^(?:(?![^:@]+:[^:@\/]*@)(http|https|ws|wss):\/\/)?((?:(([^:@]*)(?::([^:@]*))?)?@)?((?:[a-f0-9]{0,4}:){2,7}[a-f0-9]{0,4}|[^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/;
        var n = ["source", "protocol", "authority", "userInfo", "user", "password", "host", "port", "relative", "path", "directory", "file", "query", "anchor"];
        t.exports = function i(t) {
            var e = t
              , i = t.indexOf("[")
              , o = t.indexOf("]");
            if (i != -1 && o != -1) {
                t = t.substring(0, i) + t.substring(i, o).replace(/:/g, ";") + t.substring(o, t.length)
            }
            var s = r.exec(t || "")
              , a = {}
              , c = 14;
            while (c--) {
                a[n[c]] = s[c] || ""
            }
            if (i != -1 && o != -1) {
                a.source = e;
                a.host = a.host.substring(1, a.host.length - 1).replace(/;/g, ":");
                a.authority = a.authority.replace("[", "").replace("]", "").replace(/;/g, ":");
                a.ipv6uri = true
            }
            return a
        }
    }
    , function(t, e) {
        "use strict";
        t.exports = function() {
            return function() {}
        }
    }
    , function(t, e, r) {
        var n = r(3)("socket.io-parser");
        var i = r(5);
        var o = r(6);
        var s = r(7);
        var a = r(9);
        e.protocol = 4;
        e.types = ["CONNECT", "DISCONNECT", "EVENT", "ACK", "ERROR", "BINARY_EVENT", "BINARY_ACK"];
        e.CONNECT = 0;
        e.DISCONNECT = 1;
        e.EVENT = 2;
        e.ACK = 3;
        e.ERROR = 4;
        e.BINARY_EVENT = 5;
        e.BINARY_ACK = 6;
        e.Encoder = c;
        e.Decoder = u;
        function c() {}
        c.prototype.encode = function(t, r) {
            if (e.BINARY_EVENT == t.type || e.BINARY_ACK == t.type) {
                h(t, r)
            } else {
                var n = f(t);
                r([n])
            }
        }
        ;
        function f(t) {
            var r = "";
            var n = false;
            r += t.type;
            if (e.BINARY_EVENT == t.type || e.BINARY_ACK == t.type) {
                r += t.attachments;
                r += "-"
            }
            if (t.nsp && "/" != t.nsp) {
                n = true;
                r += t.nsp
            }
            if (null != t.id) {
                if (n) {
                    r += ",";
                    n = false
                }
                r += t.id
            }
            if (null != t.data) {
                if (n)
                    r += ",";
                r += i.stringify(t.data)
            }
            return r
        }
        function h(t, e) {
            function r(t) {
                var r = s.deconstructPacket(t);
                var n = f(r.packet);
                var i = r.buffers;
                i.unshift(n);
                e(i)
            }
            s.removeBlobs(t, r)
        }
        function u() {
            this.reconstructor = null
        }
        o(u.prototype);
        u.prototype.add = function(t) {
            var r;
            if ("string" == typeof t) {
                r = p(t);
                if (e.BINARY_EVENT == r.type || e.BINARY_ACK == r.type) {
                    this.reconstructor = new d(r);
                    if (this.reconstructor.reconPack.attachments === 0) {
                        this.emit("decoded", r)
                    }
                } else {
                    this.emit("decoded", r)
                }
            } else if (a(t) || t.base64) {
                if (!this.reconstructor) {
                    throw new Error("got binary data when not reconstructing a packet")
                } else {
                    r = this.reconstructor.takeBinaryData(t);
                    if (r) {
                        this.reconstructor = null;
                        this.emit("decoded", r)
                    }
                }
            } else {
                throw new Error("Unknown type: " + t)
            }
        }
        ;
        function p(t) {
            var r = {};
            var n = 0;
            r.type = Number(t.charAt(0));
            if (null == e.types[r.type])
                return y();
            if (e.BINARY_EVENT == r.type || e.BINARY_ACK == r.type) {
                var i = "";
                while (t.charAt(++n) != "-") {
                    i += t.charAt(n);
                    if (n == t.length)
                        break
                }
                if (i != Number(i) || t.charAt(n) != "-") {
                    throw new Error("Illegal attachments")
                }
                r.attachments = Number(i)
            }
            if ("/" == t.charAt(n + 1)) {
                r.nsp = "";
                while (++n) {
                    var o = t.charAt(n);
                    if ("," == o)
                        break;
                    r.nsp += o;
                    if (n == t.length)
                        break
                }
            } else {
                r.nsp = "/"
            }
            var s = t.charAt(n + 1);
            if ("" !== s && Number(s) == s) {
                r.id = "";
                while (++n) {
                    var o = t.charAt(n);
                    if (null == o || Number(o) != o) {
                        --n;
                        break
                    }
                    r.id += t.charAt(n);
                    if (n == t.length)
                        break
                }
                r.id = Number(r.id)
            }
            if (t.charAt(++n)) {
                r = l(r, t.substr(n))
            }
            return r
        }
        function l(t, e) {
            try {
                t.data = i.parse(e)
            } catch (r) {
                return y()
            }
            return t
        }
        u.prototype.destroy = function() {
            if (this.reconstructor) {
                this.reconstructor.finishedReconstruction()
            }
        }
        ;
        function d(t) {
            this.reconPack = t;
            this.buffers = []
        }
        d.prototype.takeBinaryData = function(t) {
            this.buffers.push(t);
            if (this.buffers.length == this.reconPack.attachments) {
                var e = s.reconstructPacket(this.reconPack, this.buffers);
                this.finishedReconstruction();
                return e
            }
            return null
        }
        ;
        d.prototype.finishedReconstruction = function() {
            this.reconPack = null;
            this.buffers = []
        }
        ;
        function y(t) {
            return {
                type: e.ERROR,
                data: "parser error"
            }
        }
    }
    , function(e, r) {
        e.exports = t
    }
    , function(t, e) {
        t.exports = r;
        function r(t) {
            if (t)
                return n(t)
        }
        function n(t) {
            for (var e in r.prototype) {
                t[e] = r.prototype[e]
            }
            return t
        }
        r.prototype.on = r.prototype.addEventListener = function(t, e) {
            this._callbacks = this._callbacks || {};
            (this._callbacks[t] = this._callbacks[t] || []).push(e);
            return this
        }
        ;
        r.prototype.once = function(t, e) {
            var r = this;
            this._callbacks = this._callbacks || {};
            function n() {
                r.off(t, n);
                e.apply(this, arguments)
            }
            n.fn = e;
            this.on(t, n);
            return this
        }
        ;
        r.prototype.off = r.prototype.removeListener = r.prototype.removeAllListeners = r.prototype.removeEventListener = function(t, e) {
            this._callbacks = this._callbacks || {};
            if (0 == arguments.length) {
                this._callbacks = {};
                return this
            }
            var r = this._callbacks[t];
            if (!r)
                return this;
            if (1 == arguments.length) {
                delete this._callbacks[t];
                return this
            }
            var n;
            for (var i = 0; i < r.length; i++) {
                n = r[i];
                if (n === e || n.fn === e) {
                    r.splice(i, 1);
                    break
                }
            }
            return this
        }
        ;
        r.prototype.emit = function(t) {
            this._callbacks = this._callbacks || {};
            var e = [].slice.call(arguments, 1)
              , r = this._callbacks[t];
            if (r) {
                r = r.slice(0);
                for (var n = 0, i = r.length; n < i; ++n) {
                    r[n].apply(this, e)
                }
            }
            return this
        }
        ;
        r.prototype.listeners = function(t) {
            this._callbacks = this._callbacks || {};
            return this._callbacks[t] || []
        }
        ;
        r.prototype.hasListeners = function(t) {
            return !!this.listeners(t).length
        }
    }
    , function(t, e, r) {
        (function(t) {
            var n = r(8);
            var i = r(9);
            e.deconstructPacket = function(t) {
                var e = [];
                var r = t.data;
                function o(t) {
                    if (!t)
                        return t;
                    if (i(t)) {
                        var r = {
                            _placeholder: true,
                            num: e.length
                        };
                        e.push(t);
                        return r
                    } else if (n(t)) {
                        var s = new Array(t.length);
                        for (var a = 0; a < t.length; a++) {
                            s[a] = o(t[a])
                        }
                        return s
                    } else if ("object" == typeof t && !(t instanceof Date)) {
                        var s = {};
                        for (var c in t) {
                            s[c] = o(t[c])
                        }
                        return s
                    }
                    return t
                }
                var s = t;
                s.data = o(r);
                s.attachments = e.length;
                return {
                    packet: s,
                    buffers: e
                }
            }
            ;
            e.reconstructPacket = function(t, e) {
                var r = 0;
                function i(t) {
                    if (t && t._placeholder) {
                        var r = e[t.num];
                        return r
                    } else if (n(t)) {
                        for (var o = 0; o < t.length; o++) {
                            t[o] = i(t[o])
                        }
                        return t
                    } else if (t && "object" == typeof t) {
                        for (var s in t) {
                            t[s] = i(t[s])
                        }
                        return t
                    }
                    return t
                }
                t.data = i(t.data);
                t.attachments = undefined;
                return t
            }
            ;
            e.removeBlobs = function(e, r) {
                function o(e, c, f) {
                    if (!e)
                        return e;
                    if (t.Blob && e instanceof Blob || t.File && e instanceof File) {
                        s++;
                        var h = new FileReader;
                        h.onload = function() {
                            if (f) {
                                f[c] = this.result
                            } else {
                                a = this.result
                            }
                            if (!--s) {
                                r(a)
                            }
                        }
                        ;
                        h.readAsArrayBuffer(e)
                    } else if (n(e)) {
                        for (var u = 0; u < e.length; u++) {
                            o(e[u], u, e)
                        }
                    } else if (e && "object" == typeof e && !i(e)) {
                        for (var p in e) {
                            o(e[p], p, e)
                        }
                    }
                }
                var s = 0;
                var a = e;
                o(a);
                if (!s) {
                    r(a)
                }
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e) {
        t.exports = Array.isArray || function(t) {
            return Object.prototype.toString.call(t) == "[object Array]"
        }
    }
    , function(t, e) {
        (function(e) {
            t.exports = r;
            function r(t) {
                return e.Buffer && e.Buffer.isBuffer(t) || e.ArrayBuffer && t instanceof ArrayBuffer
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e, r) {
        "use strict";
        var n = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function(t) {
            return typeof t
        }
        : function(t) {
            return t && typeof Symbol === "function" && t.constructor === Symbol && t !== Symbol.prototype ? "symbol" : typeof t
        }
        ;
        var i = r(11);
        var o = r(38);
        var s = r(29);
        var a = r(4);
        var c = r(40);
        var f = r(41);
        var h = r(3)("socket.io-client:manager");
        var u = r(36);
        var p = r(42);
        var l = Object.prototype.hasOwnProperty;
        t.exports = d;
        function d(t, e) {
            if (!(this instanceof d))
                return new d(t,e);
            if (t && "object" === (typeof t === "undefined" ? "undefined" : n(t))) {
                e = t;
                t = undefined
            }
            e = e || {};
            e.path = e.path || "/socket.io";
            this.nsps = {};
            this.subs = [];
            this.opts = e;
            this.reconnection(e.reconnection !== false);
            this.reconnectionAttempts(e.reconnectionAttempts || Infinity);
            this.reconnectionDelay(e.reconnectionDelay || 1e3);
            this.reconnectionDelayMax(e.reconnectionDelayMax || 5e3);
            this.randomizationFactor(e.randomizationFactor || .5);
            this.backoff = new p({
                min: this.reconnectionDelay(),
                max: this.reconnectionDelayMax(),
                jitter: this.randomizationFactor()
            });
            this.timeout(null == e.timeout ? 2e4 : e.timeout);
            this.readyState = "closed";
            this.uri = t;
            this.connecting = [];
            this.lastPing = null;
            this.encoding = false;
            this.packetBuffer = [];
            this.encoder = new a.Encoder;
            this.decoder = new a.Decoder;
            this.autoConnect = e.autoConnect !== false;
            if (this.autoConnect)
                this.open()
        }
        d.prototype.emitAll = function() {
            this.emit.apply(this, arguments);
            for (var t in this.nsps) {
                if (l.call(this.nsps, t)) {
                    this.nsps[t].emit.apply(this.nsps[t], arguments)
                }
            }
        }
        ;
        d.prototype.updateSocketIds = function() {
            for (var t in this.nsps) {
                if (l.call(this.nsps, t)) {
                    this.nsps[t].id = this.engine.id
                }
            }
        }
        ;
        s(d.prototype);
        d.prototype.reconnection = function(t) {
            if (!arguments.length)
                return this._reconnection;
            this._reconnection = !!t;
            return this
        }
        ;
        d.prototype.reconnectionAttempts = function(t) {
            if (!arguments.length)
                return this._reconnectionAttempts;
            this._reconnectionAttempts = t;
            return this
        }
        ;
        d.prototype.reconnectionDelay = function(t) {
            if (!arguments.length)
                return this._reconnectionDelay;
            this._reconnectionDelay = t;
            this.backoff && this.backoff.setMin(t);
            return this
        }
        ;
        d.prototype.randomizationFactor = function(t) {
            if (!arguments.length)
                return this._randomizationFactor;
            this._randomizationFactor = t;
            this.backoff && this.backoff.setJitter(t);
            return this
        }
        ;
        d.prototype.reconnectionDelayMax = function(t) {
            if (!arguments.length)
                return this._reconnectionDelayMax;
            this._reconnectionDelayMax = t;
            this.backoff && this.backoff.setMax(t);
            return this
        }
        ;
        d.prototype.timeout = function(t) {
            if (!arguments.length)
                return this._timeout;
            this._timeout = t;
            return this
        }
        ;
        d.prototype.maybeReconnectOnOpen = function() {
            if (!this.reconnecting && this._reconnection && this.backoff.attempts === 0) {
                this.reconnect()
            }
        }
        ;
        d.prototype.open = d.prototype.connect = function(t, e) {
            if (~this.readyState.indexOf("open"))
                return this;
            this.engine = i(this.uri, this.opts);
            var r = this.engine;
            var n = this;
            this.readyState = "opening";
            this.skipReconnect = false;
            var o = c(r, "open", function() {
                n.onopen();
                t && t()
            });
            var s = c(r, "error", function(e) {
                n.cleanup();
                n.readyState = "closed";
                n.emitAll("connect_error", e);
                if (t) {
                    var r = new Error("Connection error");
                    r.data = e;
                    t(r)
                } else {
                    n.maybeReconnectOnOpen()
                }
            });
            if (false !== this._timeout) {
                var a = this._timeout;
                var f = setTimeout(function() {
                    o.destroy();
                    r.close();
                    r.emit("error", "timeout");
                    n.emitAll("connect_timeout", a)
                }, a);
                this.subs.push({
                    destroy: function h() {
                        clearTimeout(f)
                    }
                })
            }
            this.subs.push(o);
            this.subs.push(s);
            return this
        }
        ;
        d.prototype.onopen = function() {
            this.cleanup();
            this.readyState = "open";
            this.emit("open");
            var t = this.engine;
            this.subs.push(c(t, "data", f(this, "ondata")));
            this.subs.push(c(t, "ping", f(this, "onping")));
            this.subs.push(c(t, "pong", f(this, "onpong")));
            this.subs.push(c(t, "error", f(this, "onerror")));
            this.subs.push(c(t, "close", f(this, "onclose")));
            this.subs.push(c(this.decoder, "decoded", f(this, "ondecoded")))
        }
        ;
        d.prototype.onping = function() {
            this.lastPing = new Date;
            this.emitAll("ping")
        }
        ;
        d.prototype.onpong = function() {
            this.emitAll("pong", new Date - this.lastPing)
        }
        ;
        d.prototype.ondata = function(t) {
            this.decoder.add(t)
        }
        ;
        d.prototype.ondecoded = function(t) {
            this.emit("packet", t)
        }
        ;
        d.prototype.onerror = function(t) {
            this.emitAll("error", t)
        }
        ;
        d.prototype.socket = function(t, e) {
            var r = this.nsps[t];
            if (!r) {
                r = new o(this,t,e);
                this.nsps[t] = r;
                var n = this;
                r.on("connecting", i);
                r.on("connect", function() {
                    r.id = n.engine.id
                });
                if (this.autoConnect) {
                    i()
                }
            }
            function i() {
                if (!~u(n.connecting, r)) {
                    n.connecting.push(r)
                }
            }
            return r
        }
        ;
        d.prototype.destroy = function(t) {
            var e = u(this.connecting, t);
            if (~e)
                this.connecting.splice(e, 1);
            if (this.connecting.length)
                return;
            this.close()
        }
        ;
        d.prototype.packet = function(t) {
            var e = this;
            if (t.query && t.type === 0)
                t.nsp += "?" + t.query;
            if (!e.encoding) {
                e.encoding = true;
                this.encoder.encode(t, function(r) {
                    for (var n = 0; n < r.length; n++) {
                        e.engine.write(r[n], t.options)
                    }
                    e.encoding = false;
                    e.processPacketQueue()
                })
            } else {
                e.packetBuffer.push(t)
            }
        }
        ;
        d.prototype.processPacketQueue = function() {
            if (this.packetBuffer.length > 0 && !this.encoding) {
                var t = this.packetBuffer.shift();
                this.packet(t)
            }
        }
        ;
        d.prototype.cleanup = function() {
            var t = this.subs.length;
            for (var e = 0; e < t; e++) {
                var r = this.subs.shift();
                r.destroy()
            }
            this.packetBuffer = [];
            this.encoding = false;
            this.lastPing = null;
            this.decoder.destroy()
        }
        ;
        d.prototype.close = d.prototype.disconnect = function() {
            this.skipReconnect = true;
            this.reconnecting = false;
            if ("opening" === this.readyState) {
                this.cleanup()
            }
            this.backoff.reset();
            this.readyState = "closed";
            if (this.engine)
                this.engine.close()
        }
        ;
        d.prototype.onclose = function(t) {
            this.cleanup();
            this.backoff.reset();
            this.readyState = "closed";
            this.emit("close", t);
            if (this._reconnection && !this.skipReconnect) {
                this.reconnect()
            }
        }
        ;
        d.prototype.reconnect = function() {
            if (this.reconnecting || this.skipReconnect)
                return this;
            var t = this;
            if (this.backoff.attempts >= this._reconnectionAttempts) {
                this.backoff.reset();
                this.emitAll("reconnect_failed");
                this.reconnecting = false
            } else {
                var e = this.backoff.duration();
                this.reconnecting = true;
                var r = setTimeout(function() {
                    if (t.skipReconnect)
                        return;
                    t.emitAll("reconnect_attempt", t.backoff.attempts);
                    t.emitAll("reconnecting", t.backoff.attempts);
                    if (t.skipReconnect)
                        return;
                    t.open(function(e) {
                        if (e) {
                            t.reconnecting = false;
                            t.reconnect();
                            t.emitAll("reconnect_error", e.data)
                        } else {
                            t.onreconnect()
                        }
                    })
                }, e);
                this.subs.push({
                    destroy: function n() {
                        clearTimeout(r)
                    }
                })
            }
        }
        ;
        d.prototype.onreconnect = function() {
            var t = this.backoff.attempts;
            this.reconnecting = false;
            this.backoff.reset();
            this.updateSocketIds();
            this.emitAll("reconnect", t)
        }
    }
    , function(t, e, r) {
        t.exports = r(12)
    }
    , function(t, e, r) {
        t.exports = r(13);
        t.exports.parser = r(20)
    }
    , function(t, e, r) {
        (function(e) {
            var n = r(14);
            var i = r(29);
            var o = r(3)("engine.io-client:socket");
            var s = r(36);
            var a = r(20);
            var c = r(2);
            var f = r(37);
            var h = r(30);
            t.exports = u;
            function u(t, r) {
                if (!(this instanceof u))
                    return new u(t,r);
                r = r || {};
                if (t && "object" === typeof t) {
                    r = t;
                    t = null
                }
                if (t) {
                    t = c(t);
                    r.hostname = t.host;
                    r.secure = t.protocol === "https" || t.protocol === "wss";
                    r.port = t.port;
                    if (t.query)
                        r.query = t.query
                } else if (r.host) {
                    r.hostname = c(r.host).host
                }
                this.secure = null != r.secure ? r.secure : e.location && "https:" === location.protocol;
                if (r.hostname && !r.port) {
                    r.port = this.secure ? "443" : "80"
                }
                this.agent = r.agent || false;
                this.hostname = r.hostname || (e.location ? location.hostname : "localhost");
                this.port = r.port || (e.location && location.port ? location.port : this.secure ? 443 : 80);
                this.query = r.query || {};
                if ("string" === typeof this.query)
                    this.query = h.decode(this.query);
                this.upgrade = false !== r.upgrade;
                this.path = (r.path || "/engine.io").replace(/\/$/, "") + "/";
                this.forceJSONP = !!r.forceJSONP;
                this.jsonp = false !== r.jsonp;
                this.forceBase64 = !!r.forceBase64;
                this.enablesXDR = !!r.enablesXDR;
                this.timestampParam = r.timestampParam || "t";
                this.timestampRequests = r.timestampRequests;
                this.transports = r.transports || ["polling", "websocket"];
                this.readyState = "";
                this.writeBuffer = [];
                this.prevBufferLen = 0;
                this.policyPort = r.policyPort || 843;
                this.rememberUpgrade = r.rememberUpgrade || false;
                this.binaryType = null;
                this.onlyBinaryUpgrades = r.onlyBinaryUpgrades;
                this.perMessageDeflate = false !== r.perMessageDeflate ? r.perMessageDeflate || {} : false;
                if (true === this.perMessageDeflate)
                    this.perMessageDeflate = {};
                if (this.perMessageDeflate && null == this.perMessageDeflate.threshold) {
                    this.perMessageDeflate.threshold = 1024
                }
                this.pfx = r.pfx || null;
                this.key = r.key || null;
                this.passphrase = r.passphrase || null;
                this.cert = r.cert || null;
                this.ca = r.ca || null;
                this.ciphers = r.ciphers || null;
                this.rejectUnauthorized = r.rejectUnauthorized === undefined ? null : r.rejectUnauthorized;
                this.forceNode = !!r.forceNode;
                var n = typeof e === "object" && e;
                if (n.global === n) {
                    if (r.extraHeaders && Object.keys(r.extraHeaders).length > 0) {
                        this.extraHeaders = r.extraHeaders
                    }
                    if (r.localAddress) {
                        this.localAddress = r.localAddress
                    }
                }
                this.id = null;
                this.upgrades = null;
                this.pingInterval = null;
                this.pingTimeout = null;
                this.pingIntervalTimer = null;
                this.pingTimeoutTimer = null;
                this.open()
            }
            u.priorWebsocketSuccess = false;
            i(u.prototype);
            u.protocol = a.protocol;
            u.Socket = u;
            u.Transport = r(19);
            u.transports = r(14);
            u.parser = r(20);
            u.prototype.createTransport = function(t) {
                var e = p(this.query);
                e.EIO = a.protocol;
                e.transport = t;
                if (this.id)
                    e.sid = this.id;
                var r = new n[t]({
                    agent: this.agent,
                    hostname: this.hostname,
                    port: this.port,
                    secure: this.secure,
                    path: this.path,
                    query: e,
                    forceJSONP: this.forceJSONP,
                    jsonp: this.jsonp,
                    forceBase64: this.forceBase64,
                    enablesXDR: this.enablesXDR,
                    timestampRequests: this.timestampRequests,
                    timestampParam: this.timestampParam,
                    policyPort: this.policyPort,
                    socket: this,
                    pfx: this.pfx,
                    key: this.key,
                    passphrase: this.passphrase,
                    cert: this.cert,
                    ca: this.ca,
                    ciphers: this.ciphers,
                    rejectUnauthorized: this.rejectUnauthorized,
                    perMessageDeflate: this.perMessageDeflate,
                    extraHeaders: this.extraHeaders,
                    forceNode: this.forceNode,
                    localAddress: this.localAddress
                });
                return r
            }
            ;
            function p(t) {
                var e = {};
                for (var r in t) {
                    if (t.hasOwnProperty(r)) {
                        e[r] = t[r]
                    }
                }
                return e
            }
            u.prototype.open = function() {
                var t;
                if (this.rememberUpgrade && u.priorWebsocketSuccess && this.transports.indexOf("websocket") !== -1) {
                    t = "websocket"
                } else if (0 === this.transports.length) {
                    var e = this;
                    setTimeout(function() {
                        e.emit("error", "No transports available")
                    }, 0);
                    return
                } else {
                    t = this.transports[0]
                }
                this.readyState = "opening";
                try {
                    t = this.createTransport(t)
                } catch (r) {
                    this.transports.shift();
                    this.open();
                    return
                }
                t.open();
                this.setTransport(t)
            }
            ;
            u.prototype.setTransport = function(t) {
                var e = this;
                if (this.transport) {
                    this.transport.removeAllListeners()
                }
                this.transport = t;
                t.on("drain", function() {
                    e.onDrain()
                }).on("packet", function(t) {
                    e.onPacket(t)
                }).on("error", function(t) {
                    e.onError(t)
                }).on("close", function() {
                    e.onClose("transport close")
                })
            }
            ;
            u.prototype.probe = function(t) {
                var e = this.createTransport(t, {
                    probe: 1
                });
                var r = false;
                var n = this;
                u.priorWebsocketSuccess = false;
                function i() {
                    if (n.onlyBinaryUpgrades) {
                        var t = !this.supportsBinary && n.transport.supportsBinary;
                        r = r || t
                    }
                    if (r)
                        return;
                    e.send([{
                        type: "ping",
                        data: "probe"
                    }]);
                    e.once("packet", function(t) {
                        if (r)
                            return;
                        if ("pong" === t.type && "probe" === t.data) {
                            n.upgrading = true;
                            n.emit("upgrading", e);
                            if (!e)
                                return;
                            u.priorWebsocketSuccess = "websocket" === e.name;
                            n.transport.pause(function() {
                                if (r)
                                    return;
                                if ("closed" === n.readyState)
                                    return;
                                h();
                                n.setTransport(e);
                                e.send([{
                                    type: "upgrade"
                                }]);
                                n.emit("upgrade", e);
                                e = null;
                                n.upgrading = false;
                                n.flush()
                            })
                        } else {
                            var i = new Error("probe error");
                            i.transport = e.name;
                            n.emit("upgradeError", i)
                        }
                    })
                }
                function o() {
                    if (r)
                        return;
                    r = true;
                    h();
                    e.close();
                    e = null
                }
                function s(t) {
                    var r = new Error("probe error: " + t);
                    r.transport = e.name;
                    o();
                    n.emit("upgradeError", r)
                }
                function a() {
                    s("transport closed")
                }
                function c() {
                    s("socket closed")
                }
                function f(t) {
                    if (e && t.name !== e.name) {
                        o()
                    }
                }
                function h() {
                    e.removeListener("open", i);
                    e.removeListener("error", s);
                    e.removeListener("close", a);
                    n.removeListener("close", c);
                    n.removeListener("upgrading", f)
                }
                e.once("open", i);
                e.once("error", s);
                e.once("close", a);
                this.once("close", c);
                this.once("upgrading", f);
                e.open()
            }
            ;
            u.prototype.onOpen = function() {
                this.readyState = "open";
                u.priorWebsocketSuccess = "websocket" === this.transport.name;
                this.emit("open");
                this.flush();
                if ("open" === this.readyState && this.upgrade && this.transport.pause) {
                    for (var t = 0, e = this.upgrades.length; t < e; t++) {
                        this.probe(this.upgrades[t])
                    }
                }
            }
            ;
            u.prototype.onPacket = function(t) {
                if ("opening" === this.readyState || "open" === this.readyState || "closing" === this.readyState) {
                    this.emit("packet", t);
                    this.emit("heartbeat");
                    switch (t.type) {
                    case "open":
                        this.onHandshake(f(t.data));
                        break;
                    case "pong":
                        this.setPing();
                        this.emit("pong");
                        break;
                    case "error":
                        var e = new Error("server error");
                        e.code = t.data;
                        this.onError(e);
                        break;
                    case "message":
                        this.emit("data", t.data);
                        this.emit("message", t.data);
                        break
                    }
                } else {}
            }
            ;
            u.prototype.onHandshake = function(t) {
                this.emit("handshake", t);
                this.id = t.sid;
                this.transport.query.sid = t.sid;
                this.upgrades = this.filterUpgrades(t.upgrades);
                this.pingInterval = t.pingInterval;
                this.pingTimeout = t.pingTimeout;
                this.onOpen();
                if ("closed" === this.readyState)
                    return;
                this.setPing();
                this.removeListener("heartbeat", this.onHeartbeat);
                this.on("heartbeat", this.onHeartbeat)
            }
            ;
            u.prototype.onHeartbeat = function(t) {
                clearTimeout(this.pingTimeoutTimer);
                var e = this;
                e.pingTimeoutTimer = setTimeout(function() {
                    if ("closed" === e.readyState)
                        return;
                    e.onClose("ping timeout")
                }, t || e.pingInterval + e.pingTimeout)
            }
            ;
            u.prototype.setPing = function() {
                var t = this;
                clearTimeout(t.pingIntervalTimer);
                t.pingIntervalTimer = setTimeout(function() {
                    t.ping();
                    t.onHeartbeat(t.pingTimeout)
                }, t.pingInterval)
            }
            ;
            u.prototype.ping = function() {
                var t = this;
                this.sendPacket("ping", function() {
                    t.emit("ping")
                })
            }
            ;
            u.prototype.onDrain = function() {
                this.writeBuffer.splice(0, this.prevBufferLen);
                this.prevBufferLen = 0;
                if (0 === this.writeBuffer.length) {
                    this.emit("drain")
                } else {
                    this.flush()
                }
            }
            ;
            u.prototype.flush = function() {
                if ("closed" !== this.readyState && this.transport.writable && !this.upgrading && this.writeBuffer.length) {
                    this.transport.send(this.writeBuffer);
                    this.prevBufferLen = this.writeBuffer.length;
                    this.emit("flush")
                }
            }
            ;
            u.prototype.write = u.prototype.send = function(t, e, r) {
                this.sendPacket("message", t, e, r);
                return this
            }
            ;
            u.prototype.sendPacket = function(t, e, r, n) {
                if ("function" === typeof e) {
                    n = e;
                    e = undefined
                }
                if ("function" === typeof r) {
                    n = r;
                    r = null
                }
                if ("closing" === this.readyState || "closed" === this.readyState) {
                    return
                }
                r = r || {};
                r.compress = false !== r.compress;
                var i = {
                    type: t,
                    data: e,
                    options: r
                };
                this.emit("packetCreate", i);
                this.writeBuffer.push(i);
                if (n)
                    this.once("flush", n);
                this.flush()
            }
            ;
            u.prototype.close = function() {
                if ("opening" === this.readyState || "open" === this.readyState) {
                    this.readyState = "closing";
                    var t = this;
                    if (this.writeBuffer.length) {
                        this.once("drain", function() {
                            if (this.upgrading) {
                                n()
                            } else {
                                e()
                            }
                        })
                    } else if (this.upgrading) {
                        n()
                    } else {
                        e()
                    }
                }
                function e() {
                    t.onClose("forced close");
                    t.transport.close()
                }
                function r() {
                    t.removeListener("upgrade", r);
                    t.removeListener("upgradeError", r);
                    e()
                }
                function n() {
                    t.once("upgrade", r);
                    t.once("upgradeError", r)
                }
                return this
            }
            ;
            u.prototype.onError = function(t) {
                u.priorWebsocketSuccess = false;
                this.emit("error", t);
                this.onClose("transport error", t)
            }
            ;
            u.prototype.onClose = function(t, e) {
                if ("opening" === this.readyState || "open" === this.readyState || "closing" === this.readyState) {
                    var r = this;
                    clearTimeout(this.pingIntervalTimer);
                    clearTimeout(this.pingTimeoutTimer);
                    this.transport.removeAllListeners("close");
                    this.transport.close();
                    this.transport.removeAllListeners();
                    this.readyState = "closed";
                    this.id = null;
                    this.emit("close", t, e);
                    r.writeBuffer = [];
                    r.prevBufferLen = 0
                }
            }
            ;
            u.prototype.filterUpgrades = function(t) {
                var e = [];
                for (var r = 0, n = t.length; r < n; r++) {
                    if (~s(this.transports, t[r]))
                        e.push(t[r])
                }
                return e
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e, r) {
        (function(t) {
            var n = r(15);
            var i = r(17);
            var o = r(33);
            var s = r(34);
            e.polling = a;
            e.websocket = s;
            function a(e) {
                var r;
                var s = false;
                var a = false;
                var c = false !== e.jsonp;
                if (t.location) {
                    var f = "https:" === location.protocol;
                    var h = location.port;
                    if (!h) {
                        h = f ? 443 : 80
                    }
                    s = e.hostname !== location.hostname || h !== e.port;
                    a = e.secure !== f
                }
                e.xdomain = s;
                e.xscheme = a;
                r = new n(e);
                if ("open"in r && !e.forceJSONP) {
                    return new i(e)
                } else {
                    if (!c)
                        throw new Error("JSONP disabled");
                    return new o(e)
                }
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e, r) {
        (function(e) {
            var n = r(16);
            t.exports = function(t) {
                var r = t.xdomain;
                var i = t.xscheme;
                var o = t.enablesXDR;
                try {
                    if ("undefined" !== typeof XMLHttpRequest && (!r || n)) {
                        return new XMLHttpRequest
                    }
                } catch (s) {}
                try {
                    if ("undefined" !== typeof XDomainRequest && !i && o) {
                        return new XDomainRequest
                    }
                } catch (s) {}
                if (!r) {
                    try {
                        return new (e[["Active"].concat("Object").join("X")])("Microsoft.XMLHTTP")
                    } catch (s) {}
                }
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e) {
        try {
            t.exports = typeof XMLHttpRequest !== "undefined" && "withCredentials"in new XMLHttpRequest
        } catch (r) {
            t.exports = false
        }
    }
    , function(t, e, r) {
        (function(e) {
            var n = r(15);
            var i = r(18);
            var o = r(29);
            var s = r(31);
            var a = r(3)("engine.io-client:polling-xhr");
            t.exports = f;
            t.exports.Request = h;
            function c() {}
            function f(t) {
                i.call(this, t);
                this.requestTimeout = t.requestTimeout;
                if (e.location) {
                    var r = "https:" === location.protocol;
                    var n = location.port;
                    if (!n) {
                        n = r ? 443 : 80
                    }
                    this.xd = t.hostname !== e.location.hostname || n !== t.port;
                    this.xs = t.secure !== r
                } else {
                    this.extraHeaders = t.extraHeaders
                }
            }
            s(f, i);
            f.prototype.supportsBinary = true;
            f.prototype.request = function(t) {
                t = t || {};
                t.uri = this.uri();
                t.xd = this.xd;
                t.xs = this.xs;
                t.agent = this.agent || false;
                t.supportsBinary = this.supportsBinary;
                t.enablesXDR = this.enablesXDR;
                t.pfx = this.pfx;
                t.key = this.key;
                t.passphrase = this.passphrase;
                t.cert = this.cert;
                t.ca = this.ca;
                t.ciphers = this.ciphers;
                t.rejectUnauthorized = this.rejectUnauthorized;
                t.requestTimeout = this.requestTimeout;
                t.extraHeaders = this.extraHeaders;
                return new h(t)
            }
            ;
            f.prototype.doWrite = function(t, e) {
                var r = typeof t !== "string" && t !== undefined;
                var n = this.request({
                    method: "POST",
                    data: t,
                    isBinary: r
                });
                var i = this;
                n.on("success", e);
                n.on("error", function(t) {
                    i.onError("xhr post error", t)
                });
                this.sendXhr = n
            }
            ;
            f.prototype.doPoll = function() {
                var t = this.request();
                var e = this;
                t.on("data", function(t) {
                    e.onData(t)
                });
                t.on("error", function(t) {
                    e.onError("xhr poll error", t)
                });
                this.pollXhr = t
            }
            ;
            function h(t) {
                this.method = t.method || "GET";
                this.uri = t.uri;
                this.xd = !!t.xd;
                this.xs = !!t.xs;
                this.async = false !== t.async;
                this.data = undefined !== t.data ? t.data : null;
                this.agent = t.agent;
                this.isBinary = t.isBinary;
                this.supportsBinary = t.supportsBinary;
                this.enablesXDR = t.enablesXDR;
                this.requestTimeout = t.requestTimeout;
                this.pfx = t.pfx;
                this.key = t.key;
                this.passphrase = t.passphrase;
                this.cert = t.cert;
                this.ca = t.ca;
                this.ciphers = t.ciphers;
                this.rejectUnauthorized = t.rejectUnauthorized;
                this.extraHeaders = t.extraHeaders;
                this.create()
            }
            o(h.prototype);
            h.prototype.create = function() {
                var t = {
                    agent: this.agent,
                    xdomain: this.xd,
                    xscheme: this.xs,
                    enablesXDR: this.enablesXDR
                };
                t.pfx = this.pfx;
                t.key = this.key;
                t.passphrase = this.passphrase;
                t.cert = this.cert;
                t.ca = this.ca;
                t.ciphers = this.ciphers;
                t.rejectUnauthorized = this.rejectUnauthorized;
                var r = this.xhr = new n(t);
                var i = this;
                try {
                    r.open(this.method, this.uri, this.async);
                    try {
                        if (this.extraHeaders) {
                            r.setDisableHeaderCheck(true);
                            for (var o in this.extraHeaders) {
                                if (this.extraHeaders.hasOwnProperty(o)) {
                                    r.setRequestHeader(o, this.extraHeaders[o])
                                }
                            }
                        }
                    } catch (s) {}
                    if (this.supportsBinary) {
                        r.responseType = "arraybuffer"
                    }
                    if ("POST" === this.method) {
                        try {
                            if (this.isBinary) {
                                r.setRequestHeader("Content-type", "application/octet-stream")
                            } else {
                                r.setRequestHeader("Content-type", "text/plain;charset=UTF-8")
                            }
                        } catch (s) {}
                    }
                    try {
                        r.setRequestHeader("Accept", "*/*")
                    } catch (s) {}
                    if ("withCredentials"in r) {
                        r.withCredentials = true
                    }
                    if (this.requestTimeout) {
                        r.timeout = this.requestTimeout
                    }
                    if (this.hasXDR()) {
                        r.onload = function() {
                            i.onLoad()
                        }
                        ;
                        r.onerror = function() {
                            i.onError(r.responseText)
                        }
                    } else {
                        r.onreadystatechange = function() {
                            if (4 !== r.readyState)
                                return;
                            if (200 === r.status || 1223 === r.status) {
                                i.onLoad()
                            } else {
                                setTimeout(function() {
                                    i.onError(r.status)
                                }, 0)
                            }
                        }
                    }
// alert(this.data);
                    r.send(this.data)
                } catch (s) {
                    setTimeout(function() {
                        i.onError(s)
                    }, 0);
                    return
                }
                if (e.document) {
                    this.index = h.requestsCount++;
                    h.requests[this.index] = this
                }
            }
            ;
            h.prototype.onSuccess = function() {
                this.emit("success");
                this.cleanup()
            }
            ;
            h.prototype.onData = function(t) {
                this.emit("data", t);
                this.onSuccess()
            }
            ;
            h.prototype.onError = function(t) {
                this.emit("error", t);
                this.cleanup(true)
            }
            ;
            h.prototype.cleanup = function(t) {
                if ("undefined" === typeof this.xhr || null === this.xhr) {
                    return
                }
                if (this.hasXDR()) {
                    this.xhr.onload = this.xhr.onerror = c
                } else {
                    this.xhr.onreadystatechange = c
                }
                if (t) {
                    try {
                        this.xhr.abort()
                    } catch (r) {}
                }
                if (e.document) {
                    delete h.requests[this.index]
                }
                this.xhr = null
            }
            ;
            h.prototype.onLoad = function() {
                var t;
                try {
                    var e;
                    try {
                        e = this.xhr.getResponseHeader("Content-Type").split(";")[0]
                    } catch (r) {}
                    if (e === "application/octet-stream") {
                        t = this.xhr.response || this.xhr.responseText
                    } else {
                        if (!this.supportsBinary) {
                            t = this.xhr.responseText
                        } else {
                            try {
                                t = String.fromCharCode.apply(null, new Uint8Array(this.xhr.response))
                            } catch (r) {
                                var n = new Uint8Array(this.xhr.response);
                                var i = [];
                                for (var o = 0, s = n.length; o < s; o++) {
                                    i.push(n[o])
                                }
                                t = String.fromCharCode.apply(null, i)
                            }
                        }
                    }
                } catch (r) {
                    this.onError(r)
                }
                if (null != t) {
                    this.onData(t)
                }
            }
            ;
            h.prototype.hasXDR = function() {
                return "undefined" !== typeof e.XDomainRequest && !this.xs && this.enablesXDR
            }
            ;
            h.prototype.abort = function() {
                this.cleanup()
            }
            ;
            h.requestsCount = 0;
            h.requests = {};
            if (e.document) {
                if (e.attachEvent) {
                    e.attachEvent("onunload", u)
                } else if (e.addEventListener) {
                    e.addEventListener("beforeunload", u, false)
                }
            }
            function u() {
                for (var t in h.requests) {
                    if (h.requests.hasOwnProperty(t)) {
                        h.requests[t].abort()
                    }
                }
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e, r) {
        var n = r(19);
        var i = r(30);
        var o = r(20);
        var s = r(31);
        var a = r(32);
        var c = r(3)("engine.io-client:polling");
        t.exports = h;
        var f = function() {
            var t = r(15);
            var e = new t({
                xdomain: false
            });
            return null != e.responseType
        }();
        function h(t) {
            var e = t && t.forceBase64;
            if (!f || e) {
                this.supportsBinary = false
            }
            n.call(this, t)
        }
        s(h, n);
        h.prototype.name = "polling";
        h.prototype.doOpen = function() {
            this.poll()
        }
        ;
        h.prototype.pause = function(t) {
            var e = this;
            this.readyState = "pausing";
            function r() {
                e.readyState = "paused";
                t()
            }
            if (this.polling || !this.writable) {
                var n = 0;
                if (this.polling) {
                    n++;
                    this.once("pollComplete", function() {
                        --n || r()
                    })
                }
                if (!this.writable) {
                    n++;
                    this.once("drain", function() {
                        --n || r()
                    })
                }
            } else {
                r()
            }
        }
        ;
        h.prototype.poll = function() {
            this.polling = true;
            this.doPoll();
            this.emit("poll")
        }
        ;
        h.prototype.onData = function(t) {
            var e = this;
            var r = function(t, r, n) {
                if ("opening" === e.readyState) {
                    e.onOpen()
                }
                if ("close" === t.type) {
                    e.onClose();
                    return false
                }
                e.onPacket(t)
            };
            o.decodePayload(t, this.socket.binaryType, r);
            if ("closed" !== this.readyState) {
                this.polling = false;
                this.emit("pollComplete");
                if ("open" === this.readyState) {
                    this.poll()
                } else {}
            }
        }
        ;
        h.prototype.doClose = function() {
            var t = this;
            function e() {
                t.write([{
                    type: "close"
                }])
            }
            if ("open" === this.readyState) {
                e()
            } else {
                this.once("open", e)
            }
        }
        ;
        h.prototype.write = function(t) {
            var e = this;
            this.writable = false;
            var r = function() {
                e.writable = true;
                e.emit("drain")
            };
            o.encodePayload(t, this.supportsBinary, function(t) {
                e.doWrite(t, r)
            })
        }
        ;
        h.prototype.uri = function() {
            var t = this.query || {};
            var e = this.secure ? "https" : "http";
            var r = "";
            if (false !== this.timestampRequests) {
                t[this.timestampParam] = a()
            }
            if (!this.supportsBinary && !t.sid) {
                t.b64 = 1
            }
            t = i.encode(t);
            if (this.port && ("https" === e && Number(this.port) !== 443 || "http" === e && Number(this.port) !== 80)) {
                r = ":" + this.port
            }
            if (t.length) {
                t = "?" + t
            }
            var n = this.hostname.indexOf(":") !== -1;
            return e + "://" + (n ? "[" + this.hostname + "]" : this.hostname) + r + this.path + t
        }
    }
    , function(t, e, r) {
        var n = r(20);
        var i = r(29);
        t.exports = o;
        function o(t) {
            this.path = t.path;
            this.hostname = t.hostname;
            this.port = t.port;
            this.secure = t.secure;
            this.query = t.query;
            this.timestampParam = t.timestampParam;
            this.timestampRequests = t.timestampRequests;
            this.readyState = "";
            this.agent = t.agent || false;
            this.socket = t.socket;
            this.enablesXDR = t.enablesXDR;
            this.pfx = t.pfx;
            this.key = t.key;
            this.passphrase = t.passphrase;
            this.cert = t.cert;
            this.ca = t.ca;
            this.ciphers = t.ciphers;
            this.rejectUnauthorized = t.rejectUnauthorized;
            this.forceNode = t.forceNode;
            this.extraHeaders = t.extraHeaders;
            this.localAddress = t.localAddress
        }
        i(o.prototype);
        o.prototype.onError = function(t, e) {
            var r = new Error(t);
            r.type = "TransportError";
            r.description = e;
            this.emit("error", r);
            return this
        }
        ;
        o.prototype.open = function() {
            if ("closed" === this.readyState || "" === this.readyState) {
                this.readyState = "opening";
                this.doOpen()
            }
            return this
        }
        ;
        o.prototype.close = function() {
            if ("opening" === this.readyState || "open" === this.readyState) {
                this.doClose();
                this.onClose()
            }
            return this
        }
        ;
        o.prototype.send = function(t) {
            if ("open" === this.readyState) {
                this.write(t);
            } else {
                throw new Error("Transport not open")
            }
        }
        ;
        o.prototype.onOpen = function() {
            this.readyState = "open";
            this.writable = true;
            this.emit("open")
        }
        ;
        o.prototype.onData = function(t) {
            var e = n.decodePacket(t, this.socket.binaryType);
            this.onPacket(e)
        }
        ;
        o.prototype.onPacket = function(t) {
            this.emit("packet", t)
        }
        ;
        o.prototype.onClose = function() {
            this.readyState = "closed";
            this.emit("close")
        }
    }
    , function(t, e, r) {
        (function(t) {
            var n = r(21);
            var i = r(22);
            var o = r(23);
            var s = r(24);
            var a = r(25);
            var c;
            if (t && t.ArrayBuffer) {
                c = r(27)
            }
            var f = typeof navigator !== "undefined" && /Android/i.test(navigator.userAgent);
            var h = typeof navigator !== "undefined" && /PhantomJS/i.test(navigator.userAgent);
            var u = f || h;
            e.protocol = 3;
            var p = e.packets = {
                open: 0,
                close: 1,
                ping: 2,
                pong: 3,
                message: 4,
                upgrade: 5,
                noop: 6
            };
            var l = n(p);
            var d = {
                type: "error",
                data: "parser error"
            };
            var y = r(28);
            e.encodePacket = function(e, r, n, i) {
                if ("function" == typeof r) {
                    i = r;
                    r = false
                }
                if ("function" == typeof n) {
                    i = n;
                    n = null
                }
                var o = e.data === undefined ? undefined : e.data.buffer || e.data;
                if (t.ArrayBuffer && o instanceof ArrayBuffer) {
                    return m(e, r, i)
                } else if (y && o instanceof t.Blob) {
                    return b(e, r, i)
                }
                if (o && o.base64) {
                    return v(e, i)
                }
                var s = p[e.type];
                if (undefined !== e.data) {
                    s += n ? a.encode(String(e.data)) : String(e.data)
                }
                return i("" + s)
            }
            ;
            function v(t, r) {
                var n = "b" + e.packets[t.type] + t.data.data;
                return r(n)
            }
            function m(t, r, n) {
                if (!r) {
                    return e.encodeBase64Packet(t, n)
                }
                var i = t.data;
                var o = new Uint8Array(i);
                var s = new Uint8Array(1 + i.byteLength);
                s[0] = p[t.type];
                for (var a = 0; a < o.length; a++) {
                    s[a + 1] = o[a]
                }
                return n(s.buffer)
            }
            function g(t, r, n) {
                if (!r) {
                    return e.encodeBase64Packet(t, n)
                }
                var i = new FileReader;
                i.onload = function() {
                    t.data = i.result;
                    e.encodePacket(t, r, true, n)
                }
                ;
                return i.readAsArrayBuffer(t.data)
            }
            function b(t, r, n) {
                if (!r) {
                    return e.encodeBase64Packet(t, n)
                }
                if (u) {
                    return g(t, r, n)
                }
                var i = new Uint8Array(1);
                i[0] = p[t.type];
                var o = new y([i.buffer, t.data]);
                return n(o)
            }
            e.encodeBase64Packet = function(r, n) {
                var i = "b" + e.packets[r.type];
                if (y && r.data instanceof t.Blob) {
                    var o = new FileReader;
                    o.onload = function() {
                        var t = o.result.split(",")[1];
                        n(i + t)
                    }
                    ;
                    return o.readAsDataURL(r.data)
                }
                var s;
                try {
                    s = String.fromCharCode.apply(null, new Uint8Array(r.data))
                } catch (a) {
                    var c = new Uint8Array(r.data);
                    var f = new Array(c.length);
                    for (var h = 0; h < c.length; h++) {
                        f[h] = c[h]
                    }
                    s = String.fromCharCode.apply(null, f)
                }
                i += t.btoa(s);
                return n(i)
            }
            ;
            e.decodePacket = function(t, r, n) {
                if (t === undefined) {
                    return d
                }
                if (typeof t == "string") {
                    if (t.charAt(0) == "b") {
                        return e.decodeBase64Packet(t.substr(1), r)
                    }
                    if (n) {
                        t = k(t);
                        if (t === false) {
                            return d
                        }
                    }
                    var i = t.charAt(0);
                    if (Number(i) != i || !l[i]) {
                        return d
                    }
                    if (t.length > 1) {
                        return {
                            type: l[i],
                            data: t.substring(1)
                        }
                    } else {
                        return {
                            type: l[i]
                        }
                    }
                }
                var s = new Uint8Array(t);
                var i = s[0];
                var a = o(t, 1);
                if (y && r === "blob") {
                    a = new y([a])
                }
                return {
                    type: l[i],
                    data: a
                }
            }
            ;
            function k(t) {
                try {
                    t = a.decode(t)
                } catch (e) {
                    return false
                }
                return t
            }
            e.decodeBase64Packet = function(t, e) {
                var r = l[t.charAt(0)];
                if (!c) {
                    return {
                        type: r,
                        data: {
                            base64: true,
                            data: t.substr(1)
                        }
                    }
                }
                var n = c.decode(t.substr(1));
                if (e === "blob" && y) {
                    n = new y([n])
                }
                return {
                    type: r,
                    data: n
                }
            }
            ;
            e.encodePayload = function(t, r, n) {
                if (typeof r == "function") {
                    n = r;
                    r = null
                }
                var o = i(t);
                if (r && o) {
                    if (y && !u) {
                        return e.encodePayloadAsBlob(t, n)
                    }
                    return e.encodePayloadAsArrayBuffer(t, n)
                }
                if (!t.length) {
                    return n("0:")
                }
                function s(t) {
                    return t.length + ":" + t
                }
                function a(t, n) {
                    e.encodePacket(t, !o ? false : r, true, function(t) {
                        n(null, s(t))
                    })
                }
                w(t, a, function(t, e) {
                    return n(e.join(""))
                })
            }
            ;
            function w(t, e, r) {
                var n = new Array(t.length);
                var i = s(t.length, r);
                var o = function(t, r, i) {
                    e(r, function(e, r) {
                        n[t] = r;
                        i(e, n)
                    })
                };
                for (var a = 0; a < t.length; a++) {
                    o(a, t[a], i)
                }
            }
            e.decodePayload = function(t, r, n) {
                if (typeof t != "string") {
                    return e.decodePayloadAsBinary(t, r, n)
                }
                if (typeof r === "function") {
                    n = r;
                    r = null
                }
                var i;
                if (t == "") {
                    return n(d, 0, 1)
                }
                var o = "", s, a;
                for (var c = 0, f = t.length; c < f; c++) {
                    var h = t.charAt(c);
                    if (":" != h) {
                        o += h
                    } else {
                        if ("" == o || o != (s = Number(o))) {
                            return n(d, 0, 1)
                        }
                        a = t.substr(c + 1, s);
                        if (o != a.length) {
                            return n(d, 0, 1)
                        }
                        if (a.length) {
                            i = e.decodePacket(a, r, true);
                            if (d.type == i.type && d.data == i.data) {
                                return n(d, 0, 1)
                            }
                            var u = n(i, c + s, f);
                            if (false === u)
                                return
                        }
                        c += s;
                        o = ""
                    }
                }
                if (o != "") {
                    return n(d, 0, 1)
                }
            }
            ;
            e.encodePayloadAsArrayBuffer = function(t, r) {
                if (!t.length) {
                    return r(new ArrayBuffer(0))
                }
                function n(t, r) {
                    e.encodePacket(t, true, true, function(t) {
                        return r(null, t)
                    })
                }
                w(t, n, function(t, e) {
                    var n = e.reduce(function(t, e) {
                        var r;
                        if (typeof e === "string") {
                            r = e.length
                        } else {
                            r = e.byteLength
                        }
                        return t + r.toString().length + r + 2
                    }, 0);
                    var i = new Uint8Array(n);
                    var o = 0;
                    e.forEach(function(t) {
                        var e = typeof t === "string";
                        var r = t;
                        if (e) {
                            var n = new Uint8Array(t.length);
                            for (var s = 0; s < t.length; s++) {
                                n[s] = t.charCodeAt(s)
                            }
                            r = n.buffer
                        }
                        if (e) {
                            i[o++] = 0
                        } else {
                            i[o++] = 1
                        }
                        var a = r.byteLength.toString();
                        for (var s = 0; s < a.length; s++) {
                            i[o++] = parseInt(a[s])
                        }
                        i[o++] = 255;
                        var n = new Uint8Array(r);
                        for (var s = 0; s < n.length; s++) {
                            i[o++] = n[s]
                        }
                    });
                    return r(i.buffer)
                })
            }
            ;
            e.encodePayloadAsBlob = function(t, r) {
                function n(t, r) {
                    e.encodePacket(t, true, true, function(t) {
                        var e = new Uint8Array(1);
                        e[0] = 1;
                        if (typeof t === "string") {
                            var n = new Uint8Array(t.length);
                            for (var i = 0; i < t.length; i++) {
                                n[i] = t.charCodeAt(i)
                            }
                            t = n.buffer;
                            e[0] = 0
                        }
                        var o = t instanceof ArrayBuffer ? t.byteLength : t.size;
                        var s = o.toString();
                        var a = new Uint8Array(s.length + 1);
                        for (var i = 0; i < s.length; i++) {
                            a[i] = parseInt(s[i])
                        }
                        a[s.length] = 255;
                        if (y) {
                            var c = new y([e.buffer, a.buffer, t]);
                            r(null, c)
                        }
                    })
                }
                w(t, n, function(t, e) {
                    return r(new y(e))
                })
            }
            ;
            e.decodePayloadAsBinary = function(t, r, n) {
                if (typeof r === "function") {
                    n = r;
                    r = null
                }
                var i = t;
                var s = [];
                var a = false;
                while (i.byteLength > 0) {
                    var c = new Uint8Array(i);
                    var f = c[0] === 0;
                    var h = "";
                    for (var u = 1; ; u++) {
                        if (c[u] == 255)
                            break;
                        if (h.length > 310) {
                            a = true;
                            break
                        }
                        h += c[u]
                    }
                    if (a)
                        return n(d, 0, 1);
                    i = o(i, 2 + h.length);
                    h = parseInt(h);
                    var p = o(i, 0, h);
                    if (f) {
                        try {
                            p = String.fromCharCode.apply(null, new Uint8Array(p))
                        } catch (l) {
                            var y = new Uint8Array(p);
                            p = "";
                            for (var u = 0; u < y.length; u++) {
                                p += String.fromCharCode(y[u])
                            }
                        }
                    }
                    s.push(p);
                    i = o(i, h)
                }
                var v = s.length;
                s.forEach(function(t, i) {
                    n(e.decodePacket(t, r, true), i, v)
                })
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e) {
        t.exports = Object.keys || function r(t) {
            var e = [];
            var r = Object.prototype.hasOwnProperty;
            for (var n in t) {
                if (r.call(t, n)) {
                    e.push(n)
                }
            }
            return e
        }
    }
    , function(t, e, r) {
        (function(e) {
            var n = r(8);
            t.exports = i;
            function i(t) {
                function r(t) {
                    if (!t)
                        return false;
                    if (e.Buffer && e.Buffer.isBuffer && e.Buffer.isBuffer(t) || e.ArrayBuffer && t instanceof ArrayBuffer || e.Blob && t instanceof Blob || e.File && t instanceof File) {
                        return true
                    }
                    if (n(t)) {
                        for (var i = 0; i < t.length; i++) {
                            if (r(t[i])) {
                                return true
                            }
                        }
                    } else if (t && "object" == typeof t) {
                        if (t.toJSON && "function" == typeof t.toJSON) {
                            t = t.toJSON()
                        }
                        for (var o in t) {
                            if (Object.prototype.hasOwnProperty.call(t, o) && r(t[o])) {
                                return true
                            }
                        }
                    }
                    return false
                }
                return r(t)
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e) {
        t.exports = function(t, e, r) {
            var n = t.byteLength;
            e = e || 0;
            r = r || n;
            if (t.slice) {
                return t.slice(e, r)
            }
            if (e < 0) {
                e += n
            }
            if (r < 0) {
                r += n
            }
            if (r > n) {
                r = n
            }
            if (e >= n || e >= r || n === 0) {
                return new ArrayBuffer(0)
            }
            var i = new Uint8Array(t);
            var o = new Uint8Array(r - e);
            for (var s = e, a = 0; s < r; s++,
            a++) {
                o[a] = i[s]
            }
            return o.buffer
        }
    }
    , function(t, e) {
        t.exports = r;
        function r(t, e, r) {
            var i = false;
            r = r || n;
            o.count = t;
            return t === 0 ? e() : o;
            function o(t, n) {
                if (o.count <= 0) {
                    throw new Error("after called too many times")
                }
                --o.count;
                if (t) {
                    i = true;
                    e(t);
                    e = r
                } else if (o.count === 0 && !i) {
                    e(null, n)
                }
            }
        }
        function n() {}
    }
    , function(t, e, r) {
        var n;
        (function(t, i) {
            (function(o) {
                var s = typeof e == "object" && e;
                var a = typeof t == "object" && t && t.exports == s && t;
                var c = typeof i == "object" && i;
                if (c.global === c || c.window === c) {
                    o = c
                }
                var f = String.fromCharCode;
                function h(t) {
                    var e = [];
                    var r = 0;
                    var n = t.length;
                    var i;
                    var o;
                    while (r < n) {
                        i = t.charCodeAt(r++);
                        if (i >= 55296 && i <= 56319 && r < n) {
                            o = t.charCodeAt(r++);
                            if ((o & 64512) == 56320) {
                                e.push(((i & 1023) << 10) + (o & 1023) + 65536)
                            } else {
                                e.push(i);
                                r--
                            }
                        } else {
                            e.push(i)
                        }
                    }
                    return e
                }
                function u(t) {
                    var e = t.length;
                    var r = -1;
                    var n;
                    var i = "";
                    while (++r < e) {
                        n = t[r];
                        if (n > 65535) {
                            n -= 65536;
                            i += f(n >>> 10 & 1023 | 55296);
                            n = 56320 | n & 1023
                        }
                        i += f(n)
                    }
                    return i
                }
                function p(t, e) {
                    return f(t >> e & 63 | 128)
                }
                function l(t) {
                    if ((t & 4294967168) == 0) {
                        return f(t)
                    }
                    var e = "";
                    if ((t & 4294965248) == 0) {
                        e = f(t >> 6 & 31 | 192)
                    } else if ((t & 4294901760) == 0) {
                        e = f(t >> 12 & 15 | 224);
                        e += p(t, 6)
                    } else if ((t & 4292870144) == 0) {
                        e = f(t >> 18 & 7 | 240);
                        e += p(t, 12);
                        e += p(t, 6)
                    }
                    e += f(t & 63 | 128);
                    return e
                }
                function d(t) {
                    var e = h(t);
                    var r = e.length;
                    var n = -1;
                    var i;
                    var o = "";
                    while (++n < r) {
                        i = e[n];
                        o += l(i)
                    }
                    return o
                }
                function y() {
                    if (b >= g) {
                        throw Error("Invalid byte index")
                    }
                    var t = m[b] & 255;
                    b++;
                    if ((t & 192) == 128) {
                        return t & 63
                    }
                    throw Error("Invalid continuation byte")
                }
                function v() {
                    var t;
                    var e;
                    var r;
                    var n;
                    var i;
                    if (b > g) {
                        throw Error("Invalid byte index")
                    }
                    if (b == g) {
                        return false
                    }
                    t = m[b] & 255;
                    b++;
                    if ((t & 128) == 0) {
                        return t
                    }
                    if ((t & 224) == 192) {
                        var e = y();
                        i = (t & 31) << 6 | e;
                        if (i >= 128) {
                            return i
                        } else {
                            throw Error("Invalid continuation byte")
                        }
                    }
                    if ((t & 240) == 224) {
                        e = y();
                        r = y();
                        i = (t & 15) << 12 | e << 6 | r;
                        if (i >= 2048) {
                            return i
                        } else {
                            throw Error("Invalid continuation byte")
                        }
                    }
                    if ((t & 248) == 240) {
                        e = y();
                        r = y();
                        n = y();
                        i = (t & 15) << 18 | e << 12 | r << 6 | n;
                        if (i >= 65536 && i <= 1114111) {
                            return i
                        }
                    }
                    throw Error("Invalid WTF-8 detected")
                }
                var m;
                var g;
                var b;
                function k(t) {
                    m = h(t);
                    g = m.length;
                    b = 0;
                    var e = [];
                    var r;
                    while ((r = v()) !== false) {
                        e.push(r)
                    }
                    return u(e)
                }
                var w = {
                    version: "1.0.0",
                    encode: d,
                    decode: k
                };
                if (true) {
                    !(n = function() {
                        return w
                    }
                    .call(e, r, e, t),
                    n !== undefined && (t.exports = n))
                } else if (s && !s.nodeType) {
                    if (a) {
                        a.exports = w
                    } else {
                        var x = {};
                        var A = x.hasOwnProperty;
                        for (var B in w) {
                            A.call(w, B) && (s[B] = w[B])
                        }
                    }
                } else {
                    o.wtf8 = w
                }
            }
            )(this)
        }
        ).call(e, r(26)(t), function() {
            return this
        }())
    }
    , function(t, e) {
        t.exports = function(t) {
            if (!t.webpackPolyfill) {
                t.deprecate = function() {}
                ;
                t.paths = [];
                t.children = [];
                t.webpackPolyfill = 1
            }
            return t
        }
    }
    , function(t, e) {
        (function() {
            "use strict";
            var t = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
            var r = new Uint8Array(256);
            for (var n = 0; n < t.length; n++) {
                r[t.charCodeAt(n)] = n
            }
            e.encode = function(e) {
                var r = new Uint8Array(e), n, i = r.length, o = "";
                for (n = 0; n < i; n += 3) {
                    o += t[r[n] >> 2];
                    o += t[(r[n] & 3) << 4 | r[n + 1] >> 4];
                    o += t[(r[n + 1] & 15) << 2 | r[n + 2] >> 6];
                    o += t[r[n + 2] & 63]
                }
                if (i % 3 === 2) {
                    o = o.substring(0, o.length - 1) + "="
                } else if (i % 3 === 1) {
                    o = o.substring(0, o.length - 2) + "=="
                }
                return o
            }
            ;
            e.decode = function(t) {
                var e = t.length * .75, n = t.length, i, o = 0, s, a, c, f;
                if (t[t.length - 1] === "=") {
                    e--;
                    if (t[t.length - 2] === "=") {
                        e--
                    }
                }
                var h = new ArrayBuffer(e)
                  , u = new Uint8Array(h);
                for (i = 0; i < n; i += 4) {
                    s = r[t.charCodeAt(i)];
                    a = r[t.charCodeAt(i + 1)];
                    c = r[t.charCodeAt(i + 2)];
                    f = r[t.charCodeAt(i + 3)];
                    u[o++] = s << 2 | a >> 4;
                    u[o++] = (a & 15) << 4 | c >> 2;
                    u[o++] = (c & 3) << 6 | f & 63
                }
                return h
            }
        }
        )()
    }
    , function(t, e) {
        (function(e) {
            var r = e.BlobBuilder || e.WebKitBlobBuilder || e.MSBlobBuilder || e.MozBlobBuilder;
            var n = function() {
                try {
                    var t = new Blob(["hi"]);
                    return t.size === 2
                } catch (e) {
                    return false
                }
            }();
            var i = n && function() {
                try {
                    var t = new Blob([new Uint8Array([1, 2])]);
                    return t.size === 2
                } catch (e) {
                    return false
                }
            }();
            var o = r && r.prototype.append && r.prototype.getBlob;
            function s(t) {
                for (var e = 0; e < t.length; e++) {
                    var r = t[e];
                    if (r.buffer instanceof ArrayBuffer) {
                        var n = r.buffer;
                        if (r.byteLength !== n.byteLength) {
                            var i = new Uint8Array(r.byteLength);
                            i.set(new Uint8Array(n,r.byteOffset,r.byteLength));
                            n = i.buffer
                        }
                        t[e] = n
                    }
                }
            }
            function a(t, e) {
                e = e || {};
                var n = new r;
                s(t);
                for (var i = 0; i < t.length; i++) {
                    n.append(t[i])
                }
                return e.type ? n.getBlob(e.type) : n.getBlob()
            }
            function c(t, e) {
                s(t);
                return new Blob(t,e || {})
            }
            t.exports = function() {
                if (n) {
                    return i ? e.Blob : c
                } else if (o) {
                    return a
                } else {
                    return undefined
                }
            }()
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e, r) {
        if (true) {
            t.exports = n
        }
        function n(t) {
            if (t)
                return i(t)
        }
        function i(t) {
            for (var e in n.prototype) {
                t[e] = n.prototype[e]
            }
            return t
        }
        n.prototype.on = n.prototype.addEventListener = function(t, e) {
            this._callbacks = this._callbacks || {};
            (this._callbacks["$" + t] = this._callbacks["$" + t] || []).push(e);
            return this
        }
        ;
        n.prototype.once = function(t, e) {
            function r() {
                this.off(t, r);
                e.apply(this, arguments)
            }
            r.fn = e;
            this.on(t, r);
            return this
        }
        ;
        n.prototype.off = n.prototype.removeListener = n.prototype.removeAllListeners = n.prototype.removeEventListener = function(t, e) {
            this._callbacks = this._callbacks || {};
            if (0 == arguments.length) {
                this._callbacks = {};
                return this
            }
            var r = this._callbacks["$" + t];
            if (!r)
                return this;
            if (1 == arguments.length) {
                delete this._callbacks["$" + t];
                return this
            }
            var n;
            for (var i = 0; i < r.length; i++) {
                n = r[i];
                if (n === e || n.fn === e) {
                    r.splice(i, 1);
                    break
                }
            }
            return this
        }
        ;
        n.prototype.emit = function(t) {
            this._callbacks = this._callbacks || {};
            var e = [].slice.call(arguments, 1)
              , r = this._callbacks["$" + t];
            if (r) {
                r = r.slice(0);
                for (var n = 0, i = r.length; n < i; ++n) {
                    r[n].apply(this, e)
                }
            }
            return this
        }
        ;
        n.prototype.listeners = function(t) {
            this._callbacks = this._callbacks || {};
            return this._callbacks["$" + t] || []
        }
        ;
        n.prototype.hasListeners = function(t) {
            return !!this.listeners(t).length
        }
    }
    , function(t, e) {
        e.encode = function(t) {
            var e = "";
            for (var r in t) {
                if (t.hasOwnProperty(r)) {
                    if (e.length)
                        e += "&";
                    e += encodeURIComponent(r) + "=" + encodeURIComponent(t[r])
                }
            }
            return e
        }
        ;
        e.decode = function(t) {
            var e = {};
            var r = t.split("&");
            for (var n = 0, i = r.length; n < i; n++) {
                var o = r[n].split("=");
                e[decodeURIComponent(o[0])] = decodeURIComponent(o[1])
            }
            return e
        }
    }
    , function(t, e) {
        t.exports = function(t, e) {
            var r = function() {};
            r.prototype = e.prototype;
            t.prototype = new r;
            t.prototype.constructor = t
        }
    }
    , function(t, e) {
        "use strict";
        var r = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_".split(""), n = 64, i = {}, o = 0, s = 0, a;
        function c(t) {
            var e = "";
            do {
                e = r[t % n] + e;
                t = Math.floor(t / n)
            } while (t > 0);return e
        }
        function f(t) {
            var e = 0;
            for (s = 0; s < t.length; s++) {
                e = e * n + i[t.charAt(s)]
            }
            return e
        }
        function h() {
            var t = c(+new Date);
            if (t !== a)
                return o = 0,
                a = t;
            return t + "." + c(o++)
        }
        for (; s < n; s++)
            i[r[s]] = s;
        h.encode = c;
        h.decode = f;
        t.exports = h
    }
    , function(t, e, r) {
        (function(e) {
            var n = r(18);
            var i = r(31);
            t.exports = f;
            var o = /\n/g;
            var s = /\\n/g;
            var a;
            function c() {}
            function f(t) {
                n.call(this, t);
                this.query = this.query || {};
                if (!a) {
                    if (!e.___eio)
                        e.___eio = [];
                    a = e.___eio
                }
                this.index = a.length;
                var r = this;
                a.push(function(t) {
                    r.onData(t)
                });
                this.query.j = this.index;
                if (e.document && e.addEventListener) {
                    e.addEventListener("beforeunload", function() {
                        if (r.script)
                            r.script.onerror = c
                    }, false)
                }
            }
            i(f, n);
            f.prototype.supportsBinary = false;
            f.prototype.doClose = function() {
                if (this.script) {
                    this.script.parentNode.removeChild(this.script);
                    this.script = null
                }
                if (this.form) {
                    this.form.parentNode.removeChild(this.form);
                    this.form = null;
                    this.iframe = null
                }
                n.prototype.doClose.call(this)
            }
            ;
            f.prototype.doPoll = function() {
                var t = this;
                var e = document.createElement("script");
                if (this.script) {
                    this.script.parentNode.removeChild(this.script);
                    this.script = null
                }
                e.async = true;
                e.src = this.uri();
                e.onerror = function(e) {
                    t.onError("jsonp poll error", e)
                }
                ;
                var r = document.getElementsByTagName("script")[0];
                if (r) {
                    r.parentNode.insertBefore(e, r)
                } else {
                    (document.head || document.body).appendChild(e)
                }
                this.script = e;
                var n = "undefined" !== typeof navigator && /gecko/i.test(navigator.userAgent);
                if (n) {
                    setTimeout(function() {
                        var t = document.createElement("iframe");
                        document.body.appendChild(t);
                        document.body.removeChild(t)
                    }, 100)
                }
            }
            ;
            f.prototype.doWrite = function(t, e) {
                var r = this;
                if (!this.form) {
                    var n = document.createElement("form");
                    var i = document.createElement("textarea");
                    var a = this.iframeId = "eio_iframe_" + this.index;
                    var c;
                    n.className = "socketio";
                    n.style.position = "absolute";
                    n.style.top = "-1000px";
                    n.style.left = "-1000px";
                    n.target = a;
                    n.method = "POST";
                    n.setAttribute("accept-charset", "utf-8");
                    i.name = "d";
                    n.appendChild(i);
                    document.body.appendChild(n);
                    this.form = n;
                    this.area = i
                }
                this.form.action = this.uri();
                function f() {
                    h();
                    e()
                }
                function h() {
                    if (r.iframe) {
                        try {
                            r.form.removeChild(r.iframe)
                        } catch (t) {
                            r.onError("jsonp polling iframe removal error", t)
                        }
                    }
                    try {
                        var e = '<iframe src="javascript:0" name="' + r.iframeId + '">';
                        c = document.createElement(e)
                    } catch (t) {
                        c = document.createElement("iframe");
                        c.name = r.iframeId;
                        c.src = "javascript:0"
                    }
                    c.id = r.iframeId;
                    r.form.appendChild(c);
                    r.iframe = c
                }
                h();
                t = t.replace(s, "\\\n");
                this.area.value = t.replace(o, "\\n");
                try {
                    this.form.submit()
                } catch (u) {}
                if (this.iframe.attachEvent) {
                    this.iframe.onreadystatechange = function() {
                        if (r.iframe.readyState === "complete") {
                            f()
                        }
                    }
                } else {
                    this.iframe.onload = f
                }
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e, r) {
        (function(e) {
            var n = r(19);
            var i = r(20);
            var o = r(30);
            var s = r(31);
            var a = r(32);
            var c = r(3)("engine.io-client:websocket");
            var f = e.WebSocket || e.MozWebSocket;
            var h;
            if (typeof window === "undefined") {
                try {
                    h = r(35)
                } catch (u) {}
            }
            var p = f;
            if (!p && typeof window === "undefined") {
                p = h
            }
            t.exports = l;
            function l(t) {
                var e = t && t.forceBase64;
                if (e) {
                    this.supportsBinary = false
                }
                this.perMessageDeflate = t.perMessageDeflate;
                this.usingBrowserWebSocket = f && !t.forceNode;
                if (!this.usingBrowserWebSocket) {
                    p = h
                }
                n.call(this, t)
            }
            s(l, n);
            l.prototype.name = "websocket";
            l.prototype.supportsBinary = true;
            l.prototype.doOpen = function() {
                if (!this.check()) {
                    return
                }
                var t = this.uri();
                var e = void 0;
                var r = {
                    agent: this.agent,
                    perMessageDeflate: this.perMessageDeflate
                };
                r.pfx = this.pfx;
                r.key = this.key;
                r.passphrase = this.passphrase;
                r.cert = this.cert;
                r.ca = this.ca;
                r.ciphers = this.ciphers;
                r.rejectUnauthorized = this.rejectUnauthorized;
                if (this.extraHeaders) {
                    r.headers = this.extraHeaders
                }
                if (this.localAddress) {
                    r.localAddress = this.localAddress
                }
                try {
                    this.ws = this.usingBrowserWebSocket ? new p(t) : new p(t,e,r)
                } catch (n) {
                    return this.emit("error", n)
                }
                if (this.ws.binaryType === undefined) {
                    this.supportsBinary = false
                }
                if (this.ws.supports && this.ws.supports.binary) {
                    this.supportsBinary = true;
                    this.ws.binaryType = "nodebuffer"
                } else {
                    this.ws.binaryType = "arraybuffer"
                }
                this.addEventListeners()
            }
            ;
            l.prototype.addEventListeners = function() {
                var t = this;
                this.ws.onopen = function() {
                    t.onOpen()
                }
                ;
                this.ws.onclose = function() {
                    t.onClose()
                }
                ;
                this.ws.onmessage = function(e) {
                    t.onData(e.data)
                }
                ;
                this.ws.onerror = function(e) {
                    t.onError("websocket error", e)
                }
            }
            ;
            l.prototype.write = function(t) {
                var r = this;
                this.writable = false;
                var n = t.length;
                for (var o = 0, s = n; o < s; o++) {
                    (function(t) {
                        i.encodePacket(t, r.supportsBinary, function(i) {
                            if (!r.usingBrowserWebSocket) {
                                var o = {};
                                if (t.options) {
                                    o.compress = t.options.compress
                                }
                                if (r.perMessageDeflate) {
                                    var s = "string" === typeof i ? e.Buffer.byteLength(i) : i.length;
                                    if (s < r.perMessageDeflate.threshold) {
                                        o.compress = false
                                    }
                                }
                            }
                            try {
                                if (r.usingBrowserWebSocket) {
                                    r.ws.send(i)
                                } else {
                                    r.ws.send(i, o)
                                }
                            } catch (c) {}
                            --n || a()
                        })
                    }
                    )(t[o])
                }
                function a() {
                    r.emit("flush");
                    setTimeout(function() {
                        r.writable = true;
                        r.emit("drain")
                    }, 0)
                }
            }
            ;
            l.prototype.onClose = function() {
                n.prototype.onClose.call(this)
            }
            ;
            l.prototype.doClose = function() {
                if (typeof this.ws !== "undefined") {
                    this.ws.close()
                }
            }
            ;
            l.prototype.uri = function() {
                var t = this.query || {};
                var e = this.secure ? "wss" : "ws";
                var r = "";
                if (this.port && ("wss" === e && Number(this.port) !== 443 || "ws" === e && Number(this.port) !== 80)) {
                    r = ":" + this.port
                }
                if (this.timestampRequests) {
                    t[this.timestampParam] = a()
                }
                if (!this.supportsBinary) {
                    t.b64 = 1
                }
                t = o.encode(t);
                if (t.length) {
                    t = "?" + t
                }
                var n = this.hostname.indexOf(":") !== -1;
                return e + "://" + (n ? "[" + this.hostname + "]" : this.hostname) + r + this.path + t
            }
            ;
            l.prototype.check = function() {
                return !!p && !("__initialize"in p && this.name === l.prototype.name)
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e) {}
    , function(t, e) {
        var r = [].indexOf;
        t.exports = function(t, e) {
            if (r)
                return t.indexOf(e);
            for (var n = 0; n < t.length; ++n) {
                if (t[n] === e)
                    return n
            }
            return -1
        }
    }
    , function(t, e) {
        (function(e) {
            var r = /^[\],:{}\s]*$/;
            var n = /\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g;
            var i = /"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g;
            var o = /(?:^|:|,)(?:\s*\[)+/g;
            var s = /^\s+/;
            var a = /\s+$/;
            t.exports = function c(t) {
                if ("string" != typeof t || !t) {
                    return null
                }
                t = t.replace(s, "").replace(a, "");
                if (e.JSON && JSON.parse) {
                    return JSON.parse(t)
                }
                if (r.test(t.replace(n, "@").replace(i, "]").replace(o, ""))) {
                    return new Function("return " + t)()
                }
            }
        }
        ).call(e, function() {
            return this
        }())
    }
    , function(t, e, r) {
        "use strict";
        var n = r(4);
        var i = r(29);
        var o = r(39);
        var s = r(40);
        var a = r(41);
        var c = r(3)("socket.io-client:socket");
        var f = r(22);
        t.exports = e = p;
        var h = {
            connect: 1,
            connect_error: 1,
            connect_timeout: 1,
            connecting: 1,
            disconnect: 1,
            error: 1,
            reconnect: 1,
            reconnect_attempt: 1,
            reconnect_failed: 1,
            reconnect_error: 1,
            reconnecting: 1,
            ping: 1,
            pong: 1
        };
        var u = i.prototype.emit;
        function p(t, e, r) {
            this.io = t;
            this.nsp = e;
            this.json = this;
            this.ids = 0;
            this.acks = {};
            this.receiveBuffer = [];
            this.sendBuffer = [];
            this.connected = false;
            this.disconnected = true;
            if (r && r.query) {
                this.query = r.query
            }
            if (this.io.autoConnect)
                this.open()
        }
        i(p.prototype);
        p.prototype.subEvents = function() {
            if (this.subs)
                return;
            var t = this.io;
            this.subs = [s(t, "open", a(this, "onopen")), s(t, "packet", a(this, "onpacket")), s(t, "close", a(this, "onclose"))]
        }
        ;
        p.prototype.open = p.prototype.connect = function() {
            if (this.connected)
                return this;
            this.subEvents();
            this.io.open();
            if ("open" === this.io.readyState)
                this.onopen();
            this.emit("connecting");
            return this
        }
        ;
        p.prototype.send = function() {
            var t = o(arguments);
            t.unshift("message");
            this.emit.apply(this, t);
            return this
        }
        ;
        p.prototype.emit = function(t) {
            if (h.hasOwnProperty(t)) {
                u.apply(this, arguments);
                return this
            }
            var e = o(arguments);
            var r = n.EVENT;
            if (f(e)) {
                r = n.BINARY_EVENT
            }
            var i = {
                type: r,
                data: e
            };
            i.options = {};
            i.options.compress = !this.flags || false !== this.flags.compress;
            if ("function" === typeof e[e.length - 1]) {
                this.acks[this.ids] = e.pop();
                i.id = this.ids++
            }
            if (this.connected) {
                this.packet(i)
            } else {
                this.sendBuffer.push(i)
            }
            delete this.flags;
            return this
        }
        ;
        p.prototype.packet = function(t) {
            t.nsp = this.nsp;
            this.io.packet(t)
        }
        ;
        p.prototype.onopen = function() {
            if ("/" !== this.nsp) {
                if (this.query) {
                    this.packet({
                        type: n.CONNECT,
                        query: this.query
                    })
                } else {
                    this.packet({
                        type: n.CONNECT
                    })
                }
            }
        }
        ;
        p.prototype.onclose = function(t) {
            this.connected = false;
            this.disconnected = true;
            delete this.id;
            this.emit("disconnect", t)
        }
        ;
        p.prototype.onpacket = function(t) {
            if (t.nsp !== this.nsp)
                return;
            switch (t.type) {
            case n.CONNECT:
                this.onconnect();
                break;
            case n.EVENT:
                this.onevent(t);
                break;
            case n.BINARY_EVENT:
                this.onevent(t);
                break;
            case n.ACK:
                this.onack(t);
                break;
            case n.BINARY_ACK:
                this.onack(t);
                break;
            case n.DISCONNECT:
                this.ondisconnect();
                break;
            case n.ERROR:
                this.emit("error", t.data);
                break
            }
        }
        ;
        p.prototype.onevent = function(t) {
            var e = t.data || [];
            if (null != t.id) {
                e.push(this.ack(t.id))
            }
            if (this.connected) {
                u.apply(this, e)
            } else {
                this.receiveBuffer.push(e)
            }
        }
        ;
        p.prototype.ack = function(t) {
            var e = this;
            var r = false;
            return function() {
                if (r)
                    return;
                r = true;
                var i = o(arguments);
                var s = f(i) ? n.BINARY_ACK : n.ACK;
                e.packet({
                    type: s,
                    id: t,
                    data: i
                })
            }
        }
        ;
        p.prototype.onack = function(t) {
            var e = this.acks[t.id];
            if ("function" === typeof e) {
                e.apply(this, t.data);
                delete this.acks[t.id]
            } else {}
        }
        ;
        p.prototype.onconnect = function() {
            this.connected = true;
            this.disconnected = false;
            this.emit("connect");
            this.emitBuffered()
        }
        ;
        p.prototype.emitBuffered = function() {
            var t;
            for (t = 0; t < this.receiveBuffer.length; t++) {
                u.apply(this, this.receiveBuffer[t])
            }
            this.receiveBuffer = [];
            for (t = 0; t < this.sendBuffer.length; t++) {
                this.packet(this.sendBuffer[t])
            }
            this.sendBuffer = []
        }
        ;
        p.prototype.ondisconnect = function() {
            this.destroy();
            this.onclose("io server disconnect")
        }
        ;
        p.prototype.destroy = function() {
            if (this.subs) {
                for (var t = 0; t < this.subs.length; t++) {
                    this.subs[t].destroy()
                }
                this.subs = null
            }
            this.io.destroy(this)
        }
        ;
        p.prototype.close = p.prototype.disconnect = function() {
            if (this.connected) {
                this.packet({
                    type: n.DISCONNECT
                })
            }
            this.destroy();
            if (this.connected) {
                this.onclose("io client disconnect")
            }
            return this
        }
        ;
        p.prototype.compress = function(t) {
            this.flags = this.flags || {};
            this.flags.compress = t;
            return this
        }
    }
    , function(t, e) {
        t.exports = r;
        function r(t, e) {
            var r = [];
            e = e || 0;
            for (var n = e || 0; n < t.length; n++) {
                r[n - e] = t[n]
            }
            return r
        }
    }
    , function(t, e) {
        "use strict";
        t.exports = r;
        function r(t, e, r) {
            t.on(e, r);
            return {
                destroy: function n() {
                    t.removeListener(e, r)
                }
            }
        }
    }
    , function(t, e) {
        var r = [].slice;
        t.exports = function(t, e) {
            if ("string" == typeof e)
                e = t[e];
            if ("function" != typeof e)
                throw new Error("bind() requires a function");
            var n = r.call(arguments, 2);
            return function() {
                return e.apply(t, n.concat(r.call(arguments)))
            }
        }
    }
    , function(t, e) {
        t.exports = r;
        function r(t) {
            t = t || {};
            this.ms = t.min || 100;
            this.max = t.max || 1e4;
            this.factor = t.factor || 2;
            this.jitter = t.jitter > 0 && t.jitter <= 1 ? t.jitter : 0;
            this.attempts = 0
        }
        r.prototype.duration = function() {
            var t = this.ms * Math.pow(this.factor, this.attempts++);
            if (this.jitter) {
                var e = Math.random();
                var r = Math.floor(e * this.jitter * t);
                t = (Math.floor(e * 10) & 1) == 0 ? t - r : t + r
            }
            return Math.min(t, this.max) | 0
        }
        ;
        r.prototype.reset = function() {
            this.attempts = 0
        }
        ;
        r.prototype.setMin = function(t) {
            this.ms = t
        }
        ;
        r.prototype.setMax = function(t) {
            this.max = t
        }
        ;
        r.prototype.setJitter = function(t) {
            this.jitter = t
        }
    }
    ])
});
var goEasy_logs = [];
var goEasy_debug = false;
function log(e) {
    if (goEasy_debug) {
        var n = (new Date).formatDate("yy-MM-dd hh:mm:ss.S");
        goEasy_logs.push(n + " " + e + "\n")
    }
}
!function(e) {
    if ("object" == typeof exports && "undefined" != typeof module)
        module.exports = e();
    else if ("function" == typeof define && define.amd)
        define([], e);
    else {
        var n;
        "undefined" != typeof window ? n = window : "undefined" != typeof global ? n = global : "undefined" != typeof self && (n = self),
        n.uuid = e()
    }
}(function() {
    return function e(n, t, i) {
        function o(s, u) {
            if (!t[s]) {
                if (!n[s]) {
                    var a = "function" == typeof require && require;
                    if (!u && a)
                        return a(s, !0);
                    if (r)
                        return r(s, !0);
                    var c = new Error("Cannot find module '" + s + "'");
                    throw c.code = "MODULE_NOT_FOUND",
                    c
                }
                var l = t[s] = {
                    exports: {}
                };
                n[s][0].call(l.exports, function(e) {
                    var t = n[s][1][e];
                    return o(t ? t : e)
                }, l, l.exports, e, n, t, i)
            }
            return t[s].exports
        }
        for (var r = "function" == typeof require && require, s = 0; s < i.length; s++)
            o(i[s]);
        return o
    }({
        1: [function(e, n, t) {
            var i = e("./v1")
              , o = e("./v4")
              , r = o;
            r.v1 = i,
            r.v4 = o,
            n.exports = r
        }
        , {
            "./v1": 4,
            "./v4": 5
        }],
        2: [function(e, n, t) {
            function i(e, n) {
                var t = n || 0
                  , i = o;
                return i[e[t++]] + i[e[t++]] + i[e[t++]] + i[e[t++]] + "-" + i[e[t++]] + i[e[t++]] + "-" + i[e[t++]] + i[e[t++]] + "-" + i[e[t++]] + i[e[t++]] + "-" + i[e[t++]] + i[e[t++]] + i[e[t++]] + i[e[t++]] + i[e[t++]] + i[e[t++]]
            }
            for (var o = [], r = 0; r < 256; ++r)
                o[r] = (r + 256).toString(16).substr(1);
            n.exports = i
        }
        , {}],
        3: [function(e, n, t) {
            (function(e) {
                var t, i = e.crypto || e.msCrypto;
                if (i && i.getRandomValues) {
                    var o = new Uint8Array(16);
                    t = function() {
                        return i.getRandomValues(o),
                        o
                    }
                }
                if (!t) {
                    var r = new Array(16);
                    t = function() {
                        for (var e, n = 0; n < 16; n++)
                            0 === (3 & n) && (e = 4294967296 * Math.random()),
                            r[n] = e >>> ((3 & n) << 3) & 255;
                        return r
                    }
                }
                n.exports = t
            }
            ).call(this, "undefined" != typeof global ? global : "undefined" != typeof self ? self : "undefined" != typeof window ? window : {})
        }
        , {}],
        4: [function(e, n, t) {
            function i(e, n, t) {
                var i = n && t || 0
                  , o = n || [];
                e = e || {};
                var s = void 0 !== e.clockseq ? e.clockseq : a
                  , d = void 0 !== e.msecs ? e.msecs : (new Date).getTime()
                  , f = void 0 !== e.nsecs ? e.nsecs : l + 1
                  , h = d - c + (f - l) / 1e4;
                if (h < 0 && void 0 === e.clockseq && (s = s + 1 & 16383),
                (h < 0 || d > c) && void 0 === e.nsecs && (f = 0),
                f >= 1e4)
                    throw new Error("uuid.v1(): Can't create more than 10M uuids/sec");
                c = d,
                l = f,
                a = s,
                d += 122192928e5;
                var b = (1e4 * (268435455 & d) + f) % 4294967296;
                o[i++] = b >>> 24 & 255,
                o[i++] = b >>> 16 & 255,
                o[i++] = b >>> 8 & 255,
                o[i++] = 255 & b;
                var y = d / 4294967296 * 1e4 & 268435455;
                o[i++] = y >>> 8 & 255,
                o[i++] = 255 & y,
                o[i++] = y >>> 24 & 15 | 16,
                o[i++] = y >>> 16 & 255,
                o[i++] = s >>> 8 | 128,
                o[i++] = 255 & s;
                for (var p = e.node || u, g = 0; g < 6; ++g)
                    o[i + g] = p[g];
                return n ? n : r(o)
            }
            var o = e("./lib/rng")
              , r = e("./lib/bytesToUuid")
              , s = o()
              , u = [1 | s[0], s[1], s[2], s[3], s[4], s[5]]
              , a = 16383 & (s[6] << 8 | s[7])
              , c = 0
              , l = 0;
            n.exports = i
        }
        , {
            "./lib/bytesToUuid": 2,
            "./lib/rng": 3
        }],
        5: [function(e, n, t) {
            function i(e, n, t) {
                var i = n && t || 0;
                "string" == typeof e && (n = "binary" == e ? new Array(16) : null,
                e = null),
                e = e || {};
                var s = e.random || (e.rng || o)();
                if (s[6] = 15 & s[6] | 64,
                s[8] = 63 & s[8] | 128,
                n)
                    for (var u = 0; u < 16; ++u)
                        n[i + u] = s[u];
                return n || r(s)
            }
            var o = e("./lib/rng")
              , r = e("./lib/bytesToUuid");
            n.exports = i
        }
        , {
            "./lib/bytesToUuid": 2,
            "./lib/rng": 3
        }]
    }, {}, [1])(1)
});
Date.prototype.formatDate = function(e) {
    var n = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        S: this.getMilliseconds()
    };
    if (/(y+)/.test(e))
        e = e.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var t in n)
        if (n.hasOwnProperty(t)) {
            if (new RegExp("(" + t + ")").test(e))
                e = e.replace(RegExp.$1, RegExp.$1.length == 1 ? n[t] : ("00" + n[t]).substr(("" + n[t]).length))
        }
    return e
}
;
function getCookie(e) {
    var n, t = new RegExp("(^| )" + e + "=([^;]*)(;|$)");
    if (n = document.cookie.match(t))
        return unescape(n[2]);
    else
        return null
}
function setCookie(e, n) {
    var t = 30;
    var i = new Date;
    i.setTime(i.getTime() + t * 24 * 60 * 60 * 1e3);
    document.cookie = e + "=" + escape(n) + ";expires=" + i.toGMTString()
}
var GoEasyDomainNumber = function() {
    currentNumber = this.initialCurrentNumber()
};
GoEasyDomainNumber.prototype = {
    maxNumber: 10,
    number: function() {
        return currentNumber
    },
    initialCurrentNumber: function() {
        currentNumber = parseInt(getCookie("goeasyNode"));
        if (currentNumber > 0 && currentNumber < this.maxNumber) {
            currentNumber = currentNumber + 1
        } else {
            currentNumber = Math.floor(Math.random() * 10 + 1)
        }
        setCookie("goeasyNode", currentNumber);
        return currentNumber
    }
};
var goEasyDomainNumber = new GoEasyDomainNumber;
function GoEasyArray() {
    Array.apply(this)
}
GoEasyArray.prototype = new Array;
GoEasyArray.prototype.indexOfGuid = function(e) {
    for (var n = 0; n < this.length; n++) {
        if (this[n] == e)
            return n
    }
    return -1
}
;
GoEasyArray.prototype.unshiftGuid = function(e) {
    var n = false;
    var t = this.indexOfGuid(e);
    if (t > -1) {
        n = true;
        this.splice(t, 1)
    }
    this.unshift(e);
    while (this.length > 100) {
        this.pop()
    }
    return n
}
;
function GoEasy(e) {
    log("GoEasy() Create GoEasy object:" + JSON.stringify(e));
    if (this._isEmpty(e.appkey)) {
        if (typeof e.onConnectFailed !== "undefined") {
            e.onConnectFailed({
                code: 400,
                content: "appkey is required"
            })
        }
        return
    }
    this._copyConfig(e);
    var n = this._subServerHost.replace("://", "://" + goEasyDomainNumber.number());
    var t = n + ":" + this._subServerPort;
    this.socket = io.connect(t);
    this._callbackEvents(e)
}
GoEasy.prototype = {
    debug: false,
    socket: null,
    authorizeResult: null,
    channels: [],
    _subServerHost: "http://www.goeasy.io",
    _subServerPort: "8000",
    networkStatus: "initial",
    subscribeBuffer: [],
    maxRetries: 3,
    receivedGuids: new GoEasyArray,
    _copyConfig: function(e) {
        this._appkey = e.appkey;
        this._otp = e.otp;
        if (this._isEmpty(e.userId)) {
            this._userId = "anonymous-" + Math.floor(Math.random() * 1e5 + 1);
            e.userId = this._userId
        } else {
            this._userId = this._trim(e.userId)
        }
        if (this._isEmpty(e.username)) {
            this._username = "";
            e.username = ""
        } else {
            this._username = this._trim(e.username)
        }
        if (e.debug == true) {
            this.debug = true
        }
    },
    subscribe: function(e) {
        log("subscribe() subscribe:" + JSON.stringify(e));
        if (this._isEmpty(e.channel)) {
            log("subscribe() 'channel' is required.");
            if (typeof e.onFailed !== "undefined") {
                e.onFailed({
                    code: 400,
                    content: "channel is required"
                })
            }
            return
        }
        this.subscribeBuffer[e.channel] = e;
        log("subscribe() add subscription into subscribeBuffer:" + JSON.stringify(this.subscribeBuffer[e.channel]));
        var n = this;
        if (n.authorizeResult != null && n.networkStatus == "connected") {
            e.checking = true;
            n.doSubscribeAndCheckAck(e)
        }
    },
    doSubscribe: function(e) {
        log("doSubscribe() with subscription:" + JSON.stringify(e));
        var n = this;
        if (n.authorizeResult.code == 200) {
            if (this._isEmpty(e.channel)) {
                log("doSubscribe() subscribe failed with empty channel");
                n.sendlogs();
                e.finish = true
            } else {
                var t = {
                    channel: e.channel,
                    sid: n.authorizeResult.sid
                };
                log("doSubscribe() emit subscribe params:" + JSON.stringify(t));
                n.socket.emit("subscribe", t, function(t) {
                    log("doSubscribe() receive subscribe ack:" + JSON.stringify(t));
                    if (e.finish != true) {
                        e.finish = true;
                        delete n.subscribeBuffer[e.channel];
                        log("doSubscribe() delete subscription from subscribeBuffer:" + JSON.stringify(e));
                        if (t.resultCode == 200) {
                            n.channels[e.channel] = e;
                            if (typeof e.onSuccess !== "undefined") {
                                e.onSuccess()
                            }
                        } else {
                            if (typeof e.onFailed !== "undefined") {
                                e.onFailed({
                                    code: t.resultCode,
                                    content: t.content
                                })
                            }
                        }
                    }
                })
            }
        } else {
            log("doSubscribe() return with authorize code:" + n.authorizeResult.code)
        }
    },
    doSubscribeAndCheckAck: function(e) {
        log("doSubscribeAndCheckAck():" + JSON.stringify(e));
        e.finish = false;
        var n = this;
        n.doSubscribe(e);
        var t = window.setInterval(function() {
            if (!e.finish && n.networkStatus == "connected") {
                log("doSubscribeAndCheckAck() retry doSubscribe:" + JSON.stringify(e));
                n.doSubscribe(e)
            } else {
                log("doSubscribeAndCheckAck() clean doSubscribeAndCheckAck:" + JSON.stringify(e));
                e.checking = false;
                window.clearInterval(t)
            }
        }, 1300)
    },
    unsubscribe: function(e) {
        if (this._isEmpty(e.channel)) {
            this.log("'channel' is required.");
            if (typeof e.onFailed !== "undefined") {
                e.onFailed({
                    code: 400,
                    content: "channel is required"
                })
            }
            return
        }
        if (typeof this.channels[e.channel] == "undefined") {
            this.log("'channel' is not subscribed.");
            if (typeof e.onFailed !== "undefined") {
                e.onFailed({
                    code: 400,
                    content: "channel[" + e.channel + "] is not subscribed"
                })
            }
            return
        }
        var n = this;
        var t = false;
        var i = 0;
        function o() {
            if (n.authorizeResult.code == 200) {
                n.socket.emit("unsubscribe", {
                    sid: n.authorizeResult.sid,
                    channel: e.channel
                }, function(i) {
                    t = true;
                    if (i.resultCode == 200) {
                        delete n.channels[e.channel];
                        log("doUnsubscribe() delete from channels:" + JSON.stringify(e));
                        if (typeof e.onSuccess !== "undefined") {
                            e.onSuccess()
                        }
                    } else {
                        if (typeof e.onFailed !== "undefined") {
                            e.onFailed({
                                code: i.resultCode,
                                content: i.content
                            })
                        }
                    }
                })
            } else {
                t = true;
                if (typeof e.onFailed !== "undefined") {
                    e.onFailed({
                        code: n.authorizeResult.code,
                        content: n.authorizeResult.content
                    })
                }
            }
        }
        function r() {
            o();
            var r = window.setInterval(function() {
                if (!t && n.networkStatus == "connected" && i < 0) {
                    i++;
                    o()
                } else if (i == n.maxRetries) {
                    window.clearInterval(r);
                    if (typeof e.onFailed !== "undefined") {
                        e.onFailed({
                            code: 408,
                            content: "Server unreachable or timeout"
                        })
                    }
                } else {
                    window.clearInterval(r)
                }
            }, 1e3)
        }
        if (this.authorizeResult != null && n.networkStatus == "connected") {
            r()
        } else {
            var s = window.setInterval(function() {
                if (n.authorizeResult != null && n.networkStatus == "connected") {
                    window.clearInterval(s);
                    r()
                } else {
                    i++;
                    if (i == n.maxRetries) {
                        window.clearInterval(s);
                        if (typeof e.onFailed !== "undefined") {
                            e.onFailed({
                                code: 408,
                                content: "Server unreachable or timeout"
                            })
                        }
                    }
                }
            }, 1e3)
        }
    },
    publish: function(e) {
        if (this._isEmpty(e.channel)) {
            this.log("'channel' is required.");
            if (typeof e.onFailed !== "undefined") {
                e.onFailed({
                    code: 400,
                    content: "channel is required"
                })
            }
            return
        }
        if (this._isEmpty(e.message)) {
            this.log("'message' is required.");
            if (typeof e.onFailed !== "undefined") {
                e.onFailed({
                    code: 400,
                    content: "message is required"
                })
            }
            return
        }
        var n = this;
        var t = false;
        var i = 0;
        function o(o) {
            if (n.authorizeResult.code == 200) {
                n.socket.emit("publish", {
                    sid: n.authorizeResult.sid,
                    channel: e.channel,
                    content: e.message,
                    guid: o,
                    retried: i
                }, function(n) {
                    t = true;
                    if (n.resultCode == 200) {
                        if (typeof e.onSuccess !== "undefined") {
                            e.onSuccess()
                        }
                    } else {
                        if (typeof e.onFailed !== "undefined") {
                            e.onFailed({
                                code: n.resultCode,
                                content: n.content
                            })
                        }
                    }
                })
            } else {
                t = true;
                if (typeof e.onFailed !== "undefined") {
                    e.onFailed({
                        code: n.authorizeResult.code,
                        content: n.authorizeResult.content
                    })
                }
            }
        }
        function r() {
            var r = n.uuid_goeasy();
            o(r);
            var s = window.setInterval(function() {
                if (!t && i < n.maxRetries) {
                    i++;
                    o(r)
                } else if (i == n.maxRetries) {
                    window.clearInterval(s);
                    if (typeof e.onFailed !== "undefined") {
                        e.onFailed({
                            code: 408,
                            content: "Server unreachable or timeout"
                        })
                    }
                } else {
                    window.clearInterval(s)
                }
            }, 1e3)
        }
        if (this.authorizeResult != null && n.networkStatus == "connected") {
            r()
        } else {
            var s = window.setInterval(function() {
                if (n.authorizeResult != null && n.networkStatus == "connected") {
                    window.clearInterval(s);
                    r()
                } else {
                    i++;
                    if (i == n.maxRetries) {
                        window.clearInterval(s);
                        if (typeof e.onFailed !== "undefined") {
                            e.onFailed({
                                code: 408,
                                content: "Server unreachable or timeout"
                            })
                        }
                    }
                }
            }, 1e3)
        }
    },
    _callbackEvents: function(e) {
        var n = this;
        this.socket.on("message", function(e, t) {
            if (n.receivedGuids.unshiftGuid(e.i)) {
                return
            }
            if (e.a && t) {
                t("ack")
            }
            if (typeof n.channels[e.n].onMessage !== "undefined") {
                n.channels[e.n].onMessage({
                    channel: e.n,
                    content: e.c
                })
            }
        });
        this.socket.on("connect", function() {
            n.networkStatus = "connected";
            var t = {
                appkey: n._appkey,
                userId: n._userId,
                username: n._username,
                startMillis: millis,
                artifactVersion: goEasy_V,
                otp: n._otp
            };
            if (n.authorizeResult != null) {
                t.sid = n.authorizeResult.sid
            }
            var i = false;
            function o() {
                log("doAuthorize() emit authorize params:" + JSON.stringify(t));
                if (n._isEmpty(t.artifactVersion)) {
                    n.sendlogs()
                }
                n.socket.emit("authorize", t, function(t) {
                    log("doAuthorize() received authorize ack:" + JSON.stringify(t));
                    if (!i) {
                        i = true;
                        if (n.authorizeResult == null) {
                            n.authorizeResult = {}
                        }
                        n.authorizeResult.code = t.resultCode;
                        n.authorizeResult.content = t.content;
                        if (t.resultCode == 200) {
                            if (n.authorizeResult.sid == null) {
                                n.authorizeResult.sid = t.sid
                            } else if (n.authorizeResult.sid != t.sid) {
                                n.authorizeResult.sid = t.sid;
                                for (var o in n.channels) {
                                    if (n.channels.hasOwnProperty(o)) {
                                        log("doAuthorize() sid expired and will call doSubscribeAndCheckAck from channels:" + JSON.stringify(n.channels[o]));
                                        n.doSubscribeAndCheckAck(n.channels[o])
                                    }
                                }
                            }
                            for (var r in n.subscribeBuffer) {
                                if (n.subscribeBuffer.hasOwnProperty(r)) {
                                    log("doAuthorize() will doSubscribeAndCheckAck from subscribeBuffer:" + JSON.stringify(n.subscribeBuffer[r]));
                                    n.doSubscribeAndCheckAck(n.subscribeBuffer[r])
                                }
                            }
                            if (typeof e.onConnected !== "undefined") {
                                e.onConnected()
                            }
                        } else {
                            if (typeof e.onConnectFailed !== "undefined") {
                                e.onConnectFailed({
                                    code: t.resultCode,
                                    content: t.content
                                })
                            }
                        }
                    }
                })
            }
            o();
            var r = window.setInterval(function() {
                if (!i && n.networkStatus == "connected") {
                    o()
                } else {
                    window.clearInterval(r)
                }
            }, 1300)
        });
        this.socket.on("connect_error", function(n) {
            if (typeof e.onConnectFailed !== "undefined") {
                e.onConnectFailed({
                    code: 408,
                    content: n
                })
            }
        });
        this.socket.on("disconnect", function() {
            n.networkStatus = "disconnected";
            if (n.authorizeResult == null) {
                n.authorizeResult = {}
            }
            n.authorizeResult.code = 408;
            n.authorizeResult.content = "Server unreachable or timeout";
            if (typeof e.onDisconnected !== "undefined") {
                e.onDisconnected()
            }
        })
    },
    _isEmpty: function(e) {
        return typeof e == "undefined" || e == null || this._trim(e).length == 0
    },
    _trim: function(e) {
        return e.replace(/(^\s*)|(\s*$)/g, "")
    },
    log: function(e) {
        if (window["console"] && this.debug) {
            console.log(e)
        }
    },
    uuid_goeasy: function() {
        return uuid.v1()
    },
    sendlogs: function() {
        if (goEasy_debug) {
            this.socket.emit("log", {
                logs: goEasy_logs
            })
        }
    }
};
