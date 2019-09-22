package base;

public class DataRecoveryThread extends Thread{

    private boolean isTeam;

    public DataRecoveryThread(boolean isTeam){
        this.isTeam = isTeam;
    }


    @Override
    public void run(){
        if(isTeam){
            System.out.println("RECOVERING TEAMS");
            Main.addToOpenTeams(Lib.recoverTeams(Main.currentSession.eventDir));
            System.out.println(Main.openTeams.get(1).toReadableString());
            System.out.println("TEAMS RECOVERED");
        }else{
            System.out.println("RECOVERING MATCHES");
            Main.addToOpenMatches(Lib.recoverMatches(Main.currentSession.eventDir));
            System.out.println(Main.openMatches.get(1).toReadableString());
            System.out.println("MATCHES RECOVERED");
        }
        this.interrupt();
    }
}