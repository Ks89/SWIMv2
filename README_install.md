# Installazione e configurazione

## Prerequisiti
Scaricare i seguenti software:
- MySQL Community Server (la versione più recente) http://www.mysql.com/downloads/mysql/
- MySQL Workbench (versione 5.2.44 o superiore) http://www.mysql.com/downloads/workbench/
- MySQL Connector/J (versione 5.1.22) http://www.mysql.com/downloads/connector/j/
- JBoss Application Server (5.1.0 GA) http://sourceforge.net/projects/jboss/files/JBoss/JBoss-5.1.0.GA
- Software per estrarre file .zip

## Procedura di installazione e configurazione
1. Installare MySQL Community Server e MySQL Workbench
2. Estrarre JBoss in un cartella a piacere (chiamata in modo generico “cartella_JBoss”).
3. Estrarre il file “mysql-connector-java-versione.zip”.
4. Del contenuto della cartella appena estratta, conservare solamente il file “mysql-connector-java-
versione-bin.jar” per la fase successiva.
5. Spostare nella cartella d’installazione di JBoss (“cartella_JBoss”/server/default/deploy) i file: SWIMdb-ds.xml, SWIMv2_EAR.ear. Inoltre, spostare il file “mysql-connector-java-versione-bin.jar” in “cartella_JBoss”/server/default/lib
6. Aprire MySQL Workbench
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/install/a-mysql-workbench.png)

7. Cliccare su “New Server Instance” in basso a destra in Fig. 1.1. Dopodiché, spuntare “localhost” e cliccare su “Continue” (Fig. 1.2). 
8. Utilizzare i parametri standard come in Fig. 1.3, personalizzare “Hostname:”, “Port:”, “Username:” e scegliere una Password. Questi dati saranno utilizzati per la procedura al punto 13.
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/install/b-creazione-nuova-istanzia.png)

9. Inserire la password dell’utente (Fig. 1.4) che sarà utilizzata in seguito per configurare il file “SWIMdb-
ds.xml”. FOTO
10. Seguire la procedura guidata e cliccare su “Continue”. FOTO
11. Mantenere i parametri predefiniti che dipendono dal sistema operativo (Fig. 1.6) e seguire la procedura guidata fino alla fine, mantenendo i parametri predefiniti. A questo punto apparirà la finestra principale con la nuova istanza del server e la connessione al database (Fig. 1.7). FOTO
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/install/c-personalizzazione-parametri.png)

12. Eseguire tramite la “versione a linea di comando” o tramite “MySQL Workbench” (consigliato) il file “Crea_Db-tabelle-dati.sql” per creare il database, le tabelle ed inserire alcuni dati predefiniti.
Ecco la procedura in MySQL Workbench:
* Fare doppio clic su “localhost” nella sezione “SQL Development” a sinistra (Fig. 1.7)
* Dal menu “File” -> “Open SQL Script...” aprire il file “Crea_Db-tabelle-dati.sql”
* Scegliere “Query” -> “Execute (All or Selection)”
Eseguendo il file .sql saranno inseriti nel database i seguenti dati:
* Amministratore:
  * Email: “admin@swim.it”
  * Password: “a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8” (cioè lo SHA-256 della password “pippo”)
* Abilita:
  * Nome: “Architetto”, nessuna Descrizione o Nome: “Avvocato”, nessuna Descrizione o Nome: “Cuoco”, nessuna Descrizione
  * Nome: “Falegname”, nessuna Descrizione o Nome: “Idraulico”, nessuna Descrizione
  * Nome: “Programamtore Android”, Descrizione: “Programmatore per dispositivi android di qualunque versione”
  * Nome: “Programmatore Java SE”, Descrizione: “Programmatore in Java Standard Edition”
