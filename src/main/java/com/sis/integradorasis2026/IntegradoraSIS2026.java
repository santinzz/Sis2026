package com.sis.integradorasis2026;
 
import java.util.List;

import com.sis.integradorasis2026.ejemplo.DatosEjemplo;
import com.sis.integradorasis2026.utils.Menu;
import com.sis.integradorasis2026.utils.MenuUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
        sistema.GetGestorUsuarios().RegistrarUsuario(
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
        sistema.GetGestorUsuarios().RegistrarUsuario(
            new Administrador(
                "administrador",
                "mayor",
                new Direccion("Admin", 1, "Admin", "Admin", 25720),
                "admin",
                "admin",
                "administrador"
            )
        );
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

                        if (sistema.GetGestorUsuarios().BuscarUsuario(correo) == null)
                        {
                            System.out.println("El usuario no existe");
                            break;
                        }

                        System.out.print("Ingrese su contrasena:= ");
                        String contrasena = lector.nextLine();
                       
                        sistema.GetGestorUsuarios().IniciarSesion(correo, contrasena);
                        break;
                    }
                    case 2:
                    {
                        Usuario usuario = PedirUsuario();
                        sistema.GetGestorUsuarios().RegistrarUsuario(usuario);
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
                        System.out.println("1. Catalogo servicios");
                        System.out.println("2. Mi perfil");
                        System.out.println("3. Editar perfil");
                        System.out.println("4. Eliminar cuenta");
                        System.out.println("5. Cerrar sesion");
                        System.out.print("Ingrese una opcion:= ");
                        if (!lector.hasNextInt()) {
                            System.out.println("");
                            continue;
                        }
 
                        int opcion = Integer.parseInt(lector.nextLine());
 
                        switch (opcion)
                        {
                            case 1: {
                                MenuUtils.MostrarCatalogo(sistema.GetGestorServicios(), lector, sistema);
                                break;
                            }
                            case 2: {
                                MenuUtils.MenuPerfilUsuario((UsuarioFinal) usuario, lector);;
                                break;
                            }
                            case 3: {
                                MenuUtils.MenuEditarPerfil((UsuarioFinal) usuario, lector);
                                break;
                            }
                            case 4: {
                                System.out.println("¿Está seguro que desea eliminar su cuenta? Esta acción no se puede deshacer. (s/n)");
                                System.out.println("Se eliminara la cuenta y se cerrara la sesion");
                                System.out.print("Ingrese su respuesta:= ");
                                String confirmacion = lector.nextLine();
                                if (confirmacion.equalsIgnoreCase("s")) {
                                    System.out.println("Eliminando cuenta");
                                    sistema.GetGestorUsuarios().EliminarUsuario(usuario.GetEmail());
                                } else {
                                    System.out.println("Eliminación cancelada");
                                }
                            }
                            case 5: {
                                System.out.println("Cerrando sesion");
                                Sesion.GetInstancia().Logout();
                                break;
                            }
 
                        }
                       
                        if (opcion == 4) break;
                    } else if (usuario instanceof Administrador)
                    {
                        System.out.println("***** PANEL ADMINISTRADOR *****");
                        System.out.println("1. Gestion de usuarios");
                        System.out.println("2. Gestion de servicios");
                        System.out.println("3. Generar reportes");
                        System.out.println("4. Cerrar sesion");
                        System.out.print("Ingrese una opcion:= ");
                        int opcionAdmin = Integer.parseInt(lector.nextLine());
 
                        switch (opcionAdmin)
                        {
                            case 1: {
                                Menu menuAdmin = new Menu(60)
                                    .Titulo("Gestion de usuarios")
                                    .AgregarCampo("Consulta de usuarios")
                                    .AgregarCampo("Baja de usuarios")
                                    .Peticion("Ingrese una opcion:= ");
                                int opcionGestionUsuarios = menuAdmin.MostrarYLeer(lector);
                                switch (opcionGestionUsuarios)
                                {
                                    case 1: {
                                        List<Usuario> usuarios = sistema.GetGestorUsuarios().GetUsuarios();
                                        Menu menuUsuarios = new Menu(80)
                                            .Titulo("Usuarios registrados")
                                            .Peticion("Seleccione un usuario:= ");
                                        for (Usuario u : usuarios) {
                                            menuUsuarios.AgregarCampo(u.GetEmail() + " - " + u.GetNombre() + " " + u.GetApellidos());
                                        }
                                        menuUsuarios.AgregarCampo("Volver");
                                        int opcionUsuario = menuUsuarios.MostrarYLeer(lector);
                                        if (opcionUsuario == usuarios.size() + 1) {
                                            break;
                                        }
                                        MenuUtils.MenuPerfilUsuario((UsuarioFinal) usuarios.get(opcionUsuario - 1), lector);
                                        break;
                                    }
                                    case 2: {
                                        break;
                                    }
                                }
                                break;
                            }
                            case 2: {
                                Menu menuAdmin = new Menu(60)
                                    .Titulo("Gestion de servicios")
                                    .AgregarCampo("Alta de servicios")
                                    .AgregarCampo("Consulta de servicios")
                                    .AgregarCampo("Baja de servicios")
                                    .AgregarCampo("Verificacion de servicios")
                                    .Peticion("Ingrese una opcion:= ");
                                menuAdmin.MostrarYLeer(lector);
                                int opcionGestionServicios = Integer.parseInt(lector.nextLine());
                               
 
                                switch (opcionGestionServicios)
                                {
                                    case 1: {
 
                                        break;
                                    }
                                }
                                break;
                            }
                            case 3: {
                                break;
                            }
                            case 4: {
                                System.out.println("Cerrando sesion");
                                Sesion.GetInstancia().Logout();
                                break;
                            }
                        }
                    } else
                    {
                        Sesion.GetInstancia().Logout();
                        break;
                    }
                }
            }
        }
    }
   
    public static UsuarioFinal PedirUsuario()
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
 
        System.out.print("Ingrese su nick:= ");
        String nick = lector.nextLine();
 
        return new UsuarioFinal(nombre, apellidos, direccion, telefonoContacto, correoElectronico, contrasena, nick, new Date());
    }
}