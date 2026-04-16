-- ──────────────────────────────────────────────────────────
-- QuantityMeasurementApp — MySQL Initialization Script
-- Runs once when the container is first created.
-- ──────────────────────────────────────────────────────────

-- Grant the app user full access to the app database
-- (user is created by MYSQL_USER / MYSQL_PASSWORD env vars)
GRANT ALL PRIVILEGES ON quantity_db.* TO '${MYSQL_USER}'@'%';
FLUSH PRIVILEGES;
