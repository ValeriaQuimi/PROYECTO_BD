
-- SP PARA PEDIDO

-- INSERTAR
DELIMITER //
CREATE PROCEDURE sp_insertarPedido(
    IN p_precioTotal    DECIMAL(10,2),
    IN p_saldoRestante  DECIMAL(10,2),
    IN p_tipoPedido     VARCHAR(20),   -- 'domicilio' | 'local'
    IN p_estadoPedido   VARCHAR(20),   -- 'preparando' | 'entregada' | 'anulado'
    IN p_idCliente      INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al insertar pedido';
    END;

    START TRANSACTION;

    IF p_precioTotal < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El precio total no puede ser negativo';
    END IF;

    IF p_saldoRestante < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El saldo restante no puede ser negativo';
    END IF;

    IF p_saldoRestante > p_precioTotal THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El saldo no puede superar el precio total';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM Cliente WHERE idCliente = p_idCliente) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El cliente asociado no existe';
    END IF;

    INSERT INTO Pedido(precioTotal, saldoRestante, tipoPedido, estadoPedido, idCliente)
    VALUES(p_precioTotal, p_saldoRestante, p_tipoPedido, p_estadoPedido, p_idCliente);

    COMMIT;
END
//
DELIMITER ;

-- ACTUALIZAR
DELIMITER ///
CREATE PROCEDURE sp_actualizarPedido(
    IN p_numOrden       INT,
    IN p_precioTotal    DECIMAL(10,2),
    IN p_saldoRestante  DECIMAL(10,2),
    IN p_tipoPedido     VARCHAR(20),
    IN p_estadoPedido   VARCHAR(20)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al actualizar pedido';
    END;

    START TRANSACTION;

    IF NOT EXISTS (SELECT 1 FROM Pedido WHERE numOrden = p_numOrden) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El pedido no existe';
    END IF;

    IF p_precioTotal < 0 OR p_saldoRestante < 0 OR p_saldoRestante > p_precioTotal THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Valores inválidos de precio/saldo';
    END IF;

    UPDATE Pedido
       SET precioTotal   = p_precioTotal,
           saldoRestante = p_saldoRestante,
           tipoPedido    = p_tipoPedido,
           estadoPedido  = p_estadoPedido
     WHERE numOrden = p_numOrden;

    COMMIT;
END///
DELIMITER ;

-- ELIMINAR
DELIMITER /
CREATE PROCEDURE sp_eliminarPedido(IN p_numOrden INT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al eliminar pedido';
    END;

    START TRANSACTION;

    IF NOT EXISTS (SELECT 1 FROM Pedido WHERE numOrden = p_numOrden) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El pedido no existe';
    END IF;

    -- Opcional: impedir eliminar si ya fue entregado
    -- IF (SELECT estadoPedido FROM Pedido WHERE numOrden = p_numOrden) = 'entregada' THEN
    --     SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede eliminar un pedido entregado';
    -- END IF;

    DELETE FROM Pedido WHERE numOrden = p_numOrden;

    COMMIT;
END/
DELIMITER ;

-- CONSULTAR
DELIMITER /
CREATE PROCEDURE sp_consultarPedidos()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al listar pedidos';
    END;

    SELECT  p.numOrden,
            p.precioTotal,
            p.saldoRestante,
            p.tipoPedido,
            p.estadoPedido,
            c.idCliente,
            c.nombre AS nombreCliente,
            c.correo AS correoCliente
    FROM Pedido p
    JOIN Cliente c ON p.idCliente = c.idCliente
    ORDER BY p.numOrden;
END/
DELIMITER ;
