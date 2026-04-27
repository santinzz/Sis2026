/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.sis.integradorasis2026;

import java.util.List;
import com.sis.integradorasis2026.utils.Menu;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author santi
 */
public class IntegradoraSIS2026 {
    static Scanner lector = new Scanner(System.in);

    public static void main(String[] args) {
        SistemaSIS sistema = new SistemaSIS();
        sistema.RegistrarUsuario(
                new UsuarioFinal(
                        "hola", 
                        "tilin dios", 
                        new Direccion("Alamo", 2604, "Monclova", "Coahuila", 25720), 
                        "8666426890", 
                           "santiagogonuz@gmail.com",
                        "hola123",
                            "santinzz", 
                                new Date()
                ));
        System.out.println("***** Sistema intercambio de servicios 2026 *****");
        
        while (true)
        {
            if (!Sesion.GetInstancia().EstaLogeado())
            {
                Menu menu = new Menu()
                    .Titulo("Autenticacion del sistema")
                    .AgregarCampo("Iniciar sesion")
                    .AgregarCampo("Crear usuario")
                    .AgregarCampo("Salir")
                    .Peticion("Ingrese una opcion:= ");
        
                int opcion = menu.MostrarYLeer(lector);
                
                switch (opcion)
                {
                    case 1: 
                    {
                        System.out.println("Inicio de sesion");
                        System.out.print("Ingrese su correo electonico:= ");
                        String correo = lector.nextLine();
                        System.out.print("Ingrese su contrasena:= ");
                        String contrasena = lector.nextLine();
                        
                        if (sistema.BuscarUsuario(correo) != null)
                        {
                            sistema.IniciarSesion(correo, contrasena);
                        }
                        break;
                    }
                    case 2: 
                    {
                        Usuario usuario = PedirUsuario();
                        sistema.RegistrarUsuario(usuario);
                        System.out.println("Usuario " + usuario.GetEmail() + " creado exitosamente");
                        break;
                    }
                    case 3:
                        System.out.println("Saliendo del programa");
                        break;
                }
                
                if (opcion == 3) break;
            } else
            {
                
                while (true) {
                    Usuario usuario = Sesion.GetInstancia().GetUsuarioActual();

                    if (usuario instanceof UsuarioFinal)
                    {
                        System.out.println("***** PANEL USUARIO *****");
                        System.out.println("Usuario: " + ((UsuarioFinal) usuario).GetNick());
                        System.out.println("1. Catalago servicios");
                        System.out.println("2. Mi perfil");
                        System.out.println("3. Cerrar sesion");
                        System.out.print("Ingrese una opcion:= ");
                        if (!lector.hasNextInt()) {
                            System.out.println("");
                            continue;
                        }

                        int opcion = Integer.parseInt(lector.nextLine());

                        switch (opcion)
                        {
                            case 1: {
                                Menu menuServicios = new Menu(60)
                                        .Titulo("Catalogo servicios disponibles");
                                break;
                            }
                            case 2: {
                                Menu menuUsuario = new Menu(60)
                                        .Titulo("Informacion usuario")
                                        .AgregarCampo("Nombre: " + usuario.GetNombre() + " " + usuario.GetApellidos())
                                        .AgregarCampo("Direccion: " + usuario.GetDireccion())
                                        .AgregarCampo("Telefono contacto: " + usuario.GetTelefonoContacto())
                                        .AgregarCampo("Correo electronico: " + usuario.GetEmail())
                                        .AgregarCampo("Tipo usuario: " + usuario.GetTipoUsuario())
                                        .AgregarCampo("Nickname: " + ((UsuarioFinal) usuario).GetNick())
                                        .AgregarCampo("Fecha Alta: " + ((UsuarioFinal) usuario).GetFechaAlta());
                                System.out.println(menuUsuario.Construir(false));
                                break;
                            }
                            case 3: {
                                System.out.println("Cerrando sesion");
                                Sesion.GetInstancia().Logout();
                                break;
                            }

                        }
                        
                        if (opcion == 3) break;
                    } else if (usuario instanceof Administrador)
                    {
                        System.out.println("***** PANEL ADMINISTRADOR *****");
                    } else 
                    {
                        Sesion.GetInstancia().Logout();
                        break;
                    }
                }
            }
        }
    }
    
    private static UsuarioFinal PedirUsuario() 
    {
        System.out.print("Ingrese su nombre:= ");
        String nombre = lector.nextLine();

        System.out.print("Ingrese sus apellidos:= ");
        String apellidos = lector.nextLine();

        System.out.println("Ingrese su direccion");
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
        Direccion direccion = new Direccion(calle, numero, ciudad, estado, codigoPostal);

        System.out.print("Telefono contacto:= ");
        String telefonoContacto = lector.nextLine();

        System.out.print("Correo electronico:= ");
        String correoElectronico = lector.nextLine();

        System.out.print("Contrasena:= ");
        String contrasena = lector.nextLine();

        System.out.print("Tipo usuario (Administrador | Usuario final) :=");
        String tipoUsuarioString = lector.nextLine();

        System.out.print("Ingrese su nick:= ");
        String nick = lector.nextLine();

        return new UsuarioFinal(nombre, apellidos, direccion, telefonoContacto, correoElectronico, contrasena, nick, new Date());
    }
}
