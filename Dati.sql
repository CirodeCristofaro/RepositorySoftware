Insert into  UTENTE (IDUTENTE,NOME,COGNOME) values ('1','ciro','de cristofaro');
Insert into  UTENTE (IDUTENTE,NOME,COGNOME) values ('2','paolo','cammardella');
Insert into  UTENTE (IDUTENTE,NOME,COGNOME) values ('3','salvatore','franzese');
Insert into  UTENTE (IDUTENTE,NOME,COGNOME) values ('4','vincenzo','bruno');
Insert into  UTENTE (IDUTENTE,NOME,COGNOME) values ('5','sara','cascone');
Insert into  UTENTE (IDUTENTE,NOME,COGNOME) values ('6','teresa','cantillo');
Insert into  UTENTE (IDUTENTE,NOME,COGNOME) values ('7','rosa','dangelo');
Insert into  UTENTE (IDUTENTE,NOME,COGNOME) values ('8','antonio ','verone');
Insert into  UTENTE (IDUTENTE,NOME,COGNOME) values ('9','salvatore','aranzulla');

commit;

Insert into  PROGETTO (NOMEPROGETTO,PATHNAME,DATAAPERTURA,DATACHIUSURA) values ('xbox y','C:\xboxY',to_date('04-FEB-20','DD-MON-RR'),null);
Insert into  PROGETTO (NOMEPROGETTO,PATHNAME,DATAAPERTURA,DATACHIUSURA) values ('tesla','C:\tesla5',to_date('23-MAR-19','DD-MON-RR'),null);
Insert into  PROGETTO (NOMEPROGETTO,PATHNAME,DATAAPERTURA,DATACHIUSURA) values ('playstation 5','C:\ps5',to_date('22-MAG-19','DD-MON-RR'),null);
Insert into  PROGETTO (NOMEPROGETTO,PATHNAME,DATAAPERTURA,DATACHIUSURA) values ('the game','C:\TheGame',to_date('31-GEN-19','DD-MON-RR'),null);
Insert into  PROGETTO (NOMEPROGETTO,PATHNAME,DATAAPERTURA,DATACHIUSURA) values ('udemy','C:\UdemySite',to_date('18-MAR-20','DD-MON-RR'),to_date('18-GIU-20','DD-MON-RR'));
commit;

Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('3','xbox y');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('4','xbox y');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('1','tesla');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('2','tesla');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('8','playstation 5');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('6','playstation 5');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('7','the game');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('5','the game');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('8','xbox y');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('9','playstation 5');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('9','udemy');
Insert into  PARTECIPA (IDUTENTE,NOMEPROGETTO) values ('3','udemy');
commit;

Insert into  PACKAGE (NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE) values ('org.xbox','C:\xboxY\org.xbox','xbox y','1');
Insert into  PACKAGE (NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE) values ('com.tesla','C:\tesla5\com.tesla','tesla','2');
Insert into  PACKAGE (NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE) values ('it.playstation','C:\ps5\it.playstation','playstation 5','3');
Insert into  PACKAGE (NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE) values ('io.thegame','C:\TheGame\io.thegame','the game','4');
Insert into  PACKAGE (NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE) values ('com.tesla.TheftProtection','C:\tesla5\com.tesla\com.tesla.TheftProtection','tesla','5');
Insert into  PACKAGE (NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE) values ('org.xbox.kinect','C:\xboxY\org.xbox\org.xbox.kinect','xbox y','6');
Insert into  PACKAGE (NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE) values ('io.thegame.pg','C:\TheGame\io.thegame\io.thegame.pg','the game','7');
Insert into  PACKAGE (NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE) values ('it.udemy','C:\UdemySite\it.udemy','udemy','8');
Insert into  PACKAGE (NOMEPACKAGE,PATHNAME,NOMEPROGETTO,IDPACKAGE) values ('it.udemy.Classroom','C:\UdemySite\it.udemy\it.udemy.Classroom','udemy','9');
commit;

Insert into  SOTTOPACKAGE (IDPACKAGE,SOTTOPACKAGE) values ('2','5');
Insert into  SOTTOPACKAGE (IDPACKAGE,SOTTOPACKAGE) values ('1','6');
Insert into  SOTTOPACKAGE (IDPACKAGE,SOTTOPACKAGE) values ('4','7');
Insert into  SOTTOPACKAGE (IDPACKAGE,SOTTOPACKAGE) values ('8','9');
commit;

Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\xboxY\org.xbox','main.java','public void start()','1','1');
Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\tesla5\com.tesla','main.java','public void start()','2','2');
Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\ps5\it.playstation','main.java','public void main()','3','3');
Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\TheGame\io.thegame','main.java','public void main()','4','4');
Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\tesla5\com.tesla\com.tesla.TheftProtection','Suspicious iteration.java','public void getSuspiciousIteration()','5','5');
Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\xboxY\org.xbox\org.xbox.kinect','Kinect.java','private Resulset powerOn()','6','6');
Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\ps5\it.playstation','LoadGame.java','public void load()','3','7');
Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\TheGame\io.thegame','CreatePg.java','private setCharacter(String info)','4','8');
Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\UdemySite\it.udemy','CreateCourse.java','private setName(String name)','8','9');
Insert into  CLASSI (PATHNAME,NOMECLASSE,METODO,IDPACKAGE,IDCLASSE) values ('C:\UdemySite\it.udemy\it.udemy.Classroom','CreateClassroom.java','private void Classroom()','9','10');
commit;

