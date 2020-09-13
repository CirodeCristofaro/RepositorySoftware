--------------------------------------------------------
--  Drop  
--------------------------------------------------------
DROP SEQUENCE    IDCLASSEINC ;
DROP SEQUENCE    IDPACKAGEINC ;
DROP SEQUENCE    IDRELEASEINC ;
DROP SEQUENCE    IDTESTINC ;
DROP SEQUENCE    IDUTENTEINC ;
DROP TABLE    ACCEDE  cascade constraints;
DROP TABLE    AVVIA  cascade constraints;
DROP TABLE    CLASSI  cascade constraints;
DROP TABLE    PACKAGE  cascade constraints;
DROP TABLE    PARTECIPA  cascade constraints;
DROP TABLE    PROGETTO  cascade constraints;
DROP TABLE    RELEASE  cascade constraints;
DROP TABLE    SOTTOMETODO  cascade constraints;
DROP TABLE    SOTTOPACKAGE  cascade constraints;
DROP TABLE    TESTANO  cascade constraints;
DROP TABLE    TESTRELEASE  cascade constraints;
DROP TABLE    UTENTE  cascade constraints;
DROP VIEW    VIEWPROGETTO ;

--------------------------------------------------------
--     Sequence IDCLASSEINC incremento idclasse
--------------------------------------------------------

   CREATE SEQUENCE     IDCLASSEINC   MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 1 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--     Sequence IDPACKAGEINC incremento idpackage
--------------------------------------------------------

   CREATE SEQUENCE     IDPACKAGEINC   MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 1 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--     Sequence IDRELEASEINC incremento idrelease
--------------------------------------------------------

   CREATE SEQUENCE     IDRELEASEINC   MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 1 NOCACHE  ORDER  NOCYCLE ;
--------------------------------------------------------
--     Sequence IDTESTINC incremento idtest
--------------------------------------------------------

   CREATE SEQUENCE     IDTESTINC   MINVALUE 1 MAXVALUE 99999 INCREMENT BY 1 START WITH 1 NOCACHE ORDER  NOCYCLE ;
--------------------------------------------------------
--     Sequence IDUTENTEINC incremento idutente
--------------------------------------------------------

   CREATE SEQUENCE     IDUTENTEINC   MINVALUE 1 MAXVALUE 999999 INCREMENT BY 1 START WITH 1 NOCACHE  ORDER  NOCYCLE ;




--------------------------------------------------------
--  Table UTENTE
--------------------------------------------------------

  CREATE TABLE    UTENTE  
   (	 IDUTENTE  NUMBER NOT NULL PRIMARY KEY, 
	 NOME  VARCHAR2(20 BYTE) NOT NULL,
	 COGNOME  VARCHAR2(40 BYTE) NOT NULL
   );
--------------------------------------------------------
--  Table PROGETTO
--------------------------------------------------------

  CREATE TABLE    PROGETTO  
   (	 NOMEPROGETTO  VARCHAR2(200 BYTE) NOT NULL PRIMARY KEY, 
	 PATHNAME  VARCHAR2(200 BYTE) NOT NULL, 
	 DATAAPERTURA  DATE NOT NULL, 
	 DATACHIUSURA  DATE
   );
--------------------------------------------------------
-- Table PARTECIPA
--------------------------------------------------------

  CREATE TABLE    PARTECIPA  
   (	 IDUTENTE  NUMBER NOT NULL, 
	 NOMEPROGETTO  VARCHAR2(200 BYTE) NOT NULL,
	CONSTRAINT  IDUTENTEPARTECIPA_FK1  FOREIGN KEY ( IDUTENTE ) REFERENCES    UTENTE  ( IDUTENTE ),
	CONSTRAINT  NOMEPROGETTOPARTECIPA_FK2  FOREIGN KEY ( NOMEPROGETTO )  REFERENCES    PROGETTO  ( NOMEPROGETTO )
   );
--------------------------------------------------------
--   Table PACKAGE
--------------------------------------------------------

  CREATE TABLE    PACKAGE  
   (	 NOMEPACKAGE  VARCHAR2(200 BYTE) NOT NULL, 
	 PATHNAME  VARCHAR2(200 BYTE) NOT NULL, 
	 NOMEPROGETTO  VARCHAR2(200 BYTE) NOT NULL, 
	 IDPACKAGE  NUMBER NOT NULL PRIMARY KEY,
	CONSTRAINT  PACKAGE_FK1  FOREIGN KEY ( NOMEPROGETTO ) REFERENCES    PROGETTO  ( NOMEPROGETTO ),
	CONSTRAINT  NOMEPACKAGE_UN  UNIQUE( NOMEPACKAGE )
   );
