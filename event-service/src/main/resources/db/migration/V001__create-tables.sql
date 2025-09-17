-- -----------------------------------------------------
-- Table address
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS address (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  postal_code VARCHAR(20) NOT NULL,
  street VARCHAR(100) NOT NULL,
  province VARCHAR(60) NOT NULL,
  city VARCHAR(60) NOT NULL,
  state VARCHAR(60) NOT NULL,
  country VARCHAR(60) NOT NULL
);

-- -----------------------------------------------------
-- Table events
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS events (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  creator_id VARCHAR(255) NOT NULL,
  title VARCHAR(150) NOT NULL,
  description VARCHAR(255),
  date TIMESTAMP NOT NULL,
  hour TIME NOT NULL,
  duration TIME NOT NULL,
  category VARCHAR(50) NOT NULL,
  address_id VARCHAR(255) NOT NULL,
  address_number INT NOT NULL,
  address_complement VARCHAR(50),
  ticket_price DECIMAL(10,2) NOT NULL,
  capacity INT NOT NULL,
  CONSTRAINT fk_events_address
    FOREIGN KEY (address_id)
    REFERENCES address (id)
);

-- -----------------------------------------------------
-- Table tickets
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tickets (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  event_id VARCHAR(255) NOT NULL,
  code VARCHAR(255) NOT NULL UNIQUE,
  status VARCHAR(50) NOT NULL,
  purchased_at TIMESTAMP NOT NULL,
  participant_id VARCHAR(255) NOT NULL,
  CONSTRAINT fk_tickets_events
    FOREIGN KEY (event_id)
    REFERENCES events (id)
);

-- -----------------------------------------------------
-- Table payouts
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS payouts (
  id VARCHAR(255) PRIMARY KEY NOT NULL,
  event_id VARCHAR(255) NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  code VARCHAR(255) NOT NULL,
  status VARCHAR(50) NOT NULL,
  due_date TIMESTAMP NOT NULL,
  paid_at TIMESTAMP,
  CONSTRAINT fk_payouts_events
    FOREIGN KEY (event_id)
    REFERENCES events (id)
);