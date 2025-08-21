-- CREATE DATABASE gemaplusdb;
USE gemaplusdb;

CREATE TABLE Cliente (
    idCliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    telefono INT NOT NULL,
    correo VARCHAR(80) NOT NULL,
    tipo ENUM('vip', 'estandar') NOT NULL,
    dir_ciudad VARCHAR(20) NOT NULL,
    dir_calle VARCHAR(10) NOT NULL,
    dir_referencia VARCHAR(50) NOT NULL
);

CREATE TABLE Categoria (
    idCat INT AUTO_INCREMENT PRIMARY KEY,
    nombreCat VARCHAR(30) NOT NULL,
    descripcion VARCHAR(250)
);

CREATE TABLE Producto (
    idProduct INT AUTO_INCREMENT PRIMARY KEY,
    nombreProduct VARCHAR(30) NOT NULL,
    descripcion VARCHAR(250),
    cantidadDisp INT NOT NULL,
    stockMin INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    estadoProducto ENUM('disponible', 'agotado') NOT NULL,
    tipoProducto ENUM('personalizado', 'estandar') NOT NULL,
    idCat INT NOT NULL,
    FOREIGN KEY (idCat) REFERENCES Categoria(idCat)
);

CREATE TABLE Pedido (
    numOrden INT AUTO_INCREMENT PRIMARY KEY,
    precioTotal DECIMAL(10,2) NOT NULL,
    saldoRestante DECIMAL(10,2) NOT NULL,
    tipoPedido ENUM('domicilio', 'local') NOT NULL,
    estadoPedido ENUM('preparando', 'entregada', 'anulado') NOT NULL,
    idCliente INT NOT NULL,
    FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)
);

CREATE TABLE Detalle (
    numOrden INT NOT NULL,
    idProduct INT NOT NULL,
    cantidad INT NOT NULL,
    precioCantidad DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (numOrden, idProduct),
    FOREIGN KEY (numOrden) REFERENCES Pedido(numOrden),
    FOREIGN KEY (idProduct) REFERENCES Producto(idProduct)
);

CREATE TABLE Pago (
    idPago INT AUTO_INCREMENT PRIMARY KEY,
    fechaPago DATE NOT NULL,
    montoPago DECIMAL(10,2) NOT NULL,
    metodoPago ENUM('efectivo', 'transferencia') NOT NULL,
    numOrden INT NOT NULL,
    FOREIGN KEY (numOrden) REFERENCES Pedido(numOrden)
);

CREATE TABLE Repartidor (
    idRepartidor INT AUTO_INCREMENT PRIMARY KEY,
    telefono INT NOT NULL,
    nombre VARCHAR(20) NOT NULL
);

CREATE TABLE Entrega (
    numEntrega INT AUTO_INCREMENT,
    fechaEntrega DATE NOT NULL,
    estadoEntrega ENUM('pendiente', 'entregada') NOT NULL,
    idRepartidor INT NOT NULL,
    numOrden INT NOT NULL,
    PRIMARY KEY (numEntrega),
    FOREIGN KEY (idRepartidor) REFERENCES Repartidor(idRepartidor),
    FOREIGN KEY (numOrden) REFERENCES Pedido(numOrden)
);
