package base;

public class DataRecoveryThread extends Thread{


    @Override
    public void run(){
        System.out.println("RECOVERING MATCHES");
        Main.openMatches.addAll(Lib.recoverMatches(Main.currentSession.eventDir));
        System.out.println("MATCHES RECOVERED\nRECOVERING TEAMS");
        Main.openTeams.addAll(Lib.recoverTeams(Main.currentSession.eventDir));
        System.out.println("TEAMS RECOVERED");
        // Main.openPits.addAll(Lib.recoverPits(Main.currentSession.eventDir));
        System.out.println(Main.openMatches.get(1).toReadableString());
        System.out.println(Main.openTeams.get(5).toReadableString());
        this.interrupt();
    }
}