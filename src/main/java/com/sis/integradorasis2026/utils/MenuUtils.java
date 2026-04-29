package com.sis.integradorasis2026.utils;

import java.util.List;
import java.util.Scanner;

import com.sis.integradorasis2026.CategoriaServicio;
import com.sis.integradorasis2026.Direccion;
import com.sis.integradorasis2026.GestorServicios;
import com.sis.integradorasis2026.Servicio;
import com.sis.integradorasis2026.SistemaSIS;
import com.sis.integradorasis2026.UsuarioFinal;
import com.sis.integradorasis2026.ejemplo.DatosEjemplo;

public class MenuUtils {
    public static void MostrarCatalogo(GestorServicios gestorServicios, Scanner lector, SistemaSIS sistema) {
        DatosEjemplo.CargarServiciosEjemploSiVacio(gestorServicios, sistema);

        while (true) {
            Menu menuCatalogo = new Menu(50)
                    .Titulo("Catalogo de servicios")
                    .AgregarCampo("Ver por categoria")
                    .AgregarCampo("Ver todos")
                    .AgregarCampo("Volver")
                    .Peticion("Ingrese una opcion:= ");

            int opcion = menuCatalogo.MostrarYLeer(lector);
            switch (opcion) {
                case 1:
                    MostrarCatalogoPorCategoria(gestorServicios, lector);
                    break;
                case 2:
                    MostrarCatalogoTodos(gestorServicios, lector);
                    break;
                case 3:
                    return;
            }
        }
    }

    public static void MostrarCatalogoPorCategoria(GestorServicios gestorServicios, Scanner lector) {
        CategoriaServicio categoriaSeleccionada = MenuEnum.MostrarMenuEnum(CategoriaServicio.class, lector);

        List<Servicio> filtrados = GestorServicios.FiltrarServiciosPorCategoria(gestorServicios.GetServicios(), categoriaSeleccionada);
        MostrarListaServicios(filtrados, lector, "Servicios - " + categoriaSeleccionada);
    }

    public static void MostrarCatalogoTodos(GestorServicios gestorServicios, Scanner lector) 
    {
        List<Servicio> servicios = gestorServicios.GetServicios();
        MostrarListaServicios(servicios, lector, "Todos los servicios");
    }

    public static void MostrarListaServicios(List<Servicio> servicios, Scanner lector, String titulo) {
        if (servicios.isEmpty()) 
            {
            System.out.println("No hay servicios disponibles en este catalogo.");
            return;
        }

        Menu menuServicios = new Menu(80)
                .Titulo(titulo)
                .Peticion("Seleccione un servicio:= ");

        for (Servicio servicio : servicios) {
            menuServicios.AgregarCampo(servicio.InfoResumida());
        }
        menuServicios.AgregarCampo("Volver");

        int opcion = menuServicios.MostrarYLeer(lector);
        if (opcion == servicios.size() + 1) 
            {
            return;
        }

        Servicio seleccionado = servicios.get(opcion - 1);
        System.out.println(seleccionado.InfoAll());
        System.out.print("Presione Enter para volver...");
        lector.nextLine();
    }

    public static void MenuPerfilUsuario(UsuarioFinal usuario, Scanner lector) {
        Menu menuUsuario = new Menu(60)
                .Titulo("Informacion usuario")
                .AgregarCampo("Nombre: " + usuario.GetNombre() + " " + usuario.GetApellidos())
                .AgregarCampo("Direccion: " + usuario.GetDireccion())
                .AgregarCampo("Telefono contacto: " + usuario.GetTelefonoContacto())
                .AgregarCampo("Correo electronico: " + usuario.GetEmail())
                .AgregarCampo("Tipo usuario: " + usuario.GetTipoUsuario())
                .AgregarCampo("Nickname: " + usuario.GetNick())
                .AgregarCampo("Fecha Alta: " + usuario.GetFechaAlta());
        System.out.println(menuUsuario.Construir(false));
    }

        public static void MenuEditarPerfil(UsuarioFinal usuario, Scanner lector) {
        Menu menuUsuario = new Menu(60)
                .Titulo("Editar perfil")
                .AgregarCampo("1. Cambiar nombre")
                .AgregarCampo("2. Cambiar direccion")
                .AgregarCampo("3. Cambiar telefono contacto")
                .AgregarCampo("4. Cambiar contrasena")
                .AgregarCampo("5. Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcionEdicion = menuUsuario.MostrarYLeer(lector);
        switch (opcionEdicion) {
            case 1: {
                System.out.print("Ingrese nuevo nombre:= ");
                String nuevoNombre = lector.nextLine();
                usuario.SetNombre(nuevoNombre);
                System.out.println("Nombre actualizado correctamente");
                break;
            }
            case 2: {
                System.out.println("Ingrese nueva direccion");
                System.out.print("Calle:= ");
                String calle = lector.nextLine();
                System.out.print("Numero:= ");
                int numero = Integer.parseInt(lector.nextLine());
                System.out.print("Ciudad:= ");
                String ciudad = lector.nextLine();
                System.out.print("Estado:= ");
                String estado = lector.nextLine();
                System.out.print("Codigo postal:= ");
                int codigoPostal = Integer.parseInt(lector.nextLine());
                Direccion nuevaDireccion = new Direccion(calle, numero, ciudad, estado, codigoPostal);
                usuario.SetDireccion(nuevaDireccion);
                System.out.println("Direccion actualizada correctamente");
                break;
            }
            case 3: {
                System.out.print("Ingrese nuevo telefono contacto:= ");
                String nuevoTelefono = lector.nextLine();
                usuario.SetTelefonoContacto(nuevoTelefono);
                System.out.println("Telefono contacto actualizado correctamente");
                break;
            }
            case 4: {
                System.out.print("Ingrese nueva contrasena:= ");
                String nuevaContrasena = lector.nextLine();
                usuario.SetContrasena(nuevaContrasena);
                System.out.println("Contrasena actualizada correctamente");
                break;
            }
            case 5: {
                break;
            }
        }
    }
}
