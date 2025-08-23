USE gemaplusdb;

--INDICES

CREATE INDEX idx_pagosPorFecha ON pago(fechaPago);

CREATE INDEX idx_metodosPagoPorFecha ON pago(fechaPago,metodoPago);

CREATE INDEX idx_pedidosPorCliente ON Pedido(idCliente);

CREATE INDEX idx_productosPorCategoria ON Producto(idCat);
