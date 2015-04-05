# Installazione e configurazione

## Prerequisiti
Scaricare i seguenti software:

- [MySQL Community Server]( http://www.mysql.com/downloads/mysql/) (la versione più recente)
- [MySQL Workbench](http://www.mysql.com/downloads/workbench/) (versione 5.2.44 o superiore) 
- [MySQL Connector/J](http://www.mysql.com/downloads/connector/j/) (versione 5.1.22) 
- [JBoss Application Server](http://sourceforge.net/projects/jboss/files/JBoss/JBoss-5.1.0.GA) (5.1.0 GA) per Java 6 
- Software per estrarre file .zip

## Procedura di installazione e configurazione
1. Installare Java, Eclipse, MySQL Community Server e MySQL Workbench
2. Estrarre JBoss in un cartella a piacere (chiamata in modo generico “cartella_JBoss”).
3. Estrarre il file “mysql-connector-java-versione.zip”.
4. Del contenuto della cartella appena estratta, conservare solamente il file **“mysql-connector-java-
versione-bin.jar”** per la fase successiva.
5. Spostare nella cartella d’installazione di JBoss (“cartella_JBoss”**/server/default/deploy**) i file: **SWIMdb-ds.xml**, **SWIMv2_EAR.ear**. Inoltre, spostare il file **“mysql-connector-java-versione-bin.jar”** in “cartella_JBoss”**/server/default/lib**
6. Aprire MySQL Workbench e creare una "New Server Instance"
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/install/a-mysql-workbench_.png)

7. Utilizzare i parametri standard, personalizzare “Hostname:”, “Port:”, “Username:” e scegliere una Password. Questi dati saranno utilizzati per la procedura nei punti successivi.
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/install/b-creazione-nuova-istanzia_.png)

8. Inserire la password dell’utente che sarà utilizzata in seguito per configurare il file **“SWIMdb-ds.xml”**.
9. Mantenere i parametri predefiniti che dipendono dal sistema operativo e seguire la procedura guidata fino alla fine. A questo punto apparirà la finestra principale con la nuova istanza del server e la connessione al database.
10. **Eseguire** tramite la “versione a linea di comando” o tramite “MySQL Workbench” (consigliato) **il file “Crea_Db-tabelle-dati.sql” per creare il database, le tabelle ed inserire alcuni dati predefiniti**. Nel caso di MySQL Workbench, seguire questa procedura:
  * Fare doppio clic su “localhost” nella sezione “SQL Development”.
  * Dal menu “File” -> “Open SQL Script...” aprire il file “Crea_Db-tabelle-dati.sql”.
  * Scegliere “Query” -> “Execute (All or Selection)”.
  Eseguendo il file .sql saranno inseriti nel database i seguenti dati:
  * Amministratore:
    * Email: “admin@swim.it”.
    * Password: “a2242ead55c94c3deb7cf2340bfef9d5bcaca22dfe66e646745ee4371c633fc8” (cioè lo SHA-256 della password “pippo”).
  * Abilita:
    * Nome: “Architetto”, nessuna Descrizione o Nome: “Avvocato”, nessuna Descrizione o Nome: “Cuoco”, nessuna Descrizione.
    * Nome: “Falegname”, nessuna Descrizione o Nome: “Idraulico”, nessuna Descrizione.
    * Nome: “Programamtore Android”, Descrizione: “Programmatore per dispositivi android di qualunque versione”.
    * Nome: “Programmatore Java SE”, Descrizione: “Programmatore in Java Standard Edition”.
11. Modificare il file **“SWIMdb-ds.xml”** spostato in “cartella_JBoss” e personalizzando le righe seguenti:
  * <connection-url>jdbc:mysql://**NOME_HOST:PORTA/NOME_DATABASE**</connection-url> 
  * <user-name>**NOME_UTENTE**</user-name>
  * <password>**PASSWORD**</password>
    * NOME_HOST: indirizzo del server.
    * PORTA: porta per connettersi al server (es: 3306)
    * NOME_DATABASE: è il nome del database, scegliere “SWIMdb”.
    * **NOME_UTENTE**: nome dell’utente del database (scelto in precedenza).
    * **PASSWORD**: password dell’utente del database (scelto in precedenza).
12. **Per garantire il corretto funzionamento dell’upload delle foto del profilo è necessario modificare un parametro in MySQL Workbench tramite la seguente procedura**:
  * Dalla sezione “Server Administration” di MySQL Workbench, cliccare due volte sull’istanza del server creata nei punti precedenti.
  * Scegliere "Server" -> "Options File".
  * Nella casella “Locate option:” scrivere “max_allowed_packet” (senza virgolette) e cliccare sul pulsante “Find”.
  * Spuntare la casella sotto la sezione “Data/memory size” che si chiama "max_allowed_packet" e inserire un valore superiore ai 6MB (si consiglia 10000000 o superiore).
  * Cliccare su “Apply...” in basso a destra e per finire su “Apply” nella finestra di conferma successiva.

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/install/c-personalizzazione-parametri.png)
 
13. Avviare il server JBoss.
14. Dal browser accedere a [QUESTA PAGINA](http://localhost:8080/SWIMv2_WEB/testInstallazione?emailAdmin=admin@swim.it) per verificare il corretto funzionamento dell’applicazione. (Attenzione, il collegamento riportato sopra deve essere personalizzato in base al server su cui è eseguito). Apparirà uno dei seguenti messaggi:
  * “Test connessione al database: esito positivo”: nel caso sia funzionante.
  * “Test connessione al database: esito negativo”: in caso di problemi di connessione al database.


## Importazione del codice sorgente

#### Prerequisiti
- [Eclipse EE](https://eclipse.org/downloads/) (la versione più recente) 
- [Java 6](http://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase6-419409.html) (ed assicurarsi che sia la versione predefinita nel sistema operativo) 

In caso vi fossero problemi ad utilizzare il software fornito tramite il solo file .ear è possibile utilizzare Eclipse Luna EE per eseguire il prodotto. Attenzione: prima di svolgere questa fase completare tutti punti descritti poco fa senza considerare il file .ear.
Presupponendo che Eclipse sia installato e che sia configurato il server JBoss 5.1 installato nei punti precedenti seguire la seguente procedura:
<br>
1. Assicurarsi che Eclipse sia chiuso.
<br>
2. Eseguire il file “lombok.jar” allegato al codice sorgente ed installare il plugin in Eclipse seguendo la procedura guidata.
<br>
3. Estrarre il file .zip contenente il codice sorgente.
<br>
4. Rimuovere eventuali progetti dal “Workspace” di Eclipse per evitare conflitti di qualunque tipo (solo a scopo precauzionale).
<br>
5. Avviare Eclipse.
<br>
6. Utilizzare la funzione “Import”->”Import”->”General”->”Existing Projects into workspace“.
<br>
7. Scegliere la cartella estratta e seguire la procedura guidata per importare in Eclipse i tre progetti direttamente dalla cartella git usata per scaricare il sorgente da questa repository (con “Copy projects into workspace”):
  * SWIMv2_EAR
  * SWIMv2_EJB
  * SWIMv2_WEB
<br>
8. Premere il tasto destro del mouse su SWIMv2_EAR, poi su “Properties”->”Server” e scegliere il server “JBoss 5” creato in precedenza.
<br>
9. Premere il tasto destro del mouse su SWIMv2_EJB e scegliere “Properties”->”Java build Path” e importare le librerie corrette nel caso appaiano come “unbound”. **Per evitare possibili errori si consiglia di rimuovere la libreria JUnit4 e dalla scheda Properties”- >”Java build Path”->“Libraries” cliccare su “Add Library”->”JUnit”->”JUnit4” e confermare per reinserirla**.
<br>
10. Premere il tasto destro del mouse su SWIMv2_WEB e scegliere “Properties”->”Java build Path” e importare le librerie corrette nel caso appaiano come “unbound”.
<br>
11. Attenzione, **i progetti WEB e EJB** in “Properties”->”Project references” **NON devono avere caselle spuntate, invece in EAR si, entrambe le voci**, una associata al progetto WEB e l’altra all’EJB.
<br>
12. Questa configurazione permetterà di **eseguire il progetto scegliendo “Run As”->”Run On Server” sul progetto SWIMv2_EAR. Non eseguire mai l’applicazione tramite i singoli progetti EJB e WEB**, essi non sono configurati per essere “pubblicati” sul server. Il vantaggio di questa scelta è la creazione di un unico file contenente la parte EJB, quella WEB ed infine tutte le librerie senza doverle gestire manualmente.


## Collegamento a SWIMv2
Una volta che il prodotto è stato installato e configurato con successo, sarà disponibile a tutti accedendo tramite un browser all’indirizzo http://localhost:8080/SWIMv2_WEB/home. Si capisce di avere accesso al software SWIMv2 se nel browser apparirà la seguente pagina

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/1-homepage.png)
