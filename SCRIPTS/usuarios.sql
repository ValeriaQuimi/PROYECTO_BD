USE gemaplusdb;

-- crear el primer usuario
CREATE USER 'est1'@'localhost' IDENTIFIED BY '2025bd1pa';

-- darle por lo menos 2 permisos
GRANT EXECUTE ON PROCEDURE gemaplusdb.insertarProducto TO 'est1'@'localhost'; 
GRANT SELECT, INSERT ON gemaplusdb.Producto TO 'est1'@'localhost'; 

GRANT SELECT ON gemaplusdb.reporteCategoriaMasVendida TO 'est1'@'localhost';

--crear el segundo usuario
CREATE USER 'est2'@'localhost' IDENTIFIED BY '2025bd2pa';

-- Darle por lo menos 2 permisos 
GRANT EXECUTE ON PROCEDURE gemaplusdb.insertarCliente TO 'est2'@'localhost';
GRANT SELECT, UPDATE ON gemaplusdb.Pedido TO 'est2'@'localhost';

GRANT SELECT ON gemaplusdb.reporteHistorialDeComprasDeClientes TO 'est2'@'localhost';
