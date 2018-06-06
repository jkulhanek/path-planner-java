package student;

import cz.cvut.atg.zui.astar.RoadGraph;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

public class StateSpace{
    private RoadGraph graph;
    private GraphNode goalNode;
    private Double averageSpeed;
    private Double minimalSpeed;
    private Double maximalSpeed;

    public StateSpace(RoadGraph graph, GraphNode goalNode){
        this.graph = graph;
        this.goalNode = goalNode;
    }

    public GraphNode getGoalNode(){
        return goalNode;
    }

    public double getAverageSpeed(){
        if(averageSpeed == null){
            averageSpeed = Utils.getAverageSpeed(graph);
        }

        return averageSpeed;
    }

    public double getMinimalSpeed(){
        if(minimalSpeed == null){
            minimalSpeed = Utils.getMinimalSpeed(graph);
        }

        return minimalSpeed;
    }

    public double getMaximalSpeed(){
        if(maximalSpeed == null){
            maximalSpeed = Utils.getMaximalSpeed(graph);
        }

        return maximalSpeed;
    }

    public RoadGraph getGraph(){
        return this.graph;
    }
}