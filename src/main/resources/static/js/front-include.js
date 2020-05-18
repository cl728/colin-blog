$(function () {
    $.get("/front-header.html", function (data) {
        $("#header").html(data);
    });
    $.get("/front-footer.html", function (data) {
        $("#footer").html(data);
    });
});