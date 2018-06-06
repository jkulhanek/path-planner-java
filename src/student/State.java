package student;

import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.List;
import cz.cvut.atg.zui.astar.Utils;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

public abstract class State implements Comparable<State> {
    private GraphNode graphNode;
    private Double cost = null;
    private StateSpace space;
    private Double heuristicCost = null;

	public State(GraphNode current, StateSpace space){
        this.graphNode = current;
        this.space = space;
    }


    public double getBaseCost(){
        if(cost== null){
            cost = calculateCost();
        }

        return cost;
    }

    public double getCost(){
        return getBaseCost() + getHeuristicCost();
    }

    protected StateSpace getSpace(){
        return this.space;
    }


    private double getHeuristicCost(){
        if(heuristicCost== null){
            heuristicCost = calculateHeuristicCost();
        }

        return heuristicCost;
    }

    private double calculateHeuristicCost() {
		return 60.0 * Utils.distanceInKM(this.graphNode, this.space.getGoalNode()) / this.space.getMaximalSpeed();
	}


	protected abstract double calculateCost();

    public GraphNode getGraphNode(){
        return graphNode;
    }

    public long getGraphNodeId(){
        return graphNode.getId();
    }


	@Override
	public int compareTo(State arg0) {
		return ((Double)getCost()).compareTo(arg0.getCost());
    }

    private List<State> children;

    protected List<State> getChildren(){
        return children;
    }

    protected void reevaluate(OpenList openList){
        this.cost = null; // resets cost
        openList.reevaluate(this);

        if(getChildren()!= null){
            for(State child : getChildren()){
                child.reevaluate(openList);
            }
        }
    }
    
    public List<State> expand(){        
        List<GraphEdge> edges =  this.space.getGraph().getNodeOutcomingEdges(graphNode.getId());
        if(edges != null){
            this.children = new ArrayList<State>();
            for(GraphEdge edge : edges){
                long toNodeId = edge.getToNodeId();
                GraphNode toNode = this.space.getGraph().getNodeByNodeId(toNodeId);
                State state = new StateImpl(edge, this, toNode, this.space);
                children.add(state);
            }
        }

        return getChildren();
    }
}