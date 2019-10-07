package base;

public class Scouter {
    // Represents a person doing scouting.
    public String name; // The scouter's name
    public CustomTeam[] teams; // The teams they'll be scouting

    public Scouter(String name, CustomTeam[] teams) {
        this.name = name;
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomTeam[] getTeams() {
        return teams;
    }

    public void setTeams(CustomTeam[] teams) {
        this.teams = teams;
    }
}
