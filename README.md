# SWIMv2: Small World Hypothesis Machine v2

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/0-header.png)

Il progetto consiste in un social network (chiamato SWIMv2) che permette di cercare e/o offrire aiuto per svolgere specifici lavori.

## News
- *04/05/2015* - **SWIM v2** 1.0 Public release

## Informations
Scaricare i seguenti software:
- MySQL Community Server (la versione più recente) http://www.mysql.com/downloads/mysql/
- MySQL Workbench (versione 5.2.44 o superiore) http://www.mysql.com/downloads/workbench/
- MySQL Connector/J (versione 5.1.22) http://www.mysql.com/downloads/connector/j/
- JBoss Application Server (5.1.0 GA) http://sourceforge.net/projects/jboss/files/JBoss/JBoss-5.1.0.GA
- Software per estrarre file .zip

### Installazione e configurazione
Nota: Chiudere/disattivare eventuali Firewall installati perché potrebbero interferire con la procedura di configurazione di MySQL Community Server.

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
<connection-url>jdbc:mysql://NOME_HOST:PORTA/NOME_DATABASE</connection-url> <user-name>NOME_UTENTE</user-name>
<password>PASSWORD</password>
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

### Importazione del codice sorgente
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


### Collegarsi a SWIMv2
Una volta che il prodotto è stato installato e configurato con successo sarà disponibile a tutti accedendo tramite un browser web all’indirizzo http://localhost:8080/SWIMv2_WEB/home. Capirai di avere accesso al software SWIMv2 se sul tuo browser apparirà la seguente pagina

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/1-homepage.png)



## Features
Le funzioni principali del prodotto, tratte dal documento ricevuto dal committente, sono le seguenti:
###Utenti
* Devono
  * Scegliere almeno un’abilità da inserire nel proprio insieme personale, durante la registrazione.
* Possono
  * Cercare altri utenti.
  * Inviare/rispondere a richieste di amicizia.
  * Ricevere suggerimenti di amicizia dal sistema. Quando un utente stringe amicizia con un altro (senza aver ricevuto precedenti suggerimenti), entrambi visualizzano una lista di amici consigliati.
  * Cercare aiuto tra gli amici o nell’intera rete di utenti.
  * Accettare/rifiutare richieste di aiuto da parte di altri utenti.
  * Fornire un feedback dopo aver ricevuto aiuto.
  * Modificare il proprio profilo.
  * Proporre all’amministratore l’aggiunta di nuovi elementi all’insieme generale delle abilità. In caso di approvazione, le modifiche saranno disponibili a tutti gli utenti del social network (per maggiori informazioni fare riferimento ai capitoli successivi).

###Visitatori
* Possono utilizzare la funzione di ricerca per i visitatori, per trovare gli utenti in base alle abilità richieste, con la limitazione di non potere accedere alle informazioni personali.
* Possono registrarsi al sistema.

###Amministratore
* Deve definire l’insieme delle abilità degli utenti al momento della registrazione.

Può confermare/rifiutare le proposte di aggiunta all’insieme generale delle abilità



## Usage

### Utente non registrato
#### Ricerca per utenti non registrati
Questa funzionalità, permette la ricerca di aiuto, tra gli utenti registrati nel sito, selezionando particolari abilità. Essendo una funzionalità per utenti non registrati, può essere utilizzata, partendo dalla pagina iniziale illustrata in Fig. 1.1, cliccando sul pulsante Ricerca Aiuto.
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/2-pulsante-ricerca-aiuto-utenti-nn-registrati.png)

Si accederà quindi alla pagina di ricerca, dove, selezionando le abilità cercate, e premendo il pulsante Cerca si otterrà la lista di utenti presenti nel database del sito.
FOTO 
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/3-ricerca-aiuto-nn-reg.png)
Ovviamente, l’utente non registrato non potrà in alcun modo interagire con gli utenti ricercati, questo per invogliare l’utente a registrarsi.


