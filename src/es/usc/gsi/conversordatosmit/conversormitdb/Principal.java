package es.usc.gsi.conversordatosmit.conversormitdb;


import es.usc.gsi.conversordatosmit.interfaz.VentanaPrincipal;

public class Principal {

    // Constructor
    public Principal() {

        // Carga de controladores: al cargar la clase tambien se crea un objeto
        // de cada uno de los controladores: siguen el patron Singleton


        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();

        ventanaPrincipal.setVisible(true);

    }

    public static void main(String[] args) {
        new Principal();
    } // Fin main

} // Fin clase Principal
