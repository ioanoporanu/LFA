public class Main {
    public static void main(String [] args) {
        Input input = new Input();
        if(args[0].equals("accessible")) {
            input.BFS(input.initialStates,"accessible");
            for(int i = 0; i < input.accessibleStates.size(); i++){
                System.out.println(input.accessibleStates.get(i));
            }
        }

        if(args[0].equals("productive")) {
            input.BFS(input.finalStates, "productive");
            for(int i = 0; i < input.productiveStates.size(); i++){
                System.out.println(input.productiveStates.get(i));
            }
        }

        if(args[0].equals("useful")) {
            input.BFS(input.initialStates,"accessible");
            input.BFS(input.finalStates, "productive");
            for( int i = 0; i < input.utilStates.size(); i++){
                System.out.println(input.utilStates.get(i));
            }
        }

        if(args[0].equals("synchronize")) {
            String p = input.synchronize();
            System.out.println(p);
        }
    }
}
