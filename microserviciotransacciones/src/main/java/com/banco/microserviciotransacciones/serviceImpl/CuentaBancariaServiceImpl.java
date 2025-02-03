/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciotransacciones.serviceImpl;


import com.banco.microserviciotransacciones.model.CuentaBancaria;
import com.banco.microserviciotransacciones.repository.ClienteRepository;
import com.banco.microserviciotransacciones.repository.CuentaBancariaRepository;
import com.banco.microserviciotransacciones.service.CuentaBancariaService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author nancy
 */
@Service
@RequiredArgsConstructor
public class CuentaBancariaServiceImpl implements CuentaBancariaService {
    
    private final CuentaBancariaRepository cuentaBancariaRepository;
    private final ClienteRepository clienteRepository;
    private final ConversionService conversionService;
    //private final ClienteService clienteService;
   private static final Logger logger = LoggerFactory.getLogger(CuentaBancariaServiceImpl.class);

//    
//    @Override
//    @Transactional()
//    public CuentaBancaria crearCuentaBancaria(CuentaCreateTemp cuentaCreateTemp) {
//
//        if (cuentaCreateTemp.getSaldoInicial().compareTo(BigDecimal.ZERO) < 0) {
//            throw new Exceptions(ErrorEnum.CREAR_CUENTA_SALDO_INVALIDO, HttpStatus.BAD_REQUEST.value());
//        }
//
//        var cliente = clienteService.findByidCliente(cuentaCreateTemp.getCliente().getIdCliente());
//        
//        var cuentaEntity = CuentaBancaria.builder()
//            .idcliente(cliente)
//            .numerocuenta(cuentaCreateTemp.getNumerocuenta())
//            .tipocuenta(cuentaCreateTemp.getTipocuenta())
//            .saldoactual(cuentaCreateTemp.getSaldoInicial())
//            .fechaCreacion(new Date())
//            .build();
//        return this.cuentaBancariaRepository.save(cuentaEntity);
//       // return this.conversionService.convert(cuentaEntity, CuentaSecTemp.class);
//    }
//
//    @Override
//    public Page<CuentaBancaria> listCuentas(Pageable pageable) {
//        return this.cuentaBancariaRepository.findAll(pageable);
//    }
//    
    @Override
    public CuentaBancaria obtenerCuentaPorNumero(String numeroCuenta) {
          logger.info("Consultando la cuenta bancaria con número: {}", numeroCuenta);
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findByNumerocuenta(numeroCuenta);
//                .orElseThrow(() -> {
//                    logger.warn("No se encontró una cuenta con el número de cuenta: {}", numeroCuenta);
//                    return new RuntimeException("Cuenta no encontrada"+numeroCuenta);
//                });
        logger.info("Cuenta encontrada: {}", cuentaBancaria.getIdcuentabancaria());
        return cuentaBancaria;
    }
//    
//    @Override
//     public List<CuentaBancaria> obtenerCuentasPorCliente(Long idcliente) {
//        logger.info("Iniciando la obtención de cuentas bancarias para el cliente con id: {}", idcliente);
//
//        try {
//            List<CuentaBancaria> cuentas = cuentaBancariaRepository.findByIdcliente_Idcliente(idcliente);
//            if (cuentas.isEmpty()) {
//                logger.warn("No se encontraron cuentas bancarias para el cliente con id: {}", idcliente);
//            } else {
//                logger.info("Operación exitosa: Se encontraron {} cuentas para el cliente con id: {}", cuentas.size(), idcliente);
//            }
//            return cuentas;
//        } catch (Exception e) {
//            logger.error("Error crítico al intentar obtener las cuentas bancarias para el cliente con id: {}. Error: {}", idcliente, e.getMessage(), e);
//            throw new RuntimeException("Error al obtener las cuentas bancarias.");
//        }
//    }
    }

