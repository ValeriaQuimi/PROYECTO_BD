use gemaplusdb;

ALTER TABLE Cliente
MODIFY COLUMN dir_calle VARCHAR(100) NOT NULL;

INSERT INTO Cliente (nombre, telefono, correo, tipo, dir_ciudad, dir_calle, dir_referencia) VALUES
('Ana Torres', 983123456, 'ana.torres@mail.com', 'vip', 'Quito', 'Av. Amazonas', 'Frente al Quicentro'),
('Carlos Ruiz', 984654321, 'carlos.ruiz@mail.com', 'estandar', 'Guayaquil', 'Calle Chile', 'Junto a la farmacia Cruz Azul'),
('Lucía Pérez', 985876543, 'lucia.perez@mail.com', 'vip', 'Cuenca', 'Av. Solano', '3er piso, edificio Andino'),
('Pedro Gómez', 986567890, 'pedro.gomez@mail.com', 'estandar', 'Quito', 'Jr. Manabí', 'Puerta gris con rejas'),
('María Soto', 987345678, 'maria.soto@mail.com', 'vip', 'Loja', 'Calle Bolívar', 'A media cuadra del mercado'),
('Jorge León', 988678901, 'jorge.leon@mail.com', 'estandar', 'Ambato', 'Av. Cevallos', 'Frente al Mall de los Andes'),
('Valeria Díaz', 989999111, 'valeria.diaz@mail.com', 'vip', 'Manta', 'Av. Malecón', 'Casa color blanco con azul'),
('Renato Paredes', 981010101, 'renato.paredes@mail.com', 'estandar', 'Portoviejo', 'Jr. Olmedo', 'Cerca del estadio'),
('Karla Mendoza', 982020202, 'karla.mendoza@mail.com', 'vip', 'Riobamba', '10 de Agosto', 'Pasaje sin nombre'),
('Daniel Salas', 983030303, 'daniel.salas@mail.com', 'estandar', 'Quito', 'Av. 6 de Diciembre', 'Edificio El Dorado, piso 5');


INSERT INTO Categoria (nombreCat, descripcion) VALUES
('Anillos', 'Anillos de compromiso y moda'),
('Collares', 'Collares de oro, plata y bisutería'),
('Pulseras', 'Pulseras de diferentes materiales'),
('Aretes', 'Aretes modernos y clásicos'),
('Relojes', 'Relojes para dama y caballero'),
('Joyas Personalizadas', 'Diseños únicos a pedido'),
('Broches', 'Broches elegantes'),
('Alianzas', 'Alianzas matrimoniales'),
('Set de Joyería', 'Conjunto de joyas'),
('Otros', 'Accesorios varios');

INSERT INTO Producto (nombreProduct, descripcion, cantidadDisp, stockMin, precio, estadoProducto, tipoProducto, idCat) VALUES
('Anillo Simple Acero', 'Anillo económico de acero inoxidable', 30, 5, 8.50, 'disponible', 'estandar', 1),
('Collar con Dije', 'Collar artesanal con dije de resina', 20, 3, 12.00, 'disponible', 'estandar', 2),
('Pulsera Tejida', 'Pulsera tejida a mano', 40, 10, 4.00, 'disponible', 'estandar', 3),
('Aretes de Madera', 'Aretes hechos con madera reciclada', 25, 5, 5.00, 'disponible', 'estandar', 4),
('Reloj Artesanal', 'Reloj con correa de cuero y diseño único', 10, 2, 18.00, 'disponible', 'estandar', 5),
('Anillo Personalizado', 'Anillo hecho a pedido con iniciales', 5, 1, 15.00, 'disponible', 'personalizado', 6),
('Broche Flor', 'Broche con diseño de flor pintado a mano', 10, 2, 6.00, 'disponible', 'estandar', 7),
('Alianza Económica', 'Pareja de alianzas en acero inoxidable', 12, 3, 14.00, 'disponible', 'estandar', 8),
('Set Básico', 'Set de collar + aretes en caja artesanal', 6, 2, 20.00, 'disponible', 'estandar', 9),
('Llavero con Nombre', 'Llavero personalizado con grabado', 25, 5, 3.50, 'disponible', 'estandar', 10);


INSERT INTO Pedido (precioTotal, saldoRestante, tipoPedido, estadoPedido, idCliente) VALUES
(20.00, 0.00, 'domicilio', 'entregada', 1),
(12.00, 0.00, 'local', 'entregada', 2),
(15.00, 5.00, 'domicilio', 'preparando', 3),
(12.00, 0.00, 'local', 'entregada', 4),
(8.00, 0.00, 'domicilio', 'entregada', 5),
(14.00, 0.00, 'local', 'entregada', 6),
(4.00, 0.00, 'domicilio', 'entregada', 7),
(6.00, 0.00, 'local', 'entregada', 8),
(5.00, 0.00, 'domicilio', 'anulado', 9),
(20.00, 5.00, 'domicilio', 'preparando', 10);


INSERT INTO Detalle (numOrden, idProduct, cantidad, precioCantidad) VALUES
(1, 1, 2, 17.00), 
(2, 2, 1, 12.00),
(3, 6, 1, 15.00),
(4, 2, 1, 12.00),
(5, 4, 2, 10.00),
(6, 8, 1, 14.00),
(7, 3, 1, 4.00),
(8, 7, 1, 6.00),
(9, 4, 1, 5.00),
(10, 9, 1, 20.00);


INSERT INTO Pago (fechaPago, montoPago, metodoPago, numOrden) VALUES
('2025-07-20', 20.00, 'efectivo', 1),
('2025-07-21', 12.00, 'transferencia', 2),
('2025-07-22', 10.00, 'efectivo', 3),
('2025-07-23', 12.00, 'transferencia', 4),
('2025-07-24', 8.00, 'efectivo', 5),
('2025-07-25', 14.00, 'efectivo', 6),
('2025-07-26', 4.00, 'transferencia', 7),
('2025-07-26', 6.00, 'efectivo', 8),
('2025-07-26', 0.00, 'efectivo', 9),
('2025-07-26', 15.00, 'transferencia', 10);

INSERT INTO Repartidor (telefono, nombre) VALUES
(900111222, 'Luis Paredes'),
(900222333, 'Fernanda Ríos'),
(900333444, 'David Romero'),
(900444555, 'Carla Mejía'),
(900555666, 'Juan López'),
(900666777, 'Andrea Salazar'),
(900777888, 'Sergio Ponce'),
(900888999, 'Elena Gutiérrez'),
(900999000, 'Diego Núñez'),
(901000111, 'Natalia Espinoza');

INSERT INTO Entrega (fechaEntrega, estadoEntrega, idRepartidor, numOrden) VALUES
('2025-07-20', 'entregada', 1, 1),
('2025-07-21', 'entregada', 2, 2),
('2025-07-22', 'pendiente', 3, 3),
('2025-07-23', 'entregada', 4, 4),
('2025-07-24', 'entregada', 5, 5),
('2025-07-25', 'entregada', 6, 6),
('2025-07-25', 'entregada', 7, 7),
('2025-07-26', 'entregada', 8, 8),
('2025-07-26', 'entregada', 9, 9),
('2025-07-26', 'pendiente', 10, 10);

