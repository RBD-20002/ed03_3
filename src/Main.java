import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    static {
        try {
            // Configuración del archivo de logs
            FileHandler fileHandler = new FileHandler("logs.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.severe("Error al configurar el archivo de logs: " + e.getMessage());
            for (StackTraceElement element : e.getStackTrace()) {
                logger.severe(element.toString());  // Registrar la traza completa
            }
        }
    }
    public static void main(String[] args) {
        List<String> productos = new ArrayList<>();
        List<Double> precios = new ArrayList<>();

        // Añadir productos y precios
        productos.add("Laptop");
        precios.add(1200.0);
        productos.add("Telefono");
        precios.add(600.0);
        productos.add("Auriculares");
        precios.add(150.0);

        // Intentar calcular el total de la compra
        double totalCompra = calcularTotalCompra(productos, precios, new int[]{1, 2, 1});

        System.out.println("Total de la compra con impuestos y descuento: $" + Math.round(totalCompra * 100.0) / 100.0);
    }
    // Calcula el total de una compra en base a los productos y cantidades
    public static double calcularTotalCompra(List<String> productos, List<Double> precios, int[] cantidades) {
        double subtotal = calcularSubtotal(productos, precios, cantidades);
        logger.info("Subtotal calculado: " + subtotal);

        double descuento = aplicarDescuento(subtotal); // Error en descuento
        logger.info("Descuento aplicado: " + descuento);

        double totalConDescuento = subtotal - descuento;

        // Error lógico: no se aplica correctamente la función calcularImpuestos
        double totalConImpuestos = calcularImpuestos(totalConDescuento);
        logger.info("Total tras aplicar impuestos: " + totalConImpuestos);

        return totalConImpuestos;
    }
    // Calcula el subtotal de la compra
    public static double calcularSubtotal(List<String> productos, List<Double> precios, int[] cantidades) {
        double subtotal = 0;
        for (int i = 0; i < productos.size(); i++) {
            if (cantidades[i] > 0) {
                subtotal += precios.get(i) * cantidades[i];
                logger.info("Producto: " + productos.get(i) + ", Cantidad: " + cantidades[i] +
                        ", Precio unitario: " + precios.get(i) +
                        ", Subtotal actual: " + subtotal);
            } else {
                logger.warning("Cantidad inválida para el producto: " + productos.get(i));
            }
        }
        System.out.println("Subtotal: $" + subtotal);
        return subtotal;
    }

    public static double aplicarDescuento(double subtotal) {
        if (subtotal > 1000) {
            return subtotal * 0.15; // Descuento del 15%
        } else if (subtotal > 500) {
            return subtotal * 0.10; // Descuento del 10%
        }
        // Error: sin descuento
        return 0.0;
    }
    // Calcula los impuestos aplicados al total con descuento
    public static double calcularImpuestos(double total) {
        final double IMPUESTO = 0.21; // Impuesto del 21%
        double impuestos = total * IMPUESTO;
        logger.info("Impuestos calculados: " + impuestos);
        return total + impuestos;
    }
}
