function UserWindow() {
    this.userClicked = new Event(this);
    this.clickedUserContainer;
    var _this = this;
    this.userClicked.attach(function (sender, args) {
        sender.clickedUserContainer = args.target;
    });
    $('.overlay').click(function () {
        $(".user_info").css('display', 'none');
        $('.overlay').fadeOut(200);
    });
}

UserWindow.prototype.showInfo = function (data) {
    var container = $(".user_info").first();
    var coordinats = this.clickedUserContainer.getBoundingClientRect();
    container.css("top", coordinats.top + "px");
    container.css("left", coordinats.left + "px");
    $('.overlay').fadeIn(200);
    changeContainerStyle(container);
    container.find(".avatar").attr("src", "images/" + data.picturePath);
    container.find(".nick").text(data.name);
    container.find(".role").text(data.role);
    container.find(".status").text(data.status);
}

function changeContainerStyle(container) {
    container.css("position", "absolute");
    container.css("border", "2px solid black");
    container.css("display", "flex");
    container.css("flexDirection", "column");
    container.css("justifyContent", "flex-start");
    container.css("alignItems", "center");
}
