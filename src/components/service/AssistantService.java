@Service
public class AssistantService {

    private final Agent agent;

    public AssistantService(ProductoTool productoTool,
                            CarritoTool carritoTool) {

        this.agent = Agent.builder()
                .instructions("""
                    Eres un asistente de e-commerce.
                    Si el usuario pregunta precio o stock usa consultarProducto.
                    Si quiere comprar usa agregarAlCarrito.
                """)
                .tools(productoTool, carritoTool)
                .build();
    }

    public String chat(String message) {
        return agent.run(message);
    }
}