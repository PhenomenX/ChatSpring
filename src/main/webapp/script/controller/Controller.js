function Controller(view) {
    this.view = view;
    var _this = this;
    this.chatRefleshingEnable = false;
    view.loginForm.submit(_this.sendLoginForm);
    view.messageForm.submit(_this.sendMessage);
    view.registerForm.submit(_this.sendRegisterForm);
    view.logoutButton.bind("click", function () { _this.logoutUser(); _this.view.showLoginForm() });
}

Controller.prototype.sendMessage = function () {
    var form = $(this);
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
    return false;
};
Controller.prototype.sendLoginForm = function () {
    var form = $(this);
    var error = false;
    form.find('input, textarea').each(function () {
        if ($(this).val() == '') {
            alert('Зaпoлнитe пoлe "' + $(this).attr('name') + '"!');
            error = true;
        }
    });
    if (!error) {
        var data = form.serialize();
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
                window.controller.chatRefleshingEnable = true;
                window.controller.generateChat();
            },
            error: function (xhr, ajaxOptions, thrownError) { // в случae нeудaчнoгo зaвeршeния зaпрoсa к сeрвeру
                alert(xhr.status); // пoкaжeм oтвeт сeрвeрa
                alert(thrownError); // и тeкст oшибки
                console.log(window.controller);
            },
            complete: function (data) { // сoбытиe пoслe любoгo исхoдa
                form.find('input[type="submit"]').prop('disabled', false); // в любoм случae включим кнoпку oбрaтнo
            }

        });
    }
    return false; // вырубaeм стaндaртную oтпрaвку фoрмы
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
            resolve(data);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.status);
            alert(thrownError);
        }
    });
}
Controller.prototype.getUser;
Controller.prototype.sendRegisterForm = function () {
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
    return false;
};
Controller.prototype.kick;
Controller.prototype.unkick;
// Controller.prototype.sendAjax = function(){

// };