--------------------------------------------------------
--    Table SOTTOPACKAGE
--------------------------------------------------------

  CREATE TABLE    SOTTOPACKAGE  
   (	 IDPACKAGE  NUMBER , 
	 SOTTOPACKAGE  NUMBER ,
	CONSTRAINT  IDPACKAGEPRINCIPALE_FK1  FOREIGN KEY ( IDPACKAGE )  REFERENCES    PACKAGE  ( IDPACKAGE ),
	CONSTRAINT  SOTTOPACKAGE_FK2  FOREIGN KEY ( SOTTOPACKAGE )   REFERENCES    PACKAGE  ( IDPACKAGE )
   );
--------------------------------------------------------
--     Table CLASSI
--------------------------------------------------------

  CREATE TABLE    CLASSI  
   (	 PATHNAME  VARCHAR2(200 BYTE) NOT NULL, 
	 NOMECLASSE  VARCHAR2(200 BYTE) NOT NULL, 
	 METODO  VARCHAR2(200 BYTE) NOT NULL, 
	 IDPACKAGE  NUMBER NOT NULL, 
	 IDCLASSE  NUMBER NOT NULL PRIMARY KEY,
	CONSTRAINT  CLASSI_FK1  FOREIGN KEY ( IDPACKAGE ) REFERENCES    PACKAGE  ( IDPACKAGE )
   );
--------------------------------------------------------
--     Table SOTTOMETODO
--------------------------------------------------------

  CREATE TABLE    SOTTOMETODO  
   (	 IDCLASSE  NUMBER , 
	 SOTTOMETODO  VARCHAR2(200 BYTE),
	CONSTRAINT  SOTTOMETODO_FK1  FOREIGN KEY ( IDCLASSE ) REFERENCES    CLASSI  ( IDCLASSE )
   );
--------------------------------------------------------
--     Table ACCEDE
--------------------------------------------------------

  CREATE TABLE    ACCEDE  
   (	 IDUTENTE  NUMBER, 
	 DATAAPERTURA  DATE, 
	 DATACHIUSURA  DATE, 
	 IDCLASSE  NUMBER,
	CONSTRAINT  IDCLASSEACCEDE_FK  FOREIGN KEY ( IDCLASSE )  REFERENCES    CLASSI  ( IDCLASSE ),
	CONSTRAINT  IDUTENTEACCEDE_FK  FOREIGN KEY ( IDUTENTE ) REFERENCES    UTENTE  ( IDUTENTE )
   );
--------------------------------------------------------
--     Table RELEASE
--------------------------------------------------------

  CREATE TABLE    RELEASE  
   (	 STATUS  VARCHAR2(200 BYTE), 
	 NOMEPROGETTO  VARCHAR2(200 BYTE) NOT NULL , 
	 IDRELEASE  NUMBER NOT NULL PRIMARY KEY, 
	 VERSIONE  NUMBER NOT NULL,
	CONSTRAINT  STATUS_ENUM  CHECK (STATUS IN ('ELIMINATO','AGGIORNATO','IMMUTATO')),
	CONSTRAINT  NOMEPROGETTORELEASE_FK  FOREIGN KEY ( NOMEPROGETTO ) REFERENCES    PROGETTO  ( NOMEPROGETTO )
   
   );
--------------------------------------------------------
--     Table TESTRELEASE
--------------------------------------------------------

  CREATE TABLE    TESTRELEASE  
   (	 IDTEST  NUMBER NOT NULL PRIMARY KEY, 
	 DATATEST  DATE NOT NULL, 
	 DESCRIZIONE  VARCHAR2(2000 BYTE) NOT NULL, 
	 RISULTATO  VARCHAR2(20 BYTE) NOT NULL, 
	 PATHNAME  VARCHAR2(200 BYTE) NOT NULL
   );
--------------------------------------------------------
--     Table TESTANO
--------------------------------------------------------

  CREATE TABLE    TESTANO  
   (	 IDRELEASE  NUMBER NOT NULL, 
	 IDTEST  NUMBER NOT NULL,
	CONSTRAINT  IDRELEASETESTANO_FK  FOREIGN KEY ( IDRELEASE ) REFERENCES    RELEASE  ( IDRELEASE ),
	CONSTRAINT  IDTESTTESTANO_FK  FOREIGN KEY ( IDTEST )  REFERENCES    TESTRELEASE  ( IDTEST )
   );
