$(function () {
    $.get("manage-header.html", function (data) {
        $("#header").html(data);
    });
    $.get("manage-footer.html", function (data) {
        $("#footer").html(data);
    });
});