Insert into  SOTTOMETODO (IDCLASSE,SOTTOMETODO) values ('5','private setAlarm()');
Insert into  SOTTOMETODO (IDCLASSE,SOTTOMETODO) values ('10','private setPlace(int IdStudent)');
commit;

Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('3',to_date('04-FEB-20','DD-MON-RR'),null,'1');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('4',to_date('04-FEB-20','DD-MON-RR'),null,'1');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('1',to_date('23-MAR-19','DD-MON-RR'),null,'2');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('2',to_date('23-MAR-19','DD-MON-RR'),null,'2');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('8',to_date('22-MAG-19','DD-MON-RR'),to_date('18-GIU-20','DD-MON-RR'),'3');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('6',to_date('22-MAG-19','DD-MON-RR'),to_date('18-GIU-20','DD-MON-RR'),'3');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('7',to_date('31-GEN-19','DD-MON-RR'),null,'4');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('5',to_date('31-GEN-19','DD-MON-RR'),null,'4');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('1',to_date('23-MAR-19','DD-MON-RR'),null,'5');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('2',to_date('23-MAR-19','DD-MON-RR'),null,'5');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('3',to_date('04-FEB-20','DD-MON-RR'),null,'6');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('4',to_date('04-FEB-20','DD-MON-RR'),null,'6');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('8',to_date('22-MAG-19','DD-MON-RR'),to_date('18-GIU-20','DD-MON-RR'),'7');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('6',to_date('22-MAG-19','DD-MON-RR'),to_date('18-GIU-20','DD-MON-RR'),'7');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('7',to_date('31-GEN-19','DD-MON-RR'),null,'8');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('5',to_date('31-GEN-19','DD-MON-RR'),null,'8');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('8',to_date('04-FEB-20','DD-MON-RR'),null,'6');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('9',to_date('22-MAG-19','DD-MON-RR'),to_date('18-GIU-20','DD-MON-RR'),'3');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('9',to_date('18-MAR-20','DD-MON-RR'),to_date('26-MAR-20','DD-MON-RR'),'9');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('3',to_date('18-MAR-20','DD-MON-RR'),to_date('26-MAR-20','DD-MON-RR'),'9');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('9',to_date('18-MAR-20','DD-MON-RR'),to_date('18-GIU-20','DD-MON-RR'),'10');
Insert into  ACCEDE (IDUTENTE,DATAAPERTURA,DATACHIUSURA,IDCLASSE) values ('3',to_date('18-MAR-20','DD-MON-RR'),to_date('18-GIU-20','DD-MON-RR'),'10');
commit;

Insert into  RELEASE (STATUS,NOMEPROGETTO,IDRELEASE,VERSIONE) values ('IMMUTATO','xbox y','1','1');
Insert into  RELEASE (STATUS,NOMEPROGETTO,IDRELEASE,VERSIONE) values (null,'tesla','2','1');
Insert into  RELEASE (STATUS,NOMEPROGETTO,IDRELEASE,VERSIONE) values (null,'playstation 5','3','1');
Insert into  RELEASE (STATUS,NOMEPROGETTO,IDRELEASE,VERSIONE) values (null,'the game','4','1');
Insert into  RELEASE (STATUS,NOMEPROGETTO,IDRELEASE,VERSIONE) values ('IMMUTATO','tesla','5','2');
Insert into  RELEASE (STATUS,NOMEPROGETTO,IDRELEASE,VERSIONE) values (null,'udemy','6','1');
Insert into  RELEASE (STATUS,NOMEPROGETTO,IDRELEASE,VERSIONE) values ('IMMUTATO','udemy','7','2');
commit;

Insert into  TESTRELEASE (IDTEST,DATATEST,DESCRIZIONE,RISULTATO,PATHNAME) values ('1',to_date('18-MAR-20','DD-MON-RR'),'Test avviamento prototipo motore','SUPERATO','C:\test avviamento motore.exe');
Insert into  TESTRELEASE (IDTEST,DATATEST,DESCRIZIONE,RISULTATO,PATHNAME) values ('2',to_date('18-MAR-20','DD-MON-RR'),'Kinect rilevamento movimento','FALLITO','C:\kinect.exe');
Insert into  TESTRELEASE (IDTEST,DATATEST,DESCRIZIONE,RISULTATO,PATHNAME) values ('3',to_date('18-MAR-20','DD-MON-RR'),'Test creazione dell corso','SUPERATO','C:\test.exe');
Insert into  TESTRELEASE (IDTEST,DATATEST,DESCRIZIONE,RISULTATO,PATHNAME) values ('4',to_date('18-MAR-20','DD-MON-RR'),'Test avviamento corso e associazione classe ','FALLITO','C:\testAula.exe');
commit;

Insert into  TESTANO (IDRELEASE,IDTEST) values ('2','1');
Insert into  TESTANO (IDRELEASE,IDTEST) values ('1','2');
Insert into  TESTANO (IDRELEASE,IDTEST) values ('6','3');
Insert into  TESTANO (IDRELEASE,IDTEST) values ('7','4');
commit;

Insert into  AVVIA (IDUTENTE,IDTEST) values ('1','1');
Insert into  AVVIA (IDUTENTE,IDTEST) values ('3','2');
Insert into  AVVIA (IDUTENTE,IDTEST) values ('9','3');
Insert into  AVVIA (IDUTENTE,IDTEST) values ('9','4');
commit;