#### Registrazione
Questa funzione, permette la registrazione al social network. Essendo una funzionalità per utenti non registrati, può essere utilizzata, partendo dalla pagina iniziale illustrata in Fig. 1.1, cliccando sul link Registrati, illustrato nella seguente figura
FOTO
Si verrà così indirizzati verso la pagina di registrazione, dove compilando tutti i campi obbligatori, e facoltativamente inserendo un foto, e premendo il tasto Invia, si potrà così effettuare la registrazione, come mostrato nelle seguenti figure.

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/4-form-registrazione.png)

Volendo inserire anche una foto, basterà premere il pulsante sfoglia, e selezionare il percorso dell’immagine che si vuole caricare.
FOTO
Una volta terminate queste operazioni, premendo il tasto Invia, si eseguirà la registrazione, e si sarà indirizzati verso la home dell’utente registrato, dove vengono visualizzate tutte le informazioni dell’utente collegato (profilo).

### Utente registrato
Di seguito è spiegato come utilizzare le funzioni dell’utente registrato. Per comodità, è subito mostrata la pagina “Home” dell’utente registrato, che è la pagina a cui si viene indirizzati successivamente alla registrazione, o al Login al social network.
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/5-registrato.png)

#### Login
Per accedere al sito come utente registrato, si dovrà ovviamente eseguire un login, direttamente dalla pagina in Fig. 1.1. Una volta inserite le credenziali, basterà premere il pulsante Login, per effettuare l’accesso al social network, e accedere così alla Home dell’utente registrato (figura 3.1)
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/6-login.png)

#### Ricerca Utenti
La funzione Ricerca Utenti è accessibile premendo il pulsante Ricerca Utenti, visibile in qualsiasi pagina del sito.
FOTO
Una volta cliccato su questo pulsante, si verrà indirizzati alla pagina di Ricerca Utente, dove compilando i campi di testo nome e/o cognome, e premendo il pulsante Cerca si potrà accedere ai risultati della ricerca.
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/7-ricerca-utenti.png)

FOTO
Una volta visualizzati i risultati della ricerca, cliccando sull’Email dell’utente in questione, si potrà accedere alla pagina del suo profilo.

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/8-dettaglio-altro-utente.png)


Da questa pagina, si può accedere a molte funzionalità del nostro social network. Infatti, oltre a poter leggere i dati dell’utente in questione, si può:
* Richiedere l’amicizia, premendo il pulsante Richiedi Amicizia (presente se l’utente non è ancora tra gli amici).
* Visualizzare i feedback dettagliati dell’utente, oltre al solo punteggio, premendo il link “Visualizza i feedback di questo utente, accedendo così a una pagina con tutti i feedback ricevuti e le relative collaborazioni.
* Richiedere una collaborazione, premendo il pulsante Richiedi collaborazione, dopo aver inserito obbligatoriamente un Nome nella relativa casella di testo, e opzionalmente una Descrizione.

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/9-richiesta-amicizia-inviata.png)

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/10-lista-feedback.png)

4 FOTO

#### Ricerca aiuto
La funzione Ricerca Aiuto è accessibile premendo il pulsante Ricerca Utenti, visibile in qualsiasi pagina del sito. Essa permette all’utente di selezionare un insieme di abilità da quelle selezionabili nella pagina, ed eseguire una ricerca di tutti quegli utenti con quelle determinate abilità. Si può anche selezionale la funzione di ricerca tra i soli utenti amici.

FOTO
FOTO
Una volta premuto il pulsante Cerca, si potranno consultare i risultati della ricerca, come visibile nella figura 3.15.

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/11-risultato-ricerca-aiuto.png)

Esattamente come nella ricerca utenti, sarà sufficiente cliccare sull’email dell’utente a cui si vuole richiedere aiuto, per andare nella pagina del profilo di quell’utente, (fig. 3.6), e da lì per richiedere la collaborazione basterà seguire i passi descritti nelle figure 3.11 e 3.12.

