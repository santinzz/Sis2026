package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private List<Usuario> usuarios;

    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
    }

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
    
    public void EliminarUsuario(String email)
    {
        String emailNormalizado = Usuario.NormalizarEmail(email);
        if (usuarios.removeIf(usuario -> usuario.GetEmail().equals(emailNormalizado))) {
            System.out.println("Usuario con correo: " + emailNormalizado + " eliminado exitosamente.");
        } else {
            System.out.println("Usuario con correo: " + emailNormalizado + " no encontrado");
        }
    }
    
    public Usuario BuscarUsuario(String email) 
    {
        String emailNormalizado = Usuario.NormalizarEmail(email);
        return BuscarUsuarioNormalizado(emailNormalizado);
    }
    
    public void IniciarSesion(String email, String contrasena) {
        String emailNormalizado = Usuario.NormalizarEmail(email);
        Usuario usuario = BuscarUsuarioNormalizado(emailNormalizado);
        boolean credencialesValidas = usuario != null && usuario.Autenticar(emailNormalizado, contrasena);
        if (usuario != null && credencialesValidas) 
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
        } else {
            System.out.println("Credenciales invalidas para el correo: " + emailNormalizado);
        }
    }
    
    public void CerrarSesion() {
        if (Sesion.GetInstancia().EstaLogeado()) {
            Sesion.GetInstancia().Logout();
        } else {
            throw new IllegalStateException("No hay una sesion activa para cerrar");
        }
    }

    public List<Usuario> GetUsuarios() {
        return usuarios;
    }
}
