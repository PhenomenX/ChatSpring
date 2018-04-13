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
    this.messagesContainer.empty();
    for (var i = 0; i < messages.length; i++) {
        $("<p>" + messages[i] + "</p>").appendTo(this.messagesContainer);
    }
};
View.prototype.refleshUsers = function (users) {
    this.usersContainer.empty();
    if (window.localStorage.getItem("role") == "USER") {
        for (var i = 0; i < users.length; i++) {
            $("<div>" + users[i].name + "</div>").appendTo(this.usersContainer);
        }
    }
    else {
        for (var i = 0; i < users.length; i++) {
            let container = $("<div>" + users[i].name + "</div>");
            // ДОБАВИТЬ КНОПКУ KICK
        }
    }
};
View.prototype.showLoginForm = function () {
    this.loginForm.css("display", "flex");
    this.loginButton.css("display", "none");
    this.chat.css("display", "none");
    this.registerForm.css("display", "none");
    this.registerButton.css("display", "flex");
    window.localStorage.setItem("state", "login");
};
View.prototype.showChat = function (users, messages) {
    this.loginForm.css("display", "none");
    this.chat.css("display", "flex");
    this.refleshUsers(users);
    this.refleshMessages(messages);
    window.localStorage.setItem("state", "chat");
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
    window.localStorage.setItem("state", "register");
};
