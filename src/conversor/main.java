/*

 */
package conversor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 *
 * @author Johan
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        

        String[] tipoConversor = {"Moneda", "Distancia", "Tiempo"};
        boolean continuar = true;
        
        do {
            Object valorSeleccionado = JOptionPane.showInputDialog(null,
                "Elija uno", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                tipoConversor, tipoConversor[0]);

        int indiceTipoConversor = retornarIndice(tipoConversor, valorSeleccionado);

        switch (indiceTipoConversor) {
            case 0:
                List<Moneda> monedas = new ArrayList<>();

                monedas.add(new Moneda("Peso dominicano", 0.018));
                monedas.add(new Moneda("Dolar estadounidense", 1));
                monedas.add(new Moneda("Peso mexicano", 0.052));
                monedas.add(new Moneda("Dolar canadiense", 0.75));
                monedas.add(new Moneda("Euro", 1.07));
                monedas.add(new Moneda("Libra esterlina", 1.22));

                Moneda monedaBase = introducirBase(monedas);

                Moneda monedaConversor=conversorBase(monedas, monedaBase);

                double valorConverir = introducirValor();
                double valorConvertido ;
                try 
                {
                     valorConvertido = monedaBase.obtenerConversion(monedaConversor, valorConverir);
                } catch (Exception e) {
                    break;//salimos del switch
                }
                
                
                JOptionPane.showMessageDialog(null, ("El valor en " + monedaConversor.getNombre()+"s"
                        + " es de " +String.format("%.4f", valorConvertido)));
                break;
            case 1:
                
                List<Distancia> distancias = new ArrayList<>();

                distancias.add(new Distancia("Metro", 1));
                distancias.add(new Distancia("Milimetro", 0.001));
                distancias.add(new Distancia("Centimetro", 0.01));
                distancias.add(new Distancia("Kilometro",1000));
                distancias.add(new Distancia("Milla", 1609.34));
                distancias.add(new Distancia("Pie",0.3048));
                
                Distancia distanciaBase = introducirBase(distancias);

                Distancia distanciaConversor= conversorBase(distancias, distanciaBase);

                valorConverir = introducirValor();
                try{
                    valorConvertido = distanciaBase.obtenerConversion(distanciaConversor, valorConverir);
                }
                catch (Exception ex) {
                    break;//salimos del switch
                }
                
                JOptionPane.showMessageDialog(null, ("El valor en " + distanciaConversor.getNombre()+"s"
                        + " es de " +  String.format("%.4f", valorConvertido)));
                break;

            case 2:
                List<Tiempo> tiempos = new ArrayList<>();

                tiempos.add(new Tiempo("Segundo", 1));
                tiempos.add(new Tiempo("Minuto", 0.0166667));
                tiempos.add(new Tiempo("Hora", 0.000277778));
                tiempos.add(new Tiempo("Milisegundo",0.001));
                tiempos.add(new Tiempo("Microsegundo", 0.000001));
                
                Tiempo tiempoBase = introducirBase(tiempos);

                Tiempo tiempoConversor= conversorBase(tiempos, tiempoBase);

                valorConverir = introducirValor();
                
                try {
                    valorConvertido = tiempoBase.obtenerConversion(tiempoConversor, valorConverir);
                } catch (Exception e) {
                    break;
                }
                
                JOptionPane.showMessageDialog(null, ("El valor en " + tiempoConversor.getNombre()+"s"
                        + " es de " + String.format("%.4f", valorConvertido) ));
                break;

            default:
                JOptionPane.showMessageDialog(null, "Algo inesperado ocurrió", "alert", JOptionPane.ERROR_MESSAGE);
                
                
        }
        
            if (JOptionPane.showConfirmDialog(null, "¿Desea continuar?", "Aviso de salida",
            JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
            continuar=false;
        } 
        } while (continuar);
        

    }

    /**
     * Se encarga de retornar el índice en que se encuentre un arreglo de objetos
     * @param arreglo Arreglo en el que se desea buscar un elemento
     * @param valorBuscar Elemento que se buscará en el arreglo
     * @return índice en que se encuentra el valorBuscar , si no se encuentra retorna -1
     **/
    public static int retornarIndice(Object[] arreglo, Object valorBuscar) {
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] == valorBuscar) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Se encarga de solicitar un valor decimal positivo en donde si no se cumple se llamará recursivamente 
     * hasta su cumplimiento
     * 
     * @return valor decimal positivo
     **/
    public static double introducirValor() {
        try {
            String inputValue = JOptionPane.showInputDialog("Digite el valor a convertir: ");
            if(Double.parseDouble(inputValue)<=0)
                throw new IllegalArgumentException("Valor inesperado");
            return Double.parseDouble(inputValue);
        } catch (NullPointerException e) {
            
            return -1; //Retorna un NullPointerException cuando se cierra la ventana por lo que solamente se deja cerrar
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Valor no esperado", "alert", JOptionPane.ERROR_MESSAGE);
            return introducirValor(); //Se llama de manera recursiva hasta que se digite bien o se cierre la pestaña
        }
    }
    /**
     * Se encarga solicitar la selección de un tipo que extiende de la clase de Cambio T pasada una lista 
     * de tipos
     * @return el tipo seleccionado
     **/
     public static <T extends Cambio> T introducirBase(List<T> valoresCambios)
     {
        String nombreClase= valoresCambios.get(0).getClass().getSimpleName();
         T distanciaSeleccionada = (T) JOptionPane.showInputDialog(null,
                         "Elija "+nombreClase.toLowerCase()+" base", "Input",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        valoresCambios.toArray(), valoresCambios.get(0).getNombre());
         return distanciaSeleccionada;
     }
     
     
     /**
      * Se encarga de llamar al cambio en que se desea convertir el primer cambio
      * @param valoresCambios lista donde T representa una clase que hereda de Cambio
      * con todos los cambio que se desea mostrar
      * @param cambioBase cambio que se desea convertir en otra unidad
      * @return retorna el objeto seleccionado de la lista de  valoresCambios
      **/
     public static <T extends Cambio> T conversorBase (List<T> valoresCambios,T cambioBase)
     {
        String nombreClase= cambioBase.getClass().getSimpleName();
        T distanciaConversor
                        = (T) JOptionPane.showInputDialog(null,
                                "Elija "+nombreClase.toLowerCase()+" a convertir", "Input",
                                JOptionPane.INFORMATION_MESSAGE, null,
                                valoresCambios.stream().filter(cambio -> !cambio.getNombre().equalsIgnoreCase(cambioBase.getNombre())).collect(Collectors.toList()).toArray(),
                                valoresCambios.get(0).getNombre());
          
          return distanciaConversor;
     }
    

}
