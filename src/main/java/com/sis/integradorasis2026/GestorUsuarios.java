package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    //Declaración de atributos
    private List<Usuario> usuarios;

    //Constructor para inicializar el gestor de usuarios
    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    // Método privado para buscar un usuario por su email (con el formato establecido previamente)
    private Usuario BuscarUsuarioNormalizado(String emailNormalizado)
    {
        for (Usuario usuario : usuarios)
        {
            if (usuario.GetEmail().equals(emailNormalizado))
            {
                return usuario;
            }
        }
        return null;
    }
    
    // Métodos de comportamiento para registrar usuario
    public void RegistrarUsuario(Usuario usuario) 
    {
        String emailNormalizado = Usuario.NormalizarEmail(usuario.GetEmail());
        if (emailNormalizado.isEmpty()) {
            System.out.println("El email es obligatorio para registrar un usuario.");
            return;
        }
        if (BuscarUsuarioNormalizado(emailNormalizado) != null) {
            System.out.println("Ya existe un usuario con el correo: " + emailNormalizado);
            return;
        }
        usuarios.add(usuario);
        System.out.println("Usuario registrado correctamente: " + usuario.GetEmail());
    }
    
    // Método para eliminar un usuario por su email
    public void EliminarUsuario(String email)
    {
        String emailNormalizado = Usuario.NormalizarEmail(email);
        if (usuarios.removeIf(usuario -> usuario.GetEmail().equals(emailNormalizado))) {
            System.out.println("Usuario con correo: " + emailNormalizado + " eliminado exitosamente.");
        } else {
            System.out.println("Usuario con correo: " + emailNormalizado + " no encontrado");
        }
    }
    
    // Método para buscar un usuario por su email
    public Usuario BuscarUsuario(String email) 
    {
        String emailNormalizado = Usuario.NormalizarEmail(email);
        return BuscarUsuarioNormalizado(emailNormalizado);
    }

    // Método get para obtener la lista de usuarios registrados en el gestor de usuarios
    public List<Usuario> GetUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    // Método para iniciar sesión de un usuario
    public void IniciarSesion(String email, String contrasena) {
        String emailNormalizado = Usuario.NormalizarEmail(email);
        Usuario usuario = BuscarUsuarioNormalizado(emailNormalizado);
        if (usuario != null && usuario.Autenticar(emailNormalizado, contrasena)) 
        {
            if (usuario instanceof Administrador) 
            {
                System.out.println("Acceso concedido: PANEL DE CONTROL ADMINISTRADOR");
            } 
            else 
            {
                System.out.println("Acceso concedido: PANEL DE USUARIO");
            }
            Sesion.GetInstancia().Login(usuario);
            return;
        }
        System.out.println("Credenciales no validas");
    }
    
    // Método para cerrar sesión del usuario actualmente logeado
    public void CerrarSesion() {
        if (Sesion.GetInstancia().EstaLogeado()) {
            Sesion.GetInstancia().Logout();
        } else {
            throw new IllegalStateException("No hay una sesion activa para cerrar");
        }
    }
}
