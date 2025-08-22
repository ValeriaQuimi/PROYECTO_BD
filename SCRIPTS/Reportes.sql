USE gemaplusdb; 


-- Reporte 1
CREATE VIEW reporteCategoriaMasVendida AS
SELECT 
    cat.nombreCat AS Categoria,
    SUM(d.cantidad) AS total_cantidad_pedida,
    COUNT(DISTINCT p.idCliente) AS numero_clientes
FROM Detalle d
JOIN Producto pr ON d.idProduct = pr.idProduct
JOIN Categoria cat ON pr.idCat = cat.idCat
JOIN Pedido p ON d.numOrden = p.numOrden
GROUP BY cat.idCat, cat.nombreCat
ORDER BY total_cantidad_pedida DESC;


SELECT *
FROM reporteCategoriaMasVendida; 

-- Reporte 2

CREATE OR REPLACE VIEW reporteHistorialDeComprasDeClientes AS
SELECT 
    c.idCliente,
    c.nombre AS nombreCliente,
    c.correo,
    c.tipo,
    p.numOrden,
    p.fechaPedido, -- si no tienes fecha, se puede quitar
    p.precioTotal,
    d.cantidad,
    pr.nombreProduct,
    pr.tipoProducto,
    d.precioCantidad
FROM Cliente c
INNER JOIN Pedido p ON c.idCliente = p.idCliente
INNER JOIN Detalle d ON p.numOrden = d.numOrden
INNER JOIN Producto pr  ON d.idProduct = pr.idProduct
ORDER BY c.idCliente, p.numOrden;

SELECT *
FROM reporteHistorialDeComprasDeClientes;


-- Reporte 3

CREATE OR REPLACE VIEW pagosDelMes as
SELECT
    (pa.idPago)ID,
    (pa.montoPago)Monto,
    (pa.fechaPago)Fecha,
    (pa.metodoPago)Metodo,
    (pe.numOrden)Pedido,
    (c.nombre)Cliente,
    (c.correo)Correo,
    (c.tipo)TipoCliente
FROM cliente c
JOIN pedido pe USING(idCliente)
JOIN pago pa USING(numOrden)
WHERE MONTH(fechaPago) = MONTH(curdate());

SELECT *
FROM pagosdelmes;