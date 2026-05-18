package com.sis.integradorasis2026;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Reportes {
	// MÉTODOS DE FILTRADO Y ANÁLISIS
	// Filtrar servicios por categoría
	public static List<Servicio> ServiciosPorCategoria(List<Servicio> servicios, String categoria) {
		List<Servicio> filtrados = new ArrayList<>();
		if (servicios == null || categoria == null) {
			return filtrados;
		}
		String categoriaNormalizada = categoria.trim().toLowerCase();
		for (Servicio servicio : servicios) {
			for (String tipo : servicio.GetTipos()) {
				if (tipo != null && tipo.trim().toLowerCase().equals(categoriaNormalizada)) {
					filtrados.add(servicio);
					break;
				}
			}
		}
		return filtrados;
	}

	// Filtrar servicios por precio
	public static List<Servicio> ServiciosPorPrecio(List<Servicio> servicios, double min, double max) {
		List<Servicio> filtrados = new ArrayList<>();
		if (servicios == null) {
			return filtrados;
		}
		for (Servicio servicio : servicios) {
			double precio = servicio.GetPrecioHora();
			if (precio >= min && precio <= max) {
				filtrados.add(servicio);
			}
		}
		return filtrados;
	}

	// Filtrar servicios por ciudad
	public static List<Servicio> ServiciosPorCiudad(List<Servicio> servicios, String ciudad) {
		List<Servicio> filtrados = new ArrayList<>();
		if (servicios == null || ciudad == null) {
			return filtrados;
		}
		String ciudadNormalizada = ciudad.trim().toLowerCase();
		for (Servicio servicio : servicios) {
			Ubicacion ubicacion = servicio.GetUbicacion();
			if (ubicacion != null && ubicacion.GetCiudad().trim().toLowerCase().equals(ciudadNormalizada)) {
				filtrados.add(servicio);
			}
		}
		return filtrados;
	}

	// Filtrar servicios por calificación
	public static List<Servicio> ServiciosPorCalificacion(List<Servicio> servicios, double minimo) {
		List<Servicio> filtrados = new ArrayList<>();
		if (servicios == null) {
			return filtrados;
		}
		for (Servicio servicio : servicios) {
			if (servicio.GetCalificacionPromedio() >= minimo) {
				filtrados.add(servicio);
			}
		}
		return filtrados;
	}

	// Filtrar los mejores servicios
	public static List<Servicio> TopServicios(List<Servicio> servicios, int limite, boolean mejores) {
		List<Servicio> ordenados = new ArrayList<>();
		if (servicios == null) {
			return ordenados;
		}
		ordenados.addAll(servicios);
		ordenados.sort(Comparator.comparingDouble(Servicio::GetCalificacionPromedio));
		if (mejores) {
			ordenados.sort(Comparator.comparingDouble(Servicio::GetCalificacionPromedio).reversed());
		}
		if (limite <= 0 || limite > ordenados.size()) {
			return ordenados;
		}
		return new ArrayList<>(ordenados.subList(0, limite));
	}

	// Obtener el último pago realizado
	public static Pago UltimoPago(List<Pago> pagos) {
		if (pagos == null || pagos.isEmpty()) {
			return null;
		}
		Pago ultimo = pagos.get(0);
		for (Pago pago : pagos) {
			if (pago.GetFecha().after(ultimo.GetFecha())) {
				ultimo = pago;
			}
		}
		return ultimo;
	}

	// Obtener comentarios por usuario
	public static List<Opinion> ComentariosPorUsuario(List<Servicio> servicios, String email) {
		List<Opinion> opiniones = new ArrayList<>();
		if (servicios == null || email == null) {
			return opiniones;
		}
		String emailNormalizado = Usuario.NormalizarEmail(email);
		for (Servicio servicio : servicios) {
			for (Opinion opinion : servicio.GetOpiniones()) {
				if (opinion != null && emailNormalizado.equals(Usuario.NormalizarEmail(opinion.GetAutorEmail()))) {
					opiniones.add(opinion);
				}
			}
		}
		return opiniones;
	}

	// Obtener servicios relacionados por tipo
	public static List<Servicio> ServiciosRelacionados(Servicio base, List<Servicio> servicios) {
		List<Servicio> relacionados = new ArrayList<>();
		if (base == null || servicios == null) {
			return relacionados;
		}
		for (Servicio servicio : servicios) {
			if (servicio == base) {
				continue;
			}
			for (String tipo : base.GetTipos()) {
				if (servicio.GetTipos().contains(tipo)) {
					relacionados.add(servicio);
					break;
				}
			}
		}
		return relacionados;
	}
}
