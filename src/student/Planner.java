package student;

import cz.cvut.atg.zui.astar.AbstractOpenList;
import cz.cvut.atg.zui.astar.PlannerInterface;
import cz.cvut.atg.zui.astar.RoadGraph;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Planner implements PlannerInterface {

    private OpenList openList;

	public Planner(){
    }

    @Override
    public List<GraphEdge> plan(RoadGraph graph, GraphNode origin, GraphNode destination) {
        StateSpace space = new StateSpace(graph, destination);
        StartState state = new StartState(origin, space);

        Map<Long, State> closedList = new HashMap<Long, State>();
        openList = new OpenList();
        openList.add(state);

        while(!openList.isEmpty()){
            State currentState = openList.getItem();

            if(currentState.getGraphNodeId() == destination.getId()){
                return computePath(currentState);
            }
            
           /* State closedState = null;
            if((closedState = closedList.get(currentState.getGraphNodeId())) !=  null){
                if(closedState.getBaseCost() <= currentState.getBaseCost() || closedState instanceof StartState || currentState instanceof StartState){
                    continue;
                    // Cost is lower
                    // no need for revisiting
                }
                else{
                    currentState = ((StateImpl)closedState).rebaseTo((StateImpl)currentState, openList);
                    continue;
                }
            }*/
                       

            List<State> expanded = currentState.expand();

            if(expanded!=null){
                for(State item : expanded){
                    // Do not revisit closed item
                    // Do not even add them to the open list
                    // To minimize the number of added items
                    State compareState = null;
                    if((compareState = closedList.get(item.getGraphNodeId())) !=  null){
                        if(compareState.getBaseCost() <= item.getBaseCost() || compareState instanceof StartState || item instanceof StartState){
                            continue;
                            // Cost is lower
                            // no need for revisiting
                        }
                        else{
                            item = ((StateImpl)compareState).rebaseTo((StateImpl)item, openList);
                            continue;
                        }
                    }
                    else if((compareState = openList.get(item.getGraphNodeId())) !=  null){
                        if(compareState.getBaseCost() < item.getBaseCost() || compareState instanceof StartState || item instanceof StartState){
                            continue;
                            // Cost is lower
                            // no need for revisiting
                        }
                        else{
                            item = ((StateImpl)compareState).rebaseTo((StateImpl)item, openList);
                            continue;
                        }
                    }
                    else{
                        openList.add(item);
                    }
                }
            }

            // add to closed list
            closedList.put(currentState.getGraphNodeId(), currentState);
        }
        
        return null;
    }

    private List<GraphEdge> computePath(State currentState) {
        ArrayList<GraphEdge> edges = new ArrayList<>();
        addToPath(edges, currentState);
        return edges;
    }
    
    private void addToPath(List<GraphEdge> edges, State state){
        if(!(state instanceof StateImpl)){
            return;
        }
        else{
            StateImpl stateImpl = (StateImpl)state;
            addToPath(edges, stateImpl.getParentState());
            edges.add(stateImpl.getIncommingEdge());
        }
    }

	@Override
    public AbstractOpenList getOpenList() {
        // This breaks OOP principles!!!!!!
        return this.openList;
    }
}