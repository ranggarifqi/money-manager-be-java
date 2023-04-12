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
    CONSTRAINT "Users_email_unique_idx" UNIQUE ("email"),
    CONSTRAINT "Users_phone_unique_idx" UNIQUE ("phone")
)
--rollback DROP TABLE Users;
