function View() {
    //DOM Elements
    this.loginForm = $(".login_form").first();
    this.messageForm = $(".message_form").first();
    this.registerForm = $(".registration_form").first();
    this.loginButton = $(".login_button").first();
    this.logoutButton = $(".logout_button").first();
    this.profileButton = $(".profile_button").first();
    this.registerButton = $(".register_button").first();
    this.messagesContainer = $(".messages").first();
    this.usersContainer = $(".users").first();
    this.errorContainer = $(".error_container").first();
    this.chat = $(".chat").first();
    this.userWindow = new UserWindow();
    var _this = this;

    //Events
    this.messageSended = new Event(this);
    this.loginFormSended = new Event(this);
    this.registerFormSended = new Event(this);
    this.logoutClicked = new Event(this);
    this.userKicked = new Event(this);
    this.userUnkicked = new Event(this);
    this.userClicked = this.userWindow.userClicked;

    //Event Initializers
    this.messageForm.submit(function (event) { event.preventDefault(); _this.messageSended.notify(event) });
    this.loginForm.submit(function (event) { event.preventDefault(); _this.loginFormSended.notify(event) });
    this.registerForm.submit(function (event) { event.preventDefault(); _this.registerFormSended.notify(event) });
    this.logoutButton.click(function () { _this.logoutClicked.notify() });

    //View Changers
    this.registerButton.bind("click", function () { _this.showRegisterForm() });
    this.loginButton.bind("click", function () { _this.showLoginForm() });
    this.profileButton.bind("click", _this.showProfile.bind(_this));
}

View.prototype.refleshMessages = function (messages) {
    this.messagesContainer.empty();
    for (var i = 0; i < messages.length; i++) {
        $("<p>" + messages[i] + "</p>").appendTo(this.messagesContainer);
    }
};

View.prototype.refleshUsers = function (users, kickedUsers) {
    this.usersContainer.empty();
    var _this = this;
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    $("<p>Logged users:</p>").appendTo(this.usersContainer);
    if (user.role == "USER") {
        for (var i = 0; i < users.length; i++) {
            var userContainer = $("<div class=\"user\">" + users[i].name + "</div>")
                .appendTo(this.usersContainer);
            userContainer.click(function (e) {
                _this.userClicked.notify({ 'user': e.currentTarget.textContent, 'target': e.currentTarget })
            });
        }
    }
    else {
        for (var i = 0; i < users.length; i++) {
            let userContainer = $("<div class=\"user\">" + users[i].name + "</div>");
            let button = $("<input class=\"kick_button\" type=\"button\" value=\"kick\"/>");
            userContainer.click(function (e) {
                _this.userClicked.notify({ 'user': e.currentTarget.textContent, 'target': e.currentTarget })
            });
            button.click(function (e) {
                e.stopPropagation()
                _this.userKicked.notify({ 'user': e.currentTarget.parentNode.textContent })
            });
            button.appendTo(userContainer);
            userContainer.appendTo(this.usersContainer);
        }
        $("<p>Kicked users:</p>").appendTo(this.usersContainer);
        for (var i = 0; i < kickedUsers.length; i++) {
            let userContainer = $("<div class=\"user\">" + kickedUsers[i].name + "</div>");
            let button = $("<input class=\"unkick_button\" type=\"button\" value=\"unkick\"/>");
            userContainer.click(function (e) {
                _this.userClicked.notify({ 'user': e.currentTarget.textContent, 'target': e.currentTarget })
            });
            button.click(function (e) {
                e.stopPropagation()
                _this.userUnkicked.notify({ 'user': e.currentTarget.parentNode.textContent })
            });
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
    this.errorContainer.css("display", "none");
    this.profileButton.css("display", "none");
    this.logoutButton.css("display", "none");
    window.sessionStorage.setItem("state", "login");
    var greetingContainer = $(".header h3").get(0);
    greetingContainer.textContent = "Welcome!";
};
View.prototype.showChat = function () {
    this.loginForm.css("display", "none");
    this.errorContainer.css("display", "none");
    this.chat.css("display", "flex");
    this.registerButton.css("display", "none");
    this.profileButton.css("display", "flex");
    this.logoutButton.css("display", "flex");
    window.sessionStorage.setItem("state", "chat");
};

View.prototype.refleshChat = function (users, messages, kickedUsers) {
    this.refleshUsers(users, kickedUsers);
    this.refleshMessages(messages);
};

View.prototype.showProfile = function (e) {
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    this.userWindow.clickedUserContainer = e.currentTarget;
    this.userWindow.showInfo(user);
};

View.prototype.showUser = function () {
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    var greetingContainer = $(".header h3").get(0);
    greetingContainer.textContent = "Welcome, " + user.name + "!";
};

View.prototype.showRegisterForm = function () {
    this.loginForm.css("display", "none");
    this.chat.css("display", "none");
    this.loginButton.css("display", "flex");
    this.registerForm.css("display", "flex");
    this.registerButton.css("display", "none");
    this.errorContainer.css("display", "none");
    this.logoutButton.css("display", "none");
    this.profileButton.css("display", "none");
    window.sessionStorage.setItem("state", "register");
};

View.prototype.showError = function (status, message) {
    this.showLoginForm();
    this.errorContainer.css("display", "flex");
    this.errorContainer.text(status + " - " + message);
}