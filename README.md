## How to Add a new DB Migration script
1. Install liquibase

```
# list out all possible liquibase configuration for pom.xml file
./mvnw liquibase:help -Ddetail=true -Dgoal=generateChangeLog
```
2. Liquibase only read 1 changelog file. So if you want to add some migration script, add a new changeset to `./migrations/changelog.sql`.

```
--liquibase formatted sql
/* https://docs.liquibase.com/concepts/changelogs/sql-format.html */

--changeset authorname:<current_timestamp>_<migration_name>
--comment: example comment
create table person (
    id int primary key auto_increment not null,
    name varchar(50) not null,
    address1 varchar(50),
    address2 varchar(50),
    city varchar(30)
)
--rollback DROP TABLE person;

--changeset authorname:2
--comment: example comment

```

3. If all good, then run this
```
./mvnw compile package
```

## How to Rollback Migration
```
# Generate the SQL, ensure that the SQL is correct
./mvnw liquibase:rollbackSQL -Dliquibase.rollbackCount=1

# After we're sure, then run this
./mvnw liquibase:rollback -Dliquibase.rollbackCount=1
```

Then if you want to migrate again, execute this

```
./mvnw liquibase:updateSQL
./mvnw liquibase:update
```
