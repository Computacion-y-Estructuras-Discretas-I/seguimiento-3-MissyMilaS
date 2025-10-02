package ui;

import java.util.Scanner;
import structures.PilaGenerica;
import structures.TablasHash;

public class Main {

    private Scanner sc;

    public Main() {
        sc = new Scanner(System.in);
    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("Hola Jacob ponme 5 por fa UwU");
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                    break;

                case 2:
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }

                    encontrarParesConSuma(numeros, objetivo);
                    break;

                case 3:
                    System.out.println("Chao NO LO QUIERO VAYASE MLP (My Little Pony)");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {
        int size= s.length();
        PilaGenerica<Character> pila= new PilaGenerica<>(size);

        if (s.equals("")) {
            return true;
        }

        for (int i = 0; i< size; i++) {
            char c= s.charAt(i);

            if (c== '(' || c== '{'|| c=='[') {
                pila.Push(c);
            }else if(c== ')' || c== '}'|| c==']'){
                if (pila.getTop()==0) {
                    return false;
                }

                char ultimo= pila.Pop();
                if (((c==')')&&(ultimo!='('))||((c=='}')&&(ultimo!='{'))||((c==']')&&(ultimo!='['))) {
                    return false;
                }
            }
        }
        return pila.getTop() == 0;
    }

    /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     */
    public void encontrarParesConSuma(int[] numeros, int objetivo) {
        int size= numeros.length;
        String pare= "";
        try {
            TablasHash tabla = new TablasHash(size);

            for (int x : numeros) {
                tabla.insert(Math.abs(x), x);
            }

            for (int x : numeros) {
                int complemento = objetivo - x;
                if (complemento!=x) {
                    if (tabla.search(Math.abs(complemento),complemento)) { 
                        pare += "("+complemento+","+x+")\n";
                        tabla.delete(Math.abs(complemento), complemento);
                        tabla.delete(Math.abs(x), x);
                        if (tabla.search(Math.abs(x), x) || tabla.search(Math.abs(complemento), complemento)) {
                            tabla.delete(Math.abs(complemento), complemento);
                            tabla.delete(Math.abs(x), x);
                            }
                        }  
                }else{
                    pare="No hay suma de pares para"+ objetivo;
                    }     
            }
            System.out.println(pare);
               
        } catch (Exception e) {
            System.out.println("Error con TablasHash: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
