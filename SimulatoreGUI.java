import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SimulatoreGUI extends JFrame implements Observer {

    private Simulatore simulatore;
    private MappaPanel mappaPanel;
    private JTextArea logArea;
    private JLabel tickLabel;
    private JLabel umaniLabel;
    private JLabel zombieLabel;
    private JLabel statoLabel;
    private Timer timer;

    public SimulatoreGUI() {
        super("Zombie Apocalypse Simulation Engine");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 680);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        mappaPanel = new MappaPanel();
        logArea = new JTextArea();
        tickLabel = new JLabel();
        umaniLabel = new JLabel();
        zombieLabel = new JLabel();
        statoLabel = new JLabel();

        timer = new Timer(550, event -> eseguiTurno());

        add(creaPannelloComandi(), BorderLayout.NORTH);
        add(mappaPanel, BorderLayout.CENTER);
        add(creaPannelloLaterale(), BorderLayout.EAST);

        resetSimulazione();
    }

    private JPanel creaPannelloComandi() {

        JPanel pannello = new JPanel(new BorderLayout(12, 0));
        pannello.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel titolo = new JLabel("Zombie Apocalypse");
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));

        JPanel bottoni = new JPanel();

        JButton avviaButton = new JButton("Avvia");
        JButton pausaButton = new JButton("Pausa");
        JButton turnoButton = new JButton("Turno");

        avviaButton.addActionListener(event -> timer.start());
        pausaButton.addActionListener(event -> timer.stop());
        turnoButton.addActionListener(event -> eseguiTurno());

        bottoni.add(avviaButton);
        bottoni.add(pausaButton);
        bottoni.add(turnoButton);

        pannello.add(titolo, BorderLayout.WEST);
        pannello.add(bottoni, BorderLayout.EAST);

        return pannello;
    }

    private JPanel creaPannelloLaterale() {

        JPanel pannello = new JPanel(new BorderLayout(0, 12));
        pannello.setPreferredSize(new Dimension(300, 0));
        pannello.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));

        JPanel statistiche = new JPanel(new GridLayout(4, 1, 0, 6));
        statistiche.setBorder(BorderFactory.createTitledBorder("Statistiche"));
        statistiche.add(tickLabel);
        statistiche.add(umaniLabel);
        statistiche.add(zombieLabel);
        statistiche.add(statoLabel);

        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Eventi"));

        JPanel legenda = new JPanel(new GridLayout(7, 1, 0, 4));
        legenda.setBorder(BorderFactory.createTitledBorder("Legenda"));
        legenda.add(new JLabel("S = Soldato"));
        legenda.add(new JLabel("M = Medico"));
        legenda.add(new JLabel("C = Civile"));
        legenda.add(new JLabel("Z = Zombie"));
        legenda.add(new JLabel("R = Runner"));
        legenda.add(new JLabel("T = Tank"));
        legenda.add(new JLabel("+ / A = Medikit / Munizioni"));

        pannello.add(statistiche, BorderLayout.NORTH);
        pannello.add(scrollPane, BorderLayout.CENTER);
        pannello.add(legenda, BorderLayout.SOUTH);

        return pannello;
    }

    private void resetSimulazione() {

        timer.stop();
        logArea.setText("");
        simulatore = ScenarioFactory.creaScenarioBase(false);
        simulatore.aggiungiObserver(this);
        mappaPanel.setMappa(simulatore.getMappa());
        aggiornaVista();
        aggiorna("Scenario iniziale caricato.");
    }

    private void eseguiTurno() {

        if (simulatore.isSimulazioneTerminata()) {
            timer.stop();
            return;
        }

        simulatore.eseguiTurno();
        aggiornaVista();

        if (simulatore.isSimulazioneTerminata()) {
            timer.stop();
        }
    }

    private void aggiornaVista() {

        tickLabel.setText("Tick: " + simulatore.getTick());
        umaniLabel.setText("Umani vivi: " + simulatore.contaUmaniVivi());
        zombieLabel.setText("Zombie vivi: " + simulatore.contaZombieVivi());

        if (simulatore.isSimulazioneTerminata()) {
            statoLabel.setText("Stato: terminata");
        } else {
            statoLabel.setText("Stato: in corso");
        }

        mappaPanel.repaint();
    }

    @Override
    public void aggiorna(String evento) {

        Runnable aggiornaLog = () -> {
            logArea.append(evento + System.lineSeparator());
            logArea.setCaretPosition(logArea.getDocument().getLength());
        };

        if (SwingUtilities.isEventDispatchThread()) {
            aggiornaLog.run();
        } else {
            SwingUtilities.invokeLater(aggiornaLog);
        }
    }

    private static class MappaPanel extends JPanel {

        private Mappa mappa;

        public MappaPanel() {
            setPreferredSize(new Dimension(620, 620));
            setBackground(new Color(245, 245, 240));
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }

        public void setMappa(Mappa mappa) {
            this.mappa = mappa;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            if (mappa == null) {
                return;
            }

            Graphics2D g = (Graphics2D) graphics;

            int colonne = mappa.getLarghezza();
            int righe = mappa.getAltezza();
            int lato = Math.min(
                getWidth() / colonne,
                getHeight() / righe
            );

            int offsetX = (getWidth() - lato * colonne) / 2;
            int offsetY = (getHeight() - lato * righe) / 2;

            disegnaGriglia(g, colonne, righe, lato, offsetX, offsetY);
            disegnaZoneContaminate(g, lato, offsetX, offsetY);
            disegnaBarricate(g, lato, offsetX, offsetY);
            disegnaRisorse(g, lato, offsetX, offsetY);
            disegnaAgenti(g, lato, offsetX, offsetY);
        }

        private void disegnaGriglia(
            Graphics2D g,
            int colonne,
            int righe,
            int lato,
            int offsetX,
            int offsetY
        ) {

            g.setColor(new Color(220, 220, 210));

            for (int y = 0; y < righe; y++) {
                for (int x = 0; x < colonne; x++) {
                    g.drawRect(
                        offsetX + x * lato,
                        offsetY + y * lato,
                        lato,
                        lato
                    );
                }
            }
        }

        private void disegnaZoneContaminate(
            Graphics2D g,
            int lato,
            int offsetX,
            int offsetY
        ) {

            for (ZonaContaminata zona : mappa.getZoneContaminate()) {
                int x = offsetX + zona.getX() * lato;
                int y = offsetY + zona.getY() * lato;

                g.setColor(new Color(232, 190, 190));
                g.fillRect(x + 1, y + 1, lato - 1, lato - 1);
                disegnaTesto(g, x, y, lato, Color.DARK_GRAY, "X");
            }
        }

        private void disegnaBarricate(
            Graphics2D g,
            int lato,
            int offsetX,
            int offsetY
        ) {

            for (Barricata barricata : mappa.getBarricate()) {
                int x = offsetX + barricata.getX() * lato;
                int y = offsetY + barricata.getY() * lato;

                g.setColor(new Color(95, 78, 60));
                g.fillRect(
                    x + lato / 8,
                    y + lato / 8,
                    lato - lato / 4,
                    lato - lato / 4
                );
                disegnaTesto(g, x, y, lato, Color.WHITE, "B");
            }
        }

        private void disegnaRisorse(
            Graphics2D g,
            int lato,
            int offsetX,
            int offsetY
        ) {

            for (Risorsa risorsa : mappa.getRisorse()) {
                int x = offsetX + risorsa.getX() * lato;
                int y = offsetY + risorsa.getY() * lato;

                if (risorsa instanceof RisorsaMedikit) {
                    disegnaCella(g, x, y, lato, new Color(84, 163, 105), "+");
                } else if (risorsa instanceof RisorsaMunizioni) {
                    disegnaCella(g, x, y, lato, new Color(210, 157, 58), "A");
                }
            }
        }

        private void disegnaAgenti(
            Graphics2D g,
            int lato,
            int offsetX,
            int offsetY
        ) {

            List<Agente> agenti = mappa.getAgenti();

            for (Agente agente : agenti) {
                if (!agente.isVivo()) {
                    continue;
                }

                int x = offsetX + agente.getX() * lato;
                int y = offsetY + agente.getY() * lato;

                if (agente instanceof Runner) {
                    disegnaCella(g, x, y, lato, new Color(150, 72, 180), "R");
                } else if (agente instanceof Tank) {
                    disegnaCella(g, x, y, lato, new Color(105, 94, 80), "T");
                } else if (agente instanceof Zombie) {
                    disegnaCella(g, x, y, lato, new Color(122, 153, 57), "Z");
                } else if (agente instanceof Umano) {
                    disegnaUmano(g, (Umano) agente, x, y, lato);
                }
            }
        }

        private void disegnaUmano(
            Graphics2D g,
            Umano umano,
            int x,
            int y,
            int lato
        ) {

            Professione professione = umano.getProfessione();

            if (professione instanceof Soldato) {
                disegnaCella(g, x, y, lato, new Color(55, 119, 184), "S");
            } else if (professione instanceof Medico) {
                disegnaCella(g, x, y, lato, new Color(218, 80, 89), "M");
            } else {
                disegnaCella(g, x, y, lato, new Color(80, 158, 160), "C");
            }
        }

        private void disegnaCella(
            Graphics2D g,
            int x,
            int y,
            int lato,
            Color colore,
            String testo
        ) {

            int margine = Math.max(4, lato / 8);
            int diametro = lato - margine * 2;

            g.setColor(colore);
            g.fillOval(x + margine, y + margine, diametro, diametro);

            g.setColor(Color.WHITE);
            disegnaTesto(g, x, y, lato, Color.WHITE, testo);
        }

        private void disegnaTesto(
            Graphics2D g,
            int x,
            int y,
            int lato,
            Color colore,
            String testo
        ) {

            g.setColor(colore);
            g.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, lato / 3)));

            int larghezzaTesto = g.getFontMetrics().stringWidth(testo);
            int altezzaTesto = g.getFontMetrics().getAscent();

            g.drawString(
                testo,
                x + (lato - larghezzaTesto) / 2,
                y + (lato + altezzaTesto) / 2 - 3
            );
        }
    }
}
