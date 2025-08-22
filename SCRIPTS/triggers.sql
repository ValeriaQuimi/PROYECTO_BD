USE gemaplusdb;


-- trigger

DELIMITER //
CREATE TRIGGER actualizarStock
AFTER INSERT ON Detalle
FOR EACH ROW
BEGIN
    UPDATE Producto
    SET cantidadDisp = cantidadDisp - NEW.cantidad
    WHERE idProduct = NEW.idProduct;

    UPDATE Producto
    SET estadoProducto = 'agotado'
    WHERE idProduct = NEW.idProduct
      AND cantidadDisp <= 0;
END//
DELIMITER ;
