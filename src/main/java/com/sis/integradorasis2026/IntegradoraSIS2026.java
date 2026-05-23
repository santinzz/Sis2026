// COMENTARIO DE PRUEBA
//Revision de cambios
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.sis.integradorasis2026;

import com.sis.integradorasis2026.utils.Color;
import java.util.List;
import com.sis.integradorasis2026.utils.Menu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class IntegradoraSIS2026 {
    static Scanner lector = new Scanner(System.in);

    // Lista de categorias predefinidas para los servicios
    private static final List<String> CATEGORIAS_DEFAULT = Arrays.asList(
        "Hogar",
        "Tecnologia",
        "Educacion",
        "Salud",
        "Transporte",
        "Eventos"
    );



    // Metodo principal del programa
    // Inicializa el sistema, y crea usuarios de prueba (administrador y usuario final)
        // Usuario Final: Santiago Gonzalez Nunez
        public static void main(String[] args) {
            SistemaSIS sistema = new SistemaSIS();
            sistema.GetGestorUsuarios().RegistrarUsuario(
                    new UsuarioFinal(
                            "Santiago", 
                            "Gonzalez Nunez", 
                            new Direccion("Alamo", 2604, "Monclova", "Coahuila", 25720), 
                            "8666426890", 
                            "santiagogonuz@gmail.com",
                            "hola123",
                                "santinzz", 
                                    new Date()
                    ));
        // Usuario Administrador: Devany Esparza Cruz
            sistema.GetGestorUsuarios().RegistrarUsuario(
                new Administrador(
                    "Devany", 
                    "Esparza Cruz", 
                    new Direccion("Central", 1, "Monclova", "Coahuila", 25700), 
                    "8660000000", 
                    "admin@sys",
                    "admin123"
                )
            );



    //Mostrar menu principal
        System.out.println("***** Sistema intercambio de servicios 2026 *****");
        
        // Bucle principal del programa, este se mantiene activo hasta que el usuario decida salir
        while (true)
        {
            // Si no hay usuario logeado, mostrar menu de autenticacion
            if (!Sesion.GetInstancia().EstaLogeado())
            {
                // Menu de autenticacion
                Menu menu = new Menu()
                    .Titulo("Autenticacion del sistema")
                    .AgregarCampo("Iniciar sesion")
                    .AgregarCampo("Crear usuario")
                    .AgregarCampo("Salir")
                    .Peticion("Ingrese una opcion:= ");
                int opcion = menu.MostrarYLeer(lector);
                
                // Procesar opcion ingresada en la autenticacion
                switch (opcion)
                {
                    // Opcion 1: Iniciar sesion
                    case 1: 
                    {
                        System.out.println("Inicio de sesion");
                        System.out.print("Ingrese su correo electonico:= ");
                        String correo = lector.nextLine();

                        if (sistema.GetGestorUsuarios().BuscarUsuario(correo) == null) 
                        {
                           System.out.println(Color.colorize("El usuario no existe intente denuevo!!", Color.RED));
                           break;
                        }
                        
                        System.out.print("Ingrese su contrasena:= ");
                        String contrasena = lector.nextLine();
                        
                        
                        sistema.GetGestorUsuarios().IniciarSesion(correo, contrasena);
                        break;
                    }
                    // Opcion 2: Crear usuario
                    case 2: 
                    {
                        Usuario usuario = PedirUsuario();
                        sistema.GetGestorUsuarios().RegistrarUsuario(usuario);
                        System.out.println("Usuario " + usuario.GetEmail() + " creado exitosamente");
                        break;
                    }
                    // Opcion 3: Salir del programa
                    case 3:
                        System.out.println("Saliendo del programa");
                        break;
                }
                
                // Si el usuario desea salir, terminar el programa
                if (opcion == 3) break;
            } 




            // Si hay un usuario logeado, mostrar menu principal dependiendo del tipo de usuario (administrador o usuario final)
            else
            {
                // Bucle del menu principal, se mantiene activo hasta que el usuario decida cerrar sesion o eliminar su cuenta
                while (true) {
                    // Obtener el usuario actual logeado en el sistema
                    Usuario usuario = Sesion.GetInstancia().GetUsuarioActual();


                    // Si el usuario es un usuario final, mostrar menu de usuario final
                    if (usuario instanceof UsuarioFinal)
                    {
                        // Menu de usuario final
                        Menu menuUsuario = new Menu()
                                .Titulo("Panel usuario")
                                .AgregarCampo("Servicios")
                                .AgregarCampo("Pagos y balance")
                                .AgregarCampo("Mi perfil")
                                .AgregarCampo("Cerrar sesion")
                                .Peticion("Ingrese una opcion:= ");
                        int opcion = menuUsuario.MostrarYLeer(lector);
                        switch (opcion) {
                            // Opcion 1: Menu de servicios del usuario
                            case 1:
                                MenuServiciosUsuario((UsuarioFinal) usuario, sistema);
                                break;
                            // Opcion 2: Menu de pagos y balance del usuario
                            case 2:
                                MenuPagosUsuario((UsuarioFinal) usuario, sistema);
                                break;
                            // Opcion 3: Menu de perfil del usuario
                            case 3:
                                MenuPerfilUsuarioPrincipal((UsuarioFinal) usuario, sistema);
                                break;
                            // Opcion 4: Cerrar sesion
                            case 4:
                                Sesion.GetInstancia().Logout();
                                break;
                        }


                    // Si el usuario es un administrador, mostrar menu de administrador
                    } else if (usuario instanceof Administrador)
                    {
                        Menu menuAdmin = new Menu()
                                .Titulo("Panel administrador")
                                .AgregarCampo("Servicios")
                                .AgregarCampo("Usuarios")
                                .AgregarCampo("Reportes")
                                .AgregarCampo("Cerrar sesion")
                                .Peticion("Ingrese una opcion:= ");
                        int opcionAdmin = menuAdmin.MostrarYLeer(lector);
                        // Procesar opcion ingresada en el menu de administrador
                        switch (opcionAdmin)
                        {
                            // Opcion 1: Menu de gestion de servicios del administrador
                            case 1 -> MenuServiciosAdmin(sistema);
                            // Opcion 2: Menu de gestion de usuarios del administrador
                            case 2 -> MenuUsuariosAdmin(sistema);
                            // Opcion 3: Menu de gestion de reportes del administrador
                            case 3 -> MenuReportesAdmin(sistema);
                            // Opcion 4: Cerrar sesion
                            case 4 -> Sesion.GetInstancia().Logout();
                        }
                    // Si el tipo de usuario no es reconocido, cerrar sesion por seguridad
                    } else 
                    {
                        Sesion.GetInstancia().Logout();
                        break;
                    }
                }
            }
        }
    }





    //Métodos de comportamiento
    //Método para mostrar el perfil del usuario, con toda su información relevante
    private static void MenuPerfilUsuario(UsuarioFinal usuario, Scanner lector) {
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



    // Método para mostrar un menu de edición del perfil del usuario, permitiendo modificar su información personal
    private static void MenuEditarPerfil(UsuarioFinal usuario, Scanner lector) {
        Menu menuUsuario = new Menu(60)
                .Titulo("Editar perfil")
                .AgregarCampo("Cambiar nombre")
                .AgregarCampo("Cambiar direccion")
                .AgregarCampo("Cambiar telefono contacto")
                .AgregarCampo("Cambiar contrasena")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcionEdicion = menuUsuario.MostrarYLeer(lector);
        // Procesar opcion ingresada para editar el perfil del usuario
        switch (opcionEdicion) {
            // Opcion 1: Cambiar nombre del usuario
            case 1: {
                System.out.print("Ingrese nuevo nombre:= ");
                String nuevoNombre = lector.nextLine();
                usuario.SetNombre(nuevoNombre);
                System.out.println("Nombre actualizado correctamente"); 
                break;
            }
            // Opcion 2: Cambiar direccion del usuario
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
            // Opcion 3: Cambiar telefono contacto del usuario
            case 3: {
                System.out.print("Ingrese nuevo telefono contacto:= ");
                String nuevoTelefono = lector.nextLine();
                usuario.SetTelefonoContacto(nuevoTelefono);
                System.out.println("Telefono contacto actualizado correctamente"); 
                break;
            }
            // Opcion 4: Cambiar contrasena del usuario
            case 4: {
                System.out.print("Ingrese nueva contrasena:= ");
                String nuevaContrasena = lector.nextLine();
                usuario.SetContrasena(nuevaContrasena);
                System.out.println("Contrasena actualizada correctamente"); 
                break;
            }
            // Opcion 5: Volver al menu anterior sin hacer cambios
            case 5: {
                break;
            }
        }
    }



    // Método para mostrar el menu principal del perfil del usuario
    private static void MenuPerfilUsuarioPrincipal(UsuarioFinal usuario, SistemaSIS sistema) {
        Menu menuPerfil = new Menu(60)
                .Titulo("Mi perfil")
                .AgregarCampo("Ver informacion de mi perfil")
                .AgregarCampo("Editar informacion de mi perfil")
                .AgregarCampo("Eliminar mi cuenta")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcionPerfil = menuPerfil.MostrarYLeer(lector);
        // Procesar opcion ingresada en el menu de perfil del usuario
        switch (opcionPerfil) {
            // Opcion 1: Ver informacion del perfil del usuario
            case 1:
                MenuPerfilUsuario(usuario, lector);
                break;
            // Opcion 2: Editar informacion del perfil del usuario
            case 2:
                MenuEditarPerfil(usuario, lector);
                break;
            // Opcion 3: Eliminar la cuenta del usuario
            case 3:
                sistema.GetGestorUsuarios().EliminarUsuario(usuario.GetEmail());
                Sesion.GetInstancia().Logout();
                System.out.println("Cuenta eliminada exitosamente");
                break;
            // Opcion 4: Volver al menu anterior sin hacer cambios
            case 4:
                break;
        }
    }



    // Método para mostrar el menu de servicios del usuario
    private static void MenuServiciosUsuario(UsuarioFinal usuario, SistemaSIS sistema) {
        Menu menuServicios = new Menu(60)
                .Titulo("Servicios")
                .AgregarCampo("Alta de servicio")
                .AgregarCampo("Consulta servicios")
                .AgregarCampo("Modificar servicio")
                .AgregarCampo("Baja de servicio")
                .AgregarCampo("Calificar servicios recibidos")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcion = menuServicios.MostrarYLeer(lector);
        // Procesar opcion ingresada en el menu de servicios del usuario
        switch (opcion) {
            // Opcion 1: Alta de servicio por parte del usuario
            case 1:
                AltaServicioUsuario(usuario, sistema);
                break;
            // Opcion 2: Consulta de servicios, mostrando tanto el catalogo publico como los servicios propios del usuario
            case 2:
                Menu menuConsulta = new Menu(60)
                        .Titulo("Consulta de servicios")
                        .AgregarCampo("Catalogo publico")
                        .AgregarCampo("Mis servicios")
                        .AgregarCampo("Volver")
                        .Peticion("Ingrese una opcion:= ");
                int opcionConsulta = menuConsulta.MostrarYLeer(lector);
                if (opcionConsulta == 1) {
                    MostrarCatalogo(sistema.GetGestorServicios(), lector, sistema);
                } else if (opcionConsulta == 2) {
                    MostrarMisServicios(usuario);
                }
                break;
            // Opcion 3: Modificar servicio por parte del usuario
            case 3:
                ModificarServicioUsuario(usuario);
                break;
            // Opcion 4: Baja de servicio por parte del usuario
            case 4:
                BajaServicioUsuario(usuario, sistema);
                break;
            // Opcion 5: Calificar servicios recibidos por parte del usuario
            case 5:
                CalificarServiciosRecibidos(usuario);
                break;
            // Opcion 6: Volver al menu anterior sin hacer cambios
            case 6:
                break;
        }
    }



    // Método para mostrar el menu de pagos y balance del usuario
    private static void MenuPagosUsuario(UsuarioFinal usuario, SistemaSIS sistema) {
        Menu menuPagos = new Menu(60)
                .Titulo("Pagos y balance")
                .AgregarCampo("Pagar servicio")
                .AgregarCampo("Historial de pagos")
                .AgregarCampo("Balance y movimientos")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcion = menuPagos.MostrarYLeer(lector);
        // Procesar opcion ingresada en el menu de pagos y balance del usuario
        switch (opcion) {
            // Opcion 1: Pagar un servicio desde el menu de pagos del usuario
            case 1:
                ComprarServicio(usuario, sistema);
                break;
            // Opcion 2: Mostrar el historial de pagos realizados y recibidos por el usuario
            case 2:
                MostrarHistorialPagos(usuario);
                break;
            // Opcion 3: Mostrar el balance actual del usuario y sus movimientos recientes
            case 3:
                MenuBalanceUsuario(usuario);
                break;
            // Opcion 4: Volver al menu anterior sin hacer cambios
            case 4:
                break;
        }
    }



    // Método para mostrar el menu de balance del usuario
    private static void MenuBalanceUsuario(UsuarioFinal usuario) {
        Menu menuBalance = new Menu(60)
                .Titulo("Balance")
                .AgregarCampo("Ver saldo")
                .AgregarCampo("Depositar")
                .AgregarCampo("Retirar")
                .AgregarCampo("Ver movimientos")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcion = menuBalance.MostrarYLeer(lector);
        // Procesar opcion ingresada en el menu de balance del usuario
        switch (opcion) {
            // Opcion 1: Ver saldo actual del usuario
            case 1:
                System.out.printf("Saldo actual: $%.2f%n", usuario.GetCuenta().GetSaldo());
                break;
            // Opcion 2: Depositar dinero en la cuenta del usuario
            case 2: {
                double monto = LeerDouble("Monto a depositar:= ");
                try {
                    usuario.GetCuenta().Depositar(monto);
                    System.out.println("Deposito realizado correctamente.");
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            }
            // Opcion 3: Retirar dinero de la cuenta del usuario
            case 3: {
                double monto = LeerDouble("Monto a retirar:= ");
                try {
                    usuario.GetCuenta().Retirar(monto);
                    System.out.println("Retiro realizado correctamente.");
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            }
            // Opcion 4: Ver movimientos de la cuenta del usuario
            case 4:
                List<Transaccion> movimientos = usuario.GetCuenta().GetHistorialTransacciones();
                if (movimientos.isEmpty()) {
                    System.out.println("No hay movimientos registrados.");
                } else {
                    for (Transaccion transaccion : movimientos) {
                        System.out.println(transaccion.toString());
                    }
                }
                break;
            // Opcion 5: Volver al menu anterior sin hacer cambios
            case 5:
                break;
        }
    }



    // Método para mostrar el menu de gestion de servicios del administrador
    private static void MenuServiciosAdmin(SistemaSIS sistema) {
        Menu menuServicios = new Menu(60)
                .Titulo("Gestion de servicios")
                .AgregarCampo("Consulta servicios")
                .AgregarCampo("Alta de servicio")
                .AgregarCampo("Baja de servicio")
                .AgregarCampo("Modificar servicio")
                .AgregarCampo("Verificar publicaciones de servicios")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcionServicios = menuServicios.MostrarYLeer(lector);
        // Procesar opcion ingresada en el menu de gestion de servicios del administrador
        switch (opcionServicios) {
            // Opcion 1: Consulta de servicios registrados en el sistema, mostrando su informacion resumida
            case 1:
                List<Servicio> servicios = sistema.GetGestorServicios().GetServicios();
                if (servicios.isEmpty()) {
                    System.out.println("No hay servicios registrados.");
                } else {
                    for (Servicio servicio : servicios) {
                        System.out.println(servicio.InfoResumida());
                    }
                }
                break;
            // Opcion 2: Alta de servicio por parte del administrador, permitiendo crear un servicio directamente aprobado
            case 2:
                AltaServicioAdmin(sistema);
                break;
            // Opcion 3: Baja de servicio por parte del administrador, permitiendo eliminar cualquier servicio registrado en el sistema
            case 3:
                BajaServicioAdmin(sistema);
                break;
            // Opcion 4: Modificar un servicio por parte del administrador, permitiendo editar cualquier servicio registrado en el sistema sin necesidad de validacion
            case 4:
                ModificarServicioAdmin(sistema);
                break;
            // Opcion 5: Verificar publicaciones de servicios pendientes de aprobacion, permitiendo aprobar o rechazar cada servicio registrado por los usuarios finales
            case 5:
                ValidarServiciosPendientes(sistema);
                break;
            // Opcion 6: Volver al menu anterior sin hacer cambios
            case 6:
                break;
        }
    }
    // Método para mostrar el menu de gestion de usuarios del administrador
    private static void MenuUsuariosAdmin(SistemaSIS sistema) {
        Menu menuUsuarios = new Menu(60)
                .Titulo("Gestion de usuarios")
                .AgregarCampo("Consulta usuarios")
                .AgregarCampo("Alta de usuario")
                .AgregarCampo("Baja de usuario")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcionUsuarios = menuUsuarios.MostrarYLeer(lector);
        // Procesar opcion ingresada en el menu de gestion de usuarios del administrador
        switch (opcionUsuarios) {
            // Opcion 1: Consulta de usuarios registrados en el sistema, mostrando su correo y tipo de usuario
            case 1:
                List<Usuario> usuarios = sistema.GetGestorUsuarios().GetUsuarios();
                if (usuarios.isEmpty()) {
                    System.out.println("No hay usuarios registrados.");
                } else {
                    for (Usuario usuario : usuarios) {
                        System.out.println(usuario.GetEmail() + " | " + usuario.GetTipoUsuario());
                    }
                }
                break;
            // Opcion 2: Alta de usuario por parte del administrador, permitiendo crear un usuario directamente aprobado
            case 2:
                AltaUsuarioAdmin(sistema);
                break;
            // Opcion 3: Baja de usuario por parte del administrador, permitiendo eliminar cualquier usuario registrado en el sistema
            case 3: {
                System.out.print("Correo del usuario a eliminar:= ");
                String correo = lector.nextLine();
                sistema.GetGestorUsuarios().EliminarUsuario(correo);
                break;
            }
            // Opcion 4: Volver al menu anterior sin hacer cambios
            case 4:
                break;
        }
    }



    // Método para mostrar el menu de gestion de reportes del administrador
    private static void MenuReportesAdmin(SistemaSIS sistema) {
        Menu menuReportes = new Menu(60)
                .Titulo("Gestion de reportes")
                .AgregarCampo("Reportes de servicios")
                .AgregarCampo("Reportes de usuarios")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcionReportes = menuReportes.MostrarYLeer(lector);
        // Procesar opcion ingresada en el menu de gestion de reportes del administrador
        switch (opcionReportes) {
            // Opcion 1: Menu de reportes relacionados con los servicios registrados en el sistema
            case 1:
                MenuReportesServicios(sistema);
                break;
            // Opcion 2: Menu de reportes relacionados con los usuarios registrados en el sistema
            case 2:
                MenuReportesUsuarios(sistema);
                break;
            // Opcion 3: Volver al menu anterior sin hacer cambios
            case 3:
                break;
        }
    }



    // Método para mostrar el menu de reportes relacionados con los servicios registrados en el sistema
    private static void MenuReportesServicios(SistemaSIS sistema) {
        Menu menuReportes = new Menu(70)
                .Titulo("Reportes de servicios")
                .AgregarCampo("Servicio por Tipo")
                .AgregarCampo("Servicios por Precio")
                .AgregarCampo("Servicios por Ciudad")
                .AgregarCampo("Servicios por Calificacion")
                .AgregarCampo("Servicios Mejor Calificados")
                .AgregarCampo("Servicios Peor Calificados")
                .AgregarCampo("Servicios relacionados")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcion = menuReportes.MostrarYLeer(lector);
        // Obtener la lista de servicios registrados en el sistema para generar los reportes
        List<Servicio> servicios = sistema.GetGestorServicios().GetServicios();
        // Procesar opcion ingresada en el menu de reportes de servicios del administrador
        switch (opcion) {
            // Opcion 1: Reporte de servicios filtrados por categoria
            case 1: {
                System.out.print("Ingrese categoria:= ");
                String categoria = lector.nextLine();
                List<Servicio> filtrados = Reportes.ServiciosPorCategoria(servicios, categoria);
                ImprimirServicios(filtrados);
                break;
            }
            // Opcion 2: Reporte de servicios filtrados por rango de precio
            case 2: {
                double min = LeerDouble("Precio minimo:= ");
                double max = LeerDouble("Precio maximo:= ");
                List<Servicio> filtrados = Reportes.ServiciosPorPrecio(servicios, min, max);
                ImprimirServicios(filtrados);
                break;
            }
            // Opcion 3: Reporte de servicios filtrados por ciudad del proveedor
            case 3: {
                System.out.print("Ciudad:= ");
                String ciudad = lector.nextLine();
                List<Servicio> filtrados = Reportes.ServiciosPorCiudad(servicios, ciudad);
                ImprimirServicios(filtrados);
                break;
            }
            // Opcion 4: Reporte de servicios filtrados por calificacion minima
            case 4: {
                double min = LeerDouble("Calificacion minima:= ");
                List<Servicio> filtrados = Reportes.ServiciosPorCalificacion(servicios, min);
                ImprimirServicios(filtrados);
                break;
            }
            // Opcion 5: Reporte de los 5 servicios mejor calificados en el sistema
            case 5: {
                List<Servicio> top = Reportes.TopServicios(servicios, 5, true);
                ImprimirServicios(top);
                break;
            }
            // Opcion 6: Reporte de los 5 servicios peor calificados en el sistema
            case 6: {
                List<Servicio> worst = Reportes.TopServicios(servicios, 5, false);
                ImprimirServicios(worst);
                break;
            }
            // Opcion 7: Reporte de servicios relacionados a un servicio base seleccionado, mostrando servicios de la misma categoria o con palabras clave similares
            case 7: {
                Servicio base = SeleccionarServicio(servicios, "Seleccione un servicio base");
                if (base != null) {
                    List<Servicio> relacionados = Reportes.ServiciosRelacionados(base, servicios);
                    ImprimirServicios(relacionados);
                }
                break;
            }
            // Opcion 8: Volver al menu anterior sin hacer cambios
            case 8:
                break;
        }
    }



    // Método para mostrar el menu de reportes relacionados con los usuarios registrados en el sistema
    private static void MenuReportesUsuarios(SistemaSIS sistema) {
        Menu menuReportes = new Menu(70)
                .Titulo("Reportes de usuarios")
                .AgregarCampo("Ultimo servicio brindado")
                .AgregarCampo("Ultimo servicio recibido")
                .AgregarCampo("Comentarios hechos por usuario")
                .AgregarCampo("Usuarios mejor calificados")
                .AgregarCampo("Usuarios peor calificados")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcion = menuReportes.MostrarYLeer(lector);
        // Procesar opcion ingresada en el menu de reportes de usuarios del administrador
        switch (opcion) {
            // Opcion 1: Reporte del ultimo servicio brindado por un usuario seleccionado
            case 1: {
                UsuarioFinal usuario = SeleccionarUsuarioFinal(sistema);
                if (usuario != null) {
                    Pago ultimo = Reportes.UltimoPago(usuario.GetPagosRecibidos());
                    System.out.println(ultimo == null ? "Sin pagos recibidos" : ultimo.toString());
                }
                break;
            }
            // Opcion 2: Reporte del ultimo servicio recibido por un usuario seleccionado
            case 2: {
                UsuarioFinal usuario = SeleccionarUsuarioFinal(sistema);
                if (usuario != null) {
                    Pago ultimo = Reportes.UltimoPago(usuario.GetPagosRealizados());
                    System.out.println(ultimo == null ? "Sin pagos realizados" : ultimo.toString());
                }
                break;
            }
            // Opcion 3: Reporte de comentarios hechos por un usuario seleccionado
            case 3: {
                System.out.print("Correo del usuario:= ");
                String correo = lector.nextLine();
                List<Opinion> opiniones = Reportes.ComentariosPorUsuario(sistema.GetGestorServicios().GetServicios(), correo);
                if (opiniones.isEmpty()) {
                    System.out.println("No hay comentarios registrados.");
                } else {
                    for (Opinion opinion : opiniones) {
                        System.out.println(opinion.toString());
                    }
                }
                break;
            }
            // Opcion 4: Reporte de los 5 usuarios mejor calificados en el sistema, mostrando su calificacion promedio basada en las opiniones recibidas en los servicios que han brindado
            case 4: {
                List<UsuarioFinal> usuarios = ObtenerUsuariosFinales(sistema);
                usuarios.sort((a, b) -> Double.compare(CalificacionPromedioUsuario(b), CalificacionPromedioUsuario(a)));
                ImprimirUsuariosConCalificacion(usuarios);
                break;
            }
            // Opcion 5: Reporte de los 5 usuarios peor calificados en el sistema, mostrando su calificacion promedio basada en las opiniones recibidas en los servicios que han brindado
            case 5: {
                List<UsuarioFinal> usuarios = ObtenerUsuariosFinales(sistema);
                usuarios.sort((a, b) -> Double.compare(CalificacionPromedioUsuario(a), CalificacionPromedioUsuario(b)));
                ImprimirUsuariosConCalificacion(usuarios);
                break;
            }
            // Opcion 6: Volver al menu anterior sin hacer cambios
            case 6:
                break;
        }
    }

    // Métodos que ayudan a las operaciones de servicios del usuario
    // Método Alta de servicio del usuario
    private static void AltaServicioUsuario(UsuarioFinal usuario, SistemaSIS sistema) {
        Servicio servicio = CrearServicioInteractivo(usuario, usuario, false);
        if (servicio == null) {
            return;
        }
        servicio.SetEstadoPublicacion(EstadoServicio.PENDIENTE);
        sistema.GetGestorServicios().RegistrarServicio(servicio);
        usuario.AgregarServicioBrindado(servicio);
        System.out.println("Servicio registrado y enviado a validacion.");
    }
    // Método Modificar servicio del usuario
    private static void ModificarServicioUsuario(UsuarioFinal usuario) {
        Servicio servicio = SeleccionarServicio(usuario.GetServiciosBrindados(), "Mis servicios");
        if (servicio == null) {
            return;
        }
        ModificarServicio(servicio, true);
        System.out.println("Servicio actualizado y enviado a validacion.");
    }
    // Método Baja de servicio del usuario
    private static void BajaServicioUsuario(UsuarioFinal usuario, SistemaSIS sistema) {
        Servicio servicio = SeleccionarServicio(usuario.GetServiciosBrindados(), "Mis servicios");
        if (servicio == null) {
            return;
        }
        sistema.GetGestorServicios().EliminarServicio(servicio);
        usuario.RemoverServicioBrindado(servicio);
        System.out.println("Servicio eliminado correctamente.");
    }
    // Método para calificar servicios recibidos
    private static void CalificarServiciosRecibidos(UsuarioFinal usuario) {
        Servicio servicio = SeleccionarServicio(usuario.GetServiciosRecibidos(), "Servicios recibidos");
        if (servicio == null) {
            return;
        }
        double calificacion = LeerDouble("Calificacion (1-5):= ");
        System.out.print("Comentario:= ");
        String comentario = lector.nextLine();
        System.out.print("Evidencias (rutas separadas por coma, opcional):= ");
        String evidenciaInput = lector.nextLine();
        List<String> evidencias = ParsearLista(evidenciaInput);
        Opinion opinion = new Opinion(comentario, calificacion, usuario.GetEmail(), evidencias);
        servicio.AgregarOpinion(opinion);
        System.out.println("Opinion registrada correctamente.");
    }
    // Método para pagar un servicio desde el menu de pagos del usuario, registrando la transaccion en las cuentas de ambos usuarios
    private static void ComprarServicio(UsuarioFinal cliente, SistemaSIS sistema) {
        List<Servicio> servicios = sistema.GetGestorServicios().GetServiciosAprobados();
        List<Servicio> disponibles = new ArrayList<>();
        for (Servicio servicio : servicios) {
            if (servicio.GetProveedor() != cliente) {
                disponibles.add(servicio);
            }
        }
        Servicio servicio = SeleccionarServicio(disponibles, "Servicios disponibles para pagar");
        if (servicio == null) {
            return;
        }
        if (!(servicio.GetProveedor() instanceof UsuarioFinal)) {
            System.out.println("El proveedor no puede recibir pagos.");
            return;
        }
        UsuarioFinal proveedor = (UsuarioFinal) servicio.GetProveedor();
        double monto = servicio.GetPrecioHora();
        try {
            cliente.GetCuenta().RegistrarPagoSalida(monto, "Pago servicio: " + servicio.GetNombre());
            proveedor.GetCuenta().RegistrarPagoEntrada(monto, "Pago recibido: " + servicio.GetNombre());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        Pago pago = new Pago(cliente, proveedor, servicio, monto);
        pago.Completar();
        cliente.RegistrarPagoRealizado(pago);
        proveedor.RegistrarPagoRecibido(pago);
        cliente.AgregarServicioRecibido(servicio);
        if (!proveedor.GetServiciosBrindados().contains(servicio)) {
            proveedor.AgregarServicioBrindado(servicio);
        }
        System.out.println("Pago realizado correctamente.");
    }
    // Método para mostrar el historial de pagos realizados y recibidos por el usuario
    private static void MostrarHistorialPagos(UsuarioFinal usuario) {
        System.out.println("Pagos realizados:");
        List<Pago> realizados = usuario.GetPagosRealizados();
        if (realizados.isEmpty()) {
            System.out.println("Sin pagos realizados.");
        } else {
            for (Pago pago : realizados) {
                System.out.println(pago.toString());
            }
        }
        System.out.println("Pagos recibidos:");
        List<Pago> recibidos = usuario.GetPagosRecibidos();
        if (recibidos.isEmpty()) {
            System.out.println("Sin pagos recibidos.");
        } else {
            for (Pago pago : recibidos) {
                System.out.println(pago.toString());
            }
        }
    }
    // Método para dar de alta un servicio por parte del administrador
    private static void AltaServicioAdmin(SistemaSIS sistema) {
        UsuarioFinal proveedor = SeleccionarUsuarioFinal(sistema);
        if (proveedor == null) {
            return;
        }
        Servicio servicio = CrearServicioInteractivo(proveedor, Sesion.GetInstancia().GetUsuarioActual(), true);
        if (servicio == null) {
            return;
        }
        servicio.Aprobar();
        sistema.GetGestorServicios().RegistrarServicio(servicio);
        proveedor.AgregarServicioBrindado(servicio);
        System.out.println("Servicio creado y aprobado correctamente.");
    }
    // Método para dar de baja un servicio por parte del administrador
    private static void BajaServicioAdmin(SistemaSIS sistema) {
        Servicio servicio = SeleccionarServicio(sistema.GetGestorServicios().GetServicios(), "Servicios registrados");
        if (servicio == null) {
            return;
        }
        sistema.GetGestorServicios().EliminarServicio(servicio);
        if (servicio.GetProveedor() instanceof UsuarioFinal) {
            ((UsuarioFinal) servicio.GetProveedor()).RemoverServicioBrindado(servicio);
        }
        System.out.println("Servicio eliminado correctamente.");
    }
    // Método para modificar un servicio por parte del administrador
    private static void ModificarServicioAdmin(SistemaSIS sistema) {
        Servicio servicio = SeleccionarServicio(sistema.GetGestorServicios().GetServicios(), "Servicios registrados");
        if (servicio == null) {
            return;
        }
        ModificarServicio(servicio, false);
        System.out.println("Servicio actualizado correctamente.");
    }
    // Método para validar servicios pendientes de aprobacion por parte del administrador
    private static void ValidarServiciosPendientes(SistemaSIS sistema) {
        List<Servicio> pendientes = sistema.GetGestorServicios().GetServiciosPendientes();
        Servicio servicio = SeleccionarServicio(pendientes, "Servicios pendientes");
        if (servicio == null) {
            return;
        }
        Menu menu = new Menu(40)
                .Titulo("Validar servicio")
                .AgregarCampo("Aprobar")
                .AgregarCampo("Rechazar")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcion = menu.MostrarYLeer(lector);
        switch (opcion) {
            case 1:
                sistema.GetGestorServicios().AprobarServicio(servicio);
                System.out.println("Servicio aprobado.");
                break;
            case 2:
                sistema.GetGestorServicios().RechazarServicio(servicio);
                System.out.println("Servicio rechazado.");
                break;
            case 3:
                break;
        }
    }
    // Método para dar de alta un usuario por parte del administrador
    private static void AltaUsuarioAdmin(SistemaSIS sistema) {
        Menu menuTipo = new Menu(40)
                .Titulo("Tipo de usuario")
                .AgregarCampo("Administrador")
                .AgregarCampo("Usuario final")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcion = menuTipo.MostrarYLeer(lector);
        switch (opcion) {
            case 1: {
                Administrador admin = PedirAdministrador();
                sistema.GetGestorUsuarios().RegistrarUsuario(admin);
                break;
            }
            case 2: {
                UsuarioFinal usuario = PedirUsuario();
                sistema.GetGestorUsuarios().RegistrarUsuario(usuario);
                break;
            }
            case 3:
                break;
        }
    }
    // Método para pedir los datos necesarios para crear un nuevo administrador
    private static Administrador PedirAdministrador() {
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
        return new Administrador(nombre, apellidos, direccion, telefonoContacto, correoElectronico, contrasena);
    }
    // Método para pedir los datos necesarios para crear un nuevo usuario final
    private static Servicio CrearServicioInteractivo(Usuario proveedor, Usuario creador, boolean aprobado) {
        System.out.print("Nombre del servicio:= ");
        String nombre = lector.nextLine();
        System.out.print("Descripcion:= ");
        String descripcion = lector.nextLine();
        double precio = LeerDouble("Precio por hora:= ");
        Complejidad complejidad = SeleccionarComplejidad();
        Horario horario = SeleccionarHorario();
        System.out.print("Ciudad:= ");
        String ciudad = lector.nextLine();
        System.out.print("Municipio:= ");
        String municipio = lector.nextLine();
        Ubicacion ubicacion = new Ubicacion(ciudad, municipio);
        System.out.print("Edad recomendada:= ");
        String edad = lector.nextLine();
        List<String> categorias = SeleccionarCategorias();

        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Nombre invalido.");
            return null;
        }

        Servicio servicio = new Servicio(nombre, precio, complejidad, ubicacion, horario, edad, proveedor);
        servicio.SetDescripcion(descripcion);
        servicio.SetTipos(categorias);
        servicio.SetCreador(creador);
        if (aprobado) {
            servicio.Aprobar();
        } else {
            servicio.SetEstadoPublicacion(EstadoServicio.PENDIENTE);
        }
        return servicio;
    }
    // Método para Modificar un servicio
    private static void ModificarServicio(Servicio servicio, boolean requiereValidacion) {
        Menu menuEditar = new Menu(60)
                .Titulo("Modificar servicio")
                .AgregarCampo("Nombre")
                .AgregarCampo("Descripcion")
                .AgregarCampo("Precio por hora")
                .AgregarCampo("Categorias")
                .AgregarCampo("Complejidad")
                .AgregarCampo("Horario")
                .AgregarCampo("Ubicacion")
                .AgregarCampo("Edad recomendada")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcion = menuEditar.MostrarYLeer(lector);
        switch (opcion) {
            case 1:
                System.out.print("Nuevo nombre:= ");
                servicio.SetNombre(lector.nextLine());
                break;
            case 2:
                System.out.print("Nueva descripcion:= ");
                servicio.SetDescripcion(lector.nextLine());
                break;
            case 3:
                servicio.SetPrecioHora(LeerDouble("Nuevo precio:= "));
                break;
            case 4:
                servicio.SetTipos(SeleccionarCategorias());
                break;
            case 5:
                servicio.SetComplejidad(SeleccionarComplejidad());
                break;
            case 6:
                servicio.SetHorarioRealizacion(SeleccionarHorario());
                break;
            case 7:
                System.out.print("Ciudad:= ");
                String ciudad = lector.nextLine();
                System.out.print("Municipio:= ");
                String municipio = lector.nextLine();
                servicio.SetUbicacion(new Ubicacion(ciudad, municipio));
                break;
            case 8:
                System.out.print("Edad recomendada:= ");
                servicio.SetEdadRecomendada(lector.nextLine());
                break;
            case 9:
                return;
        }
        if (requiereValidacion) {
            servicio.SetEstadoPublicacion(EstadoServicio.PENDIENTE);
        }
    }
    // Método para mostrar un menu de seleccion de servicios a partir de una lista dada, mostrando su informacion resumida
    private static Servicio SeleccionarServicio(List<Servicio> servicios, String titulo) {
        if (servicios == null || servicios.isEmpty()) {
            System.out.println("No hay servicios disponibles.");
            return null;
        }
        Menu menu = new Menu(80)
                .Titulo(titulo)
                .Peticion("Seleccione un servicio:= ");
        for (Servicio servicio : servicios) {
            menu.AgregarCampo(servicio.InfoResumida());
        }
        menu.AgregarCampo("Volver");
        int opcion = menu.MostrarYLeer(lector);
        if (opcion == servicios.size() + 1) {
            return null;
        }
        return servicios.get(opcion - 1);
    }
    // Método para mostrar un menu de seleccion de usuarios finales a partir de la lista de usuarios registrados en el sistema, mostrando su correo y nick
    private static UsuarioFinal SeleccionarUsuarioFinal(SistemaSIS sistema) {
        List<UsuarioFinal> usuarios = ObtenerUsuariosFinales(sistema);
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios finales registrados.");
            return null;
        }
        Menu menu = new Menu(60)
                .Titulo("Usuarios finales")
                .Peticion("Seleccione un usuario:= ");
        for (UsuarioFinal usuario : usuarios) {
            menu.AgregarCampo(usuario.GetEmail() + " | " + usuario.GetNick());
        }
        menu.AgregarCampo("Volver");
        int opcion = menu.MostrarYLeer(lector);
        if (opcion == usuarios.size() + 1) {
            return null;
        }
        return usuarios.get(opcion - 1);
    }
    // Método para obtener la lista de usuarios finales registrados en el sistema
    private static List<UsuarioFinal> ObtenerUsuariosFinales(SistemaSIS sistema) {
        List<UsuarioFinal> usuariosFinales = new ArrayList<>();
        for (Usuario usuario : sistema.GetGestorUsuarios().GetUsuarios()) {
            if (usuario instanceof UsuarioFinal) {
                usuariosFinales.add((UsuarioFinal) usuario);
            }
        }
        return usuariosFinales;
    }
    // Método para calcular la calificacion promedio de un usuario final a partir de las opiniones recibidas en los servicios que ha brindado
    private static double CalificacionPromedioUsuario(UsuarioFinal usuario) {
        List<Servicio> servicios = usuario.GetServiciosBrindados();
        if (servicios.isEmpty()) {
            return 0.0;
        }
        double suma = 0.0;
        for (Servicio servicio : servicios) {
            suma += servicio.GetCalificacionPromedio();
        }
        return suma / servicios.size();
    }
    // Método para imprimir una lista de usuarios finales con su calificacion promedio, mostrando su correo y calificacion promedio
    private static void ImprimirUsuariosConCalificacion(List<UsuarioFinal> usuarios) {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios para mostrar.");
            return;
        }
        for (UsuarioFinal usuario : usuarios) {
            double calificacion = CalificacionPromedioUsuario(usuario);
            System.out.printf("%s | %.2f%n", usuario.GetEmail(), calificacion);
        }
    }
    // Método para imprimir una lista de servicios mostrando su informacion resumida
    private static void ImprimirServicios(List<Servicio> servicios) {
        if (servicios == null || servicios.isEmpty()) {
            System.out.println("No hay servicios para mostrar.");
            return;
        }
        for (Servicio servicio : servicios) {
            System.out.println(servicio.InfoResumida());
        }
    }
    // Método para mostrar la informacion completa de un servicio brindado por el usuario a partir de un menu de seleccion de sus servicios brindados
    private static void MostrarMisServicios(UsuarioFinal usuario) {
        Servicio servicio = SeleccionarServicio(usuario.GetServiciosBrindados(), "Mis servicios");
        if (servicio == null) {
            return;
        }
        System.out.println(servicio.InfoAll());
    }
    // Método para mostrar la informacion completa de un servicio recibido por el usuario a partir de un menu de seleccion de sus servicios recibidos
    private static Complejidad SeleccionarComplejidad() {
        Menu menu = new Menu(40)
                .Titulo("Complejidad")
                .Peticion("Seleccione un nivel:= ");
        for (Complejidad nivel : Complejidad.values()) {
            menu.AgregarCampo(nivel.name());
        }
        int opcion = menu.MostrarYLeer(lector);
        return Complejidad.values()[opcion - 1];
    }
    // Método para mostrar un menu de seleccion de horarios a partir de las opciones definidas
    private static Horario SeleccionarHorario() {
        Menu menu = new Menu(40)
                .Titulo("Horario")
                .Peticion("Seleccione un horario:= ");
        for (Horario horario : Horario.values()) {
            menu.AgregarCampo(horario.name());
        }
        int opcion = menu.MostrarYLeer(lector);
        return Horario.values()[opcion - 1];
    }
    // Método para mostrar un menu de seleccion de categorias a partir de las categorias por defecto definidas 
    private static List<String> SeleccionarCategorias() {
        System.out.println("Categorias disponibles:");
        for (int i = 0; i < CATEGORIAS_DEFAULT.size(); i++) {
            System.out.println((i + 1) + ". " + CATEGORIAS_DEFAULT.get(i));
        }
        System.out.print("Ingrese numeros separados por coma (opcional):= ");
        String entrada = lector.nextLine();
        List<String> categorias = new ArrayList<>();
        if (!entrada.trim().isEmpty()) {
            String[] partes = entrada.split(",");
            for (String parte : partes) {
                try {
                    int indice = Integer.parseInt(parte.trim());
                    if (indice >= 1 && indice <= CATEGORIAS_DEFAULT.size()) {
                        categorias.add(CATEGORIAS_DEFAULT.get(indice - 1));
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Categoria ignorada: " + parte);
                }
            }
        }
        System.out.print("Categorias adicionales (texto, separadas por coma):= ");
        String extra = lector.nextLine();
        categorias.addAll(ParsearLista(extra));
        if (categorias.isEmpty()) {
            categorias.add("General");
        }
        return categorias;
    }
    //Transformar un renglon de elementos separados por comas y espacios en una lista limpia
    private static List<String> ParsearLista(String entrada) {
        List<String> lista = new ArrayList<>();
        if (entrada == null || entrada.trim().isEmpty()) {
            return lista;
        }
        String[] partes = entrada.split(",");
        for (String parte : partes) {
            String valor = parte.trim();
            if (!valor.isEmpty()) {
                lista.add(valor);
            }
        }
        return lista;
    }
    // Método para leer un valor cuando no es válido mostrando un mensaje de error
    private static double LeerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = lector.nextLine();
                return Double.parseDouble(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Valor invalido, intente de nuevo.");
            }
        }
    }
    // Método para pedir los datos necesarios para crear un nuevo usuario final
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

        System.out.print("Ingrese su nick:= ");
        String nick = lector.nextLine();

        return new UsuarioFinal(nombre, apellidos, direccion, telefonoContacto, correoElectronico, contrasena, nick, new Date());
    }
    // Método para mostrar el catalogo de servicios disponibles en el sistema, con opciones de filtrado
    private static void MostrarCatalogo(GestorServicios gestorServicios, Scanner lector, SistemaSIS sistema) {
        CargarServiciosEjemploSiVacio(gestorServicios, sistema);

        while (true) {
            Menu menuCatalogo = new Menu(50)
                .Titulo("Catalogo de servicios")
                .AgregarCampo("Ver por categoria")
                .AgregarCampo("Ver todos")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");

            int opcion = menuCatalogo.MostrarYLeer(lector);
            switch (opcion) {
                // Opcion 1: Mostrar catalogo filtrado por categoria
                case 1:
                    MostrarCatalogoPorCategoria(gestorServicios, lector);
                    break;
                // Opcion 2: Mostrar catalogo completo sin filtros
                case 2:
                    MostrarCatalogoTodos(gestorServicios, lector);
                    break;
                // Opcion 3: Volver al menu anterior sin hacer cambios
                case 3:
                    return;
            }
        }
    }
    // Método para mostrar el catalogo de servicios filtrado por categoria
    private static void MostrarCatalogoPorCategoria(GestorServicios gestorServicios, Scanner lector) {
        Menu menuCategorias = new Menu(50)
            .Titulo("Categorias")
            .Peticion("Seleccione una categoria:= ");

        for (String categoria : CATEGORIAS_DEFAULT) {
            menuCategorias.AgregarCampo(categoria);
        }

        int opcion = menuCategorias.MostrarYLeer(lector);
        String categoriaSeleccionada = CATEGORIAS_DEFAULT.get(opcion - 1);
        List<Servicio> aprobados = gestorServicios.GetServiciosAprobados();
        List<Servicio> filtrados = FiltrarServiciosPorCategoria(aprobados, categoriaSeleccionada);
        MostrarListaServicios(filtrados, lector, "Servicios - " + categoriaSeleccionada, aprobados);
    }
    // Método para mostrar el catalogo completo de servicios sin filtros
    private static void MostrarCatalogoTodos(GestorServicios gestorServicios, Scanner lector) {
        List<Servicio> servicios = gestorServicios.GetServiciosAprobados();
        MostrarListaServicios(servicios, lector, "Todos los servicios", servicios);
    }
    // Método para mostrar una lista de servicios con su informacion resumida
    private static void MostrarListaServicios(List<Servicio> servicios, Scanner lector, String titulo, List<Servicio> universo) {
        if (servicios.isEmpty()) {
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
        if (opcion == servicios.size() + 1) {
            return;
        }

        Servicio seleccionado = servicios.get(opcion - 1);
        System.out.println(seleccionado.InfoAll());

        List<Servicio> relacionados = Reportes.ServiciosRelacionados(seleccionado, universo);
        if (!relacionados.isEmpty()) {
            System.out.println("Servicios relacionados:");
            for (Servicio relacionado : relacionados) {
                System.out.println("- " + relacionado.InfoResumida());
            }
        }
        System.out.print("Presione Enter para volver...");
        lector.nextLine();
    }
    // Método para filtrar una lista de servicios por categoria, sin importar las mayusculas y espacios
    private static List<Servicio> FiltrarServiciosPorCategoria(List<Servicio> servicios, String categoria) {
        String categoriaNormalizada = NormalizarCategoria(categoria);
        List<Servicio> filtrados = new ArrayList<>();

        for (Servicio servicio : servicios) {
            for (String tipo : servicio.GetTipos()) {
                if (NormalizarCategoria(tipo).equals(categoriaNormalizada)) {
                    filtrados.add(servicio);
                    break;
                }
            }
        }

        return filtrados;
    }
    // Método para eliminar espacios y convirtiendo a minusculas para comparaciones
    private static String NormalizarCategoria(String categoria) {
        if (categoria == null) {
            return "";
        }
        return categoria.trim().toLowerCase();
    }
    // Metodo para cargar servicios de ejemplo en el sistema si no hay servicios registrados, asignandolos a un proveedor de ejemplo
    private static void CargarServiciosEjemploSiVacio(GestorServicios gestorServicios, SistemaSIS sistema) {
        if (!gestorServicios.GetServicios().isEmpty()) {
            return;
        }

        UsuarioFinal proveedorEjemplo = (UsuarioFinal) sistema.GetGestorUsuarios().BuscarUsuario("santiagogonuz@gmail.com");
        if (proveedorEjemplo == null) {
            return;
        }

        Servicio servicio1 = CrearServicioEjemplo(
            "Limpieza de hogar express",
            "Limpieza basica de habitaciones y areas comunes.",
            120.0,
            Complejidad.BAJA,
            new Ubicacion("Monclova", "Centro"),
            Horario.MANANA,
            "Todo publico",
            Arrays.asList("Hogar"),
            4.3,
            proveedorEjemplo
        );
        gestorServicios.RegistrarServicio(servicio1);
        proveedorEjemplo.AgregarServicioBrindado(servicio1);

        Servicio servicio2 = CrearServicioEjemplo(
            "Soporte tecnico basico",
            "Revision de equipo, limpieza de software y ajustes de rendimiento.",
            200.0,
            Complejidad.MEDIA,
            new Ubicacion("Monclova", "Industrial"),
            Horario.TARDE,
            "Todo publico",
            Arrays.asList("Tecnologia"),
            4.6,
            proveedorEjemplo
        );
        gestorServicios.RegistrarServicio(servicio2);
        proveedorEjemplo.AgregarServicioBrindado(servicio2);

        Servicio servicio3 = CrearServicioEjemplo(
            "Clases de matematicas",
            "Asesoria para nivel secundaria y preparatoria.",
            150.0,
            Complejidad.MEDIA_ALTA,
            new Ubicacion("Monclova", "Universidad"),
            Horario.MEDIODIA,
            "12+",
            Arrays.asList("Educacion"),
            4.8,
            proveedorEjemplo
        );
        gestorServicios.RegistrarServicio(servicio3);
        proveedorEjemplo.AgregarServicioBrindado(servicio3);

        Servicio servicio4 = CrearServicioEjemplo(
            "Traslado al aeropuerto",
            "Servicio de traslado seguro con reservacion previa.",
            300.0,
            Complejidad.BAJA_MEDIA,
            new Ubicacion("Monclova", "Norte"),
            Horario.NOCHE,
            "Todo publico",
            Arrays.asList("Transporte"),
            4.2,
            proveedorEjemplo
        );
        gestorServicios.RegistrarServicio(servicio4);
        proveedorEjemplo.AgregarServicioBrindado(servicio4);

        Servicio servicio5 = CrearServicioEjemplo(
            "Cobertura fotografica de eventos",
            "Sesion fotografica y edicion basica incluida.",
            500.0,
            Complejidad.ALTA,
            new Ubicacion("Monclova", "Centro"),
            Horario.TARDE,
            "Todo publico",
            Arrays.asList("Eventos", "Tecnologia"),
            4.7,
            proveedorEjemplo
        );
        gestorServicios.RegistrarServicio(servicio5);
        proveedorEjemplo.AgregarServicioBrindado(servicio5);
    }

    // Metodo auxiliar para crear un servicio de ejemplo con todos sus datos, utilizado para cargar servicios de ejemplo en el sistema
    private static Servicio CrearServicioEjemplo(
        String nombre,
        String descripcion,
        double precioHora,
        Complejidad complejidad,
        Ubicacion ubicacion,
        Horario horario,
        String edadRecomendada,
        List<String> categorias,
        double calificacion,
        UsuarioFinal proveedor
    ) 
    // Crea un servicio con los datos proporcionados, asignandole el estado de aprobado y la calificacion promedio dada, sin necesidad de pasar por el proceso de creacion
    {
        Servicio servicio = new Servicio(nombre, precioHora, complejidad, ubicacion, horario, edadRecomendada, proveedor);
        servicio.SetDescripcion(descripcion);
        servicio.SetTipos(new ArrayList<>(categorias));
        servicio.SetCalificacionPromedio(calificacion);
        servicio.SetEstadoPublicacion(EstadoServicio.APROBADO);
        return servicio;
    }
}
