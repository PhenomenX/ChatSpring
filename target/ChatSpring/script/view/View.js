function View() {
    this.loginForm = $("#login_form");
    this.messageForm = $("#message_form");
    this.logoutButton = $("#logout_button");
    this.profileButton = $("#profile_button");
    this.registerButton = $("#register_button");
    this.messagesContainer = $("#messages");
    this.usersContainer = $("#users");
}

View.prototype.refleshMessages;
View.prototype.refleshUsers = function(users){
    for (var i = 0; i < users.length; i++) {
        usersContainer.appendChild();
    }
};
View.prototype.showLoginForm;
View.prototype.showChat = function(users, messages){
    alert(users);
    alert(messages);
};
View.prototype.showProfile;
View.prototype.showUser;
View.prototype.showRegisterForm;
