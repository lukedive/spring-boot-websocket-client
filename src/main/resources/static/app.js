var stompClient = null;
var stompClientBinary = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function setConnectedBinary(connected) {
    document.getElementById('connectBinary').disabled = connected;
    document.getElementById('disconnectBinary').disabled = !connected;
    document.getElementById('conversationDivBinary').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('responseBinary').innerHTML = '';
}


function stringToBin(str) {
	  var result = [];
	  for (var i = 0; i < str.length; i++) {
	    result.push(str.charCodeAt(i));
	  }
	  return result;
	}

function bin2String(array) {
	  return String.fromCharCode.apply(String, array);
}

function connectBinary() {
    stompClient = Stomp.client('ws://localhost:8090/ws-binary');
    stompClient.debug = null;
    stompClient.connect({}, function(frame) {
        setConnectedBinary(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings-binary', function(greeting){
            showGreetingBinary(greeting.body);
        });
        stompClient.subscribe('/user/queue/horray', function(greeting){
            showGreetingBinary(greeting.body);
        });
    });
}


function connect() {
    stompClient = Stomp.client('ws://localhost:8090/ws');
    stompClient.debug = null;
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(greeting){
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function disconnectBinary() {
    if (stompClientBinary != null) {
        stompClientBinary.disconnect();
    }
    setConnectedBinary(false);
    console.log("Disconnected binary");
}

function sendName() {
    var name = document.getElementById('name').value;
    stompClient.send("/app/hello", {}, JSON.stringify({ 'name': name }));
}

function sendNameBinary() {
    var name = document.getElementById('nameBinary').value;
    stompClient.send("/app/hello-binary", {}, name);
}

function showGreeting(message) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    response.appendChild(p);
}

function showGreetingBinary(message) {
    var response = document.getElementById('responseBinary');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    response.appendChild(p);
}
