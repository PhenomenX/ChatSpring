function View() {
    this.loginForm = $("#login_form");
    this.messageForm = $("#message_form");
    this.registerForm = $("#registration_form");
    this.loginButton = $("#login_button");
    this.logoutButton = $("#logout_button");
    this.profileButton = $("#profile_button");
    this.registerButton = $("#register_button");
    this.messagesContainer = $("#messages");
    this.usersContainer = $("#users");
    this.chat = $("#chat");
    var _this = this;
    this.registerButton.bind("click", function () { _this.showRegisterForm() });
    this.loginButton.bind("click", function () { _this.showLoginForm() });
    this.profileButton.bind("click", function () { _this.showProfile() });
}

View.prototype.refleshMessages = function (messages) {
    for (var i = 0; i < messages.length; i++) {
        $("<p>" + messages[i] + "</p>").appendTo(this.messagesContainer);
    }
};
View.prototype.refleshUsers = function (users) {
    for (var i = 0; i < users.length; i++) {
        $("<p>" + users[i].name + "</p>").appendTo(this.usersContainer);;
    }
};
View.prototype.showLoginForm = function () {
    this.loginForm.css("display", "flex");
    this.loginButton.css("display", "none");
    this.chat.css("display", "none");
    this.registerForm.css("display", "none");
    this.registerButton.css("display", "flex");
};
View.prototype.showChat = function (users, messages) {
    this.loginForm.css("display", "none");
    this.chat.css("display", "flex");
    this.refleshUsers(users);
    this.refleshMessages(messages);
};
View.prototype.showProfile = function () {

};
View.prototype.showUser = function () {

};
View.prototype.showRegisterForm = function () {
    this.loginForm.css("display", "none");
    this.chat.css("display", "none");
    this.loginButton.css("display", "flex");
    this.registerForm.css("display", "flex");
    this.registerButton.css("display", "none");
};
