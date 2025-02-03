/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author nancy
 */
import com.banco.microserviciocuentas.controller.CuentaBancariaController;
import com.banco.microserviciocuentas.enums.TipoCuentaEnum;
import com.banco.microserviciocuentas.exceptions.Exceptions;
import com.banco.microserviciocuentas.model.CuentaBancaria;
import com.banco.microserviciocuentas.service.CuentaBancariaService;
import com.banco.microserviciocuentas.utilities.template.CuentaCreateTemp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaControllerTest {

    @Mock
    private CuentaBancariaService cuentaBancariaService;  // Simula el servicio

    @Mock
    private BindingResult bindingResult;  // Simula el BindingResult

    private CuentaBancariaController cuentaBancariaController;

    @Before
    public void setUp() {
        // Inicializa los mocks de Mockito
        MockitoAnnotations.initMocks(this);

        // Crea el controlador y pasa la dependencia simulada
        cuentaBancariaController = new CuentaBancariaController(cuentaBancariaService);
    }

    @Test
    public void testCrearCuentaExito() {
        // Datos de prueba válidos
        CuentaCreateTemp cuentaCreateTemp = new CuentaCreateTemp("1234567890", TipoCuentaEnum.Ahorros, new BigDecimal("1000"));
        CuentaBancaria cuentaBancaria = new CuentaBancaria("1234567890", TipoCuentaEnum.Ahorros, new BigDecimal("1000"));

        // Simulamos que no hay errores de validación
        when(bindingResult.hasErrors()).thenReturn(false);
        when(cuentaBancariaService.crearCuentaBancaria(cuentaCreateTemp)).thenReturn(cuentaBancaria);

        // Ejecutamos el método
        CuentaBancaria resultado = cuentaBancariaController.crear(cuentaCreateTemp, bindingResult);

        // Verificamos el resultado
        assertNotNull("La cuenta bancaria debe ser creada", resultado);
        assertEquals("El número de cuenta debe ser 1234567890", "1234567890", resultado.getNumerocuenta());
    }

    @Test
    public void testCrearCuentaErrorValidacion() {
        // Datos de prueba inválidos (número de cuenta vacío)
        CuentaCreateTemp cuentaCreateTemp = new CuentaCreateTemp("", TipoCuentaEnum.Ahorros, new BigDecimal("1000"));

        // Simulamos que hay errores de validación
        List<FieldError> errores = new ArrayList<>();
        errores.add(new FieldError("cuentaCreateTemp", "numerocuenta", "El número de cuenta no puede estar vacío"));
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(errores);

        try {
            // Ejecutamos el método, esperamos que se lance una excepción
            cuentaBancariaController.crear(cuentaCreateTemp, bindingResult);
            fail("Se esperaba una excepción por errores de validación");
        } catch (Exceptions e) {
            // Verificamos que la excepción sea lanzada correctamente
            assertEquals("El código de error debe ser 400", 400, e.getCode());
        }
    }
}