#### Visualizzare e gestire le collaborazioni richieste
La funzione di gestione delle collaborazioni richieste è accessibile premendo il pulsate visualizza collaborazioni, visualizzabile in qualunque parte del sito.
FOTO
Una volta premuto il tasto, saremo reindirizzati nella pagina in figura 3.17, dove avremo accesso a tutte le nostre collaborazioni, qui se esistono, vi saranno: una lista delle collaborazioni da terminare, delle collaborazioni terminate in cui possiamo inserire un feedback, e uno storico di tutte le altre collaborazioni.

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/12-collaborazioni.png)

Per terminare una collaborazione, basterà premere il pulsante termina presente nella colonna azione della tabella Collaborazioni in corso (fig. 3.18), per aggiungere un feedback basterà premere sul link nella colonna azione della tabella Collaborazioni senza feedback (fig. 3.19), oppure per guardare i dettagli di una qualsiasi collaborazione basterà cliccare sul nome della collaborazione di cui si vuole sapere i dettagli.
FOTO
FOTO

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/13-dettaglio-collaborazione.png)

#### Visualizzare le richieste di aiuto o amicizia di altri utenti

Per accedere a queste funzionalità, basterà premere il pulsante Visualizza Notifiche, visualizzabile in qualunque parte del social network (fig. 3.21).
Qui potremo confermare o rifiutare, richieste di aiuto e di amicizia che gli altri utenti ci hanno fatto.
Selezionando il Nome della collaborazione, o il Nome dell’utente che sta richiedendo l’amicizia, si arriverà in una pagina con una piccola descrizione della collaborazione o dell’utente, e premendo sul pulsante Conferma o Rifiuta (fig. 3.22), si risponderà appunto a tali richieste.

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/14-richieste-aiuto.png)

FOTO
FOTO
#### Visualizzare le risposte degli utenti alle tue richieste
In caso di conferma di un’amicizia, e di rifiuto o conferma di una collaborazione da parte di un utente, comparirà un collegamento nella tua homepage (fig. 3.23) dove si potranno visualizzare appunto queste notifiche, e in caso di amicizia diretta, si potranno visualizzare i suggerimenti di amicizia che il social network ti propone.
FOTO

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/15-risposte-utenti.png)

Le notifiche di risposta saranno visualizzate solamente una volta, e da qui potrai accedere alla funzionalità di suggerimento di amicizia. Come si può notare in figura 3.24 è presente una tabella chiamata Suggerimenti di amicizia, con in una colonna l’amico che hai appena acquisito (DIRETTAMENTE) e una con un utente che è amico di questo utente e non tuo, e al quale ovviamente non hai già richiesto una amicizia. Per richiedere l’amicizia a uno o più utenti presenti in questa tabella, basterà selezionare gli utenti a cui si vuole richiedere l’amicizia (attraverso la checkbox nella prima colonna della tabella) e premere il pulsante richiedi amicizia.
E’ possibile anche che un utente respinga la tua richiesta di aiuto, ma nella figura di esempio (3.24) non sono presenti richieste respinte, ed è segnalato dall’apposito messaggio all’inizio della pagina (con sfondo blu).
#### Proponi abilità
Nel caso in cui l’utente avesse una abilità che non è presente nell’insieme generale delle abilità, il sistema mette a disposizione una funzionalità che permette di inviare una richiesta all’amministratore.
Selezionando dal menu la voce “Proponi nuove abilità”, si presenterà all’utente una pagina con due aree d’inserimento testo: una per indicare il nome dell’abilità e l’altra dove specificare il motivo per il quale si vuole che venga aggiunta. Una volta premuto il pulsante invia, la richiesta sarà inoltrata all’amministratore che deciderà se confermare o rifiutare la vostra richiesta.

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/16-proposta-abilita.png)


#### Modifica profilo
Il sistema permette all’utente di modificare le proprie informazioni personali tramite la sezione “Modifica profilo” (vedi Fig. 3.4).

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/17-modifica-profilo.png)


