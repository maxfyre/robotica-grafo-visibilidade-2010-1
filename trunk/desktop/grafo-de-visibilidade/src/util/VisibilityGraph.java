/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;


/**
 *
 * @author Lucas
 */
public class VisibilityGraph {

    private ObjectsControl objetos;
    private ArrayList<Line2D> linhas;
    private ArrayList<Line2D> bestWay;
    private Graph graph;

    public VisibilityGraph(ObjectsControl objetos) {
        this.objetos = objetos;
        this.linhas = new ArrayList<Line2D>();
        this.bestWay = new ArrayList<Line2D>();
    }

    public void createGraph(){
        //Liga todos os vértices entre si, não faz analise ainda (pode se fazer logo essa junção, e só inserir as linhas válidas, deixei assim pra poupar meu tempo)
        int j;
        for(int i=0; i<objetos.getObstacles().size(); i++){
            for(int k=0; k<objetos.getObstacles().get(i).getSetOfPoints().size(); k++){
                for(j=0;j<objetos.getObstacles().size();j++){
                    for(int l=0; l<objetos.getObstacles().get(j).getSetOfPoints().size(); l++)
                        linhas.add(new Line2D.Float(objetos.getObstacles().get(i).getSetOfPoints().get(k), objetos.getObstacles().get(j).getSetOfPoints().get(l)));
                }
            }
        }
        for(int i=0; i<objetos.getObstacles().size(); i++){
            for(j=0; j<objetos.getObstacles().get(i).getSetOfPoints().size(); j++){
                linhas.add(new Line2D.Float(objetos.getpStart(), objetos.getObstacles().get(i).getSetOfPoints().get(j)));
                linhas.add(new Line2D.Float(objetos.getpEnd(), objetos.getObstacles().get(i).getSetOfPoints().get(j)));
            }
        }
        removerInvalidos();
        gerarMelhorCaminho();
    }

    private void removerInvalidos(){
        for(int i=0; i<linhas.size(); i++){
            for(int j=0; j<objetos.getObstacles().size(); j++){
                if(testarValidade(linhas.get(i), objetos.getObstacles().get(j))){
                    linhas.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    private boolean testarValidade(Line2D line, Obstacle objeto){
        boolean intercepta = false;
        //Verifica se não vai ter intersecção entre retas, analisando os vértices
        //Todas as linhas do objeto
        for(int i=0; i<objeto.getLinesObjects().size(); i++){
            //Compara distancia da linha a um ponto(vertice)
            if(line.intersectsLine(objeto.getLinesObjects().get(i))){
                if(line.getP1().equals(objeto.getLinesObjects().get(i).getP1()) || line.getP2().equals(objeto.getLinesObjects().get(i).getP1()) || line.getP1().equals(objeto.getLinesObjects().get(i).getP2()) || line.getP2().equals(objeto.getLinesObjects().get(i).getP2())){       
                }else
                    intercepta=true;
            }
        }

        //Verifica problemas de ligações por dentro dos poligonos
        if(intercepta==false){
            //Verifica se o caminho não passa por dentro do poligono, ligando dois vértices do mesmo
            if(objeto.getSetOfPoints().contains(line.getP1()) && objeto.getSetOfPoints().contains(line.getP2()) && !line.getP1().equals(line.getP2()) ){
                int P1 = objeto.getSetOfPoints().indexOf(line.getP1());
                int P2 = objeto.getSetOfPoints().indexOf(line.getP2());
                System.out.println(P1 + " " + P2);
                if(P1-P2==1 || P1-P2==-1 || P1-P2==objeto.getLinesObjects().size()-1 || P1-P2==-(objeto.getLinesObjects().size()-1)){
                }else
                    intercepta=true; 
            }
        }
        return intercepta;
    }

    public void gerarMelhorCaminho(){
        graph = new Graph(linhas, objetos.getListOfExpandedPoints());
        System.out.println(objetos.getListOfExpandedPoints().size());

        ArrayList<Point> points = graph.searchWay();
        for(int i=1; i<points.size(); i++){
            bestWay.add(new Line2D.Float(points.get(i-1), points.get(i)));
        }

    }

    public ArrayList<Line2D> getListOfLines(){
        return linhas;
    }

    public ArrayList<Line2D> getBestWay(){
        return bestWay;
    }
}
