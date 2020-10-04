CREATE TABLE APP.COMPANY (
	companyName varchar(50) NOT NULL,
	companyAddress varchar(150) NOT NULL,
	companyCity varchar(50) NOT NULL,
	gstNumber varchar(100),
	PRIMARY KEY(gstNumber),
	createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE APP.CUSTOMER (
    customerId INT PRIMARY KEY, 
    name VARCHAR(30) NOT NULL,
    gstNumber VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(50) NOT NULL,
    city VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    phone VARCHAR(10) NOT NULL UNIQUE,
    pincode VARCHAR(10) NOT NULL,
    password VARCHAR(30) NOT NULL,
    lastLogin TIMESTAMP,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE APP.EMPLOYEE (
	employeeId int PRIMARY KEY,
	userName VARCHAR(30) NOT NULL UNIQUE,
	password varchar(30)NOT NULL,
	lastLogin TIMESTAMP,
	createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);

CREATE TABLE APP.GSTTYPE (
    gstTypeId INT PRIMARY KEY,
	type varchar(20) NOT NULL
);

CREATE TABLE APP.INVOICE (
    invoiceId int primary key,
    invoiceDate Date NOT NULL,
    orderId INT NOT NULL,
    customerId  int NOT NULL,
    gstTypeId INT NOT NULL,
    gstAmount float DEFAULT 0,
    totalInvoiceAmount float DEFAULT 0,
    status BOOLEAN NOT NULL DEFAULT FALSE, 
    FOREIGN KEY (gstTypeId) REFERENCES APP.GSTTYPE(gstTypeId),
    FOREIGN KEY (customerId) REFERENCES APP.CUSTOMER(customerId),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE APP.ORDERDETAILS (
    orderId int,
    orderDate DATE DEFAULT CURRENT_DATE NOT NULL,
    FOREIGN KEY (customerId) int NOT NULL,
    totalOrderValue double NOT NULL, -- will be calculated on the go
    shippingCost double NOT NULL, -- will be calculated on the go 
    FOREIGN KEY (shippingAgency) REFERENCES  APP.SHIPPINGCOMPANY(companyId),
    status varchar(10) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   
    PRIMARY KEY(orderId)
);

-- Single order can have multiple products
CREATE TABLE APP.ORDERPRODUCTS (
    orderId int,
    productId int,  
    primary key (orderId, productId)
);

CREATE TABLE APP.ORDERSTATUS (
        orderStatusId INT PRIMARY KEY,
        status VARCHAR(20) NOT NULL
);

CREATE TABLE APP.PRODUCT (
    productId int PRIMARY KEY,
   	productName varchar(20) NOT NULL,
    productPrice float NOT NULL,
    productCategoryId INT NOT NULL,
    FOREIGN KEY (categoryId) REFERENCES APP.PRODUCTCATEGORY(categoryId),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE APP.PRODUCTCATEGORY (
	categoryId INT PRIMARY KEY, 
    categoryName varchar(20) PRIMARY KEY
);

CREATE TABLE APP.SHIPPINGCOMPANY (
    companyId int,
    companyName varchar(20),  
    primary key (companyId)
);