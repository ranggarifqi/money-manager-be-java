-- liquibase formatted sql

--changeset rangga:1681308324_create_uuid_extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--rollback DROP EXTENSION IF EXISTS "uuid-ossp";

--changeset rangga:1681308557_create_users_table
CREATE TABLE IF NOT EXISTS "Users" (
    "id" UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    "name" VARCHAR NOT NULL,
    "email" VARCHAR NOT NULL UNIQUE,
    "phone" VARCHAR NOT NULL UNIQUE,
    "password" VARCHAR NOT NULL,
    "createdAt" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamptz,
    CONSTRAINT "Users_email_unique_idx" UNIQUE ("email"),
    CONSTRAINT "Users_phone_unique_idx" UNIQUE ("phone")
);
--rollback DROP TABLE IF EXISTS "Users";

--changeset rangga:1681394686_create_account_types_table
CREATE TABLE IF NOT EXISTS "AccountTypes" (
    "name" VARCHAR NOT NULL PRIMARY KEY,
    "createdAt" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamptz
);
INSERT INTO "AccountTypes" ("name")
VALUES
    ("Cash"),
    ("Receivables"),
    ("Loans"),
    ("Savings"),
    ("Prepaid"),
    ("Investments"),
    ("Overdrafts"),
    ("Others")
;
--rollback DROP TABLE IF EXISTS "AccountTypes";

--changeset rangga:1681394690_create_accounts_table
CREATE TABLE IF NOT EXISTS "Accounts" (
    "id" UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    "accountType" VARCHAR NOT NULL,
    "userId" UUID NOT NULL,
    "name" VARCHAR NOT NULL,
    "balance" NUMERIC(14, 2) NOT NULL DEFAULT 0,
    "createdAt" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamptz,
    CONSTRAINT "fk_accountType" FOREIGN KEY("accountType") REFERENCES "AccountTypes"("name") ON DELETE CASCADE,
    CONSTRAINT "fk_userId" FOREIGN KEY("userId") REFERENCES "Users"("id") ON DELETE CASCADE
);
--rollback DROP TABLE IF EXISTS "Accounts";