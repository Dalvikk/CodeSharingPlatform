<!-- Send data in JSON using XMLHttpRequest -->
function send() {
    try {
        let object = createObject();
        sendApiRequest(object);
    } catch (e) {
        alert("Error: " + e.message);
    }
}

function createObject() {
    let object = {
        "code": document.getElementById("code_snippet").value,
    };
    let minutesLimit = document.getElementById("time_restriction").value;
    let viewsLimit = document.getElementById("views_restriction").value;
    if (minutesLimit !== "") {
        object.minutesLimit = parseNumberOrThrowElse(minutesLimit);
    }
    if (viewsLimit !== "") {
        object.viewsLimit = parseNumberOrThrowElse(viewsLimit);
    }
    return object;
}

function parseNumberOrThrowElse(str) {
    let num = +str
    if (isNaN(num)) {
        throw new Error(str + " should be a number");
    }
    return num;
}

function sendApiRequest(object) {
    let json = JSON.stringify(object);
    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/api/code/new', false)
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(json);
    if (xhr.status === 200) {
        alert("Success!. UUID: " + xhr.response);
    } else {
        alert("Unsuccessful. Please, try again or contact with the administrator.")
    }
}