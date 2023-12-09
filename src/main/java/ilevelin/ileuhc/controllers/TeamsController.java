package ilevelin.ileuhc.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Team {

    String teamName;
    List<String> players;

    Team(String name) { this.teamName = name; players = new ArrayList<>(); }

}

public class TeamsController {

    private static TeamsController instance;
    public static TeamsController getInstance() {
        if (instance == null) instance = new TeamsController();
        return instance;
    }
    private TeamsController() { }

    private List<Team> teamList;
    private List<Team> aliveTeamList;
    public List<Team> getTeamList() { return teamList; }
    public List<Team> getAliveTeamList() { return aliveTeamList; }

    public boolean generateRandomTeams(int teamSize, List<String> playerList) {
        if (playerList.size() % teamSize != 0) return false;

        int teamAmount = playerList.size() / teamSize;
        Collections.shuffle(playerList);
        teamList = new ArrayList<>();
        aliveTeamList = new ArrayList<>();

        for (int i = 0; i < teamAmount; i++) {
            Team auxTeam = new Team("Team "+(i+1));
            Team auxTeamAlive = new Team("Team "+(i+1));
            for (int j = 0; j < teamSize; j++) {
                String player = playerList.remove(0);
                auxTeam.players.add(player);
                auxTeamAlive.players.add(player);
            }
            teamList.add(auxTeam);
            aliveTeamList.add(auxTeamAlive);
        }

        return true;
    }

    /**
     * @return Team object if only one team remains, null otherwise
     */
    public Team checkForWinner() {
        if (aliveTeamList.size() == 1) {
            String winnerTeamName = aliveTeamList.get(0).teamName;
            for (Team team : teamList)
                if (team.teamName.equals(winnerTeamName))
                    return team;
        }
        return null;
    }

    /**
     * @return Team object if only one team remains, null otherwise
     */
    public Team checkForWinner(List<String> alivePlayers) {
        boolean isWinner = true;
        Team checkedTeam = null;
        for (int i = 0; i < teamList.size(); i++){
            if (teamList.get(i).players.contains(alivePlayers.get(0))) {
                checkedTeam = teamList.get(i);
                break;
            }
        }

        if (checkedTeam == null) return null;

        for (String player : alivePlayers) {
            isWinner = isWinner && checkedTeam.players.contains(player);
        }

        return isWinner ? checkedTeam : null;
    }

    /**
     * @return Team object if a team is eliminated, null otherwise
     */
    public Team killPlayer(String playerName) {
        for (Team team : aliveTeamList) {
            if (team.players.contains(playerName)) {
                team.players.remove(playerName);
                if (team.players.size() == 0) {
                    aliveTeamList.remove(team);
                    for (Team eliminatedTeam : teamList)
                        if (eliminatedTeam.teamName.equals(team.teamName))
                            return eliminatedTeam;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

}
