function Users(users) {
    this.users = users;
}

Users.prototype.addUser = function (nick, image, role) {
    this.users.push({ 'nick': nick, 'image': image, 'role': role});
}

Users.prototype.getUser = function (nick) {
    for (var i = 0; i < this.users.length; i++) {
        if (this.users[i].nick == nick) {
            return this.users[i];
        }
    }
}
