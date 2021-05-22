package examples.regresionMultiple;

import jade.core.Agent;
import jade.core.behaviours.*;

public class regresionMultipleAgent extends Agent {
    private RegresionMultiple rMultiple;
    private double prediccionX, prediccionZ;
    private String metodo;
    private regresionMultipleGui myGui;
    private boolean terminado, metodoCargado;

    protected void setup() {
        rMultiple = new RegresionMultiple();
        myGui = new regresionMultipleGui(this);
        myGui.showGui();

        terminado = false;
        metodoCargado = false;

        metodo = "";

        addBehaviour(new entrenar());
        addBehaviour(new predecirDatos());
    }

    protected void takeDown() {
        myGui.dispose();
        System.out.println("Terminado!!!");
        terminado = true;
    }

    public void agregarPrediccion(final double preX, final double preZ) {
        prediccionX = preX;
        prediccionZ = preZ;
    }

    public void agregarMetodoRegresion(final String m){
        metodo = m;
    }

    private class entrenar extends Behaviour {
        public void action() {
            if(metodo != "" && !metodoCargado){
                rMultiple.metodoRegresion(metodo);
                rMultiple.entrenar();
                metodoCargado = true;
            }
        }

        public boolean done(){
            if (metodoCargado)
                return true;
            else
                return false;
        }
    }

    private class predecirDatos extends Behaviour {
        public void action() {
            if (prediccionX != 0 && prediccionZ != 0 && metodoCargado) {
                rMultiple.metodoRegresion(metodo);
                rMultiple.predecir(prediccionX, prediccionZ);
                prediccionX = 0;
                prediccionZ = 0;
            }
        }

        public boolean done() {
            if (terminado)
                return true;
            else
                return false;
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}
