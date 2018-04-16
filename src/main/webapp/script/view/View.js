function View() {
    //DOM Elements
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

    //Events
    this.messageSended = new Event(this);
    this.loginFormSended = new Event(this);
    this.registerFormSended = new Event(this);
    this.logoutClicked = new Event(this);
    this.userKicked = new Event(this);
    this.userUnkicked = new Event(this);

    //Event Initializers
    this.messageForm.submit(function (event) {event.preventDefault(); _this.messageSended.notify(event) });
    this.loginForm.submit(function (event) {event.preventDefault(); _this.loginFormSended.notify(event) });
    this.registerForm.submit(function (event) {event.preventDefault(); _this.registerFormSended.notify(event) });
    this.logoutButton.click(function () { _this.logoutClicked.notify() });

    //View Changers
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
    var _this = this;
    if (window.localStorage.getItem("role") == "USER") {
        for (var i = 0; i < users.length; i++) {
            $("<div class=\"user\">" + users[i].name + "</div>").appendTo(this.usersContainer);
        }
    }
    else {
        for (var i = 0; i < users.length; i++) {
            let userContainer = $("<div class=\"user\">" + users[i].name + "</div>");
            let button = $("<input class=\"kick_button\" type=\"button\" value=\"kick\"/>");
            button.click(function (e) { _this.userKicked.notify({ 'user': e.currentTarget.parentNode.textContent }) });
            button.appendTo(userContainer);
            userContainer.appendTo(this.usersContainer);
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
