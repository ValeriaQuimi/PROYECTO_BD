USE gemaplusdb; 

-- SP Para Producto

DELIMITER //
CREATE PROCEDURE insertarProducto(
 IN p_nombre VARCHAR(100),
    IN p_descripcion TEXT,
    IN p_cantidadDisp INT,
    IN p_stockMin INT,
    IN p_precio DECIMAL(10,2),
    IN p_estado VARCHAR(20),
    IN p_tipo VARCHAR(20),
    IN p_idCat INT
)

BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        -- Si ocurre un error se hace rollback
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al insertar producto';
    END;

    START TRANSACTION;
    
    IF p_precio <= 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'El precio debe ser mayor a 0';
    END IF;

    IF p_cantidadDisp < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cantidad no puede ser negativa';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM Categoria WHERE idCat = p_idCat) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La categoría no existe';
    END IF;

    INSERT INTO Producto(nombreProduct, descripcion, cantidadDisp, stockMin, precio, estadoProducto, tipoProducto, idCat)
    VALUES(p_nombre, p_descripcion, p_cantidadDisp, p_stockMin, p_precio, p_estado, p_tipo, p_idCat);

    COMMIT;
END
//

DELIMITER ;


DELIMITER ///

CREATE PROCEDURE actualizarProducto(
	IN p_idProduct INT,
    IN p_precio DECIMAL(10,2),
    IN p_descripcion TEXT
)

BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al actualizar producto';
    END;

    START TRANSACTION;
    IF p_precio <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El precio debe ser mayor a 0';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM Producto WHERE idProduct = p_idProduct) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El producto no existe';
    END IF;

    UPDATE Producto
    SET precio = p_precio,
        descripcion = p_descripcion
    WHERE idProduct = p_idProduct;

    COMMIT;
END///
 
 DELIMITER; 
 
 
 
 DELIMITER /
 CREATE PROCEDURE eliminarProducto( IN p_idProduct INT)
 BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al eliminar producto';
    END;

    START TRANSACTION;

    IF NOT EXISTS (SELECT 1 FROM Producto WHERE idProduct = p_idProduct) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El producto no existe';
    END IF;

    DELETE FROM Producto WHERE idProduct = p_idProduct;

    COMMIT;
END/

DELIMITER ;
 
 
 
 DELIMITER /
 CREATE PROCEDURE consultarProductos()
 BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al listar productos';
    END;

    SELECT  p.idProduct,
            p.nombreProduct,
            p.descripcion,
            p.precio,
            p.cantidadDisp,
            p.stockMin,
            p.estadoProducto,
            p.tipoProducto,
            c.nombreCat
    FROM Producto p
    JOIN Categoria c ON p.idCat = c.idCat
    ORDER BY p.idProduct;
END/

DELIMITER ;

