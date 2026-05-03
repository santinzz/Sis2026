// COMENTARIO DE PRUEBA
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

/**
 *
 * @author santi
 */
public class IntegradoraSIS2026 {
    static Scanner lector = new Scanner(System.in);
    private static final List<String> CATEGORIAS_DEFAULT = Arrays.asList(
        "Hogar",
        "Tecnologia",
        "Educacion",
        "Salud",
        "Transporte",
        "Eventos"
    );

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
                           System.out.println(Color.colorize("El usuario no existe intente denuevo!!", Color.RED));
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
                                MostrarCatalogo(sistema.GetGestorServicios(), lector, sistema);
                                break;
                            }
                            case 2: {
                                MenuPerfilUsuario((UsuarioFinal) usuario, lector);
                                break;
                            }
                            case 3: {
                                MenuEditarPerfil((UsuarioFinal) usuario, lector);
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
                    } else 
                    {
                        Sesion.GetInstancia().Logout();
                        break;
                    }
                }
            }
        }
    }

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

    private static void MenuEditarPerfil(UsuarioFinal usuario, Scanner lector) {
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

    private static void MostrarCatalogoPorCategoria(GestorServicios gestorServicios, Scanner lector) {
        Menu menuCategorias = new Menu(50)
            .Titulo("Categorias")
            .Peticion("Seleccione una categoria:= ");

        for (String categoria : CATEGORIAS_DEFAULT) {
            menuCategorias.AgregarCampo(categoria);
        }

        int opcion = menuCategorias.MostrarYLeer(lector);
        String categoriaSeleccionada = CATEGORIAS_DEFAULT.get(opcion - 1);
        List<Servicio> filtrados = FiltrarServiciosPorCategoria(gestorServicios.GetServicios(), categoriaSeleccionada);
        MostrarListaServicios(filtrados, lector, "Servicios - " + categoriaSeleccionada);
    }

    private static void MostrarCatalogoTodos(GestorServicios gestorServicios, Scanner lector) {
        List<Servicio> servicios = gestorServicios.GetServicios();
        MostrarListaServicios(servicios, lector, "Todos los servicios");
    }

    private static void MostrarListaServicios(List<Servicio> servicios, Scanner lector, String titulo) {
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
        System.out.print("Presione Enter para volver...");
        lector.nextLine();
    }

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

    private static String NormalizarCategoria(String categoria) {
        if (categoria == null) {
            return "";
        }
        return categoria.trim().toLowerCase();
    }

    private static void CargarServiciosEjemploSiVacio(GestorServicios gestorServicios, SistemaSIS sistema) {
        if (!gestorServicios.GetServicios().isEmpty()) {
            return;
        }

        UsuarioFinal proveedorEjemplo = (UsuarioFinal) sistema.GetGestorUsuarios().BuscarUsuario("santiagogonuz@gmail.com");

        gestorServicios.RegistrarServicio(CrearServicioEjemplo(
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
        ));

        gestorServicios.RegistrarServicio(CrearServicioEjemplo(
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
        ));

        gestorServicios.RegistrarServicio(CrearServicioEjemplo(
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
        ));

        gestorServicios.RegistrarServicio(CrearServicioEjemplo(
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
        ));

        gestorServicios.RegistrarServicio(CrearServicioEjemplo(
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
        ));
    }

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
    ) {
        Servicio servicio = new Servicio(nombre, precioHora, complejidad, ubicacion, horario, edadRecomendada, proveedor);
        servicio.SetDescripcion(descripcion);
        servicio.SetTipos(new ArrayList<>(categorias));
        servicio.SetCalificacionPromedio(calificacion);
        return servicio;
    }
}
