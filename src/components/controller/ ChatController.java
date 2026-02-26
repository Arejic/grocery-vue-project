@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final AssistantService assistantService;

    public ChatController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody Map<String, String> body) {

        String message = body.get("message");
        String reply = assistantService.chat(message);

        return new ChatResponse(reply);
    }
}