--------------------------------------------------------
--     Table AVVIA
--------------------------------------------------------

  CREATE TABLE    AVVIA  
   (	 IDUTENTE  NUMBER NOT NULL, 
	 IDTEST  NUMBER NOT NULL,
	CONSTRAINT  IDTESTAVVIA_FK  FOREIGN KEY ( IDTEST ) REFERENCES    TESTRELEASE  ( IDTEST ),
	CONSTRAINT  IDUTENTEAVVIA_FK  FOREIGN KEY ( IDUTENTE ) REFERENCES    UTENTE  ( IDUTENTE ) 
   );
-------------------------------------------------------
--     View VIEWPROGETTO
--------------------------------------------------------

  CREATE OR REPLACE  VIEW    VIEWPROGETTO  ( NOMEPROGETTO ,  PATHNAME ,  DATAAPERTURA ,  DATACHIUSURA ) AS 
  SELECT  NOMEPROGETTO , PATHNAME , DATAAPERTURA , DATACHIUSURA 
FROM PROGETTO  P;

--------------------------------------------------------
--     Trigger INSERIMENTOINVIES
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER    INSERIMENTOINVIES  
INSTEAD of  INSERT OR UPDATE ON VIEWPROGETTO
FOR EACH ROW
DECLARE
NOME progetto.nomeprogetto%TYPE;
date1 progetto.datachiusura%TYPE;
BEGIN
IF(inserting) THEN
INSERT INTO progetto(nomeprogetto,pathname,dataapertura)
VALUES(:new.nomeprogetto,:new.pathname,:new.dataapertura);
INSERT INTO release(STATUS,NOMEPROGETTO,IDRELEASE,VERSIONE)
VALUES(NULL,:new.nomeprogetto,null,1);
END IF;
IF(UPDATING) THEN

update progetto
set progetto.datachiusura=:new.datachiusura
where progetto.nomeprogetto=:old.nomeprogetto;
SELECT nomeprogetto,datachiusura INTO nome,date1
FROM  VIEWPROGETTO 
WHERE   :old.datachiusura is NUll and nomeprogetto=:old.nomeprogetto;
UPDATE ACCEDE
SET accede.datachiusura=date1
WHERE accede.datachiusura is null and idclasse in (Select cl.idclasse from partecipa join progetto p on partecipa.nomeprogetto=p.nomeprogetto
 join package pack on p.nomeprogetto=pack.nomeprogetto join classi cl on cl.idpackage=pack.idpackage 
where partecipa.nomeprogetto=nome);
END IF;
END;

/
ALTER TRIGGER    INSERIMENTOINVIES  ENABLE;
--------------------------------------------------------
--     Trigger IDCLASSE
--------------------------------------------------------

create or replace TRIGGER IDCLASSE
	BEFORE INSERT ON CLASSI
	for each row
	BEGIN
	:new.IDCLASSE:= NVL(:new.IDCLASSE,IDCLASSEINC.nextval);
	END;
/
--------------------------------------------------------
--     Trigger idPackage
--------------------------------------------------------
create or replace TRIGGER idPackage
	BEFORE INSERT ON PACKAGE
	for each row
	BEGIN
	:new.IDPACKAGE:= NVL(:new.IDPACKAGE,IDPACKAGEINC.nextval);
	END;
/
--------------------------------------------------------
--     Trigger IDRELEASE
--------------------------------------------------------
create or replace TRIGGER IDRELEASE
	BEFORE INSERT ON RELEASE
	for each row
	BEGIN
	:new.IDRELEASE:= NVL(:new.IDRELEASE,IDRELEASEINC.nextval);
	END;
/

--------------------------------------------------------
--     Trigger IDutente
--------------------------------------------------------
create or replace TRIGGER   inserimentoUtente
BEFORE INSERT ON utente
for each row
begin
:new.IDUTENTE:= NVL(:new.IDUTENTE,IDUTENTEINC.nextval);
END;
/
--------------------------------------------------------
--     Trigger IDTEST
--------------------------------------------------------
create or replace TRIGGER IDTEST
	BEFORE INSERT ON TESTRELEASE
	for each row
	BEGIN
	:new.IDTEST:= NVL(:new.IDTEST,IDTESTINC.nextval);
	END;
/