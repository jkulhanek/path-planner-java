package student;

import cz.cvut.atg.zui.astar.RoadGraph;
import eu.superhub.wp5.planner.planningstructure.GraphEdge;
import eu.superhub.wp5.planner.planningstructure.GraphNode;

public class Utils{
    public static double getAverageSpeed(RoadGraph graph){
        double totalDistance = 0;
        double averageSpeed = 0;
        for(GraphEdge node : graph.getAllEdges()){
            double dist = node.getLengthInMetres() / 1000.0;
            totalDistance += dist;
            averageSpeed += dist * node.getAllowedMaxSpeedInKmph();
        }

        return averageSpeed / totalDistance;
    }

    public static double getMinimalSpeed(RoadGraph graph){
        double minSpeed=Double.POSITIVE_INFINITY;
        for(GraphEdge node : graph.getAllEdges()){
            if(minSpeed > node.getAllowedMaxSpeedInKmph()){
                minSpeed = node.getAllowedMaxSpeedInKmph();
            }
        }

        return minSpeed;
    }

    public static double getMaximalSpeed(RoadGraph graph){
        double maxSpeed=Double.NEGATIVE_INFINITY;
        for(GraphEdge node : graph.getAllEdges()){
            if(maxSpeed < node.getAllowedMaxSpeedInKmph()){
                maxSpeed = node.getAllowedMaxSpeedInKmph();
            }
        }

        return maxSpeed;
    }
}