package examples.regresionMultiple;

public class RegresionMultiple {
    private double arrayX[], arrayY[], arrayZ[];
    private double x, y, z, xy, xx, xz, yz, zz, a, b, c;
    private int tamanio;
    private String metodo;

    public RegresionMultiple() {
        arrayX = new double[] { 0, 2, 2.5, 1, 4, 7 };
        arrayY = new double[] { 5, 10, 9, 0, 3, 27 };
        arrayZ = new double[] { 0, 1, 2, 3, 6, 2 };
        x = 0;
        y = 0;
        z = 0;
        xx = 0;
        xy = 0;
        a = 0;
        b = 0;
        c = 0;
        tamanio = arrayY.length;
    }

    public void entrenar() {
        for (int i = 0; i < tamanio; i++) {
            System.out.println("Dado " + arrayX[i] + " y " + arrayZ[i] + " => " + arrayY[i]);
            x += arrayX[i];
            y += arrayY[i];
            z += arrayZ[i];
            xy += arrayX[i] * arrayY[i];
            xx += arrayX[i] * arrayX[i];
            xz += arrayX[i] * arrayZ[i];
            yz += arrayY[i] * arrayZ[i];
            zz += arrayZ[i] * arrayZ[i];
        }
        // cramer();
        // algebraMatricial();
        if (metodo.equals("cramer")) {
            cramer();
            System.out.println("Resuelto con Cramer!!");
        } else {
            algebraMatricial();
            System.out.println("Resuelto con algebra matricial!!");
        }
        // b = (tamanio * xy - x * y) / (tamanio * xx - x * x);
        // a = (y - (b * x)) / tamanio;
    }

    public void predecir(double prediccionX, double prediccionZ) {
        double resultado = (a * prediccionX) + (b * prediccionZ) + c;
        System.out.println(resultado + " = " + a + "(" + prediccionX + ") + " + b + "(" + prediccionZ + ") + " + c);
    }

    public void metodoRegresion(String metodo) {
        this.metodo = metodo;
    }

    private void cramer() {
        double determinante = 0, determinanteX = 0, determinanteY = 0, determinanteZ = 0;
        determinante = ((x * xz * z) + (xx * zz * tamanio) + (xz * z * x))
                - ((tamanio * xz * xz) + (x * zz * x) + (z * z * xx));

        determinanteX = ((y * xz * z) + (z * x * yz) + (tamanio * xy * zz))
                - ((tamanio * xz * yz) + (y * x * zz) + (z * xy * z));

        determinanteY = ((x * xy * z) + (xx * yz * tamanio) + (xz * y * x))
                - ((tamanio * xy * xz) + (x * yz * x) + (z * y * xx));

        determinanteZ = ((x * xz * yz) + (z * xy * xz) + (y * xx * zz))
                - ((y * xz * xz) + (x * xy * zz) + (z * xx * yz));

        a = determinanteX / determinante;
        b = determinanteY / determinante;
        c = determinanteZ / determinante;
    }

    private void algebraMatricial() {
        double[][] matriz = crearMatriz();
        double[][] trans = matrizTrans(matriz);
        double[][] multXTrans = multiplicacionDeMatrices(trans, matriz);
        double[][] matrizInversa = inversa(multXTrans);
        double[] vectorTransY = matrizPorVector(arrayY, trans);
        double[] resultados = matrizPorVector(vectorTransY, matrizInversa);
        a = resultados[1];
        b = resultados[2];
        c = resultados[0];
    }

    private double[][] crearMatriz() {
        double matriz[][] = new double[arrayX.length][3];
        for (int i = 0; i < arrayX.length; i++) {
            matriz[i][0] = 1;
            matriz[i][1] = arrayX[i];
            matriz[i][2] = arrayZ[i];
        }
        return matriz;
    }

    private double[][] matrizTrans(double[][] matriz) {
        double transpuesta[][] = new double[matriz[0].length][matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                transpuesta[j][i] = matriz[i][j];
            }
        }
        return transpuesta;
    }

    private double[][] multiplicacionDeMatrices(double[][] m1, double[][] m2) {
        double[][] resultado = new double[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m1[0].length; k++) {
                    resultado[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return resultado;
    }

    private double[][] inversa(double[][] matriz) {
        double[][] matrizInversa = new double[matriz.length][matriz[0].length];
        double matrizAdjunta[][] = adjunta(matriz);
        double determinante = ((matriz[0][0] * matriz[1][1] * matriz[2][2])
                + (matriz[1][0] * matriz[2][1] * matriz[0][2]) + (matriz[2][0] * matriz[0][1] * matriz[1][2]))
                - ((matriz[0][2] * matriz[1][1] * matriz[2][0]) + (matriz[1][2] * matriz[2][1] * matriz[0][0])
                        + (matriz[2][2] * matriz[0][1] * matriz[1][0]));
        double[][] transpuestaAdjunta = matrizTrans(matrizAdjunta);
        for (int i = 0; i < transpuestaAdjunta.length; i++) {
            for (int j = 0; j < transpuestaAdjunta[0].length; j++) {
                matrizInversa[i][j] = transpuestaAdjunta[i][j] / determinante;
                // System.out.print(matrizInversa[i][j] + " ");
            }
            // System.out.println(" ");
        }
        return matrizInversa;
    }

    private double[][] adjunta(double[][] matriz) {
        double[][] matrizAdjunta = new double[3][3];
        matrizAdjunta[0][0] = (matriz[1][1] * matriz[2][2]) - (matriz[1][2] * matriz[2][1]);
        matrizAdjunta[0][1] = -((matriz[1][0] * matriz[2][2]) - (matriz[1][2] * matriz[2][0]));
        matrizAdjunta[0][2] = (matriz[1][0] * matriz[2][1]) - (matriz[1][1] * matriz[2][0]);

        matrizAdjunta[1][0] = -((matriz[0][1] * matriz[2][2]) - (matriz[0][2] * matriz[2][1]));
        matrizAdjunta[1][1] = (matriz[0][0] * matriz[2][2]) - (matriz[0][2] * matriz[2][0]);
        matrizAdjunta[1][2] = -((matriz[0][0] * matriz[2][1]) - (matriz[0][1] * matriz[2][0]));

        matrizAdjunta[2][0] = (matriz[0][1] * matriz[1][2]) - (matriz[0][2] * matriz[1][1]);
        matrizAdjunta[2][1] = -((matriz[0][0] * matriz[1][2]) - (matriz[0][2] * matriz[1][0]));
        matrizAdjunta[2][2] = (matriz[0][0] * matriz[1][1]) - (matriz[0][1] * matriz[1][0]);

        return matrizAdjunta;
    }

    private double[] matrizPorVector(double[] vector, double[][] matriz) {
        double[] vectorResultante = new double[matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                vectorResultante[i] += matriz[i][j] * vector[j];
            }
        }
        return vectorResultante;
    }
}