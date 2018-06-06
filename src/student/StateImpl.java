package student;

import java.util.Iterator;

import cz.cvut.atg.zui.astar.Utils;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

public class StateImpl extends State {
    private GraphEdge fromEdge;
    private State fromNode;

	public StateImpl(GraphEdge fromEdge, State fromNode, GraphNode current, StateSpace space){
        super(current, space);
        this.fromEdge = fromEdge;
        this.fromNode = fromNode;
    }

    @Override
	protected double calculateCost(){
        return fromNode.getBaseCost() + (60.0 * fromEdge.getLengthInMetres() / 1000.0  / fromEdge.getAllowedMaxSpeedInKmph());
    }

    public GraphEdge getIncommingEdge(){
        return fromEdge;
    }

    public State getParentState(){
        return fromNode;
    }
    


    public State rebaseTo(StateImpl newParent, OpenList openList){
        this.fromNode = newParent.fromNode;
        this.fromEdge = newParent.fromEdge;
        this.reevaluate(openList);
        return this;
    }
}