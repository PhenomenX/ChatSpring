function Controller(view) {
    this.view = view;
    var _this = this;
    this.chatRefleshingEnable = true;
    this.view.loginFormSended.attach(_this.sendLoginForm.bind(_this));
    this.view.messageSended.attach(_this.sendMessage.bind(_this));
    this.view.registerFormSended.attach(_this.sendRegisterForm.bind(_this));
    this.view.logoutClicked.attach(function () { _this.logoutUser(); _this.view.showLoginForm() });
    this.view.userKicked.attach(_this.kickUser.bind(_this));
    this.view.userUnkicked.attach(_this.unkickUser.bind(_this));
    this.view.userClicked.attach(_this.generateUserWindow.bind(_this));
}

Controller.prototype.sendMessage = function (target, event) {
    var form = $(event.currentTarget);
    var data = form.serialize();
    var controller = this;
    $.ajax({
        type: 'POST',
        url: 'messages',
        dataType: 'text',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        data: data,
        beforeSend: function (data) {
            form.find('input[type="submit"]').attr('disabled', 'disabled');
        },
        success: function (data) {
            window.controller.generateChat();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            controller.chatRefleshingEnable = false;
            controller.view.showError(xhr.status, xhr.getResponseHeader("message"));
        },
        complete: function (data) {
            form.find('input[type="submit"]').prop('disabled', false);
        }
    });
    form.get(0).messageText.value = '';
};

Controller.prototype.sendLoginForm = function (target, event) {
    var form = $(event.currentTarget);
    var data = form.serialize();
    var controller = this;
    let loginPromise = new Promise(function (resolve, reject) {
        $.ajax({
            type: 'PUT',
            url: 'users/login',
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            data: data,
            beforeSend: function (data) {
                form.find('input[type="submit"]').attr('disabled', 'disabled');
            },
            success: function (data) {
                resolve(data);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                controller.chatRefleshingEnable = false;
                controller.view.showError(xhr.status, xhr.getResponseHeader("message"));
            },
            complete: function (data) {
                form.find('input[type="submit"]').prop('disabled', false);
            }
        });
    });
    form.get(0).nick.value = '';
    form.get(0).password.value = '';
    loginPromise.then(this.processLoginResponse.bind(this));
};

Controller.prototype.generateChat = function () {
    var chatGenerating;
    var controller = this;
    var user = JSON.parse(window.sessionStorage.getItem("user"));
    this.view.showUser();
    if (user.role == "ADMIN") {
        chatGenerating = function () {
            if (this.chatRefleshingEnable) {
                Promise.all([this.getUsers("LOGIN"), this.getMessages(), this.getUsers("KICK")]).then(
                    values => { this.view.refleshChat(values[0], values[1], values[2]) });
                setTimeout(chatGenerating.bind(controller), 2000);
            }
        };
    } else {
        chatGenerating = function () {
            if (this.chatRefleshingEnable) {
                Promise.all([this.getUsers("LOGIN"), this.getMessages()]).then(
                    values => { this.view.refleshChat(values[0], values[1]) });
                setTimeout(chatGenerating.bind(controller), 2000);
            }
        }
    }
    chatGenerating.bind(controller)();
}
Controller.prototype.getMessages = function () {
    var controller = this;
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: 'GET',
            url: 'messages',
            dataType: 'json',
            success: function (data) {
                resolve(data);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                controller.chatRefleshingEnable = false;
                controller.view.showError(xhr.status, xhr.getResponseHeader("message"));
            }
        });
    });
}
Controller.prototype.getUsers = function (status) {
    var controller = this;
    return new Promise(function (resolve, reject) {
        var users;
        $.ajax({
            type: 'GET',
            url: 'users',
            data: "status=" + status,
            dataType: 'json',
            success: function (data) {
                resolve(data);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                controller.chatRefleshingEnable = false;
                controller.view.showError(xhr.status, xhr.getResponseHeader("message"));
            }
        });
    });
};

Controller.prototype.logoutUser = function () {
    this.chatRefleshingEnable = false;
    var controller = this;
    $.ajax({
        type: 'PUT',
        url: 'users/logout',
        success: function (data) {
        },
        error: function (xhr, ajaxOptions, thrownError) {
            controller.view.showError(xhr.status, xhr.getResponseHeader("message"));
        }
    });
}

Controller.prototype.sendRegisterForm = function (target, event) {
    var form = event.currentTarget;
    var data = new FormData(form);
    var controller = this;
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "users/register",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("file is uploaded");
        },
        error: function (xhr, ajaxOptions, thrownError) {
            controller.chatRefleshingEnable = false;
            controller.view.showError(xhr.status, xhr.getResponseHeader("message"));
        }
    });
    form.nick.value = '';
    form.password.value = '';
    form.file.value = '';
    this.view.showLoginForm();
    return false;
};

Controller.prototype.kickUser = function (target, args) {
    var data = 'nick=' + encodeURIComponent(args.user.trim());
    var controller = this;
    $.ajax({
        type: 'PUT',
        url: 'users/kick',
        data: data,
        success: function (data) {
        },
        error: function (xhr, ajaxOptions, thrownError) {
            controller.chatRefleshingEnable = false;
            controller.view.showError(xhr.status, xhr.getResponseHeader("message"));
        }
    });
};

Controller.prototype.unkickUser = function (target, args) {
    var controller = this;
    var data = 'nick=' + encodeURIComponent(args.user.trim());
    $.ajax({
        type: 'PUT',
        url: 'users/unkick',
        data: data,
        success: function (data) {
        },
        error: function (xhr, ajaxOptions, thrownError) {
            controller.chatRefleshingEnable = false;
            controller.view.showError(xhr.status, xhr.getResponseHeader("message"));
        }
    });
};

Controller.prototype.setCurrentView = function () {
    let state = window.sessionStorage.getItem("state");
    if (state) {
        if (state == "login") {
            this.view.showLoginForm();
        } else if (state == "chat") {
            this.generateChat();
        } else if (state == "register") {
            this.view.showRegisterForm();
        }
    }
}

Controller.prototype.processLoginResponse = function (data) {
    this.defineUser(data);
    this.chatRefleshingEnable = true;
    this.view.showChat();
    this.generateChat();
}

Controller.prototype.defineUser = function (user) {
    window.sessionStorage.setItem("user", JSON.stringify(user));
}

Controller.prototype.generateUserWindow = function (sender, args) {
    var userName = args.user.trim();
    var userPromise = new Promise(function (resolve, reject) {
        $.ajax({
            type: 'GET',
            url: 'users/' + userName,
            dataType: 'json',
            success: function (data) { 
                resolve(data) },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
            }
        });
    });
    userPromise.then(sender.showInfo.bind(sender));
}

