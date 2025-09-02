-- SP PARA DETALLE

DELIMITER //
CREATE PROCEDURE sp_insertar_detalle(
    IN p_numOrden INT,
    IN p_idProduct INT,
    IN p_cantidad INT
)
BEGIN
    DECLARE v_precio DECIMAL(10,2);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al insertar detalle';
    END;

    START TRANSACTION;

    IF p_cantidad <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La cantidad debe ser mayor a 0';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM Pedido WHERE numOrden = p_numOrden) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El pedido no existe';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM Producto WHERE idProduct = p_idProduct) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El producto no existe';
    END IF;

    SELECT precio INTO v_precio FROM Producto WHERE idProduct = p_idProduct;

    INSERT INTO Detalle (numOrden, idProduct, cantidad, precioCantidad)
    VALUES (p_numOrden, p_idProduct, p_cantidad, v_precio * p_cantidad);

    -- Actualiza stock del producto (descuenta)
    UPDATE Producto
       SET cantidadDisp = cantidadDisp - p_cantidad
     WHERE idProduct = p_idProduct;

    COMMIT;
END//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_actualizar_detalle(
    IN p_numOrden INT,
    IN p_idProduct INT,
    IN p_nuevaCantidad INT
)
BEGIN
    DECLARE v_cantidad_actual INT;
    DECLARE v_precio DECIMAL(10,2);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al actualizar detalle';
    END;

    START TRANSACTION;

    IF p_nuevaCantidad <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La cantidad debe ser mayor a 0';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM Detalle WHERE numOrden = p_numOrden AND idProduct = p_idProduct) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El detalle no existe';
    END IF;

    SELECT cantidad INTO v_cantidad_actual
      FROM Detalle
     WHERE numOrden = p_numOrden AND idProduct = p_idProduct;

    -- Revertir stock previo y aplicar nuevo
    UPDATE Producto
       SET cantidadDisp = cantidadDisp + v_cantidad_actual - p_nuevaCantidad
     WHERE idProduct = p_idProduct;

    SELECT precio INTO v_precio FROM Producto WHERE idProduct = p_idProduct;

    UPDATE Detalle
       SET cantidad = p_nuevaCantidad,
           precioCantidad = v_precio * p_nuevaCantidad
     WHERE numOrden = p_numOrden AND idProduct = p_idProduct;

    COMMIT;
END//
DELIMITER ;


DELIMITER //
CREATE PROCEDURE sp_eliminar_detalle(
    IN p_numOrden INT,
    IN p_idProduct INT
)
BEGIN
    DECLARE v_cantidad INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al eliminar detalle';
    END;

    START TRANSACTION;

    IF NOT EXISTS (SELECT 1 FROM Detalle WHERE numOrden = p_numOrden AND idProduct = p_idProduct) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El detalle no existe';
    END IF;

    SELECT cantidad INTO v_cantidad
      FROM Detalle
     WHERE numOrden = p_numOrden AND idProduct = p_idProduct;

    DELETE FROM Detalle
     WHERE numOrden = p_numOrden AND idProduct = p_idProduct;

    -- Devolver stock al eliminar el detalle
    UPDATE Producto
       SET cantidadDisp = cantidadDisp + v_cantidad
     WHERE idProduct = p_idProduct;

    COMMIT;
END//
DELIMITER ;


-- Consulta de detalles por pedido
DELIMITER //
CREATE PROCEDURE sp_consultar_detalles_por_pedido(
    IN p_numOrden INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al consultar detalles';
    END;

    SELECT d.numOrden,
           d.idProduct,
           p.nombreProduct,
           d.cantidad,
           d.precioCantidad
      FROM Detalle d
      JOIN Producto p ON d.idProduct = p.idProduct
     WHERE d.numOrden = p_numOrden
     ORDER BY d.idProduct;
END//
DELIMITER ;
