public class Main {

    public static void main(String[] args) {

        if (args.length > 0 &&
            args[0].equalsIgnoreCase("console")) {

            Simulatore simulatore =
                ScenarioFactory.creaScenarioBase(true);

            for (int i = 0; i < 30; i++) {
                simulatore.eseguiTurno();
            }

            return;
        }

        javax.swing.SwingUtilities.invokeLater(
            () -> new SimulatoreGUI().setVisible(true)
        );
    }
}
