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
}

Controller.prototype.sendMessage = function (target, event) {
    var form = $(event.currentTarget);
    var data = form.serialize();
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
            alert(xhr.status);
            alert(thrownError);
            console.log(window.controller);
        },
        complete: function (data) {
            form.find('input[type="submit"]').prop('disabled', false);
        }
    });
};

Controller.prototype.sendLoginForm = function (target, event) {
    var form = $(event.currentTarget);
    var data = form.serialize();
    let loginPromise = new Promise(function (resolve, reject) {
        $.ajax({
            type: 'PUT',
            url: 'users/login', // путь дo oбрaбoтчикa, у нaс oн лeжит в тoй жe пaпкe
            dataType: 'text',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            data: data, // дaнныe для oтпрaвки
            beforeSend: function (data) { // сoбытиe дo oтпрaвки
                form.find('input[type="submit"]').attr('disabled', 'disabled'); // нaпримeр, oтключим кнoпку, чтoбы нe жaли пo 100 рaз
            },
            success: function (data) { // сoбытиe пoслe удaчнoгo oбрaщeния к сeрвeру и пoлучeния oтвeтa
                resolve(data);
            },
            error: function (xhr, ajaxOptions, thrownError) { // в случae нeудaчнoгo зaвeршeния зaпрoсa к сeрвeру
                alert(xhr.status); // пoкaжeм oтвeт сeрвeрa
                alert(thrownError); // и тeкст oшибки
            },
            complete: function (data) { // сoбытиe пoслe любoгo исхoдa
                form.find('input[type="submit"]').prop('disabled', false); // в любoм случae включим кнoпку oбрaтнo
            }
        });
    });
    loginPromise.then(this.processLoginResponse.bind(this));
};

Controller.prototype.generateChat = function () {
    if (this.chatRefleshingEnable) {
        Promise.all([this.getUsers(), this.getMessages()]).then(
            values => { this.view.showChat(values[0], values[1]) });
        setTimeout(this.generateChat.bind(this), 2000);
    }
}

Controller.prototype.getMessages = function () {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: 'GET',
            url: 'messages',
            dataType: 'json',
            success: function (data) {
                resolve(data);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
            }
        });
    });
}
Controller.prototype.getUsers = function () {
    return new Promise(function (resolve, reject) {
        var users;
        $.ajax({
            type: 'GET',
            url: 'users',
            data: "status=LOGIN",
            dataType: 'json',
            success: function (data) {
                resolve(data);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert(xhr.status);
                alert(thrownError);
            }
        });
    });
};

Controller.prototype.logoutUser = function () {
    this.chatRefleshingEnable = false;
    $.ajax({
        type: 'PUT',
        url: 'users/logout',
        success: function (data) {
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status);
            alert(thrownError);
        }
    });
}
Controller.prototype.getUser;

Controller.prototype.sendRegisterForm = function (target, event) {
    var form = $("#registration_form")[0];
    var data = new FormData(form);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "users",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 60000,
        success: function (data) {
            console.log("file is uploaded");
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status);
            alert(thrownError);
        }
    });
    this.view.showLoginForm();
    return false;
};

Controller.prototype.kickUser = function (target, args) {
    var data = 'nick=' + encodeURIComponent(args.user.trim());
    $.ajax({
        type: 'PUT',
        url: 'users/kick',
        data: data,
        success: function (data) {
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status);
            alert(thrownError);
        }
    });
};

Controller.prototype.unkickUser = function () { };

Controller.prototype.setCurrentView = function () {
    let state = window.localStorage.getItem("state");
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
    this.defineRole(data);
    this.chatRefleshingEnable = true;
    this.generateChat();
}

Controller.prototype.defineRole = function (role) {
    window.localStorage.setItem("role", role);
}



