/*
 * Creado por Fernando López Trejo
 * Ejercicio personal para el curso:
 * 	Java  8 Desde 0 de OpenWebinars
 */

package buscaminas;

import java.util.Random;
import java.util.Scanner;

public class Buscaminas {
	
	//constantes que nos dibujaran el tamaño de tablero
	final static int PRINCIPIANTE = 8;
	final static int INTERMEDIO = 16;
	final static int EXPERTO = 30; //Como seguimos las indicaciones de wikipedia el experto es 16 x 30. Se implementará de esta forma en el juego. (Intermedio,Experto)
	
	//Cte para el símbolo de bomba y del marcador de bomba
	final static char BOMBA = 'O';
	final static char MARCADOR = 'V';
	final static char VACIO = '.';
	
	private static Scanner sc; 
	private static boolean validSel = false;
	
	public static void main (String[] args) {
		
		//La clase scanner nos valdrá para leer las entradas de teclado para marcar nuestros movimientos
		sc = new Scanner(System.in);
		
		/*
		 * Explicamos la mécanica del juego y solicitamos 
		 * al jugador que seleccione un nivel de dificultad.
		 * Conforme a este nivel dibujaremos un mapa de minas u otro
		 * 
		 */
		
		System.out.println("|-----BUSCAMINAS-----|");
		System.out.println("");
		System.out.println("Bievenido al juego del buscaminas. Su objetivo es destapar todas las casillas sin que aparezca una mina bajo ella.");
		System.out.println("Según el nivel de dificultad el número de mina y el tamaño del tablero variará. ");
		System.out.println("");
		System.out.println("NIVELES:");
		System.out.println("Principiante (P) | tablero 8x8 | Minas a descubrir:10 minas");
		System.out.println("Intermedio (I) | tablero 16x16 | Minas a descubrir:40 minas");
		System.out.println("Experto (E) | tablero 16x30 | Minas a descubrir:99 minas");
		System.out.println("");
		System.out.println("Por favor seleccione un nivel (P, I, E): ");
		
		String nivel = sc.nextLine();
		nivel = nivel.toUpperCase();
		
		String caso = "";
		
		do {
			System.out.println("");
			//Según seleccionamos nivel se nos confirma el tipo de selección
			switch(nivel) {
			case "P": System.out.println("Nivel seleccionado PRINCIPIANTE \n");
						caso ="P";
						break;
			case "I": System.out.println("Nivel seleccionado INTERMEDIO \n");
						caso = "I";
						break;
			case "E": System.out.println("Nivel seleccionado EXPERTO \n");
						caso = "E";
						break;
			}
			
			if( nivel.equals(caso)  != true ) {
				
				System.out.println("Por favor seleccione un nivel correco P ; I ; E:");
				nivel = sc.nextLine();
				nivel = nivel.toUpperCase();
				
			}else { 
				
				validSel = true;
				
			}
		} while (validSel != true);
		
		System.out.println("Para marcar una casilla introduzca  coordenadas siguiendo el ejemplo. Ej: 2,5");
		
			char[][] mapaBM = selectNivel(nivel);
			
		while(true) {
			validSel = false;
			int ptoUno, ptoDos;
			System.out.println("Introduzca coordenadas:");
			 
				do {
					String ptosCoordenadas = sc.nextLine(); 
					ptoUno = Integer.parseInt(ptosCoordenadas.substring(0,1));
					ptoDos = Integer.parseInt(ptosCoordenadas.substring(2));
					
					validSel = validarCoordenadas(ptoUno, ptoDos, nivel);
					if(validSel == false) {
						System.out.println("Por favor, introduzca coordenadas dentro del rango: ");
					}
				
				}while(validSel != true);
			
			//Si las coordenadas son correctas
			//pasamos a descubir la casilla y ver si está VACIA o hay BOMBA
			comprobarCasilla(ptoUno, ptoDos, mapaBM,nivel);
		}
	}

	//Comprobamos que las coordenadas están dentro de
	//los párametros que contemple nuestra matriz
	//no puede ser superior al número de filas ni de columnas
	private static boolean validarCoordenadas(int ptoA, int ptoB, String nivel) {
		boolean validacion = false;
		ptoA = ptoA - 1;
		ptoB = ptoB - 1;
		
		switch(nivel) {
			case "P": //tablero 8x8 = 0->7
				if((ptoA > -1 && ptoA < 8 ) && (ptoB > -1 && ptoB < 8)) {
					validacion = true;
				}else {validacion = false;}
				break;
			case "I": //tablero 16x16 = 0->15
				if((ptoA > -1 && ptoA < 16 ) && (ptoB > -1 && ptoB < 16)) {
					validacion = true;
				}else {validacion = false;}
				break;
			case"E": //tablero 16x30 = 0->15/0->29
				if((ptoA > -1 && ptoA < 16 ) && (ptoB > -1 && ptoB < 29)) {
					validacion = true;
				}else {validacion = false;}
				break;
			}
		
		return validacion;
	}
	
	
	//Método para comprobar que hay detrás de la casilla
	//seleccionada si hay BOMBA el juego termina
	//Si hay espacio VACIO pediremos otra tirada
	public static void comprobarCasilla(int ptoA, int ptoB, char[][] mapa, String nivel) {
		boolean endGame = false;
		int i = 0,j = 0;//fila, columna
		
		switch(nivel){
		case "P": 
			 i = PRINCIPIANTE;
			 j = PRINCIPIANTE;
			break;
		case"I":
			 i = INTERMEDIO;
			 i = INTERMEDIO;
			break;
		case "E":
			 i = INTERMEDIO;
			 j = EXPERTO;
			break;
		}
		
		if(mapa[ptoA-1][ptoB-1] == VACIO) {
				redibujarMapa(endGame, ptoA, ptoB, mapa,i,j);
		}else { 
			endGame = true;
			redibujarMapa(endGame, ptoA, ptoB, mapa,i,j);
			System.out.println("\nBOMBAAAAAA!!!! EL JUEGO HA TERMINADO");
			System.exit(0);
			
		}
	}


