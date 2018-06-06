package student;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

import cz.cvut.atg.zui.astar.AbstractOpenList;

public class OpenList extends AbstractOpenList<State>
{
	private PriorityQueue<State> queue;
	private Map<Long, Integer> indexes;

	public OpenList(){
		this.queue = new PriorityQueue<State>();
		this.indexes = new HashMap<Long, Integer>();
	}

	@Override
	protected boolean addItem(State item) {
		queue.add(item);

		Integer oldVal = indexes.get(item.getGraphNodeId());
		if(oldVal == null){
			indexes.put(item.getGraphNodeId(), 1);
		}
		else{
			indexes.replace(item.getGraphNodeId(), oldVal, oldVal+1);
		}
		return true;
	}

	public boolean containsKey(long key){
		return indexes.containsKey(key);
	}

	public State get(long key){
		if(!containsKey(key))
			return null;		

		State state = null;

		for(State item:queue){
			if(item.getGraphNodeId() == key){
				state = item;
				break;
			}
		}
		
		return state;
	}
	

	public State getItem(){		
		State ret = queue.poll();
		Integer oldVal = indexes.get(ret.getGraphNodeId());
		if(oldVal == 1){
			indexes.remove(ret.getGraphNodeId());
		}
		else{
			indexes.replace(ret.getGraphNodeId(), oldVal, oldVal-1);
		}

		return ret;
	}

	public boolean isEmpty(){
		return queue.isEmpty();
	}

	public void reevaluate(State item){
		if(indexes.containsKey(item.getGraphNodeId()))
			if(queue.remove(item))
				queue.add(item);
	}
}