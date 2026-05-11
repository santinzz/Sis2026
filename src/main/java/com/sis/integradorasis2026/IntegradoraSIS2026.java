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
        sistema.GetGestorUsuarios().RegistrarUsuario(
            new Administrador(
                "Admin", 
                "Principal", 
                new Direccion("Central", 1, "Monclova", "Coahuila", 25700), 
                "8660000000", 
                "admin@sys",
                "admin123"
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
                        Menu menuUsuario = new Menu()
                                .Titulo("Panel usuario")
                                .AgregarCampo("Servicios")
                                .AgregarCampo("Pagos y balance")
                                .AgregarCampo("Mi perfil")
                                .AgregarCampo("Cerrar sesion")
                                .Peticion("Ingrese una opcion:= ");
                        int opcion = menuUsuario.MostrarYLeer(lector);
                        switch (opcion) {
                            case 1:
                                MenuServiciosUsuario((UsuarioFinal) usuario, sistema);
                                break;
                            case 2:
                                MenuPagosUsuario((UsuarioFinal) usuario, sistema);
                                break;
                            case 3:
                                MenuPerfilUsuarioPrincipal((UsuarioFinal) usuario, sistema);
                                break;
                            case 4:
                                Sesion.GetInstancia().Logout();
                                break;
                        }






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

                        switch (opcionAdmin)
                        {
                            case 1 -> MenuServiciosAdmin(sistema);
                            case 2 -> MenuUsuariosAdmin(sistema);
                            case 3 -> MenuReportesAdmin(sistema);
                            case 4 -> Sesion.GetInstancia().Logout();
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

    private static void MenuPerfilUsuarioPrincipal(UsuarioFinal usuario, SistemaSIS sistema) {
        Menu menuPerfil = new Menu(60)
                .Titulo("Mi perfil")
                .AgregarCampo("Ver informacion de mi perfil")
                .AgregarCampo("Editar informacion de mi perfil")
                .AgregarCampo("Eliminar mi cuenta")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcionPerfil = menuPerfil.MostrarYLeer(lector);
        switch (opcionPerfil) {
            case 1:
                MenuPerfilUsuario(usuario, lector);
                break;
            case 2:
                MenuEditarPerfil(usuario, lector);
                break;
            case 3:
                sistema.GetGestorUsuarios().EliminarUsuario(usuario.GetEmail());
                Sesion.GetInstancia().Logout();
                System.out.println("Cuenta eliminada exitosamente");
                break;
            case 4:
                break;
        }
    }

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
        switch (opcion) {
            case 1:
                AltaServicioUsuario(usuario, sistema);
                break;
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
            case 3:
                ModificarServicioUsuario(usuario);
                break;
            case 4:
                BajaServicioUsuario(usuario, sistema);
                break;
            case 5:
                CalificarServiciosRecibidos(usuario);
                break;
            case 6:
                break;
        }
    }

    private static void MenuPagosUsuario(UsuarioFinal usuario, SistemaSIS sistema) {
        Menu menuPagos = new Menu(60)
                .Titulo("Pagos y balance")
                .AgregarCampo("Pagar servicio")
                .AgregarCampo("Historial de pagos")
                .AgregarCampo("Balance y movimientos")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcion = menuPagos.MostrarYLeer(lector);
        switch (opcion) {
            case 1:
                ComprarServicio(usuario, sistema);
                break;
            case 2:
                MostrarHistorialPagos(usuario);
                break;
            case 3:
                MenuBalanceUsuario(usuario);
                break;
            case 4:
                break;
        }
    }

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
        switch (opcion) {
            case 1:
                System.out.printf("Saldo actual: $%.2f%n", usuario.GetCuenta().GetSaldo());
                break;
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
            case 5:
                break;
        }
    }

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
        switch (opcionServicios) {
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
            case 2:
                AltaServicioAdmin(sistema);
                break;
            case 3:
                BajaServicioAdmin(sistema);
                break;
            case 4:
                ModificarServicioAdmin(sistema);
                break;
            case 5:
                ValidarServiciosPendientes(sistema);
                break;
            case 6:
                break;
        }
    }

    private static void MenuUsuariosAdmin(SistemaSIS sistema) {
        Menu menuUsuarios = new Menu(60)
                .Titulo("Gestion de usuarios")
                .AgregarCampo("Consulta usuarios")
                .AgregarCampo("Alta de usuario")
                .AgregarCampo("Baja de usuario")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcionUsuarios = menuUsuarios.MostrarYLeer(lector);
        switch (opcionUsuarios) {
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
            case 2:
                AltaUsuarioAdmin(sistema);
                break;
            case 3: {
                System.out.print("Correo del usuario a eliminar:= ");
                String correo = lector.nextLine();
                sistema.GetGestorUsuarios().EliminarUsuario(correo);
                break;
            }
            case 4:
                break;
        }
    }

    private static void MenuReportesAdmin(SistemaSIS sistema) {
        Menu menuReportes = new Menu(60)
                .Titulo("Gestion de reportes")
                .AgregarCampo("Reportes de servicios")
                .AgregarCampo("Reportes de usuarios")
                .AgregarCampo("Volver")
                .Peticion("Ingrese una opcion:= ");
        int opcionReportes = menuReportes.MostrarYLeer(lector);
        switch (opcionReportes) {
            case 1:
                MenuReportesServicios(sistema);
                break;
            case 2:
                MenuReportesUsuarios(sistema);
                break;
            case 3:
                break;
        }
    }

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
        List<Servicio> servicios = sistema.GetGestorServicios().GetServicios();
        switch (opcion) {
            case 1: {
                System.out.print("Ingrese categoria:= ");
                String categoria = lector.nextLine();
                List<Servicio> filtrados = Reportes.ServiciosPorCategoria(servicios, categoria);
                ImprimirServicios(filtrados);
                break;
            }
            case 2: {
                double min = LeerDouble("Precio minimo:= ");
                double max = LeerDouble("Precio maximo:= ");
                List<Servicio> filtrados = Reportes.ServiciosPorPrecio(servicios, min, max);
                ImprimirServicios(filtrados);
                break;
            }
            case 3: {
                System.out.print("Ciudad:= ");
                String ciudad = lector.nextLine();
                List<Servicio> filtrados = Reportes.ServiciosPorCiudad(servicios, ciudad);
                ImprimirServicios(filtrados);
                break;
            }
            case 4: {
                double min = LeerDouble("Calificacion minima:= ");
                List<Servicio> filtrados = Reportes.ServiciosPorCalificacion(servicios, min);
                ImprimirServicios(filtrados);
                break;
            }
            case 5: {
                List<Servicio> top = Reportes.TopServicios(servicios, 5, true);
                ImprimirServicios(top);
                break;
            }
            case 6: {
                List<Servicio> worst = Reportes.TopServicios(servicios, 5, false);
                ImprimirServicios(worst);
                break;
            }
            case 7: {
                Servicio base = SeleccionarServicio(servicios, "Seleccione un servicio base");
                if (base != null) {
                    List<Servicio> relacionados = Reportes.ServiciosRelacionados(base, servicios);
                    ImprimirServicios(relacionados);
                }
                break;
            }
            case 8:
                break;
        }
    }

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
        switch (opcion) {
            case 1: {
                UsuarioFinal usuario = SeleccionarUsuarioFinal(sistema);
                if (usuario != null) {
                    Pago ultimo = Reportes.UltimoPago(usuario.GetPagosRecibidos());
                    System.out.println(ultimo == null ? "Sin pagos recibidos" : ultimo.toString());
                }
                break;
            }
            case 2: {
                UsuarioFinal usuario = SeleccionarUsuarioFinal(sistema);
                if (usuario != null) {
                    Pago ultimo = Reportes.UltimoPago(usuario.GetPagosRealizados());
                    System.out.println(ultimo == null ? "Sin pagos realizados" : ultimo.toString());
                }
                break;
            }
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
            case 4: {
                List<UsuarioFinal> usuarios = ObtenerUsuariosFinales(sistema);
                usuarios.sort((a, b) -> Double.compare(CalificacionPromedioUsuario(b), CalificacionPromedioUsuario(a)));
                ImprimirUsuariosConCalificacion(usuarios);
                break;
            }
            case 5: {
                List<UsuarioFinal> usuarios = ObtenerUsuariosFinales(sistema);
                usuarios.sort((a, b) -> Double.compare(CalificacionPromedioUsuario(a), CalificacionPromedioUsuario(b)));
                ImprimirUsuariosConCalificacion(usuarios);
                break;
            }
            case 6:
                break;
        }
    }

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

    private static void ModificarServicioUsuario(UsuarioFinal usuario) {
        Servicio servicio = SeleccionarServicio(usuario.GetServiciosBrindados(), "Mis servicios");
        if (servicio == null) {
            return;
        }
        ModificarServicio(servicio, true);
        System.out.println("Servicio actualizado y enviado a validacion.");
    }

    private static void BajaServicioUsuario(UsuarioFinal usuario, SistemaSIS sistema) {
        Servicio servicio = SeleccionarServicio(usuario.GetServiciosBrindados(), "Mis servicios");
        if (servicio == null) {
            return;
        }
        sistema.GetGestorServicios().EliminarServicio(servicio);
        usuario.RemoverServicioBrindado(servicio);
        System.out.println("Servicio eliminado correctamente.");
    }

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

    private static void ModificarServicioAdmin(SistemaSIS sistema) {
        Servicio servicio = SeleccionarServicio(sistema.GetGestorServicios().GetServicios(), "Servicios registrados");
        if (servicio == null) {
            return;
        }
        ModificarServicio(servicio, false);
        System.out.println("Servicio actualizado correctamente.");
    }

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

    private static List<UsuarioFinal> ObtenerUsuariosFinales(SistemaSIS sistema) {
        List<UsuarioFinal> usuariosFinales = new ArrayList<>();
        for (Usuario usuario : sistema.GetGestorUsuarios().GetUsuarios()) {
            if (usuario instanceof UsuarioFinal) {
                usuariosFinales.add((UsuarioFinal) usuario);
            }
        }
        return usuariosFinales;
    }

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

    private static void ImprimirServicios(List<Servicio> servicios) {
        if (servicios == null || servicios.isEmpty()) {
            System.out.println("No hay servicios para mostrar.");
            return;
        }
        for (Servicio servicio : servicios) {
            System.out.println(servicio.InfoResumida());
        }
    }

    private static void MostrarMisServicios(UsuarioFinal usuario) {
        Servicio servicio = SeleccionarServicio(usuario.GetServiciosBrindados(), "Mis servicios");
        if (servicio == null) {
            return;
        }
        System.out.println(servicio.InfoAll());
    }

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
        List<Servicio> aprobados = gestorServicios.GetServiciosAprobados();
        List<Servicio> filtrados = FiltrarServiciosPorCategoria(aprobados, categoriaSeleccionada);
        MostrarListaServicios(filtrados, lector, "Servicios - " + categoriaSeleccionada, aprobados);
    }

    private static void MostrarCatalogoTodos(GestorServicios gestorServicios, Scanner lector) {
        List<Servicio> servicios = gestorServicios.GetServiciosAprobados();
        MostrarListaServicios(servicios, lector, "Todos los servicios", servicios);
    }

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
        servicio.SetEstadoPublicacion(EstadoServicio.APROBADO);
        return servicio;
    }
}