Dalla pagina “Modifica profilo” l’utente può cambiare la sua foto del profilo, o aggiungere delle abilità, selezionandole dall’insieme generale delle abilità.
Le modifiche sono rese effettive appena premuto il pulsante “Invia”.
#### Visualizza feedback
Selezionando dal menù la voce “Visualizza feedback”, è possibile visionare l’elenco di tutti i feedback che sono stati rilasciati dagli utenti con cui si è collaborato. Come si può vedere dalla figura sottostante, per ogni feedback è possibile vedere: il nome della collaborazione a cui è riferito, il nome della persona con cui si è collaborato (e che ha rilasciato il feedback), il punteggio del feedback e il commento (opzionale).
![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/18-lista-feedback.png)



### Amministratore
Il team di sviluppo ha scelto una suddivisione netta delle funzionalità tra amministratore e utente. Infatti, l’amministratore, se vuole usufruire delle normali funzionalità del sistema, dovrebbe registrarsi a sua volta come utente.
Di seguito viene spiegato come utilizzare le funzionalità del sistema dedicate all’amministratore.
#### Login
Come specificato sopra, la parte dedicata all’amministratore è separata da quella dedicata agli utenti. Proprio per rispettare questa scelta, la pagina di login dell’amministratore la si può trovare tramite il link http://localhost:8080/SWIMv2_WEB/admin.
Una volta su questa pagina, basta inserire l’indirizzo email dell’amministratore (default: admin@swim.it) e la password (default: pippo).

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/19-login-amministratore.png)

Una volta effettuato il login, verrete reindirizzati nella home page. La pagina home si presenta piuttosto scarna, con un menù dal quale potrete accedere a tutte le funzionalità dell’amministratore.
FOTO

#### Aggiungi abilita
Selezionando dal menù la voce “Aggiungi abilita”, verrete reindirizzati nella seguente pagina (vedi figura sottostante).

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/20-inserisci-abilita.png)

Da questa pagina avrete la possibilità di aggiungere abilità all’insieme generale delle abilità. Molto semplicemente basta scrivere nell’apposito spazio il nome dell’abilità, e nello spazio sottostante una breve descrizione (massimo 300 caratteri) di quello che l’abilità rappresenta.
####Visualizza abilità proposte
L’altra voce del menu amministratore, vi permette di visionare tutte le abilità che gli utenti hanno proposto di aggiungere al all’insieme generale delle abilità.

![alt tag](http://www.stefanocappa.it/publicfiles/Github_repositories_images/Swimv2/21-proposte-abilita.png)

Tutte le abilità proposte dagli utenti saranno visualizzate in un elenco, con indicati: il nome dell’abilità e il nome dell’utente che l’ha proposta.
Per aggiungere una delle abilità proposte, l’amministratore deve selezionarne una dall’elenco cliccando sul nome (Esempio. L’amministratore seleziona “Docente di matematica”).
FOTO
Una volta selezionata un’abilità verrete reindirizzati in un’altra pagina, con i dettagli della proposta.
In questa pagina potrete visionare nel dettaglio: l’email dell’utente che ha proposto l’abilità, il nome di quest’ultima e il motivo per il quale si vuole che venga aggiunta.
Poco più in basso avrete a disposizione due aree di inserimento testo (Nome, Descrizione), e due pulsanti: “Conferma” (per confermare l’inserimento dell’abilità, nell’insieme generale delle abilità) e “Rifiuta” (per rifiutare la richiesta dell’utente).
Qui sotto potete vedere un esempio di cosa viene visualizzato selezionando l’abilità “Docente di matematica” (vedi Fig. 4.5).
FOTO



## Future extensions
- [ ] 
- [ ] 



## License

Copyright 2012-2015 Stefano Cappa, Jacopo Bulla, Davide Caio

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

<br/>
**Created by Stefano Cappa, Jacopo Bulla, Davide Caio**
