-- Roles base: se insertan automáticamente al arrancar la app
INSERT INTO rol (nombre) VALUES ('PADRE') ON CONFLICT (nombre) DO NOTHING;
INSERT INTO rol (nombre) VALUES ('HIJO')  ON CONFLICT (nombre) DO NOTHING;
INSERT INTO rol (nombre) VALUES ('ADMIN') ON CONFLICT (nombre) DO NOTHING;
