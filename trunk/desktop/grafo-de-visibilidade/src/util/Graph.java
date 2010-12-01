/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Elvio
 */
public class Graph {
    
    private int adjMat[][];
    private int nVerts;
    private ArrayList<Vertex> vertexList;

    
    public Graph(ArrayList<Line2D> lines, ArrayList<Point> points){
        vertexList = new ArrayList<Vertex>();
        nVerts=points.size();
        adjMat = new int[nVerts][nVerts];
        
        for(int i=0; i<points.size(); i++){
            vertexList.add(new Vertex(points.get(i)));
            for(int j=0; j<points.size(); j++){
                adjMat[i][j]=0;
            }
        }
        addEdges(lines);
        displayMatriz();
//        search();
    }

    //Gerando a matriz de acordo com as linhas obtidas anteriormente
    private void addEdges(ArrayList<Line2D> lines){
        for(int i=0; i<lines.size(); i++){
            for(int j=0; j<nVerts; j++){
                for(int k=0; k<nVerts; k++){
                    if(lines.get(i).getP1().equals(vertexList.get(j).getId()) && lines.get(i).getP2().equals(vertexList.get(k).getId())){
                        System.out.println(j + " " + k);
                        if(j!=k){
//                            double z = lines.get(i).getP1().distance(lines.get(i).getP2());
                            adjMat[k][j]=1;
                            adjMat[j][k]=1;
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Point> searchWay(){
        ArrayList<Point> points = new ArrayList<Point>();

        //a pilha deu pau... =/
        ArrayList<Integer> stack = new ArrayList<Integer>();
        vertexList.get(0).setWasVisited(true);
        points.add(vertexList.get(0).getId());
        stack.add(0);
        System.out.println("Caminho.....");

        while(!stack.isEmpty()){
            int vertex= getAdjUnvisited(stack.get(stack.size()-1));
            if(vertex==-1){
                stack.remove(stack.size()-1);
            }else{
                vertexList.get(vertex).setWasVisited(true);
                displayVertex(vertex);
                points.add(vertexList.get(vertex).getId());
                stack.add(vertex);
            }
        }

        System.out.println("fim...");
        return points;

    }

    private int getAdjUnvisited(int vertex){
        for(int i=0; i<nVerts; i++){
            if(adjMat[vertex][i]>0 && !vertexList.get(i).isWasVisited())
                return i;
        }
        return -1;
    }


    public void displayAllVertex(){
        for(int i=0; i<nVerts; i++){
            System.out.print(vertexList.get(i).getId().getX() + " ");
            System.out.println(vertexList.get(i).getId().getY());
        }
    }

    public void displayVertex(int i){
        System.out.print(vertexList.get(i).getId().getX() + " ");
        System.out.println(vertexList.get(i).getId().getY());
    }

    public void displayMatriz(){
        for(int i=0; i<nVerts; i++){
            for(int j=0; j<nVerts; j++){
                System.out.print(adjMat[i][j]);
            }
            System.out.println("");
        }
    }


}
