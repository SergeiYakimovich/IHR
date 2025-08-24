DROP TABLE IF EXISTS ihr.candidates;
DROP TABLE IF EXISTS ihr.vacancies;
DROP TABLE IF EXISTS ihr.skills;
DROP TABLE IF EXISTS ihr.entity_skills;

DELETE FROM ihr.databasechangelog WHERE filename = '0.0.1/0.0.1-create-tables.xml';