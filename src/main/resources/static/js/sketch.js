let stompClient = null;
let myColor = [Math.floor(Math.random()*255), Math.floor(Math.random()*255), Math.floor(Math.random()*255)];

function connect() {
    const socket = new SockJS('/tablero-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/draw', function (message) {
            const data = JSON.parse(message.body);
            fill(data.color[0], data.color[1], data.color[2]);
            noStroke();
            ellipse(data.x, data.y, 10, 10);
        });
        stompClient.subscribe('/topic/clear', function () {
            background(255);
        });
    });
}

function setup() {
    // Crear el canvas y ubicarlo después del botón
    let canvas = createCanvas(640, 480);
    canvas.parent(document.body); // Asegura que el canvas esté en el body
    background(255); // Fondo blanco inicial
    connect();

    document.getElementById('clearBtn').onclick = function() {
        background(255);
        stompClient.send("/app/clear", {}, "");
    };
}

function draw() {
    if (mouseIsPressed && mouseY < height) {
        fill(myColor[0], myColor[1], myColor[2]);
        noStroke();
        ellipse(mouseX, mouseY, 10, 10);

        // Enviar evento de dibujo
        if (stompClient && stompClient.connected) {
            stompClient.send("/app/draw", {}, JSON.stringify({
                x: mouseX,
                y: mouseY,
                color: myColor
            }));
        }
    }
}