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
    ('Cash'),
    ('Receivables'),
    ('Loans'),
    ('Savings'),
    ('Prepaid'),
    ('Investments'),
    ('Overdrafts'),
    ('Others')
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
    CONSTRAINT "fk_Accounts_AccountTypes" FOREIGN KEY("accountType") REFERENCES "AccountTypes"("name") ON DELETE NO ACTION,
    CONSTRAINT "fk_Accounts_Users" FOREIGN KEY("userId") REFERENCES "Users"("id") ON DELETE CASCADE
);
--rollback DROP TABLE IF EXISTS "Accounts";

--changeset rangga:1681394700_create_transaction_types_table
CREATE TABLE IF NOT EXISTS "TransactionTypes" (
    "name" VARCHAR NOT NULL PRIMARY KEY,
    "createdAt" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamptz
);
INSERT INTO "TransactionTypes" ("name")
VALUES
    ('Income'),
    ('Expense'),
    ('Transfer')
;
--rollback DROP TABLE IF EXISTS "TransactionTypes";

--changeset rangga:1681394710_create_transactions_table
CREATE TABLE IF NOT EXISTS "Transactions" (
    "id" UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    "userId" UUID NOT NULL,
    "fromAccountId" UUID NOT NULL,
    "toAccountId" UUID NULL,
    "transactionType" VARCHAR NOT NULL,
    "date" DATE NOT NULL,
    "amount" NUMERIC(14, 2) NOT NULL,
    "note" TEXT NULL,
    "createdAt" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamptz,
    CONSTRAINT "fk_Transactions_Users" FOREIGN KEY ("userId") REFERENCES "Users"("id") ON DELETE CASCADE,
    CONSTRAINT "fk_Transactions_Accounts_from" FOREIGN KEY("fromAccountId") REFERENCES "Accounts"("id") ON DELETE CASCADE,
    CONSTRAINT "fk_Transactions_Accounts_to" FOREIGN KEY("toAccountId") REFERENCES "Accounts"("id") ON DELETE CASCADE,
    CONSTRAINT "fk_Transactions_TransactionTypes" FOREIGN KEY("transactionType") REFERENCES "TransactionTypes"("name") ON DELETE NO ACTION
);
--rollback DROP TABLE IF EXISTS "Transactions";

--changeset rangga:1681394720_create_categories_table
CREATE TABLE IF NOT EXISTS "Categories" (
    "id" UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    "parentId" UUID NULL,
    "userId" UUID NOT NULL,
    "name" VARCHAR NOT NULL,
    "createdAt" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamptz,
    CONSTRAINT "fk_Categories_Users" FOREIGN KEY("userId") REFERENCES "Users"("id") ON DELETE CASCADE,
    CONSTRAINT "fk_Categories_parent" FOREIGN KEY("parentId") REFERENCES "Categories"("id") ON DELETE SET NULL
);
--rollback DROP TABLE IF EXISTS "Categories";

--changeset rangga:1681394730_create_transaction_categories_table
CREATE TABLE IF NOT EXISTS "TransactionCategories" (
    "id" UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    "transactionId" UUID NOT NULL,
    "categoryId" UUID NOT NULL,
    "createdAt" timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" timestamptz,
    CONSTRAINT "fk_TransactionCategories_Transactions" FOREIGN KEY("transactionId") REFERENCES "Transactions"("id") ON DELETE CASCADE,
    CONSTRAINT "fk_TransactionCategories_Categories" FOREIGN KEY("categoryId") REFERENCES "Categories"("id") ON DELETE CASCADE
);
--rollback DROP TABLE IF EXISTS "TransactionCategories";

--changeset rangga:1681555532_add_user_verification_columns_to_users_table
ALTER TABLE "Users"
ADD COLUMN "verifyToken" VARCHAR NULL,
ADD COLUMN "verifiedAt" timestamptz NULL
;
--rollback ALTER TABLE "Users" DROP COLUMN "verifyToken", DROP COLUMN "verifiedAt";