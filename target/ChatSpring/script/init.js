jQuery(document).ready(function () {
    var view = new View();
    var controller = new Controller(view);
    window.view = view;
    window.controller = controller;
    console.log(window.controller);
});