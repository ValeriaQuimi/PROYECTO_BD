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