	private static void redibujarMapa(boolean finDelJuego, int ptoA, int ptoB, char[][] mapa,int fila, int columna) {

			for( int i = 0; i<fila;i++) {
				for(int j = 0; j<columna;j++) {
					if((i == ptoA-1) && ( j == ptoB-1) && (finDelJuego != true)) {
						mapa[i][j] = VACIO;
					}
					else if((i == ptoA-1) && ( j == ptoB-1) && (finDelJuego == true)){
						mapa[i][j] = BOMBA;
					}else { mapa[i][j] = '#';}
				}
			}
			
			//Dibujamos un encabezado de coordenadas de columnas
			//según número de columnas'j'
			for (int k = 0; k <= columna; k++) {
				if(k == 0) {
					System.out.print( "   ");
				}else  if(k < 10) {
					System.out.print( k +  "  ");
				}else { System.out.print( k +  " ");}
				
				if(k == columna) {
					System.out.println();
				}
			}
	
			//dibujamos el mapa ocultando todas las casillas
			//y dibujamos el número de fila 
			for (int l = 0; l < fila; l++) {
				if ((l+1)<10) {
					System.out.print((l + 1) + "  ");
				} else {System.out.print((l + 1) + " ");}
				
				for (int m = 0; m < columna; m++) {
					System.out.print(mapa[l][m] + "  ");
				}
				System.out.println();
			}
	}

	//Método al que le pasamos el nivel seleccionado
	//y nos crea un array bidimensional de acuerdo 
	//a la distribución de cada nivel
	private static char[][] selectNivel(String nivel) {
		int fila = 0, columna = 0;

		
		switch(nivel) {
		case "P": 	fila = PRINCIPIANTE;
					columna = PRINCIPIANTE;
					break;
		case "I":	fila = INTERMEDIO;
					columna = INTERMEDIO;
					break;
		case "E":	fila = INTERMEDIO;
					columna = EXPERTO;
					break;
		}

		return dibujarMapa(fila, columna);
	}

	
	//Método que dibuja el mapa  de acuerdo  las medidas
	//que se le pasan desde el método selectNivel
	private static char[][] dibujarMapa(int fila, int columna) {
		
		char[][] mapa = new char[fila][columna];
		char[] columnaCoor = new char[columna];
		
		//Rellenamos todo el mapa con VACIOS
		for(int i = 0; i < fila; i++) {
			for(int j = 0; j < columna; j++) {
				mapa[i][j] = VACIO;
			}
		}
				
		
		//el número de bombas depende de la columna, así que 
		//según columna daremos a la variable bomba un valor u otro
		//rellenamos la matriz con las bombas de forma aleatoria
		int bombas = 0;
		
		switch(columna) {
		case PRINCIPIANTE: bombas = 10;
				break;
		case INTERMEDIO: bombas = 40;
				break;
		case EXPERTO: bombas = 99;
				break;
		}
		System.out.println("El número de bombas en este mapa es de: " + bombas +"\n");
		int ptoFila, ptoColumna;
		while(bombas != 0) {
			boolean position = false;
				while (position != true) {
					
					if(columna == EXPERTO) {
						ptoFila = coordenadasAleatorias(INTERMEDIO);
						ptoColumna = coordenadasAleatorias(columna);
					}else {
						ptoFila = coordenadasAleatorias(columna);
						ptoColumna = coordenadasAleatorias(columna);
					}
						if(mapa[ptoFila][ptoColumna] == VACIO) {
							mapa[ptoFila][ptoColumna] = BOMBA;
							
							position = true;
						}
					
				}
			
			bombas = bombas - 1;
		}
		
		//Dibujamos un encabezado de coordenadas de columnas
		//según número de columnas'j'
		for (int j = 0; j <= columna; j++) {
			if(j == 0) {
				System.out.print( "   ");
			}else  if(j < 10) {
				System.out.print( j +  "  ");
			}else { System.out.print( j +  " ");}
			
			if(j == columna) {
				System.out.println();
			}
		}

		//dibujamos el mapa ocultando todas las casillas
		//y dibujamos el número de fila 
		for (int i = 0; i < fila; i++) {
			if ((i+1)<10) {
				System.out.print((i + 1) + "  ");
			} else {System.out.print((i + 1) + " ");}
			
			for (int j = 0; j < columna; j++) {
				System.out.print("#  ");
			}
			System.out.println();
		}
		return mapa;
	}
	
	private static int coordenadasAleatorias(int columna) {
		int aleatorio = (int)(Math.random()*columna);
		return aleatorio;
	}
	
	
	
}
