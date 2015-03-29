/*
 * Copyright (c) 2014 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

/**
 * Usage:
 * Open {@link http://www.mrc.uidaho.edu/mrc/people/jff/digital/MIPSir.html}, paste this script into your console and the data is
 * generated and appended to the page.
 */

"use strict";

function polyfill() {
    if (!String.prototype.subString) {
        String.prototype.subString = function () {
            return String.prototype.substring.apply(this, arguments);
        }
    }
}

polyfill();

$(".MsoNormalTable").each(function () {
    var syntax = $(this).find("tbody:first > tr:nth-child(3) > td:nth-child(2) > p:first > font:first > span:first").text();
    if (!syntax) {
        syntax = $(this).find("tbody:first > tr:nth-child(3) > td:nth-child(2) > p:first > span:first > font:first > span:first").text();
    }
    var name = syntax.split(" ")[0].toUpperCase();
    var encoding = $(this).find("tbody:first > tr:nth-child(4) > td:nth-child(2) > p:first > code:first > font:first > span:first").text().replace(/\n/g, "").replace(/ /g, "");
    var bits = encoding.subString(26, 32);
    if (bits != "iiiiii") {
        $("body").append($("<pre></pre>").html(name + "(0b" + bits + "),"));
    }
});
