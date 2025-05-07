#ifndef GREEDY_ROBOT_H
#define GREEDY_ROBOT_H

#include <string>
#include <vector>
using namespace std;

// Computes the shortest distance between the robot and the treasure
int ShortestPossibleDistance(int robot_x, int robot_y, int treasure_x, int treasure_y);

// recursively finds all valid paths from the robot to the treasure within the max_distance
void FindPathsToTreasureHelper(int robot_x, int robot_y, int treasure_x, int treasure_y, int max_distance, string thisDistance, char lastdirection, int count, vector<string>& possibleDistance);

// Entry point to find all valid paths and output them.
void FindPathsToTreasure(int robot_x, int robot_y, int treasure_x, int treasure_y, int max_distance);

#endif 
