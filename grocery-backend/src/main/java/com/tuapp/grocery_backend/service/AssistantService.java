package com.tuapp.grocery_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.tuapp.grocery_backend.model.Producto;
import com.tuapp.grocery_backend.repository.ProductoRepository;

@Service
public class AssistantService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoService carritoService;

    public String chat(String message) {
        if (message == null || message.isBlank()) {
            return "¿Podrías escribir tu consulta nuevamente? 😊";
        }

        String msg = message.toLowerCase().trim();
        List<Producto> productos = productoRepository.findAll();

        // ===== SALUDOS =====
        if (msg.matches(".*\\b(hola|buenas|hey|hi|hello)\\b.*")) {
            return "¡Hola! 👋 Soy tu asistente virtual. Puedes preguntarme por productos o hacer pedidos.";
        }

        // ===== LIMPIAR PALABRAS IRRELEVANTES =====
        String[] stopWords = {"quiero", "dame", "agrega", "al", "carrito", "por favor", "vendeme"};
        for (String w : stopWords) msg = msg.replace(w, "").trim();

        // ===== CONSULTA DE VARIEDADES =====
        if (msg.matches(".*\\b(tipo|variedad|qué tipo)\\b.*")) {
            for (Producto p : productos) {
                if (msg.contains(p.getNombre().toLowerCase())) {
                    List<Producto> encontrados = productoRepository.findByNombreContainingIgnoreCase(p.getNombre());
                    if (encontrados == null || encontrados.isEmpty()) {
                        return "No encontré variedades disponibles de " + capitalize(p.getNombre()) + ".";
                    }

                    StringBuilder resVariedades = new StringBuilder();
                    resVariedades.append("Tenemos las siguientes variedades de ")
                                 .append(capitalize(p.getNombre()))
                                 .append(":\n");

                    for (Producto prod : encontrados) {
                        resVariedades.append("- ")
                                     .append(prod.getVariedad() != null ? prod.getVariedad() : "Sin variedad")
                                     .append(" ($")
                                     .append(prod.getPrecio())
                                     .append(")\n");
                    }

                    return resVariedades.toString();
                }
            }
            return "No encontré el producto solicitado para consultar variedades.";
        }

        // ===== DETECCIÓN DE PRODUCTO PARA ESPERAR INSTRUCCIÓN =====
        for (Producto p : productos) {
            String nombreLower = p.getNombre().toLowerCase();
            String variedadLower = p.getVariedad() != null ? p.getVariedad().toLowerCase() : "";

            if (msg.contains(nombreLower) || (!variedadLower.isEmpty() && msg.contains(variedadLower))) {
                // Solo confirma que el producto existe, no agrega automáticamente
                String displayName = p.getVariedad() != null ? p.getVariedad() : capitalize(p.getNombre());
                return "Encontré " + displayName + ". Por favor, indica la cantidad que deseas agregar al carrito.";
            }
        }

        // ===== PEDIDO CON CANTIDAD EXPLÍCITA =====
        String[] partes = msg.split(",| y ");
        StringBuilder respuesta = new StringBuilder();

        for (String parte : partes) {
            parte = parte.trim();
            if (parte.isEmpty()) continue;

            double cantidad = detectarCantidadNumero(parte); // Soporta fracciones y kilos
            boolean agregado = false;

            for (Producto p : productos) {
                String nombreLower = p.getNombre().toLowerCase();
                String variedadLower = p.getVariedad() != null ? p.getVariedad().toLowerCase() : "";

                if (parte.contains(nombreLower) || (!variedadLower.isEmpty() && parte.contains(variedadLower))) {
                    String displayName = p.getVariedad() != null ? p.getVariedad() : capitalize(p.getNombre());
                    respuesta.append(agregarAlCarrito(p, cantidad, displayName)).append("\n");
                    agregado = true;
                    break;
                }
            }

            if (!agregado) {
                respuesta.append("No encontré el producto: ").append(parte).append("\n");
            }
        }

        if (respuesta.length() > 0) return respuesta.toString().trim();

        // ===== RESPUESTAS FALLO ALEATORIAS =====
        String[] respuestasFallback = {
            "Hmm 🤔 no estoy seguro, ¿puedes reformularlo?",
            "No entendí eso, pero podemos intentarlo de nuevo 😊",
            "¡Ups! Creo que eso no es un producto válido, intenta otra vez 🛒"
        };
        return respuestasFallback[new Random().nextInt(respuestasFallback.length)];
    }

    // =========================
    // MÉTODO AUXILIAR PARA AGREGAR AL CARRITO
    // =========================
    private String agregarAlCarrito(Producto p, double cantidad, String displayName) {
        int cantidadEntera = (int) Math.ceil(cantidad); // Convertir a entero para el carrito
        if (p.getStock() == null || p.getStock() < cantidadEntera) {
            if (p.getStock() != null && p.getStock() > 0) {
                carritoService.agregar(p, p.getStock());
                return "Solo quedan " + p.getStock() + " unidad(es) de " + displayName + ", las agregué al carrito 📦";
            }
            return "No hay suficiente stock de " + displayName + " 📦";
        }

        carritoService.agregar(p, cantidadEntera);
        return "Perfecto 🙌 Agregué " + cantidad + " unidad(es)/kilo(s) de " + displayName + " al carrito.";
    }

    // =========================
    // DETECTAR CANTIDAD (soporta fracciones y kilos)
    // =========================
    private double detectarCantidadNumero(String msg) {
        msg = msg.toLowerCase();

        // Fracciones y palabras
        if (msg.contains("medio kilo") || msg.contains("1/2 kilo") || msg.contains("1/2k")) return 0.5;
        if (msg.contains("cuarto de kilo") || msg.contains("1/4 kilo") || msg.contains("1/4k")) return 0.25;

        // Números seguidos de "kilo", "kg" o "k"
        Pattern patternKilo = Pattern.compile("(\\d+(\\.\\d+)?)\\s*(kilo|kg|k)");
        Matcher matcherKilo = patternKilo.matcher(msg);
        if (matcherKilo.find()) {
            try { return Double.parseDouble(matcherKilo.group(1)); } 
            catch (NumberFormatException e) { return 1; }
        }

        // Si solo hay número
        Pattern patternNum = Pattern.compile("(\\d+)");
        Matcher matcherNum = patternNum.matcher(msg);
        if (matcherNum.find()) {
            try { return Double.parseDouble(matcherNum.group(1)); } 
            catch (NumberFormatException e) { return 1; }
        }

        return 1;
    }

    // =========================
    // Capitalizar la primera letra
    // =========================
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
}