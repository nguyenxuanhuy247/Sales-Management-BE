START TRANSACTION;
SET
time_zone = "+00:00";

CREATE
DATABASE IF NOT EXISTS shopapp;
USE
shopapp;

-- Bảng roles
CREATE TABLE roles
(
  id   INT(11) PRIMARY KEY NOT NULL,
  name VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Bảng users
CREATE TABLE users
(
  id            INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  fullname      VARCHAR(100)          DEFAULT '',
  phone_number  VARCHAR(10)  NOT NULL,
  address       VARCHAR(200)          DEFAULT '',
  password      VARCHAR(100) NOT NULL DEFAULT '',
  created_at    DATETIME              DEFAULT NULL,
  updated_at    DATETIME              DEFAULT NULL,
  is_active     TINYINT(1) DEFAULT 1,
  date_of_birth DATE                  DEFAULT NULL,
  role_id       INT                   DEFAULT NULL,
  KEY           idx_role_id (role_id),
  CONSTRAINT fk_users_roles FOREIGN KEY (role_id) REFERENCES roles (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Bảng tokens
CREATE TABLE tokens
(
  id              INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
  token           VARCHAR(255) NOT NULL,
  token_type      VARCHAR(50)  NOT NULL,
  expiration_date DATETIME DEFAULT NULL,
  revoked         TINYINT(1) NOT NULL,
  expired         TINYINT(1) NOT NULL,
  user_id         INT      DEFAULT NULL,
  KEY             idx_user_id (user_id),
  CONSTRAINT uq_token UNIQUE (token),
  CONSTRAINT fk_tokens_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Bảng categories
CREATE TABLE categories
(
  id   INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: đồ điện tử'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Bảng products
CREATE TABLE products
(
  id          INT(11) PRIMARY KEY AUTO_INCREMENT,
  name        VARCHAR(350) DEFAULT NULL COMMENT 'Tên sản phẩm',
  price       FLOAT NOT NULL CHECK (price >= 0),
  thumbnail   VARCHAR(300) DEFAULT '',
  description LONGTEXT     DEFAULT '',
  created_at  DATETIME     DEFAULT NULL,
  updated_at  DATETIME     DEFAULT NULL,
  category_id INT(11) DEFAULT NULL,
  KEY         idx_category_id (category_id),
  CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Bảng orders
CREATE TABLE orders
(
  id               INT(11) PRIMARY KEY AUTO_INCREMENT,
  user_id          INT(11) DEFAULT NULL,
  fullname         VARCHAR(100)   DEFAULT '',
  email            VARCHAR(100)   DEFAULT '',
  phone_number     VARCHAR(20)  NOT NULL,
  address          VARCHAR(200) NOT NULL,
  note             VARCHAR(100)   DEFAULT '',
  order_date       DATETIME       DEFAULT CURRENT_TIMESTAMP,
  status           ENUM('pending','processing','shipped','delivered','cancelled') DEFAULT NULL COMMENT 'Trạng thái đơn hàng',
  total_money      DECIMAL(15, 2) DEFAULT 0,
  active           TINYINT(1) DEFAULT 1,
  shipping_method  VARCHAR(100)   DEFAULT NULL,
  shipping_address VARCHAR(200)   DEFAULT NULL,
  shipping_date    DATE           DEFAULT NULL,
  tracking_number  VARCHAR(100)   DEFAULT NULL,
  payment_method   VARCHAR(100)   DEFAULT NULL,
  KEY              idx_orders_user_id (user_id),
  CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Bảng order_details
CREATE TABLE order_details
(
  id                 INT(11) PRIMARY KEY AUTO_INCREMENT,
  order_id           INT(11) DEFAULT NULL,
  product_id         INT(11) DEFAULT NULL,
  price              FLOAT       DEFAULT NULL CHECK (price >= 0),
  number_of_products INT         DEFAULT NULL CHECK (number_of_products > 0),
  total_money        FLOAT       DEFAULT NULL CHECK (total_money >= 0),
  color              VARCHAR(20) DEFAULT '',
  KEY                idx_order_id (order_id),
  KEY                idx_product_id (product_id),
  CONSTRAINT fk_order_details_order FOREIGN KEY (order_id) REFERENCES orders (id),
  CONSTRAINT fk_order_details_product FOREIGN KEY (product_id) REFERENCES products (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

COMMIT;
