USE gemaplusdb;

--INDICES

CREATE INDEX idx_pagosPorFecha ON pago(fechaPago);

CREATE INDEX idx_metodosPagoPorFecha ON pago(fechaPago,metodoPago);