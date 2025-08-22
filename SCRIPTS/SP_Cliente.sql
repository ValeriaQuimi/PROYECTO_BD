DELIMITER //
CREATE PROCEDURE sp_cliente(
    IN p_accion VARCHAR(20),
    IN p_idCliente INT,
    IN p_nombre VARCHAR(20),
    IN p_telefono INT,
    IN p_correo VARCHAR(80),
    IN p_tipo ENUM('vip','estandar'),
    IN p_ciudad VARCHAR(20),
    IN p_calle VARCHAR(10),
    IN p_referencia VARCHAR(50)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error en procedimiento de Cliente';
    END;

    START TRANSACTION;

    -- Insertar Cliente
    IF p_accion = 'insertar' THEN
        IF p_nombre IS NULL OR p_nombre = '' THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'El nombre no puede estar vacío';
        END IF;

        INSERT INTO Cliente(nombre, telefono, correo, tipo, dir_ciudad, dir_calle, dir_referencia)
        VALUES (p_nombre, p_telefono, p_correo, p_tipo, p_ciudad, p_calle, p_referencia);

    -- Actualizar Cliente
    ELSEIF p_accion = 'actualizar' THEN
        IF NOT EXISTS (SELECT 1 FROM Cliente WHERE idCliente = p_idCliente) THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Cliente no existe';
        END IF;

        UPDATE Cliente
        SET nombre = p_nombre,
            telefono = p_telefono,
            correo = p_correo,
            tipo = p_tipo,
            dir_ciudad = p_ciudad,
            dir_calle = p_calle,
            dir_referencia = p_referencia
        WHERE idCliente = p_idCliente;

    -- Eliminar Cliente
    ELSEIF p_accion = 'eliminar' THEN
        IF NOT EXISTS (SELECT 1 FROM Cliente WHERE idCliente = p_idCliente) THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Cliente no existe';
        END IF;

        DELETE FROM Cliente WHERE idCliente = p_idCliente;

    ELSE
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Acción no válida. Use insertar, actualizar o eliminar';
    END IF;

    COMMIT;
END //
DELIMITER ;
