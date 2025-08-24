-- Insertar

DELIMITER //

CREATE PROCEDURE sp_insertar_entrega(
    IN p_fechaEntrega DATE,
    IN p_estadoEntrega ENUM('pendiente', 'entregado', 'cancelado'),
    IN p_idRepartidor INT,
    IN p_numOrden INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al insertar la entrega.';
    END;

    START TRANSACTION;

    -- Validación de existencia del repartidor
    IF NOT EXISTS (SELECT 1 FROM repartidor WHERE idRepartidor = p_idRepartidor) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El repartidor no existe.';
    END IF;

    -- Validación de existencia del pedido
    IF NOT EXISTS (SELECT 1 FROM pedido WHERE numOrden = p_numOrden) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El pedido no existe.';
    END IF;

    -- Inserción
    INSERT INTO entrega (fechaEntrega, estadoEntrega, idRepartidor, numOrden)
    VALUES (p_fechaEntrega, p_estadoEntrega, p_idRepartidor, p_numOrden);

    COMMIT;
END;
//

DELIMITER ;

-- Actualizar

DELIMITER //

CREATE PROCEDURE sp_actualizar_entrega(
    IN p_numEntrega INT,
    IN p_nuevoEstado ENUM('pendiente', 'entregado', 'cancelado'),
    IN p_nuevaFecha DATE
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al actualizar la entrega.';
    END;

    START TRANSACTION;

    -- Validación de existencia
    IF NOT EXISTS (SELECT 1 FROM entrega WHERE numEntrega = p_numEntrega) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La entrega no existe.';
    END IF;

    -- Actualización
    UPDATE entrega
    SET estadoEntrega = p_nuevoEstado,
        fechaEntrega = p_nuevaFecha
    WHERE numEntrega = p_numEntrega;

    COMMIT;
END;
//

DELIMITER ;

-- Eliminar

DELIMITER //

CREATE PROCEDURE sp_eliminar_entrega(
    IN p_numEntrega INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al eliminar la entrega.';
    END;

    START TRANSACTION;

    -- Validación de existencia
    IF NOT EXISTS (SELECT 1 FROM entrega WHERE numEntrega = p_numEntrega) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La entrega no existe.';
    END IF;

    -- Eliminación
    DELETE FROM entrega
    WHERE numEntrega = p_numEntrega;

    COMMIT;
END;
//

DELIMITER ;