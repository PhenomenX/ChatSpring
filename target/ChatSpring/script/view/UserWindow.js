function UserWindow() {
    this.userClicked = new Event(this);
    this.clickedUserContainer;
    var _this = this;
    this.userClicked.attach(function(sender, args){
        sender.clickedUserContainer = args.target;
    });
}

UserWindow.prototype.showInfo = function (data) {
    var container = $(".user_info").get(0);
    var coordinats = this.clickedUserContainer.getBoundingClientRect();
    container.style.top = coordinats.top + "px";
    container.style.left = coordinats.left + "px";
    changeContainerStyle(container);
    alert(data);
    container.getElementsByClassName("avatar")[0].setAttribute("src","/images/" + data.picturePath);
}

function changeContainerStyle(container) {
    container.style.position = "absolute";
    container.style.paddingRight = "20px";
    container.style.paddingBottom = "20px";
    container.style.border = "2px solid black";
    container.style.display = "flex";
    container.style.align = "left";
}
