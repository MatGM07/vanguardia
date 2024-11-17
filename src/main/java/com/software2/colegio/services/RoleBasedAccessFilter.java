package com.software2.colegio.services;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RoleBasedAccessFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Verificar si la ruta es /content/citas
        if (new AntPathRequestMatcher("/content/citas/**").matches(request)) {
            // Verificar la sesión
            if (request.getSession(false) == null) {
                response.sendRedirect("/user/login"); // Redirigir al login si no hay sesión
                return;
            }

            // Verificar el rol en la sesión
            String role = (String) request.getSession().getAttribute("role");
            if (role == null || (!role.equals("ROLE_ACUDIENTE") && (!role.equals("ROLE_ADMIN")) )) {
                response.sendRedirect("/"); // Redirigir si no tiene permiso
                return;
            }
        }

        if (new AntPathRequestMatcher("/user/register").matches(request)) {
            // Verificar la sesión
            if (request.getSession(false) == null) {
                response.sendRedirect("/user/login"); // Redirigir al login si no hay sesión
                return;
            }

            // Verificar el rol en la sesión
            String role = (String) request.getSession().getAttribute("role");
            if (role == null || !role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/"); // Redirigir si no tiene permiso
                return;
            }
        }

        if (new AntPathRequestMatcher("/content/horas").matches(request)) {
            System.out.println("CUCUC");
            // Verificar la sesión
            if (request.getSession(false) == null) {
                response.sendRedirect("/user/login"); // Redirigir al login si no hay sesión
                return;
            }

            // Verificar el rol en la sesión
            String role = (String) request.getSession().getAttribute("role");
            System.out.println(role);
            if (role == null || (!role.equals("ROLE_DOCENTE") && (!role.equals("ROLE_ADMIN")))) {
                response.sendRedirect("/"); // Redirigir si no tiene permiso
                return;
            }
        }

        filterChain.doFilter(request, response); // Continuar con la cadena de filtros
    }
}
