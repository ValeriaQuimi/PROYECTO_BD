DELIMITER //
CREATE PROCEDURE sp_pedido(
    IN p_accion VARCHAR(20),
    IN p_numOrden INT,
    IN p_precioTotal DECIMAL(10,2),
    IN p_saldoRestante DECIMAL(10,2),
    IN p_tipoPedido ENUM('domicilio','local'),
    IN p_estadoPedido ENUM('preparando','entregada','anulado'),
    IN p_idCliente INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error en procedimiento de Pedido';
    END;

    START TRANSACTION;

    -- Insertar Pedido
    IF p_accion = 'insertar' THEN
        IF NOT EXISTS (SELECT 1 FROM Cliente WHERE idCliente = p_idCliente) THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Cliente no existe, no se puede crear pedido';
        END IF;

        INSERT INTO Pedido(precioTotal, saldoRestante, tipoPedido, estadoPedido, idCliente)
        VALUES (p_precioTotal, p_saldoRestante, p_tipoPedido, p_estadoPedido, p_idCliente);

    -- Actualizar Pedido
    ELSEIF p_accion = 'actualizar' THEN
        IF NOT EXISTS (SELECT 1 FROM Pedido WHERE numOrden = p_numOrden) THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Pedido no existe';
        END IF;

        UPDATE Pedido
        SET precioTotal = p_precioTotal,
            saldoRestante = p_saldoRestante,
            tipoPedido = p_tipoPedido,
            estadoPedido = p_estadoPedido
        WHERE numOrden = p_numOrden;

    -- Eliminar Pedido
    ELSEIF p_accion = 'eliminar' THEN
        IF NOT EXISTS (SELECT 1 FROM Pedido WHERE numOrden = p_numOrden) THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Pedido no existe';
        END IF;

        DELETE FROM Pedido WHERE numOrden = p_numOrden;

    ELSE
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Acción no válida. Use insertar, actualizar o eliminar';
    END IF;

    COMMIT;
END //
DELIMITER ;
