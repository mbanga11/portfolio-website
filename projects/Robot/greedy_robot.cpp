#include <iostream>
#include <string>
#include <vector>
#include <cstdlib>
#include <cmath>
using namespace std;

// Returns the distance between the robot and the treasure.
// No diagonal movement allowed.
int ShortestPossibleDistance(int robot_x, int robot_y, int treasure_x, int treasure_y) {
    return abs(robot_x - treasure_x) + abs(robot_y - treasure_y);
}

void FindPathsToTreasure(int robot_x, int robot_y, int treasure_x, int treasure_y, int max_distance) {
    vector<string> possibleDistance; // Stores valid paths from robot - treasure as strings
    string thisDistance; // Tracks current path being used
    char lastdirection = ' ';  // Stores the last direction moved to keep the max distance in check 
    int count = 0; // Keeps track of how many steps have been taken in the current path.

    // Recursively find all valid paths.
    FindPathsToTreasureHelper(robot_x, robot_y, treasure_x, treasure_y, max_distance, thisDistance, lastdirection, count, possibleDistance);

    // Output all paths
    for (const string& path : possibleDistance) {
        cout << path << endl;
    }
    cout << "Number of paths: " << possibleDistance.size() << endl;
}


// Helpers that has more information to find valid paths 
void FindPathsToTreasureHelper(int robot_x, int robot_y, int treasure_x, int treasure_y, int max_distance, string thisDistance, char lastdirection, int count, vector<string>& possibleDistance) {
    //Base Case: checks if they are on the same point and adds to path
    if (robot_x == treasure_x && robot_y == treasure_y) {
        possibleDistance.push_back(thisDistance);
        return;
    }

    int currentDistance= ShortestPossibleDistance(robot_x, robot_y, treasure_x, treasure_y);

    // North
    int nextDistanceN = ShortestPossibleDistance(robot_x, robot_y + 1, treasure_x, treasure_y);
    if (nextDistanceN < currentDistance) {
        if (lastdirection == 'N' && count + 1 <= max_distance )
            // If continuing in the same direction, increment step count and continue path.
            FindPathsToTreasureHelper(robot_x, robot_y + 1, treasure_x, treasure_y, max_distance, thisDistance + 'N', 'N', count + 1, possibleDistance);
        else if( lastdirection != 'N'  )
            // If changing direction, reset step count to 1 and continue path.
            FindPathsToTreasureHelper(robot_x, robot_y + 1, treasure_x, treasure_y, max_distance, thisDistance + 'N', 'N', 1, possibleDistance);
    }

    // South
    int nextDistanceS = ShortestPossibleDistance(robot_x, robot_y - 1, treasure_x, treasure_y);
    if (nextDistanceS < currentDistance) {
        if  (lastdirection == 'S'&& count + 1 <= max_distance)
            // If continuing in the same direction, increment step count and continue path.
            FindPathsToTreasureHelper(robot_x, robot_y - 1, treasure_x, treasure_y, max_distance, thisDistance + 'S', 'S', count + 1, possibleDistance);
            else if( lastdirection != 'S'  )
            // If changing direction, reset step count to 1 and continue path.
            FindPathsToTreasureHelper(robot_x, robot_y - 1, treasure_x, treasure_y, max_distance, thisDistance + 'S', 'S', 1, possibleDistance);
    }

    // East
    int nextDistanceE = ShortestPossibleDistance(robot_x + 1, robot_y, treasure_x, treasure_y);
    if (nextDistanceE < currentDistance) {
        if (lastdirection == 'E'&& count + 1 <= max_distance)
            // If continuing in the same direction, increment step count and continue path.
            FindPathsToTreasureHelper(robot_x + 1, robot_y, treasure_x, treasure_y, max_distance, thisDistance + 'E', 'E', count + 1, possibleDistance);
        else if( lastdirection != 'E'  )
            // If changing direction, reset step count to 1 and continue path.
            FindPathsToTreasureHelper(robot_x + 1, robot_y, treasure_x, treasure_y, max_distance, thisDistance + 'E', 'E', 1, possibleDistance);
    }

    // West
    int nextDistanceW = ShortestPossibleDistance(robot_x - 1, robot_y, treasure_x, treasure_y);
    if (nextDistanceW < currentDistance) {
        if (lastdirection == 'W' && count + 1 <= max_distance)
            // If continuing in the same direction, increment step count and continue path.
            FindPathsToTreasureHelper(robot_x - 1, robot_y, treasure_x, treasure_y, max_distance, thisDistance + 'W', 'W', count + 1, possibleDistance);
        else if( lastdirection != 'W'  )
            // If changing direction, reset step count to 1 and continue path.
            FindPathsToTreasureHelper(robot_x - 1, robot_y, treasure_x, treasure_y, max_distance, thisDistance + 'W', 'W', 1, possibleDistance);
    }
}



int main(int argc, char* argv[]) {
    // Check if exactly 5 arguments are provided
    if (argc != 6) {
        cout << "ERROR: Not the right amount of information" << endl;
        return 1;
    }

    int max_distance; 
    int robot_x;
    int robot_y;
    int treasure_x;
    int treasure_y;

    // Convert command-line arguments to integers.
    try {
        max_distance = stoi(argv[1]);
        robot_x = stoi(argv[2]);
        robot_y = stoi(argv[3]);
        treasure_x = stoi(argv[4]);
        treasure_y = stoi(argv[5]);
    // Handle non-integer inputs.
    } catch (invalid_argument& e) {
        
        cout << "Error: All arguments must be integers." << endl;
        return 1;
    }
    // See that max_distance is positive.
    if (max_distance <= 0) {
        cout << "Error: max_distance must be greater than 0." << endl;
        return 1;
    }

    // Begin finding all valid paths from robot to treasure.
    FindPathsToTreasure(robot_x, robot_y, treasure_x, treasure_y, max_distance);

    return 0;
}

