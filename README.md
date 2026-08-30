# Zombie Apocalypse Simulation Engine

Progetto Java ad oggetti per simulare l'evoluzione di un'epidemia zombie su una mappa a griglia.
La simulazione contiene umani con professioni diverse, zombie con mutazioni diverse, risorse,
barricate, zone contaminate, collisioni, contagio e condizioni automatiche di fine partita.

## Come eseguire

Compilazione:

```bash
javac *.java
```

Avvio con interfaccia grafica Swing:

```bash
java Main
```

Avvio in console:

```bash
java Main console
```

## Requisiti implementati

- Entita base astratta `Agente` con coordinate, salute e velocita.
- Gerarchia `Umano`, `Zombie`, `Runner`, `Tank`.
- Professioni `Soldato`, `Medico`, `Civile`.
- Inventario degli umani con `Medikit` e `Munizioni`.
- Mappa a griglia con agenti, risorse, barricate e zone contaminate.
- Game loop a turni nella classe `Simulatore`.
- Fase di percezione, fase di azione, collisioni e risoluzione degli scontri.
- Contagio: un umano morto per morso notifica il simulatore e viene trasformato in zombie.
- Condizioni di fine simulazione: estinzione umana, eradicazione zombie e timeout.
- Interfaccia grafica Swing con griglia, legenda, statistiche e log eventi.
- Diagramma UML in `diagramma-classi.puml`.

## Design pattern usati

### Factory Method

La classe astratta `AgenteFactory` definisce il metodo `creaAgente`.
Le factory concrete (`UmanoFactory`, `ZombieFactory`, `RunnerFactory`, `TankFactory`) creano le entita
senza spargere `new` nel codice principale. Il simulatore usa la factory anche quando un umano viene
convertito in zombie.

### State Pattern

L'interfaccia `StatoUmano` rappresenta il comportamento dinamico di un umano.
Le classi `InEsplorazione`, `InCombattimento`, `InFuga` e `Infetto` cambiano il comportamento del metodo
`agisci` in base alla situazione.

### Observer Pattern

L'interfaccia `Observer` permette di ascoltare eventi della simulazione.
La `Console` osserva gli eventi testuali, mentre il `Simulatore` osserva gli umani e intercetta l'evento
`onMortePerMorso`. Quando un umano muore per morso, il simulatore lo rimuove dalla mappa e crea uno
zombie nelle stesse coordinate.

## Classi principali

- `Main`: punto di ingresso, avvia GUI o console.
- `ScenarioFactory`: crea lo scenario iniziale della partita.
- `Simulatore`: gestisce tick, agenti, eventi, collisioni e fine simulazione.
- `SimulatoreGUI`: interfaccia grafica Swing.
- `Mappa`: contiene agenti, risorse, barricate e zone contaminate.
- `Agente`: classe astratta per tutti gli attori mobili.
- `Umano`: agente con professione, inventario e stato.
- `Zombie`: agente nemico con inseguimento e morso.

## Legenda GUI

- `S`: soldato
- `M`: medico
- `C`: civile
- `Z`: zombie base
- `R`: runner
- `T`: tank
- `+`: medikit
- `A`: munizioni
- `B`: barricata
- `X`: zona contaminata

## Note progettuali

La simulazione e' volutamente semplice: gli agenti si muovono su una griglia e usano una distanza
geometrica semplificata per inseguire o percepire i nemici. L'obiettivo principale del progetto non e'
creare un gioco completo, ma mostrare una struttura ad oggetti con ereditarieta, polimorfismo,
incapsulamento, interfacce e design pattern.
