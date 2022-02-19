import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problem {

    private Map<Integer, Integer> edgeMap = new HashMap<>();
    private List<Integer> nodeList = new ArrayList<>();
    private List<Integer> nodes = new ArrayList<>();
    private Stack<Integer> nodeStack = new Stack<>();
    private Map<Integer, List<Integer>> possibleEdgeMap = new HashMap<>();
    private int nodeSize;

    public Problem(int nodeSize) {
        this.nodeSize = nodeSize;
        buildNodeListToRange();
        buildPossibleEdgeMap();
        buildNodeStack();
    }

    private void buildNodeListToRange() {
        this.nodes.addAll(IntStream.range(0, nodeSize).boxed().collect(Collectors.toList()));
    }

    public void edgeBasedSolution() {
        long startSec = System.currentTimeMillis();

        while (!possibleEdgeMap.isEmpty()) {

            int firstNode = nodeStack.pop();
            int secondNode = 0;

            List<Integer> nodeSet = possibleEdgeMap.get(firstNode);
            int randomInSetNumber = 0;

            if (nodeSet == null) {

            } else if (nodeSet.isEmpty()) {
                possibleEdgeMap.remove(firstNode);
            } else {
                Random randomInSet = new Random();
                randomInSetNumber = randomInSet.nextInt(nodeSet.size());
                secondNode = nodeSet.get(randomInSetNumber);

                possibleEdgeMap.remove(firstNode);
                possibleEdgeMap.remove(secondNode);

                for (List<Integer> nodes : possibleEdgeMap.values()) {
                    int indexOfFirstNode = nodes.indexOf(firstNode);
                    int indexOfSecondNode = nodes.indexOf(secondNode);

                    if (indexOfFirstNode != -1)
                        nodes.remove(nodes.indexOf(firstNode));
                    if (indexOfSecondNode != -1)
                        nodes.remove(nodes.indexOf(secondNode));
                }

                edgeMap.put(firstNode, secondNode);

            }
        }

        long endSec = System.currentTimeMillis();

        System.out.println("Calculation Time: " + (endSec - startSec) + " ms.");
        for (Map.Entry<Integer, Integer> edge : edgeMap.entrySet()) {
            System.out.println("(" + edge.getKey() + ", " + edge.getValue() + ")");
        }
    }

    //    public void nodeBasedSolution() {
    //        long startSec = System.currentTimeMillis();
    //
    //        while (!possibleEdgeMap.isEmpty()) {
    //
    //            int firstNode = nodeStack.pop();
    //
    //            List<Integer> nodeSet = possibleEdgeMap.get(firstNode);
    //
    //            if (nodeSet == null) {
    //
    //            } else if (nodeSet.isEmpty()) {
    //                possibleEdgeMap.remove(firstNode);
    //            } else {
    //                possibleEdgeMap.remove(firstNode);
    //
    //                for (List<Integer> nodes : possibleEdgeMap.values()) {
    //                    int indexOfFirstNode = nodes.indexOf(firstNode);
    //
    //                    if (indexOfFirstNode != -1)
    //                        nodes.remove(nodes.indexOf(firstNode));
    //                }
    //                nodeList.add(firstNode);
    //            }
    //        }
    //        long endSec = System.currentTimeMillis();
    //
    //        System.out.println("Calculation Time: " + (endSec - startSec) + " ms.");
    //        for (Integer edge : nodeList) {
    //            System.out.println("(" + edge + ")");
    //        }
    //    }

    private void buildNodeStack() {
        while (!nodes.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(nodes.size());
            int node = nodes.remove(index);
            nodeStack.add(node);
        }
    }

    private void buildPossibleEdgeMap() {
        for (int nodeIndex = 0; nodeIndex < nodeSize - 1; nodeIndex++) {
            Random random = new Random();
            int numberOfNVertex = random.nextInt(nodeSize - nodeIndex + 1) + nodeIndex;
            possibleEdgeMap.put(nodeIndex,
                                new ArrayList<>(new Random()
                                                .ints(numberOfNVertex, nodeIndex + 1, nodeSize)
                                                .boxed().collect(Collectors.toSet())));
        }
    }

    public Map<Integer, Integer> getEdgeMap() {
        return edgeMap;
    }

}
