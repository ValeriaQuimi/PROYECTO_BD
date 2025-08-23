-- INSERTAR
DELIMITER //
CREATE PROCEDURE sp_insertarCliente(
    IN p_nombre       VARCHAR(20),
    IN p_telefono     INT,
    IN p_correo       VARCHAR(80),
    IN p_tipo         VARCHAR(20),   -- 'vip' | 'estandar' (se valida por negocio)
    IN p_dir_ciudad   VARCHAR(20),
    IN p_dir_calle    VARCHAR(10),
    IN p_dir_referencia VARCHAR(50)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al insertar cliente';
    END;

    START TRANSACTION;

    IF p_nombre IS NULL OR p_nombre = '' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El nombre no puede estar vacío';
    END IF;

    IF p_telefono IS NULL OR p_telefono <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El teléfono debe ser mayor que 0';
    END IF;

    IF p_correo IS NULL OR p_correo = '' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El correo es obligatorio';
    END IF;

    INSERT INTO Cliente(nombre, telefono, correo, tipo, dir_ciudad, dir_calle, dir_referencia)
    VALUES(p_nombre, p_telefono, p_correo, p_tipo, p_dir_ciudad, p_dir_calle, p_dir_referencia);

    COMMIT;
END
//
DELIMITER ;

-- ACTUALIZAR
DELIMITER ///
CREATE PROCEDURE sp_actualizarCliente(
    IN p_idCliente    INT,
    IN p_nombre       VARCHAR(20),
    IN p_telefono     INT,
    IN p_correo       VARCHAR(80),
    IN p_tipo         VARCHAR(20),
    IN p_dir_ciudad   VARCHAR(20),
    IN p_dir_calle    VARCHAR(10),
    IN p_dir_referencia VARCHAR(50)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al actualizar cliente';
    END;

    START TRANSACTION;

    IF NOT EXISTS (SELECT 1 FROM Cliente WHERE idCliente = p_idCliente) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El cliente no existe';
    END IF;

    UPDATE Cliente
       SET nombre = p_nombre,
           telefono = p_telefono,
           correo = p_correo,
           tipo = p_tipo,
           dir_ciudad = p_dir_ciudad,
           dir_calle = p_dir_calle,
           dir_referencia = p_dir_referencia
     WHERE idCliente = p_idCliente;

    COMMIT;
END///
DELIMITER ;

-- ELIMINAR
DELIMITER /
CREATE PROCEDURE sp_eliminarCliente(IN p_idCliente INT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al eliminar cliente';
    END;

    START TRANSACTION;

    IF NOT EXISTS (SELECT 1 FROM Cliente WHERE idCliente = p_idCliente) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El cliente no existe';
    END IF;

    -- Opcional: impedir eliminar si tiene pedidos
    -- IF EXISTS (SELECT 1 FROM Pedido WHERE idCliente = p_idCliente) THEN
    --     SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede eliminar un cliente con pedidos';
    -- END IF;

    DELETE FROM Cliente WHERE idCliente = p_idCliente;

    COMMIT;
END/
DELIMITER ;

-- CONSULTAR
DELIMITER /
CREATE PROCEDURE sp_consultarClientes()
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al listar clientes';
    END;

    SELECT  c.idCliente,
            c.nombre,
            c.correo,
            c.tipo,
            c.telefono,
            c.dir_ciudad,
            c.dir_calle,
            c.dir_referencia
    FROM Cliente c
    ORDER BY c.idCliente;
END/
DELIMITER ;
