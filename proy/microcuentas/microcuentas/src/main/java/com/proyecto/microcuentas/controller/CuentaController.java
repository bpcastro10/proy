package com.proyecto.microcuentas.controller;

import com.proyecto.microcuentas.dto.CuentaDTO;
import com.proyecto.microcuentas.entity.Cuenta;
import com.proyecto.microcuentas.service.CuentaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService service;
    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<CuentaDTO> listar() {
        return service.listar().stream()
            .map(c -> mapper.map(c, CuentaDTO.class))
            .collect(Collectors.toList());
    }

    @GetMapping("/{numeroCuenta}")
    public CuentaDTO buscar(@PathVariable String numeroCuenta) {
        return mapper.map(service.buscarPorId(numeroCuenta), CuentaDTO.class);
    }

    @PostMapping
    public CuentaDTO crear(@RequestBody CuentaDTO dto) {
        Cuenta c = mapper.map(dto, Cuenta.class);
        return mapper.map(service.guardar(c), CuentaDTO.class);
    }

    @PutMapping("/{numeroCuenta}")
    public CuentaDTO actualizar(@PathVariable String numeroCuenta, @RequestBody CuentaDTO dto) {
        Cuenta c = mapper.map(dto, Cuenta.class);
        c.setNumeroCuenta(numeroCuenta);
        return mapper.map(service.guardar(c), CuentaDTO.class);
    }

    @DeleteMapping("/{numeroCuenta}")
    public void eliminar(@PathVariable String numeroCuenta) {
        service.eliminar(numeroCuenta);
    }
} 