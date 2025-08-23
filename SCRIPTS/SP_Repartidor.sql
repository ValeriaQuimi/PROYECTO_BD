USE gemaplusdb; 

-- SP para repartidor

DELIMITER //

CREATE PROCEDURE sp_insertarRepartidor(
    IN p_nombre VARCHAR(100),
    IN p_telefono INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error al insertar repartidor';
    END;

    START TRANSACTION;
    
    IF p_telefono IS NULL OR p_telefono <= 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'El teléfono debe ser mayor a 0';
    END IF;

    INSERT INTO Repartidor(nombre, telefono)
    VALUES (p_nombre, p_telefono);

    COMMIT;
END//

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE sp_actualizarRepartidor(
    IN p_id INT,
    IN p_nuevoTelefono INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error al actualizar repartidor';
    END;

    START TRANSACTION;

    IF NOT EXISTS (SELECT 1 FROM Repartidor WHERE idRepartidor = p_id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Repartidor no encontrado';
    END IF;

    UPDATE Repartidor
    SET telefono = p_nuevoTelefono
    WHERE idRepartidor = p_id;

    COMMIT;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_eliminarRepartidor(
    IN p_id INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Error al eliminar repartidor';
    END;

    START TRANSACTION;
    
    IF NOT EXISTS (SELECT 1 FROM Repartidor WHERE idRepartidor = p_id) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Repartidor no encontrado';
    END IF;

    DELETE FROM Repartidor WHERE idRepartidor = p_id;

    COMMIT;
END$$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE sp_consultarRepartidores()
BEGIN

 DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al listar repartidores';
    END;
    START TRANSACTION;
    SELECT idRepartidor, nombre, telefono
    FROM Repartidor;
    
    COMMIT;
END$$

DELIMITER ;


