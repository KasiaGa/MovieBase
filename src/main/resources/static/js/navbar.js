$(document).ready(function () {
    $("#navigation").append("<div class=\"nav-wrapper grey darken-4\">" +
        "<a href=\"#\" class=\"brand-logo nav-menu-logo\">MovieBase</a>" +
        "<ul id=\"nav-mobile\" class=\"left hide-on-med-and-down\" style='padding-left: 190px;'>" +
        "<li><a href=\"profile.html\">Profile</a></li>" +
        "<li><a href=\"index.html\">Movies</a></li>" +
        "<li><a href=\"likes.html\">Likes</a></li>" +
        "<li><a href=\"search.html\">Search</a></li> </ul>" +
        '<a href="welcome.html" class="right hide-on-med-and-down nav-menu-right waves-effect waves-light btn" style="margin-top: 14px; margin-right: 20px;" onclick="signOut();"><i class="material-icons left" style="margin-top: -14px; margin-left:-5px;">power_settings_new</i>logout</a></div>'
    )
});