-- Insertar una nueva competici�n
INSERT INTO COMPETICIONES (NOMBRE, FECHA_INICIO, FECHA_FIN, CURSO, COD_JUEGO)
VALUES ('Torneo de Verano', TO_DATE('2024-06-01', 'YYYY-MM-DD'), TO_DATE('2024-08-01', 'YYYY-MM-DD'), 1, 1);

-- Suponiendo que el COD_COMPE generado para esta competici�n es 1

-- Insertar cuatro nuevos equipos
INSERT INTO EQUIPOS (NOMBRE, FECHA_FUNDACION)
VALUES ('Equipo A', TO_DATE('2000-01-01', 'YYYY-MM-DD'));

INSERT INTO EQUIPOS (NOMBRE, FECHA_FUNDACION)
VALUES ('Equipo B', TO_DATE('2001-01-01', 'YYYY-MM-DD'));

INSERT INTO EQUIPOS (NOMBRE, FECHA_FUNDACION)
VALUES ('Equipo C', TO_DATE('2002-01-01', 'YYYY-MM-DD'));

INSERT INTO EQUIPOS (NOMBRE, FECHA_FUNDACION)
VALUES ('Equipo D', TO_DATE('2003-01-01', 'YYYY-MM-DD'));

-- Suponiendo que los COD_EQUIPO generados para estos equipos son 1, 2, 3, y 4

-- Asociar los equipos con la competici�n
INSERT INTO EQUIPO_COMPETICION (COD_EQUIPO, COD_COMPETICION, PUNTOS)
VALUES (13, 5, 0);

INSERT INTO EQUIPO_COMPETICION (COD_EQUIPO, COD_COMPETICION, PUNTOS)
VALUES (14, 5, 0);

INSERT INTO EQUIPO_COMPETICION (COD_EQUIPO, COD_COMPETICION, PUNTOS)
VALUES (15, 5, 0);

INSERT INTO EQUIPO_COMPETICION (COD_EQUIPO, COD_COMPETICION, PUNTOS)
VALUES (16, 5, 0);

-- Ejecutar el procedimiento para una competici�n espec�fica
BEGIN
    GENERAR_CALENDARIO(5);
END;
/

-- Verificar jornadas
SELECT * FROM JORNADAS WHERE COD_COMPE = 5;

-- Verificar enfrentamientos
SELECT * FROM ENFRENTAMIENTOS WHERE COD_JORNADA IN (SELECT COD_JORNADAS FROM JORNADAS WHERE COD_COMPE = 5);