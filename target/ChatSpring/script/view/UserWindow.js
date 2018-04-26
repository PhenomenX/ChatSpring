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
    var container = $(".user_info").get(0);
    var coordinats = this.clickedUserContainer.getBoundingClientRect();
    container.style.top = coordinats.top + "px";
    container.style.left = coordinats.left + "px";
    $('.overlay').fadeIn(200);
    changeContainerStyle(container);
    container.getElementsByClassName("avatar")[0].setAttribute("src", "images/" + data.picturePath);
    container.getElementsByClassName("nick")[0].textContent = data.name;
    container.getElementsByClassName("role")[0].textContent = data.role;
    container.getElementsByClassName("status")[0].textContent = data.status;
}

function changeContainerStyle(container) {
    container.style.position = "absolute";
    container.style.border = "2px solid black";
    container.style.display = "flex";
    container.style.flexDirection = "column";
    container.style.justifyContent = "flex-start";
    container.style.alignItems = "center";
}
