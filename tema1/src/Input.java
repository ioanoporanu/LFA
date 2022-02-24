import java.util.*;
import java.util.stream.Collectors;

public class Input {
    public int n;
    public int m;
    public int s;
    public int f;

    public ArrayList<Integer> utilStates;
    public ArrayList<Integer> accessibleStates;
    public ArrayList<Integer> productiveStates;

    public ArrayList<ArrayList<Integer>> graph;
    public ArrayList<ArrayList<Integer>> complementarGraph;
    public int[] initialStates;
    public int[] finalStates;

    public Input(){
        graph = new ArrayList<ArrayList<Integer>>();
        complementarGraph = new ArrayList<ArrayList<Integer>>();
        readInp();
    }

    void readInp(){
        String[] params;
        String line;
        Scanner tralala = new Scanner(System.in);
        line = tralala.nextLine();
        params = line.split(" ");
        n = Integer.parseInt(params[0]);
        m = Integer.parseInt(params[1]);
        s = Integer.parseInt(params[2]);
        f = Integer.parseInt(params[3]);

        initialStates = new int[s];
        finalStates = new int[f];

        for(int i = 0; i < n; i++){
            graph.add(new ArrayList<>());
            complementarGraph.add(new ArrayList<>());
        }

        for(int i = 0; i < n; i++){
            line = tralala.nextLine();
            params = line.split(" ");
            for(int j = 0; j < m; j++){
                graph.get(i).add(Integer.parseInt(params[j]));
                complementarGraph.get(Integer.parseInt(params[j])).add(i);
            }
        }

        if(s > 0) {
            line = tralala.nextLine();
            params = line.split(" ");
            for (int i = 0; i < s; i++) {
                initialStates[i] = Integer.parseInt(params[i]);
            }
        }

        if(f > 0) {
            line = tralala.nextLine();
            params = line.split(" ");
            for (int i = 0; i < f; i++) {
                finalStates[i] = Integer.parseInt(params[i]);
            }
        }

        tralala.close();
        utilStates = new ArrayList<>();
        accessibleStates = new ArrayList<>();
        productiveStates = new ArrayList<>();
    }

    public void BFS(int[] states, String mode){
        ArrayList<ArrayList<Integer>> graphInp = new ArrayList<>();

        if (mode.equals("accessible")) {
            graphInp = graph;
        } else if (mode.equals("productive")) {
            graphInp = complementarGraph;
        }

        boolean[] visited = new boolean[graphInp.size()];
        Queue<Integer> queue = new ArrayDeque<>();

        for (int state : states) {
            visited[state] = true;
            queue.add(state);
        }

        int node;
        while (queue.size() != 0)
        {
            node = queue.poll();
            for(int i = 0; i < graphInp.get(node).size(); i++) {
                int n = graphInp.get(node).get(i);
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }

        for(int i = 0; i < visited.length; i++){
            if(visited[i]) {

                if (mode.equals("accessible")) {
                    accessibleStates.add(i);
                }
                if (mode.equals("productive")) {
                    productiveStates.add(i);
                    if(accessibleStates.contains(i))
                        utilStates.add(i);
                }
            }
        }
    }

    String DFS(int state1, int state2, String Path, ArrayList<Pair> visited, boolean endState){
        if(state1 == state2) {
            if (endState){
                for(int i = 0; i < finalStates.length; i++){
                    if(state1 == finalStates[i])
                        return Path;
                }
            } else {
                return Path;
            }
        }

        Pair pair = new Pair(state1, state2);
        for (Pair p : visited) {
            if (p.first == state1 || p.second == state2) {
                return null;
            }
        }
        ArrayList<Pair> newVisited = new ArrayList<>(visited);
        newVisited.add(pair);
        for(int j = 0; j < m; j++){
                String res = DFS(graph.get(state1).get(j), graph.get(state2).get(j), Path + j + " ", newVisited, endState);
                if(res != null)
                    return res;
        }
        return null;
    }

    int decodeState (int state, String code) {
        String[] codes = code.split(" ");
        for(int i = 0; i < codes.length; i++){
            state  = graph.get(state).get(Integer.parseInt(codes[i]));
        }

        return state;
    }

    String synchronize(){
        ArrayList<Integer> states = new ArrayList<Integer>();
        ArrayList<Integer> newStates = new ArrayList<Integer>();
        if (s == 0) {
            for(int i = 0; i < n; i++){
                states.add(i);
            }
        } else {
            for(int i = 0; i < initialStates.length; i++){
                states.add(initialStates[i]);
            }
        }

        String result = "";

        while(states.size() > 1){
            ArrayList<Pair> visited = new ArrayList<>();
            String p = "";
            if(f != 0) {
                p = DFS(states.get(0), states.get(1), "", visited, true) + " ";
            } else {
                p = DFS(states.get(0), states.get(1), "", visited, false) + " ";
            }
            result += p;
            for(int i = 0; i < states.size(); i++){
               int t = decodeState(states.get(i), p);
               if (!newStates.contains(t)){
                   newStates.add(t);
               }
            }
            states = new ArrayList<>(newStates);
            newStates = new ArrayList<>();

        }
        return result;
    }
}
