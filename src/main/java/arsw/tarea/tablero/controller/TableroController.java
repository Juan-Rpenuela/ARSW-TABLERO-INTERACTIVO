package arsw.tarea.tablero.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TableroController {

    @MessageMapping("/draw")
    @SendTo("/topic/draw")
    public DrawEvent draw(DrawEvent event) {
        return event;
    }

    @MessageMapping("/clear")
    @SendTo("/topic/clear")
    public String clear() {
        return "clear";
    }
}
