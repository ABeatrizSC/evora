ALTER TABLE tickets
    DROP COLUMN status,
    DROP COLUMN purchased_at;

CREATE TABLE IF NOT EXISTS purchases (
    id VARCHAR(255) PRIMARY KEY NOT NULL,
    code VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL DEFAULT 0.0,
    due_date DATE NOT NULL,
    paid_at TIMESTAMP,
    cancelled_at TIMESTAMP
);

ALTER TABLE tickets
    ADD COLUMN purchase_id VARCHAR(255) UNIQUE,
    ADD CONSTRAINT fk_tickets_purchases
        FOREIGN KEY (purchase_id)
        REFERENCES purchases (id);
