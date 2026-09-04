public class Main {

    public static void main(String[] args) {

        if (args.length > 0 &&args[0].equalsIgnoreCase("console")) {

            Simulatore simulatore =ScenarioFactory.creaScenarioBase(true);

            simulatore.eseguiTurno();

            return;
        }

        javax.swing.SwingUtilities.invokeLater(
            new Runnable() {
                @Override
                public void run() {
                    new SimulatoreGUI().setVisible(true);
                }
            }
        );
    }
}
