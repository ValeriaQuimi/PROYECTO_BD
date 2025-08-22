USE gemaplusdb;


--TRIGGER 1

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

--TRIGGER 2

DELIMITER $$

CREATE TRIGGER actualizarSaldo
BEFORE INSERT ON Pago
FOR EACH ROW
BEGIN
    DECLARE saldo DECIMAL(10,2);

    SELECT saldoRestante INTO saldo
    FROM Pedido
    WHERE numOrden = NEW.numOrden;

    IF NEW.montoPago > saldo THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Monto de pago inválido';
        
    ELSE
        UPDATE Pedido
        SET saldoRestante = saldoRestante - NEW.montoPago
        WHERE numOrden = NEW.numOrden;

    END IF;

END

$$DELIMITER ;
