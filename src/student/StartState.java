package student;

import eu.superhub.wp5.planner.planningstructure.GraphNode;

public final class StartState extends State{

    public StartState(GraphNode current, StateSpace space) {
        super(current, space);
    }


	@Override
	protected double calculateCost() {
		return 0;
	}

}