13. Modificare il file “SWIMdb-ds.xml” spostato in “cartella_JBoss” nel punto 5 e personalizzando le righe
seguenti:
* <connection-url>jdbc:mysql://NOME_HOST:PORTA/NOME_DATABASE</connection-url> <user-name>NOME_UTENTE</user-name>
* <password>PASSWORD</password>
* NOME_HOST: indirizzo del server.
* PORTA: porta per connettersi al server (es: 3306)
* NOME_DATABASE: è il nome del database, scegliere “SWIMdb”.
* NOME_UTENTE: nome dell’utente del database (scelto nel punto 8)
* PASSWORD: password dell’utente del database (scelto nel punto 8)
14. Per garantire il corretto funzionamento dell’upload delle foto del profilo è necessario modificare un parametro in MySQL Workbench tramite la seguente procedura:
* Dalla sezione “Server Administration” di MySQL Workbench, cliccare due volte sull’istanza del server creata nei punti precedenti.
* Dalla barra laterale, sotto la voce “CONFIGURATION”, scegliere “Option File”.
* Nella casella “Locate option:” scrivere “max_allowed_packet” (senza virgolette) e cliccare
sul pulsante “Find”.
* Spuntare la casella sotto la sezione “Data/memory size” che si chiama
“max_allowed_packet” e inserire un valore superiore ai 6MB (si consiglia 10000000 o
superiore) (vedi Fig. 1.8).
* Cliccare su “Apply...” in basso a destra e per finire su “Apply” nella finestra di conferma
successiva. FOTO
15. Avviare il server JBoss
16. Dal browser accedere alla pagina di test per verificare il corretto funzionamento dell’applicazione.
http://localhost:8080/SWIMv2_WEB/testInstallazione?emailAdmin=admin@swim.it
Apparirà uno dei seguenti messaggi:
* “Test connessione al database: esito positivo”: nel caso sia funzionante.
* “Test connessione al database: esito negativo”: in caso di problemi di connessione al
database.
Attenzione: il collegamento riportato sopra deve essere personalizzato in base al server su cui è eseguito.

## Importazione del codice sorgente
In caso vi fossero problemi ad utilizzare il software fornito tramite il solo file .ear è possibile utilizzare Eclipse Indigo per eseguire il prodotto.
Attenzione: prima di svolgere questa fase completare tutti punti del capitolo precedente dall’1 al 14 senza considerare il file .ear.
Presupponendo che Eclipse sia installato e che sia configurato il server JBoss 5.1 installato nei punti precedenti seguire la seguente procedura:
1. Assicurarsi che Eclipse Indigo sia chiuso
2. Eseguire il file “lombok.jar” allegato al codice sorgente ed installare il plugin in Eclipse seguendo la
procedura guidata.
3. Estrarre il file .zip contenente il codice sorgente.
4. Rimuovere eventuali progetti dal “Workspace” di Eclipse per evitare conflitti di qualunque tipo (solo
a scopo precauzionale)
5. Avviare Eclipse Indigo
6. Utilizzare la funzione “Import”->”Import”->”General”->”Existing Projects into workspace“
7. Scegliere la cartella estratta e seguire la procedura guidata per importare in Eclipse i tre progetti:
  * SWIMv2_EAR
  * SWIMv2_EJB
  * SWIMv2_WEB
Assicurarsi di selezionare la voce: “Copy projects into workspace”.
Confermare ed attendere alcuni secondi fino al termine della procedura.
I tre progetti sono già pre-configurati, ma si consiglia di seguire la procedura per verificare che tutte le opzioni siano correte.
8. Premere il tasto destro del mouse su SWIMv2_EAR, poi su “Properties”->”Server” e scegliere il server “JBoss 5” creato in precedenza.
9. Premere il tasto destro del mouse su SWIMv2_EJB e scegliere “Properties”->”Java build Path” e importare le librerie corrette nel caso appaiano come “unbound”.
Per evitare possibili errori si consiglia di rimuovere la libreria JUnit4 e dalla scheda Properties”- >”Java build Path”->“Libraries” cliccare su “Add Library”->”JUnit”->”JUnit4” e confermare per reinserirla.
10. Premere il tasto destro del mouse su SWIMv2_WEB e scegliere “Properties”->”Java build Path” e importare le librerie corrette nel caso appaiano come “unbound”.
11. Attenzione, i progetti WEB e EJB in “Properties”->”Project references” NON devono avere caselle spuntate, invece in EAR si, entrambe le voci, una associata al progetto WEB e l’altra all’EJB.
12. Questa configurazione permetterà di eseguire il progetto scegliendo “Run As”->”Run On Server” sul progetto SWIMv2_EAR. Non eseguire mai l’applicazione tramite i singoli progetti EJB e WEB, essi non sono configurati per essere “pubblicati” sul server. Il vantaggio di questa scelta è la creazione di un unico file contenente la parte EJB, quella WEB ed infine tutte le librerie senza doverle gestire manualmente.


## Collegarsi a SWIMv2
Una volta che il prodotto è stato installato e configurato con successo sarà disponibile a tutti accedendo tramite un browser web all’indirizzo http://localhost:8080/SWIMv2_WEB/home. Capirai di avere accesso al software SWIMv2 se sul tuo browser apparirà la seguente pagina

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/1-homepage.png)
