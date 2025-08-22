USE gemaplusdb;

--SP PARA CATEGORIA


-- INSERTAR

DELIMITER $

CREATE PROCEDURE sp_insertarCategoria
    (
    IN name VARCHAR(30),
    IN description VARCHAR(250)
    )
    BEGIN
        DECLARE existente VARCHAR(30);

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            ROLLBACK;
            SIGNAL SQLSTATE "45000" SET MESSAGE_TEXT = "Ha ocurrido un error";
        END;
        
        START TRANSACTION;
        
        SELECT nombreCat INTO existente
        FROM categoria
        WHERE nombreCat = name;
        
        IF existente IS NULL THEN
            INSERT INTO categoria(nombreCat,descripcion) VALUES (name,description);
        END IF;

        COMMIT;
        
    END 

$ DELIMITER ;


-- ACTUALIZAR

DELIMITER $
CREATE PROCEDURE sp_actualizarCategoria
	(
    IN id INT,
    IN name VARCHAR(30),
    IN description VARCHAR(250)
    )
	BEGIN
		DECLARE existente INT;
    
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN
			ROLLBACK;
			SIGNAL SQLSTATE "45000" SET MESSAGE_TEXT = "Ha ocurrido un error";
		END;
	
		START TRANSACTION;
    
		SELECT idCat INTO existente
		FROM categoria
		WHERE idCat = id;
		
		IF existente IS NOT NULL THEN
			UPDATE categoria
            SET 
				nombreCat = name,
				descripcion = description
			WHERE idCat = id;

		END IF;

		COMMIT;
		
	END 

$ DELIMITER ;


-- ELIMINAR

DELIMITER $
CREATE PROCEDURE sp_eliminarCategoria
	(
    IN id INT
    )
	BEGIN
		DECLARE existente INT;
    
		DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN
			ROLLBACK;
			SIGNAL SQLSTATE "45000" SET MESSAGE_TEXT = "Ha ocurrido un error";
		END;
	
		START TRANSACTION;
    
		SELECT idCat INTO existente
		FROM categoria
		WHERE idCat = id;
		
		IF existente IS NOT NULL THEN
			DELETE FROM categoria
			WHERE idCat = id;
            
		END IF;
        
		COMMIT;

	END 
    
$ DELIMITER ;