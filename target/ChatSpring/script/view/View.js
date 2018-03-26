function View() {
    this.loginForm = $("#login_form");
    this.messageForm = $("#message_form");
    this.logoutButton = $("#logout_button");
    this.profileButton = $("#profile_button");
    this.registerButton = $("#register_button");
    this.messagesContainer = $("#messages");
    this.usersContainer = $("#users");
    this.chat = $("#chat");
}

View.prototype.refleshMessages = function(messages){
    for (var i = 0; i < messages.length; i++) {
        $("<p>" + messages[i] + "</p>").appendTo(this.messagesContainer);
    }
};
View.prototype.refleshUsers = function(users){
    for (var i = 0; i < users.length; i++) {
        $("<p>" + users[i].name + "</p>").appendTo(this.usersContainer);;
    }
};
View.prototype.showLoginForm;
View.prototype.showChat = function(users, messages){
    this.loginForm.css("display", "none");
    this.chat.css("display", "flex");
    this.refleshUsers(users);
    this.refleshMessages(messages);
};
View.prototype.showProfile;
View.prototype.showUser;
View.prototype.showRegisterForm;
