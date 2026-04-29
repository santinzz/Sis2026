package com.sis.integradorasis2026;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
 
abstract public class Usuario {
    private String nombre;
    private String apellidos;
    private Direccion direccion;
    private String telefonoContacto;
    private String email;
    private String contrasena;
    private TipoUsuario tipoUsuario;
    private Cuenta cuenta;
    private List<Servicio> serviciosBrindados;
    private List<Servicio> serviciosRecibidos;
   
    public Usuario(String nombre, String apellidos, Direccion direccion, String telefonoContacto, String email, String contrasena, TipoUsuario tipoUsuario)
    {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefonoContacto = telefonoContacto;
        this.email = NormalizarEmail(email);
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.cuenta = new Cuenta();
        this.serviciosBrindados = new ArrayList<>();
        this.serviciosRecibidos = new ArrayList<>();
    }
 
    public static String NormalizarEmail(String email)
    {
        if (email == null) {
            return "";
        }
        return email.trim().toLowerCase(Locale.ROOT);
    }
   
    public String GetNombre()
    {
        return nombre;
    }
   
    public void SetNombre(String nombre)
    {
        this.nombre = nombre;
    }
   
    public String GetApellidos()
    {
        return apellidos;
    }
   
    public void SetApellidos(String apellidos)
    {
        this.apellidos = apellidos;
    }
   
    public Direccion GetDireccion()
    {
        return direccion;
    }
   
    public void SetDireccion(Direccion direccion)
    {
        this.direccion = direccion;
    }
   
    public String GetTelefonoContacto()
    {
        return telefonoContacto;
    }
   
    public void SetTelefonoContacto(String telefonoContacto)
    {
        this.telefonoContacto = telefonoContacto;
    }
   
    public String GetEmail()
    {
        return email;
    }
   
    public void SetEmail(String email)
    {
        this.email = NormalizarEmail(email);
    }
   
    public boolean CompararContrasena(String contrasena) {
        return this.contrasena.equals(contrasena);
    }
   
    public TipoUsuario GetTipoUsuario()
    {
        return tipoUsuario;
    }
   
    public void SetTipoUsuario(TipoUsuario tipoUsuario)
    {
        this.tipoUsuario = tipoUsuario;
    }
   
    public boolean Autenticar(String email, String contrasena)
    {
        boolean esEmailCorrecto = GetEmail().equals(NormalizarEmail(email));
        return esEmailCorrecto && CompararContrasena(contrasena);
    }
 
    public void SetContrasena(String contrasena)
    {
        this.contrasena = contrasena;
    }
 
    public Cuenta GetCuenta()
    {
        return cuenta;
    }
 
    public void SetCuenta(Cuenta cuenta)
    {
        this.cuenta = cuenta;
    }
 
    public List<Servicio> GetServiciosRecibidos()
    {
        return serviciosRecibidos;
    }
 
    public void SetServiciosRecibidos(List<Servicio> serviciosRecibidos)
    {
        this.serviciosRecibidos = serviciosRecibidos;
    }
 
    public List<Servicio> GetServiciosBrindados()
    {
        return serviciosBrindados;
    }
 
    public void SetServiciosBrindados(List<Servicio> serviciosBrindados)
    {
        this.serviciosBrindados = serviciosBrindados;
    }
}