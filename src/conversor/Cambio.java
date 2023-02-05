
package conversor;

import javax.swing.JOptionPane;


public abstract class Cambio {
    private String nombre;
    private double valorReferente;
    
    
    public Cambio(String nombre, double valorReferente) {
        this.nombre = nombre;
        this.valorReferente= valorReferente;
    }
  
    public String getNombre() {
        return nombre;
    }

    public double getValorReferente() {
        return valorReferente;
    }

    public void setValorReferente(double valorReferente) {
        this.valorReferente = valorReferente;
    }
    
    /**
     * Método que se encarga de realizar la conversión de un Cambio a otro basándose en 
     * el valor de referencia en común de los cambio
     * @param cambio Cambio a convertir
     * @return Representación de una unidad del cambio antiguo al nuevo cambio
     **/
    private double obtenerConversion(Cambio cambio)
    {
        try 
        {
            if(this.valorReferente<=0||cambio.getValorReferente()<=0)
                throw new IllegalArgumentException("Valor no posible");
            return this.valorReferente/ cambio.getValorReferente();
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "No es posible realizar esta operación", "alert", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    /**
     * Método que toma un cambio y su cantidad para retornar la conversión en el cambio entrada
     * @param moneda Representa el Cambio en que se desea convertir la cantidad
     * @param cantidad Cantidad del Cambio
     * 
     * @return La representación en el Cambio pasado como parámetro por la cantidad
     * @throws IllegalArgumentException al pasar como parámetro un valor menor o igual a 0
     **/
    
    public double obtenerConversion(Cambio cambio, double cantidad){
        if(cantidad<=0)
            throw new IllegalArgumentException("Valor fuera de rango");
        
        return obtenerConversion(cambio)*cantidad;
    }

    @Override
    public String toString() {
        return   this.nombre ;
    }
}
